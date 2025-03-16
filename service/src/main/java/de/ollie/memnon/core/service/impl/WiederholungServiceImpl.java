package de.ollie.memnon.core.service.impl;

import de.ollie.memnon.core.model.Wiederholung;
import de.ollie.memnon.core.model.WiederholungJaehrlich;
import de.ollie.memnon.core.service.WiederholungService;
import jakarta.inject.Named;
import java.util.List;

@Named
class WiederholungServiceImpl implements WiederholungService {

	@Override
	public List<Wiederholung> holeAlleWiederholungenAufsteigendSortiertNachName() {
		return List.of(new WiederholungJaehrlich());
	}
}
