package de.ollie.counter.ws.gui.vaadin.masterdata;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.textfield.IntegerField;

import de.ollie.counter.ws.core.model.Counter;
import de.ollie.counter.ws.core.model.CounterHistory;
import de.ollie.counter.ws.core.model.User;
import de.ollie.counter.ws.core.service.CounterHistoryService;
import de.ollie.counter.ws.core.service.CounterService;
import de.ollie.counter.ws.core.service.localization.ResourceManager;
import de.ollie.counter.ws.core.service.UserService;
import de.ollie.counter.ws.gui.SessionData;
import de.ollie.counter.ws.gui.vaadin.component.AbstractMasterDataBaseLayout;
import de.ollie.counter.ws.gui.vaadin.component.ButtonFactory;
import lombok.Generated;
import lombok.RequiredArgsConstructor;

/**
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@RequiredArgsConstructor
public class CounterHistoryDetailsLayout extends AbstractMasterDataBaseLayout {

	private final ButtonFactory buttonFactory;
	private final CounterHistory model;
	private final CounterHistoryService service;
	private final CounterService counterService;
	private final UserService userService;
	private final ResourceManager resourceManager;
	private final SessionData session;
	private final Observer observer;
	private final DetailsLayoutComboBoxItemLabelGenerator<CounterHistory> comboBoxItemLabelGenerator;

	private ComboBox<Counter> comboBoxCounter;
	private ComboBox<User> comboBoxUser;
	private IntegerField integerFieldCurrentValue;
	private DateTimePicker dateTimePickerLastCounterEvent;

	@Override
	public void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		createButtons();
		comboBoxCounter = createComboBox("CounterHistoryDetailsLayout.field.counter.label", model.getCounter(), counterService.findAll().toArray(new Counter[0]));
		comboBoxCounter
				.setItemLabelGenerator(
						counter  -> comboBoxItemLabelGenerator != null
								? comboBoxItemLabelGenerator.getLabel(CounterHistory.COUNTER, counter)
								: "" + counter.getName());
		comboBoxUser = createComboBox("CounterHistoryDetailsLayout.field.user.label", model.getUser(), userService.findAll().toArray(new User[0]));
		comboBoxUser
				.setItemLabelGenerator(
						user  -> comboBoxItemLabelGenerator != null
								? comboBoxItemLabelGenerator.getLabel(CounterHistory.USER, user)
								: "" + user.getName());
		integerFieldCurrentValue = createIntegerField("CounterHistoryDetailsLayout.field.currentvalue.label", model.getCurrentValue(), null, null, null);
		dateTimePickerLastCounterEvent = new DateTimePicker(resourceManager.getLocalizedString("CounterHistoryDetailsLayout.field.lastcounterevent.label", session.getLocalization()), model.getLastCounterEvent(), event -> {});
		getStyle().set("-moz-border-radius", "4px");
		getStyle().set("-webkit-border-radius", "4px");
		getStyle().set("border-radius", "4px");
		getStyle().set("border", "1px solid #A9A9A9");
		getStyle()
				.set(
						"box-shadow",
						"10px 10px 20px #e4e4e4, -10px 10px 20px #e4e4e4, -10px -10px 20px #e4e4e4, 10px -10px 20px #e4e4e4");
		setMargin(false);
		setWidthFull();
		add(
				comboBoxCounter,
				comboBoxUser,
				integerFieldCurrentValue,
				dateTimePickerLastCounterEvent,
				getMasterDataButtonLayout(model.getId() > 0));
		comboBoxCounter.focus();
	}

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
	protected void remove() {
		service.delete(model);
		observer.remove();
	}

	@Override
	protected void save() {
		model.setCounter(comboBoxCounter.getValue());
		model.setUser(comboBoxUser.getValue());
		model.setCurrentValue(integerFieldCurrentValue.getValue());
		model.setLastCounterEvent(dateTimePickerLastCounterEvent.getValue());
		observer.save(service.update(model));
	}

}