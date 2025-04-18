package de.ollie.memnon.shell.command;

import de.ollie.memnon.core.model.Erinnerung;
import de.ollie.memnon.core.service.ErinnerungService;
import de.ollie.memnon.shell.OutputManager;
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
		l.forEach(erinnerung ->
			out.println(
				String.format(
					"%-40s %10s %10s %s",
					erinnerung.getName(),
					formatter.format(erinnerung.getNaechsterTermin()),
					(erinnerung.getBezugsdatum() != null ? formatter.format(erinnerung.getBezugsdatum()) : "-"),
					erinnerungService.ermittleStatus(erinnerung.getId())
				)
			)
		);
		return l.size() + " element(s)";
	}
}
