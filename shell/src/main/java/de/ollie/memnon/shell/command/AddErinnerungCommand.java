package de.ollie.memnon.shell.command;

import de.ollie.memnon.core.model.Erinnerung;
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
		Wiederholung wiederholung = wiederholungService.holeWiederholungMitNamen(WiederholungJaehrlich.NAME).orElse(null);
		Erinnerung erzeugteErinnerung = erinnerungService.erzeugeErinnerung(name, ersterTermin, wiederholung, bezugsdatum);
		return (
			"Erinnerung (" +
			erzeugteErinnerung.getName() +
			" - " +
			erzeugteErinnerung.getNaechsterTermin() +
			" - " +
			(erzeugteErinnerung.getWiederholung() != null ? erzeugteErinnerung.getWiederholung().getName() : "n/a") +
			")"
		);
	}
}
