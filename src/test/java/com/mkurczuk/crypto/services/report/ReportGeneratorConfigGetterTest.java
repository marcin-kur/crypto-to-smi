package com.mkurczuk.crypto.services.report;

import com.mkurczuk.crypto.model.Cryptocurrency;
import com.mkurczuk.crypto.model.StockMarketIndex;
import com.mkurczuk.crypto.repositories.CryptocurrencyRepository;
import com.mkurczuk.crypto.repositories.StockMarketIndexRepository;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReportGeneratorConfigGetterTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private CryptocurrencyRepository cryptocurrencyRepository;

    @Mock
    private StockMarketIndexRepository stockMarketIndexRepository;

    @Mock
    private LocalDateParser localDateParser;

    @Test
    public void shouldGetFailedDueToStartDateIsEmpty() {
        when(cryptocurrencyRepository.findCryptocurrencyByName(anyString())).thenReturn(Optional.ofNullable(mock(Cryptocurrency.class)));
        when(stockMarketIndexRepository.findStockMarketIndexByName(anyString())).thenReturn(Optional.ofNullable(mock(StockMarketIndex.class)));
        when(localDateParser.parse(anyString())).thenReturn(LocalDate.now());
        ReportGeneratorConfigGetter reportGeneratorConfigGetter = new ReportGeneratorConfigGetter(
                cryptocurrencyRepository,
                stockMarketIndexRepository,
                localDateParser);

        Throwable thrown = catchThrowable(() ->
                reportGeneratorConfigGetter.get(
                        Optional.empty(),
                        Optional.of("Test"),
                        Optional.of("Test"),
                        Optional.of("Test"),
                        Optional.of(1)
                ));

        assertThat(thrown)
                .isInstanceOf(ReportGeneratorConfigException.class)
                .hasMessageContaining("Start Date");
    }

    @Test
    public void shouldGetFailedDueToStartDateParsingFailed() {
        when(cryptocurrencyRepository.findCryptocurrencyByName(anyString())).thenReturn(Optional.ofNullable(mock(Cryptocurrency.class)));
        when(stockMarketIndexRepository.findStockMarketIndexByName(anyString())).thenReturn(Optional.ofNullable(mock(StockMarketIndex.class)));
        when(localDateParser.parse("Start Date")).thenThrow(new RuntimeException());
        ReportGeneratorConfigGetter reportGeneratorConfigGetter = new ReportGeneratorConfigGetter(
                cryptocurrencyRepository,
                stockMarketIndexRepository,
                localDateParser);

        Throwable thrown = catchThrowable(() ->
                reportGeneratorConfigGetter.get(
                        Optional.of("Start Date"),
                        Optional.of("End Date"),
                        Optional.of("Test"),
                        Optional.of("Test"),
                        Optional.of(1)
                ));

        assertThat(thrown)
                .isInstanceOf(ReportGeneratorConfigException.class)
                .hasMessageContaining("Start Date");
    }

    @Test
    public void shouldGetFailedDueToEndDateIsEmpty() {
        when(cryptocurrencyRepository.findCryptocurrencyByName(anyString())).thenReturn(Optional.ofNullable(mock(Cryptocurrency.class)));
        when(stockMarketIndexRepository.findStockMarketIndexByName(anyString())).thenReturn(Optional.ofNullable(mock(StockMarketIndex.class)));
        when(localDateParser.parse(anyString())).thenReturn(LocalDate.now());
        ReportGeneratorConfigGetter reportGeneratorConfigGetter = new ReportGeneratorConfigGetter(
                cryptocurrencyRepository,
                stockMarketIndexRepository,
                localDateParser);

        Throwable thrown = catchThrowable(() ->
                reportGeneratorConfigGetter.get(
                        Optional.of("Test"),
                        Optional.empty(),
                        Optional.of("Test"),
                        Optional.of("Test"),
                        Optional.of(1)
                ));

        assertThat(thrown)
                .isInstanceOf(ReportGeneratorConfigException.class)
                .hasMessageContaining("End Date");
    }

    @Test
    public void shouldGetFailedDueToEndDateParsingFailed() {
        when(cryptocurrencyRepository.findCryptocurrencyByName(anyString())).thenReturn(Optional.ofNullable(mock(Cryptocurrency.class)));
        when(stockMarketIndexRepository.findStockMarketIndexByName(anyString())).thenReturn(Optional.ofNullable(mock(StockMarketIndex.class)));
        when(localDateParser.parse("Start Date")).thenReturn(LocalDate.now());
        when(localDateParser.parse("End Date")).thenThrow(new RuntimeException());
        ReportGeneratorConfigGetter reportGeneratorConfigGetter = new ReportGeneratorConfigGetter(
                cryptocurrencyRepository,
                stockMarketIndexRepository,
                localDateParser);

        Throwable thrown = catchThrowable(() ->
                reportGeneratorConfigGetter.get(
                        Optional.of("Start Date"),
                        Optional.of("End Date"),
                        Optional.of("Test"),
                        Optional.of("Test"),
                        Optional.of(1)
                ));

        assertThat(thrown)
                .isInstanceOf(ReportGeneratorConfigException.class)
                .hasMessageContaining("End Date");
    }

    @Test
    public void shouldGetFailedDueToCryptoIsEmpty() {
        when(cryptocurrencyRepository.findCryptocurrencyByName(anyString())).thenReturn(Optional.ofNullable(mock(Cryptocurrency.class)));
        when(stockMarketIndexRepository.findStockMarketIndexByName(anyString())).thenReturn(Optional.ofNullable(mock(StockMarketIndex.class)));
        when(localDateParser.parse(anyString())).thenReturn(LocalDate.now());
        ReportGeneratorConfigGetter reportGeneratorConfigGetter = new ReportGeneratorConfigGetter(
                cryptocurrencyRepository,
                stockMarketIndexRepository,
                localDateParser);

        Throwable thrown = catchThrowable(() ->
                reportGeneratorConfigGetter.get(
                        Optional.of("Test"),
                        Optional.of("Test"),
                        Optional.empty(),
                        Optional.of("Test"),
                        Optional.of(1)
                ));

        assertThat(thrown)
                .isInstanceOf(ReportGeneratorConfigException.class)
                .hasMessageContaining("Cryptocurrency");
    }

    @Test
    public void shouldGetFailedDueToCryptoNotExists() {
        when(cryptocurrencyRepository.findCryptocurrencyByName(anyString())).thenReturn(Optional.empty());
        when(stockMarketIndexRepository.findStockMarketIndexByName(anyString())).thenReturn(Optional.ofNullable(mock(StockMarketIndex.class)));
        when(localDateParser.parse(anyString())).thenReturn(LocalDate.now());
        ReportGeneratorConfigGetter reportGeneratorConfigGetter = new ReportGeneratorConfigGetter(
                cryptocurrencyRepository,
                stockMarketIndexRepository,
                localDateParser);

        Throwable thrown = catchThrowable(() ->
                reportGeneratorConfigGetter.get(
                        Optional.of("Test"),
                        Optional.of("Test"),
                        Optional.empty(),
                        Optional.of("Test"),
                        Optional.of(1)
                ));

        assertThat(thrown)
                .isInstanceOf(ReportGeneratorConfigException.class)
                .hasMessageContaining("Cryptocurrency");
    }

    @Test
    public void shouldGetFailedDueToStockIndexIsEmpty() {
        when(cryptocurrencyRepository.findCryptocurrencyByName(anyString())).thenReturn(Optional.ofNullable(mock(Cryptocurrency.class)));
        when(stockMarketIndexRepository.findStockMarketIndexByName(anyString())).thenReturn(Optional.ofNullable(mock(StockMarketIndex.class)));
        when(localDateParser.parse(anyString())).thenReturn(LocalDate.now());
        ReportGeneratorConfigGetter reportGeneratorConfigGetter = new ReportGeneratorConfigGetter(
                cryptocurrencyRepository,
                stockMarketIndexRepository,
                localDateParser);

        Throwable thrown = catchThrowable(() ->
                reportGeneratorConfigGetter.get(
                        Optional.of("Test"),
                        Optional.of("Test"),
                        Optional.of("Test"),
                        Optional.empty(),
                        Optional.of(1)
                ));

        assertThat(thrown)
                .isInstanceOf(ReportGeneratorConfigException.class)
                .hasMessageContaining("Stock Market Index");
    }

    @Test
    public void shouldGetFailedDueToStockIndexNotExists() {
        when(cryptocurrencyRepository.findCryptocurrencyByName(anyString())).thenReturn(Optional.ofNullable(mock(Cryptocurrency.class)));
        when(stockMarketIndexRepository.findStockMarketIndexByName(anyString())).thenReturn(Optional.empty());
        when(localDateParser.parse(anyString())).thenReturn(LocalDate.now());
        ReportGeneratorConfigGetter reportGeneratorConfigGetter = new ReportGeneratorConfigGetter(
                cryptocurrencyRepository,
                stockMarketIndexRepository,
                localDateParser);

        Throwable thrown = catchThrowable(() ->
                reportGeneratorConfigGetter.get(
                        Optional.of("Test"),
                        Optional.of("Test"),
                        Optional.of("Test"),
                        Optional.empty(),
                        Optional.of(1)
                ));

        assertThat(thrown)
                .isInstanceOf(ReportGeneratorConfigException.class)
                .hasMessageContaining("Stock Market Index");
    }

    @Test
    public void shouldGetFailedDueToIntervalIsNotPopulated() {
        when(cryptocurrencyRepository.findCryptocurrencyByName(anyString())).thenReturn(Optional.ofNullable(mock(Cryptocurrency.class)));
        when(stockMarketIndexRepository.findStockMarketIndexByName(anyString())).thenReturn(Optional.ofNullable(mock(StockMarketIndex.class)));
        when(localDateParser.parse(anyString())).thenReturn(LocalDate.now());
        ReportGeneratorConfigGetter reportGeneratorConfigGetter = new ReportGeneratorConfigGetter(
                cryptocurrencyRepository,
                stockMarketIndexRepository,
                localDateParser);

        Throwable thrown = catchThrowable(() ->
                reportGeneratorConfigGetter.get(
                        Optional.of(String.valueOf(LocalDate.now())),
                        Optional.of(String.valueOf(LocalDate.now())),
                        Optional.of("Test"),
                        Optional.of("Test"),
                        Optional.empty()
                ));

        assertThat(thrown)
                .isInstanceOf(ReportGeneratorConfigException.class)
                .hasMessageContaining("Interval");
    }

    @Test
    public void shouldGetSuccessfully() {
        when(cryptocurrencyRepository.findCryptocurrencyByName(anyString())).thenReturn(Optional.ofNullable(mock(Cryptocurrency.class)));
        when(stockMarketIndexRepository.findStockMarketIndexByName(anyString())).thenReturn(Optional.ofNullable(mock(StockMarketIndex.class)));
        when(localDateParser.parse(anyString())).thenReturn(LocalDate.now());
        ReportGeneratorConfigGetter reportGeneratorConfigGetter = new ReportGeneratorConfigGetter(
                cryptocurrencyRepository,
                stockMarketIndexRepository,
                localDateParser);

        assertThatCode(() ->
                reportGeneratorConfigGetter.get(
                        Optional.of(String.valueOf(LocalDate.now())),
                        Optional.of(String.valueOf(LocalDate.now())),
                        Optional.of("Test"),
                        Optional.of("Test"),
                        Optional.of(1)
                )
        ).doesNotThrowAnyException();
    }
}
