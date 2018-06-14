package com.mkurczuk.crypto.services.report;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import com.mkurczuk.crypto.services.AppProps;
import com.mkurczuk.crypto.services.RestException;
import com.mkurczuk.crypto.services.RestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class CurrencyExchangeService {
    private final AppProps appProps;
    private final RestService restService;
    private final ObjectMapper objectMapper;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    List<CurrencyExchangeRate> getCurrencyExchangeRates(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        String url = getUrl(startDateTime, endDateTime);
        String response = restService.doGet(url);
        return parseResponse(response);
    }

    private List<CurrencyExchangeRate> parseResponse(String response) {
        try {
            CurrencyExchangeRates currencyExchangeRates = objectMapper.readValue(response, CurrencyExchangeRates.class);
            return currencyExchangeRates.getRates();
        } catch (IOException e) {
            log.error("Could not parse CurrencyExchangeRates", e);
            throw new RestException("Failed to get currency exchange rates.");
        }
    }

    private String getUrl(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return String.format(
                appProps.currencyUrl,
                appProps.exchangeCurrency,
                startDateTime.format(DATE_TIME_FORMATTER),
                endDateTime.format(DATE_TIME_FORMATTER));
    }
}
