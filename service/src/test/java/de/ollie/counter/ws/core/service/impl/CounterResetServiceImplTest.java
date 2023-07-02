package de.ollie.counter.ws.core.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.ollie.counter.ws.core.model.Counter;
import de.ollie.counter.ws.core.model.Period;
import de.ollie.counter.ws.core.model.User;
import de.ollie.counter.ws.core.service.CounterService;
import de.ollie.counter.ws.core.service.CurrentTimeService;

@ExtendWith(MockitoExtension.class)
public class CounterResetServiceImplTest {

	private static final LocalDateTime NOW = LocalDateTime.now();

	@Mock
	private Counter counter0;
	@Mock
	private Counter counter1;
	@Mock
	private User user;

	@Mock
	private CounterService counterService;
	@Mock
	private CurrentTimeService currentTimeService;

	@InjectMocks
	private CounterResetServiceImpl unitUnderTest;

	@Nested
	class TestsOfMethod_processCounterPeriodEnds_User {

		@Test
		void doesNothingWithCounterService_passingANullPointer() {
			unitUnderTest.processCounterPeriodEnds(null);
			verifyNoInteractions(counterService);
		}

		@Test
		void updatesNoCounter_databaseHasCountersWithLastCounterEventInCurrentMonthAndYearOnly() {
			// Prepare
			when(currentTimeService.now()).thenReturn(NOW);
			when(counter0.getLastCounterEvent()).thenReturn(NOW);
			when(counter0.getPeriod()).thenReturn(Period.MONTH);
			when(counterService.findAllByUser(user)).thenReturn(List.of(counter0));
			// Run
			unitUnderTest.processCounterPeriodEnds(user);
			// Check
			verify(counterService, never()).update(any(Counter.class));
		}

		@Test
		void updatesNoCounter_databaseHasCounters99YearsOldLastCounterEventsButAllWithPeriodNONE() {
			// Prepare
			when(counter0.getPeriod()).thenReturn(Period.NONE);
			when(counterService.findAllByUser(user)).thenReturn(List.of(counter0));
			// Run
			unitUnderTest.processCounterPeriodEnds(user);
			// Check
			verify(counterService, never()).update(any(Counter.class));
		}

		@Nested
		class TestsForPeriod_MONTH {

			@Test
			void updatesTheCorrectCounter_databaseHasACounterWithLastCounterEventIsOneMonthBeforeAndPeriodMONTH() {
				// Prepare
				when(currentTimeService.now()).thenReturn(NOW);
				when(counter0.getPeriod()).thenReturn(Period.MONTH);
				when(counter0.getLastCounterEvent()).thenReturn(NOW.minusMonths(1));
				when(counterService.findAllByUser(user)).thenReturn(List.of(counter0));
				// Run
				unitUnderTest.processCounterPeriodEnds(user);
				// Check
				verify(counterService, times(1)).update(counter0);
			}

			@Test
			void updateTheLastCounterEventToNOW_databaseHasACounterWithLastCounterEventIsOneMonthBeforeAndPeriodMONTH() {
				// Prepare
				when(currentTimeService.now()).thenReturn(NOW);
				when(counter0.getPeriod()).thenReturn(Period.MONTH);
				when(counter0.getLastCounterEvent()).thenReturn(NOW.minusMonths(1));
				when(counterService.findAllByUser(user)).thenReturn(List.of(counter0));
				// Run
				unitUnderTest.processCounterPeriodEnds(user);
				// Check
				verify(counter0, times(1)).setLastCounterEvent(NOW);
			}

			@Test
			void updateTheCurrentValueToZero_databaseHasACounterWithLastCounterEventIsOneMonthBeforeAndPeriodMONTH() {
				// Prepare
				when(currentTimeService.now()).thenReturn(NOW);
				when(counter0.getPeriod()).thenReturn(Period.MONTH);
				when(counter0.getLastCounterEvent()).thenReturn(NOW.minusMonths(1));
				when(counterService.findAllByUser(user)).thenReturn(List.of(counter0));
				// Run
				unitUnderTest.processCounterPeriodEnds(user);
				// Check
				verify(counter0, times(1)).setCurrentValue(0);
			}

		}

		@Nested
		class TestsForPeriod_YEAR {

			@Test
			void updatesTheCorrectCounter_databaseHasACounterWithLastCounterEventIsOneMonthBeforeAndPeriodYEAR() {
				// Prepare
				when(currentTimeService.now()).thenReturn(NOW);
				when(counter0.getPeriod()).thenReturn(Period.YEAR);
				when(counter0.getLastCounterEvent()).thenReturn(NOW.minusYears(1));
				when(counterService.findAllByUser(user)).thenReturn(List.of(counter0));
				// Run
				unitUnderTest.processCounterPeriodEnds(user);
				// Check
				verify(counterService, times(1)).update(counter0);
			}

			@Test
			void updateTheLastCounterEventToNOW_databaseHasACounterWithLastCounterEventIsOneMonthBeforeAndPeriodYEAR() {
				// Prepare
				when(currentTimeService.now()).thenReturn(NOW);
				when(counter0.getPeriod()).thenReturn(Period.YEAR);
				when(counter0.getLastCounterEvent()).thenReturn(NOW.minusYears(1));
				when(counterService.findAllByUser(user)).thenReturn(List.of(counter0));
				// Run
				unitUnderTest.processCounterPeriodEnds(user);
				// Check
				verify(counter0, times(1)).setLastCounterEvent(NOW);
			}

			@Test
			void updateTheCurrentValueToZero_databaseHasACounterWithLastCounterEventIsOneMonthBeforeAndPeriodYEAR() {
				// Prepare
				when(currentTimeService.now()).thenReturn(NOW);
				when(counter0.getPeriod()).thenReturn(Period.YEAR);
				when(counter0.getLastCounterEvent()).thenReturn(NOW.minusYears(1));
				when(counterService.findAllByUser(user)).thenReturn(List.of(counter0));
				// Run
				unitUnderTest.processCounterPeriodEnds(user);
				// Check
				verify(counter0, times(1)).setCurrentValue(0);
			}

		}

	}

}
