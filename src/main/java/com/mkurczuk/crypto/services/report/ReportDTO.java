package com.mkurczuk.crypto.services.report;

import lombok.Data;

@Data
public class ReportDTO {
    private final String startDateTime;
    private final String endDateTime;
    private final String exchangeRate;
    private final String cryptocurrencyAverage;
    private final String stockIndexAverage;
    private final String calculatedValue;
}
