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

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.Notification;
import org.remus.infomngmnt.NotificationImportance;
import org.remus.infomngmnt.Severity;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Notification</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.impl.NotificationImpl#getTimeStamp <em>Time Stamp</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.NotificationImpl#getImportance <em>Importance</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.NotificationImpl#getSeverity <em>Severity</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.NotificationImpl#isNoticed <em>Noticed</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.NotificationImpl#getMessage <em>Message</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.NotificationImpl#getDetails <em>Details</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.NotificationImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.NotificationImpl#getAffectedInfoUnitIds <em>Affected Info Unit Ids</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NotificationImpl extends AdapterImpl implements Notification {
	/**
	 * The default value of the '{@link #getTimeStamp() <em>Time Stamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeStamp()
	 * @generated
	 * @ordered
	 */
	protected static final Date TIME_STAMP_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTimeStamp() <em>Time Stamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeStamp()
	 * @generated
	 * @ordered
	 */
	protected Date timeStamp = TIME_STAMP_EDEFAULT;

	/**
	 * The default value of the '{@link #getImportance() <em>Importance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImportance()
	 * @generated
	 * @ordered
	 */
	protected static final NotificationImportance IMPORTANCE_EDEFAULT = NotificationImportance.NONE;

	/**
	 * The cached value of the '{@link #getImportance() <em>Importance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImportance()
	 * @generated
	 * @ordered
	 */
	protected NotificationImportance importance = IMPORTANCE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSeverity() <em>Severity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeverity()
	 * @generated
	 * @ordered
	 */
	protected static final Severity SEVERITY_EDEFAULT = Severity.OK;

	/**
	 * The cached value of the '{@link #getSeverity() <em>Severity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeverity()
	 * @generated
	 * @ordered
	 */
	protected Severity severity = SEVERITY_EDEFAULT;

	/**
	 * The default value of the '{@link #isNoticed() <em>Noticed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNoticed()
	 * @generated
	 * @ordered
	 */
	protected static final boolean NOTICED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isNoticed() <em>Noticed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNoticed()
	 * @generated
	 * @ordered
	 */
	protected boolean noticed = NOTICED_EDEFAULT;

	/**
	 * The default value of the '{@link #getMessage() <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessage()
	 * @generated
	 * @ordered
	 */
	protected static final String MESSAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMessage() <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessage()
	 * @generated
	 * @ordered
	 */
	protected String message = MESSAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDetails() <em>Details</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDetails()
	 * @generated
	 * @ordered
	 */
	protected static final String DETAILS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDetails() <em>Details</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDetails()
	 * @generated
	 * @ordered
	 */
	protected String details = DETAILS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<Notification> children;

	/**
	 * The cached value of the '{@link #getAffectedInfoUnitIds() <em>Affected Info Unit Ids</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAffectedInfoUnitIds()
	 * @generated
	 * @ordered
	 */
	protected EList<String> affectedInfoUnitIds;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NotificationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfomngmntPackage.Literals.NOTIFICATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimeStamp(Date newTimeStamp) {
		Date oldTimeStamp = timeStamp;
		timeStamp = newTimeStamp;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, org.eclipse.emf.common.notify.Notification.SET, InfomngmntPackage.NOTIFICATION__TIME_STAMP, oldTimeStamp, timeStamp));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationImportance getImportance() {
		return importance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImportance(NotificationImportance newImportance) {
		NotificationImportance oldImportance = importance;
		importance = newImportance == null ? IMPORTANCE_EDEFAULT : newImportance;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, org.eclipse.emf.common.notify.Notification.SET, InfomngmntPackage.NOTIFICATION__IMPORTANCE, oldImportance, importance));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Severity getSeverity() {
		return severity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSeverity(Severity newSeverity) {
		Severity oldSeverity = severity;
		severity = newSeverity == null ? SEVERITY_EDEFAULT : newSeverity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, org.eclipse.emf.common.notify.Notification.SET, InfomngmntPackage.NOTIFICATION__SEVERITY, oldSeverity, severity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isNoticed() {
		return noticed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNoticed(boolean newNoticed) {
		boolean oldNoticed = noticed;
		noticed = newNoticed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, org.eclipse.emf.common.notify.Notification.SET, InfomngmntPackage.NOTIFICATION__NOTICED, oldNoticed, noticed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMessage(String newMessage) {
		String oldMessage = message;
		message = newMessage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, org.eclipse.emf.common.notify.Notification.SET, InfomngmntPackage.NOTIFICATION__MESSAGE, oldMessage, message));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDetails(String newDetails) {
		String oldDetails = details;
		details = newDetails;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, org.eclipse.emf.common.notify.Notification.SET, InfomngmntPackage.NOTIFICATION__DETAILS, oldDetails, details));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Notification> getChildren() {
		if (children == null) {
			children = new EObjectContainmentEList<Notification>(Notification.class, this, InfomngmntPackage.NOTIFICATION__CHILDREN);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getAffectedInfoUnitIds() {
		if (affectedInfoUnitIds == null) {
			affectedInfoUnitIds = new EDataTypeUniqueEList<String>(String.class, this, InfomngmntPackage.NOTIFICATION__AFFECTED_INFO_UNIT_IDS);
		}
		return affectedInfoUnitIds;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case InfomngmntPackage.NOTIFICATION__CHILDREN:
				return ((InternalEList<?>)getChildren()).basicRemove(otherEnd, msgs);
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
			case InfomngmntPackage.NOTIFICATION__TIME_STAMP:
				return getTimeStamp();
			case InfomngmntPackage.NOTIFICATION__IMPORTANCE:
				return getImportance();
			case InfomngmntPackage.NOTIFICATION__SEVERITY:
				return getSeverity();
			case InfomngmntPackage.NOTIFICATION__NOTICED:
				return isNoticed() ? Boolean.TRUE : Boolean.FALSE;
			case InfomngmntPackage.NOTIFICATION__MESSAGE:
				return getMessage();
			case InfomngmntPackage.NOTIFICATION__DETAILS:
				return getDetails();
			case InfomngmntPackage.NOTIFICATION__CHILDREN:
				return getChildren();
			case InfomngmntPackage.NOTIFICATION__AFFECTED_INFO_UNIT_IDS:
				return getAffectedInfoUnitIds();
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
			case InfomngmntPackage.NOTIFICATION__TIME_STAMP:
				setTimeStamp((Date)newValue);
				return;
			case InfomngmntPackage.NOTIFICATION__IMPORTANCE:
				setImportance((NotificationImportance)newValue);
				return;
			case InfomngmntPackage.NOTIFICATION__SEVERITY:
				setSeverity((Severity)newValue);
				return;
			case InfomngmntPackage.NOTIFICATION__NOTICED:
				setNoticed(((Boolean)newValue).booleanValue());
				return;
			case InfomngmntPackage.NOTIFICATION__MESSAGE:
				setMessage((String)newValue);
				return;
			case InfomngmntPackage.NOTIFICATION__DETAILS:
				setDetails((String)newValue);
				return;
			case InfomngmntPackage.NOTIFICATION__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends Notification>)newValue);
				return;
			case InfomngmntPackage.NOTIFICATION__AFFECTED_INFO_UNIT_IDS:
				getAffectedInfoUnitIds().clear();
				getAffectedInfoUnitIds().addAll((Collection<? extends String>)newValue);
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
			case InfomngmntPackage.NOTIFICATION__TIME_STAMP:
				setTimeStamp(TIME_STAMP_EDEFAULT);
				return;
			case InfomngmntPackage.NOTIFICATION__IMPORTANCE:
				setImportance(IMPORTANCE_EDEFAULT);
				return;
			case InfomngmntPackage.NOTIFICATION__SEVERITY:
				setSeverity(SEVERITY_EDEFAULT);
				return;
			case InfomngmntPackage.NOTIFICATION__NOTICED:
				setNoticed(NOTICED_EDEFAULT);
				return;
			case InfomngmntPackage.NOTIFICATION__MESSAGE:
				setMessage(MESSAGE_EDEFAULT);
				return;
			case InfomngmntPackage.NOTIFICATION__DETAILS:
				setDetails(DETAILS_EDEFAULT);
				return;
			case InfomngmntPackage.NOTIFICATION__CHILDREN:
				getChildren().clear();
				return;
			case InfomngmntPackage.NOTIFICATION__AFFECTED_INFO_UNIT_IDS:
				getAffectedInfoUnitIds().clear();
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
			case InfomngmntPackage.NOTIFICATION__TIME_STAMP:
				return TIME_STAMP_EDEFAULT == null ? timeStamp != null : !TIME_STAMP_EDEFAULT.equals(timeStamp);
			case InfomngmntPackage.NOTIFICATION__IMPORTANCE:
				return importance != IMPORTANCE_EDEFAULT;
			case InfomngmntPackage.NOTIFICATION__SEVERITY:
				return severity != SEVERITY_EDEFAULT;
			case InfomngmntPackage.NOTIFICATION__NOTICED:
				return noticed != NOTICED_EDEFAULT;
			case InfomngmntPackage.NOTIFICATION__MESSAGE:
				return MESSAGE_EDEFAULT == null ? message != null : !MESSAGE_EDEFAULT.equals(message);
			case InfomngmntPackage.NOTIFICATION__DETAILS:
				return DETAILS_EDEFAULT == null ? details != null : !DETAILS_EDEFAULT.equals(details);
			case InfomngmntPackage.NOTIFICATION__CHILDREN:
				return children != null && !children.isEmpty();
			case InfomngmntPackage.NOTIFICATION__AFFECTED_INFO_UNIT_IDS:
				return affectedInfoUnitIds != null && !affectedInfoUnitIds.isEmpty();
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
		result.append(" (timeStamp: ");
		result.append(timeStamp);
		result.append(", importance: ");
		result.append(importance);
		result.append(", severity: ");
		result.append(severity);
		result.append(", noticed: ");
		result.append(noticed);
		result.append(", message: ");
		result.append(message);
		result.append(", details: ");
		result.append(details);
		result.append(", affectedInfoUnitIds: ");
		result.append(affectedInfoUnitIds);
		result.append(')');
		return result.toString();
	}

} //NotificationImpl
