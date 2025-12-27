package de.ollie.memnon.core.service;

import de.ollie.memnon.core.model.Erinnerung;
import de.ollie.memnon.core.model.ErinnerungId;
import de.ollie.memnon.core.model.ErinnerungStatus;
import de.ollie.memnon.core.model.Wiederholung;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ErinnerungService {
	Optional<LocalDate> aktualisiereNaechsterTermin(Erinnerung erinnerung);

	ErinnerungStatus ermittleStatus(Erinnerung erinnerung);

	ErinnerungId erzeugeErinnerung(String name, LocalDate ersterTermin, Wiederholung wiederholung, LocalDate bezugsdatum);

	List<ErinnerungId> findeAlleErinnerungIdZuSuchstring(String suchString);

	List<Erinnerung> holeAlleErinnerungenAufsteigendSortiertNachNaechsterTermin();

	Optional<Erinnerung> holeErinnerungZuId(ErinnerungId erinnerungId);

	void loescheErinnerung(ErinnerungId erinnerungId);
}
