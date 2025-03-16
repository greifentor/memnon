package de.ollie.memnon.core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.ollie.memnon.core.exception.NoSuchRecordException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NoSuchRecordExceptionTest {

	private static final String ENTITY_NAME = "Entity-Name";
	private static final String ID_ATTRIBUTE_NAME = "Id-Attribute-Name";
	private static final String VALUE = "Value";

	private NoSuchRecordException unitUnderTest;

	@BeforeEach
	void beforeEach() {
		unitUnderTest = new NoSuchRecordException(VALUE, ENTITY_NAME, ID_ATTRIBUTE_NAME);
	}

	@Nested
	class TestsOfConstructor_string_Throwable_String_String {

		@Test
		void setsTheCauseCorrectly() {
			assertNull(unitUnderTest.getCause());
		}

		@Test
		void setsTheMessageCorrectly() {
			assertEquals(NoSuchRecordException.MESSAGE, unitUnderTest.getMessage());
		}

		@Test
		void setsTheMessageDataCorrectly_withColumnNames() {
			assertEquals(
				Map.of(
					NoSuchRecordException.PARAMETER_ENTITY_NAME,
					ENTITY_NAME,
					NoSuchRecordException.PARAMETER_ID_ATTRIBUTE_NAME,
					ID_ATTRIBUTE_NAME,
					NoSuchRecordException.PARAMETER_VALUE_NAME,
					VALUE
				),
				unitUnderTest.getMessageData()
			);
		}

		@Test
		void setsTheMessageDataCorrectly_withOneColumnNameOnly() {
			assertEquals(
				Map.of(
					NoSuchRecordException.PARAMETER_ENTITY_NAME,
					ENTITY_NAME,
					NoSuchRecordException.PARAMETER_ID_ATTRIBUTE_NAME,
					ID_ATTRIBUTE_NAME,
					NoSuchRecordException.PARAMETER_VALUE_NAME,
					VALUE
				),
				unitUnderTest.getMessageData()
			);
		}

		@Test
		void setsTheMessageIdCorrectly() {
			assertEquals(NoSuchRecordException.MESSAGE_ID, unitUnderTest.getMessageId());
		}
	}
}
