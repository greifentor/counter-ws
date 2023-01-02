package de.ollie.counter.ws.persistence.converter;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import lombok.Generated;
import lombok.RequiredArgsConstructor;

import de.ollie.counter.ws.persistence.entity.CounterHistoryDBO;
import de.ollie.counter.ws.core.model.CounterHistory;

/**
 * A DBO converter for counter_historys.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
@RequiredArgsConstructor
public class CounterHistoryDBOConverter implements ToModelConverter<CounterHistory, CounterHistoryDBO> {

	private final PeriodDBOConverter periodDBOConverter;
	private final UserDBOConverter userDBOConverter;
	private final CounterDBOConverter counterDBOConverter;
	private final ViewModeDBOConverter viewModeDBOConverter;

	public CounterHistoryDBO toDBO(CounterHistory model) {
		if (model == null) {
			return null;
		}
		return new CounterHistoryDBO()
				.setId(model.getId())
				.setCounter(counterDBOConverter.toDBO(model.getCounter()))
				.setUser(userDBOConverter.toDBO(model.getUser()))
				.setCurrentValue(model.getCurrentValue())
				.setLastCounterEvent(model.getLastCounterEvent())
				.setName(model.getName())
				.setPeriod(periodDBOConverter.toDBO(model.getPeriod()))
				.setSortOrder(model.getSortOrder())
				.setTimestamp(model.getTimestamp())
				.setValueToDevide(model.getValueToDevide())
				.setViewMode(viewModeDBOConverter.toDBO(model.getViewMode()));
	}

	@Override
	public CounterHistory toModel(CounterHistoryDBO dbo) {
		if (dbo == null) {
			return null;
		}
		return new CounterHistory()
				.setId(dbo.getId())
				.setCounter(counterDBOConverter.toModel(dbo.getCounter()))
				.setUser(userDBOConverter.toModel(dbo.getUser()))
				.setCurrentValue(dbo.getCurrentValue())
				.setLastCounterEvent(dbo.getLastCounterEvent())
				.setName(dbo.getName())
				.setPeriod(periodDBOConverter.toModel(dbo.getPeriod()))
				.setSortOrder(dbo.getSortOrder())
				.setTimestamp(dbo.getTimestamp())
				.setValueToDevide(dbo.getValueToDevide())
				.setViewMode(viewModeDBOConverter.toModel(dbo.getViewMode()));
	}

	@Override
	public List<CounterHistory> toModel(List<CounterHistoryDBO> dbos) {
		if (dbos == null) {
			return null;
		}
		return dbos.stream().map(this::toModel).collect(Collectors.toList());
	}

}