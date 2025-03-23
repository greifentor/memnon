package de.ollie.memnon.core.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WiederholungServiceTest {

	@Nested
	class constants {

		@Test
		void WiederholungJaehrlich_isSet() {
			assertNotNull(WiederholungService.WIEDERHOLUNG_JAEHRLICH);
		}
	}
}
