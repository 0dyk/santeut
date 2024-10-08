package com.santeut.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    public CustomFilter(){
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain)  -> {
            log.info("Custom Pre Filter");

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("Custom POST Filter");
            }));
        };
    }

    public static class Config{
    }
}
