package de.ollie.counter.ws.gui.vaadin.masterdata;

import java.time.format.DateTimeFormatter;

import javax.inject.Named;

import de.ollie.counter.ws.core.model.CounterHistory;
import lombok.Generated;

/**
 * An implementation of the MasterDataGridFieldRenderer interface for counter historys.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
public class CounterHistoryMasterDataGridFieldRenderer implements MasterDataGridFieldRenderer<CounterHistory> {

	final static DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Override
	public Object getHeaderString(String fieldName, CounterHistory model) {
		if (CounterHistory.COUNTER.equals(fieldName)) {
			return model.getCounter() != null ? model.getCounter().getName() : "-";
		} else if (CounterHistory.LASTCOUNTEREVENT.equals(fieldName)) {
			return model.getLastCounterEvent() != null ? model.getLastCounterEvent().format(DATETIME_FORMATTER) : "-";
		} else if (CounterHistory.USER.equals(fieldName)) {
			return model.getUser() != null ? model.getUser().getName() : "-";
		}
		return null;
	}

	@Override
	public boolean hasRenderingFor(String fieldName) {
		if (CounterHistory.COUNTER.equals(fieldName) || CounterHistory.LASTCOUNTEREVENT.equals(fieldName)
				|| CounterHistory.USER.equals(fieldName)) {
			return true;
		}
		return false;
	}

}