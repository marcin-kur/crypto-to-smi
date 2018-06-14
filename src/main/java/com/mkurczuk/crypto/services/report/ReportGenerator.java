package com.mkurczuk.crypto.services.report;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mkurczuk.crypto.model.ReportLog;
import com.mkurczuk.crypto.repositories.ReportLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReportGenerator {
    private final ReportLogRepository reportLogRepository;
    private final CurrencyExchangeService currencyExchangeService;

    public List<Report> generateReports(ReportGeneratorConfig reportGeneratorConfig) {
        List<CurrencyExchangeRate> currencyExchangeRates = currencyExchangeService.getCurrencyExchangeRates(
                reportGeneratorConfig.getStartDateTime(),
                reportGeneratorConfig.getEndDateTime());

        Stream<Range<LocalDateTime>> intervalStream = getIntervalStream(
                reportGeneratorConfig.getStartDateTime(),
                reportGeneratorConfig.getEndDateTime(),
                reportGeneratorConfig.getInterval());

        List<ReportLog> reportLogs = reportLogRepository.findReportLogsByCryptocurrencyIsOrIndexDictionaryIsAndDateTimeBetween(
                reportGeneratorConfig.getCryptocurrency(),
                reportGeneratorConfig.getStockMarketIndex(),
                reportGeneratorConfig.getStartDateTime(),
                reportGeneratorConfig.getEndDateTime());

        return intervalStream
                .map(getReport(reportLogs, currencyExchangeRates))
                .collect(Collectors.toList());
    }

    private Function<Range<LocalDateTime>, Report> getReport(List<ReportLog> reportLogs, List<CurrencyExchangeRate> currencyExchangeRates) {
        return interval -> {
            Stream<ReportLog> cryptocurrencyReportLogs = reportLogs.stream()
                    .filter(reportLog -> Optional.ofNullable(reportLog.getCryptocurrency()).isPresent() && isIntervalBetween(interval, reportLog));
            Stream<ReportLog> stockIndexReportLogs = reportLogs.stream()
                    .filter(reportLog -> Optional.ofNullable(reportLog.getIndexDictionary()).isPresent() && isIntervalBetween(interval, reportLog));
            Optional<BigDecimal> cryptocurrencyAverage = getAverage(cryptocurrencyReportLogs);
            Optional<BigDecimal> stockIndexAverage = getAverage(stockIndexReportLogs);
            Optional<BigDecimal> currencyExchangeRate = getCurrencyExchangeRate(interval.getStart(), currencyExchangeRates);

            return new Report(
                    interval.getStart(),
                    interval.getEnd(),
                    currencyExchangeRate,
                    cryptocurrencyAverage,
                    stockIndexAverage,
                    calculateFinalValue(cryptocurrencyAverage, stockIndexAverage, currencyExchangeRate)
            );
        };
    }

    private Optional<BigDecimal> getCurrencyExchangeRate(LocalDateTime localDateTime, List<CurrencyExchangeRate> currencyExchangeRates) {
        ChronoLocalDate chronoLocalDate = ChronoLocalDate.from(localDateTime);
        for (CurrencyExchangeRate currencyExchangeRate : currencyExchangeRates) {
            if (currencyExchangeRate.getDate().compareTo(chronoLocalDate) == 0) {
                return Optional.ofNullable(currencyExchangeRate.getPrice());
            }
        }
        return Optional.empty();
    }

    private Optional<BigDecimal> calculateFinalValue(Optional<BigDecimal> cryptocurrencyAverage, Optional<BigDecimal> stockIndexAverage, Optional<BigDecimal> currencyExchangeRate) {
        if (cryptocurrencyAverage.isPresent() && stockIndexAverage.isPresent() && currencyExchangeRate.isPresent()) {
            return Optional.of(
                    cryptocurrencyAverage.get()
                            .multiply(currencyExchangeRate.get())
                            .divide(stockIndexAverage.get(), 5, RoundingMode.HALF_UP));
        }
        return Optional.empty();
    }

    private Optional<BigDecimal> getAverage(Stream<ReportLog> reportLogStream) {
        List<BigDecimal> bigDecimals = reportLogStream.map(ReportLog::getValue).collect(Collectors.toList());

        if (bigDecimals.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(
                bigDecimals.stream()
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .divide(BigDecimal.valueOf(bigDecimals.size()), 5, RoundingMode.HALF_UP)
        );
    }

    Stream<Range<LocalDateTime>> getIntervalStream(LocalDateTime startDateTime, LocalDateTime endDateTime, int interval) {
        return Stream
                .iterate(startDateTime, dateTime -> dateTime.plusHours(interval))
                .limit((ChronoUnit.HOURS.between(startDateTime, endDateTime) / interval) + 1)
                .map(dateTime -> new Range<>(dateTime, dateTime.plusHours(interval)));
    }

    private boolean isIntervalBetween(Range<LocalDateTime> interval, ReportLog reportLog) {
        return reportLog.getDateTime().isAfter(interval.getStart()) && reportLog.getDateTime().isBefore(interval.getEnd());
    }
}