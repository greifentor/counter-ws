package de.ollie.counter.ws.core.service.impl;

import java.time.LocalDateTime;

import javax.inject.Named;

import de.ollie.counter.ws.core.service.CurrentTimeService;

@Named
public class CurrentTimeServiceImpl implements CurrentTimeService {

	@Override
	public LocalDateTime now() {
		return LocalDateTime.now();
	}

}
