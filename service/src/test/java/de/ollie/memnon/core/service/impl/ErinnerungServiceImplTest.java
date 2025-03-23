package de.ollie.memnon.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import de.ollie.memnon.core.model.Erinnerung;
import de.ollie.memnon.core.service.WiederholungService;
import de.ollie.memnon.core.service.port.persistence.ErinnerungPersistencePort;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ErinnerungServiceImplTest {

	private static final LocalDate BEZUGSDATUM = LocalDate.of(2000, 4, 7);
	private static final LocalDate ERSTER_TERMIN = LocalDate.of(2025, 4, 7);
	private static final String NAME = "name";
	private static final UUID UID = UUID.randomUUID();

	@Mock
	private Erinnerung erinnerungPassed;

	@Mock
	private ErinnerungPersistencePort erinnerungPersistencePort;

	@Mock
	private Erinnerung erinnerungReturned;

	@Mock
	private UUIDProvider uuidProvider;

	@InjectMocks
	private ErinnerungServiceImpl unitUnderTest;

	@Nested
	class aktualsiereErinnerung_Erinnerung {

		@Test
		void throwsAnException_passingANullValueAsErinnerung() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.aktualsiereErinnerung(null));
		}

		@Test
		void returnsTheUpdatedErinnerung() {
			// Prepare
			when(erinnerungPersistencePort.save(erinnerungPassed)).thenReturn(erinnerungReturned);
			// Run
			Erinnerung returned = unitUnderTest.aktualsiereErinnerung(erinnerungPassed);
			// Check
			assertEquals(erinnerungReturned, returned);
		}
	}

	@Nested
	class aktualisiereNaechsterTermin_Erinnerung {

		@Test
		void t() {
			assertNull(unitUnderTest.aktualisiereNaechsterTermin(erinnerungPassed));
		}
	}

	@Nested
	class erzeugeErinnerung_String_LocalDate_Wiederholung_LocalDate {

		@Test
		void throwsAnException_passingANullValueAsNaechsterTermin() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.erzeugeErinnerung(NAME, null, WiederholungService.WIEDERHOLUNG_JAEHRLICH, BEZUGSDATUM)
			);
		}

		@Test
		void throwsAnException_passingANullValueAsName() {
			assertThrows(
				IllegalArgumentException.class,
				() ->
					unitUnderTest.erzeugeErinnerung(null, ERSTER_TERMIN, WiederholungService.WIEDERHOLUNG_JAEHRLICH, BEZUGSDATUM)
			);
		}

		@Test
		void returnsTheNewErinnerungObject() {
			// Prepare
			Erinnerung newErinnerung = new Erinnerung()
				.setBezugsdatum(BEZUGSDATUM)
				.setId(UID)
				.setNaechsterTermin(ERSTER_TERMIN)
				.setName(NAME)
				.setWiederholung(WiederholungService.WIEDERHOLUNG_JAEHRLICH);
			when(erinnerungPersistencePort.save(newErinnerung)).thenReturn(newErinnerung);
			when(uuidProvider.create()).thenReturn(UID);
			// Run
			Erinnerung returned = unitUnderTest.erzeugeErinnerung(
				NAME,
				ERSTER_TERMIN,
				WiederholungService.WIEDERHOLUNG_JAEHRLICH,
				BEZUGSDATUM
			);
			// Check
			assertEquals(newErinnerung, returned);
		}
	}

	@Nested
	class holeAlleErinnerungenAufsteigendSortiertNachNaechsterTermin {

		@Test
		void t() {
			assertNull(unitUnderTest.holeAlleErinnerungenAufsteigendSortiertNachNaechsterTermin());
		}
	}

	@Nested
	class loescheErinnerung_Erinnerung {

		@Test
		void doesNothing() {
			unitUnderTest.loescheErinnerung(erinnerungPassed);
			verifyNoInteractions(erinnerungPassed, erinnerungPersistencePort, erinnerungReturned, uuidProvider);
		}
	}
}
