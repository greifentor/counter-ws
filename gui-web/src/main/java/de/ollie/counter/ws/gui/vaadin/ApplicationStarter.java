package de.ollie.counter.ws.gui.vaadin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The starter class for the application.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories("de.ollie.counter.ws.persistence.repository")
@ComponentScan("de.ollie.counter.ws")
@EntityScan("de.ollie.counter.ws.persistence.entity")
public class ApplicationStarter extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationStarter.class, args);
	}

}