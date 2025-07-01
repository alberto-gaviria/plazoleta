package com.plazoleta.users;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class UsersApplicationTests {

	@Test
	void mainMethodShouldRunWithoutException() {
		assertDoesNotThrow(() -> UsersApplication.main(new String[] {}));
	}
}