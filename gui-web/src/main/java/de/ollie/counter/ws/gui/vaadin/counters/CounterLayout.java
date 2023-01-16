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

import de.ollie.counter.ws.core.model.Counter;
import de.ollie.counter.ws.core.model.ViewMode;
import de.ollie.counter.ws.core.service.CounterService;
import de.ollie.counter.ws.core.service.TimeDistanceService;
import de.ollie.counter.ws.core.service.localization.ResourceManager;
import de.ollie.counter.ws.gui.SessionData;
import de.ollie.counter.ws.gui.vaadin.component.ButtonFactory;

public class CounterLayout extends VerticalLayout {

	private static final Logger LOG = LogManager.getLogger(CounterLayout.class);

	private final CounterService counterService;
	private final ButtonFactory buttonFactory;
	private final ResourceManager resourceManager;
	private final SessionData session;
	private final TimeDistanceService timeDistanceService;

	private Counter counter;

	public CounterLayout(Counter counter, CounterService counterService, TimeDistanceService timeDistanceService,
			ButtonFactory buttonFactory,
			ResourceManager resourceManager, SessionData session) {
		this.counter = counter;
		this.counterService = counterService;
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
							buttonFactory
									.createResourcedButton(
											resourceManager,
											"CounterLayout.button.dividebycounts.inc.label",
											event -> incrementCounter(),
											session),
							buttonFactory
									.createResourcedButton(
											resourceManager,
											"CounterLayout.button.dividebycounts.dec.label",
											event -> decrementCounter(),
											session));
		} else if (counter.getViewMode() == ViewMode.DIVIDED_BY_COUNTS) {
			int divideBy = counter.getCurrentValue() < 1 ? 1 : counter.getCurrentValue();
			layout.add(new Label(String.format("%1.2f", (counter.getValueToDevide() / divideBy))));
			layout
					.add(
							buttonFactory
									.createResourcedButton(
											resourceManager,
											"CounterLayout.button.dividebycounts.inc.label",
											event -> incrementCounter(),
											session),
							buttonFactory
									.createResourcedButton(
											resourceManager,
											"CounterLayout.button.dividebycounts.dec.label",
											event -> decrementCounter(),
											session));
		} else if (counter.getViewMode() == ViewMode.LAST_CLICK_DATE) {
			if (counter.getLastCounterEvent() != null) {
				String pattern = resourceManager.getLocalizedString("datetime.format", session.getLocalization());
				layout
						.add(
								new DateTimePicker(
										resourceManager
												.getLocalizedString(
														"CounterLayout.datepicker.label.text",
														session.getLocalization()),
										counter.getLastCounterEvent(),
										event -> setLastClickEvent(event.getValue())));
				// layout.add(new Label(counter.getLastCounterEvent().format(DateTimeFormatter.ofPattern(pattern))));
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
		add(layout);
	}

	private void decrementCounter() {
		LOG.info("decrementCounter");
		addToCounter(-1);
	}

	private void addToCounter(int value) {
		LOG.info("addToCounter");
		counter.setLastCounterEvent(LocalDateTime.now());
		counter.setCurrentValue(counter.getCurrentValue() + value);
		counter = counterService.update(counter);
		updateView();
	}

	private void incrementCounter() {
		LOG.info("incrementCounter");
		addToCounter(1);
	}

	private void setLastClickEvent(LocalDateTime localDateTime) {
		LOG.info("setLastClickEvent");
		counter.setLastCounterEvent(localDateTime);
		counter = counterService.update(counter);
		updateView();
	}

}