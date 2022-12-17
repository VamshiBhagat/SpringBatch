package com.example.batch.writers;

import com.example.batch.models.StudentDTO;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;

@Service
public class SpringDataWriter {

    @Autowired
    @Qualifier(value = "targetDataSource")
    private DataSource dataSource;

    @Bean(name = "studentDTOJdbcBatchItemWriter")
    public JdbcBatchItemWriter<StudentDTO> studentDTOJdbcBatchItemWriter() throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        boolean truncateTableStudent = statement.execute("TRUNCATE TABLE student");
        if (!truncateTableStudent) {
            JdbcBatchItemWriter<StudentDTO> studentDTOJdbcBatchItemWriter = new JdbcBatchItemWriter<>();
            studentDTOJdbcBatchItemWriter.setDataSource(dataSource);
            studentDTOJdbcBatchItemWriter.setSql("INSERT INTO student VALUES(?,?,?,?,?,?)");
            studentDTOJdbcBatchItemWriter.setItemPreparedStatementSetter(new ItemPreparedStatementSetter<StudentDTO>() {
                @Override
                public void setValues(StudentDTO item, PreparedStatement ps) throws SQLException {
                    ps.setInt(1, item.getId());
                    ps.setString(2, item.getName());
                    ps.setInt(3, item.getAge());
                    ps.setLong(4, Thread.currentThread().getId());
                    ps.setString(5, Thread.currentThread().getName());
                    ps.setTimestamp(6, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
                }
            });
            return studentDTOJdbcBatchItemWriter;
        }
        return null;
    }
}
