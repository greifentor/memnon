package de.ollie.memnon.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.memnon.configuration.ServiceConfiguration;
import de.ollie.memnon.core.model.ConnectorId;
import de.ollie.memnon.core.model.Erinnerung;
import de.ollie.memnon.core.model.ErinnerungId;
import de.ollie.memnon.core.model.ErinnerungStatus;
import de.ollie.memnon.core.model.ExternalErinnerung;
import de.ollie.memnon.core.model.Wiederholung;
import de.ollie.memnon.core.service.LocalDateService;
import de.ollie.memnon.core.service.WiederholungService;
import de.ollie.memnon.core.service.port.connector.ExternalErinnerungConnector;
import de.ollie.memnon.core.service.port.persistence.ErinnerungPersistencePort;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ErinnerungServiceImplTest {

	private static final LocalDate BEZUGSDATUM = LocalDate.of(2000, 4, 7);
	private static final LocalDate ERSTER_TERMIN = LocalDate.of(2025, 4, 7);
	private static final LocalDate LOCAL_DATE = LocalDate.of(2025, 4, 3);
	private static final String NAME = "name";
	private static final LocalDate NOW = LocalDate.now();
	private static final UUID UID = UUID.randomUUID();
	private static final String SUCHSTRING = "such-string";

	@Mock
	private Erinnerung erinnerung;

	@Mock
	private ErinnerungId erinnerungId;

	@Mock
	private ErinnerungId erinnerungIdReturned;

	@Mock
	private Erinnerung erinnerungPassed;

	private List<ExternalErinnerungConnector> externalErinnerungConnectors = new ArrayList<>();

	@Mock
	private ErinnerungPersistencePort erinnerungPersistencePort;

	@Mock
	private Erinnerung erinnerungReturned;

	@Mock
	private LocalDateService localDateService;

	@Mock
	private UUIDProvider uuidProvider;

	@Mock
	private Wiederholung wiederholung;

	@Mock
	private ServiceConfiguration serviceConfiguration;

	@InjectMocks
	private ErinnerungServiceImpl unitUnderTest;

	@BeforeEach
	void beforeEach() {
		ReflectionTestUtils.setField(unitUnderTest, "externalErinnerungConnectors", externalErinnerungConnectors);
	}

	@Nested
	class aktualisiereNaechsterTermin_Erinnerung {

		@Test
		void throwsAnException_passingANullValueAsErinnerungId() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.aktualisiereNaechsterTermin(null));
		}

		@Test
		void doesNotEnhanceTheNaechsterTermin_whenWiederholungIsNull() {
			// Prepare
			when(erinnerung.getId()).thenReturn(erinnerungId);
			when(erinnerung.getWiederholung()).thenReturn(null);
			when(erinnerungPersistencePort.findById(erinnerungId)).thenReturn(Optional.of(erinnerung));
			// Run & Check
			assertTrue(unitUnderTest.aktualisiereNaechsterTermin(erinnerung).isEmpty());
			verify(erinnerungPersistencePort, never()).save(erinnerung);
		}

		@Test
		void returnsNewNaechsterTermin_whenWiederholungIsNotNull() {
			// Prepare
			when(erinnerung.getId()).thenReturn(erinnerungId);
			when(erinnerung.getWiederholung()).thenReturn(wiederholung);
			when(erinnerung.berechneNaechsterTermin()).thenReturn(LOCAL_DATE);
			when(erinnerungPersistencePort.findById(erinnerungId)).thenReturn(Optional.of(erinnerung));
			// Run & Check
			assertEquals(LOCAL_DATE, unitUnderTest.aktualisiereNaechsterTermin(erinnerung).get());
		}

		@Test
		void setsTheNewNaechsterTerminToTheErinnerung() {
			// Prepare
			when(erinnerung.getId()).thenReturn(erinnerungId);
			when(erinnerung.getWiederholung()).thenReturn(wiederholung);
			when(erinnerung.berechneNaechsterTermin()).thenReturn(LOCAL_DATE);
			when(erinnerungPersistencePort.findById(erinnerungId)).thenReturn(Optional.of(erinnerung));
			// Run
			unitUnderTest.aktualisiereNaechsterTermin(erinnerung);
			// Check
			verify(erinnerung, times(1)).setNaechsterTermin(LOCAL_DATE);
		}

		@Test
		void savedTheErinnerung_whenNewNaechsterTerminHasBeenChanged() {
			// Prepare
			when(erinnerung.getId()).thenReturn(erinnerungId);
			when(erinnerung.getWiederholung()).thenReturn(wiederholung);
			when(erinnerung.berechneNaechsterTermin()).thenReturn(LOCAL_DATE);
			when(erinnerungPersistencePort.findById(erinnerungId)).thenReturn(Optional.of(erinnerung));
			// Run
			unitUnderTest.aktualisiereNaechsterTermin(erinnerung);
			// Check
			verify(erinnerungPersistencePort, times(1)).save(erinnerung);
		}

		@Test
		void doesNotCallErinnerungPersistencePortSaveMethod_whenErinnerungIsNotFoundInPersistenceLayer() {
			// Prepare
			when(erinnerung.getId()).thenReturn(erinnerungId);
			when(erinnerungPersistencePort.findById(erinnerungId)).thenReturn(Optional.empty());
			// Run
			unitUnderTest.aktualisiereNaechsterTermin(erinnerung);
			// Check
			verify(erinnerungPersistencePort, never()).save(any(Erinnerung.class));
		}

		@Test
		void callsTheCorrectExternalErinnerungConnector_confirmMethod() {
			// Prepare
			ExternalErinnerungConnector connector = mock(ExternalErinnerungConnector.class);
			ConnectorId connectorId = mock(ConnectorId.class);
			ExternalErinnerung erinnerung = mock(ExternalErinnerung.class);
			externalErinnerungConnectors.add(connector);
			when(connector.getId()).thenReturn(connectorId);
			when(erinnerung.getId()).thenReturn(erinnerungId);
			when(erinnerung.getConnectorId()).thenReturn(connectorId);
			when(erinnerungPersistencePort.findById(erinnerungId)).thenReturn(Optional.empty());
			unitUnderTest.postConstruct();
			// Run
			unitUnderTest.aktualisiereNaechsterTermin(erinnerung);
			// Check
			verify(connector, times(1)).confirm(erinnerungId);
		}
	}

	@Nested
	class ermittleStatus_ErinnerungId {

		@Test
		void throwsAnException_passingANullValueAsErinnerungId() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.ermittleStatus(null));
		}

		@Test
		void returnsStatusVerpasst_whenErinnerungNaechsterTermin_isBeforeNow() {
			// Prepare
			when(erinnerung.getNaechsterTermin()).thenReturn(NOW.minusDays(1));
			when(localDateService.now()).thenReturn(NOW);
			// Run
			ErinnerungStatus returned = unitUnderTest.ermittleStatus(erinnerung);
			// Check
			assertEquals(ErinnerungStatus.VERPASST, returned);
		}

		@Test
		void returnsStatusHeute_whenErinnerungNaechsterTermin_isEqualNow() {
			// Prepare
			when(erinnerung.getNaechsterTermin()).thenReturn(NOW);
			when(localDateService.now()).thenReturn(NOW);
			// Run
			ErinnerungStatus returned = unitUnderTest.ermittleStatus(erinnerung);
			// Check
			assertEquals(ErinnerungStatus.HEUTE, returned);
		}

		@Test
		void returnsStatusMorgen_whenErinnerungNaechsterTermin_isNowPlusOneDay() {
			// Prepare
			when(erinnerung.getNaechsterTermin()).thenReturn(NOW.plusDays(1));
			when(localDateService.now()).thenReturn(NOW);
			// Run
			ErinnerungStatus returned = unitUnderTest.ermittleStatus(erinnerung);
			// Check
			assertEquals(ErinnerungStatus.MORGEN, returned);
		}

		@Test
		void returnsStatusEntfernt_whenErinnerungNaechsterTermin_isAfterDemaechst() {
			// Prepare
			int demnaechst = 7;
			when(erinnerung.getNaechsterTermin()).thenReturn(NOW.plusDays(demnaechst + 1));
			when(localDateService.now()).thenReturn(NOW);
			when(serviceConfiguration.getStatusMaxDaysDemaechst()).thenReturn(demnaechst);
			// Run
			ErinnerungStatus returned = unitUnderTest.ermittleStatus(erinnerung);
			// Check
			assertEquals(ErinnerungStatus.ENTFERNT, returned);
		}

		@Test
		void returnsStatusDemnaechst_whenErinnerungNaechsterTermin_isEqualDemaechst() {
			// Prepare
			int demnaechst = 7;
			when(erinnerung.getNaechsterTermin()).thenReturn(NOW.plusDays(demnaechst));
			when(localDateService.now()).thenReturn(NOW);
			when(serviceConfiguration.getStatusMaxDaysDemaechst()).thenReturn(demnaechst);
			// Run
			ErinnerungStatus returned = unitUnderTest.ermittleStatus(erinnerung);
			// Check
			assertEquals(ErinnerungStatus.DEMNAECHST, returned);
		}

		@Test
		void returnsStatusDemnaechst_whenErinnerungNaechsterTermin_isBeforeDemaechst() {
			// Prepare
			int demnaechst = 7;
			when(erinnerung.getNaechsterTermin()).thenReturn(NOW.plusDays(demnaechst - 1));
			when(localDateService.now()).thenReturn(NOW);
			when(serviceConfiguration.getStatusMaxDaysDemaechst()).thenReturn(demnaechst);
			// Run
			ErinnerungStatus returned = unitUnderTest.ermittleStatus(erinnerung);
			// Check
			assertEquals(ErinnerungStatus.DEMNAECHST, returned);
		}

		@Test
		void returnsStatusNah_whenErinnerungNaechsterTermin_isEqualNah() {
			// Prepare
			int demnaechst = 3;
			when(erinnerung.getNaechsterTermin()).thenReturn(NOW.plusDays(demnaechst));
			when(localDateService.now()).thenReturn(NOW);
			when(serviceConfiguration.getStatusMaxDaysNah()).thenReturn(demnaechst);
			// Run
			ErinnerungStatus returned = unitUnderTest.ermittleStatus(erinnerung);
			// Check
			assertEquals(ErinnerungStatus.NAH, returned);
		}

		@Test
		void returnsStatusNah_whenErinnerungNaechsterTermin_isBeforeNah() {
			// Prepare
			int demnaechst = 3;
			when(erinnerung.getNaechsterTermin()).thenReturn(NOW.plusDays(demnaechst - 1));
			when(localDateService.now()).thenReturn(NOW);
			when(serviceConfiguration.getStatusMaxDaysNah()).thenReturn(demnaechst);
			// Run
			ErinnerungStatus returned = unitUnderTest.ermittleStatus(erinnerung);
			// Check
			assertEquals(ErinnerungStatus.NAH, returned);
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
	class findeAlleErinnerungIdZuSuchstring_String {

		@Test
		void throwsException_passingANullPointer() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.findeAlleErinnerungIdZuSuchstring(null));
		}

		@Test
		void callsThePersistencePortFindMethodCorrectly() {
			// Prepare
			List<ErinnerungId> expected = List.of();
			when(erinnerungPersistencePort.findIdsByNameContains(SUCHSTRING)).thenReturn(expected);
			// Run
			List<ErinnerungId> returned = unitUnderTest.findeAlleErinnerungIdZuSuchstring(SUCHSTRING);
			// Check
			assertSame(expected, returned);
		}
	}

	@Nested
	class holeAlleErinnerungenAufsteigendSortiertNachNaechsterTermin {

		@Test
		void callsTheErinnerungPersistencePortMethodCorrectly() {
			// Prepare
			List<Erinnerung> l = new ArrayList<>();
			when(erinnerungPersistencePort.findAllOrderedByNaechsterTerminAsc()).thenReturn(l);
			// Run & Check
			assertSame(l, unitUnderTest.holeAlleErinnerungenAufsteigendSortiertNachNaechsterTermin());
		}

		@Test
		void addsNoExternalErinnerungObjects_whenNoExternalErinnerungConnectorIsADefined() {
			// Prepare
			List<Erinnerung> l = new ArrayList<>();
			when(erinnerungPersistencePort.findAllOrderedByNaechsterTerminAsc()).thenReturn(l);
			// Run & Check
			assertTrue(unitUnderTest.holeAlleErinnerungenAufsteigendSortiertNachNaechsterTermin().isEmpty());
		}

		@Test
		void addsTheExternalErinnerungObjects_foundInTheExternalErinnerungConnectors() {
			// Prepare
			ExternalErinnerung externalErinnerung = mock(ExternalErinnerung.class);
			ExternalErinnerungConnector connector = mock(ExternalErinnerungConnector.class);
			List<Erinnerung> l = new ArrayList<>();
			when(erinnerungPersistencePort.findAllOrderedByNaechsterTerminAsc()).thenReturn(l);
			when(connector.findAllErinnerungen()).thenReturn(List.of(externalErinnerung));
			externalErinnerungConnectors.add(connector);
			// Run
			List<Erinnerung> returned = unitUnderTest.holeAlleErinnerungenAufsteigendSortiertNachNaechsterTermin();
			// Check
			assertSame(externalErinnerung, returned.get(0));
		}

		@Test
		void sortsTheErinnerungObjects_byNaechsterTermin() {
			// Prepare
			Erinnerung erinnerung0 = createErinnerung(LocalDate.of(2025, 12, 26));
			Erinnerung erinnerung1 = createErinnerung(LocalDate.of(2025, 12, 28));
			ExternalErinnerung externalErinnerung = mock(ExternalErinnerung.class);
			ExternalErinnerungConnector connector = mock(ExternalErinnerungConnector.class);
			List<Erinnerung> l = List.of(erinnerung0, erinnerung1);
			when(connector.findAllErinnerungen()).thenReturn(List.of(externalErinnerung));
			when(erinnerungPersistencePort.findAllOrderedByNaechsterTerminAsc()).thenReturn(l);
			when(externalErinnerung.getNaechsterTermin()).thenReturn(LocalDate.of(2025, 12, 27));
			externalErinnerungConnectors.add(connector);
			// Run
			List<Erinnerung> returned = unitUnderTest.holeAlleErinnerungenAufsteigendSortiertNachNaechsterTermin();
			// Check
			assertSame(erinnerung0, returned.get(0));
			assertSame(externalErinnerung, returned.get(1));
			assertSame(erinnerung1, returned.get(2));
		}

		private Erinnerung createErinnerung(LocalDate date) {
			Erinnerung erinnerung = mock(Erinnerung.class);
			when(erinnerung.getNaechsterTermin()).thenReturn(date);
			return erinnerung;
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

	@Nested
	class loescheErinnerung_Erinnerung {

		@Test
		void throwsAnException_passingANullValueAsErinnerungId() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.loescheErinnerung(null));
		}

		@Test
		void callsThePersistencePortRemoveMethod_withPassedId() {
			// Run
			unitUnderTest.loescheErinnerung(erinnerungId);
			// Check
			verify(erinnerungPersistencePort, times(1)).remove(erinnerungId);
		}
	}
}
