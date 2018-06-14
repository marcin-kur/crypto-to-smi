package com.mkurczuk.crypto.services.scheduler;

import com.mkurczuk.crypto.model.StockMarketIndex;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class StockMarketIndexLoggerTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private AppProps appProps;

    @Mock
    private RestService restService;

    @Mock
    private StockMarketIndexParser stockMarketIndexParser;

    @Mock
    private ReportLogRepository reportLogRepository;

    @Test
    public void shouldSaveRecord() throws IOException {
        StockMarketIndex stockMarketIndex = mock(StockMarketIndex.class);
        appProps.stockMarketIndexUrl = "url";
        when(stockMarketIndex.getCode()).thenReturn("Code");
        when(restService.doGet(anyString())).thenReturn("Ok");
        when(stockMarketIndexParser.parse(anyString())).thenReturn(Optional.of(BigDecimal.ONE));
        StockMarketIndexLogger stockMarketIndexLogger = new StockMarketIndexLogger(appProps, restService, stockMarketIndexParser, reportLogRepository);

        stockMarketIndexLogger.log(stockMarketIndex);

        verify(reportLogRepository, times(1)).save(any());
    }

    @Test
    public void shouldNotSaveWhenStockIndexParserReturnEmptyOptional() throws IOException {
        StockMarketIndex stockMarketIndex = mock(StockMarketIndex.class);
        appProps.stockMarketIndexUrl = "url";
        when(stockMarketIndex.getCode()).thenReturn("Code");
        when(restService.doGet(anyString())).thenReturn("Ok");
        when(stockMarketIndexParser.parse(anyString())).thenReturn(Optional.empty());
        StockMarketIndexLogger stockMarketIndexLogger = new StockMarketIndexLogger(appProps, restService, stockMarketIndexParser, reportLogRepository);

        stockMarketIndexLogger.log(stockMarketIndex);

        verify(reportLogRepository, times(0)).save(any());
    }
}
