package de.ollie.memnon.shell.interpreter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import de.ollie.memnon.shell.command.AddErinnerungCommand;
import de.ollie.memnon.shell.command.ListErinnerungCommand;
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

	@Mock
	private AddErinnerungCommand addErinnerungCommand;

	@Mock
	private ListErinnerungCommand listErinnerungCommand;

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
}
