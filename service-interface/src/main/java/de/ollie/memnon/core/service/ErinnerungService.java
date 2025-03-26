package de.ollie.memnon.core.service;

import de.ollie.memnon.core.model.Erinnerung;
import de.ollie.memnon.core.model.ErinnerungId;
import de.ollie.memnon.core.model.Wiederholung;
import java.time.LocalDate;
import java.util.List;

public interface ErinnerungService {
	LocalDate aktualisiereNaechsterTermin(ErinnerungId erinnerungId);

	ErinnerungId erzeugeErinnerung(String name, LocalDate ersterTermin, Wiederholung wiederholung, LocalDate bezugsdatum);

	List<Erinnerung> holeAlleErinnerungenAufsteigendSortiertNachNaechsterTermin();

	void loescheErinnerung(ErinnerungId erinnerungId);
}
