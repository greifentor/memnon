package de.ollie.memnon.core.service.impl;

import de.ollie.memnon.core.service.OutputManager;
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
