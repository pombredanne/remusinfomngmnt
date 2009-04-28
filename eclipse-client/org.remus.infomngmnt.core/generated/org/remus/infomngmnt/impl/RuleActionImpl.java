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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.RuleAction;
import org.remus.infomngmnt.RuleValue;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule Action</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.impl.RuleActionImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.RuleActionImpl#getInfoTypeId <em>Info Type Id</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.RuleActionImpl#getRuleValue <em>Rule Value</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.RuleActionImpl#getGroovyMatcher <em>Groovy Matcher</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RuleActionImpl extends EObjectImpl implements RuleAction {
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
	 * The default value of the '{@link #getInfoTypeId() <em>Info Type Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInfoTypeId()
	 * @generated
	 * @ordered
	 */
	protected static final String INFO_TYPE_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInfoTypeId() <em>Info Type Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInfoTypeId()
	 * @generated
	 * @ordered
	 */
	protected String infoTypeId = INFO_TYPE_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRuleValue() <em>Rule Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleValue()
	 * @generated
	 * @ordered
	 */
	protected RuleValue ruleValue;

	/**
	 * The default value of the '{@link #getGroovyMatcher() <em>Groovy Matcher</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroovyMatcher()
	 * @generated
	 * @ordered
	 */
	protected static final String GROOVY_MATCHER_EDEFAULT = "return true";

	/**
	 * The cached value of the '{@link #getGroovyMatcher() <em>Groovy Matcher</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroovyMatcher()
	 * @generated
	 * @ordered
	 */
	protected String groovyMatcher = GROOVY_MATCHER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleActionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfomngmntPackage.Literals.RULE_ACTION;
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
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.RULE_ACTION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInfoTypeId() {
		return infoTypeId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInfoTypeId(String newInfoTypeId) {
		String oldInfoTypeId = infoTypeId;
		infoTypeId = newInfoTypeId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.RULE_ACTION__INFO_TYPE_ID, oldInfoTypeId, infoTypeId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleValue getRuleValue() {
		return ruleValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRuleValue(RuleValue newRuleValue, NotificationChain msgs) {
		RuleValue oldRuleValue = ruleValue;
		ruleValue = newRuleValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, InfomngmntPackage.RULE_ACTION__RULE_VALUE, oldRuleValue, newRuleValue);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRuleValue(RuleValue newRuleValue) {
		if (newRuleValue != ruleValue) {
			NotificationChain msgs = null;
			if (ruleValue != null)
				msgs = ((InternalEObject)ruleValue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - InfomngmntPackage.RULE_ACTION__RULE_VALUE, null, msgs);
			if (newRuleValue != null)
				msgs = ((InternalEObject)newRuleValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - InfomngmntPackage.RULE_ACTION__RULE_VALUE, null, msgs);
			msgs = basicSetRuleValue(newRuleValue, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.RULE_ACTION__RULE_VALUE, newRuleValue, newRuleValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getGroovyMatcher() {
		return groovyMatcher;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGroovyMatcher(String newGroovyMatcher) {
		String oldGroovyMatcher = groovyMatcher;
		groovyMatcher = newGroovyMatcher;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.RULE_ACTION__GROOVY_MATCHER, oldGroovyMatcher, groovyMatcher));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case InfomngmntPackage.RULE_ACTION__RULE_VALUE:
				return basicSetRuleValue(null, msgs);
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
			case InfomngmntPackage.RULE_ACTION__NAME:
				return getName();
			case InfomngmntPackage.RULE_ACTION__INFO_TYPE_ID:
				return getInfoTypeId();
			case InfomngmntPackage.RULE_ACTION__RULE_VALUE:
				return getRuleValue();
			case InfomngmntPackage.RULE_ACTION__GROOVY_MATCHER:
				return getGroovyMatcher();
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
			case InfomngmntPackage.RULE_ACTION__NAME:
				setName((String)newValue);
				return;
			case InfomngmntPackage.RULE_ACTION__INFO_TYPE_ID:
				setInfoTypeId((String)newValue);
				return;
			case InfomngmntPackage.RULE_ACTION__RULE_VALUE:
				setRuleValue((RuleValue)newValue);
				return;
			case InfomngmntPackage.RULE_ACTION__GROOVY_MATCHER:
				setGroovyMatcher((String)newValue);
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
			case InfomngmntPackage.RULE_ACTION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case InfomngmntPackage.RULE_ACTION__INFO_TYPE_ID:
				setInfoTypeId(INFO_TYPE_ID_EDEFAULT);
				return;
			case InfomngmntPackage.RULE_ACTION__RULE_VALUE:
				setRuleValue((RuleValue)null);
				return;
			case InfomngmntPackage.RULE_ACTION__GROOVY_MATCHER:
				setGroovyMatcher(GROOVY_MATCHER_EDEFAULT);
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
			case InfomngmntPackage.RULE_ACTION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case InfomngmntPackage.RULE_ACTION__INFO_TYPE_ID:
				return INFO_TYPE_ID_EDEFAULT == null ? infoTypeId != null : !INFO_TYPE_ID_EDEFAULT.equals(infoTypeId);
			case InfomngmntPackage.RULE_ACTION__RULE_VALUE:
				return ruleValue != null;
			case InfomngmntPackage.RULE_ACTION__GROOVY_MATCHER:
				return GROOVY_MATCHER_EDEFAULT == null ? groovyMatcher != null : !GROOVY_MATCHER_EDEFAULT.equals(groovyMatcher);
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
		result.append(", infoTypeId: ");
		result.append(infoTypeId);
		result.append(", groovyMatcher: ");
		result.append(groovyMatcher);
		result.append(')');
		return result.toString();
	}

} //RuleActionImpl
