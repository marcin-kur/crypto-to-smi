package com.mkurczuk.crypto.services.scheduler;

import com.mkurczuk.crypto.repositories.CryptocurrencyRepository;
import com.mkurczuk.crypto.repositories.StockMarketIndexRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
class ReportLogScheduler {
    private final CryptocurrencyLogger cryptocurrencyLogger;
    private final CryptocurrencyRepository cryptocurrencyRepository;
    private final StockMarketIndexLogger stockMarketIndexService;
    private final StockMarketIndexRepository stockMarketIndexRepository;

    @Scheduled(fixedRate = 600000)
    void doReports() {
        createCryptoReportLogs();
        createStockMarketIndexesReportLogs();
    }

    void createCryptoReportLogs() {
        cryptocurrencyRepository
                .findAll()
                .forEach(cryptocurrencyLogger::log);
    }

    void createStockMarketIndexesReportLogs() {
        stockMarketIndexRepository
                .findAll()
                .forEach(stockMarketIndexService::log);
    }
}

