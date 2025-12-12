package com.ivangeorgiev.domuapi.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.ivangeorgiev.domuapi.web",
        "com.ivangeorgiev.domuapi.core",
        "com.ivangeorgiev.domuapi.data"
})
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}