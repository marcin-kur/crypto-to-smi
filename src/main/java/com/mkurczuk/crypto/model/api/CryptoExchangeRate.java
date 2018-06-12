package com.mkurczuk.crypto.model.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mkurczuk.crypto.services.deserializers.CryptoExchangeRateDeserializer;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonDeserialize(using = CryptoExchangeRateDeserializer.class)
public class CryptoExchangeRate {
    private final String base;
    private final String target;
    private final BigDecimal price;
}