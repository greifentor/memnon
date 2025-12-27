package de.ollie.memnon.shell.interpreter;

import static de.ollie.memnon.util.Check.ensure;

import de.ollie.memnon.shell.command.AddErinnerungCommand;
import de.ollie.memnon.shell.command.ListErinnerungCommand;
import de.ollie.memnon.shell.command.RemoveErinnerungCommand;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor // NO_UCD
public class ErinnerungInterpreter { // NO_UCD

	private final AddErinnerungCommand addErinnerungCommand;
	private final ListErinnerungCommand listErinnerungCommand;
	private final RemoveErinnerungCommand removeErinnerungCommand;

	@ShellMethod(value = "Add a new erinnerung", key = { "add-erinnerung", "ae" })
	public String add(
		@ShellOption(help = "Name of the erinnerung.", value = "name") String name,
		@ShellOption(help = "First date of the erinnerung.", value = "ersterTermin") String ersterTermin,
		@ShellOption(
			help = "A bezugsdatum for the erinnerung.",
			value = "bezugsdatum",
			defaultValue = "null"
		) String bezugsdatum
	) {
		return addErinnerungCommand.run(
			name,
			LocalDate.parse(ersterTermin, DateTimeFormatter.ofPattern("dd.MM.yyyy")),
			LocalDate.parse(bezugsdatum, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
		);
	}

	@ShellMethod(value = "Lists all stored erinnerungen", key = { "list-erinnerung", "le" })
	public String list() {
		return listErinnerungCommand.run();
	}

	@ShellMethod(value = "Removes an erinnerung", key = { "remove-erinnerung", "re" })
	public String remove(
		@ShellOption(
			help = "Identifying part of the name of the erinnerung to update.",
			value = "searchName"
		) String searchName
	) {
		ensure(searchName != null, "search name cannot be null!");
		ensure(!searchName.isEmpty(), "search name cannot be empty!");
		return removeErinnerungCommand.run(searchName);
	}
}
