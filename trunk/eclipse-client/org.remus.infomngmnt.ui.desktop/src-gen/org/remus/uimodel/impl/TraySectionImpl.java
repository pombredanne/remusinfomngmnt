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
package org.remus.uimodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.swt.graphics.Image;

import org.remus.infomngmnt.ui.desktop.extension.AbstractTraySection;

import org.remus.uimodel.TraySection;
import org.remus.uimodel.UimodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tray Section</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.uimodel.impl.TraySectionImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.remus.uimodel.impl.TraySectionImpl#getImage <em>Image</em>}</li>
 *   <li>{@link org.remus.uimodel.impl.TraySectionImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.remus.uimodel.impl.TraySectionImpl#getImplementation <em>Implementation</em>}</li>
 *   <li>{@link org.remus.uimodel.impl.TraySectionImpl#getTemplateId <em>Template Id</em>}</li>
 *   <li>{@link org.remus.uimodel.impl.TraySectionImpl#getPreferenceOptions <em>Preference Options</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TraySectionImpl extends EObjectImpl implements TraySection {
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
	 * The default value of the '{@link #getImage() <em>Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImage()
	 * @generated
	 * @ordered
	 */
	protected static final Image IMAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImage() <em>Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImage()
	 * @generated
	 * @ordered
	 */
	protected Image image = IMAGE_EDEFAULT;

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
	 * The default value of the '{@link #getImplementation() <em>Implementation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementation()
	 * @generated
	 * @ordered
	 */
	protected static final AbstractTraySection IMPLEMENTATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImplementation() <em>Implementation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementation()
	 * @generated
	 * @ordered
	 */
	protected AbstractTraySection implementation = IMPLEMENTATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getTemplateId() <em>Template Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemplateId()
	 * @generated
	 * @ordered
	 */
	protected static final String TEMPLATE_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTemplateId() <em>Template Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemplateId()
	 * @generated
	 * @ordered
	 */
	protected String templateId = TEMPLATE_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPreferenceOptions() <em>Preference Options</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreferenceOptions()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> preferenceOptions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TraySectionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UimodelPackage.Literals.TRAY_SECTION;
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
			eNotify(new ENotificationImpl(this, Notification.SET, UimodelPackage.TRAY_SECTION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImage(Image newImage) {
		Image oldImage = image;
		image = newImage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UimodelPackage.TRAY_SECTION__IMAGE, oldImage, image));
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
			eNotify(new ENotificationImpl(this, Notification.SET, UimodelPackage.TRAY_SECTION__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractTraySection getImplementation() {
		return implementation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplementation(AbstractTraySection newImplementation) {
		AbstractTraySection oldImplementation = implementation;
		implementation = newImplementation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UimodelPackage.TRAY_SECTION__IMPLEMENTATION, oldImplementation, implementation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTemplateId() {
		return templateId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTemplateId(String newTemplateId) {
		String oldTemplateId = templateId;
		templateId = newTemplateId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UimodelPackage.TRAY_SECTION__TEMPLATE_ID, oldTemplateId, templateId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getPreferenceOptions() {
		if (preferenceOptions == null) {
			preferenceOptions = new EcoreEMap<String,String>(UimodelPackage.Literals.STRING_TO_STRING_MAP, StringToStringMapImpl.class, this, UimodelPackage.TRAY_SECTION__PREFERENCE_OPTIONS);
		}
		return preferenceOptions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case UimodelPackage.TRAY_SECTION__PREFERENCE_OPTIONS:
				return ((InternalEList<?>)getPreferenceOptions()).basicRemove(otherEnd, msgs);
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
			case UimodelPackage.TRAY_SECTION__NAME:
				return getName();
			case UimodelPackage.TRAY_SECTION__IMAGE:
				return getImage();
			case UimodelPackage.TRAY_SECTION__DESCRIPTION:
				return getDescription();
			case UimodelPackage.TRAY_SECTION__IMPLEMENTATION:
				return getImplementation();
			case UimodelPackage.TRAY_SECTION__TEMPLATE_ID:
				return getTemplateId();
			case UimodelPackage.TRAY_SECTION__PREFERENCE_OPTIONS:
				if (coreType) return getPreferenceOptions();
				else return getPreferenceOptions().map();
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
			case UimodelPackage.TRAY_SECTION__NAME:
				setName((String)newValue);
				return;
			case UimodelPackage.TRAY_SECTION__IMAGE:
				setImage((Image)newValue);
				return;
			case UimodelPackage.TRAY_SECTION__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case UimodelPackage.TRAY_SECTION__IMPLEMENTATION:
				setImplementation((AbstractTraySection)newValue);
				return;
			case UimodelPackage.TRAY_SECTION__TEMPLATE_ID:
				setTemplateId((String)newValue);
				return;
			case UimodelPackage.TRAY_SECTION__PREFERENCE_OPTIONS:
				((EStructuralFeature.Setting)getPreferenceOptions()).set(newValue);
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
			case UimodelPackage.TRAY_SECTION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UimodelPackage.TRAY_SECTION__IMAGE:
				setImage(IMAGE_EDEFAULT);
				return;
			case UimodelPackage.TRAY_SECTION__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case UimodelPackage.TRAY_SECTION__IMPLEMENTATION:
				setImplementation(IMPLEMENTATION_EDEFAULT);
				return;
			case UimodelPackage.TRAY_SECTION__TEMPLATE_ID:
				setTemplateId(TEMPLATE_ID_EDEFAULT);
				return;
			case UimodelPackage.TRAY_SECTION__PREFERENCE_OPTIONS:
				getPreferenceOptions().clear();
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
			case UimodelPackage.TRAY_SECTION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UimodelPackage.TRAY_SECTION__IMAGE:
				return IMAGE_EDEFAULT == null ? image != null : !IMAGE_EDEFAULT.equals(image);
			case UimodelPackage.TRAY_SECTION__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case UimodelPackage.TRAY_SECTION__IMPLEMENTATION:
				return IMPLEMENTATION_EDEFAULT == null ? implementation != null : !IMPLEMENTATION_EDEFAULT.equals(implementation);
			case UimodelPackage.TRAY_SECTION__TEMPLATE_ID:
				return TEMPLATE_ID_EDEFAULT == null ? templateId != null : !TEMPLATE_ID_EDEFAULT.equals(templateId);
			case UimodelPackage.TRAY_SECTION__PREFERENCE_OPTIONS:
				return preferenceOptions != null && !preferenceOptions.isEmpty();
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
		result.append(", image: ");
		result.append(image);
		result.append(", description: ");
		result.append(description);
		result.append(", implementation: ");
		result.append(implementation);
		result.append(", templateId: ");
		result.append(templateId);
		result.append(')');
		return result.toString();
	}

} //TraySectionImpl
