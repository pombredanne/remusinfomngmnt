package org.remus.infomngmnt.connector.webdav;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import org.remus.infomngmnt.BinaryReference;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.common.core.streams.StreamCloser;
import org.remus.infomngmnt.common.core.util.CollectionFilter;
import org.remus.infomngmnt.common.core.util.CollectionUtils;
import org.remus.infomngmnt.common.core.util.IdFactory;
import org.remus.infomngmnt.common.core.util.ResourceUtil;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.core.remote.AbstractExtensionRepository;
import org.remus.infomngmnt.core.remote.RemoteActivator;
import org.remus.infomngmnt.core.remote.RemoteException;
import org.remus.infomngmnt.core.remote.services.IRepositoryService;
import org.remus.infomngmnt.core.services.IEditingHandler;
import org.remus.infomngmnt.core.services.IInformationTypeHandler;
import org.remus.infomngmnt.model.remote.ILoginCallBack;
import org.remus.infomngmnt.model.remote.IRepository;
import org.remus.infomngmnt.util.InformationUtil;
import org.remus.infomngmnt.util.StatusCreator;

import com.googlecode.sardine.DavResource;
import com.googlecode.sardine.Sardine;
import com.googlecode.sardine.SardineFactory;
import com.googlecode.sardine.util.SardineException;

/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class WebDAVConnector extends AbstractExtensionRepository implements IRepository {

	private Sardine api;

	public static final String FOLDER_PREFIX_CAT = "CAT_"; //$NON-NLS-1$
	public static final String FOLDER_PREFIX_INFO = "INFO_"; //$NON-NLS-1$
	public static final String FOLDER_NAME_BINARIES = ".binaries"; //$NON-NLS-1$
	public static final String FILENAME_CAT = "category.xml"; //$NON-NLS-1$

	private final PropertyChangeListener credentialsMovedListener = new PropertyChangeListener() {
		public void propertyChange(final PropertyChangeEvent evt) {
			reset();
		}
	};

	private IEditingHandler editingService;

	public WebDAVConnector() {
		BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
		ServiceReference serviceReference = bundleContext.getServiceReference(IEditingHandler.class
				.getName());
		if (serviceReference != null) {
			this.editingService = (IEditingHandler) bundleContext.getService(serviceReference);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.model.remote.IRepository#login(org.remus.infomngmnt
	 * .model.remote.ILoginCallBack, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void login(final ILoginCallBack callback, final IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.model.remote.IRepository#reset()
	 */
	public void reset() {

		this.api = null;
		getCredentialProvider().removePropertyChangeListener(this.credentialsMovedListener);

	}

	public RemoteObject addToRepository(final SynchronizableObject item,
			final IProgressMonitor monitor) throws RemoteException {
		RemoteObject parentRemoteCat = getRemoteObjectBySynchronizableObject(
				(SynchronizableObject) item.eContainer(), monitor);
		String url = parentRemoteCat.getUrl();
		if (item instanceof InformationUnitListItem) {
			try {
				InformationUnit adapter = (InformationUnit) item.getAdapter(InformationUnit.class);
				InformationUnit copy = (InformationUnit) EcoreUtil.copy(adapter);
				String newPath = new StringWriter().append(url).append(FOLDER_PREFIX_INFO).append(
						adapter.getId()).append("/").toString();
				getApi().createDirectory(newPath);
				String remotePathName = new StringWriter().append(newPath).append(copy.getId())
						.append(".info").toString();

				byte[] saveObjectToByte = this.editingService.saveObjectToByte(copy);
				getApi().put(remotePathName, saveObjectToByte);
				InformationStructureRead read = InformationStructureRead.newSession(adapter);
				List<BinaryReference> binaryReferences = read.getBinaryReferences();
				if (binaryReferences.size() > 0) {
					String binaryFolder = new StringWriter().append(newPath).append("/").append(
							FOLDER_NAME_BINARIES).toString();
					getApi().createDirectory(binaryFolder);
					for (BinaryReference binaryReference : binaryReferences) {
						IFile binaryReferenceToFile = InformationUtil.binaryReferenceToFile(
								binaryReference, adapter);
						String projectRelativePath = binaryReference.getProjectRelativePath();
						String binaryRefFile = new StringWriter().append(binaryFolder).append("/")
								.append(projectRelativePath).toString();
						InputStream contents = null;
						try {
							getApi().put(binaryRefFile, binaryReferenceToFile.getContents());
						} finally {
							if (contents != null) {
								StreamCloser.closeStreams(contents);
							}
						}

					}
				}
				DavResource resourceFromUrl = getResourceFromUrl(newPath);
				if (resourceFromUrl != null) {
					return buildSingleInfoUnit(resourceFromUrl);
				}
			} catch (Exception e) {
				throw new RemoteException(StatusCreator.newStatus(
						"Error adding element to repository", e));
			}
		}
		if (item instanceof Category) {
			try {
				Category copy = (Category) EcoreUtil.copy(item);
				copy.getChildren().clear();
				copy.getInformationUnit().clear();
				copy
						.eUnset(InfomngmntPackage.Literals.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA);
				String newPath = new StringWriter().append(url).append(FOLDER_PREFIX_CAT).append(
						copy.getId()).append("/").toString();
				boolean exists = getApi().exists(newPath);
				if (exists) {
					newPath = new StringWriter().append(url).append(FOLDER_PREFIX_CAT).append(
							IdFactory.createId()).append("/").toString();
				}
				getApi().createDirectory(newPath);
				String newRemoteCatPath = new StringWriter().append(newPath).append(FILENAME_CAT)
						.toString();
				byte[] saveObjectToByte = this.editingService.saveObjectToByte(copy);
				getApi().put(newRemoteCatPath, saveObjectToByte);
				DavResource resourceFromUrl = getResourceFromUrl(newPath);
				if (resourceFromUrl != null) {
					return buildSingleCategory(resourceFromUrl);
				}
			} catch (SardineException e) {
				throw new RemoteException(StatusCreator.newStatus("Error committing element", e));
			}
		}
		return null;
	}

	public RemoteObject commit(final SynchronizableObject item2commit,
			final IProgressMonitor monitor) throws CoreException {
		RemoteObject remoteObject = getRemoteObjectBySynchronizableObject(item2commit, monitor);
		if (remoteObject != null) {
			if (item2commit instanceof InformationUnitListItem) {
				try {
					InformationUnit adapter = (InformationUnit) item2commit
							.getAdapter(InformationUnit.class);
					InformationUnit copy = (InformationUnit) EcoreUtil.copy(adapter);

					byte[] saveObjectToByte = this.editingService.saveObjectToByte(copy);
					String infoFile = new StringWriter().append(remoteObject.getUrl()).append(
							remoteObject.getId()).append(".info").toString();
					getApi().put(infoFile, saveObjectToByte);
					String binaryFolder = new StringWriter().append(remoteObject.getUrl()).append(
							FOLDER_NAME_BINARIES).append("/").toString();

					InformationStructureRead read = InformationStructureRead.newSession(adapter);
					List<BinaryReference> binaryReferences = read.getBinaryReferences();
					try {
						DavResource resourceFromUrl = getResourceFromUrl(binaryFolder);
						if (resourceFromUrl.isDirectory()) {
							getApi().delete(binaryFolder);
						}
					} catch (Exception e) {
						// do nothing.. we probably have no binary reference
						// folder...
					}
					if (binaryReferences.size() > 0) {
						getApi().createDirectory(binaryFolder);
						for (BinaryReference binaryReference : binaryReferences) {
							IFile binaryReferenceToFile = InformationUtil.binaryReferenceToFile(
									binaryReference, adapter);
							String projectRelativePath = binaryReference.getProjectRelativePath();
							String binaryRefFile = new StringWriter().append(binaryFolder).append(
									"/").append(projectRelativePath).toString();

							InputStream contents = null;
							try {
								contents = binaryReferenceToFile.getContents();
								getApi().put(binaryRefFile, contents);
							} finally {
								if (contents != null) {
									StreamCloser.closeStreams(contents);
								}
							}
						}
					}
				} catch (Exception e) {
					throw new RemoteException(StatusCreator
							.newStatus("Error committing element", e));
				}
				DavResource resourceFromUrl = getResourceFromUrl(remoteObject.getUrl());
				if (resourceFromUrl != null) {
					return buildSingleInfoUnit(resourceFromUrl);
				}

			}
			if (item2commit instanceof Category) {
				Category copy = (Category) EcoreUtil.copy(item2commit);
				copy.getChildren().clear();
				copy.getInformationUnit().clear();
				copy
						.eUnset(InfomngmntPackage.Literals.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA);
				String infoFile = new StringWriter().append(remoteObject.getUrl()).append("/")
						.append(FILENAME_CAT).toString();
				try {
					byte[] saveObjectToByte = this.editingService.saveObjectToByte(copy);
					getApi().put(infoFile, saveObjectToByte);
				} catch (Exception e) {
					throw new RemoteException(StatusCreator.newStatus("Error committing category",
							e));
				}
				DavResource resourceFromUrl = getResourceFromUrl(remoteObject.getUrl());
				if (resourceFromUrl != null) {
					return buildSingleCategory(resourceFromUrl);
				}
			}
		}
		return null;
	}

	public void deleteFromRepository(final SynchronizableObject item, final IProgressMonitor monitor)
			throws CoreException {
		try {

			getApi().delete(item.getSynchronizationMetaData().getUrl());

		} catch (SardineException e) {
			throw new RemoteException(StatusCreator.newStatus("Error deleting remote objects", e));
		}

	}

	@Override
	public String getRepositoryUrl() {
		return ((WebDavCredentialProvider) getCredentialProvider()).getUrl();
	}

	public RemoteObject[] getChildren(final IProgressMonitor monitor,
			final RemoteContainer container, final boolean showOnlyContainers)
			throws RemoteException {
		List<RemoteObject> returnValue = new ArrayList<RemoteObject>();
		returnValue.addAll(buildSubFolders(container.getUrl()));
		if (!showOnlyContainers) {
			returnValue.addAll(buildItems(container.getUrl()));
		}
		return returnValue.toArray(new RemoteObject[returnValue.size()]);
	}

	private Collection<? extends RemoteObject> buildItems(final String url) throws RemoteException {
		List<DavResource> resources;
		try {
			resources = getApi().getResources(url);
		} catch (SardineException e) {
			throw new RemoteException(StatusCreator.newStatus("Error building remote object", e));
		}
		List<DavResource> filter = CollectionUtils.filter(resources,
				new CollectionFilter<DavResource>() {

					public boolean select(final DavResource item) {
						return item.getName().startsWith(FOLDER_PREFIX_INFO);
					}
				});
		List<RemoteObject> returnValue = new ArrayList<RemoteObject>();
		if (filter != null) {
			for (DavResource file2 : filter) {
				RemoteObject buildSingleInfoUnit = buildSingleInfoUnit(file2);
				if (buildSingleInfoUnit != null) {
					returnValue.add(buildSingleInfoUnit);
				}
			}

		}
		return returnValue;
	}

	private RemoteObject buildSingleInfoUnit(final DavResource file2) throws RemoteException {
		if (file2.isDirectory()) {
			List<DavResource> resources;
			try {
				resources = getApi().getResources(file2.getAbsoluteUrl());
			} catch (Exception e1) {
				throw new RemoteException(StatusCreator.newStatus(
						"Error building single remote object", e1));
			}
			List<DavResource> filter = CollectionUtils.filter(resources,
					new CollectionFilter<DavResource>() {
						public boolean select(final DavResource item) {
							return item.getName().endsWith(".info");
						}
					});

			if (filter.size() == 1) {
				InputStream fileInputStream = null;
				DavResource davResource = filter.get(0);
				try {
					fileInputStream = getApi().getInputStream(davResource.getAbsoluteUrl());
					IFile createTempFile = ResourceUtil.createTempFile();
					createTempFile.setContents(fileInputStream, true, false,
							new NullProgressMonitor());
					InformationUnit unit = this.editingService.getObjectFromFile(createTempFile,
							InfomngmntPackage.Literals.INFORMATION_UNIT, null);
					if (unit != null) {
						RemoteObject container = InfomngmntFactory.eINSTANCE.createRemoteObject();
						container.setName(unit.getLabel());
						container.setUrl(file2.getAbsoluteUrl());
						container.setWrappedObject(unit);
						container.setId(unit.getId());
						container.setHash(String.valueOf(davResource.getModified().getTime()));
						return container;
					}
				} catch (Exception e) {
					// return just null.
				} finally {
					StreamCloser.closeStreams(fileInputStream);
				}
			}
		}
		return null;
	}

	private Collection<? extends RemoteObject> buildSubFolders(final String url)
			throws RemoteException {
		List<DavResource> resources;
		try {
			resources = getApi().getResources(url);
		} catch (Exception e) {
			throw new RemoteException(StatusCreator.newStatus("Error building subfolders", e));
		}
		List<RemoteObject> returnValue = new ArrayList<RemoteObject>();
		List<DavResource> filter = CollectionUtils.filter(resources,
				new CollectionFilter<DavResource>() {

					public boolean select(final DavResource item) {
						return item.getName().startsWith(FOLDER_PREFIX_CAT);
					}
				});
		if (filter != null) {
			for (DavResource file2 : filter) {
				RemoteContainer buildSingleCategory = buildSingleCategory(file2);
				if (buildSingleCategory != null) {
					returnValue.add(buildSingleCategory);
				}
			}
		}
		return returnValue;
	}

	@Override
	public InformationUnit getPrefetchedInformationUnit(final RemoteObject remoteObject) {
		return (InformationUnit) remoteObject.getWrappedObject();
	}

	private RemoteContainer buildSingleCategory(final DavResource file2) throws RemoteException {
		if (file2.isDirectory()) {
			List<DavResource> resources;
			try {
				resources = getApi().getResources(file2.getAbsoluteUrl());
			} catch (Exception e1) {
				throw new RemoteException(StatusCreator.newStatus(
						"Error getting category from remote WebDav resource", e1));
			}
			List<DavResource> filter = CollectionUtils.filter(resources,
					new CollectionFilter<DavResource>() {
						public boolean select(final DavResource item) {
							return FILENAME_CAT.equals(item.getName());
						}
					});
			if (filter.size() == 1) {
				InputStream inputStream = null;
				try {
					DavResource davResource = filter.get(0);
					inputStream = getApi().getInputStream(davResource.getAbsoluteUrl());
					IFile createTempFile = ResourceUtil.createTempFile();
					createTempFile.setContents(inputStream, true, false, new NullProgressMonitor());
					Category unit = this.editingService.getObjectFromFile(createTempFile,
							InfomngmntPackage.Literals.CATEGORY, null);
					if (unit != null) {
						RemoteContainer container = InfomngmntFactory.eINSTANCE
								.createRemoteContainer();
						container.setName(unit.getLabel());
						container.setUrl(file2.getAbsoluteUrl());
						container.setWrappedObject(unit);
						container.setId(unit.getId());
						container.setHash(String.valueOf(davResource.getModified().getTime()));
						return container;
					}
				} catch (Exception e) {
					e.printStackTrace();
					// return just null.
				} finally {
					StreamCloser.closeStreams(inputStream);
				}

			}
		}
		return null;
	}

	public InformationUnit getFullObject(final InformationUnitListItem informationUnitListItem,
			final IProgressMonitor monitor) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public RemoteObject getRemoteObjectBySynchronizableObject(final SynchronizableObject object,
			final IProgressMonitor monitor) throws RemoteException {
		String url = object.getSynchronizationMetaData().getUrl();
		if (getRepositoryById(getLocalRepositoryId()).getUrl().equals(url)) {
			return getRepositoryById(getLocalRepositoryId());
		}
		DavResource resourceFromUrl = getResourceFromUrl(url);
		if (resourceFromUrl != null) {
			if (object instanceof Category) {
				return buildSingleCategory(resourceFromUrl);
			}
			if (object instanceof InformationUnitListItem) {
				return buildSingleInfoUnit(resourceFromUrl);
			}
		}
		return null;
	}

	public String getTypeIdByObject(final RemoteObject remoteObject) {
		Object wrappedObject = remoteObject.getWrappedObject();
		if (wrappedObject instanceof InformationUnit) {
			String type = ((InformationUnit) wrappedObject).getType();
			IInformationTypeHandler service = RemoteActivator.getDefault().getServiceTracker()
					.getService(IInformationTypeHandler.class);
			IInfoType infoTypeByType = service.getInfoTypeByType(type);
			RemoteActivator.getDefault().getServiceTracker().ungetService(service);
			if (infoTypeByType != null) {
				return type;
			}
		}
		return null;
	}

	private DavResource getResourceFromUrl(final String url) throws RemoteException {
		List<DavResource> resources;
		try {
			resources = getApi().getResources(url);
		} catch (SardineException e) {
			throw new RemoteException(StatusCreator.newStatus("Error resolving DAVElement", e));
		}
		for (DavResource davResource : resources) {
			if (davResource.getAbsoluteUrl().equals(url)) {
				return davResource;
			}
		}
		return null;
	}

	@Override
	public IFile getBinaryReferences(final InformationUnitListItem remoteObject,
			final InformationUnit localInfoFragment, final IProgressMonitor monitor)
			throws RemoteException {
		if (localInfoFragment.getBinaryReferences() != null) {
			String url = remoteObject.getSynchronizationMetaData().getUrl();
			String remoteFilePath = new StringWriter().append(url).append(FOLDER_NAME_BINARIES)
					.append("/").append(
							localInfoFragment.getBinaryReferences().getProjectRelativePath())
					.toString();
			try {
				DavResource resourceFromUrl = getResourceFromUrl(remoteFilePath);

				if (resourceFromUrl != null && !resourceFromUrl.isDirectory()) {
					IFile createTempFile = ResourceUtil.createTempFile(new Path(resourceFromUrl
							.getName()).getFileExtension());
					InputStream fileInputStream = null;

					try {
						fileInputStream = getApi().getInputStream(resourceFromUrl.getAbsoluteUrl());
						createTempFile.setContents(fileInputStream, true, false,
								new SubProgressMonitor(monitor, IProgressMonitor.UNKNOWN));
						return createTempFile;
					} finally {
						StreamCloser.closeStreams(fileInputStream);
					}
				}
			} catch (Exception e) {
				throw new RemoteException(StatusCreator.newStatus(
						"Error resolving binary references", e));
			}
		}
		return null;

	}

	public synchronized Sardine getApi() throws RemoteException {
		if (this.api != null) {
			reset();
		}
		if (this.api == null) {
			getCredentialProvider().setIdentifier(getLocalRepositoryId());
			try {
				IRepositoryService service = RemoteActivator.getDefault().getServiceTracker()
						.getService(IRepositoryService.class);
				RemoteRepository repositoryById = service.getRepositoryById(getLocalRepositoryId());
				if (Boolean.valueOf(repositoryById.getOptions().get(
						Activator.REPOSITORY_OPTIONS_BASIC_AUTHENTICATION))) {
					this.api = SardineFactory.begin(getCredentialProvider().getUserName(),
							getCredentialProvider().getPassword());
				} else {
					this.api = SardineFactory.begin();
				}

				RemoteActivator.getDefault().getServiceTracker().ungetService(service);
				getCredentialProvider().addPropertyChangeListener(this.credentialsMovedListener);
			} catch (SardineException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return this.api;
	}

	@Override
	public IStatus validate() {
		try {
			getApi().getResources(getRepositoryUrl());
		} catch (Exception e) {
			return StatusCreator.newStatus(e.getMessage());
		}
		return Status.OK_STATUS;
	}

}
