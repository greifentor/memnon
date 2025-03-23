package de.ollie.memnon.persistence.jpa;

import static de.ollie.memnon.util.Check.ensure;

import de.ollie.memnon.core.model.Erinnerung;
import de.ollie.memnon.core.service.WiederholungService;
import de.ollie.memnon.core.service.port.persistence.ErinnerungPersistencePort;
import de.ollie.memnon.persistence.jpa.mapper.ErinnerungDBOMapper;
import de.ollie.memnon.persistence.jpa.repository.ErinnerungDBORepository;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class ErinnerungJPAPersistenceAdapter implements ErinnerungPersistencePort {

	private final ErinnerungDBOMapper mapper;
	private final ErinnerungDBORepository repository;
	private final WiederholungService wiederholungService;

	@Override
	public Erinnerung save(Erinnerung erinnerung) {
		ensure(erinnerung != null, "erinnerung cannot be null!");
		return mapper.toModel(repository.save(mapper.toDbo(erinnerung)), wiederholungService);
	}
}
