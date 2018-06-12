package com.mkurczuk.crypto.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkurczuk.crypto.model.api.CryptoExchangeRate;
import com.mkurczuk.crypto.model.database.Cryptocurrency;
import com.mkurczuk.crypto.model.database.ReportLog;
import com.mkurczuk.crypto.repositories.ReportLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class CryptoExchangeService {
    private final UrlProps urlProps;
    private final RestService restService;
    private final ReportLogRepository reportLogRepository;

    @Autowired
    public CryptoExchangeService(UrlProps urlProps, RestService restService, ReportLogRepository reportLogRepository) {
        this.urlProps = urlProps;
        this.restService = restService;
        this.reportLogRepository = reportLogRepository;
    }

    public void run(Cryptocurrency cryptocurrency) {
        String url = String.format(urlProps.crypto, cryptocurrency.getCode(), "USD");
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
