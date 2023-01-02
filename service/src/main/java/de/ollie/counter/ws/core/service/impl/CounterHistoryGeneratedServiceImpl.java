package de.ollie.counter.ws.core.service.impl;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import de.ollie.counter.ws.core.model.Page;
import de.ollie.counter.ws.core.model.PageParameters;
import de.ollie.counter.ws.core.model.CounterHistory;
import de.ollie.counter.ws.core.model.User;
import de.ollie.counter.ws.core.service.port.persistence.CounterHistoryPersistencePort;
import de.ollie.counter.ws.core.service.CounterHistoryService;
import lombok.Generated;

import de.ollie.counter.ws.core.model.User;

/**
 * A generated service interface implementation for CounterHistory management.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public abstract class CounterHistoryGeneratedServiceImpl implements CounterHistoryService {

	@Inject
	protected CounterHistoryPersistencePort persistencePort;

	@Override
	public CounterHistory create(CounterHistory model) {
		return persistencePort.create(model);
	}

	@Override
	public List<CounterHistory> findAll() {
		return persistencePort.findAll();
	}

	@Override
	public Page<CounterHistory> findAll(PageParameters pageParameters) {
		return persistencePort.findAll(pageParameters);
	}

	@Override
	public Optional<CounterHistory> findById(Long id) {
		return persistencePort.findById(id);
	}

	@Override
	public CounterHistory update(CounterHistory model) {
		return persistencePort.update(model);
	}

	@Override
	public void delete(CounterHistory model) {
		persistencePort.delete(model);
	}

	@Override
	public List<CounterHistory> findAllByUser(User user) {
		return persistencePort.findAllByUser(user);
	}

}