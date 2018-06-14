package com.mkurczuk.crypto.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class RestService {
    private final RestTemplate restTemplate;

    public String doGet(String url) throws RestException {
        log.info("Url: " + url);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }
        throw new RestException(responseEntity.getStatusCode(), responseEntity.getBody());
    }
}
