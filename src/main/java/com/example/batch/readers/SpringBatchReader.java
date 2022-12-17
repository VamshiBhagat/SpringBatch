package com.example.batch.readers;

import com.example.batch.models.StudentDTO;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class SpringBatchReader {

    @Autowired
    @Qualifier(value = "sourceDataSource")
    private DataSource dataSource;

    @Bean(name = "studentDTOJdbcCursorItemReader")
    public JdbcCursorItemReader<StudentDTO> studentDTOJdbcCursorItemReader() {
        JdbcCursorItemReader<StudentDTO> studentDTOJdbcCursorItemReader = new JdbcCursorItemReader<StudentDTO>();
        studentDTOJdbcCursorItemReader.setName("Source-Student-Reader");
        studentDTOJdbcCursorItemReader.setDataSource(this.dataSource);
        studentDTOJdbcCursorItemReader.setSql("SELECT id,name,age FROM student");
        studentDTOJdbcCursorItemReader.setRowMapper(new RowMapper<StudentDTO>() {
            @Override
            public StudentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new StudentDTO(rs.getInt(1), rs.getString(2), rs.getInt(3));
            }
        });
        studentDTOJdbcCursorItemReader.setQueryTimeout(10000);
        studentDTOJdbcCursorItemReader.setConnectionAutoCommit(true);
        return studentDTOJdbcCursorItemReader;
    }
}
