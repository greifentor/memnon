package de.ollie.memnon.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WiederholungJaehrlichTest {

	private static final LocalDate NAECHSTER_TERMIN = LocalDate.of(2025, 3, 16);

	@Mock
	private Erinnerung erinnerung;

	@InjectMocks
	private WiederholungJaehrlich unitUnderTest;

	@Nested
	class berechneNaechsterTermin_Erinnerung {

		@Test
		void throwsException_passingANullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.berechneNaechsterTermin(null));
		}

		@Test
		void setsNaechsterTerminOneYearAfterTheCurrentOne() {
			// Prepare
			when(erinnerung.getNaechsterTermin()).thenReturn(NAECHSTER_TERMIN);
			// Run
			unitUnderTest.berechneNaechsterTermin(erinnerung);
			// Check
			verify(erinnerung, times(1)).setNaechsterTermin(NAECHSTER_TERMIN.plusYears(1));
		}

		@Test
		void returnsTheNewNaechsterTermin() {
			// Prepare
			when(erinnerung.getNaechsterTermin()).thenReturn(NAECHSTER_TERMIN);
			// Run
			LocalDate returned = unitUnderTest.berechneNaechsterTermin(erinnerung);
			// Check
			assertEquals(NAECHSTER_TERMIN.plusYears(1), returned);
		}
	}
}
