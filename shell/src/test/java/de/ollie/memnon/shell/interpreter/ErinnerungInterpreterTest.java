package de.ollie.memnon.shell.interpreter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import de.ollie.memnon.shell.command.AddErinnerungCommand;
import de.ollie.memnon.shell.command.ListErinnerungCommand;
import de.ollie.memnon.shell.command.UpdateErinnerungCommand;
import java.time.LocalDate;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ErinnerungInterpreterTest {

	private static final String NAME = "name";
	private static final String RETURN_STRING = "return-string";
	private static final String SEARCH_STRING = "search-string";

	@Mock
	private AddErinnerungCommand addErinnerungCommand;

	@Mock
	private ListErinnerungCommand listErinnerungCommand;

	@Mock
	private UpdateErinnerungCommand updateErinnerungCommand;

	@InjectMocks
	private ErinnerungInterpreter unitUnderTest;

	@Nested
	class add_String_String_String {

		@Test
		void returnsTheCorrectString() {
			// Prepare
			String bezugsdatumString = "06.02.1998";
			String ersterTerminString = "06.02.1999";
			LocalDate bezugsdatum = LocalDate.of(1998, 2, 6);
			LocalDate ersterTermin = LocalDate.of(1999, 2, 6);
			when(addErinnerungCommand.run(NAME, ersterTermin, bezugsdatum)).thenReturn(RETURN_STRING);
			// Run
			String returned = unitUnderTest.add(NAME, ersterTerminString, bezugsdatumString);
			// Check
			assertEquals(RETURN_STRING, returned);
		}
	}

	@Nested
	class list {

		@Test
		void callsTheCorrect() {
			// Prepare
			when(unitUnderTest.list()).thenReturn(RETURN_STRING);
			// Run & Check
			assertEquals(RETURN_STRING, unitUnderTest.list());
		}
	}

	@Nested
	class update_String {

		@Test
		void throwsAnException_passingANullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.update(null));
		}

		@Test
		void throwsAnException_passingAnEmptyString() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.update(""));
		}

		@Test
		void returnsTheCorrectString() {
			// Prepare
			when(updateErinnerungCommand.run(SEARCH_STRING)).thenReturn(RETURN_STRING);
			// Run
			String returned = unitUnderTest.update(SEARCH_STRING);
			// Check
			assertEquals(RETURN_STRING, returned);
		}
	}
}
