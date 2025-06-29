package com.plazoleta.users.domain.spi;

public interface IPasswordEncoderPort {
    String encode(String rawPassword);
}
