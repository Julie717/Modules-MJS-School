package com.epam.esm.dao.connection;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@PropertySource("classpath:database.properties")
@ComponentScan("com.epam.esm")
public class ApplicationConfig {
    private static final String TIME_ZONE_NAME = "serverTimezone";
    @Value("${driverName}")
    private String driverName;

    @Value("${url}")
    private String url;

    @Value("${user}")
    private String user;

    @Value("${password}")
    private String password;

    @Value("${initialSize}")
    private int initialSize;

    @Value("${maxNonActive}")
    private int maxNonActive;

    @Value("${serverTimezone}")
    private String serverTimezone;

    @Bean
    public BasicDataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driverName);
        ds.setUrl(url);
        ds.setUsername(user);
        ds.setPassword(password);
        ds.setInitialSize(initialSize);
        ds.setMaxIdle(maxNonActive);
        ds.addConnectionProperty(TIME_ZONE_NAME, serverTimezone);
        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}