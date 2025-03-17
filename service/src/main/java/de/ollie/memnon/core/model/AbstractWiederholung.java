package de.ollie.memnon.core.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
abstract class AbstractWiederholung implements Wiederholung {

	private final String name;
}
