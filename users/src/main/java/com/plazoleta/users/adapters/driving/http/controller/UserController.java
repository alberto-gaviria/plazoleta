package com.plazoleta.users.adapters.driving.http.controller;

import com.plazoleta.users.adapters.driving.http.dto.response.UserResponse;
import com.plazoleta.users.adapters.driving.http.mapper.IUserResponseMapper;
import com.plazoleta.users.domain.api.IUserQueryServicePort;
import com.plazoleta.users.domain.model.User;
import com.plazoleta.users.adapters.driving.http.util.HttpConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(HttpConstants.Paths.USUARIOS)
@Tag(name = "Consulta de Usuarios", description = "API para consulta de información de usuarios")
public class UserController {

    private final IUserQueryServicePort userQueryServicePort;
    private final IUserResponseMapper userResponseMapper;

    public UserController(IUserQueryServicePort userQueryServicePort,
                          IUserResponseMapper userResponseMapper) {
        this.userQueryServicePort = userQueryServicePort;
        this.userResponseMapper = userResponseMapper;
    }

    @Operation(summary = "Obtener usuario por ID",
            description = "Permite obtener la información de un usuario específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping(HttpConstants.Paths.BY_ID)
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = userQueryServicePort.getUserById(id);
        UserResponse userResponse = userResponseMapper.userToDto(user);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(summary = "Obtener información del usuario autenticado",
            description = "Permite obtener la información del usuario actualmente autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    @GetMapping(HttpConstants.Paths.ME)
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        User user = userQueryServicePort.getUserById(userId);
        UserResponse userResponse = userResponseMapper.userToDto(user);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(summary = "Obtener email del usuario",
            description = "Permite obtener el email de un usuario específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}/email")
    public ResponseEntity<String> getUserEmail(@PathVariable Long id) {
        User user = userQueryServicePort.getUserById(id);
        return ResponseEntity.ok(user.getCorreo());
    }
}