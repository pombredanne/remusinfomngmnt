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
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationStructure;
import org.remus.infomngmnt.InformationStructureItem;
import org.remus.infomngmnt.InformationStructureType;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Information Structure</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.remus.infomngmnt.impl.InformationStructureImpl#getType <em>
 * Type</em>}</li>
 * <li>
 * {@link org.remus.infomngmnt.impl.InformationStructureImpl#getStructureItems
 * <em>Structure Items</em>}</li>
 * <li>
 * {@link org.remus.infomngmnt.impl.InformationStructureImpl#getReferencedStructureItems
 * <em>Referenced Structure Items</em>}</li>
 * <li>
 * {@link org.remus.infomngmnt.impl.InformationStructureImpl#isCanHaveBinaryReferences
 * <em>Can Have Binary References</em>}</li>
 * <li>{@link org.remus.infomngmnt.impl.InformationStructureImpl#getLabel <em>
 * Label</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public abstract class InformationStructureImpl extends EObjectImpl implements InformationStructure {
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final InformationStructureType TYPE_EDEFAULT = InformationStructureType.NONE;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected InformationStructureType type = TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getStructureItems()
	 * <em>Structure Items</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStructureItems()
	 * @generated
	 * @ordered
	 */
	protected EList<InformationStructureItem> structureItems;

	/**
	 * The cached value of the '{@link #getReferencedStructureItems()
	 * <em>Referenced Structure Items</em>}' reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getReferencedStructureItems()
	 * @generated
	 * @ordered
	 */
	protected EList<InformationStructureItem> referencedStructureItems;

	/**
	 * The default value of the '{@link #isCanHaveBinaryReferences()
	 * <em>Can Have Binary References</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #isCanHaveBinaryReferences()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CAN_HAVE_BINARY_REFERENCES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCanHaveBinaryReferences()
	 * <em>Can Have Binary References</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #isCanHaveBinaryReferences()
	 * @generated
	 * @ordered
	 */
	protected boolean canHaveBinaryReferences = CAN_HAVE_BINARY_REFERENCES_EDEFAULT;

	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected InformationStructureImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfomngmntPackage.Literals.INFORMATION_STRUCTURE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public InformationStructureType getType() {
		return this.type;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setType(final InformationStructureType newType) {
		InformationStructureType oldType = this.type;
		this.type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					InfomngmntPackage.INFORMATION_STRUCTURE__TYPE, oldType, this.type));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<InformationStructureItem> getStructureItems() {
		if (this.structureItems == null) {
			this.structureItems = new EObjectContainmentEList<InformationStructureItem>(
					InformationStructureItem.class, this,
					InfomngmntPackage.INFORMATION_STRUCTURE__STRUCTURE_ITEMS);
		}
		return this.structureItems;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<InformationStructureItem> getReferencedStructureItems() {
		if (this.referencedStructureItems == null) {
			this.referencedStructureItems = new EObjectResolvingEList<InformationStructureItem>(
					InformationStructureItem.class, this,
					InfomngmntPackage.INFORMATION_STRUCTURE__REFERENCED_STRUCTURE_ITEMS);
		}
		return this.referencedStructureItems;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isCanHaveBinaryReferences() {
		return this.canHaveBinaryReferences;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setCanHaveBinaryReferences(final boolean newCanHaveBinaryReferences) {
		boolean oldCanHaveBinaryReferences = this.canHaveBinaryReferences;
		this.canHaveBinaryReferences = newCanHaveBinaryReferences;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					InfomngmntPackage.INFORMATION_STRUCTURE__CAN_HAVE_BINARY_REFERENCES,
					oldCanHaveBinaryReferences, this.canHaveBinaryReferences));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setLabel(final String newLabel) {
		String oldLabel = this.label;
		this.label = newLabel;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					InfomngmntPackage.INFORMATION_STRUCTURE__LABEL, oldLabel, this.label));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(final InternalEObject otherEnd, final int featureID,
			final NotificationChain msgs) {
		switch (featureID) {
		case InfomngmntPackage.INFORMATION_STRUCTURE__STRUCTURE_ITEMS:
			return ((InternalEList<?>) getStructureItems()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
		switch (featureID) {
		case InfomngmntPackage.INFORMATION_STRUCTURE__TYPE:
			return getType();
		case InfomngmntPackage.INFORMATION_STRUCTURE__STRUCTURE_ITEMS:
			return getStructureItems();
		case InfomngmntPackage.INFORMATION_STRUCTURE__REFERENCED_STRUCTURE_ITEMS:
			return getReferencedStructureItems();
		case InfomngmntPackage.INFORMATION_STRUCTURE__CAN_HAVE_BINARY_REFERENCES:
			return isCanHaveBinaryReferences();
		case InfomngmntPackage.INFORMATION_STRUCTURE__LABEL:
			return getLabel();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(final int featureID, final Object newValue) {
		switch (featureID) {
		case InfomngmntPackage.INFORMATION_STRUCTURE__TYPE:
			setType((InformationStructureType) newValue);
			return;
		case InfomngmntPackage.INFORMATION_STRUCTURE__STRUCTURE_ITEMS:
			getStructureItems().clear();
			getStructureItems().addAll((Collection<? extends InformationStructureItem>) newValue);
			return;
		case InfomngmntPackage.INFORMATION_STRUCTURE__REFERENCED_STRUCTURE_ITEMS:
			getReferencedStructureItems().clear();
			getReferencedStructureItems().addAll(
					(Collection<? extends InformationStructureItem>) newValue);
			return;
		case InfomngmntPackage.INFORMATION_STRUCTURE__CAN_HAVE_BINARY_REFERENCES:
			setCanHaveBinaryReferences((Boolean) newValue);
			return;
		case InfomngmntPackage.INFORMATION_STRUCTURE__LABEL:
			setLabel((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(final int featureID) {
		switch (featureID) {
		case InfomngmntPackage.INFORMATION_STRUCTURE__TYPE:
			setType(TYPE_EDEFAULT);
			return;
		case InfomngmntPackage.INFORMATION_STRUCTURE__STRUCTURE_ITEMS:
			getStructureItems().clear();
			return;
		case InfomngmntPackage.INFORMATION_STRUCTURE__REFERENCED_STRUCTURE_ITEMS:
			getReferencedStructureItems().clear();
			return;
		case InfomngmntPackage.INFORMATION_STRUCTURE__CAN_HAVE_BINARY_REFERENCES:
			setCanHaveBinaryReferences(CAN_HAVE_BINARY_REFERENCES_EDEFAULT);
			return;
		case InfomngmntPackage.INFORMATION_STRUCTURE__LABEL:
			setLabel(LABEL_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(final int featureID) {
		switch (featureID) {
		case InfomngmntPackage.INFORMATION_STRUCTURE__TYPE:
			return this.type != TYPE_EDEFAULT;
		case InfomngmntPackage.INFORMATION_STRUCTURE__STRUCTURE_ITEMS:
			return this.structureItems != null && !this.structureItems.isEmpty();
		case InfomngmntPackage.INFORMATION_STRUCTURE__REFERENCED_STRUCTURE_ITEMS:
			return this.referencedStructureItems != null
					&& !this.referencedStructureItems.isEmpty();
		case InfomngmntPackage.INFORMATION_STRUCTURE__CAN_HAVE_BINARY_REFERENCES:
			return this.canHaveBinaryReferences != CAN_HAVE_BINARY_REFERENCES_EDEFAULT;
		case InfomngmntPackage.INFORMATION_STRUCTURE__LABEL:
			return LABEL_EDEFAULT == null ? this.label != null : !LABEL_EDEFAULT.equals(this.label);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (type: ");
		result.append(this.type);
		result.append(", canHaveBinaryReferences: ");
		result.append(this.canHaveBinaryReferences);
		result.append(", label: ");
		result.append(this.label);
		result.append(')');
		return result.toString();
	}

} // InformationStructureImpl
