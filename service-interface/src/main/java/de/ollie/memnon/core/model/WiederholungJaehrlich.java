package de.ollie.memnon.core.model;

import static de.ollie.memnon.util.Check.ensure;

import java.time.LocalDate;

public class WiederholungJaehrlich extends AbstractWiederholung {

	public static final String NAME = "Jährlich";

	public WiederholungJaehrlich() {
		super(NAME);
	}

	@Override
	public LocalDate berechneNaechsterTermin(Erinnerung erinnerung) {
		ensure(erinnerung != null, "erinnerung cannot be null!");
		LocalDate newNaechsterTermin = erinnerung.getNaechsterTermin().plusYears(1);
		erinnerung.setNaechsterTermin(newNaechsterTermin);
		return newNaechsterTermin;
	}
}
