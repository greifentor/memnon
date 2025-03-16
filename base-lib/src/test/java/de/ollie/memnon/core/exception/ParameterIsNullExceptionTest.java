package de.ollie.memnon.core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.ollie.memnon.core.exception.ParameterIsNullException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ParameterIsNullExceptionTest {

	private static final String ENTITY_NAME = "Entity-Name";
	private static final String ATTRIBUTE_NAME = "Attribute-Name";

	private ParameterIsNullException unitUnderTest;

	@BeforeEach
	void beforeEach() {
		unitUnderTest = new ParameterIsNullException(ENTITY_NAME, ATTRIBUTE_NAME);
	}

	@Nested
	class TestsOfConstructor_string_Throwable_String_String {

		@Test
		void setsTheCauseCorrectly() {
			assertNull(unitUnderTest.getCause());
		}

		@Test
		void setsTheMessageCorrectly() {
			assertEquals(ParameterIsNullException.MESSAGE, unitUnderTest.getMessage());
		}

		@Test
		void setsTheMessageDataCorrectly_withColumnNames() {
			assertEquals(
				Map.of(
					ParameterIsNullException.PARAMETER_ENTITY_NAME,
					ENTITY_NAME,
					ParameterIsNullException.PARAMETER_ATTRIBUTE_NAME,
					ATTRIBUTE_NAME
				),
				unitUnderTest.getMessageData()
			);
		}

		@Test
		void setsTheMessageDataCorrectly_withOneColumnNameOnly() {
			assertEquals(
				Map.of(
					ParameterIsNullException.PARAMETER_ENTITY_NAME,
					ENTITY_NAME,
					ParameterIsNullException.PARAMETER_ATTRIBUTE_NAME,
					ATTRIBUTE_NAME
				),
				unitUnderTest.getMessageData()
			);
		}

		@Test
		void setsTheMessageIdCorrectly() {
			assertEquals(ParameterIsNullException.MESSAGE_ID, unitUnderTest.getMessageId());
		}
	}
}
