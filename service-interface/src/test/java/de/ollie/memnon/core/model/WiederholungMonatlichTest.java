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
class WiederholungMonatlichTest {

	private static final LocalDate BEZUGSDATUM = LocalDate.of(2025, 3, 16);
	private static final LocalDate NAECHSTER_TERMIN = LocalDate.of(2025, 5, 20);

	@Mock
	private Erinnerung erinnerung;

	@InjectMocks
	private WiederholungMonatlich unitUnderTest;

	@Nested
	class berechneNaechsterTermin_Erinnerung {

		@Test
		void throwsException_passingANullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.berechneNaechsterTermin(null));
		}

		@Test
		void setsNaechsterTerminToTheFirstMonthlyOccurrenceAfterTheCurrentOne() {
			// Prepare
			when(erinnerung.getBezugsdatum()).thenReturn(BEZUGSDATUM);
			when(erinnerung.getNaechsterTermin()).thenReturn(NAECHSTER_TERMIN);
			// Run
			unitUnderTest.berechneNaechsterTermin(erinnerung);
			// Check
			verify(erinnerung, times(1)).setNaechsterTermin(LocalDate.of(2025, 6, 16));
		}

		@Test
		void avoidsMonthEndDriftByCalculatingFromTheBezugsdatum() {
			// Prepare
			LocalDate bezugsdatum = LocalDate.of(2025, 1, 31);
			when(erinnerung.getBezugsdatum()).thenReturn(bezugsdatum);
			when(erinnerung.getNaechsterTermin()).thenReturn(LocalDate.of(2025, 2, 28));
			// Run
			unitUnderTest.berechneNaechsterTermin(erinnerung);
			// Check
			verify(erinnerung, times(1)).setNaechsterTermin(LocalDate.of(2025, 3, 31));
		}

		@Test
		void returnsTheNewNaechsterTermin() {
			// Prepare
			when(erinnerung.getBezugsdatum()).thenReturn(BEZUGSDATUM);
			when(erinnerung.getNaechsterTermin()).thenReturn(NAECHSTER_TERMIN);
			// Run
			LocalDate returned = unitUnderTest.berechneNaechsterTermin(erinnerung);
			// Check
			assertEquals(LocalDate.of(2025, 6, 16), returned);
		}
	}

	@Nested
	class getName {

		@Test
		void returnsTheCorrectName() {
			assertEquals(WiederholungMonatlich.NAME, unitUnderTest.getName());
		}
	}
}
