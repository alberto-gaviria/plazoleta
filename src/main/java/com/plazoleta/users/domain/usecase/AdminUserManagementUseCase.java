package com.plazoleta.users.domain.usecase;

import com.plazoleta.users.domain.api.IAdminUserManagementServicePort;
import com.plazoleta.users.domain.model.RoleType;
import com.plazoleta.users.domain.model.User;
import com.plazoleta.users.domain.spi.IUserPersistencePort;
import com.plazoleta.users.domain.spi.IPasswordEncoderPort;
import com.plazoleta.users.domain.util.DomainConstants;
import com.plazoleta.users.domain.util.exceptions.InvalidUsuarioException;

import java.time.LocalDate;
import java.time.Period;

public class AdminUserManagementUseCase implements IAdminUserManagementServicePort {

    private final IUserPersistencePort usuarioPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;

    public AdminUserManagementUseCase(IUserPersistencePort usuarioPersistencePort,
                                      IPasswordEncoderPort passwordEncoderPort) {
        this.usuarioPersistencePort = usuarioPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public void savePropietario(User user) {
        validateUsuario(user);
        validateMayorEdad(user.getFechaNacimiento());

        user.setRoleType(RoleType.PROPIETARIO);
        user.setClave(passwordEncoderPort.encode(user.getClave()));

        usuarioPersistencePort.saveUsuario(user);
    }

    private void validateUsuario(User user) {
        if (user == null) {
            throw new InvalidUsuarioException(DomainConstants.Usuario.ERROR_USUARIO_NULO);
        }

        if (user.getNombre() == null || user.getNombre().trim().isEmpty()) {
            throw new InvalidUsuarioException(DomainConstants.Usuario.ERROR_NOMBRE_REQUERIDO);
        }

        if (user.getApellido() == null || user.getApellido().trim().isEmpty()) {
            throw new InvalidUsuarioException(DomainConstants.Usuario.ERROR_APELLIDO_REQUERIDO);
        }

        if (user.getNumeroDocumento() == null || user.getNumeroDocumento().trim().isEmpty()) {
            throw new InvalidUsuarioException(DomainConstants.Usuario.ERROR_DOCUMENTO_REQUERIDO);
        }

        if (user.getCelular() == null || user.getCelular().trim().isEmpty()) {
            throw new InvalidUsuarioException(DomainConstants.Usuario.ERROR_CELULAR_REQUERIDO);
        }

        if (user.getCorreo() == null || user.getCorreo().trim().isEmpty()) {
            throw new InvalidUsuarioException(DomainConstants.Usuario.ERROR_CORREO_REQUERIDO);
        }

        if (user.getClave() == null || user.getClave().trim().isEmpty()) {
            throw new InvalidUsuarioException(DomainConstants.Usuario.ERROR_CLAVE_REQUERIDA);
        }
    }

    private void validateMayorEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            throw new InvalidUsuarioException(DomainConstants.Usuario.ERROR_FECHA_NACIMIENTO_REQUERIDA);
        }

        LocalDate fechaActual = LocalDate.now();
        int edad = Period.between(fechaNacimiento, fechaActual).getYears();

        if (edad < DomainConstants.Usuario.EDAD_MINIMA) {
            throw new InvalidUsuarioException(DomainConstants.Usuario.ERROR_MENOR_EDAD);
        }
    }
}