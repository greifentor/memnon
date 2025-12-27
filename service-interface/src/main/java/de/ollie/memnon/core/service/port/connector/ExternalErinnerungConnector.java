package de.ollie.memnon.core.service.port.connector;

import de.ollie.memnon.core.model.ConnectorId;
import de.ollie.memnon.core.model.ErinnerungId;
import de.ollie.memnon.core.model.ExternalErinnerung;
import java.util.List;

/**
 * A bridge interface to connect external system data to the MemNoN data pool.
 *
 * Each implementation should have a unique id, especially, when the Erinnerung objects delivered by the bridge
 * implementation, can be confirmed.
 *
 * (24.12.2025)
 */
public interface ExternalErinnerungConnector {
	/**
	 * @return "true", when the Erinnerung objects can be confirmed. Note, that the returned id of the bridge have to be
	 *         unique in this case.
	 */
	boolean canBeConfirmed();

	/**
	 * Confirmes the Erinnerung with the passed id in the external system.
	 *
	 * @param id The id of the Erinnerung object which should be confirmed.
	 * @return "true" in case of a successful confirmation, "false" otherwise.
	 * @throws ConnectorOperationFailedException In case of an error while confirmation.
	 * @throws UnsupportedOperationException When Erinnerung objects related to the bridge implementation, can not be
	 *         confirmed.
	 */
	boolean confirm(ErinnerungId id);

	/**
	 * @return A list of external Erinnerung objects from the external system.
	 */
	List<ExternalErinnerung> findAllErinnerungen();

	/**
	 * @return The id of the bridge (different values necessary when confirmation is activated for the bridge
	 *         implementation.
	 */
	ConnectorId getId();
}
