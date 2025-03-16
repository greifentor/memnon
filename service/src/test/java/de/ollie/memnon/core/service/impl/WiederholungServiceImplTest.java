package de.ollie.memnon.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.ollie.memnon.core.model.Wiederholung;
import de.ollie.memnon.core.model.WiederholungJaehrlich;
import java.util.List;
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

		@Test
		void returnsAListOfWiederholung_withCorrectElements() {
			// Prepare
			List<Wiederholung> expected = List.of(new WiederholungJaehrlich());
			// Run
			List<Wiederholung> returned = unitUnderTest.holeAlleWiederholungenAufsteigendSortiertNachName();
			// Check
			assertEquals(expected, returned);
		}
	}
}
