package de.ollie.memnon.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LocalDateServiceImplTest {

	@InjectMocks
	private LocalDateServiceImpl unitUnderTest;

	@Nested
	class now {

		@Test
		void returnsAnObject() {
			assertNotNull(unitUnderTest.now());
		}

		@Test
		void returnsANewObject_onEachCall() {
			assertNotSame(unitUnderTest.now(), unitUnderTest.now());
		}
	}
}
