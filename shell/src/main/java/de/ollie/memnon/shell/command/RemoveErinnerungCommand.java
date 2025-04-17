package de.ollie.memnon.shell.command;

import static de.ollie.memnon.util.Check.ensure;

import de.ollie.memnon.core.model.ErinnerungId;
import de.ollie.memnon.core.service.ErinnerungService;
import jakarta.inject.Named;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor // NO_UCD
public class RemoveErinnerungCommand {

	private final ErinnerungService erinnerungService;

	public String run(String searchString) {
		ensure(searchString != null, "search string cannot be null!");
		ensure(!searchString.isEmpty(), "search string cannot be empty!");
		List<ErinnerungId> ids = erinnerungService.findeAlleErinnerungIdZuSuchstring(searchString);
		ensure(
			ids.size() < 2,
			() -> new IllegalStateException("There are more than one hits for search string: " + searchString)
		);
		ensure(
			ids.size() == 1,
			() -> new NoSuchElementException("No such erinnerung found for search string: " + searchString)
		);
		erinnerungService.loescheErinnerung(ids.get(0));
		return "element removed with id: " + ids.get(0);
	}
}
