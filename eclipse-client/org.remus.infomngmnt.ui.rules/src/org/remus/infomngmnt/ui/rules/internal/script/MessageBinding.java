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

package org.remus.infomngmnt.ui.rules.internal.script;

import groovy.lang.Binding;

import java.util.Map;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.ui.rules.extension.IGroovyBinding;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MessageBinding implements IGroovyBinding {

	/**
	 * 
	 */
	public MessageBinding() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.ui.rules.extension.IGroovyBinding#afterEvaluation(groovy.lang.Binding, org.remus.infomngmnt.InformationUnit)
	 */
	public Map afterEvaluation(Binding binding, InformationUnit createdObject) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.ui.rules.extension.IGroovyBinding#beforeEvaluation(groovy.lang.Binding)
	 */
	public void beforeEvaluation(Binding binding) {
		// TODO Auto-generated method stub

	}

}
