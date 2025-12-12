package com.ivangeorgiev.domuapi.web.controller;

import com.ivangeorgiev.domuapi.core.PricesConfigProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/my")
public class MyController {
    private final PricesConfigProperties pricesConfigProperties;

    public MyController(PricesConfigProperties pricesConfigProperties){
        this.pricesConfigProperties = pricesConfigProperties;
    }

    @GetMapping("/e")
    public String sayHello() {
        return "hello";
    }
}
