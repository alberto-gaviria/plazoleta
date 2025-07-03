package com.plazoleta.users.adapters.driving.http.controller;

import com.plazoleta.users.adapters.driving.http.dto.response.UserResponse;
import com.plazoleta.users.adapters.driving.http.mapper.IUserResponseMapper;
import com.plazoleta.users.domain.api.IUserQueryServicePort;
import com.plazoleta.users.domain.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Consulta de Usuarios", description = "API para consulta de información de usuarios")
public class UserController {

    private final IUserQueryServicePort userQueryServicePort;
    private final IUserResponseMapper userResponseMapper;

    public UserController(IUserQueryServicePort userQueryServicePort,
                          IUserResponseMapper userResponseMapper) {
        this.userQueryServicePort = userQueryServicePort;
        this.userResponseMapper = userResponseMapper;
    }

    @Operation(summary = "Obtener usuario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = userQueryServicePort.getUserById(id);
        UserResponse userResponse = userResponseMapper.userToDto(user);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(summary = "Obtener información del usuario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        User user = userQueryServicePort.getUserById(userId);
        UserResponse userResponse = userResponseMapper.userToDto(user);
        return ResponseEntity.ok(userResponse);
    }
}