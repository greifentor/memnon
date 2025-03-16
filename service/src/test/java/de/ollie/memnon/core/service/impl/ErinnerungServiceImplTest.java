package de.ollie.memnon.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import de.ollie.memnon.core.model.Erinnerung;
import de.ollie.memnon.core.service.port.persistence.ErinnerungPersistencePort;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ErinnerungServiceImplTest {

	@Mock
	private Erinnerung erinnerungPassed;

	@Mock
	private ErinnerungPersistencePort erinnerungPersistencePort;

	@Mock
	private Erinnerung erinnerungReturned;

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
}
