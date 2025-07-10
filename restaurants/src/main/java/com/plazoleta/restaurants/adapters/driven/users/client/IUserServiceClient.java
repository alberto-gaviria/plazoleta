package com.plazoleta.restaurants.adapters.driven.users.client;

import com.plazoleta.restaurants.infrastructure.configuration.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "users-service",
        url = "${users.service.url}",
        configuration = FeignClientConfiguration.UsersServiceConfig.class
)
public interface IUserServiceClient {

    @GetMapping("/usuarios/{id}/email")
    String getUserEmail(@PathVariable("id") Long id);

    @GetMapping("/usuarios/{id}/telefono")
    String getUserPhone(@PathVariable("id") Long id);
}