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

import org.eclipse.emf.ecore.EObject;

import org.eclipse.swt.graphics.Image;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Desktop Tool Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmt.common.ui.uimodel.DesktopToolItem#getId <em>Id</em>}</li>
 *   <li>{@link org.remus.infomngmt.common.ui.uimodel.DesktopToolItem#getName <em>Name</em>}</li>
 *   <li>{@link org.remus.infomngmt.common.ui.uimodel.DesktopToolItem#getImage <em>Image</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmt.common.ui.uimodel.UIModelPackage#getDesktopToolItem()
 * @model
 * @generated
 */
public interface DesktopToolItem extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.remus.infomngmt.common.ui.uimodel.UIModelPackage#getDesktopToolItem_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.remus.infomngmt.common.ui.uimodel.DesktopToolItem#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.remus.infomngmt.common.ui.uimodel.UIModelPackage#getDesktopToolItem_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.remus.infomngmt.common.ui.uimodel.DesktopToolItem#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Image</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Image</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Image</em>' attribute.
	 * @see #setImage(Image)
	 * @see org.remus.infomngmt.common.ui.uimodel.UIModelPackage#getDesktopToolItem_Image()
	 * @model dataType="org.remus.infomngmt.common.ui.uimodel.Image"
	 * @generated
	 */
	Image getImage();

	/**
	 * Sets the value of the '{@link org.remus.infomngmt.common.ui.uimodel.DesktopToolItem#getImage <em>Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Image</em>' attribute.
	 * @see #getImage()
	 * @generated
	 */
	void setImage(Image value);

} // DesktopToolItem
