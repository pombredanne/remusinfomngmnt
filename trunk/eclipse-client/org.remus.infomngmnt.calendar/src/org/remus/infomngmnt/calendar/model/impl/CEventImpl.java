/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.remus.infomngmnt.calendar.model.impl;

import java.util.Collection;
import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.remus.infomngmnt.calendar.model.CEvent;
import org.remus.infomngmnt.calendar.model.ModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>CEvent</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.calendar.model.impl.CEventImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.impl.CEventImpl#isDateAbsolute <em>Date Absolute</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.impl.CEventImpl#getDate <em>Date</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.impl.CEventImpl#getReferenceEvent <em>Reference Event</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.impl.CEventImpl#isAlarm <em>Alarm</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.impl.CEventImpl#getPredecessors <em>Predecessors</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.impl.CEventImpl#getSuccessors <em>Successors</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.impl.CEventImpl#isAlarmDue <em>Alarm Due</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CEventImpl extends EObjectImpl implements CEvent {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isDateAbsolute() <em>Date Absolute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDateAbsolute()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DATE_ABSOLUTE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isDateAbsolute() <em>Date Absolute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDateAbsolute()
	 * @generated
	 * @ordered
	 */
	protected boolean dateAbsolute = DATE_ABSOLUTE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDate() <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDate() <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDate()
	 * @generated
	 * @ordered
	 */
	protected Date date = DATE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getReferenceEvent() <em>Reference Event</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferenceEvent()
	 * @generated
	 * @ordered
	 */
	protected CEvent referenceEvent;

	/**
	 * The default value of the '{@link #isAlarm() <em>Alarm</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAlarm()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ALARM_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAlarm() <em>Alarm</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAlarm()
	 * @generated
	 * @ordered
	 */
	protected boolean alarm = ALARM_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPredecessors() <em>Predecessors</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPredecessors()
	 * @generated
	 * @ordered
	 */
	protected EList<CEvent> predecessors;

	/**
	 * The cached value of the '{@link #getSuccessors() <em>Successors</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuccessors()
	 * @generated
	 * @ordered
	 */
	protected EList<CEvent> successors;

	/**
	 * The default value of the '{@link #isAlarmDue() <em>Alarm Due</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAlarmDue()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ALARM_DUE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAlarmDue() <em>Alarm Due</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAlarmDue()
	 * @generated
	 * @ordered
	 */
	protected boolean alarmDue = ALARM_DUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CEventImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.CEVENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CEVENT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDateAbsolute() {
		return dateAbsolute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDateAbsolute(boolean newDateAbsolute) {
		boolean oldDateAbsolute = dateAbsolute;
		dateAbsolute = newDateAbsolute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CEVENT__DATE_ABSOLUTE, oldDateAbsolute, dateAbsolute));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDate(Date newDate) {
		Date oldDate = date;
		date = newDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CEVENT__DATE, oldDate, date));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CEvent getReferenceEvent() {
		if (referenceEvent != null && referenceEvent.eIsProxy()) {
			InternalEObject oldReferenceEvent = (InternalEObject)referenceEvent;
			referenceEvent = (CEvent)eResolveProxy(oldReferenceEvent);
			if (referenceEvent != oldReferenceEvent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.CEVENT__REFERENCE_EVENT, oldReferenceEvent, referenceEvent));
			}
		}
		return referenceEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CEvent basicGetReferenceEvent() {
		return referenceEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReferenceEvent(CEvent newReferenceEvent) {
		CEvent oldReferenceEvent = referenceEvent;
		referenceEvent = newReferenceEvent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CEVENT__REFERENCE_EVENT, oldReferenceEvent, referenceEvent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAlarm() {
		return alarm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAlarm(boolean newAlarm) {
		boolean oldAlarm = alarm;
		alarm = newAlarm;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CEVENT__ALARM, oldAlarm, alarm));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CEvent> getPredecessors() {
		if (predecessors == null) {
			predecessors = new EObjectWithInverseResolvingEList.ManyInverse<CEvent>(CEvent.class, this, ModelPackage.CEVENT__PREDECESSORS, ModelPackage.CEVENT__SUCCESSORS);
		}
		return predecessors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CEvent> getSuccessors() {
		if (successors == null) {
			successors = new EObjectWithInverseResolvingEList.ManyInverse<CEvent>(CEvent.class, this, ModelPackage.CEVENT__SUCCESSORS, ModelPackage.CEVENT__PREDECESSORS);
		}
		return successors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAlarmDue() {
		return alarmDue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAlarmDue(boolean newAlarmDue) {
		boolean oldAlarmDue = alarmDue;
		alarmDue = newAlarmDue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CEVENT__ALARM_DUE, oldAlarmDue, alarmDue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.CEVENT__PREDECESSORS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getPredecessors()).basicAdd(otherEnd, msgs);
			case ModelPackage.CEVENT__SUCCESSORS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getSuccessors()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.CEVENT__PREDECESSORS:
				return ((InternalEList<?>)getPredecessors()).basicRemove(otherEnd, msgs);
			case ModelPackage.CEVENT__SUCCESSORS:
				return ((InternalEList<?>)getSuccessors()).basicRemove(otherEnd, msgs);
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
			case ModelPackage.CEVENT__NAME:
				return getName();
			case ModelPackage.CEVENT__DATE_ABSOLUTE:
				return isDateAbsolute() ? Boolean.TRUE : Boolean.FALSE;
			case ModelPackage.CEVENT__DATE:
				return getDate();
			case ModelPackage.CEVENT__REFERENCE_EVENT:
				if (resolve) return getReferenceEvent();
				return basicGetReferenceEvent();
			case ModelPackage.CEVENT__ALARM:
				return isAlarm() ? Boolean.TRUE : Boolean.FALSE;
			case ModelPackage.CEVENT__PREDECESSORS:
				return getPredecessors();
			case ModelPackage.CEVENT__SUCCESSORS:
				return getSuccessors();
			case ModelPackage.CEVENT__ALARM_DUE:
				return isAlarmDue() ? Boolean.TRUE : Boolean.FALSE;
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
			case ModelPackage.CEVENT__NAME:
				setName((String)newValue);
				return;
			case ModelPackage.CEVENT__DATE_ABSOLUTE:
				setDateAbsolute(((Boolean)newValue).booleanValue());
				return;
			case ModelPackage.CEVENT__DATE:
				setDate((Date)newValue);
				return;
			case ModelPackage.CEVENT__REFERENCE_EVENT:
				setReferenceEvent((CEvent)newValue);
				return;
			case ModelPackage.CEVENT__ALARM:
				setAlarm(((Boolean)newValue).booleanValue());
				return;
			case ModelPackage.CEVENT__PREDECESSORS:
				getPredecessors().clear();
				getPredecessors().addAll((Collection<? extends CEvent>)newValue);
				return;
			case ModelPackage.CEVENT__SUCCESSORS:
				getSuccessors().clear();
				getSuccessors().addAll((Collection<? extends CEvent>)newValue);
				return;
			case ModelPackage.CEVENT__ALARM_DUE:
				setAlarmDue(((Boolean)newValue).booleanValue());
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
			case ModelPackage.CEVENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ModelPackage.CEVENT__DATE_ABSOLUTE:
				setDateAbsolute(DATE_ABSOLUTE_EDEFAULT);
				return;
			case ModelPackage.CEVENT__DATE:
				setDate(DATE_EDEFAULT);
				return;
			case ModelPackage.CEVENT__REFERENCE_EVENT:
				setReferenceEvent((CEvent)null);
				return;
			case ModelPackage.CEVENT__ALARM:
				setAlarm(ALARM_EDEFAULT);
				return;
			case ModelPackage.CEVENT__PREDECESSORS:
				getPredecessors().clear();
				return;
			case ModelPackage.CEVENT__SUCCESSORS:
				getSuccessors().clear();
				return;
			case ModelPackage.CEVENT__ALARM_DUE:
				setAlarmDue(ALARM_DUE_EDEFAULT);
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
			case ModelPackage.CEVENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ModelPackage.CEVENT__DATE_ABSOLUTE:
				return dateAbsolute != DATE_ABSOLUTE_EDEFAULT;
			case ModelPackage.CEVENT__DATE:
				return DATE_EDEFAULT == null ? date != null : !DATE_EDEFAULT.equals(date);
			case ModelPackage.CEVENT__REFERENCE_EVENT:
				return referenceEvent != null;
			case ModelPackage.CEVENT__ALARM:
				return alarm != ALARM_EDEFAULT;
			case ModelPackage.CEVENT__PREDECESSORS:
				return predecessors != null && !predecessors.isEmpty();
			case ModelPackage.CEVENT__SUCCESSORS:
				return successors != null && !successors.isEmpty();
			case ModelPackage.CEVENT__ALARM_DUE:
				return alarmDue != ALARM_DUE_EDEFAULT;
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
		result.append(" (name: ");
		result.append(name);
		result.append(", dateAbsolute: ");
		result.append(dateAbsolute);
		result.append(", date: ");
		result.append(date);
		result.append(", alarm: ");
		result.append(alarm);
		result.append(", alarmDue: ");
		result.append(alarmDue);
		result.append(')');
		return result.toString();
	}

} //CEventImpl
