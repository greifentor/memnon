package de.ollie.memnon.core.service.impl;

import de.ollie.memnon.core.model.Wiederholung;
import de.ollie.memnon.core.service.WiederholungService;
import jakarta.inject.Named;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Named
class WiederholungServiceImpl implements WiederholungService {

	static final Map<String, Wiederholung> WIEDERHOLUNGEN = Map.of(
		WIEDERHOLUNG_JAEHRLICH.getName(),
		WIEDERHOLUNG_JAEHRLICH
	);

	@Override
	public List<Wiederholung> holeAlleWiederholungenAufsteigendSortiertNachName() {
		return List.of(WIEDERHOLUNGEN.values().toArray(new Wiederholung[WIEDERHOLUNGEN.size()]));
	}

	@Override
	public Optional<Wiederholung> holeWiederholungMitNamen(String name) {
		return Optional.ofNullable(WIEDERHOLUNGEN.get(name));
	}
}
