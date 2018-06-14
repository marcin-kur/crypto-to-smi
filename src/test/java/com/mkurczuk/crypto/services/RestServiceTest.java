package com.mkurczuk.crypto.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.anything;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest(RestService.class)
public class RestServiceTest {

    @Autowired
    private RestService restService;

    @Autowired
    private MockRestServiceServer server;

    @Test
    public void shouldDoGetSuccessfully() {
        this.server
                .expect(anything())
                .andRespond(withSuccess("Ok", MediaType.APPLICATION_JSON));

        assertThatCode(() ->
                restService.doGet("url")
        ).doesNotThrowAnyException();
    }

    @Test
    public void shouldThrowExceptionForDoGet() {
        this.server
                .expect(anything())
                .andRespond(withBadRequest());

        Throwable thrown = catchThrowable(() -> restService.doGet("url"));

        assertThat(thrown).isInstanceOf(HttpClientErrorException.class);
    }
}
