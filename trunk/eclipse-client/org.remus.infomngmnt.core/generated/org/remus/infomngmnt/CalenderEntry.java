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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Calender Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.CalenderEntry#getStart <em>Start</em>}</li>
 *   <li>{@link org.remus.infomngmnt.CalenderEntry#getEnd <em>End</em>}</li>
 *   <li>{@link org.remus.infomngmnt.CalenderEntry#getEntryType <em>Entry Type</em>}</li>
 *   <li>{@link org.remus.infomngmnt.CalenderEntry#getReminder <em>Reminder</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.InfomngmntPackage#getCalenderEntry()
 * @model
 * @generated
 */
public interface CalenderEntry extends EObject {
	/**
	 * Returns the value of the '<em><b>Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start</em>' attribute.
	 * @see #setStart(Date)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getCalenderEntry_Start()
	 * @model required="true"
	 * @generated
	 */
	Date getStart();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.CalenderEntry#getStart <em>Start</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start</em>' attribute.
	 * @see #getStart()
	 * @generated
	 */
	void setStart(Date value);

	/**
	 * Returns the value of the '<em><b>End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End</em>' attribute.
	 * @see #setEnd(Date)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getCalenderEntry_End()
	 * @model required="true"
	 * @generated
	 */
	Date getEnd();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.CalenderEntry#getEnd <em>End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End</em>' attribute.
	 * @see #getEnd()
	 * @generated
	 */
	void setEnd(Date value);

	/**
	 * Returns the value of the '<em><b>Entry Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.remus.infomngmnt.CalendarEntryType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entry Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entry Type</em>' attribute.
	 * @see org.remus.infomngmnt.CalendarEntryType
	 * @see #setEntryType(CalendarEntryType)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getCalenderEntry_EntryType()
	 * @model
	 * @generated
	 */
	CalendarEntryType getEntryType();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.CalenderEntry#getEntryType <em>Entry Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entry Type</em>' attribute.
	 * @see org.remus.infomngmnt.CalendarEntryType
	 * @see #getEntryType()
	 * @generated
	 */
	void setEntryType(CalendarEntryType value);

	/**
	 * Returns the value of the '<em><b>Reminder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reminder</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reminder</em>' attribute.
	 * @see #setReminder(int)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getCalenderEntry_Reminder()
	 * @model
	 * @generated
	 */
	int getReminder();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.CalenderEntry#getReminder <em>Reminder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reminder</em>' attribute.
	 * @see #getReminder()
	 * @generated
	 */
	void setReminder(int value);

} // CalenderEntry
