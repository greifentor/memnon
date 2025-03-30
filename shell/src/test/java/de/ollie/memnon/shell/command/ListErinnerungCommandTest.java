package de.ollie.memnon.shell.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.memnon.core.model.Erinnerung;
import de.ollie.memnon.core.service.ErinnerungService;
import de.ollie.memnon.shell.OutputManager;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ListErinnerungCommandTest {

	private static final String BEZUGSDATUM_STRING = "06.02.1998";
	private static final LocalDate BEZUGSDATUM = LocalDate.of(1998, 2, 6);
	private static final String NAECHSTER_TERMIN_STRING = "06.02.1999";
	private static final LocalDate NAECHSTER_TERMIN = LocalDate.of(1999, 2, 6);
	private static final String NAME = "name";

	@Mock
	private Erinnerung erinnerung;

	@Mock
	private ErinnerungService erinnerungService;

	@Mock
	private OutputManager out;

	@InjectMocks
	private ListErinnerungCommand unitUnderTest;

	@Nested
	class run {

		@Test
		void returnsTheCorrectString() {
			// Prepare
			List<Erinnerung> l = List.of(erinnerung);
			String expected = l.size() + " element(s)";
			when(erinnerung.getBezugsdatum()).thenReturn(BEZUGSDATUM);
			when(erinnerung.getNaechsterTermin()).thenReturn(NAECHSTER_TERMIN);
			when(erinnerung.getName()).thenReturn(NAME);
			when(erinnerungService.holeAlleErinnerungenAufsteigendSortiertNachNaechsterTermin()).thenReturn(l);
			// Run
			String returned = unitUnderTest.run();
			// Check
			assertEquals(expected, returned);
		}

		@Test
		void callsPrintStreamCorrectly_whenNoBezugsdatumIsSet() {
			// Prepare
			List<Erinnerung> l = List.of(erinnerung);
			when(erinnerung.getNaechsterTermin()).thenReturn(NAECHSTER_TERMIN);
			when(erinnerung.getName()).thenReturn(NAME);
			when(erinnerungService.holeAlleErinnerungenAufsteigendSortiertNachNaechsterTermin()).thenReturn(l);
			// Run
			unitUnderTest.run();
			// Check
			verify(out, times(1)).println(NAME + "                      " + NAECHSTER_TERMIN_STRING + "          -");
		}

		@Test
		void callsPrintStreamCorrectly_whenBezugsdatumIsSet() {
			// Prepare
			List<Erinnerung> l = List.of(erinnerung);
			when(erinnerung.getBezugsdatum()).thenReturn(BEZUGSDATUM);
			when(erinnerung.getNaechsterTermin()).thenReturn(NAECHSTER_TERMIN);
			when(erinnerung.getName()).thenReturn(NAME);
			when(erinnerungService.holeAlleErinnerungenAufsteigendSortiertNachNaechsterTermin()).thenReturn(l);
			// Run
			unitUnderTest.run();
			// Check
			verify(out, times(1))
				.println(NAME + "                      " + NAECHSTER_TERMIN_STRING + " " + BEZUGSDATUM_STRING);
		}
	}
}
