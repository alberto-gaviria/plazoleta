package com.plazoleta.users.adapters.driving.http.controller;

import com.plazoleta.users.adapters.driving.http.dto.request.AddUserRequest;
import com.plazoleta.users.adapters.driving.http.mapper.IUserRequestMapper;
import com.plazoleta.users.domain.api.IAdminUserManagementServicePort;
import com.plazoleta.users.domain.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Administración de Usuarios", description = "API para administración de usuarios por parte del administrador")
public class AdminUserManagementRestControllerAdapter {

    private final IAdminUserManagementServicePort usuarioServicePort;
    private final IUserRequestMapper usuarioRequestMapper;

    public AdminUserManagementRestControllerAdapter(IAdminUserManagementServicePort usuarioServicePort,
                                                    IUserRequestMapper usuarioRequestMapper) {
        this.usuarioServicePort = usuarioServicePort;
        this.usuarioRequestMapper = usuarioRequestMapper;
    }

    @Operation(summary = "Crear un nuevo propietario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Propietario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "403", description = "No autorizado - Solo administradores pueden crear propietarios"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/propietario")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Void> createPropietario(@Valid @RequestBody AddUserRequest request) {
        User user = usuarioRequestMapper.addRequestToUsuario(request);
        usuarioServicePort.savePropietario(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}