package de.ollie.memnon.gui.swing;

import de.ollie.memnon.core.model.Erinnerung;
import de.ollie.memnon.core.service.ErinnerungService;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.time.LocalDate;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
			int index,
			boolean isSelected,
			boolean hasCellFocus
		) {
			JLabel label = new JLabel();
			label.setFont(new Font("monospaced", Font.PLAIN, 12));
			label.setText(String.format("%-40s %10s", erinnerung.getName(), erinnerung.getNaechsterTermin()));
			if (isSelected) {
				label.setFont(new Font("monospaced", Font.BOLD, 12));
			} else {
				LocalDate now = LocalDate.now();
				if (now.isAfter(erinnerung.getNaechsterTermin())) {
					label.setForeground(Color.RED);
				} else if (now.plusDays(3).isAfter(erinnerung.getNaechsterTermin())) {
					label.setForeground(Color.YELLOW);
				} else {
					label.setForeground(Color.GREEN);
				}
			}
			return label;
		}
	}

	@RequiredArgsConstructor
	private static class ErinnerungListSelectionListener implements ListSelectionListener {

		private final JList<Erinnerung> listErinnerungen;
		private final ErinnerungService erinnerungService;

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (listErinnerungen.getSelectedValue() != null) {
				erinnerungService.aktualisiereNaechsterTermin(listErinnerungen.getSelectedValue().getId());
				listErinnerungen.setListData(
					erinnerungService.holeAlleErinnerungenAufsteigendSortiertNachNaechsterTermin().toArray(new Erinnerung[0])
				);
			}
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
		listErinnerungen.addListSelectionListener(new ErinnerungListSelectionListener(listErinnerungen, erinnerungService));
		setContentPane(new JScrollPane(listErinnerungen));
		setSize(400, 600);
		setVisible(true);
	}
}
