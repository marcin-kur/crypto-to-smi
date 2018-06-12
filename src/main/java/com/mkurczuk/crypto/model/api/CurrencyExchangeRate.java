package com.mkurczuk.crypto.model.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mkurczuk.crypto.services.deserializers.CurrencyExchangeRateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonDeserialize(using = CurrencyExchangeRateDeserializer.class)
public class CurrencyExchangeRate {
    private final String code;
    private final BigDecimal price;
}