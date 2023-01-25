package de.ollie.counter.ws.core.service.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import de.ollie.counter.ws.core.model.Counter;
import de.ollie.counter.ws.core.service.port.persistence.CounterHistoryPersistencePort;

/**
 * A service interface implementation for Counter management.
 */
@Named
public class CounterServiceImpl extends CounterGeneratedServiceImpl {

	@Inject
	private CounterHistoryPersistencePort counterHistoryPersistencePort;

	@Override
	@Transactional
	public void delete(Counter model) {
		counterHistoryPersistencePort.findAllByCounter(model).forEach(counterHistoryPersistencePort::delete);
		super.delete(model);
	}

}