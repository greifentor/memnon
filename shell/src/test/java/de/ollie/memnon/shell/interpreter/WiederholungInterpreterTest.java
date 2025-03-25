package de.ollie.memnon.shell.interpreter;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import de.ollie.memnon.shell.command.ListWiederholungCommand;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WiederholungInterpreterTest {

	@Mock
	private ListWiederholungCommand listWiederholungCommand;

	@InjectMocks
	private WiederholungInterpreter unitUnderTest;

	@Nested
	class list {

		@Test
		void callsTheListWiederholungCommandCorrectly() {
			// Run
			unitUnderTest.list();
			// Check
			verify(listWiederholungCommand, times(1)).run(System.out);
		}
	}
}
