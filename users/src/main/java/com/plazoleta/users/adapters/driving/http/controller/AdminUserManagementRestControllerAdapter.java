package com.plazoleta.users.adapters.driving.http.controller;

import com.plazoleta.users.adapters.driving.http.dto.request.AddUserRequest;
import com.plazoleta.users.adapters.driving.http.mapper.IUserRequestMapper;
import com.plazoleta.users.domain.api.IAdminUserManagementServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class AdminUserManagementRestControllerAdapter {

    private final IAdminUserManagementServicePort usuarioServicePort;
    private final IUserRequestMapper usuarioRequestMapper;

    public AdminUserManagementRestControllerAdapter(IAdminUserManagementServicePort usuarioServicePort,
                                                    IUserRequestMapper usuarioRequestMapper) {
        this.usuarioServicePort = usuarioServicePort;
        this.usuarioRequestMapper = usuarioRequestMapper;
    }

    @Operation(summary = "Crear un nuevo propietario")
    @ApiResponse(responseCode = "201", description = "Propietario creado con éxito")
    @PostMapping("/propietario")
    public ResponseEntity<Void> addPropietario(@Valid @RequestBody AddUserRequest request) {
        usuarioServicePort.savePropietario(usuarioRequestMapper.addRequestToUsuario(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}