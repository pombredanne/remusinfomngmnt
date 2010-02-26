/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.remus.oda;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Selection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.oda.Selection#getPath <em>Path</em>}</li>
 *   <li>{@link org.remus.oda.Selection#isCategory <em>Category</em>}</li>
 *   <li>{@link org.remus.oda.Selection#getElementSelector <em>Element Selector</em>}</li>
 *   <li>{@link org.remus.oda.Selection#getInfoTypeId <em>Info Type Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.oda.OdaPackage#getSelection()
 * @model
 * @generated
 */
public interface Selection extends EObject {
	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(String)
	 * @see org.remus.oda.OdaPackage#getSelection_Path()
	 * @model required="true"
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link org.remus.oda.Selection#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

	/**
	 * Returns the value of the '<em><b>Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Category</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Category</em>' attribute.
	 * @see #setCategory(boolean)
	 * @see org.remus.oda.OdaPackage#getSelection_Category()
	 * @model required="true"
	 * @generated
	 */
	boolean isCategory();

	/**
	 * Sets the value of the '{@link org.remus.oda.Selection#isCategory <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Category</em>' attribute.
	 * @see #isCategory()
	 * @generated
	 */
	void setCategory(boolean value);

	/**
	 * Returns the value of the '<em><b>Element Selector</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element Selector</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element Selector</em>' attribute.
	 * @see #setElementSelector(String)
	 * @see org.remus.oda.OdaPackage#getSelection_ElementSelector()
	 * @model required="true"
	 * @generated
	 */
	String getElementSelector();

	/**
	 * Sets the value of the '{@link org.remus.oda.Selection#getElementSelector <em>Element Selector</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Element Selector</em>' attribute.
	 * @see #getElementSelector()
	 * @generated
	 */
	void setElementSelector(String value);

	/**
	 * Returns the value of the '<em><b>Info Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Info Type Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Info Type Id</em>' attribute.
	 * @see #setInfoTypeId(String)
	 * @see org.remus.oda.OdaPackage#getSelection_InfoTypeId()
	 * @model required="true"
	 * @generated
	 */
	String getInfoTypeId();

	/**
	 * Sets the value of the '{@link org.remus.oda.Selection#getInfoTypeId <em>Info Type Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Info Type Id</em>' attribute.
	 * @see #getInfoTypeId()
	 * @generated
	 */
	void setInfoTypeId(String value);

} // Selection
