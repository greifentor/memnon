package de.ollie.memnon.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ErinnerungTest {

	private static final LocalDate LOCAL_DATE = LocalDate.of(2000, 4, 7);

	@Mock
	private Wiederholung wiederholung;

	@InjectMocks
	private Erinnerung unitUnderTest;

	@Nested
	class berechneNaechsterTermin {

		@Test
		void returnsNull_whenErinnerungWiederholungIsNull() {
			// Prepare
			unitUnderTest.setWiederholung(null);
			unitUnderTest.setNaechsterTermin(LOCAL_DATE);
			// Run & Check
			assertNull(unitUnderTest.berechneNaechsterTermin());
		}

		@Test
		void returnsTheNewNaechsteTermin_whenErinnerungWiederholungIsSet() {
			// Prepare
			unitUnderTest.setWiederholung(wiederholung);
			when(wiederholung.berechneNaechsterTermin(unitUnderTest)).thenReturn(LOCAL_DATE);
			// Run & Check
			assertEquals(LOCAL_DATE, unitUnderTest.berechneNaechsterTermin());
		}
	}
}
