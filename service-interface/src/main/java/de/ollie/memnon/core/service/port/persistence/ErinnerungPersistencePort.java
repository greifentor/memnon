package de.ollie.memnon.core.service.port.persistence;

import de.ollie.memnon.core.model.Erinnerung;
import java.util.List;

public interface ErinnerungPersistencePort {
	List<Erinnerung> findAllOrderedByNaechsterTerminAsc();

	Erinnerung save(Erinnerung erinnerung);
}
