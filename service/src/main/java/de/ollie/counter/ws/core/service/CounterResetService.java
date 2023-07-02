package de.ollie.counter.ws.core.service;

import de.ollie.counter.ws.core.model.User;

public interface CounterResetService {

	void processCounterPeriodEnds(User user);

}
