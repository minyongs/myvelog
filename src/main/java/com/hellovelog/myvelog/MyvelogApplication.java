package com.hellovelog.myvelog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MyvelogApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyvelogApplication.class, args);
    }

}
