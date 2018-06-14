package com.mkurczuk.crypto.services.report;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class CurrencyExchangeRates {
    private final List<CurrencyExchangeRate> rates;

    @JsonCreator
    public CurrencyExchangeRates(@JsonProperty("rates") List<CurrencyExchangeRate> rates) {
        this.rates = rates;
    }
}