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
class CryptoExchangeLogger {
    private final AppProps appProps;
    private final RestService restService;
    private final ReportLogRepository reportLogRepository;

    void log(Cryptocurrency cryptocurrency) {
        String url = String.format(appProps.cryptoUrl, cryptocurrency.getCode(), appProps.exchangeCurrency);
        String response = restService.doGet(url);
        Optional<CryptoExchangeRate> optionalCryptoExchangeRate = parseResponse(response);
        optionalCryptoExchangeRate.ifPresent(cryptoExchangeRate ->
                reportLogRepository.save(getReportLog(cryptocurrency, cryptoExchangeRate))
        );
    }

    Optional<CryptoExchangeRate> parseResponse(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CryptoExchangeRate cryptoExchangeRate = objectMapper.readValue(response, CryptoExchangeRate.class);
            return Optional.of(cryptoExchangeRate);
        } catch (IOException e) {
            log.error("Could not parse CryptoExchangeRate", e);
            return Optional.empty();
        }
    }

    ReportLog getReportLog(Cryptocurrency cryptocurrency, CryptoExchangeRate cryptoExchangeRate) {
        return new ReportLog(cryptocurrency, cryptoExchangeRate.getPrice(), LocalDateTime.now());
    }
}
