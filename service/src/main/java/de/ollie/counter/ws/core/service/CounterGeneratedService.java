package de.ollie.counter.ws.core.service;

import java.util.List;
import java.util.Optional;

import de.ollie.counter.ws.core.model.Page;
import de.ollie.counter.ws.core.model.PageParameters;
import de.ollie.counter.ws.core.model.Counter;
import de.ollie.counter.ws.core.model.User;
import lombok.Generated;

import de.ollie.counter.ws.core.model.User;

/**
 * A generated service interface for Counter management.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public interface CounterGeneratedService {

	Counter create(Counter model);

	List<Counter> findAll();

	Page<Counter> findAll(PageParameters pageParameters);

	Optional<Counter> findById(Long id);

	Counter update(Counter model);

	void delete(Counter model);

	List<Counter> findAllByUser(User user);

}