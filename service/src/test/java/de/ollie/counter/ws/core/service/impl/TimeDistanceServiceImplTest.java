package de.ollie.counter.ws.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import de.ollie.counter.ws.core.service.TimeDistanceService;

@ExtendWith(MockitoExtension.class)
class TimeDistanceServiceImplTest {

	@InjectMocks
	private TimeDistanceServiceImpl unitUnderTest;

	@Nested
	class TestsOfMethod_getTimeDistanceAsString_LocalDateTime_LocalDateTime {

		@Test
		void throwsAnException_passingANullValueAsDateTime0() {
			assertThrows(
					NullPointerException.class,
					() -> unitUnderTest.getTimeDistanceAsString(null, LocalDateTime.now()));
		}

		@Test
		void throwsAnException_passingANullValueAsDateTime1() {
			assertThrows(
					NullPointerException.class,
					() -> unitUnderTest.getTimeDistanceAsString(LocalDateTime.now(), null));
		}

		@Test
		void returnsASpecialStringForAnEmptyResult_passingSameDateTimeTwice() {
			LocalDateTime dateTime = LocalDateTime.now();
			assertEquals(
					TimeDistanceService.NO_DISTANCE_STRING,
					unitUnderTest.getTimeDistanceAsString(dateTime, dateTime));
		}

		@Test
		void returnsAStringWithContent3m_passingTwoDatesWith3MinutesDistance() {
			LocalDateTime dateTime0 = LocalDateTime.now();
			LocalDateTime dateTime1 = dateTime0.plusMinutes(3);
			assertEquals("3m", unitUnderTest.getTimeDistanceAsString(dateTime0, dateTime1));
			assertEquals("3m", unitUnderTest.getTimeDistanceAsString(dateTime1, dateTime0));
		}

		@Test
		void returnsAStringWithContent2h3m_passingTwoDatesWith2HoursAnd3MinutesDistance() {
			LocalDateTime dateTime0 = LocalDateTime.now();
			LocalDateTime dateTime1 = dateTime0.plusHours(2).plusMinutes(3);
			assertEquals("2h 3m", unitUnderTest.getTimeDistanceAsString(dateTime0, dateTime1));
			assertEquals("2h 3m", unitUnderTest.getTimeDistanceAsString(dateTime1, dateTime0));
		}

		@Test
		void returnsAStringWithContent1w3d2h3m_passingTwoDatesWith10Days2HoursAnd3MinutesDistance() {
			LocalDateTime dateTime0 = LocalDateTime.now();
			LocalDateTime dateTime1 = dateTime0.plusDays(10).plusHours(2).plusMinutes(3);
			assertEquals("1w 3d 2h 3m", unitUnderTest.getTimeDistanceAsString(dateTime0, dateTime1));
			assertEquals("1w 3d 2h 3m", unitUnderTest.getTimeDistanceAsString(dateTime1, dateTime0));
		}

	}

}
