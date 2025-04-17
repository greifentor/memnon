package de.ollie.memnon.persistence.jpa;

import static de.ollie.memnon.util.Check.ensure;

import de.ollie.memnon.core.model.Erinnerung;
import de.ollie.memnon.core.model.ErinnerungId;
import de.ollie.memnon.core.service.WiederholungService;
import de.ollie.memnon.core.service.port.persistence.ErinnerungPersistencePort;
import de.ollie.memnon.persistence.jpa.mapper.ErinnerungDBOMapper;
import de.ollie.memnon.persistence.jpa.repository.ErinnerungDBORepository;
import jakarta.inject.Named;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor // NO_UCD
public class ErinnerungJPAPersistenceAdapter implements ErinnerungPersistencePort { // NO_UCD

	private final ErinnerungDBOMapper mapper;
	private final ErinnerungDBORepository repository;
	private final WiederholungService wiederholungService;

	@Override
	public Erinnerung save(Erinnerung erinnerung) {
		ensure(erinnerung != null, "erinnerung cannot be null!");
		return mapper.toModel(repository.save(mapper.toDbo(erinnerung)), wiederholungService);
	}

	@Override
	public List<Erinnerung> findAllOrderedByNaechsterTerminAsc() {
		return repository
			.findAll()
			.stream()
			.map(dbo -> mapper.toModel(dbo, wiederholungService))
			.sorted(this::compareErinnerungTo)
			.toList();
	}

	private int compareErinnerungTo(Erinnerung e0, Erinnerung e1) {
		int r = e0.getNaechsterTermin().compareTo(e1.getNaechsterTermin());
		if (r == 0) {
			r = e0.getName().compareTo(e1.getName());
		}
		return r;
	}

	@Override
	public Optional<Erinnerung> findById(ErinnerungId erinnerungId) {
		ensure(erinnerungId != null, "erinnerung id cannot be null!");
		return repository.findById(erinnerungId.getUuid()).map(e -> mapper.toModel(e, wiederholungService));
	}

	@Override
	public List<ErinnerungId> findIdsByNameContains(String suchString) {
		ensure(suchString != null, "such string cannot be null!");
		return repository.findIdsByNameContains(suchString).stream().map(dbo -> new ErinnerungId(dbo.getId())).toList();
	}

	@Override
	public void remove(ErinnerungId erinnerungId) {
		ensure(erinnerungId != null, "erinnerung id cannot be null!");
		repository.deleteById(erinnerungId.getUuid());
	}
}
