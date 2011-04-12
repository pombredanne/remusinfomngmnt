/*******************************************************************************
 * Copyright (c) 2009 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.eclipsemarketplace.connector;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.ecf.core.ContainerCreateException;
import org.eclipse.ecf.core.ContainerFactory;
import org.eclipse.ecf.core.IContainer;
import org.eclipse.ecf.filetransfer.IRetrieveFileTransferContainerAdapter;
import org.eclipse.osgi.util.NLS;
import org.eclipse.remus.InfomngmntFactory;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.RemoteContainer;
import org.eclipse.remus.RemoteObject;
import org.eclipse.remus.RemoteRepository;
import org.eclipse.remus.SynchronizableObject;
import org.eclipse.remus.common.core.util.ResourceUtil;
import org.eclipse.remus.common.io.proxy.Proxy;
import org.eclipse.remus.common.io.proxy.ProxyUtil;
import org.eclipse.remus.common.io.transfer.DownloadFileJob;
import org.eclipse.remus.core.model.InformationStructureEdit;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.core.remote.AbstractExtensionRepository;
import org.eclipse.remus.core.remote.RemoteException;
import org.eclipse.remus.model.remote.ILoginCallBack;
import org.eclipse.remus.model.remote.IRepository;
import org.eclipse.remus.util.StatusCreator;

import org.remus.infomngmnt.eclipsemarketplace.MarketPlaceActivator;
import org.remus.infomngmnt.eclipsemarketplace.api.Constants;
import org.remus.infomngmnt.eclipsemarketplace.api.MarketPlace;
import org.remus.infomngmnt.eclipsemarketplace.api.MarketPlaceException;
import org.remus.infomngmnt.eclipsemarketplace.api.beans.Category;
import org.remus.infomngmnt.eclipsemarketplace.api.beans.CategoryEntry;
import org.remus.infomngmnt.eclipsemarketplace.api.beans.Market;
import org.remus.infomngmnt.eclipsemarketplace.api.beans.MarketPlaceElement;
import org.remus.infomngmnt.eclipsemarketplace.api.beans.MarketPlaceRoot;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MarketplaceConnector extends AbstractExtensionRepository implements IRepository {

	private IContainer container;

	private IRetrieveFileTransferContainerAdapter fileReceiveAdapter;

	private MarketPlace api;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.IRepository#addToRepository(org.remus
	 * .infomngmnt.SynchronizableObject,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public RemoteObject addToRepository(final SynchronizableObject item,
			final IProgressMonitor monitor) throws RemoteException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.IRepository#commit(org.remus.infomngmnt
	 * .SynchronizableObject, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public RemoteObject commit(final SynchronizableObject item2commit,
			final IProgressMonitor monitor) throws RemoteException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.IRepository#deleteFromRepository(org
	 * .remus.infomngmnt.SynchronizableObject,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void deleteFromRepository(final SynchronizableObject item, final IProgressMonitor monitor)
			throws RemoteException {
		throw new RemoteException(StatusCreator
				.newStatus(Messages.MarketplaceConnector_DeletionNotSupported));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.IRepository#getChildren(org.eclipse.
	 * core.runtime.IProgressMonitor, org.remus.infomngmnt.RemoteContainer,
	 * boolean)
	 */
	public RemoteObject[] getChildren(final IProgressMonitor monitor,
			final RemoteContainer container, final boolean showOnlyContainers)
			throws RemoteException {
		if (container instanceof RemoteRepository) {
			try {
				MarketPlaceRoot root = getApi().getRoot();
				return buildMarkets(root);
			} catch (Exception e) {
				throw new RemoteException(StatusCreator.newStatus(Messages.MarketplaceConnector_ErrorMarkets, e));
			}
		} else if (container.getWrappedObject() instanceof Market) {
			return buildCategories((Market) container.getWrappedObject());
		} else if (container.getWrappedObject() instanceof Category & !showOnlyContainers) {
			try {
				return buildElements((Category) container.getWrappedObject(), monitor);
			} catch (Exception e) {
				throw new RemoteException(StatusCreator.newStatus(Messages.MarketplaceConnector_ErrorElements, e));
			}
		}
		return new RemoteObject[0];
	}

	private RemoteObject[] buildElements(final Category wrappedObject,
			final IProgressMonitor monitor) throws MarketPlaceException, IOException {
		CategoryEntry[] entriesForCategory = getApi().getEntriesForCategory(wrappedObject.getId());
		List<RemoteObject> returnValue = new ArrayList<RemoteObject>();
		for (CategoryEntry categoryEntry : entriesForCategory) {
			returnValue.add(buildSingleElement(categoryEntry.getId(), monitor));
		}
		return returnValue.toArray(new RemoteObject[returnValue.size()]);
	}

	private RemoteObject buildSingleElement(final int id, final IProgressMonitor monitor)
			throws MarketPlaceException, IOException {
		MarketPlaceElement marketPlaceElementById = getApi().getMarketPlaceElementById(id);
		monitor.subTask(NLS.bind(Messages.MarketplaceConnector_ContactingMarketForElement,
				marketPlaceElementById.getTitle()));
		RemoteObject remoteElement = InfomngmntFactory.eINSTANCE.createRemoteObject();
		remoteElement.setId(String.valueOf(marketPlaceElementById.getId()));
		remoteElement.setHash(marketPlaceElementById.getChanged().getTime() + "-" //$NON-NLS-1$
				+ marketPlaceElementById.getFavorited());
		remoteElement.setName(marketPlaceElementById.getTitle());
		remoteElement.setWrappedObject(marketPlaceElementById);
		remoteElement.setUrl(getRepositoryUrl() + "/node/" + remoteElement.getId()); //$NON-NLS-1$
		return remoteElement;
	}

	@Override
	public String getRepositoryUrl() {
		return Constants.API_ENDPOINT;
	}

	private RemoteObject[] buildCategories(final Market wrappedObject) {
		List<Category> categories = wrappedObject.getCategories();
		List<RemoteContainer> returnValue = new ArrayList<RemoteContainer>();
		for (Category category : categories) {
			returnValue.add(buildSingleCategory(category));
		}
		return returnValue.toArray(new RemoteContainer[returnValue.size()]);
	}

	private RemoteContainer buildSingleCategory(final Category category) {
		RemoteContainer remoteCategory = InfomngmntFactory.eINSTANCE.createRemoteContainer();
		remoteCategory.setId(String.valueOf(category.getId()));
		remoteCategory.setName(category.getName());
		remoteCategory.setUrl(getRepositoryUrl() + "/category/" + category.getId()); //$NON-NLS-1$
		remoteCategory.setWrappedObject(category);
		return remoteCategory;
	}

	private RemoteContainer[] buildMarkets(final MarketPlaceRoot root) {
		List<Market> markets = root.getMarkets();
		List<RemoteContainer> returnValue = new ArrayList<RemoteContainer>();
		for (Market market : markets) {
			returnValue.add(buildSingleMarket(market));
		}
		return returnValue.toArray(new RemoteContainer[returnValue.size()]);
	}

	private RemoteContainer buildSingleMarket(final Market market) {
		RemoteContainer remoteMarket = InfomngmntFactory.eINSTANCE.createRemoteContainer();
		remoteMarket.setId(String.valueOf(market.getId()));
		remoteMarket.setName(market.getName());
		remoteMarket.setUrl(getRepositoryUrl() + "/market/" + market.getId()); //$NON-NLS-1$
		remoteMarket.setWrappedObject(market);
		return remoteMarket;
	}

	@Override
	public InformationUnit getPrefetchedInformationUnit(final RemoteObject remoteObject) {
		if (remoteObject.getWrappedObject() instanceof MarketPlaceElement) {
			MarketPlaceElement wrappedObject = (MarketPlaceElement) remoteObject.getWrappedObject();
			InformationStructureEdit edit = InformationStructureEdit
					.newSession(MarketPlaceActivator.TYPE_ID);
			InformationUnit returnValue = edit.newInformationUnit();
			edit
					.setValue(returnValue, MarketPlaceActivator.OWNER_NODE_ID, wrappedObject
							.getOwner());
			edit.setValue(returnValue, MarketPlaceActivator.DESCRIPTION_NODE_ID, wrappedObject
					.getBody());
			edit.setValue(returnValue, MarketPlaceActivator.CHANGED_NODE_ID, wrappedObject
					.getChanged());
			edit.setValue(returnValue, MarketPlaceActivator.CREATED_NODE_ID, wrappedObject
					.getCreated());
			edit.setValue(returnValue, MarketPlaceActivator.COMPANY_NODE_ID, wrappedObject
					.getCompany());
			edit.setValue(returnValue, MarketPlaceActivator.STATUS_NODE_ID, wrappedObject
					.getStatus());
			edit.setValue(returnValue, MarketPlaceActivator.ECLIPSEVERSION_NODE_ID, wrappedObject
					.getEclipseversion());
			edit.setValue(returnValue, MarketPlaceActivator.FAVORITED_NODE_ID, wrappedObject
					.getFavorited());
			edit.setValue(returnValue, MarketPlaceActivator.FOUNDATION_NODE_ID, wrappedObject
					.isFoundationMember());
			edit.setValue(returnValue, MarketPlaceActivator.SUPPORTURL_NODE_ID, wrappedObject
					.getSupportUrl());
			edit.setValue(returnValue, MarketPlaceActivator.UPDATEUR_NODE_ID, wrappedObject
					.getUpdateUrl());
			edit.setValue(returnValue, MarketPlaceActivator.VERSION_NODE_ID, wrappedObject
					.getVersion());
			edit
					.setValue(returnValue, MarketPlaceActivator.WEBSITE_NODE_ID, wrappedObject
							.getUrl());
			edit.setValue(returnValue, MarketPlaceActivator.LICENSE_NODE_ID, wrappedObject
					.getLicense());
			edit.setValue(returnValue, MarketPlaceActivator.IMAGEURL_NODE_ID, wrappedObject
					.getImage());
			edit.setValue(returnValue, MarketPlaceActivator.INTERNAL_ID_NODE_ID, wrappedObject
					.getId());
			return returnValue;

		}
		return super.getPrefetchedInformationUnit(remoteObject);
	}

	@Override
	public IFile getBinaryReferences(final InformationUnitListItem syncObject,
			final InformationUnit localInfoFragment, final IProgressMonitor monitor)
			throws RemoteException {
		if (MarketPlaceActivator.TYPE_ID.equals(localInfoFragment.getType())) {
			InformationStructureRead read = InformationStructureRead.newSession(localInfoFragment);
			String imageUrl = (String) read.getValueByNodeId(MarketPlaceActivator.IMAGEURL_NODE_ID);
			IFile tmpFile;
			if (imageUrl != null) {
				String fileExtension = new Path(imageUrl).getFileExtension();
				if (fileExtension == null || fileExtension.indexOf('?') != -1) {
					fileExtension = ""; //$NON-NLS-1$
					tmpFile = ResourceUtil.createTempFile();
				} else {
					tmpFile = ResourceUtil.createTempFile(fileExtension);
				}

				try {
					DownloadFileJob downloadFileJob = new DownloadFileJob(new URL(imageUrl),
							tmpFile, getFileReceiveAdapter());
					IStatus run = downloadFileJob.run(monitor);
					if (run.isOK()) {
						return tmpFile;
					}
				} catch (MalformedURLException e) {
					// do nothing.
				}

			}

		}
		return super.getBinaryReferences(syncObject, localInfoFragment, monitor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.IRepository#getFullObject(org.remus.
	 * infomngmnt.InformationUnitListItem,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public InformationUnit getFullObject(final InformationUnitListItem informationUnitListItem,
			final IProgressMonitor monitor) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasBinaryReferences() {
		return true;
	}

	@Override
	public boolean multiple() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.core.remote.IRepository#
	 * getRemoteObjectBySynchronizableObject
	 * (org.remus.infomngmnt.SynchronizableObject,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public RemoteObject getRemoteObjectBySynchronizableObject(final SynchronizableObject object,
			final IProgressMonitor monitor) throws RemoteException {
		String url = object.getSynchronizationMetaData().getUrl();
		if (url != null) {
			if (url.equals(getRepositoryUrl())) {
				return getRepositoryById(object.getSynchronizationMetaData().getRepositoryId());
			} else {
				try {
					Path path = new Path(url);
					String segment = path.segment(path.segmentCount() - 2);
					// TODO : Better handling of the strings
					if (segment.equals("market")) { //$NON-NLS-1$
						MarketPlaceRoot root = getApi().getRoot();
						List<Market> markets = root.getMarkets();
						for (Market market : markets) {
							if (String.valueOf(market.getId()).equals(path.lastSegment())) {
								return buildSingleMarket(market);
							}
						}
					}
					if (segment.equals("category")) { //$NON-NLS-1$
						MarketPlaceRoot root = getApi().getRoot();
						List<Market> markets = root.getMarkets();
						for (Market market : markets) {
							List<Category> categories = market.getCategories();
							for (Category category : categories) {
								if (String.valueOf(category.getId()).equals(path.lastSegment())) {
									return buildSingleCategory(category);
								}
							}
						}
					}
					if (segment.equals("node")) { //$NON-NLS-1$
						return buildSingleElement(Integer.valueOf(path.lastSegment()), monitor);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.IRepository#getTypeIdByObject(org.remus
	 * .infomngmnt.RemoteObject)
	 */
	public String getTypeIdByObject(final RemoteObject remoteObject) {
		return MarketPlaceActivator.TYPE_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.IRepository#login(org.remus.infomngmnt
	 * .core.remote.ILoginCallBack, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void login(final ILoginCallBack callback, final IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.core.remote.IRepository#reset()
	 */
	public void reset() {
		// TODO Auto-generated method stub

	}

	private synchronized MarketPlace getApi() {
		if (this.api == null) {
			this.api = new MarketPlace();
			Proxy proxyByUrl = ProxyUtil.getProxyByUrl(Constants.API_ENDPOINT);
			if (proxyByUrl != null) {
				this.api.setProxyConfiguration(proxyByUrl.getAddress().getHostName(), proxyByUrl
						.getAddress().getPort());
				this.api.setProxyAuthenticationConfiguration(proxyByUrl.getUsername(), proxyByUrl
						.getPassword());
			}
		}
		return this.api;
	}

	protected IRetrieveFileTransferContainerAdapter getFileReceiveAdapter() {
		if (this.container == null) {
			try {
				this.container = ContainerFactory.getDefault().createContainer();
			} catch (final ContainerCreateException e) {
				throw new RuntimeException(Messages.MarketplaceConnector_ErrorInitECF, e);
			}
		}
		this.fileReceiveAdapter = (IRetrieveFileTransferContainerAdapter) this.container
				.getAdapter(IRetrieveFileTransferContainerAdapter.class);
		return this.fileReceiveAdapter;
	}

}
