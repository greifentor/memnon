package de.ollie.memnon.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WiederholungServiceImplTest {

	@InjectMocks
	private WiederholungServiceImpl unitUnderTest;

	@Nested
	class holeAlleWiederholungenAufsteigendSortiertNachName {

		@Test
		void returnsAListOfWiederholung() {
			assertNotNull(unitUnderTest.holeAlleWiederholungenAufsteigendSortiertNachName());
		}
	}
}
