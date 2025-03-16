package de.ollie.memnon.core.model;

import java.time.LocalDate;

public interface Wiederholung {
	LocalDate berechneNaechsterTermin(Erinnerung erinnerung);

	String getName();
}
