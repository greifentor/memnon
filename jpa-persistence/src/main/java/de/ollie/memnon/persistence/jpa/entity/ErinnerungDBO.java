package de.ollie.memnon.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
@Entity(name = "Erinnerung")
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "ERINNERUNG")
public class ErinnerungDBO {

	@Id
	@Column(name = "ID", nullable = false)
	private UUID id;

	@Column(name = "BEZUGSDATUM", nullable = true)
	private LocalDate bezugsdatum;

	@Column(name = "NAECHSTER_TERMIN", nullable = false)
	private LocalDate naechsterTermin;

	@Column(name = "NAME", nullable = false, unique = true)
	private String name;

	@Column(name = "WIEDERHOLUNG_NAME")
	private String wiederholungName;
}
