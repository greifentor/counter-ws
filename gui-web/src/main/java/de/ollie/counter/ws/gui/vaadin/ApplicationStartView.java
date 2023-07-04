package de.ollie.counter.ws.gui.vaadin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;

import de.ollie.counter.ws.core.model.User;
import de.ollie.counter.ws.core.model.localization.LocalizationSO;
import de.ollie.counter.ws.core.service.JWTService;
import de.ollie.counter.ws.core.service.JWTService.AuthorizationData;
import de.ollie.counter.ws.core.service.UserService;
import de.ollie.counter.ws.core.service.localization.ResourceManager;
import de.ollie.counter.ws.gui.AccessChecker;
import de.ollie.counter.ws.gui.SessionData;
import lombok.RequiredArgsConstructor;

/**
 * A start view for the application.
 */
@Route(ApplicationStartView.URL)
@PreserveOnRefresh
@VaadinSessionScope
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@CssImport(value = "./styles/vaadin-text-area-styles.css", themeFor = "vaadin-area-field")
@RequiredArgsConstructor
public class ApplicationStartView extends VerticalLayout implements AccessChecker, BeforeEnterObserver {

	public static final LocalizationSO LOCALIZATION = LocalizationSO.DE;
	public static final Logger LOG = LogManager.getLogger(ApplicationStartView.class);
	public static final String URL = "counterws";

	private final JWTService jwtService;
	private final ResourceManager resourceManager;
	private final SessionData session;
	private final UserService userService;
	private final WebAppConfiguration webAppConfiguration;

	private AuthorizationData authorizationData;
	private String token;

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		LOG.info("before enter");
		Location location = beforeEnterEvent.getLocation();
		QueryParameters queryParameters = location.getQueryParameters();
		Map<String, List<String>> parametersMap = queryParameters.getParameters();
		if ((parametersMap.get("jwt") != null) && !parametersMap.get("jwt").isEmpty()) {
			token = parametersMap.get("jwt").get(0);
		}
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		authorize();
		LOG.info("attached");
		refresh();
	}

	private void authorize() {
		if ((session.getAuthorizationData() != null) && LocalDateTime.now().isBefore(session.getValidUntil())) {
			return;
		}
		try {
			LOG.info("cube is " + (webAppConfiguration.isCubeEnabled() ? "enabled" : "disabled"));
			if (webAppConfiguration.isCubeEnabled()) {
				authorizationData = jwtService.getAuthorizationData(token);
				session.setAccessChecker(this);
				session.setAuthorizationData(authorizationData);
				session.setLocalization(LocalizationSO.DE);
				session.setValidUntil(LocalDateTime.now().plusHours(4));
			} else {
				User user =
						userService
								.findByGlobalId(webAppConfiguration.getCubeDisabledUserGlobalId())
								.orElseGet(
										() -> userService
												.update(
														new User()
																.setName("D.Fault")
																.setGlobalId(
																		webAppConfiguration
																				.getCubeDisabledUserGlobalId())
																.setToken("DEFAULT")));
				authorizationData = new AuthorizationData("carp-maps-ws", LocalDateTime.now(), user, null);
				session.setAccessChecker(this);
				session.setAuthorizationData(authorizationData);
				session.setLocalization(LocalizationSO.DE);
				session.setValidUntil(LocalDateTime.now().plusHours(4));
			}
			LOG.info("session started by user: " + authorizationData.getUser().getName());
			LOG.info("session valid until: " + session.getValidUntil());
			LOG.info("current time: " + LocalDateTime.now());
		} catch (Exception e) {
			e.printStackTrace();
			LOG.warn("tried to login with invalid token! (" + e + ")");
			switchToCube();
		}
	}

	private void refresh() {
		removeAll();
		getUI().ifPresent(ui -> ui.navigate(MainMenuView.URL));
	}

	@Override
	protected void onDetach(DetachEvent detachEvent) {
		super.onDetach(detachEvent);
		LOG.info("detached");
	}

	@Override
	public boolean isSessionValid() {
		if (jwtService
				.getLoginDate(token)
				.plusMinutes(webAppConfiguration.getMaximumJWTValidityInMinutes())
				.isBefore(LocalDateTime.now())) {
			LOG
					.info(
							"session invalid: "
									+ jwtService
											.getLoginDate(token)
											.plusMinutes(webAppConfiguration.getMaximumJWTValidityInMinutes())
									+ " < " + LocalDateTime.now());
			denyAccess();
			return false;
		}
		LOG
				.info(
						"valid until: " + jwtService
								.getLoginDate(token)
								.plusMinutes(webAppConfiguration.getMaximumJWTValidityInMinutes()));
		return true;
	}

	public void denyAccess() {
		removeAll();
		Label label = new Label(resourceManager.getLocalizedString("Error.notAuthorized.label.text", LOCALIZATION));
		label.getStyle().set("color", "red");
		HorizontalLayout layout = new HorizontalLayout();
		layout.setMargin(true);
		layout.add(label);
		add(layout);
		LOG.info("access denied!");
	}

	private void switchToCube() {
		String url = webAppConfiguration.getCubeURL();
		LOG.info("returning to: " + url);
		getUI().ifPresent(ui -> ui.getPage().setLocation(url));
	}

}