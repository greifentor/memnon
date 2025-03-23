package de.ollie.memnon.core.service;

import de.ollie.memnon.core.model.Wiederholung;
import java.util.List;
import java.util.Optional;

public interface WiederholungService {
	List<Wiederholung> holeAlleWiederholungenAufsteigendSortiertNachName();

	Optional<Wiederholung> holeWiederholungMitNamen(String name);
}
