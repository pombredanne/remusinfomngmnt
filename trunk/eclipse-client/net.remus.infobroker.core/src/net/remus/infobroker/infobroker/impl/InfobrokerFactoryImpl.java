/**
 * Copyright Tom Seidel 2008
 * All rights reserved
 *
 * $Id$
 */
package net.remus.infobroker.infobroker.impl;

import java.net.URL;

import net.remus.infobroker.infobroker.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class InfobrokerFactoryImpl extends EFactoryImpl implements InfobrokerFactory {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright Tom Seidel 2008\r\nAll rights reserved";

    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static InfobrokerFactory init() {
        try {
            InfobrokerFactory theInfobrokerFactory = (InfobrokerFactory)EPackage.Registry.INSTANCE.getEFactory("http://remus-software.net/infobroker"); 
            if (theInfobrokerFactory != null) {
                return theInfobrokerFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new InfobrokerFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public InfobrokerFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case InfobrokerPackage.VALUE: return createValue();
            case InfobrokerPackage.CREDENTIALS: return createCredentials();
            case InfobrokerPackage.PRIMARY_NODE: return createPrimaryNode();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Value createValue() {
        ValueImpl value = new ValueImpl();
        return value;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Credentials createCredentials() {
        CredentialsImpl credentials = new CredentialsImpl();
        return credentials;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PrimaryNode createPrimaryNode() {
        PrimaryNodeImpl primaryNode = new PrimaryNodeImpl();
        return primaryNode;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public InfobrokerPackage getInfobrokerPackage() {
        return (InfobrokerPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static InfobrokerPackage getPackage() {
        return InfobrokerPackage.eINSTANCE;
    }

} //InfobrokerFactoryImpl
