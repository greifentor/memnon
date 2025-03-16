package de.ollie.memnon.core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.ollie.memnon.core.exception.UniqueConstraintViolationException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UniqueConstraintViolationExceptionTest {

	private static final String ATTRIBUTE_NAME_0 = "Attribute-Name-0";
	private static final String ATTRIBUTE_NAME_1 = "Attribute-Name-1";
	private static final String ENTITY_NAME = "Entity-Name";
	private static final String VALUES = "Values";

	private UniqueConstraintViolationException unitUnderTest;

	@BeforeEach
	void beforeEach() {
		unitUnderTest = new UniqueConstraintViolationException(VALUES, ENTITY_NAME, ATTRIBUTE_NAME_0, ATTRIBUTE_NAME_1);
	}

	@Nested
	class TestsOfConstructor_string_Throwable_String_StringVarArg {

		@Test
		void setsTheCauseCorrectly() {
			assertNull(unitUnderTest.getCause());
		}

		@Test
		void setsTheMessageCorrectly() {
			assertEquals(UniqueConstraintViolationException.MESSAGE, unitUnderTest.getMessage());
		}

		@Test
		void setsTheMessageDataCorrectly_withColumnNames() {
			assertEquals(
				Map.of(
					UniqueConstraintViolationException.PARAMETER_ENTITY_NAME,
					ENTITY_NAME,
					UniqueConstraintViolationException.PARAMETER_ATTRIBUTES_NAMES,
					ATTRIBUTE_NAME_0 + "," + ATTRIBUTE_NAME_1,
					UniqueConstraintViolationException.PARAMETER_VALUES_NAME,
					VALUES
				),
				unitUnderTest.getMessageData()
			);
		}

		@Test
		void setsTheMessageDataCorrectly_withOneColumnNameOnly() {
			unitUnderTest = new UniqueConstraintViolationException(VALUES, ENTITY_NAME, ATTRIBUTE_NAME_0);
			assertEquals(
				Map.of(
					UniqueConstraintViolationException.PARAMETER_ENTITY_NAME,
					ENTITY_NAME,
					UniqueConstraintViolationException.PARAMETER_ATTRIBUTES_NAMES,
					ATTRIBUTE_NAME_0,
					UniqueConstraintViolationException.PARAMETER_VALUES_NAME,
					VALUES
				),
				unitUnderTest.getMessageData()
			);
		}

		@Test
		void setsTheMessageDataCorrectly_withNoColumnNames() {
			unitUnderTest = new UniqueConstraintViolationException(VALUES, ENTITY_NAME);
			assertEquals(
				Map.of(
					UniqueConstraintViolationException.PARAMETER_ENTITY_NAME,
					ENTITY_NAME,
					UniqueConstraintViolationException.PARAMETER_ATTRIBUTES_NAMES,
					"-",
					UniqueConstraintViolationException.PARAMETER_VALUES_NAME,
					VALUES
				),
				unitUnderTest.getMessageData()
			);
		}

		@Test
		void setsTheMessageIdCorrectly() {
			assertEquals(UniqueConstraintViolationException.MESSAGE_ID, unitUnderTest.getMessageId());
		}
	}
}
