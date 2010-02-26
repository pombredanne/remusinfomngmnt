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

package org.remus.infomngmnt.ui.propertytester;

import org.eclipse.core.expressions.PropertyTester;

import org.remus.infomngmnt.AbstractInformationUnit;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InfoUnitTypeTester extends PropertyTester {

	public static final String PROPERTY_NAME = "typeEquals"; //$NON-NLS-1$

	/**
	 * 
	 */
	public InfoUnitTypeTester() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object,
	 * java.lang.String, java.lang.Object[], java.lang.Object)
	 */
	public boolean test(final Object receiver, final String property, final Object[] args,
			final Object expectedValue) {
		if (PROPERTY_NAME.equals(property)) {
			if (receiver instanceof AbstractInformationUnit) {
				return ((AbstractInformationUnit) receiver).getType().equals(expectedValue);
			}
		}
		return false;
	}
}
