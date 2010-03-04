package org.remus.infomngmnt.model.remote;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.ChangeSetItem;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.core.remote.RemoteException;
import org.remus.infomngmnt.core.remote.services.IRepositoryExtensionService;
import org.remus.infomngmnt.core.remote.services.IRepositoryService;
import org.remus.infomngmnt.core.remote.sync.ChangeSetException;
import org.remus.infomngmnt.core.remote.sync.ChangeSetExecutor;
import org.remus.infomngmnt.core.services.IApplicationModel;

public interface IChangeHandler {

	ChangeSet createChangeSet(final Category cat, final IProgressMonitor monitor)
			throws CoreException;

	ChangeSet createChangeSet(final Category localContainer,
			final List<RemoteContainer> remoteContainers, final RemoteRepository localRepository,
			final int mode, final IProgressMonitor monitor) throws ChangeSetException;

	/**
	 * This is a recursiv method which gets the children from the repository
	 * implementation and converts the result into data-objects which can be
	 * appended to the local data- structure. At the same time a
	 * {@link SynchronizationMetadata} is created which holds important data of
	 * the repository.
	 * 
	 * @param changeSetItem
	 * @param remoteObject2
	 * @param remoteRepository
	 * @param parentCategory
	 * @param mode
	 * @throws RemoteException
	 */
	void fillRemoteContainer(final ChangeSetItem changeSetItem, final RemoteObject remoteObject2,
			final RemoteRepository remoteRepository, final Category parentCategory,
			final IProgressMonitor monitor) throws RemoteException;

	DiffModel createDiffModel(final ChangeSetItem changeSetItem, final Category targetCategory)
			throws ChangeSetException;

	DiffModel createDiffModel(final ChangeSetItem changeSetItem, final Category targetCategory,
			final boolean replaceAllLocal) throws ChangeSetException;

	void prepareSyncActions(final EList<DiffElement> diffModel, final ChangeSetItem item,
			final Category targetCategory);

	void replaceAllLocal(final ChangeSetItem changeSetItem2);

	void committAllLocal(final ChangeSetItem changeSetItem2);

	void updateFromRemote(final ChangeSetItem changeSet);

	/**
	 * Creates a changeset for a single information-unit. The
	 * {@link ChangeSetItem} has special prepared containers, which are copies
	 * of local categories but only with a copy of the requested item. This is
	 * needed for building a {@link DiffModel} and synchronizing the
	 * {@link ChangeSet} with the {@link ChangeSetExecutor}.
	 * 
	 * @param item
	 *            the local item to synchronize
	 * @param monitor
	 *            a progressmonitor
	 * @return
	 * @throws ChangeSetException
	 */
	ChangeSet syncSingleInformationUnit(final InformationUnitListItem item,
			final IProgressMonitor monitor) throws ChangeSetException;

	void setRepositoryExtensionService(IRepositoryExtensionService service);

	void setRepositoryService(IRepositoryService service);

	void setApplicationService(final IApplicationModel service);
}