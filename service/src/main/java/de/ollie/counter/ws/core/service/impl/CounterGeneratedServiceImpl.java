package de.ollie.counter.ws.core.service.impl;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import de.ollie.counter.ws.core.model.Page;
import de.ollie.counter.ws.core.model.PageParameters;
import de.ollie.counter.ws.core.model.Counter;
import de.ollie.counter.ws.core.model.User;
import de.ollie.counter.ws.core.service.port.persistence.CounterPersistencePort;
import de.ollie.counter.ws.core.service.CounterService;
import lombok.Generated;

import de.ollie.counter.ws.core.model.User;

/**
 * A generated service interface implementation for Counter management.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public abstract class CounterGeneratedServiceImpl implements CounterService {

	@Inject
	protected CounterPersistencePort persistencePort;

	@Override
	public Counter create(Counter model) {
		return persistencePort.create(model);
	}

	@Override
	public List<Counter> findAll() {
		return persistencePort.findAll();
	}

	@Override
	public Page<Counter> findAll(PageParameters pageParameters) {
		return persistencePort.findAll(pageParameters);
	}

	@Override
	public Optional<Counter> findById(Long id) {
		return persistencePort.findById(id);
	}

	@Override
	public Counter update(Counter model) {
		return persistencePort.update(model);
	}

	@Override
	public void delete(Counter model) {
		persistencePort.delete(model);
	}

	@Override
	public List<Counter> findAllByUser(User user) {
		return persistencePort.findAllByUser(user);
	}

}