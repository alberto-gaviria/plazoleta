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
        // Removido setIdRol() ya que no existe en User

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

    @Test
    void findById_WhenUserExists_ShouldReturnUser() {
        // Given
        Long userId = 1L;
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(usuarioEntityMapper.toModel(userEntity)).thenReturn(user);

        // When
        Optional<User> result = adminUserManagement.findById(userId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(usuarioRepository).findById(userId);
        verify(usuarioEntityMapper).toModel(userEntity);
    }

    @Test
    void findById_WhenUserNotExists_ShouldReturnEmpty() {
        // Given
        Long userId = 999L;
        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        // When
        Optional<User> result = adminUserManagement.findById(userId);

        // Then
        assertFalse(result.isPresent());
        verify(usuarioRepository).findById(userId);
        verify(usuarioEntityMapper, never()).toModel(any());
    }

    @Test
    void findByCorreo_WhenUserExists_ShouldReturnUser() {
        // Given
        String correo = "juan@email.com";
        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(userEntity));
        when(usuarioEntityMapper.toModel(userEntity)).thenReturn(user);

        // When
        Optional<User> result = adminUserManagement.findByCorreo(correo);

        // Then
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(usuarioRepository).findByCorreo(correo);
        verify(usuarioEntityMapper).toModel(userEntity);
    }

    @Test
    void findByCorreo_WhenUserNotExists_ShouldReturnEmpty() {
        // Given
        String correo = "noexiste@email.com";
        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.empty());

        // When
        Optional<User> result = adminUserManagement.findByCorreo(correo);

        // Then
        assertFalse(result.isPresent());
        verify(usuarioRepository).findByCorreo(correo);
        verify(usuarioEntityMapper, never()).toModel(any());
    }

    @Test
    void saveUsuario_WhenBothEmailAndDocumentExist_ShouldThrowExceptionForEmail() {
        // Given - El correo se verifica primero
        when(usuarioRepository.findByCorreo(user.getCorreo())).thenReturn(Optional.of(userEntity));

        // When & Then
        assertThrows(UserAlreadyExistsException.class, () -> adminUserManagement.saveUsuario(user));
        verify(usuarioRepository).findByCorreo(user.getCorreo());
        verify(usuarioRepository, never()).findByNumeroDocumento(any());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void constructor_ShouldCreateInstance() {
        // When
        AdminUserManagement adapter = new AdminUserManagement(usuarioRepository, usuarioEntityMapper);

        // Then
        assertNotNull(adapter);
    }
}