package com.apac.test.apactakehometest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ApacTakeHomeTestApplication {
    public static final Logger LOGGER = LogManager.getLogger(ApacTakeHomeTestApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ApacTakeHomeTestApplication.class, args);
    }

}
