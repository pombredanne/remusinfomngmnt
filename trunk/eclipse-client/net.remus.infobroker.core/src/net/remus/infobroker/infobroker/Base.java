/**
 * Copyright Tom Seidel 2008
 * All rights reserved
 *
 * $Id$
 */
package net.remus.infobroker.infobroker;

import java.util.Date;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Base</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.remus.infobroker.infobroker.Base#getLabel <em>Label</em>}</li>
 *   <li>{@link net.remus.infobroker.infobroker.Base#getDescription <em>Description</em>}</li>
 *   <li>{@link net.remus.infobroker.infobroker.Base#getCreationTime <em>Creation Time</em>}</li>
 *   <li>{@link net.remus.infobroker.infobroker.Base#getTags <em>Tags</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.remus.infobroker.infobroker.InfobrokerPackage#getBase()
 * @model abstract="true"
 * @generated
 */
public interface Base extends EObject, IAdaptable{
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright Tom Seidel 2008\r\nAll rights reserved";

    /**
     * Returns the value of the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Label</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Label</em>' attribute.
     * @see #setLabel(String)
     * @see net.remus.infobroker.infobroker.InfobrokerPackage#getBase_Label()
     * @model
     * @generated
     */
    String getLabel();

    /**
     * Sets the value of the '{@link net.remus.infobroker.infobroker.Base#getLabel <em>Label</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Label</em>' attribute.
     * @see #getLabel()
     * @generated
     */
    void setLabel(String value);

    /**
     * Returns the value of the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Description</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Description</em>' attribute.
     * @see #setDescription(String)
     * @see net.remus.infobroker.infobroker.InfobrokerPackage#getBase_Description()
     * @model
     * @generated
     */
    String getDescription();

    /**
     * Sets the value of the '{@link net.remus.infobroker.infobroker.Base#getDescription <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Description</em>' attribute.
     * @see #getDescription()
     * @generated
     */
    void setDescription(String value);

    /**
     * Returns the value of the '<em><b>Creation Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Creation Time</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Creation Time</em>' attribute.
     * @see #setCreationTime(Date)
     * @see net.remus.infobroker.infobroker.InfobrokerPackage#getBase_CreationTime()
     * @model
     * @generated
     */
    Date getCreationTime();

    /**
     * Sets the value of the '{@link net.remus.infobroker.infobroker.Base#getCreationTime <em>Creation Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Creation Time</em>' attribute.
     * @see #getCreationTime()
     * @generated
     */
    void setCreationTime(Date value);

    /**
     * Returns the value of the '<em><b>Tags</b></em>' attribute list.
     * The list contents are of type {@link java.lang.String}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Tags</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Tags</em>' attribute list.
     * @see net.remus.infobroker.infobroker.InfobrokerPackage#getBase_Tags()
     * @model
     * @generated
     */
    EList<String> getTags();

} // Base
