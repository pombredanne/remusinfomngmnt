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
package org.remus.infomngmnt;

import org.remus.infomngmnt.core.remote.IRepository;



/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Remote Repository</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.remus.infomngmnt.InfomngmntPackage#getRemoteRepository()
 * @model
 * @generated
 */
public interface RemoteRepository extends RemoteContainer {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.remus.infomngmnt.IRepository"
	 * @generated
	 */
	IRepository getRepositoryImplementation();

} // RemoteRepository
