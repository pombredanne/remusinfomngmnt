/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.remus.oda;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Result Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.oda.ResultSet#getRows <em>Rows</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.oda.OdaPackage#getResultSet()
 * @model
 * @generated
 */
public interface ResultSet extends EObject {
	/**
	 * Returns the value of the '<em><b>Rows</b></em>' containment reference list.
	 * The list contents are of type {@link org.remus.oda.Row}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rows</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rows</em>' containment reference list.
	 * @see org.remus.oda.OdaPackage#getResultSet_Rows()
	 * @model containment="true"
	 * @generated
	 */
	EList<Row> getRows();

} // ResultSet
