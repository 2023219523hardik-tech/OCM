package com.ocms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.ocms")
public class OcmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(OcmsApplication.class, args);
    }
}