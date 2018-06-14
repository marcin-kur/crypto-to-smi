package com.mkurczuk.crypto.services.scheduler;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonDeserialize(using = CryptoExchangeRateDeserializer.class)
class CryptoExchangeRate {
    private final String base;
    private final String target;
    private final BigDecimal price;
}