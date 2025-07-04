package com.plazoleta.users.adapters.driving.http.controller;

import com.plazoleta.users.adapters.driving.http.dto.request.AddUserRequest;
import com.plazoleta.users.adapters.driving.http.dto.response.UserResponse;
import com.plazoleta.users.adapters.driving.http.mapper.IUserRequestMapper;
import com.plazoleta.users.adapters.driving.http.mapper.IUserResponseMapper;
import com.plazoleta.users.domain.api.IOwnerUserManagementServicePort;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(HttpConstants.Paths.USUARIOS)
@Tag(name = "Gestión de Usuarios por Propietario", description = "API para gestión de empleados por parte del propietario")
public class OwnerUserManagementRestControllerAdapter {

    private final IOwnerUserManagementServicePort ownerUserManagementServicePort;
    private final IUserRequestMapper userRequestMapper;
    private final IUserResponseMapper userResponseMapper;

    public OwnerUserManagementRestControllerAdapter(IOwnerUserManagementServicePort ownerUserManagementServicePort,
                                                    IUserRequestMapper userRequestMapper,
                                                    IUserResponseMapper userResponseMapper){
        this.ownerUserManagementServicePort = ownerUserManagementServicePort;
        this.userRequestMapper = userRequestMapper;
        this.userResponseMapper = userResponseMapper;
    }

    @Operation(summary = "Crear un nuevo empleado",
            description = "Permite a un propietario crear un nuevo empleado en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empleado creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "403", description = "Solo propietarios pueden crear empleados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping(HttpConstants.Paths.SUB_EMPLEADO)
    @PreAuthorize("hasAuthority(T(com.plazoleta.users.adapters.driving.http.util.HttpConstants$Roles).PROPIETARIO)")
    public ResponseEntity<UserResponse> createEmpleado(@Valid @RequestBody AddUserRequest request,
                                                       Authentication authentication) {
        Long propietarioId = Long.valueOf(authentication.getName());
        User user = userRequestMapper.addRequestToUsuario(request);
        User empleadoCreado = ownerUserManagementServicePort.saveEmpleado(user, propietarioId);
        UserResponse response = userResponseMapper.userToDto(empleadoCreado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}