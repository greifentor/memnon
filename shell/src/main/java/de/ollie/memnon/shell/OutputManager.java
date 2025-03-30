package de.ollie.memnon.shell;

import java.time.format.DateTimeFormatter;

public interface OutputManager {
	static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	default DateTimeFormatter getDateFormatter() {
		return LOCAL_DATE_FORMATTER;
	}

	void println(String s);
}
