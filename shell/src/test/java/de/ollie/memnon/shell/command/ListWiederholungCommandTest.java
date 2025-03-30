package de.ollie.memnon.shell.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import de.ollie.memnon.core.model.Wiederholung;
import de.ollie.memnon.core.service.WiederholungService;
import de.ollie.memnon.shell.OutputManager;
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
	private OutputManager outputManager;

	@Mock
	private WiederholungService wiederHolungService;

	@InjectMocks
	private ListWiederholungCommand unitUnderTest;

	@Nested
	class run_PrintStream {

		@Test
		void returnsTheCorrectString() {
			// Prepare
			List<Wiederholung> l = List.of(wiederholung0, wiederholung1);
			String expected = l.size() + " element(s)";
			when(wiederholung0.getName()).thenReturn(NAME_0);
			when(wiederholung1.getName()).thenReturn(NAME_1);
			when(wiederHolungService.holeAlleWiederholungenAufsteigendSortiertNachName()).thenReturn(l);
			// Run
			String returned = unitUnderTest.run();
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
			unitUnderTest.run();
			// Check
			InOrder inOrder = Mockito.inOrder(outputManager);
			inOrder.verify(outputManager).println(NAME_0);
			inOrder.verify(outputManager).println(NAME_1);
		}
	}
}
