package com.mkurczuk.crypto.services.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mkurczuk.crypto.model.api.CurrencyExchangeRate;

import java.io.IOException;
import java.math.BigDecimal;

public class CurrencyExchangeRateDeserializer extends StdDeserializer<CurrencyExchangeRate> {
    protected CurrencyExchangeRateDeserializer() {
        this(null);
    }

    protected CurrencyExchangeRateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public CurrencyExchangeRate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        String code = node.get("code").asText();
        JsonNode rates = node.get("rates").get(0);
        BigDecimal price = new BigDecimal(rates.get("bid").asText());
        return new CurrencyExchangeRate(code, price);
    }
}
