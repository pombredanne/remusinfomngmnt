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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.Rule;
import org.remus.infomngmnt.RuleAction;
import org.remus.infomngmnt.RuleValue;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.impl.RuleImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.RuleImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.RuleImpl#isActivated <em>Activated</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.RuleImpl#getStringRepresentation <em>String Representation</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.RuleImpl#getRuleValue <em>Rule Value</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.RuleImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.RuleImpl#getAction <em>Action</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RuleImpl extends EObjectImpl implements Rule {
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
	 * The default value of the '{@link #isActivated() <em>Activated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isActivated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ACTIVATED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isActivated() <em>Activated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isActivated()
	 * @generated
	 * @ordered
	 */
	protected boolean activated = ACTIVATED_EDEFAULT;

	/**
	 * The default value of the '{@link #getStringRepresentation() <em>String Representation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStringRepresentation()
	 * @generated
	 * @ordered
	 */
	protected static final String STRING_REPRESENTATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStringRepresentation() <em>String Representation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStringRepresentation()
	 * @generated
	 * @ordered
	 */
	protected String stringRepresentation = STRING_REPRESENTATION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRuleValue() <em>Rule Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleValue()
	 * @generated
	 * @ordered
	 */
	protected RuleValue ruleValue;

	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<Rule> children;

	/**
	 * The cached value of the '{@link #getAction() <em>Action</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAction()
	 * @generated
	 * @ordered
	 */
	protected RuleAction action;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfomngmntPackage.Literals.RULE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.RULE__ID, oldId, id));
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
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.RULE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isActivated() {
		return activated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActivated(boolean newActivated) {
		boolean oldActivated = activated;
		activated = newActivated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.RULE__ACTIVATED, oldActivated, activated));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getStringRepresentation() {
		return stringRepresentation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStringRepresentation(String newStringRepresentation) {
		String oldStringRepresentation = stringRepresentation;
		stringRepresentation = newStringRepresentation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.RULE__STRING_REPRESENTATION, oldStringRepresentation, stringRepresentation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleValue getRuleValue() {
		if (ruleValue != null && ruleValue.eIsProxy()) {
			InternalEObject oldRuleValue = (InternalEObject)ruleValue;
			ruleValue = (RuleValue)eResolveProxy(oldRuleValue);
			if (ruleValue != oldRuleValue) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, InfomngmntPackage.RULE__RULE_VALUE, oldRuleValue, ruleValue));
			}
		}
		return ruleValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleValue basicGetRuleValue() {
		return ruleValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRuleValue(RuleValue newRuleValue) {
		RuleValue oldRuleValue = ruleValue;
		ruleValue = newRuleValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.RULE__RULE_VALUE, oldRuleValue, ruleValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Rule> getChildren() {
		if (children == null) {
			children = new EObjectContainmentEList<Rule>(Rule.class, this, InfomngmntPackage.RULE__CHILDREN);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleAction getAction() {
		if (action != null && action.eIsProxy()) {
			InternalEObject oldAction = (InternalEObject)action;
			action = (RuleAction)eResolveProxy(oldAction);
			if (action != oldAction) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, InfomngmntPackage.RULE__ACTION, oldAction, action));
			}
		}
		return action;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleAction basicGetAction() {
		return action;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAction(RuleAction newAction) {
		RuleAction oldAction = action;
		action = newAction;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.RULE__ACTION, oldAction, action));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case InfomngmntPackage.RULE__CHILDREN:
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
			case InfomngmntPackage.RULE__ID:
				return getId();
			case InfomngmntPackage.RULE__NAME:
				return getName();
			case InfomngmntPackage.RULE__ACTIVATED:
				return isActivated() ? Boolean.TRUE : Boolean.FALSE;
			case InfomngmntPackage.RULE__STRING_REPRESENTATION:
				return getStringRepresentation();
			case InfomngmntPackage.RULE__RULE_VALUE:
				if (resolve) return getRuleValue();
				return basicGetRuleValue();
			case InfomngmntPackage.RULE__CHILDREN:
				return getChildren();
			case InfomngmntPackage.RULE__ACTION:
				if (resolve) return getAction();
				return basicGetAction();
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
			case InfomngmntPackage.RULE__ID:
				setId((String)newValue);
				return;
			case InfomngmntPackage.RULE__NAME:
				setName((String)newValue);
				return;
			case InfomngmntPackage.RULE__ACTIVATED:
				setActivated(((Boolean)newValue).booleanValue());
				return;
			case InfomngmntPackage.RULE__STRING_REPRESENTATION:
				setStringRepresentation((String)newValue);
				return;
			case InfomngmntPackage.RULE__RULE_VALUE:
				setRuleValue((RuleValue)newValue);
				return;
			case InfomngmntPackage.RULE__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends Rule>)newValue);
				return;
			case InfomngmntPackage.RULE__ACTION:
				setAction((RuleAction)newValue);
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
			case InfomngmntPackage.RULE__ID:
				setId(ID_EDEFAULT);
				return;
			case InfomngmntPackage.RULE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case InfomngmntPackage.RULE__ACTIVATED:
				setActivated(ACTIVATED_EDEFAULT);
				return;
			case InfomngmntPackage.RULE__STRING_REPRESENTATION:
				setStringRepresentation(STRING_REPRESENTATION_EDEFAULT);
				return;
			case InfomngmntPackage.RULE__RULE_VALUE:
				setRuleValue((RuleValue)null);
				return;
			case InfomngmntPackage.RULE__CHILDREN:
				getChildren().clear();
				return;
			case InfomngmntPackage.RULE__ACTION:
				setAction((RuleAction)null);
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
			case InfomngmntPackage.RULE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case InfomngmntPackage.RULE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case InfomngmntPackage.RULE__ACTIVATED:
				return activated != ACTIVATED_EDEFAULT;
			case InfomngmntPackage.RULE__STRING_REPRESENTATION:
				return STRING_REPRESENTATION_EDEFAULT == null ? stringRepresentation != null : !STRING_REPRESENTATION_EDEFAULT.equals(stringRepresentation);
			case InfomngmntPackage.RULE__RULE_VALUE:
				return ruleValue != null;
			case InfomngmntPackage.RULE__CHILDREN:
				return children != null && !children.isEmpty();
			case InfomngmntPackage.RULE__ACTION:
				return action != null;
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
		result.append(", name: ");
		result.append(name);
		result.append(", activated: ");
		result.append(activated);
		result.append(", stringRepresentation: ");
		result.append(stringRepresentation);
		result.append(')');
		return result.toString();
	}

} //RuleImpl
