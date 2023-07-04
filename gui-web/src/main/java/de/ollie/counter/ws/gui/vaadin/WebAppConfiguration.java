package de.ollie.counter.ws.gui.vaadin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Generated;
import lombok.Getter;

/**
 * A class for the web app configuration;
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Configuration
@Generated
@Getter
public class WebAppConfiguration {

	@Value("${app.version}")
	private String appVersion;

	@Value("${cube.disabled.user.name:DEFAULT}")
	private String cubeDisabledUserGlobalId;
	@Value("${cube.enabled:true}")
	private boolean cubeEnabled;
	@Value("${cube.url}")
	private String cubeURL;

	@Value("${maximum.jwt.validity-in-minutes:60}")
	private int maximumJWTValidityInMinutes;

}