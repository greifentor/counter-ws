package de.ollie.counter.ws.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import de.ollie.counter.ws.core.model.localization.LocalizationSO;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;

import lombok.Data;

/**
 * An object to hold data during the session.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Component
@VaadinSessionScope
@Data
public class SessionData {

	private static int counter = 0;

	private SessionId id = new SessionId("counterws-" + (counter++));
	private LocalizationSO localization = LocalizationSO.DE;
	private Map<String, Object> parameters = new HashMap<>();

	public String getUserName() {
		return "N/A";
	}

	public Optional<Object> findParameter(String id) {
		return Optional.ofNullable(parameters.get(id));
	}

	public void setParameter(String id, Object obj) {
		parameters.put(id, obj);
	}

}