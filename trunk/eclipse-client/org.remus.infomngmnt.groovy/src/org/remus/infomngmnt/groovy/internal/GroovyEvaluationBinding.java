/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.groovy.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.remus.ui.rules.execution.IGroovyEvaluationBinding;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class GroovyEvaluationBinding implements IGroovyEvaluationBinding {

	private final Map<String, Object> variables = new HashMap<String, Object>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.remus.ui.rules.execution.IGroovyEvaluationBinding#setVariable
	 * (java.lang.String, java.lang.Object)
	 */
	public void setVariable(String name, Object value) {
		variables.put(name, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.remus.ui.rules.execution.IGroovyEvaluationBinding#getVariable
	 * (java.lang.String)
	 */
	public Object getVariable(String name) {
		return variables.get(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.remus.ui.rules.execution.IGroovyEvaluationBinding#getVariables
	 * ()
	 */
	public Map<String, ? extends Object> getVariables() {
		return variables;
	}

}
