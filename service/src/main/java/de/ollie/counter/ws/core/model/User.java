package de.ollie.counter.ws.core.model;

import de.ollie.counter.ws.core.model.AuthorizationUser;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * A model for users.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@Generated
@ToString(callSuper = true)
public class User extends GeneratedUser implements AuthorizationUser {

	@Override
	public User setId(long id) {
		super.setId(id);
		return this;
	}

	@Override
	public User setGlobalId(String globalId) {
		super.setGlobalId(globalId);
		return this;
	}

	@Override
	public User setName(String name) {
		super.setName(name);
		return this;
	}

	@Override
	public User setToken(String token) {
		super.setToken(token);
		return this;
	}

}