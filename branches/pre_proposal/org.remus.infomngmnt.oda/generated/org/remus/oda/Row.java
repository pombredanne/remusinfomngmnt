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
 * A representation of the model object '<em><b>Row</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.oda.Row#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.oda.OdaPackage#getRow()
 * @model
 * @generated
 */
public interface Row extends EObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Object}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute list.
	 * @see org.remus.oda.OdaPackage#getRow_Value()
	 * @model unique="false"
	 * @generated
	 */
	EList<Object> getValue();

} // Row
