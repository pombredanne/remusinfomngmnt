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

import java.util.Collection;
import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.remus.infomngmnt.BinaryReference;
import org.remus.infomngmnt.CalendarEntry;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.Link;
import org.remus.infomngmnt.Usage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Information Unit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.impl.InformationUnitImpl#getStringValue <em>String Value</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.InformationUnitImpl#getLongValue <em>Long Value</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.InformationUnitImpl#isBoolValue <em>Bool Value</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.InformationUnitImpl#getBinaryValue <em>Binary Value</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.InformationUnitImpl#getDateValue <em>Date Value</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.InformationUnitImpl#getDoubleValue <em>Double Value</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.InformationUnitImpl#getChildValues <em>Child Values</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.InformationUnitImpl#getReferences <em>References</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.InformationUnitImpl#getLinks <em>Links</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.InformationUnitImpl#getCreationDate <em>Creation Date</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.InformationUnitImpl#getUsageData <em>Usage Data</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.InformationUnitImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.InformationUnitImpl#getKeywords <em>Keywords</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.InformationUnitImpl#getCalendarEntry <em>Calendar Entry</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.InformationUnitImpl#getBinaryReferences <em>Binary References</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InformationUnitImpl extends AbstractInformationUnitImpl implements InformationUnit {
	/**
	 * The default value of the '{@link #getStringValue() <em>String Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStringValue()
	 * @generated
	 * @ordered
	 */
	protected static final String STRING_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStringValue() <em>String Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStringValue()
	 * @generated
	 * @ordered
	 */
	protected String stringValue = STRING_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getLongValue() <em>Long Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLongValue()
	 * @generated
	 * @ordered
	 */
	protected static final long LONG_VALUE_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getLongValue() <em>Long Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLongValue()
	 * @generated
	 * @ordered
	 */
	protected long longValue = LONG_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #isBoolValue() <em>Bool Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBoolValue()
	 * @generated
	 * @ordered
	 */
	protected static final boolean BOOL_VALUE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isBoolValue() <em>Bool Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBoolValue()
	 * @generated
	 * @ordered
	 */
	protected boolean boolValue = BOOL_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getBinaryValue() <em>Binary Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBinaryValue()
	 * @generated
	 * @ordered
	 */
	protected static final byte[] BINARY_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBinaryValue() <em>Binary Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBinaryValue()
	 * @generated
	 * @ordered
	 */
	protected byte[] binaryValue = BINARY_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDateValue() <em>Date Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDateValue()
	 * @generated
	 * @ordered
	 */
	protected static final Date DATE_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDateValue() <em>Date Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDateValue()
	 * @generated
	 * @ordered
	 */
	protected Date dateValue = DATE_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDoubleValue() <em>Double Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDoubleValue()
	 * @generated
	 * @ordered
	 */
	protected static final double DOUBLE_VALUE_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getDoubleValue() <em>Double Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDoubleValue()
	 * @generated
	 * @ordered
	 */
	protected double doubleValue = DOUBLE_VALUE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getChildValues() <em>Child Values</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildValues()
	 * @generated
	 * @ordered
	 */
	protected EList<InformationUnit> childValues;

	/**
	 * The cached value of the '{@link #getReferences() <em>References</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferences()
	 * @generated
	 * @ordered
	 */
	protected EList<InformationUnit> references;

	/**
	 * The cached value of the '{@link #getLinks() <em>Links</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLinks()
	 * @generated
	 * @ordered
	 */
	protected EList<Link> links;

	/**
	 * The default value of the '{@link #getCreationDate() <em>Creation Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreationDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date CREATION_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCreationDate() <em>Creation Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreationDate()
	 * @generated
	 * @ordered
	 */
	protected Date creationDate = CREATION_DATE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getUsageData() <em>Usage Data</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUsageData()
	 * @generated
	 * @ordered
	 */
	protected Usage usageData;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getKeywords() <em>Keywords</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeywords()
	 * @generated
	 * @ordered
	 */
	protected static final String KEYWORDS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getKeywords() <em>Keywords</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeywords()
	 * @generated
	 * @ordered
	 */
	protected String keywords = KEYWORDS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCalendarEntry() <em>Calendar Entry</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCalendarEntry()
	 * @generated
	 * @ordered
	 */
	protected EList<CalendarEntry> calendarEntry;

	/**
	 * The cached value of the '{@link #getBinaryReferences() <em>Binary References</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBinaryReferences()
	 * @generated
	 * @ordered
	 */
	protected EList<BinaryReference> binaryReferences;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InformationUnitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfomngmntPackage.Literals.INFORMATION_UNIT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getStringValue() {
		return stringValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStringValue(String newStringValue) {
		String oldStringValue = stringValue;
		stringValue = newStringValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.INFORMATION_UNIT__STRING_VALUE, oldStringValue, stringValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getLongValue() {
		return longValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLongValue(long newLongValue) {
		long oldLongValue = longValue;
		longValue = newLongValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.INFORMATION_UNIT__LONG_VALUE, oldLongValue, longValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isBoolValue() {
		return boolValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBoolValue(boolean newBoolValue) {
		boolean oldBoolValue = boolValue;
		boolValue = newBoolValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.INFORMATION_UNIT__BOOL_VALUE, oldBoolValue, boolValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public byte[] getBinaryValue() {
		return binaryValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBinaryValue(byte[] newBinaryValue) {
		byte[] oldBinaryValue = binaryValue;
		binaryValue = newBinaryValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.INFORMATION_UNIT__BINARY_VALUE, oldBinaryValue, binaryValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getDateValue() {
		return dateValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDateValue(Date newDateValue) {
		Date oldDateValue = dateValue;
		dateValue = newDateValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.INFORMATION_UNIT__DATE_VALUE, oldDateValue, dateValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getDoubleValue() {
		return doubleValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDoubleValue(double newDoubleValue) {
		double oldDoubleValue = doubleValue;
		doubleValue = newDoubleValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.INFORMATION_UNIT__DOUBLE_VALUE, oldDoubleValue, doubleValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<InformationUnit> getChildValues() {
		if (childValues == null) {
			childValues = new EObjectContainmentEList<InformationUnit>(InformationUnit.class, this, InfomngmntPackage.INFORMATION_UNIT__CHILD_VALUES);
		}
		return childValues;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<InformationUnit> getReferences() {
		if (references == null) {
			references = new EObjectResolvingEList<InformationUnit>(InformationUnit.class, this, InfomngmntPackage.INFORMATION_UNIT__REFERENCES);
		}
		return references;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Link> getLinks() {
		if (links == null) {
			links = new EObjectContainmentEList<Link>(Link.class, this, InfomngmntPackage.INFORMATION_UNIT__LINKS);
		}
		return links;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCreationDate(Date newCreationDate) {
		Date oldCreationDate = creationDate;
		creationDate = newCreationDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.INFORMATION_UNIT__CREATION_DATE, oldCreationDate, creationDate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Usage getUsageData() {
		if (usageData != null && usageData.eIsProxy()) {
			InternalEObject oldUsageData = (InternalEObject)usageData;
			usageData = (Usage)eResolveProxy(oldUsageData);
			if (usageData != oldUsageData) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, InfomngmntPackage.INFORMATION_UNIT__USAGE_DATA, oldUsageData, usageData));
			}
		}
		return usageData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Usage basicGetUsageData() {
		return usageData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUsageData(Usage newUsageData) {
		Usage oldUsageData = usageData;
		usageData = newUsageData;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.INFORMATION_UNIT__USAGE_DATA, oldUsageData, usageData));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.INFORMATION_UNIT__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKeywords(String newKeywords) {
		String oldKeywords = keywords;
		keywords = newKeywords;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.INFORMATION_UNIT__KEYWORDS, oldKeywords, keywords));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CalendarEntry> getCalendarEntry() {
		if (calendarEntry == null) {
			calendarEntry = new EObjectContainmentEList<CalendarEntry>(CalendarEntry.class, this, InfomngmntPackage.INFORMATION_UNIT__CALENDAR_ENTRY);
		}
		return calendarEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<BinaryReference> getBinaryReferences() {
		if (binaryReferences == null) {
			binaryReferences = new EObjectContainmentEList<BinaryReference>(BinaryReference.class, this, InfomngmntPackage.INFORMATION_UNIT__BINARY_REFERENCES);
		}
		return binaryReferences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case InfomngmntPackage.INFORMATION_UNIT__CHILD_VALUES:
				return ((InternalEList<?>)getChildValues()).basicRemove(otherEnd, msgs);
			case InfomngmntPackage.INFORMATION_UNIT__LINKS:
				return ((InternalEList<?>)getLinks()).basicRemove(otherEnd, msgs);
			case InfomngmntPackage.INFORMATION_UNIT__CALENDAR_ENTRY:
				return ((InternalEList<?>)getCalendarEntry()).basicRemove(otherEnd, msgs);
			case InfomngmntPackage.INFORMATION_UNIT__BINARY_REFERENCES:
				return ((InternalEList<?>)getBinaryReferences()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case InfomngmntPackage.INFORMATION_UNIT__STRING_VALUE:
				return getStringValue();
			case InfomngmntPackage.INFORMATION_UNIT__LONG_VALUE:
				return getLongValue();
			case InfomngmntPackage.INFORMATION_UNIT__BOOL_VALUE:
				return isBoolValue();
			case InfomngmntPackage.INFORMATION_UNIT__BINARY_VALUE:
				return getBinaryValue();
			case InfomngmntPackage.INFORMATION_UNIT__DATE_VALUE:
				return getDateValue();
			case InfomngmntPackage.INFORMATION_UNIT__DOUBLE_VALUE:
				return getDoubleValue();
			case InfomngmntPackage.INFORMATION_UNIT__CHILD_VALUES:
				return getChildValues();
			case InfomngmntPackage.INFORMATION_UNIT__REFERENCES:
				return getReferences();
			case InfomngmntPackage.INFORMATION_UNIT__LINKS:
				return getLinks();
			case InfomngmntPackage.INFORMATION_UNIT__CREATION_DATE:
				return getCreationDate();
			case InfomngmntPackage.INFORMATION_UNIT__USAGE_DATA:
				if (resolve) return getUsageData();
				return basicGetUsageData();
			case InfomngmntPackage.INFORMATION_UNIT__DESCRIPTION:
				return getDescription();
			case InfomngmntPackage.INFORMATION_UNIT__KEYWORDS:
				return getKeywords();
			case InfomngmntPackage.INFORMATION_UNIT__CALENDAR_ENTRY:
				return getCalendarEntry();
			case InfomngmntPackage.INFORMATION_UNIT__BINARY_REFERENCES:
				return getBinaryReferences();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case InfomngmntPackage.INFORMATION_UNIT__STRING_VALUE:
				setStringValue((String)newValue);
				return;
			case InfomngmntPackage.INFORMATION_UNIT__LONG_VALUE:
				setLongValue((Long)newValue);
				return;
			case InfomngmntPackage.INFORMATION_UNIT__BOOL_VALUE:
				setBoolValue((Boolean)newValue);
				return;
			case InfomngmntPackage.INFORMATION_UNIT__BINARY_VALUE:
				setBinaryValue((byte[])newValue);
				return;
			case InfomngmntPackage.INFORMATION_UNIT__DATE_VALUE:
				setDateValue((Date)newValue);
				return;
			case InfomngmntPackage.INFORMATION_UNIT__DOUBLE_VALUE:
				setDoubleValue((Double)newValue);
				return;
			case InfomngmntPackage.INFORMATION_UNIT__CHILD_VALUES:
				getChildValues().clear();
				getChildValues().addAll((Collection<? extends InformationUnit>)newValue);
				return;
			case InfomngmntPackage.INFORMATION_UNIT__REFERENCES:
				getReferences().clear();
				getReferences().addAll((Collection<? extends InformationUnit>)newValue);
				return;
			case InfomngmntPackage.INFORMATION_UNIT__LINKS:
				getLinks().clear();
				getLinks().addAll((Collection<? extends Link>)newValue);
				return;
			case InfomngmntPackage.INFORMATION_UNIT__CREATION_DATE:
				setCreationDate((Date)newValue);
				return;
			case InfomngmntPackage.INFORMATION_UNIT__USAGE_DATA:
				setUsageData((Usage)newValue);
				return;
			case InfomngmntPackage.INFORMATION_UNIT__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case InfomngmntPackage.INFORMATION_UNIT__KEYWORDS:
				setKeywords((String)newValue);
				return;
			case InfomngmntPackage.INFORMATION_UNIT__CALENDAR_ENTRY:
				getCalendarEntry().clear();
				getCalendarEntry().addAll((Collection<? extends CalendarEntry>)newValue);
				return;
			case InfomngmntPackage.INFORMATION_UNIT__BINARY_REFERENCES:
				getBinaryReferences().clear();
				getBinaryReferences().addAll((Collection<? extends BinaryReference>)newValue);
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
			case InfomngmntPackage.INFORMATION_UNIT__STRING_VALUE:
				setStringValue(STRING_VALUE_EDEFAULT);
				return;
			case InfomngmntPackage.INFORMATION_UNIT__LONG_VALUE:
				setLongValue(LONG_VALUE_EDEFAULT);
				return;
			case InfomngmntPackage.INFORMATION_UNIT__BOOL_VALUE:
				setBoolValue(BOOL_VALUE_EDEFAULT);
				return;
			case InfomngmntPackage.INFORMATION_UNIT__BINARY_VALUE:
				setBinaryValue(BINARY_VALUE_EDEFAULT);
				return;
			case InfomngmntPackage.INFORMATION_UNIT__DATE_VALUE:
				setDateValue(DATE_VALUE_EDEFAULT);
				return;
			case InfomngmntPackage.INFORMATION_UNIT__DOUBLE_VALUE:
				setDoubleValue(DOUBLE_VALUE_EDEFAULT);
				return;
			case InfomngmntPackage.INFORMATION_UNIT__CHILD_VALUES:
				getChildValues().clear();
				return;
			case InfomngmntPackage.INFORMATION_UNIT__REFERENCES:
				getReferences().clear();
				return;
			case InfomngmntPackage.INFORMATION_UNIT__LINKS:
				getLinks().clear();
				return;
			case InfomngmntPackage.INFORMATION_UNIT__CREATION_DATE:
				setCreationDate(CREATION_DATE_EDEFAULT);
				return;
			case InfomngmntPackage.INFORMATION_UNIT__USAGE_DATA:
				setUsageData((Usage)null);
				return;
			case InfomngmntPackage.INFORMATION_UNIT__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case InfomngmntPackage.INFORMATION_UNIT__KEYWORDS:
				setKeywords(KEYWORDS_EDEFAULT);
				return;
			case InfomngmntPackage.INFORMATION_UNIT__CALENDAR_ENTRY:
				getCalendarEntry().clear();
				return;
			case InfomngmntPackage.INFORMATION_UNIT__BINARY_REFERENCES:
				getBinaryReferences().clear();
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
			case InfomngmntPackage.INFORMATION_UNIT__STRING_VALUE:
				return STRING_VALUE_EDEFAULT == null ? stringValue != null : !STRING_VALUE_EDEFAULT.equals(stringValue);
			case InfomngmntPackage.INFORMATION_UNIT__LONG_VALUE:
				return longValue != LONG_VALUE_EDEFAULT;
			case InfomngmntPackage.INFORMATION_UNIT__BOOL_VALUE:
				return boolValue != BOOL_VALUE_EDEFAULT;
			case InfomngmntPackage.INFORMATION_UNIT__BINARY_VALUE:
				return BINARY_VALUE_EDEFAULT == null ? binaryValue != null : !BINARY_VALUE_EDEFAULT.equals(binaryValue);
			case InfomngmntPackage.INFORMATION_UNIT__DATE_VALUE:
				return DATE_VALUE_EDEFAULT == null ? dateValue != null : !DATE_VALUE_EDEFAULT.equals(dateValue);
			case InfomngmntPackage.INFORMATION_UNIT__DOUBLE_VALUE:
				return doubleValue != DOUBLE_VALUE_EDEFAULT;
			case InfomngmntPackage.INFORMATION_UNIT__CHILD_VALUES:
				return childValues != null && !childValues.isEmpty();
			case InfomngmntPackage.INFORMATION_UNIT__REFERENCES:
				return references != null && !references.isEmpty();
			case InfomngmntPackage.INFORMATION_UNIT__LINKS:
				return links != null && !links.isEmpty();
			case InfomngmntPackage.INFORMATION_UNIT__CREATION_DATE:
				return CREATION_DATE_EDEFAULT == null ? creationDate != null : !CREATION_DATE_EDEFAULT.equals(creationDate);
			case InfomngmntPackage.INFORMATION_UNIT__USAGE_DATA:
				return usageData != null;
			case InfomngmntPackage.INFORMATION_UNIT__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case InfomngmntPackage.INFORMATION_UNIT__KEYWORDS:
				return KEYWORDS_EDEFAULT == null ? keywords != null : !KEYWORDS_EDEFAULT.equals(keywords);
			case InfomngmntPackage.INFORMATION_UNIT__CALENDAR_ENTRY:
				return calendarEntry != null && !calendarEntry.isEmpty();
			case InfomngmntPackage.INFORMATION_UNIT__BINARY_REFERENCES:
				return binaryReferences != null && !binaryReferences.isEmpty();
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
		result.append(" (stringValue: ");
		result.append(stringValue);
		result.append(", longValue: ");
		result.append(longValue);
		result.append(", boolValue: ");
		result.append(boolValue);
		result.append(", binaryValue: ");
		result.append(binaryValue);
		result.append(", dateValue: ");
		result.append(dateValue);
		result.append(", doubleValue: ");
		result.append(doubleValue);
		result.append(", creationDate: ");
		result.append(creationDate);
		result.append(", description: ");
		result.append(description);
		result.append(", keywords: ");
		result.append(keywords);
		result.append(')');
		return result.toString();
	}

} //InformationUnitImpl
