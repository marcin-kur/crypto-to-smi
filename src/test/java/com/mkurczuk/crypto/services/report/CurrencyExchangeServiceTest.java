package com.mkurczuk.crypto.services.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkurczuk.crypto.services.AppProps;
import com.mkurczuk.crypto.services.RestException;
import com.mkurczuk.crypto.services.RestService;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
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
    public void shouldReturnCurrencyExchangeRates() throws IOException {
        CurrencyExchangeRates currencyExchangeRates = mock(CurrencyExchangeRates.class);
        appProps.currencyUrl = "url";
        appProps.exchangeCurrency = "currency";
        when(restService.doGet(anyString())).thenReturn("Ok");
        when(objectMapper.readValue("Ok", CurrencyExchangeRates.class)).thenReturn(currencyExchangeRates);
        when(currencyExchangeRates.getRates()).thenReturn(Collections.nCopies(10, mock(CurrencyExchangeRate.class)));
        CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService(appProps, restService, objectMapper);

        List<CurrencyExchangeRate> currencyExchangeRateList = currencyExchangeService.getCurrencyExchangeRates(LocalDateTime.now(), LocalDateTime.now());

        assertThat(currencyExchangeRateList).doesNotContainNull().hasSize(10);
    }

    @Test
    public void shouldThrowExceptionWhenDoGetFailed() throws IOException {
        appProps.currencyUrl = "url";
        appProps.exchangeCurrency = "currency";
        when(restService.doGet(anyString())).thenReturn("Ok");
        when(objectMapper.readValue("Ok", CurrencyExchangeRates.class)).thenThrow(new IOException());
        CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService(appProps, restService, objectMapper);

        Throwable thrown = catchThrowable(() -> currencyExchangeService.getCurrencyExchangeRates(LocalDateTime.now(), LocalDateTime.now()));

        assertThat(thrown).isInstanceOf(RestException.class);
    }
}

