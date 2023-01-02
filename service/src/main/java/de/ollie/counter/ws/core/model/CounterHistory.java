package de.ollie.counter.ws.core.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * A model for counter_historys.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@Generated
@ToString(callSuper = true)
public class CounterHistory extends GeneratedCounterHistory {

	@Override
	public CounterHistory setId(long id) {
		super.setId(id);
		return this;
	}

	@Override
	public CounterHistory setCounter(Counter counter) {
		super.setCounter(counter);
		return this;
	}

	@Override
	public CounterHistory setUser(User user) {
		super.setUser(user);
		return this;
	}

	@Override
	public CounterHistory setCurrentValue(int currentValue) {
		super.setCurrentValue(currentValue);
		return this;
	}

	@Override
	public CounterHistory setLastCounterEvent(LocalDateTime lastCounterEvent) {
		super.setLastCounterEvent(lastCounterEvent);
		return this;
	}

	@Override
	public CounterHistory setName(String name) {
		super.setName(name);
		return this;
	}

	@Override
	public CounterHistory setPeriod(Period period) {
		super.setPeriod(period);
		return this;
	}

	@Override
	public CounterHistory setSortOrder(int sortOrder) {
		super.setSortOrder(sortOrder);
		return this;
	}

	@Override
	public CounterHistory setTimestamp(LocalDateTime timestamp) {
		super.setTimestamp(timestamp);
		return this;
	}

	@Override
	public CounterHistory setValueToDevide(Double valueToDevide) {
		super.setValueToDevide(valueToDevide);
		return this;
	}

	@Override
	public CounterHistory setViewMode(ViewMode viewMode) {
		super.setViewMode(viewMode);
		return this;
	}

}