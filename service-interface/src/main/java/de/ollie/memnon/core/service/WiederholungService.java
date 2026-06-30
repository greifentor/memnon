package de.ollie.memnon.core.service;

import de.ollie.memnon.core.model.Wiederholung;
import de.ollie.memnon.core.model.WiederholungJaehrlich;
import de.ollie.memnon.core.model.WiederholungMonatlich;
import java.util.List;
import java.util.Optional;

public interface WiederholungService {
	static final Wiederholung WIEDERHOLUNG_JAEHRLICH = new WiederholungJaehrlich();
	static final Wiederholung WIEDERHOLUNG_MONATLICH = new WiederholungMonatlich();

	List<Wiederholung> holeAlleWiederholungenAufsteigendSortiertNachName();

	Optional<Wiederholung> holeWiederholungMitNamen(String name);
}
