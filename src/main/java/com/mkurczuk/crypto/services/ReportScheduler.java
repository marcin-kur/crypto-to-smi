package com.mkurczuk.crypto.services;

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
public class ReportScheduler {
    private final CryptoExchangeService cryptoExchangeService;
    private final CryptocurrencyRepository cryptocurrencyRepository;
    private final StockMarketIndexService stockMarketIndexService;
    private final StockMarketIndexRepository stockMarketIndexRepository;

    @Scheduled(fixedRate = 300000)
    public void doReports() {
        doCryptoReport();
        doStockMarketIndexesReport();
    }

    void doCryptoReport() {
        cryptocurrencyRepository
                .findAll()
                .forEach(cryptoExchangeService::run);

    }

    void doStockMarketIndexesReport() {
        stockMarketIndexRepository
                .findAll()
                .forEach(stockMarketIndexService::run);
    }
}

