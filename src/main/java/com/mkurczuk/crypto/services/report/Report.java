package com.mkurczuk.crypto.services.report;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
public class Report {
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final Optional<BigDecimal> exchangeRate;
    private final Optional<BigDecimal> cryptocurrencyAverage;
    private final Optional<BigDecimal> stockIndexAverage;
    private final Optional<BigDecimal> calculatedValue;
}