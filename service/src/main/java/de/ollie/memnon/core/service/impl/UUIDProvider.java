package de.ollie.memnon.core.service.impl;

import jakarta.inject.Named;
import java.util.UUID;

@Named
class UUIDProvider {

	UUID create() {
		return UUID.randomUUID();
	}
}
