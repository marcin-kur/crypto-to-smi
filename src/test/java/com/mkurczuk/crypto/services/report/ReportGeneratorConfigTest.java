package com.mkurczuk.crypto.services.report;

import com.mkurczuk.crypto.model.Cryptocurrency;
import com.mkurczuk.crypto.model.StockMarketIndex;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;

public class ReportGeneratorConfigTest {

    @Test
    public void shouldCreateSuccess() {
        assertThatCode(() ->
                new ReportGeneratorConfig(
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        mock(Cryptocurrency.class),
                        mock(StockMarketIndex.class),
                        1
                )
        ).doesNotThrowAnyException();
    }

    @Test
    public void shouldDatesValidationFailed() {
        Throwable thrown = catchThrowable(() ->
                new ReportGeneratorConfig(
                        LocalDateTime.now(),
                        LocalDateTime.now().plusDays(-1),
                        mock(Cryptocurrency.class),
                        mock(StockMarketIndex.class),
                        1
                ));

        assertThat(thrown).isInstanceOf(ReportGeneratorConfigException.class);
    }
}
