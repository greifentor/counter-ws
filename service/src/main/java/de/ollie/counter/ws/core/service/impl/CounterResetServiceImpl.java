package de.ollie.counter.ws.core.service.impl;

import javax.inject.Named;

import de.ollie.counter.ws.core.model.Counter;
import de.ollie.counter.ws.core.model.Period;
import de.ollie.counter.ws.core.model.User;
import de.ollie.counter.ws.core.service.CounterResetService;
import de.ollie.counter.ws.core.service.CounterService;
import de.ollie.counter.ws.core.service.CurrentTimeService;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class CounterResetServiceImpl implements CounterResetService {

	private final CounterService counterService;
	private final CurrentTimeService currentTimeService;

	@Override
	public void processCounterPeriodEnds(User user) {
		if (user == null) {
			return;
		}
		updateCounterWithExpiredPeriods(user);
	}

	private void updateCounterWithExpiredPeriods(User user) {
		counterService.findAllByUser(user).stream().filter(this::isOutOfPeriod).forEach(this::updateCounter);
	}

	private boolean isOutOfPeriod(Counter counter) {
		if (!hasPeriodToProcess(counter)) {
			return false;
		}
		return isPeriodMonthAndLastCounterEventInMonthBefore(counter)
				|| isPeriodYearAndLastCounterEventInYearBefore(counter);
	}

	private boolean hasPeriodToProcess(Counter counter) {
		return !Period.NONE.equals(counter.getPeriod());
	}

	private boolean isPeriodMonthAndLastCounterEventInMonthBefore(Counter counter) {
		return (counter.getPeriod() == Period.MONTH)
				&& !currentTimeService.now().getMonth().equals(counter.getLastCounterEvent().getMonth());
	}

	private boolean isPeriodYearAndLastCounterEventInYearBefore(Counter counter) {
		return (counter.getPeriod() == Period.YEAR)
				&& (currentTimeService.now().getYear() != counter.getLastCounterEvent().getYear());
	}

	private void updateCounter(Counter counter) {
		counter.setCurrentValue(0);
		counter.setLastCounterEvent(currentTimeService.now());
		counterService.update(counter);
	}

}