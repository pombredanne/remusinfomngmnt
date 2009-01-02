/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.core.model;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.RepositoryCollection;
import org.remus.infomngmnt.core.services.IRepositoryService;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RepositoryService implements IRepositoryService {
	
	public static final String REPOSITORY_PATH = "repositories/repositories.xml"; //$NON-NLS-1$
	
	private RepositoryCollection repositories;

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.services.IRepositoryService#getRepositories()
	 */
	public RepositoryCollection getRepositories() {
		if (this.repositories == null) {
			IPath append = InfomngmntEditPlugin.getPlugin().getStateLocation().append(REPOSITORY_PATH);
			this.repositories = EditingUtil.getInstance().getObjectFromFileUri(
					URI.createFileURI(append.toOSString()), InfomngmntPackage.Literals.REPOSITORY_COLLECTION,false);
		}
		return this.repositories;
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.services.IRepositoryService#getRepositoryById(java.lang.String)
	 */
	public RemoteRepository getRepositoryById(final String id) {
		EList<RemoteRepository> list = getRepositories().getRepositories();
		for (RemoteRepository remoteRepository : list) {
			if (remoteRepository.getId().equals(id)) {
				return remoteRepository;
			}
		}
		return null;
	}

}
