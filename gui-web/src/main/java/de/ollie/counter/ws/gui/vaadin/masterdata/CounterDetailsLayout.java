package de.ollie.counter.ws.gui.vaadin.masterdata;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

import de.ollie.counter.ws.core.model.Counter;
import de.ollie.counter.ws.core.model.Period;
import de.ollie.counter.ws.core.model.User;
import de.ollie.counter.ws.core.model.ViewMode;
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
public class CounterDetailsLayout extends AbstractMasterDataBaseLayout {

	private final ButtonFactory buttonFactory;
	private final Counter model;
	private final CounterService service;
	private final UserService userService;
	private final ResourceManager resourceManager;
	private final SessionData session;
	private final Observer observer;
	private final DetailsLayoutComboBoxItemLabelGenerator<Counter> comboBoxItemLabelGenerator;

	private ComboBox<User> comboBoxUser;
	private TextField textFieldName;
	private IntegerField integerFieldCurrentValue;
	private IntegerField integerFieldSortOrder;
	private ComboBox<Period> comboBoxPeriod;
	private ComboBox<ViewMode> comboBoxViewMode;
	private NumberField numberFieldValueToDevide;

	@Override
	public void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		createButtons();
		comboBoxUser = createComboBox("CounterDetailsLayout.field.user.label", model.getUser(), userService.findAll().toArray(new User[0]));
		comboBoxUser
				.setItemLabelGenerator(
						user  -> comboBoxItemLabelGenerator != null
								? comboBoxItemLabelGenerator.getLabel(Counter.USER, user)
								: "" + user.getName());
		textFieldName = createTextField("CounterDetailsLayout.field.name.label", model.getName());
		integerFieldCurrentValue = createIntegerField("CounterDetailsLayout.field.currentvalue.label", model.getCurrentValue(), null, null, null);
		integerFieldSortOrder = createIntegerField("CounterDetailsLayout.field.sortorder.label", model.getSortOrder(), null, null, null);
		comboBoxPeriod = createComboBox("CounterDetailsLayout.field.period.label", model.getPeriod(), Period.values());
		comboBoxViewMode = createComboBox("CounterDetailsLayout.field.viewmode.label", model.getViewMode(), ViewMode.values());
		numberFieldValueToDevide = createNumberField("CounterDetailsLayout.field.valuetodevide.label", model.getValueToDevide(), null, null, null);
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
				textFieldName,
				integerFieldCurrentValue,
				comboBoxUser,
				integerFieldSortOrder,
				comboBoxPeriod,
				comboBoxViewMode,
				numberFieldValueToDevide,
				getMasterDataButtonLayout(model.getId() > 0));
		textFieldName.focus();
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
		model.setName(textFieldName.getValue());
		model.setCurrentValue(integerFieldCurrentValue.getValue());
		model.setUser(comboBoxUser.getValue());
		model.setSortOrder(integerFieldSortOrder.getValue());
		model.setPeriod(comboBoxPeriod.getValue());
		model.setViewMode(comboBoxViewMode.getValue());
		model.setValueToDevide(numberFieldValueToDevide.getValue());
		observer.save(service.update(model));
	}

}