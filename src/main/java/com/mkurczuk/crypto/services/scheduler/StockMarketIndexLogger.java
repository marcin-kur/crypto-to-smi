package com.mkurczuk.crypto.services.scheduler;

import com.mkurczuk.crypto.model.ReportLog;
import com.mkurczuk.crypto.model.StockMarketIndex;
import com.mkurczuk.crypto.repositories.ReportLogRepository;
import com.mkurczuk.crypto.services.RestService;
import com.mkurczuk.crypto.services.AppProps;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
class StockMarketIndexLogger {
    private final AppProps appProps;
    private final StockMarketIndexParser stockMarketIndexParser;
    private final ReportLogRepository reportLogRepository;

    void log(StockMarketIndex stockMarketIndex) {
        String url = String.format(appProps.stockMarketIndexUrl, stockMarketIndex.getCode());
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
