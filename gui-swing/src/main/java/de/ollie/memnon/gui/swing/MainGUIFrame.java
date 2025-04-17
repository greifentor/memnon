package de.ollie.memnon.gui.swing;

import de.ollie.memnon.core.model.Erinnerung;
import de.ollie.memnon.core.service.ErinnerungService;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

@Named
@RequiredArgsConstructor
public class MainGUIFrame extends JFrame {

	private final ApplicationContext context;
	private final ErinnerungService erinnerungService;
	private final GUIConfiguration guiConfiguration;

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
		JPanel mainPanel = new JPanel(
			new BorderLayout(guiConfiguration.getHorizontalGap(), guiConfiguration.getVerticalGap())
		);
		mainPanel.add(new JScrollPane(listErinnerungen), BorderLayout.CENTER);
		mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);
		setContentPane(mainPanel);
		setSize(400, 600);
		setVisible(true);
	}

	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel(
			new FlowLayout(FlowLayout.RIGHT, guiConfiguration.getHorizontalGap(), guiConfiguration.getVerticalGap())
		);
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(e -> System.exit(SpringApplication.exit(context)));
		buttonPanel.add(closeButton);
		return buttonPanel;
	}
}
