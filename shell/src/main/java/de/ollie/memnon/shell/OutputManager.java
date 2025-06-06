package de.ollie.memnon.shell;

import jakarta.inject.Named;
import java.io.PrintStream;
import java.time.format.DateTimeFormatter;

@Named
public class OutputManager {

	static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	private PrintStream out = System.out;

	public void println(String s) {
		out.println(s);
	}

	public DateTimeFormatter getDateFormatter() {
		return LOCAL_DATE_FORMATTER;
	}
}
