package de.ollie.counter.ws.persistence;

import static de.ollie.counter.ws.util.Check.ensure;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import de.ollie.counter.ws.core.model.Counter;
import de.ollie.counter.ws.core.model.Page;
import de.ollie.counter.ws.core.model.PageParameters;
import de.ollie.counter.ws.core.model.User;
import de.ollie.counter.ws.core.service.exception.NotNullConstraintViolationException;
import de.ollie.counter.ws.core.service.port.persistence.CounterPersistencePort;
import de.ollie.counter.ws.persistence.converter.CounterDBOConverter;
import de.ollie.counter.ws.persistence.converter.PageConverter;
import de.ollie.counter.ws.persistence.converter.PageParametersToPageableConverter;
import de.ollie.counter.ws.persistence.converter.UserDBOConverter;
import de.ollie.counter.ws.persistence.entity.CounterDBO;
import de.ollie.counter.ws.persistence.repository.CounterDBORepository;
import lombok.Generated;

/**
 * A generated JPA persistence adapter for counters.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public abstract class CounterGeneratedJPAPersistenceAdapter implements CounterPersistencePort {

	@Inject
	protected CounterDBOConverter converter;
	@Inject
	protected CounterDBORepository repository;
	@Inject
	protected UserDBOConverter userDBOConverter;

	@Inject
	protected PageParametersToPageableConverter pageParametersToPageableConverter;

	protected PageConverter<Counter, CounterDBO> pageConverter;

	@PostConstruct
	public void postConstruct() {
		pageConverter = new PageConverter<>(converter);
	}

	@Override
	public Counter create(Counter model) {
		model.setId(-1);
		return converter.toModel(repository.save(converter.toDBO(model)));
	}

	@Override
	public List<Counter> findAll() {
		return converter.toModel(repository.findAll());
	}

	@Override
	public Page<Counter> findAll(PageParameters pageParameters) {
		return pageConverter.convert(repository.findAll(pageParametersToPageableConverter.convert(pageParameters)));
	}

	@Override
	public Optional<Counter> findById(Long id) {
		return repository.findById(id).map(dbo -> converter.toModel(dbo));
	}

	@Override
	public Counter update(Counter model) {
		ensure(
				model.getUser() != null,
				() -> new NotNullConstraintViolationException("Counter field user cannot be null.", "Counter", "user"));
		ensure(
				model.getName() != null,
				() -> new NotNullConstraintViolationException("Counter field name cannot be null.", "Counter", "name"));
		ensure(
				model.getViewMode() != null,
				() -> new NotNullConstraintViolationException("Counter field viewMode cannot be null.", "Counter", "viewMode"));
		return converter.toModel(repository.save(converter.toDBO(model)));
	}

	@Override
	public void delete(Counter model) {
		repository.deleteById(model.getId());
	}

	@Override
	public List<Counter> findAllByUser(User user) {
		return converter.toModel(repository.findAllByUser(userDBOConverter.toDBO(user)));
	}

}