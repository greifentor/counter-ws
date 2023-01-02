package de.ollie.counter.ws.core.service.port.persistence;

import java.util.List;
import java.util.Optional;

import de.ollie.counter.ws.core.model.Page;
import de.ollie.counter.ws.core.model.PageParameters;
import de.ollie.counter.ws.core.model.CounterHistory;
import de.ollie.counter.ws.core.model.User;
import lombok.Generated;

import de.ollie.counter.ws.core.model.User;

/**
 * A generated persistence port interface for CounterHistory CRUD operations.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public interface CounterHistoryGeneratedPersistencePort {

	CounterHistory create(CounterHistory model);

	List<CounterHistory> findAll();

	Page<CounterHistory> findAll(PageParameters pageParameters);

	Optional<CounterHistory> findById(Long id);

	CounterHistory update(CounterHistory model);

	void delete(CounterHistory model);

	List<CounterHistory> findAllByUser(User user);

}