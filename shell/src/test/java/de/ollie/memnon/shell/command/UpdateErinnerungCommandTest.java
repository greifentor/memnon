package de.ollie.memnon.shell.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import de.ollie.memnon.core.model.Erinnerung;
import de.ollie.memnon.core.model.ErinnerungId;
import de.ollie.memnon.core.service.ErinnerungService;
import de.ollie.memnon.shell.OutputManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateErinnerungCommandTest {

	private static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	private static final String NAME = "name";
	private static final LocalDate RETURN_DATE = LocalDate.of(2025, 3, 30);
	private static final String SEARCH_STRING = "search-string";
	private static final UUID UID = UUID.randomUUID();

	@Mock
	private Erinnerung erinnerung;

	@Mock
	private ErinnerungId erinnerungId;

	@Mock
	private ErinnerungService erinnerungService;

	@Mock
	private OutputManager outputManager;

	@InjectMocks
	private UpdateErinnerungCommand unitUnderTest;

	@Nested
	class run_String {

		@Test
		void throwsAnException_passingANullValueAsSearchString() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.run(null));
		}

		@Test
		void throwsAnException_passingAnEmptyStringAsSearchString() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.run(""));
		}

		@Test
		void returnsString() {
			// Prepare
			when(erinnerungService.aktualisiereNaechsterTermin(erinnerungId)).thenReturn(Optional.of(RETURN_DATE));
			when(erinnerungService.findeAlleErinnerungIdZuSuchstring(SEARCH_STRING)).thenReturn(List.of(erinnerungId));
			when(erinnerungService.holeErinnerungZuId(erinnerungId)).thenReturn(Optional.of(erinnerung));
			when(outputManager.getDateFormatter()).thenReturn(LOCAL_DATE_FORMATTER);
			// Run & Check
			assertEquals("1 element(s) changed", unitUnderTest.run(SEARCH_STRING));
		}

		@Test
		void callsTheOutputManagerPrintln_forEachChangedErinnerung() {
			// Prepare
			when(erinnerung.getName()).thenReturn(NAME);
			when(erinnerungService.aktualisiereNaechsterTermin(erinnerungId)).thenReturn(Optional.of(RETURN_DATE));
			when(erinnerungService.findeAlleErinnerungIdZuSuchstring(SEARCH_STRING)).thenReturn(List.of(erinnerungId));
			when(erinnerungService.holeErinnerungZuId(erinnerungId)).thenReturn(Optional.of(erinnerung));
			when(outputManager.getDateFormatter()).thenReturn(LOCAL_DATE_FORMATTER);
			// Run
			unitUnderTest.run(SEARCH_STRING);
			// Check
			verify(outputManager, times(1)).println("changed next date to 30.03.2025 for " + NAME);
		}

		@Test
		void doesNotCallTheOutputManagerPrintlnMethod_whenErinnerungHasNotBeenChanged() {
			// Prepare
			when(erinnerungService.aktualisiereNaechsterTermin(erinnerungId)).thenReturn(Optional.empty());
			when(erinnerungService.findeAlleErinnerungIdZuSuchstring(SEARCH_STRING)).thenReturn(List.of(erinnerungId));
			// Run
			unitUnderTest.run(SEARCH_STRING);
			// Check
			verifyNoInteractions(outputManager);
		}

		@Test
		void callsTheOutputManagerPrintln_withAnErrorMessage_whenErinnerungNotFoundForId() {
			// Prepare
			when(erinnerungId.getUuid()).thenReturn(UID);
			when(erinnerungService.aktualisiereNaechsterTermin(erinnerungId)).thenReturn(Optional.of(RETURN_DATE));
			when(erinnerungService.findeAlleErinnerungIdZuSuchstring(SEARCH_STRING)).thenReturn(List.of(erinnerungId));
			when(erinnerungService.holeErinnerungZuId(erinnerungId)).thenReturn(Optional.empty());
			// Run
			unitUnderTest.run(SEARCH_STRING);
			// Check
			verify(outputManager, times(1)).println("erinnerung not found with id: " + UID);
		}
	}
}
