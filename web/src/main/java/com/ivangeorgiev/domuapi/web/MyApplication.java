package com.ivangeorgiev.domuapi.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.ivangeorgiev.domuapi.web",
        "com.ivangeorgiev.domuapi.core",
        "com.ivangeorgiev.domuapi.data"
})
@EnableJpaRepositories("com.ivangeorgiev.domuapi.data.repositories")
@EntityScan("com.ivangeorgiev.domuapi.data.models")
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}