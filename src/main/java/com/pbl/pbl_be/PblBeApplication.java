package com.pbl.pbl_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class PblBeApplication {

    public static void main(String[] args) {

        SpringApplication.run(PblBeApplication.class, args);
        System.out.println("Application started successfully!");
    }

}
