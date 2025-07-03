package com.plazoleta.users.adapters.driving.http.controller;

import com.plazoleta.users.adapters.driving.http.dto.request.AddUserRequest;
import com.plazoleta.users.adapters.driving.http.dto.response.UserResponse;
import com.plazoleta.users.adapters.driving.http.mapper.IUserRequestMapper;
import com.plazoleta.users.adapters.driving.http.mapper.IUserResponseMapper;
import com.plazoleta.users.domain.api.IClientUserManagementServicePort;
import com.plazoleta.users.domain.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Autoregistro de Clientes", description = "API para que los clientes se registren en el sistema")
public class ClientUserManagementRestControllerAdapter {

    private final IClientUserManagementServicePort clientUserManagementServicePort;
    private final IUserRequestMapper userRequestMapper;
    private final IUserResponseMapper userResponseMapper;

    public ClientUserManagementRestControllerAdapter(IClientUserManagementServicePort clientUserManagementServicePort,
                                                     IUserRequestMapper userRequestMapper,
                                                     IUserResponseMapper userResponseMapper) {
        this.clientUserManagementServicePort = clientUserManagementServicePort;
        this.userRequestMapper = userRequestMapper;
        this.userResponseMapper = userResponseMapper;
    }

    @Operation(summary = "Crear cuenta de cliente (autoregistro)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/cliente")
    public ResponseEntity<UserResponse> createCliente(@Valid @RequestBody AddUserRequest request) {
        User user = userRequestMapper.addRequestToUsuario(request);
        User clienteCreado = clientUserManagementServicePort.saveCliente(user);
        UserResponse response = userResponseMapper.userToDto(clienteCreado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}