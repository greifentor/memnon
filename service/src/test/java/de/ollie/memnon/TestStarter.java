package de.ollie.memnon;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@Generated
@SpringBootApplication
@ComponentScan("de.ollie.memnon")
@EntityScan("de.ollie.memnon.persistence.jpa.entity")
// @EnableJpaRepositories(basePackages = "de.ollie.memnon.persistence.jpa.repository")
public class TestStarter {

	public static void main(String[] args) {
		SpringApplication.run(TestStarter.class, args);
	}
}
