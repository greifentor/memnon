package de.ollie.memnon.shell.command;

import static de.ollie.memnon.util.Check.ensure;

import de.ollie.memnon.core.model.ErinnerungId;
import de.ollie.memnon.core.model.Wiederholung;
import de.ollie.memnon.core.model.WiederholungJaehrlich;
import de.ollie.memnon.core.service.ErinnerungService;
import de.ollie.memnon.core.service.WiederholungService;
import jakarta.inject.Named;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class AddErinnerungCommand {

	private final ErinnerungService erinnerungService;
	private final WiederholungService wiederholungService;

	public String run(String name, LocalDate ersterTermin, LocalDate bezugsdatum) {
		ensure(ersterTermin != null, "erster termin cannot be null!");
		ensure(name != null, "name cannot be null!");
		Wiederholung wiederholung = wiederholungService.holeWiederholungMitNamen(WiederholungJaehrlich.NAME).orElse(null);
		ErinnerungId erzeugteErinnerungId = erinnerungService.erzeugeErinnerung(
			name,
			ersterTermin,
			wiederholung,
			bezugsdatum
		);
		return ("Erinnerung (" + erzeugteErinnerungId.getUuid() + ")");
	}
}
