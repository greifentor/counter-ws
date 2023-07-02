package de.ollie.counter.ws.gui.vaadin.masterdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;

import de.ollie.counter.ws.core.service.localization.ResourceManager;
import de.ollie.counter.ws.gui.SessionData;
import de.ollie.counter.ws.gui.vaadin.MainMenuView;
import de.ollie.counter.ws.gui.vaadin.UserAuthorizationChecker;
import de.ollie.counter.ws.gui.vaadin.component.Button;
import de.ollie.counter.ws.gui.vaadin.component.ButtonFactory;
import de.ollie.counter.ws.gui.vaadin.component.ButtonGrid;
import de.ollie.counter.ws.gui.vaadin.component.HeaderLayout;
import de.ollie.counter.ws.gui.vaadin.component.HeaderLayout.HeaderLayoutMode;
import de.ollie.counter.ws.gui.vaadin.component.MasterDataViewButtonAdder;
import de.ollie.counter.ws.gui.vaadin.masterdata.MasterDataGUIConfiguration;

import lombok.Generated;
import lombok.RequiredArgsConstructor;

/**
 * A layout with buttons to select a master data page.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Route(MasterDataView.URL)
@RequiredArgsConstructor
public class MasterDataView extends Scroller implements BeforeEnterObserver, HasUrlParameter<String> {

	public static final String URL = "counterws/masterdata/menu";

	private static final Logger LOG = LogManager.getLogger(MasterDataView.class);

	private final ButtonFactory buttonFactory;
	private final MasterDataGUIConfiguration guiConfiguration;
	private final ResourceManager resourceManager;
	private final SessionData session;

	@Autowired(required = false)
	private MasterDataViewButtonAdder masterDataViewButtonAdder;

	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		LOG.debug("setParameter");
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		UserAuthorizationChecker.forwardToLoginOnNoUserSetForSession(session, beforeEnterEvent);
		LOG.info("created");
		setSizeFull();
		getStyle().set("background-image", "url('" + guiConfiguration.getBackgroundFileName() + "')");
		getStyle().set("background-size", "cover");
		getStyle().set("background-attachment", "fixed");
		Button buttonMasterDataCounter =
				buttonFactory
						.createButton(
								resourceManager
										.getLocalizedString(
												"master-data.button.counter.text",
												session.getLocalization()));
		buttonMasterDataCounter.addClickListener(event -> switchToSourceCounter());
		buttonMasterDataCounter.setWidthFull();
		Button buttonMasterDataCounterHistory =
				buttonFactory
						.createButton(
								resourceManager
										.getLocalizedString(
												"master-data.button.counterhistory.text",
												session.getLocalization()));
		buttonMasterDataCounterHistory.addClickListener(event -> switchToSourceCounterHistory());
		buttonMasterDataCounterHistory.setWidthFull();
		List<Button> buttons =
				new ArrayList<>(
						Arrays
								.asList(
										buttonMasterDataCounter,
										buttonMasterDataCounterHistory
								));
		if (masterDataViewButtonAdder != null) {
			buttons.addAll(masterDataViewButtonAdder.createButtonsToAdd(session, () -> getUI()));
		} 
		ButtonGrid buttonGrid = new ButtonGrid(4, buttons);
		buttonGrid.setMargin(false);
		buttonGrid.setWidthFull();
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.setMargin(false);
		mainLayout
				.add(
						new HeaderLayout(
								buttonFactory.createBackButton(resourceManager, this::getUI, MainMenuView.URL, session),
								buttonFactory.createLogoutButton(resourceManager, this::getUI, session, LOG),
								resourceManager.getLocalizedString("master-data.header.menu.label", session.getLocalization()),
								HeaderLayoutMode.PLAIN),
						buttonGrid);
		setContent(mainLayout);
		LOG.info("main menu view opened for user '{}'.", session.getUserName());
	}

	private void switchToSourceCounter() {
		getUI().ifPresent(ui -> ui.navigate(CounterPageView.URL));
	}

	private void switchToSourceCounterHistory() {
		getUI().ifPresent(ui -> ui.navigate(CounterHistoryPageView.URL));
	}

}