package de.ollie.memnon.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import de.ollie.memnon.core.model.Erinnerung;
import de.ollie.memnon.core.model.ErinnerungId;
import de.ollie.memnon.core.service.WiederholungService;
import de.ollie.memnon.core.service.port.persistence.ErinnerungPersistencePort;
import java.time.LocalDate;
import java.util.List;
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
	private ErinnerungId erinnerungId;

	@Mock
	private ErinnerungId erinnerungIdReturned;

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
	class aktualisiereNaechsterTermin_Erinnerung {

		@Test
		void t() {
			assertNull(unitUnderTest.aktualisiereNaechsterTermin(erinnerungId));
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
				.setId(new ErinnerungId(UID))
				.setNaechsterTermin(ERSTER_TERMIN)
				.setName(NAME)
				.setWiederholung(WiederholungService.WIEDERHOLUNG_JAEHRLICH);
			when(erinnerungPersistencePort.save(newErinnerung)).thenReturn(erinnerungReturned);
			when(erinnerungReturned.getId()).thenReturn(erinnerungIdReturned);
			when(uuidProvider.create()).thenReturn(UID);
			// Run
			ErinnerungId returned = unitUnderTest.erzeugeErinnerung(
				NAME,
				ERSTER_TERMIN,
				WiederholungService.WIEDERHOLUNG_JAEHRLICH,
				BEZUGSDATUM
			);
			// Check
			assertEquals(erinnerungIdReturned, returned);
		}
	}

	@Nested
	class holeAlleErinnerungenAufsteigendSortiertNachNaechsterTermin {

		@Test
		void callsTheErinnerungPersistencePortMethodCorrectly() {
			// Prepare
			List<Erinnerung> l = List.of();
			when(erinnerungPersistencePort.findAllOrderedByNaechsterTerminAsc()).thenReturn(l);
			// Run & Check
			assertSame(l, unitUnderTest.holeAlleErinnerungenAufsteigendSortiertNachNaechsterTermin());
		}
	}

	@Nested
	class loescheErinnerung_Erinnerung {

		@Test
		void doesNothing() {
			unitUnderTest.loescheErinnerung(erinnerungId);
			verifyNoInteractions(erinnerungPassed, erinnerungPersistencePort, erinnerungReturned, uuidProvider);
		}
	}
}
