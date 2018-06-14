package com.mkurczuk.crypto.services.report;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class CurrencyExchangeRate {
    @JsonProperty("effectiveDate")
    final LocalDate date;
    @JsonProperty("bid")
    final String code;
    @JsonProperty("ask")
    final BigDecimal price;

    @JsonCreator
    public CurrencyExchangeRate(@JsonProperty("effectiveDate") String date, @JsonProperty("bid") String code, @JsonProperty("ask") String price) {
        this.date = LocalDate.parse(date);
        this.code = code;
        this.price = new BigDecimal(price);
    }


}
