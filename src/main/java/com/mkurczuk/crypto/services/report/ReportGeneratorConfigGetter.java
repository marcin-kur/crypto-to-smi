package com.mkurczuk.crypto.services.report;

import com.mkurczuk.crypto.model.Cryptocurrency;
import com.mkurczuk.crypto.model.StockMarketIndex;
import com.mkurczuk.crypto.repositories.CryptocurrencyRepository;
import com.mkurczuk.crypto.repositories.StockMarketIndexRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class ReportGeneratorConfigGetter {
    private final CryptocurrencyRepository cryptocurrencyRepository;
    private final StockMarketIndexRepository stockMarketIndexRepository;
    private final LocalDateParser localDateParser;

    public ReportGeneratorConfig get(
            Optional<String> startDate,
            Optional<String> endDate,
            Optional<String> cryptocurrency,
            Optional<String> stockMarketIndex,
            Optional<Integer> interval) {
        return new ReportGeneratorConfig(
                getStartDate(startDate),
                getEndDate(endDate),
                getCryptocurrency(cryptocurrency),
                getStockMarketIndex(stockMarketIndex),
                getInterval(interval)
        );
    }

    private LocalDateTime getStartDate(Optional<String> startDate) {
        if (!startDate.isPresent()) {
            log.error("startDate is empty:", startDate);
            throw new ReportGeneratorConfigException("Start Date is not populated correctly");
        }
        try {
            return localDateParser.parse(startDate.get())
                    .atStartOfDay();
        } catch (Exception e) {
            log.error("Failed to parse startDate", startDate, e);
            throw new ReportGeneratorConfigException("Start Date is not populated correctly");
        }
    }

    private LocalDateTime getEndDate(Optional<String> endDate) {
        if (!endDate.isPresent()) {
            throw new ReportGeneratorConfigException("End Date is not populated correctly");
        }
        try {
            return localDateParser.parse(endDate.get())
                    .atTime(23, 59, 59, 999999);
        } catch (Exception e) {
            log.error("Failed to parse endDate: ", endDate, e);
            throw new ReportGeneratorConfigException("End Date is not populated correctly");
        }
    }

    private Cryptocurrency getCryptocurrency(Optional<String> cryptocurrency) {
        return cryptocurrencyRepository
                .findCryptocurrencyByName(cryptocurrency
                        .orElseThrow(() -> new ReportGeneratorConfigException("Cryptocurrency is empty")))
                .orElseThrow(() -> new ReportGeneratorConfigException("Could not find Cryptocurrency with name: " + cryptocurrency.get()));
    }

    private StockMarketIndex getStockMarketIndex(Optional<String> stockMarketIndex) {
        return stockMarketIndexRepository
                .findStockMarketIndexByName(stockMarketIndex
                        .orElseThrow(() -> new ReportGeneratorConfigException("Stock Market Index is empty")))
                .orElseThrow(() -> new ReportGeneratorConfigException("Could not find Stock Market Index with name: " + stockMarketIndex.get()));
    }

    private int getInterval(Optional<Integer> interval) {
        return interval.orElseThrow(() -> new ReportGeneratorConfigException("Interval parameter is empty"));
    }
}
