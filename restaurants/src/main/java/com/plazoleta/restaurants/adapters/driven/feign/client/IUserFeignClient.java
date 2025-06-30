package com.plazoleta.restaurants.adapters.driven.feign.client;

import com.plazoleta.restaurants.adapters.driven.feign.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "users-service", url = "http://localhost:8080")
public interface IUserFeignClient {

    @GetMapping("/usuarios/{id}")
    UserResponse getUserById(@PathVariable("id") Long id);
}