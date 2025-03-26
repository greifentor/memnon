package de.ollie.memnon.shell.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import de.ollie.memnon.core.model.ErinnerungId;
import de.ollie.memnon.core.model.WiederholungJaehrlich;
import de.ollie.memnon.core.service.ErinnerungService;
import de.ollie.memnon.core.service.WiederholungService;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AddErinnerungCommandTest {

	private static final LocalDate BEZUGSDATUM = LocalDate.of(2000, 3, 23);
	private static final LocalDate ERSTER_TERMIN = LocalDate.of(2025, 3, 23);
	private static final UUID UID = UUID.randomUUID();
	private static final String NAME = "name";

	@Mock
	private ErinnerungId erinnerungId;

	@Mock
	private ErinnerungService erinnerungService;

	@Mock
	private WiederholungService wiederholungService;

	@InjectMocks
	private AddErinnerungCommand unitUnderTest;

	@Nested
	class run_String_LocalDate_LocalDate {

		@Test
		void throwsAnException_passingANullValueAsErsterTermin() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.run(NAME, null, BEZUGSDATUM));
		}

		@Test
		void throwsAnException_passingANullValueAsName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.run(null, ERSTER_TERMIN, BEZUGSDATUM));
		}

		@Test
		void returnsACorrectString_withWiederHolungExists() {
			// Prepare
			String expected = "Erinnerung (" + UID + ")";
			when(erinnerungId.getUuid()).thenReturn(UID);
			when(
				erinnerungService.erzeugeErinnerung(
					NAME,
					ERSTER_TERMIN,
					WiederholungService.WIEDERHOLUNG_JAEHRLICH,
					BEZUGSDATUM
				)
			)
				.thenReturn(erinnerungId);
			when(wiederholungService.holeWiederholungMitNamen(WiederholungJaehrlich.NAME))
				.thenReturn(Optional.of(WiederholungService.WIEDERHOLUNG_JAEHRLICH));
			// Run
			String returned = unitUnderTest.run(NAME, ERSTER_TERMIN, BEZUGSDATUM);
			// Check
			assertEquals(expected, returned);
		}

		@Test
		void returnsACorrectString_withWiederHolungNotExists() {
			// Prepare
			String expected = "Erinnerung (" + UID + ")";
			when(erinnerungId.getUuid()).thenReturn(UID);
			when(erinnerungService.erzeugeErinnerung(NAME, ERSTER_TERMIN, null, BEZUGSDATUM)).thenReturn(erinnerungId);
			when(wiederholungService.holeWiederholungMitNamen(WiederholungJaehrlich.NAME)).thenReturn(Optional.empty());
			// Run
			String returned = unitUnderTest.run(NAME, ERSTER_TERMIN, BEZUGSDATUM);
			// Check
			assertEquals(expected, returned);
		}
	}
}
