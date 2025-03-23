package de.ollie.memnon.shell;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Generated
@SpringBootApplication
@ComponentScan("de.ollie.memnon")
@EntityScan("de.ollie.memnon.persistence.jpa.entity")
@EnableJpaRepositories(basePackages = "de.ollie.memnon.persistence.jpa.repository")
public class MemNoNShellStarter {

	public static void main(String[] args) {
		SpringApplication.run(MemNoNShellStarter.class, args);
	}
}
