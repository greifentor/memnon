package de.ollie.memnon.shell;

import jakarta.inject.Named;
import java.io.PrintStream;

@Named
public class OutputManagerImpl implements OutputManager { // NO_UCD

	private PrintStream out = System.out;

	@Override
	public void println(String s) {
		out.println(s);
	}
}
