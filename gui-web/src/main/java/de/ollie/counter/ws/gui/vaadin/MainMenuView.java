package de.ollie.counter.ws.gui.vaadin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;

import de.ollie.counter.ws.core.model.localization.LocalizationSO;
import de.ollie.counter.ws.core.service.JWTService;
import de.ollie.counter.ws.core.service.JWTService.AuthorizationData;
import de.ollie.counter.ws.core.service.localization.ResourceManager;
import de.ollie.counter.ws.gui.AccessChecker;
import de.ollie.counter.ws.gui.SessionData;
import de.ollie.counter.ws.gui.WebAppConfiguration;
import de.ollie.counter.ws.gui.vaadin.component.Button;
import de.ollie.counter.ws.gui.vaadin.component.ButtonFactory;
import de.ollie.counter.ws.gui.vaadin.component.ButtonGrid;
import de.ollie.counter.ws.gui.vaadin.component.HeaderLayout;
import de.ollie.counter.ws.gui.vaadin.component.HeaderLayout.HeaderLayoutMode;
import de.ollie.counter.ws.gui.vaadin.masterdata.MasterDataView;
import lombok.RequiredArgsConstructor;

/**
 * A main menu view for the application.
 */
@Route(MainMenuView.URL)
@RequiredArgsConstructor
public class MainMenuView extends VerticalLayout
		implements AccessChecker, BeforeEnterObserver, HasUrlParameter<String> {

	public static final LocalizationSO LOCALIZATION = LocalizationSO.DE;
	public static final String URL = "counterws/menu";

	private static final Logger LOG = LogManager.getLogger(MainMenuView.class);

	private final ButtonFactory buttonFactory;
	private final GUIConfiguration guiConfiguration;
	private final JWTService jwtService;
	private final ResourceManager resourceManager;
	private final SessionData session;
	private final WebAppConfiguration webAppConfiguration;

	private AuthorizationData authorizationData;
	private String token;

	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		Location location = event.getLocation();
		QueryParameters queryParameters = location.getQueryParameters();
		Map<String, List<String>> parametersMap = queryParameters.getParameters();
		if ((parametersMap.get("jwt") != null) && !parametersMap.get("jwt").isEmpty()) {
			token = parametersMap.get("jwt").get(0);
		}
		try {
			authorizationData = jwtService.getAuthorizationData(token);
			session.setAccessChecker(this);
			session.setAuthorizationData(authorizationData);
			session.setLocalization(LocalizationSO.DE);
			LOG.info("session started by user: " + authorizationData.getUser().getName());
		} catch (Exception e) {
			e.printStackTrace();
			LOG.warn("tried to login with invalid token! (" + e + ")");
		}
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		UserAuthorizationChecker.forwardToLoginOnNoUserSetForSession(session, beforeEnterEvent);
		LOG.info("created");
		setMargin(false);
		setWidthFull();
		getStyle().set("background-image", "url('" + guiConfiguration.getMainMenuBackgroundFileName() + "')");
		getStyle().set("background-size", "cover");
		Button buttonMasterData =
				buttonFactory
						.createButton(
								resourceManager
										.getLocalizedString(
												"main-menu.button.master-data.text",
												session.getLocalization()));
		buttonMasterData.addClickListener(event -> switchToMasterData());
		buttonMasterData.setWidthFull();
		ButtonGrid buttonGridMasterData = new ButtonGrid(4, buttonMasterData);
		buttonGridMasterData.setMargin(false);
		buttonGridMasterData.setWidthFull();
		add(
				new HeaderLayout(
						buttonFactory
								.createResourcedButton(
										resourceManager,
										"commons.button.logout.text",
										event -> switchToCube(),
										session),
						resourceManager.getLocalizedString("commons.header.main-menu.label", session.getLocalization()),
						HeaderLayoutMode.PLAIN),
				buttonGridMasterData);
		LOG.info("main menu view opened for user '{}'.", session.getUserName());
	}

	private void switchToMasterData() {
		getUI().ifPresent(ui -> ui.navigate(MasterDataView.URL));
	}

	@Override
	public boolean checkToken() {
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