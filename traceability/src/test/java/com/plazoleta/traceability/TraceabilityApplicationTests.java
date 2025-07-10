package com.plazoleta.traceability;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TraceabilityApplicationTest {

	@Test
	void main_shouldRunWithoutExceptions() {
		assertDoesNotThrow(() -> TraceabilityApplication.main(new String[]{}));
	}
}
