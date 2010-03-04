/*******************************************************************************
 * Copyright (c) 2009 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.ui.databinding;

import java.sql.Date;
import java.text.DateFormat;

import org.eclipse.core.databinding.conversion.Converter;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class Date2StringConverter extends Converter {

	private final int dateformat;
	private final int timeformat;

	public Date2StringConverter(final int dateformat, final int timeformat) {
		super(Date.class, String.class);
		this.dateformat = dateformat;
		this.timeformat = timeformat;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.databinding.conversion.IConverter#convert(java.lang.
	 * Object)
	 */
	public Object convert(final Object fromObject) {
		DateFormat dateTimeInstance;
		if (this.timeformat > 0 && this.dateformat > 0) {
			dateTimeInstance = DateFormat.getDateTimeInstance(this.dateformat, this.timeformat);
		} else if (this.timeformat <= 0 && this.dateformat > 0) {
			dateTimeInstance = DateFormat.getDateInstance(this.dateformat);
		} else if (this.timeformat > 0 && this.dateformat <= 0) {
			dateTimeInstance = DateFormat.getTimeInstance(this.timeformat);
		} else {
			dateTimeInstance = DateFormat.getInstance();
		}
		return dateTimeInstance.format((Date) fromObject);
	}

}
