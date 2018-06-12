package com.mkurczuk.crypto.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UrlProps {
    @Value("${url.crypto}")
    String crypto;
    @Value("${url.currency}")
    String currency;
    @Value("${url.stockMarketIndex}")
    String stockMarketIndex;
}
