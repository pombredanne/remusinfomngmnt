/**
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * 
 * Contributors:
 *      Tom Seidel - initial API and implementation
 * 
 *
 * $Id$
 */
package org.remus.infomngmnt.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.core.remote.IRepository;
import org.remus.infomngmnt.core.services.IRepositoryExtensionService;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Remote Repository</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class RemoteRepositoryImpl extends RemoteContainerImpl implements RemoteRepository {
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	private IRepository implementation;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RemoteRepositoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfomngmntPackage.Literals.REMOTE_REPOSITORY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public IRepository getRepositoryImplementation() {
		if (getRepositoryTypeId() != null && this.implementation == null) {
			try {
				this.implementation = InfomngmntEditPlugin.getPlugin().getService(IRepositoryExtensionService.class).getItemByRepository(this);
			} catch (Exception e) {
				throw new IllegalStateException(NLS.bind("Repsoitory connector with id {0} was not found", getRepositoryTypeId()));
			}
			if (this.implementation == null) {
				throw new IllegalStateException(NLS.bind("Repsoitory connector with id {0} was not found", getRepositoryTypeId()));
			}
		}
		return this.implementation;
	}

} //RemoteRepositoryImpl
