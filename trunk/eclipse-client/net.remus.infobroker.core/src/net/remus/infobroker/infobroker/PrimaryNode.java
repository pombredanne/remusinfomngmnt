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
 * A representation of the model object '<em><b>Primary Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.remus.infobroker.infobroker.PrimaryNode#getContainedChildNodes <em>Contained Child Nodes</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.remus.infobroker.infobroker.InfobrokerPackage#getPrimaryNode()
 * @model
 * @generated
 */
public interface PrimaryNode extends Value {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright Tom Seidel 2008\r\nAll rights reserved";

    /**
     * Returns the value of the '<em><b>Contained Child Nodes</b></em>' containment reference list.
     * The list contents are of type {@link net.remus.infobroker.infobroker.Node}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Contained Child Nodes</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Contained Child Nodes</em>' containment reference list.
     * @see net.remus.infobroker.infobroker.InfobrokerPackage#getPrimaryNode_ContainedChildNodes()
     * @model containment="true"
     * @generated
     */
    EList<Node> getContainedChildNodes();

} // PrimaryNode
