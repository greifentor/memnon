package de.ollie.memnon.core.model;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class Erinnerung {

	private UUID id;
	private String name;
	private LocalDate bezugsdatum;
	private LocalDate naechsterTermin;
	private Wiederholung wiederholung;
}
