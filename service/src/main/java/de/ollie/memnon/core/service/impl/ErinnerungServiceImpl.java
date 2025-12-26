package de.ollie.memnon.core.service.impl;

import static de.ollie.memnon.util.Check.ensure;

import de.ollie.memnon.configuration.ServiceConfiguration;
import de.ollie.memnon.core.model.Erinnerung;
import de.ollie.memnon.core.model.ErinnerungId;
import de.ollie.memnon.core.model.ErinnerungStatus;
import de.ollie.memnon.core.model.Wiederholung;
import de.ollie.memnon.core.service.ErinnerungService;
import de.ollie.memnon.core.service.LocalDateService;
import de.ollie.memnon.core.service.port.connector.ExternalErinnerungConnector;
import de.ollie.memnon.core.service.port.persistence.ErinnerungPersistencePort;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor // NO_UCD
class ErinnerungServiceImpl implements ErinnerungService { // NO_UCD

	private static final String MSG_ERINNERUNG_NOT_FOUND = "erinnerung id cannot be null!";

	private final ErinnerungPersistencePort erinnerungPersistencePort;
	private final List<ExternalErinnerungConnector> externalErinnerungConnectors;
	private final LocalDateService localDateService;
	private final ServiceConfiguration serviceConfiguration;
	private final UUIDProvider uuidProvider;

	@Override
	public Optional<LocalDate> aktualisiereNaechsterTermin(ErinnerungId erinnerungId) {
		ensure(erinnerungId != null, MSG_ERINNERUNG_NOT_FOUND);
		return erinnerungPersistencePort
			.findById(erinnerungId)
			.filter(erinnerung -> erinnerung.getWiederholung() != null)
			.map(erinnerung -> {
				LocalDate newNaechsterTermin = erinnerung.berechneNaechsterTermin();
				erinnerung.setNaechsterTermin(newNaechsterTermin);
				erinnerungPersistencePort.save(erinnerung);
				return newNaechsterTermin;
			});
	}

	@Override
	public ErinnerungStatus ermittleStatus(ErinnerungId erinnerungId) {
		ensure(erinnerungId != null, MSG_ERINNERUNG_NOT_FOUND);
		Erinnerung erinnerung = erinnerungPersistencePort
			.findById(erinnerungId)
			.orElseThrow(() -> new NoSuchElementException(MSG_ERINNERUNG_NOT_FOUND));
		LocalDate now = localDateService.now();
		if (now.isAfter(erinnerung.getNaechsterTermin())) {
			return ErinnerungStatus.VERPASST;
		} else if (now.isEqual(erinnerung.getNaechsterTermin())) {
			return ErinnerungStatus.HEUTE;
		} else if (now.plusDays(1).isEqual(erinnerung.getNaechsterTermin())) {
			return ErinnerungStatus.MORGEN;
		} else if (now.plusDays(serviceConfiguration.getStatusMaxDaysNah() + 1L).isAfter(erinnerung.getNaechsterTermin())) {
			return ErinnerungStatus.NAH;
		} else if (
			now.plusDays(serviceConfiguration.getStatusMaxDaysDemaechst() + 1L).isAfter(erinnerung.getNaechsterTermin())
		) {
			return ErinnerungStatus.DEMNAECHST;
		}
		return ErinnerungStatus.ENTFERNT;
	}

	@Override
	public ErinnerungId erzeugeErinnerung(
		String name,
		LocalDate ersterTermin,
		Wiederholung wiederholung,
		LocalDate bezugsdatum
	) {
		ensure(ersterTermin != null, "erster termin cannot be null!");
		ensure(name != null, "name cannot be null!");
		return erinnerungPersistencePort
			.save(
				new Erinnerung()
					.setBezugsdatum(bezugsdatum)
					.setId(new ErinnerungId(uuidProvider.create()))
					.setNaechsterTermin(ersterTermin)
					.setName(name)
					.setWiederholung(wiederholung)
			)
			.getId();
	}

	@Override
	public List<ErinnerungId> findeAlleErinnerungIdZuSuchstring(String suchString) {
		ensure(suchString != null, "such string cannot be null!");
		return erinnerungPersistencePort.findIdsByNameContains(suchString);
	}

	@Override
	public List<Erinnerung> holeAlleErinnerungenAufsteigendSortiertNachNaechsterTermin() {
		List<Erinnerung> l = erinnerungPersistencePort.findAllOrderedByNaechsterTerminAsc();
		if (!externalErinnerungConnectors.isEmpty()) {
			for (ExternalErinnerungConnector connector : externalErinnerungConnectors) {
				l.addAll(connector.findAllErinnerungen());
			}
			// l = l.stream().sorted((e0, e1) -> e0.getNaechsterTermin().compareTo(e1.getNaechsterTermin())).toList();
		}
		return l;
	}

	@Override
	public Optional<Erinnerung> holeErinnerungZuId(ErinnerungId erinnerungId) {
		ensure(erinnerungId != null, MSG_ERINNERUNG_NOT_FOUND);
		return erinnerungPersistencePort.findById(erinnerungId);
	}

	@Override
	public void loescheErinnerung(ErinnerungId erinnerungId) {
		ensure(erinnerungId != null, MSG_ERINNERUNG_NOT_FOUND);
		erinnerungPersistencePort.remove(erinnerungId);
	}
}
