package de.ollie.counter.ws.persistence;

import static de.ollie.counter.ws.util.Check.ensure;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import de.ollie.counter.ws.core.model.Page;
import de.ollie.counter.ws.core.model.PageParameters;
import de.ollie.counter.ws.core.model.CounterHistory;
import de.ollie.counter.ws.core.service.exception.NotNullConstraintViolationException;
import de.ollie.counter.ws.core.service.port.persistence.CounterHistoryPersistencePort;
import de.ollie.counter.ws.persistence.converter.PageConverter;
import de.ollie.counter.ws.persistence.converter.PageParametersToPageableConverter;
import de.ollie.counter.ws.persistence.converter.CounterHistoryDBOConverter;
import de.ollie.counter.ws.persistence.entity.CounterHistoryDBO;
import de.ollie.counter.ws.persistence.repository.CounterHistoryDBORepository;
import de.ollie.counter.ws.persistence.converter.UserDBOConverter;
import lombok.Generated;

import de.ollie.counter.ws.core.model.User;

/**
 * A generated JPA persistence adapter for counter_historys.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public abstract class CounterHistoryGeneratedJPAPersistenceAdapter implements CounterHistoryPersistencePort {

	@Inject
	protected CounterHistoryDBOConverter converter;
	@Inject
	protected CounterHistoryDBORepository repository;
	@Inject
	protected UserDBOConverter userDBOConverter;

	@Inject
	protected PageParametersToPageableConverter pageParametersToPageableConverter;

	protected PageConverter<CounterHistory, CounterHistoryDBO> pageConverter;

	@PostConstruct
	public void postConstruct() {
		pageConverter = new PageConverter<>(converter);
	}

	@Override
	public CounterHistory create(CounterHistory model) {
		model.setId(-1);
		return converter.toModel(repository.save(converter.toDBO(model)));
	}

	@Override
	public List<CounterHistory> findAll() {
		return converter.toModel(repository.findAll());
	}

	@Override
	public Page<CounterHistory> findAll(PageParameters pageParameters) {
		return pageConverter.convert(repository.findAll(pageParametersToPageableConverter.convert(pageParameters)));
	}

	@Override
	public Optional<CounterHistory> findById(Long id) {
		return repository.findById(id).map(dbo -> converter.toModel(dbo));
	}

	@Override
	public CounterHistory update(CounterHistory model) {
		ensure(
				model.getCounter() != null,
				() -> new NotNullConstraintViolationException("CounterHistory field counter cannot be null.", "CounterHistory", "counter"));
		ensure(
				model.getUser() != null,
				() -> new NotNullConstraintViolationException("CounterHistory field user cannot be null.", "CounterHistory", "user"));
		ensure(
				model.getName() != null,
				() -> new NotNullConstraintViolationException("CounterHistory field name cannot be null.", "CounterHistory", "name"));
		ensure(
				model.getTimestamp() != null,
				() -> new NotNullConstraintViolationException("CounterHistory field timestamp cannot be null.", "CounterHistory", "timestamp"));
		ensure(
				model.getViewMode() != null,
				() -> new NotNullConstraintViolationException("CounterHistory field viewMode cannot be null.", "CounterHistory", "viewMode"));
		return converter.toModel(repository.save(converter.toDBO(model)));
	}

	@Override
	public void delete(CounterHistory model) {
		repository.deleteById(model.getId());
	}

	@Override
	public List<CounterHistory> findAllByUser(User user) {
		return converter.toModel(repository.findAllByUser(userDBOConverter.toDBO(user)));
	}

}