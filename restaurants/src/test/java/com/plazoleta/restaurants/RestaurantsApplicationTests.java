
package com.plazoleta.restaurants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RestaurantsApplicationTests {

	@Test
	void mainMethodShouldRunWithoutException() {
		assertDoesNotThrow(() -> RestaurantsApplication.main(new String[] {}));
	}
}