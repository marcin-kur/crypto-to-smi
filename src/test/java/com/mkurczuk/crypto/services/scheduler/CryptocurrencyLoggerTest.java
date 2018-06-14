package com.mkurczuk.crypto.services.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkurczuk.crypto.model.Cryptocurrency;
import com.mkurczuk.crypto.repositories.ReportLogRepository;
import com.mkurczuk.crypto.services.AppProps;
import com.mkurczuk.crypto.services.RestService;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.IOException;
import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CryptocurrencyLoggerTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private AppProps appProps;

    @Mock
    private RestService restService;

    @Mock
    private ReportLogRepository reportLogRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    public void shouldSaveRecord() throws IOException {
        CryptocurrencyRate cryptocurrencyRate = mock(CryptocurrencyRate.class);
        Cryptocurrency cryptocurrency = mock(Cryptocurrency.class);
        appProps.cryptoUrl = "url";
        appProps.exchangeCurrency = "currency";
        when(cryptocurrency.getCode()).thenReturn("Code");
        when(restService.doGet(anyString())).thenReturn("Ok");
        when(objectMapper.readValue("Ok", CryptocurrencyRate.class)).thenReturn(cryptocurrencyRate);
        when(cryptocurrencyRate.getPrice()).thenReturn(BigDecimal.ONE);
        CryptocurrencyLogger cryptocurrencyLogger = new CryptocurrencyLogger(appProps, restService, reportLogRepository, objectMapper);

        cryptocurrencyLogger.log(cryptocurrency);

        verify(reportLogRepository, times(1)).save(any());
    }

    @Test
    public void shouldNotSaveWhenCryptoCurrencyRateIsEmpty() throws IOException {
        Cryptocurrency cryptocurrency = mock(Cryptocurrency.class);
        appProps.cryptoUrl = "url";
        appProps.exchangeCurrency = "currency";
        when(cryptocurrency.getCode()).thenReturn("Code");
        when(restService.doGet(anyString())).thenReturn("Ok");
        when(objectMapper.readValue("Ok", CryptocurrencyRate.class)).thenReturn(null);
        CryptocurrencyLogger cryptocurrencyLogger = new CryptocurrencyLogger(appProps, restService, reportLogRepository, objectMapper);

        cryptocurrencyLogger.log(cryptocurrency);

        verify(reportLogRepository, times(0)).save(any());
    }
}
