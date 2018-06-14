package com.mkurczuk.crypto.services.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkurczuk.crypto.model.Cryptocurrency;
import com.mkurczuk.crypto.services.AppProps;
import com.mkurczuk.crypto.services.RestService;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CurrencyExchangeServiceTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private AppProps appProps;

    @Mock
    private RestService restService;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    public void shouldReturnList() {
//        appProps.currencyUrl = "url";
//        appProps.exchangeCurrency = "currency";
//        CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService(appProps, restService, objectMapper);
//        when(restService.doGet(anyString())).thenReturn("Ok");
//        when(objectMapper.readValue("Ok", CryptocurrencyRate.class)).thenReturn(cryptocurrencyRate);
//        when(cryptocurrencyRate.getPrice()).thenReturn(BigDecimal.ONE);
//        List<CurrencyExchangeRate> currencyExchangeRates = currencyExchangeService.getCurrencyExchangeRates(LocalDateTime.now(), LocalDateTime.now());
//
//        assertThat(currencyExchangeRates).doesNotContainNull().hasSize(10);
    }
}

