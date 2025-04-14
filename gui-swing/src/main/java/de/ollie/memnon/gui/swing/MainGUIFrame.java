package de.ollie.memnon.gui.swing;

import de.ollie.memnon.core.model.Erinnerung;
import de.ollie.memnon.core.service.ErinnerungService;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class MainGUIFrame extends JFrame {

	private final ErinnerungService erinnerungService;

	private static class ErinnerungListCellRenderer implements ListCellRenderer<Erinnerung> {

		@Override
		public Component getListCellRendererComponent(
			JList<? extends Erinnerung> jList,
			Erinnerung erinnerung,
			int arg2,
			boolean arg3,
			boolean arg4
		) {
			JLabel label = new JLabel();
			label.setText(String.format("%-40s %10s", erinnerung.getName(), erinnerung.getNaechsterTermin()));
			return label;
		}
	}

	@PostConstruct
	void postConstruct() {
		setTitle("MemNoN");
		JList<Erinnerung> listErinnerungen = new JList<>();
		listErinnerungen.setListData(
			erinnerungService.holeAlleErinnerungenAufsteigendSortiertNachNaechsterTermin().toArray(new Erinnerung[0])
		);
		listErinnerungen.setCellRenderer(new ErinnerungListCellRenderer());
		setContentPane(listErinnerungen);
		setSize(200, 600);
		setVisible(true);
	}
}
