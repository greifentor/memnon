package de.ollie.memnon.core.service;

import de.ollie.memnon.core.model.Erinnerung;
import de.ollie.memnon.core.model.Wiederholung;
import java.time.LocalDate;
import java.util.List;

public interface ErinnerungService {
	Erinnerung create(String name, LocalDate ersterTermin, Wiederholung wiederholung, LocalDate bezugsdatum);

	List<Erinnerung> findAllOrderedByNaechsterTerminAscending();

	Erinnerung update(Erinnerung erinnerung);

	void remove(Erinnerung erinnerung);
}
