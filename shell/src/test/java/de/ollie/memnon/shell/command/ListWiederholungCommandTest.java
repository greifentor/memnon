package de.ollie.memnon.shell.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import de.ollie.memnon.core.model.Wiederholung;
import de.ollie.memnon.core.service.WiederholungService;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ListWiederholungCommandTest {

	private static final String NAME_0 = "name-0";
	private static final String NAME_1 = "name-1";

	@Mock
	private Wiederholung wiederholung0;

	@Mock
	private Wiederholung wiederholung1;

	@Mock
	private PrintStream out;

	@Mock
	private WiederholungService wiederHolungService;

	@InjectMocks
	private ListWiederholungCommand unitUnderTest;

	@Nested
	class run_PrintStream {

		@Test
		void throwsAnException_passingANullValueAsOut() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.run(null));
		}

		@Test
		void returnsTheCorrectString() {
			// Prepare
			List<Wiederholung> l = List.of(wiederholung0, wiederholung1);
			String expected = l.size() + " element(s)";
			when(wiederholung0.getName()).thenReturn(NAME_0);
			when(wiederholung1.getName()).thenReturn(NAME_1);
			when(wiederHolungService.holeAlleWiederholungenAufsteigendSortiertNachName()).thenReturn(l);
			// Run
			String returned = unitUnderTest.run(out);
			// Check
			assertEquals(expected, returned);
		}

		@Test
		void anyWeiderhloungObjectIsPrintedToOut() {
			// Prepare
			List<Wiederholung> l = List.of(wiederholung0, wiederholung1);
			when(wiederholung0.getName()).thenReturn(NAME_0);
			when(wiederholung1.getName()).thenReturn(NAME_1);
			when(wiederHolungService.holeAlleWiederholungenAufsteigendSortiertNachName()).thenReturn(l);
			// Run
			unitUnderTest.run(out);
			// Check
			InOrder inOrder = Mockito.inOrder(out);
			inOrder.verify(out).println(NAME_0);
			inOrder.verify(out).println(NAME_1);
		}
	}
}
