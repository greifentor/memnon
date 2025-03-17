package de.ollie.memnon.shell.interpreter;

import de.ollie.memnon.shell.command.ListWiederholungCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class WiederholungInterpreter {

	private final ListWiederholungCommand listWiederholungCommand;

	@ShellMethod(value = "Lists all Wiederholung objects", key = { "list-wiederholungen", "lw" })
	public String add() {
		return listWiederholungCommand.run();
	}
}
