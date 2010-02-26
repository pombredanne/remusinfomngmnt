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

package org.remus.infomngmnt.birtreport.parameter;

import java.util.Map;

import org.eclipse.swt.widgets.Composite;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class AbstractParameterControl {

	protected Map<String, String> options;

	/**
	 * @param options
	 *            the options to set
	 */
	public final void setOptions(final Map<String, String> options) {
		this.options = options;
	}

	public abstract void createPartControl(Composite parent);

	public abstract String getParameterValue();

}
