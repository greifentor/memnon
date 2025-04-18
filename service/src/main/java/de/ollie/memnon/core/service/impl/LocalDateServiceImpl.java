package de.ollie.memnon.core.service.impl;

import de.ollie.memnon.core.service.LocalDateService;
import jakarta.inject.Named;
import java.time.LocalDate;

@Named
class LocalDateServiceImpl implements LocalDateService {

	@Override
	public LocalDate now() {
		return LocalDate.now();
	}
}
