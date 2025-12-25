package de.ollie.memnon.core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConnectorOperationFailedExceptionTest {

	@Nested
	class constructor_String_Exception {

		private static final Exception EXCEPTION = new RuntimeException();
		private static final String MESSAGE = "message";

		@Test
		void setsThePassedCauseCorrectly() {
			assertSame(EXCEPTION, new ConnectorOperationFailedException(MESSAGE, EXCEPTION).getCause());
		}

		@Test
		void setsThePassedMessageCorrectly() {
			assertEquals(MESSAGE, new ConnectorOperationFailedException(MESSAGE, EXCEPTION).getMessage());
		}
	}
}
