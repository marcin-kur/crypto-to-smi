package com.mkurczuk.crypto.services.report;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ReportDTOFactory {
    private static final String EMPTY_VALUE = "-";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public List<ReportDTO> createDTO(List<Report> reports) {
        return reports.stream()
                .map(this::createDTO)
                .collect(Collectors.toList());
    }

    private ReportDTO createDTO(Report report) {
        return new ReportDTO(
                parseLocalDateTime(report.getStartDateTime()),
                parseLocalDateTime(report.getEndDateTime()),
                parseBigDecimal(report.getExchangeRate()),
                parseBigDecimal(report.getCryptocurrencyAverage()),
                parseBigDecimal(report.getStockIndexAverage()),
                parseBigDecimal(report.getCalculatedValue())
        );
    }

    private String parseBigDecimal(Optional<BigDecimal> bigDecimalOptional) {
        if (bigDecimalOptional.isPresent()) {
            return bigDecimalOptional.get()
                    .setScale(5, RoundingMode.HALF_UP)
                    .toPlainString();
        }
        return EMPTY_VALUE;
    }

    private String parseLocalDateTime(LocalDateTime localDateTime) {
        if (localDateTime != null) {
            return DATE_TIME_FORMATTER.format(localDateTime);
        }
        log.error("Local Date Time is null");
        return EMPTY_VALUE;
    }


}
