package com.shopKpr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OwoDokanApplication {
    public static void main(String[] args) {
        SpringApplication.run(OwoDokanApplication.class, args);
    }
}
