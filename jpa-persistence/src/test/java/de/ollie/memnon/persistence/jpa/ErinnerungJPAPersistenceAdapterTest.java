package de.ollie.memnon.persistence.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.memnon.core.model.Erinnerung;
import de.ollie.memnon.core.model.ErinnerungId;
import de.ollie.memnon.core.service.WiederholungService;
import de.ollie.memnon.persistence.jpa.entity.ErinnerungDBO;
import de.ollie.memnon.persistence.jpa.mapper.ErinnerungDBOMapper;
import de.ollie.memnon.persistence.jpa.repository.ErinnerungDBORepository;
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
class ErinnerungJPAPersistenceAdapterTest {

	private static final UUID UID = UUID.randomUUID();
	private static final String SUCHSTRING = "suchstring";

	@Mock
	private Erinnerung erinnerungIn;

	@Mock
	private Erinnerung erinnerungOut;

	@Mock
	private ErinnerungDBO erinnerungDboIn;

	@Mock
	private ErinnerungDBO erinnerungDboOut;

	@Mock
	private ErinnerungId erinnerungId;

	@Mock
	private ErinnerungDBOMapper mapper;

	@Mock
	private ErinnerungDBORepository repository;

	@Mock
	private WiederholungService wiederholungService;

	@InjectMocks
	private ErinnerungJPAPersistenceAdapter unitUnderTest;

	@Nested
	class findAllOrderedByNaechsterTerminAsc {

		@Test
		void returnsACorrectList() {
			// Prepare
			List<Erinnerung> expected = List.of(erinnerungOut);
			when(mapper.toModel(erinnerungDboOut, wiederholungService)).thenReturn(erinnerungOut);
			when(repository.findAll()).thenReturn(List.of(erinnerungDboOut));
			// Run
			List<Erinnerung> returned = unitUnderTest.findAllOrderedByNaechsterTerminAsc();
			// Check
			assertEquals(expected, returned);
		}

		@Test
		void returnsACorrectlyOrderedList() {
			// Prepare
			LocalDate date = LocalDate.now();
			Erinnerung e0 = new Erinnerung().setNaechsterTermin(date).setName("B");
			Erinnerung e1 = new Erinnerung().setNaechsterTermin(date).setName("A");
			Erinnerung e2 = new Erinnerung().setNaechsterTermin(date.minusDays(1)).setName("C");
			List<Erinnerung> expected = List.of(e2, e1, e0);
			when(mapper.toModel(erinnerungDboOut, wiederholungService)).thenReturn(e0, e1, e2);
			when(repository.findAll()).thenReturn(List.of(erinnerungDboOut, erinnerungDboOut, erinnerungDboOut));
			// Run
			List<Erinnerung> returned = unitUnderTest.findAllOrderedByNaechsterTerminAsc();
			// Check
			assertEquals(expected, returned);
		}
	}

	@Nested
	class findById_ErinnerungId {

		@Test
		void throwsAnException_passingANullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.findById(null));
		}

		@Test
		void returnsTheOptionalReturnedByTheRepositoryMethod() {
			// Prepare
			when(erinnerungId.getUuid()).thenReturn(UID);
			when(mapper.toModel(erinnerungDboOut, wiederholungService)).thenReturn(erinnerungOut);
			when(repository.findById(UID)).thenReturn(Optional.of(erinnerungDboOut));
			// Run
			Optional<Erinnerung> returned = unitUnderTest.findById(erinnerungId);
			// Check
			assertEquals(Optional.of(erinnerungOut), returned);
		}
	}

	@Nested
	class findIdsByNameContains_String {

		@Test
		void throwsAnException_passingANullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.findIdsByNameContains(null));
		}

		@Test
		void returnsTheOptionalReturnedByTheRepositoryMethod() {
			// Prepare
			when(erinnerungDboOut.getId()).thenReturn(UID);
			when(repository.findIdsByNameContains(SUCHSTRING)).thenReturn(List.of(erinnerungDboOut));
			// Run
			List<ErinnerungId> returned = unitUnderTest.findIdsByNameContains(SUCHSTRING);
			// Check
			assertEquals(UID, returned.get(0).getUuid());
		}
	}

	@Nested
	class remove_ErinnerungId {

		@Test
		void throwsAnException_passingANullValueAsErinnerunId() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.remove(null));
		}

		@Test
		void callsTheRepositoryDeleteMethod_withPassedErinnerungId() {
			// Prepare
			when(erinnerungId.getUuid()).thenReturn(UID);
			// Run
			unitUnderTest.remove(erinnerungId);
			// Check
			verify(repository, times(1)).deleteById(UID);
		}
	}

	@Nested
	class save_Erinnerung {

		@Test
		void throwsAnException_passingANullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.save(null));
		}

		@Test
		void returnsTheCorrectModel_passingAModel() {
			// Prepare
			when(mapper.toDbo(erinnerungIn)).thenReturn(erinnerungDboIn);
			when(mapper.toModel(erinnerungDboOut, wiederholungService)).thenReturn(erinnerungOut);
			when(repository.save(erinnerungDboIn)).thenReturn(erinnerungDboOut);
			// Run
			Erinnerung returned = unitUnderTest.save(erinnerungIn);
			// Check
			assertEquals(erinnerungOut, returned);
		}
	}
}
