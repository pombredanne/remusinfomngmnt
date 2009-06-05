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
 * A representation of the model object '<em><b>Notification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.Notification#getTimeStamp <em>Time Stamp</em>}</li>
 *   <li>{@link org.remus.infomngmnt.Notification#getImportance <em>Importance</em>}</li>
 *   <li>{@link org.remus.infomngmnt.Notification#getSeverity <em>Severity</em>}</li>
 *   <li>{@link org.remus.infomngmnt.Notification#isNoticed <em>Noticed</em>}</li>
 *   <li>{@link org.remus.infomngmnt.Notification#getMessage <em>Message</em>}</li>
 *   <li>{@link org.remus.infomngmnt.Notification#getDetails <em>Details</em>}</li>
 *   <li>{@link org.remus.infomngmnt.Notification#getChildren <em>Children</em>}</li>
 *   <li>{@link org.remus.infomngmnt.Notification#getAffectedInfoUnitIds <em>Affected Info Unit Ids</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.InfomngmntPackage#getNotification()
 * @model
 * @generated
 */
public interface Notification extends Adapter {
	/**
	 * Returns the value of the '<em><b>Time Stamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Time Stamp</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Time Stamp</em>' attribute.
	 * @see #setTimeStamp(Date)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getNotification_TimeStamp()
	 * @model
	 * @generated
	 */
	Date getTimeStamp();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.Notification#getTimeStamp <em>Time Stamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time Stamp</em>' attribute.
	 * @see #getTimeStamp()
	 * @generated
	 */
	void setTimeStamp(Date value);

	/**
	 * Returns the value of the '<em><b>Importance</b></em>' attribute.
	 * The literals are from the enumeration {@link org.remus.infomngmnt.NotificationImportance}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Importance</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Importance</em>' attribute.
	 * @see org.remus.infomngmnt.NotificationImportance
	 * @see #setImportance(NotificationImportance)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getNotification_Importance()
	 * @model
	 * @generated
	 */
	NotificationImportance getImportance();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.Notification#getImportance <em>Importance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Importance</em>' attribute.
	 * @see org.remus.infomngmnt.NotificationImportance
	 * @see #getImportance()
	 * @generated
	 */
	void setImportance(NotificationImportance value);

	/**
	 * Returns the value of the '<em><b>Severity</b></em>' attribute.
	 * The literals are from the enumeration {@link org.remus.infomngmnt.Severity}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Severity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Severity</em>' attribute.
	 * @see org.remus.infomngmnt.Severity
	 * @see #setSeverity(Severity)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getNotification_Severity()
	 * @model
	 * @generated
	 */
	Severity getSeverity();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.Notification#getSeverity <em>Severity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Severity</em>' attribute.
	 * @see org.remus.infomngmnt.Severity
	 * @see #getSeverity()
	 * @generated
	 */
	void setSeverity(Severity value);

	/**
	 * Returns the value of the '<em><b>Noticed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Noticed</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Noticed</em>' attribute.
	 * @see #setNoticed(boolean)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getNotification_Noticed()
	 * @model
	 * @generated
	 */
	boolean isNoticed();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.Notification#isNoticed <em>Noticed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Noticed</em>' attribute.
	 * @see #isNoticed()
	 * @generated
	 */
	void setNoticed(boolean value);

	/**
	 * Returns the value of the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Message</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Message</em>' attribute.
	 * @see #setMessage(String)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getNotification_Message()
	 * @model
	 * @generated
	 */
	String getMessage();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.Notification#getMessage <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Message</em>' attribute.
	 * @see #getMessage()
	 * @generated
	 */
	void setMessage(String value);

	/**
	 * Returns the value of the '<em><b>Details</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Details</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Details</em>' attribute.
	 * @see #setDetails(String)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getNotification_Details()
	 * @model
	 * @generated
	 */
	String getDetails();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.Notification#getDetails <em>Details</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Details</em>' attribute.
	 * @see #getDetails()
	 * @generated
	 */
	void setDetails(String value);

	/**
	 * Returns the value of the '<em><b>Children</b></em>' containment reference list.
	 * The list contents are of type {@link org.remus.infomngmnt.Notification}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children</em>' containment reference list.
	 * @see org.remus.infomngmnt.InfomngmntPackage#getNotification_Children()
	 * @model containment="true"
	 * @generated
	 */
	EList<Notification> getChildren();

	/**
	 * Returns the value of the '<em><b>Affected Info Unit Ids</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Affected Info Unit Ids</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Affected Info Unit Ids</em>' attribute list.
	 * @see org.remus.infomngmnt.InfomngmntPackage#getNotification_AffectedInfoUnitIds()
	 * @model
	 * @generated
	 */
	EList<String> getAffectedInfoUnitIds();

} // Notification
