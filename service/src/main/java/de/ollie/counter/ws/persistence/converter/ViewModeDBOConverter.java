package de.ollie.counter.ws.persistence.converter;

import javax.inject.Named;

import lombok.Generated;

import de.ollie.counter.ws.persistence.entity.ViewModeDBO;
import de.ollie.counter.ws.core.model.ViewMode;

/**
 * A DBO enum converter for viewmodes.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
public class ViewModeDBOConverter {

	public ViewModeDBO toDBO(ViewMode model) {
		return model == null ? null : ViewModeDBO.valueOf(model.name());
	}

	public ViewMode toModel(ViewModeDBO dbo) {
		return dbo == null ? null : ViewMode.valueOf(dbo.name());
	}

}