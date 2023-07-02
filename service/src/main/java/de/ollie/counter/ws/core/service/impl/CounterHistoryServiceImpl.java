package de.ollie.counter.ws.core.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import de.ollie.counter.ws.core.model.Counter;
import de.ollie.counter.ws.core.model.CounterHistory;
import de.ollie.counter.ws.core.service.TimeDistanceService;

/**
 * A service interface implementation for CounterHistory management.
 */
@Named
public class CounterHistoryServiceImpl extends CounterHistoryGeneratedServiceImpl {

	@Inject
	private TimeDistanceService timeDistanceService;

	@Override
	public String getMaxDistanceByCounter(Counter counter) {
		List<CounterHistory> historyEntries =
				findAllByCounter(counter)
						.stream()
						.filter(ch -> ch.getLastCounterEvent() != null)
						.sorted((ch0, ch1) -> ch0.getLastCounterEvent().compareTo(ch1.getLastCounterEvent()))
						.collect(Collectors.toList());
		historyEntries.add(new CounterHistory().setCounter(counter).setLastCounterEvent(LocalDateTime.now()));
		long max = 0;
		for (int i = 0, leni = historyEntries.size() - 1; i <= leni; i++) {
			if (i > 0) {
				max =
						Math
								.max(
										max,
										timeDistanceService
												.getDistanceInMinutes(
														historyEntries.get(i - 1).getLastCounterEvent(),
														historyEntries.get(i).getLastCounterEvent()));
			}
		}
		return timeDistanceService.distanceToString(max);
	}

};