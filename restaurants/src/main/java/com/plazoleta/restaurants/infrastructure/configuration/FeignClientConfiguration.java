package com.plazoleta.restaurants.infrastructure.configuration;

import feign.Logger;
import feign.Request;
import feign.Retryer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class FeignClientConfiguration {

    @Value("${feign.client.timeout:30000}")
    private int timeout;

    @Value("${feign.client.retry.attempts:3}")
    private int retryAttempts;

    @Bean
    public Request.Options feignRequestOptions() {
        return new Request.Options(
                10, TimeUnit.SECONDS,
                timeout, TimeUnit.MILLISECONDS,
                true
        );
    }

    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(
                1000,
                3000,
                retryAttempts
        );
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }

    @Configuration
    public static class MessagingServiceConfig {

        @Value("${messaging.service.timeout:30000}")
        private int messagingTimeout;

        @Bean
        public Request.Options messagingOptions() {
            return new Request.Options(
                    10, TimeUnit.SECONDS,
                    messagingTimeout, TimeUnit.MILLISECONDS,
                    true
            );
        }
    }

    @Configuration
    public static class TraceabilityServiceConfig {

        @Value("${traceability.service.timeout:30000}")
        private int traceabilityTimeout;

        @Bean
        public Request.Options traceabilityOptions() {
            return new Request.Options(
                    10, TimeUnit.SECONDS,
                    traceabilityTimeout, TimeUnit.MILLISECONDS,
                    true
            );
        }
    }

    @Configuration
    public static class UsersServiceConfig {

        @Value("${users.service.timeout:30000}")
        private int usersTimeout;

        @Bean
        public Request.Options usersOptions() {
            return new Request.Options(
                    10, TimeUnit.SECONDS,
                    usersTimeout, TimeUnit.MILLISECONDS,
                    true
            );
        }
    }
}