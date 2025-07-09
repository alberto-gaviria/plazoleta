package com.plazoleta.restaurants.adapters.driven.messaging.client;

import feign.Logger;
import feign.Request;
import feign.Retryer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class MessagingServiceClientConfig {

    @Value("${messaging.service.timeout:30000}")
    private int timeout;

    @Value("${messaging.service.retry.attempts:3}")
    private int retryAttempts;

    @Bean
    public Request.Options options() {
        return new Request.Options(
                10, TimeUnit.SECONDS,  // connect timeout
                timeout, TimeUnit.MILLISECONDS,  // read timeout
                true  // follow redirects
        );
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(
                1000,  // period between retries
                3000,  // max period between retries
                retryAttempts  // max attempts
        );
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }
}