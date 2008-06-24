/**
 * Copyright Tom Seidel 2008
 * All rights reserved
 *
 * $Id$
 */
package net.remus.infobroker.infobroker.impl;

import java.util.Collection;

import net.remus.infobroker.infobroker.InfobrokerPackage;
import net.remus.infobroker.infobroker.Node;
import net.remus.infobroker.infobroker.PrimaryNode;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Primary Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.remus.infobroker.infobroker.impl.PrimaryNodeImpl#getContainedChildNodes <em>Contained Child Nodes</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PrimaryNodeImpl extends ValueImpl implements PrimaryNode {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright Tom Seidel 2008\r\nAll rights reserved";

    /**
     * The cached value of the '{@link #getContainedChildNodes() <em>Contained Child Nodes</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getContainedChildNodes()
     * @generated
     * @ordered
     */
    protected EList<Node> containedChildNodes;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PrimaryNodeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return InfobrokerPackage.Literals.PRIMARY_NODE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Node> getContainedChildNodes() {
        if (containedChildNodes == null) {
            containedChildNodes = new EObjectContainmentEList<Node>(Node.class, this, InfobrokerPackage.PRIMARY_NODE__CONTAINED_CHILD_NODES);
        }
        return containedChildNodes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case InfobrokerPackage.PRIMARY_NODE__CONTAINED_CHILD_NODES:
                return ((InternalEList<?>)getContainedChildNodes()).basicRemove(otherEnd, msgs);
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
            case InfobrokerPackage.PRIMARY_NODE__CONTAINED_CHILD_NODES:
                return getContainedChildNodes();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case InfobrokerPackage.PRIMARY_NODE__CONTAINED_CHILD_NODES:
                getContainedChildNodes().clear();
                getContainedChildNodes().addAll((Collection<? extends Node>)newValue);
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
            case InfobrokerPackage.PRIMARY_NODE__CONTAINED_CHILD_NODES:
                getContainedChildNodes().clear();
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
            case InfobrokerPackage.PRIMARY_NODE__CONTAINED_CHILD_NODES:
                return containedChildNodes != null && !containedChildNodes.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //PrimaryNodeImpl
