package de.ollie.memnon.gui.swing;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class GUIConfiguration {

	@Value("${gui.general.hgap:3}")
	private int horizontalGap;

	@Value("${gui.general.vgap:3}")
	private int verticalGap;
}
