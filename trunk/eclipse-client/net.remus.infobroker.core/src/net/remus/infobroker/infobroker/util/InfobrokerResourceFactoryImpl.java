/**
 * Copyright Tom Seidel 2008
 * All rights reserved
 *
 * $Id$
 */
package net.remus.infobroker.infobroker.util;

import org.eclipse.emf.common.util.URI;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;

/**
 * <!-- begin-user-doc -->
 * The <b>Resource Factory</b> associated with the package.
 * <!-- end-user-doc -->
 * @see net.remus.infobroker.infobroker.util.InfobrokerResourceImpl
 * @generated
 */
public class InfobrokerResourceFactoryImpl extends ResourceFactoryImpl {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright Tom Seidel 2008\r\nAll rights reserved";

    /**
     * Creates an instance of the resource factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public InfobrokerResourceFactoryImpl() {
        super();
    }

    /**
     * Creates an instance of the resource.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Resource createResource(URI uri) {
        Resource result = new InfobrokerResourceImpl(uri);
        return result;
    }

} //InfobrokerResourceFactoryImpl
