package de.ollie.memnon.shell.command;

import de.ollie.memnon.core.model.Erinnerung;
import de.ollie.memnon.core.service.ErinnerungService;
import de.ollie.memnon.core.service.OutputManager;
import jakarta.inject.Named;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor // NO_UCD
public class ListErinnerungCommand {

	private final ErinnerungService erinnerungService;
	private final OutputManager out;

	public String run() {
		List<Erinnerung> l = erinnerungService.holeAlleErinnerungenAufsteigendSortiertNachNaechsterTermin();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		l.forEach(w ->
			out.println(
				String.format(
					"%-25s %10s %10s",
					w.getName(),
					formatter.format(w.getNaechsterTermin()),
					(w.getBezugsdatum() != null ? formatter.format(w.getBezugsdatum()) : "-")
				)
			)
		);
		return l.size() + " element(s)";
	}
}
