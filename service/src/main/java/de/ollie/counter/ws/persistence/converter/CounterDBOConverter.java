package de.ollie.counter.ws.persistence.converter;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import lombok.Generated;
import lombok.RequiredArgsConstructor;

import de.ollie.counter.ws.persistence.entity.CounterDBO;
import de.ollie.counter.ws.core.model.Counter;

/**
 * A DBO converter for counters.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
@RequiredArgsConstructor
public class CounterDBOConverter implements ToModelConverter<Counter, CounterDBO> {

	private final PeriodDBOConverter periodDBOConverter;
	private final UserDBOConverter userDBOConverter;
	private final ViewModeDBOConverter viewModeDBOConverter;

	public CounterDBO toDBO(Counter model) {
		if (model == null) {
			return null;
		}
		return new CounterDBO()
				.setId(model.getId())
				.setUser(userDBOConverter.toDBO(model.getUser()))
				.setCurrentValue(model.getCurrentValue())
				.setLastCounterEvent(model.getLastCounterEvent())
				.setName(model.getName())
				.setPeriod(periodDBOConverter.toDBO(model.getPeriod()))
				.setSortOrder(model.getSortOrder())
				.setValueToDevide(model.getValueToDevide())
				.setViewMode(viewModeDBOConverter.toDBO(model.getViewMode()));
	}

	@Override
	public Counter toModel(CounterDBO dbo) {
		if (dbo == null) {
			return null;
		}
		return new Counter()
				.setId(dbo.getId())
				.setUser(userDBOConverter.toModel(dbo.getUser()))
				.setCurrentValue(dbo.getCurrentValue())
				.setLastCounterEvent(dbo.getLastCounterEvent())
				.setName(dbo.getName())
				.setPeriod(periodDBOConverter.toModel(dbo.getPeriod()))
				.setSortOrder(dbo.getSortOrder())
				.setValueToDevide(dbo.getValueToDevide())
				.setViewMode(viewModeDBOConverter.toModel(dbo.getViewMode()));
	}

	@Override
	public List<Counter> toModel(List<CounterDBO> dbos) {
		if (dbos == null) {
			return null;
		}
		return dbos.stream().map(this::toModel).collect(Collectors.toList());
	}

}