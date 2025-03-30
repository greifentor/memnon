package de.ollie.memnon.shell;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import de.ollie.memnon.shell.OutputManagerImpl;
import java.io.PrintStream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OutputManagerImplTest {

	private static final String STRING = "string";

	@Mock
	private PrintStream out;

	@InjectMocks
	private OutputManagerImpl unitUnderTest;

	@Nested
	class println_String {

		@Test
		void callsThePrintStreamCorrectly() {
			// Run
			unitUnderTest.println(STRING);
			// Check
			verify(out, times(1)).println(STRING);
		}
	}
}
