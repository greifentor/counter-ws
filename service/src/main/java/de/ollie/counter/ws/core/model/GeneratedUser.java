package de.ollie.counter.ws.core.model;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

/**
 * A model for users.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Accessors(chain = true)
@Data
@Generated
public class GeneratedUser {

	public static final String ID = "ID";
	public static final String GLOBALID = "GLOBALID";
	public static final String NAME = "NAME";
	public static final String TOKEN = "TOKEN";

	private long id;
	private String globalId;
	private String name;
	private String token;

}