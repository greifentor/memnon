package de.ollie.memnon.shell.command;

import de.ollie.memnon.core.model.Wiederholung;
import de.ollie.memnon.core.service.WiederholungService;
import jakarta.inject.Named;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class ListWiederholungCommand {

	private final WiederholungService wiederHolungService;

	public String run() {
		List<Wiederholung> l = wiederHolungService.holeAlleWiederholungenAufsteigendSortiertNachName();
		l.forEach(w -> System.out.println(w.getName()));
		return l.size() + " element(s)";
	}
}
