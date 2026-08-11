package de.ollie.memnon.gui.swing;

import de.ollie.memnon.core.model.ErinnerungId;
import de.ollie.memnon.core.model.Wiederholung;
import de.ollie.memnon.core.service.ErinnerungService;
import de.ollie.memnon.core.service.WiederholungService;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ErinnerungAnlegenDialog extends JDialog {

	static final String KEINE_WIEDERHOLUNG = "(keine)";

	private static final String DATUMSFORMAT_HINWEIS = "Datum bitte im Format JJJJ-MM-TT angeben.";

	private final transient ErinnerungService erinnerungService;
	private final transient WiederholungService wiederholungService;

	private final JTextField textFieldName = new JTextField(20);
	private final JTextField textFieldErsterTermin = new JTextField(20);
	private final JTextField textFieldBezugsdatum = new JTextField(20);
	private final JComboBox<String> comboBoxWiederholung;

	private transient ErinnerungId erzeugteErinnerungId;

	public ErinnerungAnlegenDialog(
		Frame owner,
		ErinnerungService erinnerungService,
		WiederholungService wiederholungService,
		GUIConfiguration guiConfiguration
	) {
		super(owner, "Neue Erinnerung", true);
		this.erinnerungService = erinnerungService;
		this.wiederholungService = wiederholungService;
		comboBoxWiederholung = new JComboBox<>(ermittleWiederholungNamen());
		JPanel mainPanel = new JPanel(
			new BorderLayout(guiConfiguration.getHorizontalGap(), guiConfiguration.getVerticalGap())
		);
		mainPanel.setBorder(
			BorderFactory.createEmptyBorder(
				guiConfiguration.getVerticalGap(),
				guiConfiguration.getHorizontalGap(),
				guiConfiguration.getVerticalGap(),
				guiConfiguration.getHorizontalGap()
			)
		);
		mainPanel.add(createEingabePanel(guiConfiguration), BorderLayout.CENTER);
		mainPanel.add(createButtonPanel(guiConfiguration), BorderLayout.SOUTH);
		setContentPane(mainPanel);
		pack();
		setLocationRelativeTo(owner);
	}

	private String[] ermittleWiederholungNamen() {
		List<String> namen = new ArrayList<>();
		namen.add(KEINE_WIEDERHOLUNG);
		wiederholungService.holeAlleWiederholungenAufsteigendSortiertNachName().forEach(w -> namen.add(w.getName()));
		return namen.toArray(new String[0]);
	}

	private JPanel createEingabePanel(GUIConfiguration guiConfiguration) {
		JPanel eingabePanel = new JPanel(
			new GridLayout(4, 2, guiConfiguration.getHorizontalGap(), guiConfiguration.getVerticalGap())
		);
		eingabePanel.add(new JLabel("Name"));
		eingabePanel.add(textFieldName);
		eingabePanel.add(new JLabel("Erster Termin (JJJJ-MM-TT)"));
		eingabePanel.add(textFieldErsterTermin);
		eingabePanel.add(new JLabel("Bezugsdatum (JJJJ-MM-TT)"));
		eingabePanel.add(textFieldBezugsdatum);
		eingabePanel.add(new JLabel("Wiederholung"));
		eingabePanel.add(comboBoxWiederholung);
		return eingabePanel;
	}

	private JPanel createButtonPanel(GUIConfiguration guiConfiguration) {
		JPanel buttonPanel = new JPanel(
			new FlowLayout(FlowLayout.RIGHT, guiConfiguration.getHorizontalGap(), guiConfiguration.getVerticalGap())
		);
		JButton anlegenButton = new JButton("Anlegen");
		anlegenButton.addActionListener(e -> erzeugeErinnerung());
		buttonPanel.add(anlegenButton);
		JButton abbrechenButton = new JButton("Abbrechen");
		abbrechenButton.addActionListener(e -> dispose());
		buttonPanel.add(abbrechenButton);
		getRootPane().setDefaultButton(anlegenButton);
		return buttonPanel;
	}

	private void erzeugeErinnerung() {
		String name = textFieldName.getText().trim();
		if (name.isEmpty()) {
			zeigeFehler("Bitte einen Namen angeben.");
			return;
		}
		LocalDate ersterTermin;
		LocalDate bezugsdatum;
		try {
			ersterTermin = leseDatum(textFieldErsterTermin);
			bezugsdatum = leseDatum(textFieldBezugsdatum);
		} catch (DateTimeParseException e) {
			zeigeFehler(DATUMSFORMAT_HINWEIS);
			return;
		}
		if (ersterTermin == null) {
			zeigeFehler("Bitte einen ersten Termin angeben.");
			return;
		}
		erzeugteErinnerungId =
			erinnerungService.erzeugeErinnerung(
				name,
				ersterTermin,
				ermittleAusgewaehlteWiederholung(),
				bezugsdatum != null ? bezugsdatum : ersterTermin
			);
		dispose();
	}

	private LocalDate leseDatum(JTextField textField) {
		String wert = textField.getText().trim();
		return wert.isEmpty() ? null : LocalDate.parse(wert);
	}

	private Wiederholung ermittleAusgewaehlteWiederholung() {
		String name = (String) comboBoxWiederholung.getSelectedItem();
		return KEINE_WIEDERHOLUNG.equals(name) ? null : wiederholungService.holeWiederholungMitNamen(name).orElse(null);
	}

	private void zeigeFehler(String meldung) {
		JOptionPane.showMessageDialog(this, meldung, "Eingabefehler", JOptionPane.ERROR_MESSAGE);
	}

	public Optional<ErinnerungId> getErzeugteErinnerungId() {
		return Optional.ofNullable(erzeugteErinnerungId);
	}
}
