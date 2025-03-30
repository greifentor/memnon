package de.ollie.memnon.shell.command;

import static de.ollie.memnon.util.Check.ensure;

import de.ollie.memnon.core.model.ErinnerungId;
import de.ollie.memnon.core.service.ErinnerungService;
import de.ollie.memnon.shell.OutputManager;
import jakarta.inject.Named;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor // NO_UCD
public class UpdateErinnerungCommand {

	private final ErinnerungService erinnerungService;
	private final OutputManager out;

	public String run(String searchString) {
		ensure(searchString != null, "search string cannot be null!");
		ensure(!searchString.isEmpty(), "search string cannot be empty!");
		List<ErinnerungId> ids = erinnerungService.findeAlleErinnerungIdZuSuchstring(searchString);
		ids.stream().forEach(this::aktualisiereNaechsterTermin);
		return ids.size() + " element(s) changed";
	}

	private void aktualisiereNaechsterTermin(ErinnerungId erinnerungId) {
		erinnerungService
			.aktualisiereNaechsterTermin(erinnerungId)
			.ifPresent(naechsterTermin ->
				erinnerungService
					.holeErinnerungZuId(erinnerungId)
					.ifPresentOrElse(
						erinnerung ->
							out.println(
								"changed next date to " +
								out.getDateFormatter().format(naechsterTermin) +
								" for " +
								erinnerung.getName()
							),
						() -> out.println("erinnerung not found with id: " + erinnerungId.getUuid())
					)
			);
	}
}
