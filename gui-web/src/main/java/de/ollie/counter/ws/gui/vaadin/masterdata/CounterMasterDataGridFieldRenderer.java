package de.ollie.counter.ws.gui.vaadin.masterdata;

import javax.inject.Named;

import de.ollie.counter.ws.core.model.Counter;

import lombok.Generated;

/**
 * An implementation of the MasterDataGridFieldRenderer interface for counters.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
public class CounterMasterDataGridFieldRenderer implements MasterDataGridFieldRenderer<Counter> {

	@Override
	public Object getHeaderString(String fieldName, Counter model) {
		if (Counter.USER.equals(fieldName)) {
			return model.getUser() != null ? model.getUser().getName() : "-";
		}
		return null;
	}

	@Override
	public boolean hasRenderingFor(String fieldName) {
		if (Counter.USER.equals(fieldName)) {
			return true;
		}
		return false;
	}

}