package com.mkurczuk.crypto.services.scheduler;

import com.mkurczuk.crypto.model.Cryptocurrency;
import com.mkurczuk.crypto.model.StockMarketIndex;
import com.mkurczuk.crypto.repositories.CryptocurrencyRepository;
import com.mkurczuk.crypto.repositories.StockMarketIndexRepository;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ReportLogSchedulerTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private CryptocurrencyLogger cryptocurrencyLogger;

    @Mock
    private CryptocurrencyRepository cryptocurrencyRepository;

    @Mock
    private StockMarketIndexLogger stockMarketIndexLogger;

    @Mock
    private StockMarketIndexRepository stockMarketIndexRepository;

    @Test
    public void shouldCreateReportLogsForAllStockMarketIndexesCryptocurrencies() {
        when(cryptocurrencyRepository.findAll()).thenReturn(Collections.nCopies(100, mock(Cryptocurrency.class)));
        when(stockMarketIndexRepository.findAll()).thenReturn(Collections.nCopies(0, mock(StockMarketIndex.class)));
        doNothing().when(cryptocurrencyLogger).log(any());
        doNothing().when(stockMarketIndexLogger).log(any());
        ReportLogScheduler reportLogScheduler = new ReportLogScheduler(
                cryptocurrencyLogger,
                cryptocurrencyRepository,
                stockMarketIndexLogger,
                stockMarketIndexRepository);

        reportLogScheduler.doReports();

        verify(cryptocurrencyLogger, times(100)).log(any());
        verify(stockMarketIndexLogger, times(0)).log(any());
    }

    @Test
    public void shouldCreateReportLogsForAllCryptocurrencies() {
        when(cryptocurrencyRepository.findAll()).thenReturn(Collections.nCopies(100, mock(Cryptocurrency.class)));
        when(stockMarketIndexRepository.findAll()).thenReturn(Collections.nCopies(100, mock(StockMarketIndex.class)));
        doNothing().when(cryptocurrencyLogger).log(any());
        doNothing().when(stockMarketIndexLogger).log(any());
        ReportLogScheduler reportLogScheduler = new ReportLogScheduler(
                cryptocurrencyLogger,
                cryptocurrencyRepository,
                stockMarketIndexLogger,
                stockMarketIndexRepository);

        reportLogScheduler.doReports();

        verify(cryptocurrencyLogger, times(100)).log(any());
        verify(stockMarketIndexLogger, times(100)).log(any());
    }

    @Test
    public void shouldDoNothingForZeroRecord() {
        when(cryptocurrencyRepository.findAll()).thenReturn(Collections.nCopies(0, mock(Cryptocurrency.class)));
        when(stockMarketIndexRepository.findAll()).thenReturn(Collections.nCopies(0, mock(StockMarketIndex.class)));
        doNothing().when(cryptocurrencyLogger).log(any());
        doNothing().when(stockMarketIndexLogger).log(any());
        ReportLogScheduler reportLogScheduler = new ReportLogScheduler(
                cryptocurrencyLogger,
                cryptocurrencyRepository,
                stockMarketIndexLogger,
                stockMarketIndexRepository);

        reportLogScheduler.doReports();

        verify(cryptocurrencyLogger, times(0)).log(any());
        verify(stockMarketIndexLogger, times(0)).log(any());
    }
}
