package com.mkurczuk.crypto.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkurczuk.crypto.model.api.CurrencyExchangeRate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class CurrencyExchangeService {

    private final UrlProps urlProps;

    @Autowired
    public CurrencyExchangeService(UrlProps urlProps) {
        this.urlProps = urlProps;
    }

    public CurrencyExchangeRate run(String currencyCode) {
        String url = String.format(urlProps.currency, currencyCode);
        RestService restService = new RestService();
        String response = restService.doGet(url);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            CurrencyExchangeRate currencyExchangeRate = objectMapper.readValue(response, CurrencyExchangeRate.class);
            log.info(currencyExchangeRate.toString());
            return currencyExchangeRate;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
