package de.ollie.memnon.core.model;

import static de.ollie.memnon.util.Check.ensure;

import java.time.LocalDate;

public class WiederholungWoechentlich extends AbstractWiederholung {

	public static final String NAME = "Wöchentlich";

	public WiederholungWoechentlich() {
		super(NAME);
	}

	@Override
	public LocalDate berechneNaechsterTermin(Erinnerung erinnerung) {
		ensure(erinnerung != null, "erinnerung cannot be null!");
		LocalDate newNaechsterTermin = erinnerung.getBezugsdatum();
		while (!newNaechsterTermin.isAfter(erinnerung.getNaechsterTermin())) {
			newNaechsterTermin = newNaechsterTermin.plusDays(7);
		}
		erinnerung.setNaechsterTermin(newNaechsterTermin);
		return newNaechsterTermin;
	}
}
