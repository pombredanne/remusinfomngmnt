/**
 * Copyright Tom Seidel 2008
 * All rights reserved
 *
 * $Id$
 */
package net.remus.infobroker.infobroker;

import java.util.Date;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.remus.infobroker.infobroker.Value#getString <em>String</em>}</li>
 *   <li>{@link net.remus.infobroker.infobroker.Value#getBoolean <em>Boolean</em>}</li>
 *   <li>{@link net.remus.infobroker.infobroker.Value#getDate <em>Date</em>}</li>
 *   <li>{@link net.remus.infobroker.infobroker.Value#getLong <em>Long</em>}</li>
 *   <li>{@link net.remus.infobroker.infobroker.Value#getInt <em>Int</em>}</li>
 *   <li>{@link net.remus.infobroker.infobroker.Value#getDouble <em>Double</em>}</li>
 *   <li>{@link net.remus.infobroker.infobroker.Value#getReferencedChildNodes <em>Referenced Child Nodes</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.remus.infobroker.infobroker.InfobrokerPackage#getValue()
 * @model
 * @generated
 */
public interface Value extends Node {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright Tom Seidel 2008\r\nAll rights reserved";

    /**
     * Returns the value of the '<em><b>String</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>String</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>String</em>' attribute.
     * @see #setString(String)
     * @see net.remus.infobroker.infobroker.InfobrokerPackage#getValue_String()
     * @model
     * @generated
     */
    String getString();

    /**
     * Sets the value of the '{@link net.remus.infobroker.infobroker.Value#getString <em>String</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>String</em>' attribute.
     * @see #getString()
     * @generated
     */
    void setString(String value);

    /**
     * Returns the value of the '<em><b>Boolean</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Boolean</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Boolean</em>' attribute.
     * @see #setBoolean(String)
     * @see net.remus.infobroker.infobroker.InfobrokerPackage#getValue_Boolean()
     * @model
     * @generated
     */
    String getBoolean();

    /**
     * Sets the value of the '{@link net.remus.infobroker.infobroker.Value#getBoolean <em>Boolean</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Boolean</em>' attribute.
     * @see #getBoolean()
     * @generated
     */
    void setBoolean(String value);

    /**
     * Returns the value of the '<em><b>Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Date</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Date</em>' attribute.
     * @see #setDate(Date)
     * @see net.remus.infobroker.infobroker.InfobrokerPackage#getValue_Date()
     * @model
     * @generated
     */
    Date getDate();

    /**
     * Sets the value of the '{@link net.remus.infobroker.infobroker.Value#getDate <em>Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Date</em>' attribute.
     * @see #getDate()
     * @generated
     */
    void setDate(Date value);

    /**
     * Returns the value of the '<em><b>Long</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Long</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Long</em>' attribute.
     * @see #setLong(long)
     * @see net.remus.infobroker.infobroker.InfobrokerPackage#getValue_Long()
     * @model
     * @generated
     */
    long getLong();

    /**
     * Sets the value of the '{@link net.remus.infobroker.infobroker.Value#getLong <em>Long</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Long</em>' attribute.
     * @see #getLong()
     * @generated
     */
    void setLong(long value);

    /**
     * Returns the value of the '<em><b>Int</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Int</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Int</em>' attribute.
     * @see #setInt(int)
     * @see net.remus.infobroker.infobroker.InfobrokerPackage#getValue_Int()
     * @model
     * @generated
     */
    int getInt();

    /**
     * Sets the value of the '{@link net.remus.infobroker.infobroker.Value#getInt <em>Int</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Int</em>' attribute.
     * @see #getInt()
     * @generated
     */
    void setInt(int value);

    /**
     * Returns the value of the '<em><b>Double</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Double</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Double</em>' attribute.
     * @see #setDouble(double)
     * @see net.remus.infobroker.infobroker.InfobrokerPackage#getValue_Double()
     * @model
     * @generated
     */
    double getDouble();

    /**
     * Sets the value of the '{@link net.remus.infobroker.infobroker.Value#getDouble <em>Double</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Double</em>' attribute.
     * @see #getDouble()
     * @generated
     */
    void setDouble(double value);

    /**
     * Returns the value of the '<em><b>Referenced Child Nodes</b></em>' reference list.
     * The list contents are of type {@link net.remus.infobroker.infobroker.Node}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Referenced Child Nodes</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Referenced Child Nodes</em>' reference list.
     * @see net.remus.infobroker.infobroker.InfobrokerPackage#getValue_ReferencedChildNodes()
     * @model
     * @generated
     */
    EList<Node> getReferencedChildNodes();

} // Value
