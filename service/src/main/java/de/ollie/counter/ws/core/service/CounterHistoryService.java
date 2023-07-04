package de.ollie.counter.ws.core.service;

import de.ollie.counter.ws.core.model.Counter;

/**
 * A service interface for CounterHistory management.
 */
public interface CounterHistoryService extends CounterHistoryGeneratedService {

	int getAvgClicksByCounter(Counter counter);

	int getMaxClicksByCounter(Counter counter);

	String getMaxDistanceByCounter(Counter counter);

}