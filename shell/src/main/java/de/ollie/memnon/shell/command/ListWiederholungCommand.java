package de.ollie.memnon.shell.command;

import static de.ollie.memnon.util.Check.ensure;

import de.ollie.memnon.core.model.Wiederholung;
import de.ollie.memnon.core.service.WiederholungService;
import jakarta.inject.Named;
import java.io.PrintStream;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class ListWiederholungCommand {

	private final WiederholungService wiederHolungService;

	public String run(PrintStream out) {
		ensure(out != null, "out cannot be null!");
		List<Wiederholung> l = wiederHolungService.holeAlleWiederholungenAufsteigendSortiertNachName();
		l.forEach(w -> out.println(w.getName()));
		return l.size() + " element(s)";
	}
}
