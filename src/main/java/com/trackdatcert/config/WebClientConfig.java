package com.trackdatcert.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.util.concurrent.TimeUnit;
import lombok.experimental.UtilityClass;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@UtilityClass
public class WebClientConfig {

    private static final int TIMEOUT_IN_MS = 10000;

    public WebClient createWebClient(String url) {
        return WebClient.builder()
            .clientConnector(webClientTimeoutConnector())
            .baseUrl(url)
            .build();
    }

    private ClientHttpConnector webClientTimeoutConnector() {
        HttpClient client = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT_IN_MS)
            .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(TIMEOUT_IN_MS, TimeUnit.MILLISECONDS))
                .addHandlerLast(new WriteTimeoutHandler(TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)));

        return new ReactorClientHttpConnector(client.wiretap(true));
    }
}
