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
package org.remus.infomngmnt.search.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.swt.graphics.Image;

import org.remus.infomngmnt.search.SearchPackage;
import org.remus.infomngmnt.search.Websearch;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Websearch</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.search.impl.WebsearchImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.impl.WebsearchImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.impl.WebsearchImpl#getPattern <em>Pattern</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.impl.WebsearchImpl#getImagePath <em>Image Path</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.impl.WebsearchImpl#getContributor <em>Contributor</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.impl.WebsearchImpl#getImage <em>Image</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WebsearchImpl extends EObjectImpl implements Websearch {
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
	 * The default value of the '{@link #getPattern() <em>Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPattern()
	 * @generated
	 * @ordered
	 */
	protected static final String PATTERN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPattern() <em>Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPattern()
	 * @generated
	 * @ordered
	 */
	protected String pattern = PATTERN_EDEFAULT;

	/**
	 * The default value of the '{@link #getImagePath() <em>Image Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImagePath()
	 * @generated
	 * @ordered
	 */
	protected static final String IMAGE_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImagePath() <em>Image Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImagePath()
	 * @generated
	 * @ordered
	 */
	protected String imagePath = IMAGE_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getContributor() <em>Contributor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContributor()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTRIBUTOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getContributor() <em>Contributor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContributor()
	 * @generated
	 * @ordered
	 */
	protected String contributor = CONTRIBUTOR_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WebsearchImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SearchPackage.Literals.WEBSEARCH;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = this.id;
		this.id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.WEBSEARCH__ID, oldId, this.id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.WEBSEARCH__NAME, oldName, this.name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPattern() {
		return this.pattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPattern(String newPattern) {
		String oldPattern = this.pattern;
		this.pattern = newPattern;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.WEBSEARCH__PATTERN, oldPattern, this.pattern));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImagePath() {
		return this.imagePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImagePath(String newImagePath) {
		String oldImagePath = this.imagePath;
		this.imagePath = newImagePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.WEBSEARCH__IMAGE_PATH, oldImagePath, this.imagePath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getContributor() {
		return this.contributor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContributor(String newContributor) {
		String oldContributor = this.contributor;
		this.contributor = newContributor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.WEBSEARCH__CONTRIBUTOR, oldContributor, this.contributor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Image getImage() {
		return this.image;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImage(Image newImage) {
		Image oldImage = this.image;
		this.image = newImage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.WEBSEARCH__IMAGE, oldImage, this.image));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case SearchPackage.WEBSEARCH__ID:
			return getId();
		case SearchPackage.WEBSEARCH__NAME:
			return getName();
		case SearchPackage.WEBSEARCH__PATTERN:
			return getPattern();
		case SearchPackage.WEBSEARCH__IMAGE_PATH:
			return getImagePath();
		case SearchPackage.WEBSEARCH__CONTRIBUTOR:
			return getContributor();
		case SearchPackage.WEBSEARCH__IMAGE:
			return getImage();
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
		case SearchPackage.WEBSEARCH__ID:
			setId((String)newValue);
			return;
		case SearchPackage.WEBSEARCH__NAME:
			setName((String)newValue);
			return;
		case SearchPackage.WEBSEARCH__PATTERN:
			setPattern((String)newValue);
			return;
		case SearchPackage.WEBSEARCH__IMAGE_PATH:
			setImagePath((String)newValue);
			return;
		case SearchPackage.WEBSEARCH__CONTRIBUTOR:
			setContributor((String)newValue);
			return;
		case SearchPackage.WEBSEARCH__IMAGE:
			setImage((Image)newValue);
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
		case SearchPackage.WEBSEARCH__ID:
			setId(ID_EDEFAULT);
			return;
		case SearchPackage.WEBSEARCH__NAME:
			setName(NAME_EDEFAULT);
			return;
		case SearchPackage.WEBSEARCH__PATTERN:
			setPattern(PATTERN_EDEFAULT);
			return;
		case SearchPackage.WEBSEARCH__IMAGE_PATH:
			setImagePath(IMAGE_PATH_EDEFAULT);
			return;
		case SearchPackage.WEBSEARCH__CONTRIBUTOR:
			setContributor(CONTRIBUTOR_EDEFAULT);
			return;
		case SearchPackage.WEBSEARCH__IMAGE:
			setImage(IMAGE_EDEFAULT);
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
		case SearchPackage.WEBSEARCH__ID:
			return ID_EDEFAULT == null ? this.id != null : !ID_EDEFAULT.equals(this.id);
		case SearchPackage.WEBSEARCH__NAME:
			return NAME_EDEFAULT == null ? this.name != null : !NAME_EDEFAULT.equals(this.name);
		case SearchPackage.WEBSEARCH__PATTERN:
			return PATTERN_EDEFAULT == null ? this.pattern != null : !PATTERN_EDEFAULT.equals(this.pattern);
		case SearchPackage.WEBSEARCH__IMAGE_PATH:
			return IMAGE_PATH_EDEFAULT == null ? this.imagePath != null : !IMAGE_PATH_EDEFAULT.equals(this.imagePath);
		case SearchPackage.WEBSEARCH__CONTRIBUTOR:
			return CONTRIBUTOR_EDEFAULT == null ? this.contributor != null : !CONTRIBUTOR_EDEFAULT.equals(this.contributor);
		case SearchPackage.WEBSEARCH__IMAGE:
			return IMAGE_EDEFAULT == null ? this.image != null : !IMAGE_EDEFAULT.equals(this.image);
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
		result.append(this.id);
		result.append(", name: ");
		result.append(this.name);
		result.append(", pattern: ");
		result.append(this.pattern);
		result.append(", imagePath: ");
		result.append(this.imagePath);
		result.append(", contributor: ");
		result.append(this.contributor);
		result.append(", image: ");
		result.append(this.image);
		result.append(')');
		return result.toString();
	}

} //WebsearchImpl
