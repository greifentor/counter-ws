package de.ollie.counter.ws.core.service;

import de.ollie.counter.ws.core.model.Counter;

/**
 * A service interface for CounterHistory management.
 */
public interface CounterHistoryService extends CounterHistoryGeneratedService {

	String getMaxDistanceByCounter(Counter counter);

}