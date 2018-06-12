package com.mkurczuk.crypto.services;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Optional;

@Slf4j
@Component
public class StockMarketIndexParser {

    private static final String VALUE_HTML_CLASS = "summary";
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.#");

    Optional<BigDecimal> parse(String htmlDocument) {
        Document parsedDocument = Jsoup.parse(htmlDocument);
        Elements valueElement = parsedDocument.getElementsByClass(VALUE_HTML_CLASS);
        String value = removeWhiteCharacters(valueElement.html());
        try {
            Double doubleValue = (Double) DECIMAL_FORMAT.parse(value);
            return Optional.of(new BigDecimal(doubleValue));
        } catch (ParseException e) {
            log.error("Could not parse htmlDocument.", e.getMessage());
            return Optional.empty();
        }
    }

    private String removeWhiteCharacters(String value) {
        return value.replaceAll("&nbsp;", "");
    }
}
