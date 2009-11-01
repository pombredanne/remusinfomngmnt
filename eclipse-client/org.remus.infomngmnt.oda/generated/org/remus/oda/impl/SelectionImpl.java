/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.remus.oda.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.remus.oda.OdaPackage;
import org.remus.oda.Selection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Selection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.oda.impl.SelectionImpl#getPath <em>Path</em>}</li>
 *   <li>{@link org.remus.oda.impl.SelectionImpl#isCategory <em>Category</em>}</li>
 *   <li>{@link org.remus.oda.impl.SelectionImpl#getElementSelector <em>Element Selector</em>}</li>
 *   <li>{@link org.remus.oda.impl.SelectionImpl#getInfoTypeId <em>Info Type Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SelectionImpl extends EObjectImpl implements Selection {
	/**
	 * The default value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected static final String PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected String path = PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #isCategory() <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCategory()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CATEGORY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCategory() <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCategory()
	 * @generated
	 * @ordered
	 */
	protected boolean category = CATEGORY_EDEFAULT;

	/**
	 * The default value of the '{@link #getElementSelector() <em>Element Selector</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementSelector()
	 * @generated
	 * @ordered
	 */
	protected static final String ELEMENT_SELECTOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getElementSelector() <em>Element Selector</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementSelector()
	 * @generated
	 * @ordered
	 */
	protected String elementSelector = ELEMENT_SELECTOR_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SelectionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OdaPackage.Literals.SELECTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPath() {
		return path;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPath(String newPath) {
		String oldPath = path;
		path = newPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OdaPackage.SELECTION__PATH, oldPath, path));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isCategory() {
		return category;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCategory(boolean newCategory) {
		boolean oldCategory = category;
		category = newCategory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OdaPackage.SELECTION__CATEGORY, oldCategory, category));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getElementSelector() {
		return elementSelector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setElementSelector(String newElementSelector) {
		String oldElementSelector = elementSelector;
		elementSelector = newElementSelector;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OdaPackage.SELECTION__ELEMENT_SELECTOR, oldElementSelector, elementSelector));
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
			eNotify(new ENotificationImpl(this, Notification.SET, OdaPackage.SELECTION__INFO_TYPE_ID, oldInfoTypeId, infoTypeId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OdaPackage.SELECTION__PATH:
				return getPath();
			case OdaPackage.SELECTION__CATEGORY:
				return isCategory();
			case OdaPackage.SELECTION__ELEMENT_SELECTOR:
				return getElementSelector();
			case OdaPackage.SELECTION__INFO_TYPE_ID:
				return getInfoTypeId();
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
			case OdaPackage.SELECTION__PATH:
				setPath((String)newValue);
				return;
			case OdaPackage.SELECTION__CATEGORY:
				setCategory((Boolean)newValue);
				return;
			case OdaPackage.SELECTION__ELEMENT_SELECTOR:
				setElementSelector((String)newValue);
				return;
			case OdaPackage.SELECTION__INFO_TYPE_ID:
				setInfoTypeId((String)newValue);
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
			case OdaPackage.SELECTION__PATH:
				setPath(PATH_EDEFAULT);
				return;
			case OdaPackage.SELECTION__CATEGORY:
				setCategory(CATEGORY_EDEFAULT);
				return;
			case OdaPackage.SELECTION__ELEMENT_SELECTOR:
				setElementSelector(ELEMENT_SELECTOR_EDEFAULT);
				return;
			case OdaPackage.SELECTION__INFO_TYPE_ID:
				setInfoTypeId(INFO_TYPE_ID_EDEFAULT);
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
			case OdaPackage.SELECTION__PATH:
				return PATH_EDEFAULT == null ? path != null : !PATH_EDEFAULT.equals(path);
			case OdaPackage.SELECTION__CATEGORY:
				return category != CATEGORY_EDEFAULT;
			case OdaPackage.SELECTION__ELEMENT_SELECTOR:
				return ELEMENT_SELECTOR_EDEFAULT == null ? elementSelector != null : !ELEMENT_SELECTOR_EDEFAULT.equals(elementSelector);
			case OdaPackage.SELECTION__INFO_TYPE_ID:
				return INFO_TYPE_ID_EDEFAULT == null ? infoTypeId != null : !INFO_TYPE_ID_EDEFAULT.equals(infoTypeId);
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
		result.append(" (path: ");
		result.append(path);
		result.append(", category: ");
		result.append(category);
		result.append(", elementSelector: ");
		result.append(elementSelector);
		result.append(", infoTypeId: ");
		result.append(infoTypeId);
		result.append(')');
		return result.toString();
	}

} //SelectionImpl
