package de.ollie.memnon.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import de.ollie.memnon.core.model.Erinnerung;
import de.ollie.memnon.core.model.ErinnerungId;
import de.ollie.memnon.core.model.Wiederholung;
import de.ollie.memnon.core.service.WiederholungService;
import de.ollie.memnon.core.service.port.persistence.ErinnerungPersistencePort;
import java.time.LocalDate;
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
class ErinnerungServiceImplTest {

	private static final LocalDate BEZUGSDATUM = LocalDate.of(2000, 4, 7);
	private static final LocalDate ERSTER_TERMIN = LocalDate.of(2025, 4, 7);
	private static final LocalDate LOCAL_DATE = LocalDate.of(2025, 4, 3);
	private static final String NAME = "name";
	private static final UUID UID = UUID.randomUUID();

	@Mock
	private Erinnerung erinnerung;

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

	@Mock
	private Wiederholung wiederholung;

	@InjectMocks
	private ErinnerungServiceImpl unitUnderTest;

	@Nested
	class aktualisiereNaechsterTermin_Erinnerung {

		@Test
		void throwsAnException_passingANullValueAsErinnerungId() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.aktualisiereNaechsterTermin(null));
		}

		@Test
		void doesNotEnhanceTheNaechsterTermin_whenWiederholungIsNull() {
			// Prepare
			when(erinnerung.getWiederholung()).thenReturn(null);
			when(erinnerungPersistencePort.findById(erinnerungId)).thenReturn(Optional.of(erinnerung));
			// Run & Check
			assertTrue(unitUnderTest.aktualisiereNaechsterTermin(erinnerungId).isEmpty());
			verify(erinnerungPersistencePort, never()).save(erinnerung);
		}

		@Test
		void returnsNewNaechsterTermin_whenWiederholungIsNotNull() {
			// Prepare
			when(erinnerung.getWiederholung()).thenReturn(wiederholung);
			when(erinnerung.berechneNaechsterTermin()).thenReturn(LOCAL_DATE);
			when(erinnerungPersistencePort.findById(erinnerungId)).thenReturn(Optional.of(erinnerung));
			// Run & Check
			assertEquals(LOCAL_DATE, unitUnderTest.aktualisiereNaechsterTermin(erinnerungId).get());
		}

		@Test
		void setsTheNewNaechsterTerminToTheErinnerung() {
			// Prepare
			when(erinnerung.getWiederholung()).thenReturn(wiederholung);
			when(erinnerung.berechneNaechsterTermin()).thenReturn(LOCAL_DATE);
			when(erinnerungPersistencePort.findById(erinnerungId)).thenReturn(Optional.of(erinnerung));
			// Run
			unitUnderTest.aktualisiereNaechsterTermin(erinnerungId);
			// Check
			verify(erinnerung, times(1)).setNaechsterTermin(LOCAL_DATE);
		}

		@Test
		void savedTheErinnerung_whenNewNaechsterTerminHasBeenChanged() {
			// Prepare
			when(erinnerung.getWiederholung()).thenReturn(wiederholung);
			when(erinnerung.berechneNaechsterTermin()).thenReturn(LOCAL_DATE);
			when(erinnerungPersistencePort.findById(erinnerungId)).thenReturn(Optional.of(erinnerung));
			// Run
			unitUnderTest.aktualisiereNaechsterTermin(erinnerungId);
			// Check
			verify(erinnerungPersistencePort, times(1)).save(erinnerung);
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

	@Nested
	class holeErinnerungZuId_ErinnerungId {

		@Test
		void throwsAnException_passingANullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.holeErinnerungZuId(null));
		}

		@Test
		void returnsTheValueReturnedByThePersistencePortMethod() {
			// Prepare
			Optional<Erinnerung> expected = Optional.of(erinnerung);
			when(erinnerungPersistencePort.findById(erinnerungId)).thenReturn(expected);
			// Check
			Optional<Erinnerung> returned = unitUnderTest.holeErinnerungZuId(erinnerungId);
			// Run
			assertEquals(expected, returned);
		}
	}
}
