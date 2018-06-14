package com.mkurczuk.crypto.services.report;

import com.mkurczuk.crypto.model.Cryptocurrency;
import com.mkurczuk.crypto.model.StockMarketIndex;
import lombok.Data;

import java.time.LocalDateTime;

@Data
class ReportGeneratorConfig {
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final Cryptocurrency cryptocurrency;
    private final StockMarketIndex stockMarketIndex;
    private final int interval;

    ReportGeneratorConfig(LocalDateTime startDateTime, LocalDateTime endDateTime, Cryptocurrency cryptocurrency, StockMarketIndex stockMarketIndex, int interval) {
        validateDates(startDateTime, endDateTime);

        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.cryptocurrency = cryptocurrency;
        this.stockMarketIndex = stockMarketIndex;
        this.interval = interval;
    }

    private void validateDates(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if(startDateTime.isAfter(endDateTime)) {
            throw new ReportGeneratorConfigException("Start date is after end date");
        }
    }
}
