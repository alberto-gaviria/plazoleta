package com.plazoleta.users.adapters.driven.mysql.adapter;

import com.plazoleta.users.adapters.driven.mysql.entity.UserEntity;
import com.plazoleta.users.adapters.driven.mysql.exception.UserAlreadyExistsException;
import com.plazoleta.users.adapters.driven.mysql.mapper.IUserEntityMapper;
import com.plazoleta.users.adapters.driven.mysql.repository.IUserRepository;
import com.plazoleta.users.adapters.driven.mysql.util.AdapterConstants;
import com.plazoleta.users.domain.model.User;
import com.plazoleta.users.domain.spi.IUserPersistencePort;

import java.util.Optional;

public class AdminUserManagement implements IUserPersistencePort {
    private final IUserRepository usuarioRepository;
    private final IUserEntityMapper usuarioEntityMapper;

    public AdminUserManagement(IUserRepository usuarioRepository,
                               IUserEntityMapper usuarioEntityMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioEntityMapper = usuarioEntityMapper;
    }

    @Override
    public void saveUsuario(User user) {
        Optional<UserEntity> existingByEmail = usuarioRepository.findByCorreo(user.getCorreo());
        if (existingByEmail.isPresent()) {
            throw new UserAlreadyExistsException(AdapterConstants.ErrorMessages.USUARIO_CORREO_DUPLICADO);
        }

        Optional<UserEntity> existingByDocument = usuarioRepository.findByNumeroDocumento(user.getNumeroDocumento());
        if (existingByDocument.isPresent()) {
            throw new UserAlreadyExistsException(AdapterConstants.ErrorMessages.USUARIO_DOCUMENTO_DUPLICADO);
        }

        UserEntity userEntity = usuarioEntityMapper.toEntity(user);
        usuarioRepository.save(userEntity);
    }

    @Override
    public Optional<User> findById(Long id) {
        return usuarioRepository.findById(id)
                .map(usuarioEntityMapper::toModel);
    }

    @Override
    public Optional<User> findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .map(usuarioEntityMapper::toModel);
    }
}