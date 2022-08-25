package com.campus.dataset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.campus.common","com.campus.dataset"})
public class DataSetApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataSetApplication.class, args);
    }
}
