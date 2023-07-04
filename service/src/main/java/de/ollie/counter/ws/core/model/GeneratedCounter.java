package de.ollie.counter.ws.core.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

/**
 * A model for counters.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Accessors(chain = true)
@Data
@Generated
public class GeneratedCounter {

	public static final String ID = "ID";
	public static final String USER = "USER";
	public static final String ADDITIONALDISPLAY1 = "ADDITIONALDISPLAY1";
	public static final String CURRENTVALUE = "CURRENTVALUE";
	public static final String LASTCOUNTEREVENT = "LASTCOUNTEREVENT";
	public static final String NAME = "NAME";
	public static final String PERIOD = "PERIOD";
	public static final String SORTORDER = "SORTORDER";
	public static final String VALUETODEVIDE = "VALUETODEVIDE";
	public static final String VIEWMODE = "VIEWMODE";

	private long id;
	private User user;
	private AdditionalDisplayMode additionalDisplay1;
	private int currentValue;
	private LocalDateTime lastCounterEvent;
	private String name;
	private Period period;
	private int sortOrder;
	private Double valueToDevide;
	private ViewMode viewMode;

}