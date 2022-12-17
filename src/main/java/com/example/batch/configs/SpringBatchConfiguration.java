package com.example.batch.configs;

import com.example.batch.models.StudentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(value = "SpringBatchConfiguration")
public class SpringBatchConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SpringBatchConfiguration.class);

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("studentDTOJdbcCursorItemReader")
    private JdbcCursorItemReader<StudentDTO> jdbcCursorItemReader;

    @Autowired
    @Qualifier("studentDTOJdbcBatchItemWriter")
    private JdbcBatchItemWriter<StudentDTO> studentDTOJdbcBatchItemWriter;

    @Bean(name = "dataJob")
    public Job dataJob() {
        log.info("-------------Inside the Job------------");
        return this.jobBuilderFactory.get("dataJob")
                .incrementer(new RunIdIncrementer())
                .start(dataStep())
                .build();
    }

    private Step dataStep() {
        log.info("-------------Inside the Step ------------");
        return this.stepBuilderFactory.get("dataStep")
                .<StudentDTO, StudentDTO>chunk(10)
                .reader(jdbcCursorItemReader)
                .writer(studentDTOJdbcBatchItemWriter)
                .build();
    }
}
