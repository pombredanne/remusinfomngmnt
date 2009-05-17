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

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.RuleValue;
import org.remus.infomngmnt.core.extension.TransferWrapper;
import org.remus.infomngmnt.ui.rules.ICreationTrigger;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class AbstractCreationTrigger implements ICreationTrigger {

	private Object value;

	private RuleValue ruleValue;

	private InformationUnit newInformationUnit;

	private String categoryString;

	private TransferWrapper transferType;

	public abstract void handleCreationRequest();

	public Object getValue() {
		return this.value;
	}

	public void setValue(final Object value) {
		this.value = value;
	}

	public RuleValue getRuleValue() {
		return this.ruleValue;
	}

	public void setRuleValue(final RuleValue ruleValue) {
		this.ruleValue = ruleValue;
	}

	/**
	 * @return the newInformationUnit
	 */
	public InformationUnit getNewInformationUnit() {
		return this.newInformationUnit;
	}

	/**
	 * @param newInformationUnit
	 *            the newInformationUnit to set
	 */
	public void setNewInformationUnit(final InformationUnit newInformationUnit) {
		this.newInformationUnit = newInformationUnit;
	}

	/**
	 * @return the categoryString
	 */
	public String getCategoryString() {
		return this.categoryString;
	}

	/**
	 * @param categoryString
	 *            the categoryString to set
	 */
	public void setCategoryString(final String categoryString) {
		this.categoryString = categoryString;
	}

	/**
	 * @return the transferType
	 */
	public TransferWrapper getTransferType() {
		return this.transferType;
	}

	/**
	 * @param transferType
	 *            the transferType to set
	 */
	public void setTransferType(final TransferWrapper transferType) {
		this.transferType = transferType;
	}

}
