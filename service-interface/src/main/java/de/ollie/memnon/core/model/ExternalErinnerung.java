package de.ollie.memnon.core.model;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class ExternalErinnerung extends Erinnerung {

	private BridgeId bridgeId;
}
