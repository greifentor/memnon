package de.ollie.memnon.shell;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@Generated
@SpringBootApplication
@ComponentScan("de.ollie.memnon")
public class MemNoNShellStarter {

	public static void main(String[] args) {
		SpringApplication.run(MemNoNShellStarter.class, args);
	}
}
