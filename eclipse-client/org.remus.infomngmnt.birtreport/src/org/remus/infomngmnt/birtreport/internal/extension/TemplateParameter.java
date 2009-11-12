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

package org.remus.infomngmnt.birtreport.internal.extension;

import java.util.Map;

import org.remus.infomngmnt.birtreport.extension.ITemplateParameter;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TemplateParameter implements ITemplateParameter {

	public TemplateParameter(final String label, final String name, final String parameterTypeId) {
		super();
		this.label = label;
		this.name = name;
		this.parameterTypeId = parameterTypeId;
	}

	private final String label;

	private final String name;

	private final String parameterTypeId;

	private Map<String, String> parameterTypeParameters;

	/**
	 * @return the label
	 */
	public final String getLabel() {
		return this.label;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * @return the parameterTypeId
	 */
	public final String getParameterTypeId() {
		return this.parameterTypeId;
	}

	/**
	 * @return the parameterTypeParameters
	 */
	public final Map<String, String> getParameterTypeParameters() {
		return this.parameterTypeParameters;
	}

	/**
	 * @param parameterTypeParameters
	 *            the parameterTypeParameters to set
	 */
	public final void setParameterTypeParameters(final Map<String, String> parameterTypeParameters) {
		this.parameterTypeParameters = parameterTypeParameters;
	}

}
