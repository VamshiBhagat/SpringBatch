package com.example.batch.configs;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration(value = "SpringDatasourceConfiguration")
public class SpringDatasourceConfiguration {

    @Autowired
    private Environment environment;

    @Primary
    @Bean(name = "sourceDataSource")
    @ConfigurationProperties(prefix = "spring.primary")
    public DataSource primaryDatasource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(environment.getProperty("spring.primary.datasource.url"));
        driverManagerDataSource.setDriverClassName(environment.getProperty("spring.primary.datasource.driver-class-name"));
        driverManagerDataSource.setUsername(environment.getProperty("spring.primary.datasource.username"));
        driverManagerDataSource.setPassword(environment.getProperty("spring.primary.datasource.password"));
        return driverManagerDataSource;
    }

    @Bean(name = "targetDataSource")
    @ConfigurationProperties(prefix = "spring.secondary")
    public DataSource secondaryDatasource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(environment.getProperty("spring.secondary.datasource.url"));
        driverManagerDataSource.setDriverClassName(environment.getProperty("spring.secondary.datasource.driver-class-name"));
        driverManagerDataSource.setUsername(environment.getProperty("spring.secondary.datasource.username"));
        driverManagerDataSource.setPassword(environment.getProperty("spring.secondary.datasource.password"));
        return driverManagerDataSource;
    }
}
