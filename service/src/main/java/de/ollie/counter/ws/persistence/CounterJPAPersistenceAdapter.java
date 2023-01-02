package de.ollie.counter.ws.persistence;

import java.time.LocalDateTime;

import javax.inject.Inject;
import javax.inject.Named;

import de.ollie.counter.ws.core.model.Counter;
import de.ollie.counter.ws.core.model.CounterHistory;
import de.ollie.counter.ws.core.service.port.persistence.CounterHistoryPersistencePort;

/**
 * A JPA persistence adapter for counters.
 */
@Named
public class CounterJPAPersistenceAdapter extends CounterGeneratedJPAPersistenceAdapter {

	@Inject
	private CounterHistoryPersistencePort counterHistoryPersistencePort;

	@Override
	public Counter create(Counter model) {
		model = super.create(model);
		counterHistoryPersistencePort.create(createCounterHistory(model));
		return model;
	}

	private CounterHistory createCounterHistory(Counter counter) {
		return new CounterHistory()
				.setCounter(counter)
				.setCurrentValue(counter.getCurrentValue())
				.setLastCounterEvent(counter.getLastCounterEvent())
				.setName(counter.getName())
				.setPeriod(counter.getPeriod())
				.setSortOrder(counter.getSortOrder())
				.setTimestamp(LocalDateTime.now())
				.setUser(counter.getUser())
				.setValueToDevide(counter.getValueToDevide())
				.setViewMode(counter.getViewMode());
	}

	@Override
	public Counter update(Counter model) {
		model = super.update(model);
		counterHistoryPersistencePort.create(createCounterHistory(model));
		return model;
	}

}