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
package org.remus.infomngmnt.impl;

import java.util.Date;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.remus.infomngmnt.CalendarEntry;
import org.remus.infomngmnt.CalendarEntryType;
import org.remus.infomngmnt.InfomngmntPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Calendar Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.impl.CalendarEntryImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.CalendarEntryImpl#getStart <em>Start</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.CalendarEntryImpl#getEnd <em>End</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.CalendarEntryImpl#getEntryType <em>Entry Type</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.CalendarEntryImpl#getReminder <em>Reminder</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.CalendarEntryImpl#getTitle <em>Title</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CalendarEntryImpl extends EObjectImpl implements CalendarEntry {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getStart() <em>Start</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStart()
	 * @generated
	 * @ordered
	 */
	protected static final Date START_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStart() <em>Start</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStart()
	 * @generated
	 * @ordered
	 */
	protected Date start = START_EDEFAULT;

	/**
	 * The default value of the '{@link #getEnd() <em>End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnd()
	 * @generated
	 * @ordered
	 */
	protected static final Date END_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEnd() <em>End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnd()
	 * @generated
	 * @ordered
	 */
	protected Date end = END_EDEFAULT;

	/**
	 * The default value of the '{@link #getEntryType() <em>Entry Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntryType()
	 * @generated
	 * @ordered
	 */
	protected static final CalendarEntryType ENTRY_TYPE_EDEFAULT = CalendarEntryType.ONE_TIME;

	/**
	 * The cached value of the '{@link #getEntryType() <em>Entry Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntryType()
	 * @generated
	 * @ordered
	 */
	protected CalendarEntryType entryType = ENTRY_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getReminder() <em>Reminder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReminder()
	 * @generated
	 * @ordered
	 */
	protected static final int REMINDER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getReminder() <em>Reminder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReminder()
	 * @generated
	 * @ordered
	 */
	protected int reminder = REMINDER_EDEFAULT;

	/**
	 * The default value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected static final String TITLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected String title = TITLE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CalendarEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfomngmntPackage.Literals.CALENDAR_ENTRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.CALENDAR_ENTRY__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStart(Date newStart) {
		Date oldStart = start;
		start = newStart;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.CALENDAR_ENTRY__START, oldStart, start));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getEnd() {
		return end;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnd(Date newEnd) {
		Date oldEnd = end;
		end = newEnd;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.CALENDAR_ENTRY__END, oldEnd, end));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CalendarEntryType getEntryType() {
		return entryType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntryType(CalendarEntryType newEntryType) {
		CalendarEntryType oldEntryType = entryType;
		entryType = newEntryType == null ? ENTRY_TYPE_EDEFAULT : newEntryType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.CALENDAR_ENTRY__ENTRY_TYPE, oldEntryType, entryType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getReminder() {
		return reminder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReminder(int newReminder) {
		int oldReminder = reminder;
		reminder = newReminder;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.CALENDAR_ENTRY__REMINDER, oldReminder, reminder));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTitle(String newTitle) {
		String oldTitle = title;
		title = newTitle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.CALENDAR_ENTRY__TITLE, oldTitle, title));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case InfomngmntPackage.CALENDAR_ENTRY__ID:
				return getId();
			case InfomngmntPackage.CALENDAR_ENTRY__START:
				return getStart();
			case InfomngmntPackage.CALENDAR_ENTRY__END:
				return getEnd();
			case InfomngmntPackage.CALENDAR_ENTRY__ENTRY_TYPE:
				return getEntryType();
			case InfomngmntPackage.CALENDAR_ENTRY__REMINDER:
				return getReminder();
			case InfomngmntPackage.CALENDAR_ENTRY__TITLE:
				return getTitle();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case InfomngmntPackage.CALENDAR_ENTRY__ID:
				setId((String)newValue);
				return;
			case InfomngmntPackage.CALENDAR_ENTRY__START:
				setStart((Date)newValue);
				return;
			case InfomngmntPackage.CALENDAR_ENTRY__END:
				setEnd((Date)newValue);
				return;
			case InfomngmntPackage.CALENDAR_ENTRY__ENTRY_TYPE:
				setEntryType((CalendarEntryType)newValue);
				return;
			case InfomngmntPackage.CALENDAR_ENTRY__REMINDER:
				setReminder((Integer)newValue);
				return;
			case InfomngmntPackage.CALENDAR_ENTRY__TITLE:
				setTitle((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case InfomngmntPackage.CALENDAR_ENTRY__ID:
				setId(ID_EDEFAULT);
				return;
			case InfomngmntPackage.CALENDAR_ENTRY__START:
				setStart(START_EDEFAULT);
				return;
			case InfomngmntPackage.CALENDAR_ENTRY__END:
				setEnd(END_EDEFAULT);
				return;
			case InfomngmntPackage.CALENDAR_ENTRY__ENTRY_TYPE:
				setEntryType(ENTRY_TYPE_EDEFAULT);
				return;
			case InfomngmntPackage.CALENDAR_ENTRY__REMINDER:
				setReminder(REMINDER_EDEFAULT);
				return;
			case InfomngmntPackage.CALENDAR_ENTRY__TITLE:
				setTitle(TITLE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case InfomngmntPackage.CALENDAR_ENTRY__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case InfomngmntPackage.CALENDAR_ENTRY__START:
				return START_EDEFAULT == null ? start != null : !START_EDEFAULT.equals(start);
			case InfomngmntPackage.CALENDAR_ENTRY__END:
				return END_EDEFAULT == null ? end != null : !END_EDEFAULT.equals(end);
			case InfomngmntPackage.CALENDAR_ENTRY__ENTRY_TYPE:
				return entryType != ENTRY_TYPE_EDEFAULT;
			case InfomngmntPackage.CALENDAR_ENTRY__REMINDER:
				return reminder != REMINDER_EDEFAULT;
			case InfomngmntPackage.CALENDAR_ENTRY__TITLE:
				return TITLE_EDEFAULT == null ? title != null : !TITLE_EDEFAULT.equals(title);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (id: ");
		result.append(id);
		result.append(", start: ");
		result.append(start);
		result.append(", end: ");
		result.append(end);
		result.append(", entryType: ");
		result.append(entryType);
		result.append(", reminder: ");
		result.append(reminder);
		result.append(", title: ");
		result.append(title);
		result.append(')');
		return result.toString();
	}

} //CalendarEntryImpl
