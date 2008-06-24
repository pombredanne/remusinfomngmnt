/**
 * Copyright Tom Seidel 2008
 * All rights reserved
 *
 * $Id$
 */
package net.remus.infobroker.infobroker;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.remus.infobroker.infobroker.Node#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.remus.infobroker.infobroker.InfobrokerPackage#getNode()
 * @model abstract="true"
 * @generated
 */
public interface Node extends Base {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright Tom Seidel 2008\r\nAll rights reserved";

    /**
     * Returns the value of the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Type</em>' attribute.
     * @see #setType(String)
     * @see net.remus.infobroker.infobroker.InfobrokerPackage#getNode_Type()
     * @model
     * @generated
     */
    String getType();

    /**
     * Sets the value of the '{@link net.remus.infobroker.infobroker.Node#getType <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Type</em>' attribute.
     * @see #getType()
     * @generated
     */
    void setType(String value);

} // Node
