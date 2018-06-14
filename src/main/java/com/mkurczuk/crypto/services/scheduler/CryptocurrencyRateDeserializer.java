package com.mkurczuk.crypto.services.scheduler;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

class CryptocurrencyRateDeserializer extends StdDeserializer<CryptocurrencyRate> {

    protected CryptocurrencyRateDeserializer() {
        this(null);
    }

    protected CryptocurrencyRateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public CryptocurrencyRate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        JsonNode ticker = node.get("ticker");
        String base = ticker.get("base").asText();
        String target = ticker.get("target").asText();
        BigDecimal price = new BigDecimal(ticker.get("price").asText());
        return new CryptocurrencyRate(base, target, price);
    }
}
