package de.ollie.memnon.core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.ollie.memnon.core.exception.ParameterIsBlankException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ParameterIsBlankExceptionTest {

	private static final String ENTITY_NAME = "Entity-Name";
	private static final String ATTRIBUTE_NAME = "Attribute-Name";

	private ParameterIsBlankException unitUnderTest;

	@BeforeEach
	void beforeEach() {
		unitUnderTest = new ParameterIsBlankException(ENTITY_NAME, ATTRIBUTE_NAME);
	}

	@Nested
	class TestsOfConstructor_string_Throwable_String_String {

		@Test
		void setsTheCauseCorrectly() {
			assertNull(unitUnderTest.getCause());
		}

		@Test
		void setsTheMessageCorrectly() {
			assertEquals(ParameterIsBlankException.MESSAGE, unitUnderTest.getMessage());
		}

		@Test
		void setsTheMessageDataCorrectly_withColumnNames() {
			assertEquals(
				Map.of(
					ParameterIsBlankException.PARAMETER_ENTITY_NAME,
					ENTITY_NAME,
					ParameterIsBlankException.PARAMETER_ATTRIBUTE_NAME,
					ATTRIBUTE_NAME
				),
				unitUnderTest.getMessageData()
			);
		}

		@Test
		void setsTheMessageDataCorrectly_withOneColumnNameOnly() {
			assertEquals(
				Map.of(
					ParameterIsBlankException.PARAMETER_ENTITY_NAME,
					ENTITY_NAME,
					ParameterIsBlankException.PARAMETER_ATTRIBUTE_NAME,
					ATTRIBUTE_NAME
				),
				unitUnderTest.getMessageData()
			);
		}

		@Test
		void setsTheMessageIdCorrectly() {
			assertEquals(ParameterIsBlankException.MESSAGE_ID, unitUnderTest.getMessageId());
		}
	}
}
