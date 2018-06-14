package com.mkurczuk.crypto.services.report;

import com.mkurczuk.crypto.model.Cryptocurrency;
import com.mkurczuk.crypto.model.ReportLog;
import com.mkurczuk.crypto.model.StockMarketIndex;
import com.mkurczuk.crypto.repositories.ReportLogRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReportGeneratorTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private ReportLogRepository reportLogRepository;

    @Mock
    private CurrencyExchangeService currencyExchangeService;

    @Mock
    private ReportGeneratorConfig reportGeneratorConfig;

    @Test
    public void shouldGetIntervalStream() {
        ReportGenerator reportGenerator = new ReportGenerator(reportLogRepository, currencyExchangeService);

        Stream<Range<LocalDateTime>> intervalStream = reportGenerator.getIntervalStream(
                LocalDate.now().atStartOfDay(),
                LocalDate.now().atTime(23, 59, 59, 9999),
                1
        );

        assertThat(intervalStream).hasSize(24).doesNotContainNull();
    }

    @Test
    public void shouldGenerateReports() {
        LocalDate localDate = LocalDate.of(2018, 6, 13);
        when(reportGeneratorConfig.getStartDateTime()).thenReturn(localDate.atStartOfDay());
        when(reportGeneratorConfig.getEndDateTime()).thenReturn(localDate.atTime(23, 59, 59, 9999));
        when(reportGeneratorConfig.getInterval()).thenReturn(1);
        Cryptocurrency cryptocurrency = mock(Cryptocurrency.class);
        ReportLog cryptoReportLog = mock(ReportLog.class);
        when(cryptoReportLog.getCryptocurrency()).thenReturn(cryptocurrency);
        when(cryptoReportLog.getDateTime()).thenReturn(localDate.atTime(0, 30));
        when(cryptoReportLog.getValue()).thenReturn(BigDecimal.ONE);
        StockMarketIndex stockMarketIndex = mock(StockMarketIndex.class);
        ReportLog indexReportLog = mock(ReportLog.class);
        when(indexReportLog.getIndexDictionary()).thenReturn(stockMarketIndex);
        when(indexReportLog.getDateTime()).thenReturn(localDate.atTime(0, 30));
        when(indexReportLog.getValue()).thenReturn(BigDecimal.ONE);
        List<ReportLog> reportLogList = Arrays.asList(cryptoReportLog, indexReportLog);
        when(reportLogRepository.findReportLogsByCryptocurrencyIsOrIndexDictionaryIsAndDateTimeBetween(any(), any(), any(), any()))
                .thenReturn(reportLogList);
        CurrencyExchangeRate currencyExchangeRate = mock(CurrencyExchangeRate.class);
        when(currencyExchangeRate.getDate()).thenReturn(localDate);
        when(currencyExchangeRate.getPrice()).thenReturn(BigDecimal.ONE);
        when(currencyExchangeService.getCurrencyExchangeRates(any(), any())).thenReturn(Collections.nCopies(1, currencyExchangeRate));
        ReportGenerator reportGenerator = new ReportGenerator(reportLogRepository, currencyExchangeService);

        List<Report> reports = reportGenerator.generateReports(
                reportGeneratorConfig
        );

        assertThat(reports).hasSize(24).doesNotContainNull();
    }
}
