package com.plazoleta.users.adapters.driving.http.controller;

import com.plazoleta.users.adapters.driving.http.dto.request.AddUserRequest;
import com.plazoleta.users.adapters.driving.http.dto.response.UserResponse;
import com.plazoleta.users.adapters.driving.http.mapper.IUserRequestMapper;
import com.plazoleta.users.adapters.driving.http.mapper.IUserResponseMapper;
import com.plazoleta.users.domain.api.IAdminUserManagementServicePort;
import com.plazoleta.users.domain.model.User;
import com.plazoleta.users.adapters.driving.http.util.HttpConstants;
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
@RequestMapping(HttpConstants.Paths.USUARIOS)
@Tag(name = "Administración de Usuarios", description = "API para administración de usuarios por parte del administrador")
public class AdminUserManagementRestControllerAdapter {

    private final IAdminUserManagementServicePort usuarioServicePort;
    private final IUserRequestMapper usuarioRequestMapper;
    private final IUserResponseMapper userResponseMapper;

    public AdminUserManagementRestControllerAdapter(IAdminUserManagementServicePort usuarioServicePort,
                                                    IUserRequestMapper usuarioRequestMapper,
                                                    IUserResponseMapper userResponseMapper) {
        this.usuarioServicePort = usuarioServicePort;
        this.usuarioRequestMapper = usuarioRequestMapper;
        this.userResponseMapper = userResponseMapper;
    }

    @Operation(summary = "Crear un nuevo propietario",
            description = "Permite a un administrador crear un nuevo propietario en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Propietario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "403", description = "Solo administradores pueden crear propietarios"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping(HttpConstants.Paths.SUB_PROPIETARIO)
    @PreAuthorize("hasAuthority(T(com.plazoleta.users.adapters.driving.http.util.HttpConstants$Roles).ADMINISTRADOR)")
    public ResponseEntity<UserResponse> createPropietario(@Valid @RequestBody AddUserRequest request) {
        User user = usuarioRequestMapper.addRequestToUsuario(request);
        User propietarioCreado = usuarioServicePort.savePropietario(user);
        UserResponse response = userResponseMapper.userToDto(propietarioCreado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}