package de.ollie.counter.ws.gui.vaadin.counters;

import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;

import de.ollie.counter.ws.core.model.Counter;
import de.ollie.counter.ws.core.model.User;
import de.ollie.counter.ws.core.service.CounterService;
import de.ollie.counter.ws.core.service.UserService;
import de.ollie.counter.ws.core.service.localization.ResourceManager;
import de.ollie.counter.ws.gui.SessionData;
import de.ollie.counter.ws.gui.vaadin.GUIConfiguration;
import de.ollie.counter.ws.gui.vaadin.MainMenuView;
import de.ollie.counter.ws.gui.vaadin.UserAuthorizationChecker;
import de.ollie.counter.ws.gui.vaadin.WebAppConfiguration;
import de.ollie.counter.ws.gui.vaadin.component.ButtonFactory;
import de.ollie.counter.ws.gui.vaadin.component.HeaderLayout;
import de.ollie.counter.ws.gui.vaadin.component.HeaderLayout.HeaderLayoutMode;
import lombok.RequiredArgsConstructor;

/**
 * A main menu view for the application.
 */
@Route(CountersView.URL)
@RequiredArgsConstructor
public class CountersView extends VerticalLayout implements BeforeEnterObserver, HasUrlParameter<String> {

	public static final String URL = "counterws/counters";

	private static final Logger LOG = LogManager.getLogger(CountersView.class);

	private final ButtonFactory buttonFactory;
	private final CounterService counterService;
	private final GUIConfiguration guiConfiguration;
	private final ResourceManager resourceManager;
	private final SessionData session;
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
		add(
				new HeaderLayout(
						buttonFactory.createBackButton(resourceManager, this::getUI, MainMenuView.URL, session),
						buttonFactory
								.createResourcedButton(
										resourceManager,
										"commons.button.logout.text",
										event -> switchToCube(),
										session),
						resourceManager.getLocalizedString("commons.header.counters.label", session.getLocalization()),
						HeaderLayoutMode.PLAIN));
		addCounterComponents();
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
		add(new CounterLayout(counter, counterService, buttonFactory, resourceManager, session));
	}

	private void switchToCube() {
		String url = webAppConfiguration.getCubeURL();
		LOG.info("returning to: " + url);
		getUI().ifPresent(ui -> ui.getPage().setLocation(url));
	}

}
