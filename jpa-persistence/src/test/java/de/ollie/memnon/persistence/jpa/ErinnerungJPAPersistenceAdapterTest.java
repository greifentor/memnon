package de.ollie.memnon.persistence.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import de.ollie.memnon.core.model.Erinnerung;
import de.ollie.memnon.core.service.WiederholungService;
import de.ollie.memnon.persistence.jpa.entity.ErinnerungDBO;
import de.ollie.memnon.persistence.jpa.mapper.ErinnerungDBOMapper;
import de.ollie.memnon.persistence.jpa.repository.ErinnerungDBORepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ErinnerungJPAPersistenceAdapterTest {

	@Mock
	private Erinnerung erinnerungIn;

	@Mock
	private Erinnerung erinnerungOut;

	@Mock
	private ErinnerungDBO erinnerungDboIn;

	@Mock
	private ErinnerungDBO erinnerungDboOut;

	@Mock
	private ErinnerungDBOMapper mapper;

	@Mock
	private ErinnerungDBORepository repository;

	@Mock
	private WiederholungService wiederholungService;

	@InjectMocks
	private ErinnerungJPAPersistenceAdapter unitUnderTest;

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
