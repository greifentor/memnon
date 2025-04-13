package de.ollie.memnon.gui.swing;

import java.awt.EventQueue;
import lombok.Generated;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Generated
@SpringBootApplication
@ComponentScan("de.ollie.memnon")
@EntityScan("de.ollie.memnon.persistence.jpa.entity")
@EnableJpaRepositories(basePackages = "de.ollie.memnon.persistence.jpa.repository")
public class MemNoNGuiSwingStarter {

	public static void main(String[] args) {
		var ctx = new SpringApplicationBuilder(MemNoNGuiSwingStarter.class).headless(false).run(args);
		EventQueue.invokeLater(() -> {
			var ex0 = ctx.getBean(MainGUIFrame.class);
			ex0.setVisible(true);
		});
	}
}
