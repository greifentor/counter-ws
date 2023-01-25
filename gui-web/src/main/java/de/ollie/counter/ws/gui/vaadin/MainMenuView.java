package de.ollie.counter.ws.gui.vaadin;

import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;

import de.ollie.counter.ws.core.model.Counter;
import de.ollie.counter.ws.core.model.User;
import de.ollie.counter.ws.core.model.localization.LocalizationSO;
import de.ollie.counter.ws.core.service.CounterService;
import de.ollie.counter.ws.core.service.TimeDistanceService;
import de.ollie.counter.ws.core.service.UserService;
import de.ollie.counter.ws.core.service.localization.ResourceManager;
import de.ollie.counter.ws.gui.SessionData;
import de.ollie.counter.ws.gui.vaadin.component.Button;
import de.ollie.counter.ws.gui.vaadin.component.ButtonFactory;
import de.ollie.counter.ws.gui.vaadin.component.ButtonGrid;
import de.ollie.counter.ws.gui.vaadin.component.HeaderLayout;
import de.ollie.counter.ws.gui.vaadin.component.HeaderLayout.HeaderLayoutMode;
import de.ollie.counter.ws.gui.vaadin.counters.CounterLayout;
import de.ollie.counter.ws.gui.vaadin.masterdata.MasterDataView;
import lombok.RequiredArgsConstructor;

/**
 * A main menu view for the application.
 */
@Route(MainMenuView.URL)
@RequiredArgsConstructor
public class MainMenuView extends VerticalLayout
		implements BeforeEnterObserver, HasUrlParameter<String> {

	public static final LocalizationSO LOCALIZATION = LocalizationSO.DE;
	public static final String URL = "counterws/menu";

	private static final Logger LOG = LogManager.getLogger(MainMenuView.class);

	private final ButtonFactory buttonFactory;
	private final CounterService counterService;
	private final GUIConfiguration guiConfiguration;
	private final ResourceManager resourceManager;
	private final SessionData session;
	private final TimeDistanceService timeDistanceService;
	private final UserService userService;
	private final WebAppConfiguration webAppConfiguration;

	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		LOG.info("setParameter");
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		LOG.info("beforeEnter");
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
						resourceManager
								.getLocalizedString("commons.header.main-menu.label", session.getLocalization())
								.replace("{0}", webAppConfiguration.getAppVersion()),
						HeaderLayoutMode.PLAIN),
				buttonGridMasterData);
		addCounterComponents();
		LOG.info("main menu view opened for user '{}'.", session.getUserName());
	}

	private void switchToMasterData() {
		getUI().ifPresent(ui -> ui.navigate(MasterDataView.URL));
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

	private void addCounterComponents() {
		User user = getUser();
		counterService
				.findAllByUser(user)
				.stream()
				.sorted((c0, c1) -> c0.getSortOrder() - c1.getSortOrder())
				.forEach(counter -> createAndAddNewCounterComponent(counter));
	}

	private User getUser() {
		return userService
				.findByGlobalId(session.getAuthorizationData().getUser().getGlobalId())
				.orElseThrow(
						() -> new NoSuchElementException(
								"user not found with global id: "
										+ session.getAuthorizationData().getUser().getGlobalId()));
	}

	private void createAndAddNewCounterComponent(Counter counter) {
		add(new CounterLayout(counter, counterService, timeDistanceService, buttonFactory, resourceManager, session));
	}

}