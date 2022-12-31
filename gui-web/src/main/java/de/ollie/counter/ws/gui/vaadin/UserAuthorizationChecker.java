package de.ollie.counter.ws.gui.vaadin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.router.BeforeEnterEvent;

import de.ollie.counter.ws.gui.SessionData;
import lombok.experimental.UtilityClass;
import lombok.Generated;

/**
 * A class for user authorization checks. Override with your method to check this context.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@UtilityClass
public class UserAuthorizationChecker {

	private static final Logger logger = LogManager.getLogger(UserAuthorizationChecker.class);

	public void forwardToLoginOnNoUserSetForSession(SessionData sessionData, BeforeEnterEvent beforeEnterEvent) {
		if (sessionData.getUserName() == null) {
			logger.info("no authorization forwarded to login page.");
			beforeEnterEvent.forwardTo(ApplicationStartView.URL);
		}
	}

}