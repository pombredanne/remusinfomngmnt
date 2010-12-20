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

import groovy.lang.GroovyShell;

import java.util.Map;
import java.util.Set;

import org.eclipse.remus.ui.rules.execution.IGroovyEvaluation;
import org.eclipse.remus.ui.rules.execution.IGroovyEvaluationBinding;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class GroovyEvaluation implements IGroovyEvaluation {

	private GroovyShell groovyShell;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.remus.ui.rules.execution.IGroovyEvaluation#setBinding(org
	 * .eclipse.remus.ui.rules.execution.IGroovyEvaluationBinding)
	 */
	public void setBinding(IGroovyEvaluationBinding binding) {
		Map<String, ? extends Object> variables = binding.getVariables();
		Set<String> keySet = variables.keySet();
		for (String string : keySet) {
			getShell().setVariable(string, variables.get(string));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.remus.ui.rules.execution.IGroovyEvaluation#createBinding()
	 */
	public IGroovyEvaluationBinding createBinding() {
		return new GroovyEvaluationBinding();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.remus.ui.rules.execution.IGroovyEvaluation#evaluate(java.
	 * lang.String)
	 */
	public Object evaluate(String script) {
		return getShell().evaluate(script);
	}

	private GroovyShell getShell() {
		if (groovyShell == null) {
			groovyShell = new GroovyShell();
		}
		return groovyShell;
	}

}
