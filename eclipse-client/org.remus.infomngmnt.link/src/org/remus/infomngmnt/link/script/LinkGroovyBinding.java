/*******************************************************************************
 * Copyright (c) 2011 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.link.script;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.ui.rules.execution.IGroovyEvaluationBinding;
import org.eclipse.remus.ui.rules.extension.IGroovyBinding;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class LinkGroovyBinding implements IGroovyBinding {

	/**
	 * 
	 */
	public LinkGroovyBinding() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.remus.ui.rules.extension.IGroovyBinding#beforeEvaluation(
	 * org.eclipse.remus.ui.rules.execution.IGroovyEvaluationBinding)
	 */
	public void beforeEvaluation(IGroovyEvaluationBinding binding) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.remus.ui.rules.extension.IGroovyBinding#afterEvaluation(org
	 * .eclipse.remus.ui.rules.execution.IGroovyEvaluationBinding,
	 * org.eclipse.remus.InformationUnit)
	 */
	public Map afterEvaluation(IGroovyEvaluationBinding binding,
			InformationUnit createdObject) {
		return new HashMap();
	}

}
