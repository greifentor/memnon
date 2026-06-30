package de.ollie.memnon.core.model;

import static de.ollie.memnon.util.Check.ensure;

import java.time.LocalDate;

public class WiederholungMonatlich extends AbstractWiederholung {

	public static final String NAME = "Monatlich";

	public WiederholungMonatlich() {
		super(NAME);
	}

	@Override
	public LocalDate berechneNaechsterTermin(Erinnerung erinnerung) {
		ensure(erinnerung != null, "erinnerung cannot be null!");
		LocalDate bezugsdatum = erinnerung.getBezugsdatum();
		int monate = 0;
		LocalDate newNaechsterTermin = bezugsdatum;
		while (!newNaechsterTermin.isAfter(erinnerung.getNaechsterTermin())) {
			monate++;
			newNaechsterTermin = bezugsdatum.plusMonths(monate);
		}
		erinnerung.setNaechsterTermin(newNaechsterTermin);
		return newNaechsterTermin;
	}
}
