package de.ollie.memnon.core.service;

import de.ollie.memnon.core.model.Wiederholung;
import de.ollie.memnon.core.model.WiederholungJaehrlich;
import java.util.List;
import java.util.Optional;

public interface WiederholungService {
	static final Wiederholung WIEDERHOLUNG_JAEHRLICH = new WiederholungJaehrlich();

	List<Wiederholung> holeAlleWiederholungenAufsteigendSortiertNachName();

	Optional<Wiederholung> holeWiederholungMitNamen(String name);
}
