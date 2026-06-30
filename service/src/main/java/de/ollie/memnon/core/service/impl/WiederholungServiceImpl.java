package de.ollie.memnon.core.service.impl;

import de.ollie.memnon.core.model.Wiederholung;
import de.ollie.memnon.core.service.WiederholungService;
import jakarta.inject.Named;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Named
class WiederholungServiceImpl implements WiederholungService { // NO_UCD

	private static final Map<String, Wiederholung> WIEDERHOLUNGEN = Map.of(
		WIEDERHOLUNG_JAEHRLICH.getName(),
		WIEDERHOLUNG_JAEHRLICH,
		WIEDERHOLUNG_MONATLICH.getName(),
		WIEDERHOLUNG_MONATLICH
	);

	@Override
	public List<Wiederholung> holeAlleWiederholungenAufsteigendSortiertNachName() {
		return WIEDERHOLUNGEN.values().stream().sorted(Comparator.comparing(Wiederholung::getName)).toList();
	}

	@Override
	public Optional<Wiederholung> holeWiederholungMitNamen(String name) {
		return Optional.ofNullable(WIEDERHOLUNGEN.get(name));
	}
}
