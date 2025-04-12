package de.ollie.memnon.core.service.port.persistence;

import de.ollie.memnon.core.model.Erinnerung;
import de.ollie.memnon.core.model.ErinnerungId;
import java.util.List;
import java.util.Optional;

public interface ErinnerungPersistencePort {
	List<Erinnerung> findAllOrderedByNaechsterTerminAsc();

	Optional<Erinnerung> findById(ErinnerungId erinnerungId);

	List<ErinnerungId> findIdsByNameContains(String suchString);

	Erinnerung save(Erinnerung erinnerung);
}
