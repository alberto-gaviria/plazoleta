package com.plazoleta.usuarios;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class UsuariosApplicationTests {

	@Test
	void mainMethodShouldRunWithoutException() {
		assertDoesNotThrow(() -> UsuariosApplication.main(new String[] {}));
	}
}