package com.plazoleta.users.adapters.driving.http.dto.request;

import com.plazoleta.users.adapters.driving.http.util.HttpConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddUserRequest {

    @NotBlank(message = HttpConstants.UsuarioValidation.NOMBRE_REQUERIDO)
    private String nombre;

    @NotBlank(message = HttpConstants.UsuarioValidation.APELLIDO_REQUERIDO)
    private String apellido;

    @NotBlank(message = HttpConstants.UsuarioValidation.DOCUMENTO_REQUERIDO)
    @Pattern(regexp = HttpConstants.UsuarioValidation.DOCUMENTO_PATTERN,
            message = HttpConstants.UsuarioValidation.DOCUMENTO_NUMERICO)
    private String numeroDocumento;

    @NotBlank(message = HttpConstants.UsuarioValidation.CELULAR_REQUERIDO)
    @Pattern(regexp = HttpConstants.UsuarioValidation.CELULAR_PATTERN,
            message = HttpConstants.UsuarioValidation.CELULAR_FORMATO)
    private String celular;

    @NotNull(message = HttpConstants.UsuarioValidation.FECHA_NACIMIENTO_REQUERIDA)
    private LocalDate fechaNacimiento;

    @NotBlank(message = HttpConstants.UsuarioValidation.CORREO_REQUERIDO)
    @Email(message = HttpConstants.UsuarioValidation.CORREO_FORMATO)
    private String correo;

    @NotBlank(message = HttpConstants.UsuarioValidation.CLAVE_REQUERIDA)
    private String clave;
}