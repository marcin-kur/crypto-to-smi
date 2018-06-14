package com.mkurczuk.crypto.services.report;

import com.mkurczuk.crypto.repositories.ReportLogRepository;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

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

//    @Test
//    public void shouldGetIntervalStream() {
//        ReportGenerator reportGenerator = new ReportGenerator(reportLogRepository, currencyExchangeService);
//
//        Stream<Range<LocalDateTime>> intervalStream = reportGenerator.getIntervalStream(
//                LocalDate.now().atStartOfDay(),
//                LocalDate.now().atTime(23, 59, 59, 9999),
//                1
//        );
//
//        assertThat(intervalStream).hasSize(24).doesNotContainNull();
//    }
}
