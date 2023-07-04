package de.ollie.counter.ws.gui.vaadin.masterdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;

import de.ollie.counter.ws.core.model.Counter;
import de.ollie.counter.ws.core.model.PageParameters;
import de.ollie.counter.ws.core.service.CounterService;
import de.ollie.counter.ws.core.service.localization.ResourceManager;
import de.ollie.counter.ws.gui.SessionData;
import de.ollie.counter.ws.gui.vaadin.UserAuthorizationChecker;
import de.ollie.counter.ws.gui.vaadin.component.Button;
import de.ollie.counter.ws.gui.vaadin.component.ButtonFactory;
import de.ollie.counter.ws.gui.vaadin.component.HeaderLayout;
import de.ollie.counter.ws.gui.vaadin.component.HeaderLayout.HeaderLayoutMode;
import de.ollie.counter.ws.gui.vaadin.component.MasterDataButtonLayout;
import de.ollie.counter.ws.gui.vaadin.masterdata.MasterDataGUIConfiguration;
import lombok.Generated;
import lombok.RequiredArgsConstructor;

/**
 * A view for paginated counter lists.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Route(CounterPageView.URL)
@RequiredArgsConstructor
public class CounterPageView extends Scroller implements BeforeEnterObserver, HasUrlParameter<String> {

	public static final String URL = "counterws/masterdata/counters";

	private static final Logger logger = LogManager.getLogger(CounterPageView.class);
	private static final String PARAMETER_FILTER = "CounterPageView.Filter";

	@Autowired(required = false)
	private MasterDataGridFieldRenderer<Counter> masterDataGridFieldRenderer;

	private final ButtonFactory buttonFactory;
	private final ResourceManager resourceManager;
	private final MasterDataGUIConfiguration guiConfiguration;
	private final CounterService service;
	private final SessionData session;

	private Button buttonAdd;
	private Button buttonDuplicate;
	private Button buttonEdit;
	private Button buttonRemove;
	private Grid<Counter> grid;
	private TextField textFieldFilter;
	private VerticalLayout mainLayout;

	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		logger.debug("setParameter");
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		UserAuthorizationChecker.forwardToLoginOnNoUserSetForSession(session, beforeEnterEvent);
		mainLayout = new VerticalLayout();
		getStyle().set("background-image", "url('" + guiConfiguration.getBackgroundFileName() + "')");
		getStyle().set("background-size", "cover");
		getStyle().set("background-attachment", "fixed");
		textFieldFilter = new TextField();
		textFieldFilter.addKeyUpListener(event -> {
			if (event.getKey() == Key.ENTER) {
				updateGrid(0);
			}
		});
		textFieldFilter.setWidthFull();
		session.findParameter(PARAMETER_FILTER).ifPresent(s -> textFieldFilter.setValue(s.toString()));
		textFieldFilter.addValueChangeListener(event -> updateGrid(0));
		buttonAdd = buttonFactory.createAddButton(resourceManager, event -> addRecord(), session);
		buttonDuplicate = buttonFactory.createButton(resourceManager.getLocalizedString("commons.button.duplicate.text", session.getLocalization()));
		buttonDuplicate.addClickListener(event -> duplicateRecord());
		buttonEdit = buttonFactory.createEditButton(resourceManager, event -> editRecord(), session);
		buttonRemove = buttonFactory.createRemoveButton(resourceManager, event -> removeRecord(), session);
		grid = new Grid<>();
		grid
				.addColumn(model -> getHeaderString("NAME", model, () -> model.getName()))
				.setHeader(resourceManager.getLocalizedString("CounterPageView.grid.header.name.label", session.getLocalization()))
				.setSortable(true);
		grid
				.addColumn(model -> getHeaderString("CURRENTVALUE", model, () -> model.getCurrentValue()))
				.setHeader(resourceManager.getLocalizedString("CounterPageView.grid.header.currentvalue.label", session.getLocalization()))
				.setSortable(true);
		grid
				.addColumn(model -> getHeaderString("USER", model, () -> model.getUser()))
				.setHeader(resourceManager.getLocalizedString("CounterPageView.grid.header.user.label", session.getLocalization()))
				.setSortable(true);
		grid
				.addColumn(model -> getHeaderString("SORTORDER", model, () -> model.getSortOrder()))
				.setHeader(resourceManager.getLocalizedString("CounterPageView.grid.header.sortorder.label", session.getLocalization()))
				.setSortable(true);
		grid
				.addColumn(model -> getHeaderString("PERIOD", model, () -> model.getPeriod()))
				.setHeader(resourceManager.getLocalizedString("CounterPageView.grid.header.period.label", session.getLocalization()))
				.setSortable(true);
		grid
				.addColumn(model -> getHeaderString("VIEWMODE", model, () -> model.getViewMode()))
				.setHeader(resourceManager.getLocalizedString("CounterPageView.grid.header.viewmode.label", session.getLocalization()))
				.setSortable(true);
		grid
				.addColumn(model -> getHeaderString("VALUETODEVIDE", model, () -> model.getValueToDevide()))
				.setHeader(resourceManager.getLocalizedString("CounterPageView.grid.header.valuetodevide.label", session.getLocalization()))
				.setSortable(true);
		grid
				.addColumn(model -> getHeaderString("ADDITIONALDISPLAY1", model, () -> model.getAdditionalDisplay1()))
				.setHeader(resourceManager.getLocalizedString("CounterPageView.grid.header.additionaldisplay1.label", session.getLocalization()))
				.setSortable(true);
		grid.setMultiSort(true);
		grid.setWidthFull();
		grid.addSelectionListener(this::enabledButtons);
		grid.getStyle().set("-moz-border-radius", "4px");
		grid.getStyle().set("-webkit-border-radius", "4px");
		grid.getStyle().set("border-radius", "4px");
		grid.getStyle().set("border", "1px solid #A9A9A9");
		MasterDataButtonLayout buttonLayout = new MasterDataButtonLayout(buttonAdd, buttonEdit, buttonDuplicate, buttonRemove);
		buttonLayout.setMargin(false);
		buttonLayout.setWidthFull();
		mainLayout.setMargin(false);
		mainLayout.setSizeFull();
		setSizeFull();
		VerticalLayout filterLayout = new VerticalLayout();
		filterLayout.getStyle().set("-moz-border-radius", "4px");
		filterLayout.getStyle().set("-webkit-border-radius", "4px");
		filterLayout.getStyle().set("border-radius", "4px");
		filterLayout.getStyle().set("border", "1px solid #A9A9A9");
		filterLayout
				.getStyle()
				.set(
						"box-shadow",
						"10px 10px 20px #e4e4e4, -10px 10px 20px #e4e4e4, -10px -10px 20px #e4e4e4, 10px -10px 20px #e4e4e4");
		filterLayout.setMargin(false);
		filterLayout.setWidthFull();
		filterLayout.add(textFieldFilter);
		VerticalLayout dataLayout = new VerticalLayout();
		dataLayout.getStyle().set("-moz-border-radius", "4px");
		dataLayout.getStyle().set("-webkit-border-radius", "4px");
		dataLayout.getStyle().set("border-radius", "4px");
		dataLayout.getStyle().set("border", "1px solid #A9A9A9");
		dataLayout
				.getStyle()
				.set(
						"box-shadow",
						"10px 10px 20px #e4e4e4, -10px 10px 20px #e4e4e4, -10px -10px 20px #e4e4e4, 10px -10px 20px #e4e4e4");
		dataLayout.setMargin(false);
		dataLayout.setWidthFull();
		dataLayout.add(grid, buttonLayout);
		mainLayout.add(
				new HeaderLayout(
						buttonFactory.createBackButton(resourceManager, this::getUI, MasterDataView.URL, session),
						buttonFactory.createLogoutButton(resourceManager, this::getUI, session, logger),
						resourceManager.getLocalizedString("CounterPageView.header.label", session.getLocalization()),
						HeaderLayoutMode.PLAIN),
				filterLayout,
				dataLayout);
		updateGrid(0);
		setButtonEnabled(buttonDuplicate, false);
		setButtonEnabled(buttonEdit, false);
		setButtonEnabled(buttonRemove, false);
		setContent(mainLayout);
		textFieldFilter.focus();
	}

	private Object getHeaderString(String fieldName, Counter aTable, Supplier<?> f) {
		return masterDataGridFieldRenderer != null && masterDataGridFieldRenderer.hasRenderingFor(fieldName)
				? masterDataGridFieldRenderer.getHeaderString(fieldName, aTable)
				: f.get();
	}

	private void enabledButtons(SelectionEvent<Grid<Counter>, Counter> event) {
		if (event.getFirstSelectedItem().isEmpty()) {
			setButtonEnabled(buttonAdd, true);
			setButtonEnabled(buttonDuplicate, false);
			setButtonEnabled(buttonEdit, false);
			setButtonEnabled(buttonRemove, false);
		} else {
			setButtonEnabled(buttonAdd, false);
			setButtonEnabled(buttonDuplicate, true);
			setButtonEnabled(buttonEdit, true);
			setButtonEnabled(buttonRemove, true);
		}
	}

	private void setButtonEnabled(Button button, boolean enabled) {
		button.setEnabled(enabled);
		if (enabled) {
			button.setBackgroundImage(guiConfiguration.getButtonEnabledBackgroundFileName());
			button.setBorderColor(guiConfiguration.getButtonEnabledBorderColor());
		} else {
			button.setBackgroundImage(guiConfiguration.getButtonDisabledBackgroundFileName());
			button.setBorderColor(guiConfiguration.getButtonDisabledBorderColor());
		}
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		logger.info("Counter page layout opened for user '{}'.", session.getUserName());
		super.onAttach(attachEvent);
	}

	@Override
	protected void onDetach(DetachEvent detachEvent) {
		logger.info("onDetach");
		super.onDetach(detachEvent);
		getElement().removeFromTree();
	}

	private void updateGrid(int pageNumber) {
		grid
				.setItems(
						service
								.findAll(new PageParameters().setEntriesPerPage(Integer.MAX_VALUE).setPageNumber(pageNumber))
								.getEntries()
								.stream()
								.filter(this::isMatching)
								.collect(Collectors.toList()));
	}

	private boolean isMatching(Counter model) {
		List<String> patterns =
				getWords(textFieldFilter.getValue()).stream().map(s -> s.toLowerCase()).collect(Collectors.toList());
		if (patterns.isEmpty()) {
			return true;
		}
		boolean b = true;
		for (String pattern : patterns) {
			b &= isMatchingPattern(pattern.toLowerCase(), model);
		}
		return b;
	}

	private List<String> getWords(String s) {
		List<String> l = new ArrayList<>();
		if (s != null) {
			StringTokenizer st = new StringTokenizer(s, " ");
			while (st.hasMoreTokens()) {
				l.add(st.nextToken());
			}
		}
		return l;
	}

	private boolean isMatchingPattern(String pattern, Counter model) {
		boolean result = false;
		result = result || getHeaderString(Counter.USER, model, () -> model.getUser()).toString().toLowerCase().contains(pattern);
		result = result || getHeaderString(Counter.NAME, model, () -> model.getName()).toString().toLowerCase().contains(pattern);
		return result;
	}

	private void addRecord() {
		session.setParameter(PARAMETER_FILTER, textFieldFilter.getValue());
		getUI().ifPresent(ui -> ui.navigate(CounterMaintenanceView.URL));
	}

	private void duplicateRecord() {
		session.setParameter(PARAMETER_FILTER, textFieldFilter.getValue());
		grid.getSelectedItems().stream().findFirst().ifPresent(model -> {
			QueryParameters parameters =
					new QueryParameters(Map.of("id", List.of("" + model.getId()), "duplicate", List.of("true")));
			getUI().ifPresent(ui -> ui.navigate(CounterMaintenanceView.URL, parameters));
		});
	}

	private void editRecord() {
		session.setParameter(PARAMETER_FILTER, textFieldFilter.getValue());
		grid.getSelectedItems().stream().findFirst().ifPresent(model -> {
			QueryParameters parameters = new QueryParameters(Map.of("id", List.of("" + model.getId())));
			getUI().ifPresent(ui -> ui.navigate(CounterMaintenanceView.URL, parameters));
		});
	}

	private void removeRecord() {
		grid.getSelectedItems().stream().findFirst().ifPresent(model -> {
			service.delete(model);
			updateGrid(0);
			textFieldFilter.focus();
		});
	}

}