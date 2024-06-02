package de.ollie.counter.ws.gui.vaadin.counters;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;

import de.ollie.counter.ws.core.model.AdditionalDisplayMode;
import de.ollie.counter.ws.core.model.Counter;
import de.ollie.counter.ws.core.model.ViewMode;
import de.ollie.counter.ws.core.service.CounterHistoryService;
import de.ollie.counter.ws.core.service.CounterService;
import de.ollie.counter.ws.core.service.TimeDistanceService;
import de.ollie.counter.ws.core.service.localization.ResourceManager;
import de.ollie.counter.ws.gui.SessionData;
import de.ollie.counter.ws.gui.vaadin.component.ButtonFactory;

public class CounterLayout extends VerticalLayout {

	private static final Logger LOG = LogManager.getLogger(CounterLayout.class);

	private final CounterService counterService;
	private final CounterHistoryService counterHistoryService;
	private final ButtonFactory buttonFactory;
	private final ResourceManager resourceManager;
	private final SessionData session;
	private final TimeDistanceService timeDistanceService;

	private Counter counter;

	public CounterLayout(Counter counter, CounterService counterService, CounterHistoryService counterHistoryService,
			TimeDistanceService timeDistanceService, ButtonFactory buttonFactory, ResourceManager resourceManager,
			SessionData session) {
		this.counter = counter;
		this.counterService = counterService;
		this.counterHistoryService = counterHistoryService;
		this.buttonFactory = buttonFactory;
		this.resourceManager = resourceManager;
		this.session = session;
		this.timeDistanceService = timeDistanceService;
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		LOG.info("attach");
		updateView();
	}

	private void updateView() {
		LOG.info("updateView");
		removeAll();
		setWidthFull();
		getStyle().set("-moz-border-radius", "4px");
		getStyle().set("-webkit-border-radius", "4px");
		getStyle().set("border-radius", "4px");
		getStyle().set("border", "1px solid gray");
		getStyle()
				.set(
						"box-shadow",
						"10px 10px 20px #e4e4e4, -10px 10px 20px #e4e4e4, -10px -10px 20px #e4e4e4, 10px -10px 20px #e4e4e4");
		FormLayout layout = new FormLayout();
		layout
				.setResponsiveSteps(
						new ResponsiveStep("0px", 1),
						new ResponsiveStep("320px", 2),
						new ResponsiveStep("640px", 3),
						new ResponsiveStep("960px", 4),
						new ResponsiveStep("1280px", 5),
						new ResponsiveStep("1600px", 6),
						new ResponsiveStep("1920px", 7));
		layout.add(new Label(counter.getName()));
		if (counter.getViewMode() == ViewMode.COUNTS) {
			layout.add(new Label("" + counter.getCurrentValue()));
			layout
					.add(
							new Label(),
							createIntegerFieldCurrentValue("CounterLayout.integerFieldCurrentValue.label.text"));
		} else if (counter.getViewMode() == ViewMode.DIVIDED_BY_COUNTS) {
			int divideBy = counter.getCurrentValue() < 1 ? 1 : counter.getCurrentValue();
			layout.add(new Label(String.format("%1.2f", (counter.getValueToDevide() / divideBy))));
			layout
					.add(
							new Label(),
							createIntegerFieldCurrentValue("CounterLayout.integerFieldCurrentValue.label.text"));
		} else if (counter.getViewMode() == ViewMode.LAST_CLICK_DATE) {
			if (counter.getLastCounterEvent() != null) {
				layout
						.add(
								new DateTimePicker(
										resourceManager
												.getLocalizedString(
														"CounterLayout.datepicker.label.text",
														session.getLocalization()),
										counter.getLastCounterEvent(),
										event -> setLastClickEvent(event.getValue())));
				layout
						.add(
								new Label(
										"(" + timeDistanceService
												.getTimeDistanceAsString(
														counter.getLastCounterEvent(),
														LocalDateTime.now())
												+ ")"));
			} else {
				layout.add(new Label(resourceManager.getLocalizedString("datetime.null", session.getLocalization())));
				layout.add(new Label(resourceManager.getLocalizedString("datetime.null", session.getLocalization())));
			}
			layout
					.add(
							buttonFactory
									.createResourcedButton(
											resourceManager,
											"CounterLayout.button.lastclickevent.label",
											event -> setLastClickEvent(LocalDateTime.now()),
											session));
		}
		if (counter.getViewMode() == ViewMode.LAST_CLICK_DATE) {
			if (counter.getAdditionalDisplay1() == AdditionalDisplayMode.MAX_DISTANCE) {
				layout
						.add(
								resourceManager
										.getLocalizedString(
												"CounterLayout.display.maxDistance.label",
												session.getLocalization())
										.replace(
												"${Distance}",
												counterHistoryService.getMaxDistanceByCounter(counter)));
			}
		} else if (counter.getViewMode() == ViewMode.DIVIDED_BY_COUNTS) {
			LOG.info("divided by counts");
			if (counter.getAdditionalDisplay1() == AdditionalDisplayMode.AVG_CLICKS) {
				LOG.info("average clicks");
				layout
						.add(
								resourceManager
										.getLocalizedString(
												"CounterLayout.display.avgClicks.label",
												session.getLocalization())
										.replace(
												"${Clicks}",
												"" + counterHistoryService.getAvgClicksByCounter(counter)));
			} else if (counter.getAdditionalDisplay1() == AdditionalDisplayMode.MAX_CLICKS) {
				LOG.info("max clicks");
				layout
						.add(
								resourceManager
										.getLocalizedString(
												"CounterLayout.display.maxClicks.label",
												session.getLocalization())
										.replace(
												"${Clicks}",
												"" + counterHistoryService.getMaxClicksByCounter(counter)));
			}
		}
		add(layout);
	}

	private IntegerField createIntegerFieldCurrentValue(String resourceId) {
		IntegerField integerFieldCurrentValue =
				new IntegerField(
						resourceManager.getLocalizedString(resourceId, session.getLocalization()),
						counter.getCurrentValue(),
						event -> setCurrentValue(event.getValue()));
		integerFieldCurrentValue.setHasControls(true);
		return integerFieldCurrentValue;
	}

	private void setCurrentValue(Integer currentValue) {
		LOG.info("setCurrentValue");
		counter.setCurrentValue(currentValue);
		counter.setLastCounterEvent(LocalDateTime.now());
		counter = counterService.update(counter);
		updateView();
	}

	private void setLastClickEvent(LocalDateTime localDateTime) {
		LOG.info("setLastClickEvent");
		counter.setLastCounterEvent(localDateTime);
		counter = counterService.update(counter);
		updateView();
	}

}