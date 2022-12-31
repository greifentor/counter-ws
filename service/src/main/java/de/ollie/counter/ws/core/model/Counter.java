package de.ollie.counter.ws.core.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * A model for counters.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@Generated
@ToString(callSuper = true)
public class Counter extends GeneratedCounter {

	@Override
	public Counter setId(long id) {
		super.setId(id);
		return this;
	}

	@Override
	public Counter setUser(User user) {
		super.setUser(user);
		return this;
	}

	@Override
	public Counter setCurrentValue(int currentValue) {
		super.setCurrentValue(currentValue);
		return this;
	}

	@Override
	public Counter setLastCounterEvent(LocalDateTime lastCounterEvent) {
		super.setLastCounterEvent(lastCounterEvent);
		return this;
	}

	@Override
	public Counter setName(String name) {
		super.setName(name);
		return this;
	}

	@Override
	public Counter setPeriod(Period period) {
		super.setPeriod(period);
		return this;
	}

	@Override
	public Counter setSortOrder(int sortOrder) {
		super.setSortOrder(sortOrder);
		return this;
	}

	@Override
	public Counter setValueToDevide(Double valueToDevide) {
		super.setValueToDevide(valueToDevide);
		return this;
	}

	@Override
	public Counter setViewMode(ViewMode viewMode) {
		super.setViewMode(viewMode);
		return this;
	}

}