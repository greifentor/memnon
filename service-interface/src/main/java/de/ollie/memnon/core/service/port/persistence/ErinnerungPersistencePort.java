package de.ollie.memnon.core.service.port.persistence;

import de.ollie.memnon.core.model.Erinnerung;

public interface ErinnerungPersistencePort {
	Erinnerung save(Erinnerung erinnerung);
}
