package de.ollie.counter.ws.core.service.port.persistence;

import java.util.List;
import java.util.Optional;

import de.ollie.counter.ws.core.model.Page;
import de.ollie.counter.ws.core.model.PageParameters;
import de.ollie.counter.ws.core.model.Counter;
import de.ollie.counter.ws.core.model.User;
import lombok.Generated;

import de.ollie.counter.ws.core.model.User;

/**
 * A generated persistence port interface for Counter CRUD operations.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public interface CounterGeneratedPersistencePort {

	Counter create(Counter model);

	List<Counter> findAll();

	Page<Counter> findAll(PageParameters pageParameters);

	Optional<Counter> findById(Long id);

	Counter update(Counter model);

	void delete(Counter model);

	List<Counter> findAllByUser(User user);

}