package de.ollie.memnon.shell.command;

import de.ollie.memnon.core.model.Wiederholung;
import de.ollie.memnon.core.service.WiederholungService;
import de.ollie.memnon.shell.OutputManager;
import jakarta.inject.Named;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor // NO_UCD
public class ListWiederholungCommand {

	private final OutputManager outputManager;
	private final WiederholungService wiederHolungService;

	public String run() {
		List<Wiederholung> l = wiederHolungService.holeAlleWiederholungenAufsteigendSortiertNachName();
		l.forEach(w -> outputManager.println(w.getName()));
		return l.size() + " element(s)";
	}
}
