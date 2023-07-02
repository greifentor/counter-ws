package de.ollie.counter.ws.core.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.inject.Named;

import de.ollie.counter.ws.core.service.TimeDistanceService;
import lombok.AllArgsConstructor;
import lombok.Data;

@Named
public class TimeDistanceServiceImpl implements TimeDistanceService {

	@Override
	public String getTimeDistanceAsString(LocalDateTime dateTime0, LocalDateTime dateTime1) {
		if (dateTime0.equals(dateTime1)) {
			return TimeDistanceService.NO_DISTANCE_STRING;
		}
		return distanceToString(getDistanceInMinutes(dateTime0, dateTime1));
	}

	@Override
	public long getDistanceInMinutes(LocalDateTime dateTime0, LocalDateTime dateTime1) {
		return Duration.between(getMin(dateTime0, dateTime1), getMax(dateTime0, dateTime1)).toMinutes();
	}

	private LocalDateTime getMin(LocalDateTime dateTime0, LocalDateTime dateTime1) {
		return dateTime0.isBefore(dateTime1) ? dateTime0 : dateTime1;
	}

	private LocalDateTime getMax(LocalDateTime dateTime0, LocalDateTime dateTime1) {
		return dateTime0.isAfter(dateTime1) ? dateTime0 : dateTime1;
	}

	@AllArgsConstructor
	@Data
	private static class TimeUnitData {
		private String symbol;
		private long factor;
	}

	private static final TimeUnitData[] TIME_UNIT_DATA =
			new TimeUnitData[] {
					new TimeUnitData("w", 10080),
					new TimeUnitData("d", 1440),
					new TimeUnitData("h", 60),
					new TimeUnitData("m", 1) };

	@Override
	public String distanceToString(long distanceInMinutes) {
		StringBuilder sb = new StringBuilder();
		for (TimeUnitData timeUnitData : TIME_UNIT_DATA) {
			long i = distanceInMinutes / timeUnitData.factor;
			if (i > 0) {
				if (sb.length() > 0) {
					sb.append(" ");
				}
				sb.append(i + timeUnitData.symbol);
				distanceInMinutes = distanceInMinutes % timeUnitData.factor;
			}
		}
		return sb.toString();
	}

}