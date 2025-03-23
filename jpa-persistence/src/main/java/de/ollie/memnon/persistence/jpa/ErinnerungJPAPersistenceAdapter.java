package de.ollie.memnon.persistence.jpa;

import de.ollie.memnon.core.model.Erinnerung;
import de.ollie.memnon.core.service.WiederholungService;
import de.ollie.memnon.core.service.port.persistence.ErinnerungPersistencePort;
import de.ollie.memnon.persistence.jpa.entity.ErinnerungDBO;
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
		System.out.println(mapper.toDbo(erinnerung));
		ErinnerungDBO dbo = repository.save(mapper.toDbo(erinnerung));
		System.out.println(dbo);
		System.out.println(mapper.toModel(dbo, wiederholungService));
		return mapper.toModel(repository.save(mapper.toDbo(erinnerung)), wiederholungService);
	}
}
