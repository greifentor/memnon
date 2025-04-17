package de.ollie.memnon.shell.command;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.memnon.core.model.Erinnerung;
import de.ollie.memnon.core.model.ErinnerungId;
import de.ollie.memnon.core.service.ErinnerungService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RemoveErinnerungCommandTest {

	private static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	private static final String NAME = "name";
	private static final LocalDate RETURN_DATE = LocalDate.of(2025, 3, 30);
	private static final String SEARCH_STRING = "search-string";
	private static final UUID UID = UUID.randomUUID();

	@Mock
	private Erinnerung erinnerung;

	@Mock
	private ErinnerungId erinnerungId0;

	@Mock
	private ErinnerungId erinnerungId1;

	@Mock
	private ErinnerungService erinnerungService;

	@InjectMocks
	private RemoveErinnerungCommand unitUnderTest;

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
		void throwsAnException_passingASearchString_withMoreThanOneHit() {
			// Prepare
			when(erinnerungService.findeAlleErinnerungIdZuSuchstring(SEARCH_STRING))
				.thenReturn(List.of(erinnerungId0, erinnerungId1));
			// Run & Check
			assertThrows(IllegalStateException.class, () -> unitUnderTest.run(SEARCH_STRING));
		}

		@Test
		void throwsAnException_passingASearchSring_withNoHits() {
			// Prepare
			when(erinnerungService.findeAlleErinnerungIdZuSuchstring(SEARCH_STRING)).thenReturn(List.of());
			// Run & Check
			assertThrows(NoSuchElementException.class, () -> unitUnderTest.run(SEARCH_STRING));
		}

		@Test
		void callsTheErinnerungServiceMethodRemove_passingASearchSring_withOneHit() {
			// Prepare
			when(erinnerungService.findeAlleErinnerungIdZuSuchstring(SEARCH_STRING)).thenReturn(List.of(erinnerungId0));
			// Run
			unitUnderTest.run(SEARCH_STRING);
			// Check
			verify(erinnerungService, times(1)).loescheErinnerung(erinnerungId0);
		}
	}
}
