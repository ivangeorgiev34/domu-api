package com.ivangeorgiev.domuapi.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@ConfigurationProperties(prefix = "taxes")
@Getter
@Setter
public class PricesConfigProperties {

    private BigDecimal perResidentTax;
    private BigDecimal petTax;
}
