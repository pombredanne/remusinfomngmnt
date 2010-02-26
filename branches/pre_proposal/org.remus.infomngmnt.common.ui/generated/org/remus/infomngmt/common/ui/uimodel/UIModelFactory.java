/**
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * 
 * Contributors:
 *      Tom Seidel - initial API and implementation
 * 
 *
 * $Id$
 */
package org.remus.infomngmt.common.ui.uimodel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.remus.infomngmt.common.ui.uimodel.UIModelPackage
 * @generated
 */
public interface UIModelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	UIModelFactory eINSTANCE = org.remus.infomngmt.common.ui.uimodel.impl.UIModelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Tray Section</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tray Section</em>'.
	 * @generated
	 */
	TraySection createTraySection();

	/**
	 * Returns a new object of class '<em>Tray Section Collection</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tray Section Collection</em>'.
	 * @generated
	 */
	TraySectionCollection createTraySectionCollection();

	/**
	 * Returns a new object of class '<em>Desktop Tool Item Collection</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Desktop Tool Item Collection</em>'.
	 * @generated
	 */
	DesktopToolItemCollection createDesktopToolItemCollection();

	/**
	 * Returns a new object of class '<em>Desktop Tool Item</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Desktop Tool Item</em>'.
	 * @generated
	 */
	DesktopToolItem createDesktopToolItem();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	UIModelPackage getUIModelPackage();

} //UIModelFactory
