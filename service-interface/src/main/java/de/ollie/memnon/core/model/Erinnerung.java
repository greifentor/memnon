package de.ollie.memnon.core.model;

import java.time.LocalDate;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class Erinnerung {

	private ErinnerungId id;
	private String name;
	private LocalDate bezugsdatum;
	private LocalDate naechsterTermin;
	private Wiederholung wiederholung;

	public LocalDate berechneNaechsterTermin() {
		return getWiederholung() != null ? wiederholung.berechneNaechsterTermin(this) : null;
	}
}
