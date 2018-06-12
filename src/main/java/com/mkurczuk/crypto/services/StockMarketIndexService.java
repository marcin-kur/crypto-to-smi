package com.mkurczuk.crypto.services;

import com.mkurczuk.crypto.model.database.ReportLog;
import com.mkurczuk.crypto.model.database.StockMarketIndex;
import com.mkurczuk.crypto.repositories.ReportLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class StockMarketIndexService {

    private final UrlProps urlProps;
    private final StockMarketIndexParser stockMarketIndexParser;
    private final ReportLogRepository reportLogRepository;

    @Autowired
    public StockMarketIndexService(UrlProps urlProps, StockMarketIndexParser stockMarketIndexParser, ReportLogRepository reportLogRepository) {
        this.urlProps = urlProps;
        this.stockMarketIndexParser = stockMarketIndexParser;
        this.reportLogRepository = reportLogRepository;
    }

    public void run(StockMarketIndex stockMarketIndex) {
        String url = String.format(urlProps.stockMarketIndex, stockMarketIndex.getCode());
        RestService restService = new RestService();
        String response = restService.doGet(url);
        Optional<BigDecimal> optionalResult = stockMarketIndexParser.parse(response);
        optionalResult.ifPresent(result ->
                reportLogRepository.save(getReportLog(stockMarketIndex, result))
        );
    }

    ReportLog getReportLog(StockMarketIndex stockMarketIndex, BigDecimal value) {
        return new ReportLog(stockMarketIndex, value, LocalDateTime.now());
    }
}
