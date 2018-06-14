package com.mkurczuk.crypto.services.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkurczuk.crypto.model.Cryptocurrency;
import com.mkurczuk.crypto.model.ReportLog;
import com.mkurczuk.crypto.repositories.ReportLogRepository;
import com.mkurczuk.crypto.services.RestService;
import com.mkurczuk.crypto.services.AppProps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
class CryptocurrencyLogger {
    private final AppProps appProps;
    private final RestService restService;
    private final ReportLogRepository reportLogRepository;
    private final ObjectMapper objectMapper;

    void log(Cryptocurrency cryptocurrency) {
        String url = String.format(appProps.cryptoUrl, cryptocurrency.getCode(), appProps.exchangeCurrency);
        String response = restService.doGet(url);
        Optional<CryptocurrencyRate> optionalCryptoExchangeRate = parseResponse(response);
        optionalCryptoExchangeRate.ifPresent(cryptocurrencyRate ->
                reportLogRepository.save(getReportLog(cryptocurrency, cryptocurrencyRate))
        );
    }

    Optional<CryptocurrencyRate> parseResponse(String response) {
        try {
            CryptocurrencyRate cryptocurrencyRate = objectMapper.readValue(response, CryptocurrencyRate.class);
            return Optional.ofNullable(cryptocurrencyRate);
        } catch (IOException e) {
            log.error("Could not parse CryptocurrencyRate", e);
            return Optional.empty();
        }
    }

    ReportLog getReportLog(Cryptocurrency cryptocurrency, CryptocurrencyRate cryptocurrencyRate) {
        return new ReportLog(cryptocurrency, cryptocurrencyRate.getPrice(), LocalDateTime.now());
    }
}
