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

package org.remus.infomngmnt.core.remote;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.swt.graphics.Image;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.SynchronizableObject;

/**
 * This interface defines a repository, which can synchronize between the local
 * and the remote data, based on an entry point. The idea is to make integration
 * of different connectors as easy as possible.
 * 
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface IRepository {

	/**
	 * Returns an image (typically 16x16) which represents a specific repository
	 * through the whole application.
	 * 
	 * @return the repository specific image.
	 * @since 1.0
	 */
	Image getImage();

	/**
	 * Returns the label of the repository.
	 * 
	 * @return
	 */
	String getLabel();

	String getId();

	void login(ILoginCallBack callback, IProgressMonitor monitor);

	String getRepositoryUrl();

	RemoteObject[] getChildren(IProgressMonitor monitor, RemoteContainer container,
			boolean showOnlyContainers) throws RemoteException;

	IStatus validate();

	void setCredentialProvider(ICredentialProvider provider);

	ICredentialProvider getCredentialProvider();

	void reset();

	/**
	 * Returns the scheduling rule for calling
	 * {@link #getChildren(IProgressMonitor, RemoteContainer, boolean)} or other
	 * methods which access directy to the remote repository.
	 * 
	 * @return the rule the appropriate job is scheduled.
	 */
	ISchedulingRule getRule();

	String getLocalRepositoryId();

	void setLocalRepositoryId(final String localRepositoryId);

	String getTypeIdByObject(RemoteObject remoteObject);

	InformationUnit getPrefetchedInformationUnit(RemoteObject remoteObject);

	InformationUnit getFullObject(InformationUnitListItem informationUnitListItem,
			IProgressMonitor monitor) throws RemoteException;

	/**
	 * @return
	 */
	IFile[] getBinaryReferences();

	/**
	 * Commits a {@link SynchronizableObject}. This can be a {@link Category} or
	 * a {@link InformationUnitListItem}. When committing this method has to
	 * return a new hash, which will be assigned to the
	 * {@link SynchronizableObject}.
	 * 
	 * @param item2commit
	 * @param monitor
	 * @throws RemoteException
	 *             if something went wrong.
	 * @throws OperationNotSupportedException
	 *             if committing is not supported.
	 * 
	 */
	String commit(SynchronizableObject item2commit, IProgressMonitor monitor)
			throws RemoteException;

	RemoteObject addToRepository(SynchronizableObject item, IProgressMonitor monitor)
			throws RemoteException;

	void deleteFromRepository(SynchronizableObject item, IProgressMonitor monitor)
			throws RemoteException;

	RemoteObject getRemoteObjectBySynchronizableObject(final SynchronizableObject object,
			IProgressMonitor monitor) throws RemoteException;

	/**
	 * Returns a definition which properties within an information unit are
	 * synchronized with the repository. In the most cases the repository cannot
	 * synchronize all properties of the information unit. Because of that we
	 * have to provide a collection which properties are updated
	 * 
	 * @param type
	 *            the information type
	 * @return a collection of properties which can be synchronized.
	 */
	IChangeSetDefinition getChangeSetDefinitionForType(String type);

	boolean hasBinaryReferences();

	boolean multiple();

}
