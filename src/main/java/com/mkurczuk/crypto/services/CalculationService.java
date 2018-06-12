package com.mkurczuk.crypto.services;

import com.mkurczuk.crypto.model.api.CryptoExchangeRate;
import com.mkurczuk.crypto.model.api.CurrencyExchangeRate;
import com.mkurczuk.crypto.model.api.StockMarketIndexRate;
import com.mkurczuk.crypto.model.database.ReportLog;
import com.mkurczuk.crypto.repositories.ReportLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
public class CalculationService {

    private final ReportLogRepository reportLogRepository;

    @Autowired
    public CalculationService(ReportLogRepository reportLogRepository) {
        this.reportLogRepository = reportLogRepository;
    }

    public void calculate(CryptoExchangeRate cryptoExchangeRate, CurrencyExchangeRate currencyExchangeRate, StockMarketIndexRate stockMarketIndexRate) {
        BigDecimal value = cryptoExchangeRate.getPrice().multiply(currencyExchangeRate.getPrice()).divide(stockMarketIndexRate.getPrice(),10,  BigDecimal.ROUND_HALF_UP);
//        ReportLog reportLog = new ReportLog(value, LocalDateTime.now());
//        log.info(reportLog.toString());
//        reportLogRepository.save(reportLog);
    }
}
