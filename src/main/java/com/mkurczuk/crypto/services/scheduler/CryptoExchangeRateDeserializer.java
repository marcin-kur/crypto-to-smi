package com.mkurczuk.crypto.services.scheduler;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

class CryptoExchangeRateDeserializer extends StdDeserializer<CryptoExchangeRate> {

    protected CryptoExchangeRateDeserializer() {
        this(null);
    }

    protected CryptoExchangeRateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public CryptoExchangeRate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        JsonNode ticker = node.get("ticker");
        String base = ticker.get("base").asText();
        String target = ticker.get("target").asText();
        BigDecimal price = new BigDecimal(ticker.get("price").asText());
        return new CryptoExchangeRate(base, target, price);
    }
}
