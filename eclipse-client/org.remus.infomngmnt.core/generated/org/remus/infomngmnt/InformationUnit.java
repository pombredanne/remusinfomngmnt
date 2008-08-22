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
package org.remus.infomngmnt;

import java.util.Date;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Information Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.InformationUnit#getStringValue <em>String Value</em>}</li>
 *   <li>{@link org.remus.infomngmnt.InformationUnit#getLongValue <em>Long Value</em>}</li>
 *   <li>{@link org.remus.infomngmnt.InformationUnit#isBoolValue <em>Bool Value</em>}</li>
 *   <li>{@link org.remus.infomngmnt.InformationUnit#getBinaryValue <em>Binary Value</em>}</li>
 *   <li>{@link org.remus.infomngmnt.InformationUnit#getDateValue <em>Date Value</em>}</li>
 *   <li>{@link org.remus.infomngmnt.InformationUnit#getChildValues <em>Child Values</em>}</li>
 *   <li>{@link org.remus.infomngmnt.InformationUnit#getLinks <em>Links</em>}</li>
 *   <li>{@link org.remus.infomngmnt.InformationUnit#getCreationDate <em>Creation Date</em>}</li>
 *   <li>{@link org.remus.infomngmnt.InformationUnit#getType <em>Type</em>}</li>
 *   <li>{@link org.remus.infomngmnt.InformationUnit#getUsageData <em>Usage Data</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.InfomngmntPackage#getInformationUnit()
 * @model
 * @generated
 */
public interface InformationUnit extends AbstractInformationUnit {
	/**
	 * Returns the value of the '<em><b>String Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>String Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>String Value</em>' attribute.
	 * @see #setStringValue(String)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getInformationUnit_StringValue()
	 * @model
	 * @generated
	 */
	String getStringValue();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.InformationUnit#getStringValue <em>String Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>String Value</em>' attribute.
	 * @see #getStringValue()
	 * @generated
	 */
	void setStringValue(String value);

	/**
	 * Returns the value of the '<em><b>Long Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Long Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Long Value</em>' attribute.
	 * @see #setLongValue(long)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getInformationUnit_LongValue()
	 * @model
	 * @generated
	 */
	long getLongValue();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.InformationUnit#getLongValue <em>Long Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Long Value</em>' attribute.
	 * @see #getLongValue()
	 * @generated
	 */
	void setLongValue(long value);

	/**
	 * Returns the value of the '<em><b>Bool Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bool Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bool Value</em>' attribute.
	 * @see #setBoolValue(boolean)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getInformationUnit_BoolValue()
	 * @model
	 * @generated
	 */
	boolean isBoolValue();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.InformationUnit#isBoolValue <em>Bool Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bool Value</em>' attribute.
	 * @see #isBoolValue()
	 * @generated
	 */
	void setBoolValue(boolean value);

	/**
	 * Returns the value of the '<em><b>Binary Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Binary Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Binary Value</em>' attribute.
	 * @see #setBinaryValue(byte)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getInformationUnit_BinaryValue()
	 * @model
	 * @generated
	 */
	byte getBinaryValue();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.InformationUnit#getBinaryValue <em>Binary Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Binary Value</em>' attribute.
	 * @see #getBinaryValue()
	 * @generated
	 */
	void setBinaryValue(byte value);

	/**
	 * Returns the value of the '<em><b>Date Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Date Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Date Value</em>' attribute.
	 * @see #setDateValue(Date)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getInformationUnit_DateValue()
	 * @model
	 * @generated
	 */
	Date getDateValue();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.InformationUnit#getDateValue <em>Date Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Date Value</em>' attribute.
	 * @see #getDateValue()
	 * @generated
	 */
	void setDateValue(Date value);

	/**
	 * Returns the value of the '<em><b>Child Values</b></em>' containment reference list.
	 * The list contents are of type {@link org.remus.infomngmnt.InformationUnit}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Child Values</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Child Values</em>' containment reference list.
	 * @see org.remus.infomngmnt.InfomngmntPackage#getInformationUnit_ChildValues()
	 * @model containment="true"
	 * @generated
	 */
	EList<InformationUnit> getChildValues();

	/**
	 * Returns the value of the '<em><b>Links</b></em>' reference list.
	 * The list contents are of type {@link org.remus.infomngmnt.InformationUnit}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Links</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Links</em>' reference list.
	 * @see org.remus.infomngmnt.InfomngmntPackage#getInformationUnit_Links()
	 * @model
	 * @generated
	 */
	EList<InformationUnit> getLinks();

	/**
	 * Returns the value of the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Creation Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Creation Date</em>' attribute.
	 * @see #setCreationDate(Date)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getInformationUnit_CreationDate()
	 * @model
	 * @generated
	 */
	Date getCreationDate();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.InformationUnit#getCreationDate <em>Creation Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Creation Date</em>' attribute.
	 * @see #getCreationDate()
	 * @generated
	 */
	void setCreationDate(Date value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getInformationUnit_Type()
	 * @model
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.InformationUnit#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Usage Data</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Usage Data</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Usage Data</em>' reference.
	 * @see #setUsageData(Usage)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getInformationUnit_UsageData()
	 * @model
	 * @generated
	 */
	Usage getUsageData();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.InformationUnit#getUsageData <em>Usage Data</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Usage Data</em>' reference.
	 * @see #getUsageData()
	 * @generated
	 */
	void setUsageData(Usage value);

} // InformationUnit
