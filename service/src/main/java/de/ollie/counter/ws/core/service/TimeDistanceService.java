package de.ollie.counter.ws.core.service;

import java.time.LocalDateTime;

/**
 * @author ollie (11.01.2023)
 */
public interface TimeDistanceService {

	static final String NO_DISTANCE_STRING = "-";

	String getTimeDistanceAsString(LocalDateTime dateTime1, LocalDateTime dateTime2);

}