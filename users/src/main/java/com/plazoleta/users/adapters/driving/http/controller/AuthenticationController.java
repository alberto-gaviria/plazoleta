package com.plazoleta.users.adapters.driving.http.controller;

import com.plazoleta.users.adapters.driving.http.dto.request.LoginRequest;
import com.plazoleta.users.adapters.driving.http.dto.response.AuthResponse;
import com.plazoleta.users.adapters.driving.http.mapper.IAuthResponseMapper;
import com.plazoleta.users.domain.api.IAuthenticationServicePort;
import com.plazoleta.users.domain.model.Authentication;
import com.plazoleta.users.adapters.driving.http.util.HttpConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(HttpConstants.Paths.AUTH)
@Tag(name = "Autenticación", description = "API para autenticación de usuarios")
public class AuthenticationController {

    private final IAuthenticationServicePort authenticationServicePort;
    private final IAuthResponseMapper authResponseMapper;

    public AuthenticationController(IAuthenticationServicePort authenticationServicePort,
                                    IAuthResponseMapper authResponseMapper) {
        this.authenticationServicePort = authenticationServicePort;
        this.authResponseMapper = authResponseMapper;
    }

    @Operation(summary = "Iniciar sesión",
            description = "Permite a un usuario autenticarse en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping(HttpConstants.Paths.LOGIN)
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationServicePort.authenticate(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        AuthResponse response = authResponseMapper.authToResponse(authentication);
        return ResponseEntity.ok(response);
    }
}