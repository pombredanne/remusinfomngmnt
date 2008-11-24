/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.core.extension;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import org.remus.infomngmnt.Rule;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class AbstractRuleDefinition implements Listener {

	protected Rule concreteRule;

	public void initialize(Rule rule) {
		this.concreteRule = rule;
	}

	public abstract String getStringRepresentation();

	public void handleEvent(Event event) {
		doHandleSelection(event);
	}

	protected void doHandleSelection(Event event) {
		// nothing by default.

	}

	//public abstract createEditContents(Composite parent)



}
