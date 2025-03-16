package de.ollie.memnon.core.service;

import de.ollie.memnon.core.model.Erinnerung;
import de.ollie.memnon.core.model.Wiederholung;
import java.time.LocalDate;
import java.util.List;

public interface ErinnerungService {
	Erinnerung aktualsiereErinnerung(Erinnerung erinnerung);

	Erinnerung aktualisiereNaechsterTermin(Erinnerung erinnerung);

	Erinnerung erzeugeErinnerung(String name, LocalDate ersterTermin, Wiederholung wiederholung, LocalDate bezugsdatum);

	List<Erinnerung> holeAlleErinnerungenAufsteigendSortiertNachNaechsterTermin();

	void loescheErinnerung(Erinnerung erinnerung);
}
