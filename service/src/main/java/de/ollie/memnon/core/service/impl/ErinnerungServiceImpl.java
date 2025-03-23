package de.ollie.memnon.core.service.impl;

import static de.ollie.memnon.util.Check.ensure;

import de.ollie.memnon.core.model.Erinnerung;
import de.ollie.memnon.core.model.Wiederholung;
import de.ollie.memnon.core.service.ErinnerungService;
import de.ollie.memnon.core.service.port.persistence.ErinnerungPersistencePort;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class ErinnerungServiceImpl implements ErinnerungService {

	private final ErinnerungPersistencePort erinnerungPersistencePort;
	private final UUIDProvider uuidProvider;

	@Override
	public Erinnerung aktualsiereErinnerung(Erinnerung erinnerung) {
		ensure(erinnerung != null, "erinnerung cannot be null!");
		return erinnerungPersistencePort.save(erinnerung);
	}

	@Override
	public Erinnerung aktualisiereNaechsterTermin(Erinnerung erinnerung) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Erinnerung erzeugeErinnerung(
		String name,
		LocalDate ersterTermin,
		Wiederholung wiederholung,
		LocalDate bezugsdatum
	) {
		ensure(ersterTermin != null, "erster termin cannot be null!");
		ensure(name != null, "name cannot be null!");
		return erinnerungPersistencePort.save(
			new Erinnerung()
				.setBezugsdatum(bezugsdatum)
				.setId(uuidProvider.create())
				.setNaechsterTermin(ersterTermin)
				.setName(name)
				.setWiederholung(wiederholung)
		);
	}

	@Override
	public List<Erinnerung> holeAlleErinnerungenAufsteigendSortiertNachNaechsterTermin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loescheErinnerung(Erinnerung erinnerung) {
		// TODO Auto-generated method stub
	}
}
