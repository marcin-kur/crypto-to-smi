package com.mkurczuk.crypto.model.api;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockMarketIndexRate {
    private final String code;
    private final BigDecimal price;
}
