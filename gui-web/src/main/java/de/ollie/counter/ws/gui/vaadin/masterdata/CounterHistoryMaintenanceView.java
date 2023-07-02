package de.ollie.counter.ws.gui.vaadin.masterdata;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.Route;

import de.ollie.counter.ws.core.model.CounterHistory;
import de.ollie.counter.ws.core.service.CounterHistoryService;
import de.ollie.counter.ws.core.service.CounterService;
import de.ollie.counter.ws.core.service.UserService;
import de.ollie.counter.ws.core.service.localization.ResourceManager;
import de.ollie.counter.ws.gui.SessionData;
import de.ollie.counter.ws.gui.vaadin.UserAuthorizationChecker;
import de.ollie.counter.ws.gui.vaadin.component.AbstractMasterDataBaseLayout;
import de.ollie.counter.ws.gui.vaadin.component.ButtonFactory;
import de.ollie.counter.ws.gui.vaadin.component.HeaderLayout;
import de.ollie.counter.ws.gui.vaadin.component.HeaderLayout.HeaderLayoutMode;
import lombok.Generated;
import lombok.RequiredArgsConstructor;

/**
 * A dialog to edit CounterHistory details.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Route(CounterHistoryMaintenanceView.URL)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@CssImport(value = "./styles/vaadin-text-area-styles.css", themeFor = "vaadin-text-area")
@CssImport(value = "./styles/vaadin-combo-box-styles.css", themeFor = "vaadin-combo-box")
@CssImport(value = "./styles/vaadin-checkbox-styles.css", themeFor = "vaadin-checkbox")
@RequiredArgsConstructor
public class CounterHistoryMaintenanceView extends AbstractMasterDataBaseLayout implements CounterHistoryDetailsLayout.Observer {

	public static final String URL = "counterws/masterdata/counterhistories/details";

	private static final Logger logger = LogManager.getLogger(CounterHistoryMaintenanceView.class);

	@Autowired(required = false)
	private MaintenanceViewRenderer<CounterHistory> maintenanceViewRenderer;
	@Autowired(required = false)
	private DetailsLayoutComboBoxItemLabelGenerator<CounterHistory> comboBoxItemLabelGenerator;

	private final ButtonFactory buttonFactory;
	private final ResourceManager resourceManager;
	private final MasterDataGUIConfiguration guiConfiguration;
	private final CounterHistoryService service;
	private final CounterService counterService;
	private final UserService userService;
	private final SessionData session;

	private CounterHistory model;

	@Override
	protected ButtonFactory getButtonFactory() {
		return buttonFactory;
	}

	@Override
	protected ResourceManager getResourceManager() {
		return resourceManager;
	}

	@Override
	protected SessionData getSessionData() {
		return session;
	}

	@Override
	public void doSetParameter(BeforeEvent event) {
		long id = parametersMap.containsKey("id") && (parametersMap.get("id").size() > 0)
				? Long.parseLong(parametersMap.get("id").get(0))
				: -1;
		model = service.findById(id).orElse(createNewModel());
		if (parametersMap.containsKey("duplicate") && "true".equals(parametersMap.get("duplicate").get(0))) {
			model.setId(-1);
		}
	}

	private CounterHistory createNewModel() {
		return new CounterHistory();
	}

	@Override
	public void doBeforeEnter(BeforeEnterEvent beforeEnterEvent) {
		UserAuthorizationChecker.forwardToLoginOnNoUserSetForSession(getSessionData(), beforeEnterEvent);
		setMargin(false);
		setSizeFull();
		getStyle().set("background-image", "url('" + guiConfiguration.getBackgroundFileName() + "')");
		getStyle().set("background-size", "cover");
		getStyle().set("background-attachment", "fixed");
		add(
				new HeaderLayout(
						buttonFactory
										.createBackButton(
												resourceManager,
												this::getUI,
												CounterHistoryPageView.URL,
												session),
						buttonFactory.createLogoutButton(resourceManager, this::getUI, session, logger),
								resourceManager.getLocalizedString("CounterHistoryMaintenanceView.header.prefix.label", session.getLocalization()) + getHeaderSuffix(model),
								HeaderLayoutMode.PLAIN),
				getDetailsLayout(model));
	}

	private String getHeaderSuffix(CounterHistory model) {
		return maintenanceViewRenderer != null
				? maintenanceViewRenderer.getHeaderSuffix(model)
				: "" + model.getName();
	}

	private AbstractMasterDataBaseLayout getDetailsLayout(CounterHistory model) {
		return new CounterHistoryDetailsLayout(buttonFactory, model, service, counterService, userService, resourceManager, session, this, comboBoxItemLabelGenerator);
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		logger.info("onAttach");
		super.onAttach(attachEvent);
	}

	@Override
	protected void onDetach(DetachEvent detachEvent) {
		logger.info("onDetach");
		super.onDetach(detachEvent);
		getElement().removeFromTree();
	}

	@Override
	public void save(Object model) {
		getUI().ifPresent(ui -> ui.navigate(CounterHistoryPageView.URL));
	}

	@Override
	public void save() {
		save(model);
	}

	@Override
	public void remove() {
		service.delete(model);
		getUI().ifPresent(ui -> ui.navigate(CounterHistoryPageView.URL));
	}

}