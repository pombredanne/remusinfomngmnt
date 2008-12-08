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

package org.remus.infomngmnt.ui.extension;

import org.remus.infomngmnt.RuleValue;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class AbstractCreationTrigger {

	private Object value;

	private RuleValue ruleValue;

	public abstract void handleCreationRequest();

	protected Object getValue() {
		return this.value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	protected RuleValue getRuleValue() {
		return this.ruleValue;
	}

	public void setRuleValue(RuleValue ruleValue) {
		this.ruleValue = ruleValue;
	}





}
