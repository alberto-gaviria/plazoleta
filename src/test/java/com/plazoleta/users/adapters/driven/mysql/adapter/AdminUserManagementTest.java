package com.plazoleta.users.adapters.driven.mysql.adapter;

import com.plazoleta.users.adapters.driven.mysql.entity.UserEntity;
import com.plazoleta.users.adapters.driven.mysql.exception.UserAlreadyExistsException;
import com.plazoleta.users.adapters.driven.mysql.mapper.IUserEntityMapper;
import com.plazoleta.users.adapters.driven.mysql.repository.IUserRepository;
import com.plazoleta.users.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminUserManagementTest {

    @Mock
    private IUserRepository usuarioRepository;

    @Mock
    private IUserEntityMapper usuarioEntityMapper;

    @InjectMocks
    private AdminUserManagement adminUserManagement;

    private User user;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setNombre("Juan");
        user.setApellido("Pérez");
        user.setNumeroDocumento("12345678");
        user.setCelular("+573001234567");
        user.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        user.setCorreo("juan@email.com");
        user.setClave("password123");
        user.setIdRol(2L);

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setNombre("Juan");
        userEntity.setApellido("Pérez");
        userEntity.setNumeroDocumento("12345678");
        userEntity.setCelular("+573001234567");
        userEntity.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        userEntity.setCorreo("juan@email.com");
        userEntity.setClave("password123");
        userEntity.setIdRol(2L);
    }

    @Test
    void saveUsuario_WhenValidUser_ShouldSaveSuccessfully() {
        // Given
        when(usuarioRepository.findByCorreo(user.getCorreo())).thenReturn(Optional.empty());
        when(usuarioRepository.findByNumeroDocumento(user.getNumeroDocumento())).thenReturn(Optional.empty());
        when(usuarioEntityMapper.toEntity(user)).thenReturn(userEntity);

        // When
        adminUserManagement.saveUsuario(user);

        // Then
        verify(usuarioRepository).findByCorreo(user.getCorreo());
        verify(usuarioRepository).findByNumeroDocumento(user.getNumeroDocumento());
        verify(usuarioEntityMapper).toEntity(user);
        verify(usuarioRepository).save(userEntity);
    }

    @Test
    void saveUsuario_WhenEmailExists_ShouldThrowException() {
        // Given
        when(usuarioRepository.findByCorreo(user.getCorreo())).thenReturn(Optional.of(userEntity));

        // When & Then
        assertThrows(UserAlreadyExistsException.class, () -> adminUserManagement.saveUsuario(user));
        verify(usuarioRepository).findByCorreo(user.getCorreo());
        verify(usuarioRepository, never()).findByNumeroDocumento(any());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void saveUsuario_WhenDocumentExists_ShouldThrowException() {
        // Given
        when(usuarioRepository.findByCorreo(user.getCorreo())).thenReturn(Optional.empty());
        when(usuarioRepository.findByNumeroDocumento(user.getNumeroDocumento())).thenReturn(Optional.of(userEntity));

        // When & Then
        assertThrows(UserAlreadyExistsException.class, () -> adminUserManagement.saveUsuario(user));
        verify(usuarioRepository).findByCorreo(user.getCorreo());
        verify(usuarioRepository).findByNumeroDocumento(user.getNumeroDocumento());
        verify(usuarioRepository, never()).save(any());
    }
}