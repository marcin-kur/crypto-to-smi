package com.mkurczuk.crypto.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProps {
    @Value("${url.crypto}")
    public String cryptoUrl;
    @Value("${url.currency}")
    public String currencyUrl;
    @Value("${url.stockMarketIndex}")
    public String stockMarketIndexUrl;
    @Value("${exchange.currency}")
    public String exchangeCurrency;

}
