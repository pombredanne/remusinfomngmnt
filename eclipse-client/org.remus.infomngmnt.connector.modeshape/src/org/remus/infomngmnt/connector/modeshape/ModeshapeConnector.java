/*******************************************************************************
 * Copyright (c) 2011 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.connector.modeshape;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.osgi.util.NLS;
import org.eclipse.remus.BinaryReference;
import org.eclipse.remus.Category;
import org.eclipse.remus.InfomngmntFactory;
import org.eclipse.remus.InfomngmntPackage;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.RemoteContainer;
import org.eclipse.remus.RemoteObject;
import org.eclipse.remus.RemoteRepository;
import org.eclipse.remus.SynchronizableObject;
import org.eclipse.remus.common.core.streams.StreamUtil;
import org.eclipse.remus.common.core.util.ResourceUtil;
import org.eclipse.remus.common.core.zip.Pair;
import org.eclipse.remus.common.core.zip.UnzipUtil;
import org.eclipse.remus.common.core.zip.ZipUtil;
import org.eclipse.remus.core.extension.IEmitter;
import org.eclipse.remus.core.extension.IInfoType;
import org.eclipse.remus.core.model.InformationStructureEdit;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.core.remote.AbstractExtensionRepository;
import org.eclipse.remus.core.remote.RemoteActivator;
import org.eclipse.remus.core.remote.RemoteException;
import org.eclipse.remus.core.remote.services.IRepositoryService;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.core.services.IInformationTypeHandler;
import org.eclipse.remus.jcrjson.ModeshapeClient;
import org.eclipse.remus.jcrjson.model.InformationPresentation;
import org.eclipse.remus.jcrjson.model.Item;
import org.eclipse.remus.jcrjson.model.PoolCategory;
import org.eclipse.remus.jcrjson.model.TransferCategory;
import org.eclipse.remus.jcrjson.model.ZipEntry;
import org.eclipse.remus.model.remote.ILoginCallBack;
import org.eclipse.remus.model.remote.IRepository;
import org.eclipse.remus.services.RemusServiceTracker;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.remus.util.StatusCreator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.remus.infomngmnt.emitter.uixml.UiXml;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ModeshapeConnector extends AbstractExtensionRepository implements
		IRepository {

	private static final String UIXML_EMITTER = "UIXML"; //$NON-NLS-1$

	private static final String INFO_UNIT_XML = "infoUnit.xml"; //$NON-NLS-1$

	private ModeshapeClient api;

	private IEditingHandler editingService;

	private static ThreadLocal<IFolder> tmpFile = new ThreadLocal<IFolder>();

	/**
	 * 
	 */
	public ModeshapeConnector() {
		BundleContext bundleContext = ModeshapeActivator.getDefault()
				.getBundle().getBundleContext();
		ServiceReference serviceReference = bundleContext
				.getServiceReference(IEditingHandler.class.getName());
		if (serviceReference != null) {
			editingService = (IEditingHandler) bundleContext
					.getService(serviceReference);
		}
		infoTypeService = new RemusServiceTracker(ModeshapeActivator
				.getDefault().getBundle())
				.getService(IInformationTypeHandler.class);
	}

	private final PropertyChangeListener credentialsMovedListener = new PropertyChangeListener() {
		public void propertyChange(final PropertyChangeEvent evt) {
			reset();
		}
	};

	public static final String REPOSITORY = "remus-online"; //$NON-NLS-1$

	public static final String TRANSFER_URL = "/Transfer"; //$NON-NLS-1$

	public static final String POOL_URL = "/Pool"; //$NON-NLS-1$

	private final IInformationTypeHandler infoTypeService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.remus.model.remote.IRepository#login(org.eclipse.remus.model
	 * .remote.ILoginCallBack, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void login(ILoginCallBack callback, IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.remus.model.remote.IRepository#reset()
	 */
	public void reset() {
		api = null;
		getCredentialProvider().removePropertyChangeListener(
				credentialsMovedListener);

	}

	public RemoteObject[] getChildren(IProgressMonitor monitor,
			RemoteContainer container, boolean showOnlyContainers)
			throws RemoteException {
		List<RemoteObject> returnValue = new ArrayList<RemoteObject>();
		if (container instanceof RemoteRepository) {
			if (isTransfer()) {
				returnValue.add(buildTransfer());
				returnValue.add(buildPoolItem(null));
			} else {
				RemoteObject pool = buildPoolItem(null);
				returnValue.addAll(buildPoolItems(
						(PoolCategory) pool.getWrappedObject(),
						showOnlyContainers));
			}
		} else if (container.getWrappedObject() instanceof TransferCategory) {
			returnValue.addAll(buildTransferItems((TransferCategory) container
					.getWrappedObject()));
		} else if (container.getWrappedObject() instanceof PoolCategory) {
			returnValue.addAll(buildPoolItems(
					(PoolCategory) container.getWrappedObject(),
					showOnlyContainers));
		}
		return returnValue.toArray(new RemoteObject[returnValue.size()]);
	}

	private Collection<? extends RemoteObject> buildPoolItems(
			PoolCategory wrappedObject, boolean showOnlyContainers)
			throws RemoteException {
		List<RemoteObject> returnValue = new ArrayList<RemoteObject>();

		try {
			List<Item> items = getApi().getItems(REPOSITORY,
					getWorkspaceName(), wrappedObject.getPath());
			for (Item item : items) {
				if (item instanceof PoolCategory) {
					returnValue.add(buildPoolItem((PoolCategory) item));
				} else if (item instanceof org.eclipse.remus.jcrjson.model.InformationUnit
						&& !showOnlyContainers) {
					returnValue
							.add(buildSingleRemoteObject((org.eclipse.remus.jcrjson.model.InformationUnit) item));
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue;
	}

	private Collection<? extends RemoteObject> buildTransferItems(
			TransferCategory object) throws RemoteException {
		List<RemoteObject> returnValue = new ArrayList<RemoteObject>();
		try {
			List<Item> items = getApi().getItems(REPOSITORY,
					getWorkspaceName(), object.getPath());
			for (Item item : items) {
				if (item instanceof org.eclipse.remus.jcrjson.model.InformationUnit) {
					returnValue
							.add(buildSingleRemoteObject((org.eclipse.remus.jcrjson.model.InformationUnit) item));
				}
			}
		} catch (JSONException e) {
			throw new RemoteException(StatusCreator.newStatus(
					Messages.ModeshapeConnector_ErrorLoadingTransfer, e));
		} catch (IOException e) {
			throw new RemoteException(StatusCreator.newStatus(
					Messages.ModeshapeConnector_ErrorLoadingTransfer, e));

		}
		return returnValue;
	}

	private RemoteObject buildSingleRemoteObject(
			org.eclipse.remus.jcrjson.model.InformationUnit element) {
		RemoteObject createRemoteObject = InfomngmntFactory.eINSTANCE
				.createRemoteObject();
		createRemoteObject.setHash(String.valueOf(element.getLastModified()
				.getTime()));
		createRemoteObject.setId(element.getId());
		createRemoteObject.setName(element.getName());
		createRemoteObject.setWrappedObject(element);
		createRemoteObject.setUrl(getRepositoryUrl() + "/" + element.getPath()); //$NON-NLS-1$
		return createRemoteObject;
	}

	private RemoteObject buildPoolItem(PoolCategory item)
			throws RemoteException {
		try {
			if (item == null) {
				try {
					item = (PoolCategory) getApi().getItemByPath(REPOSITORY,
							getWorkspaceName(), POOL_URL);
				} catch (Exception e) {
					// try again. it could be that the workspace is not
					// initialized
					getApi().checkInitialization(REPOSITORY, getWorkspaceName());
					item = (PoolCategory) getApi().getItemByPath(REPOSITORY,
							getWorkspaceName(), POOL_URL);
				}
			}
			RemoteContainer returnValue = InfomngmntFactory.eINSTANCE
					.createRemoteContainer();
			returnValue.setId(item.getId());
			returnValue.setName(item.getName());
			returnValue.setHash(String.valueOf(item.getLastModified()));
			if ((getRepositoryUrl() + "/" + POOL_URL).equals(item.getPath()) //$NON-NLS-1$
					&& !isTransfer()) {
				IRepositoryService service = RemoteActivator.getDefault()
						.getServiceTracker()
						.getService(IRepositoryService.class);
				RemoteRepository repositoryById = service
						.getRepositoryById(getLocalRepositoryId());
				returnValue.setUrl(repositoryById.getUrl());
			} else {
				returnValue.setUrl(getRepositoryUrl() + "/" + item.getPath()); //$NON-NLS-1$

			}
			returnValue.setWrappedObject(item);
			return returnValue;
		} catch (JSONException e) {
			throw new RemoteException(StatusCreator.newStatus(
					Messages.ModeshapeConnector_ErrorPoolCategory, e));
		} catch (IOException e) {
			throw new RemoteException(StatusCreator.newStatus(
					Messages.ModeshapeConnector_ErrorPoolCategory, e));
		}
	}

	private RemoteObject buildTransfer() throws RemoteException {
		try {
			Item itemByPath = getApi().getItemByPath(REPOSITORY,
					getWorkspaceName(), TRANSFER_URL);
			RemoteContainer returnValue = InfomngmntFactory.eINSTANCE
					.createRemoteContainer();
			returnValue.setId(itemByPath.getId());
			returnValue.setName(itemByPath.getName());
			returnValue.setHash(String.valueOf(itemByPath.getLastModified()));
			returnValue.setUrl(getRepositoryUrl() + "/" + itemByPath.getPath()); //$NON-NLS-1$
			returnValue.setWrappedObject(itemByPath);
			return returnValue;
		} catch (JSONException e) {
			throw new RemoteException(StatusCreator.newStatus(
					Messages.ModeshapeConnector_ErrorPoolCategory, e));
		} catch (IOException e) {
			throw new RemoteException(StatusCreator.newStatus(
					Messages.ModeshapeConnector_ErrorPoolCategory, e));
		}
	}

	// private String getUrl() {
	// if
	// ((ModeshapeCredentialProvider)getCredentialProvider()).isUseSeparateWorkspace())
	// {
	//
	// }
	// }

	public String getTypeIdByObject(RemoteObject remoteObject) {
		org.eclipse.remus.jcrjson.model.InformationUnit wrappedObject = (org.eclipse.remus.jcrjson.model.InformationUnit) remoteObject
				.getWrappedObject();
		String type = wrappedObject.getType();
		IInformationTypeHandler service = RemoteActivator.getDefault()
				.getServiceTracker().getService(IInformationTypeHandler.class);
		IInfoType infoTypeByType = service.getInfoTypeByType(type);
		RemoteActivator.getDefault().getServiceTracker().ungetService(service);
		if (infoTypeByType != null) {
			return type;
		}
		return null;
	}

	public InformationUnit getFullObject(
			InformationUnitListItem informationUnitListItem,
			IProgressMonitor monitor) throws RemoteException {
		RemoteObject remoteObject = getRemoteObjectBySynchronizableObject(
				informationUnitListItem, monitor);
		Object wrappedObject = remoteObject.getWrappedObject();
		try {
			if (wrappedObject instanceof InformationPresentation) {
				List<Item> items = getApi().getItems(REPOSITORY,
						getWorkspaceName(), ((Item) wrappedObject).getPath());
				if (items.size() >= 1) {
					for (Item item : items) {
						if (item instanceof ZipEntry) {
							org.eclipse.remus.jcrjson.model.ZipEntry data = (ZipEntry) item;
							byte[] loadData = getApi().loadData(REPOSITORY,
									getWorkspaceName(), data.getPath());
							File tmpZip = ResourceUtil
									.createTempFileOnFileSystem("zip"); //$NON-NLS-1$
							IOUtils.write(loadData,
									new FileOutputStream(tmpZip));
							IFolder tmpFolder = ResourceUtil
									.createTempFolderOnFileSystem();
							new UnzipUtil(tmpZip, tmpFolder.getLocation()
									.toFile()).unzipAll();
							tmpFile.set(tmpFolder);
							tmpFolder.refreshLocal(IResource.DEPTH_INFINITE,
									monitor);
							InformationUnit unit = editingService
									.getObjectFromFile(
											tmpFolder.getFile(INFO_UNIT_XML),
											InfomngmntPackage.Literals.INFORMATION_UNIT,
											null, false);
							return unit;
						}
					}

				}
			} else if (wrappedObject instanceof org.eclipse.remus.jcrjson.model.InformationUnit) {
				org.eclipse.remus.jcrjson.model.InformationUnit transferElement = (org.eclipse.remus.jcrjson.model.InformationUnit) wrappedObject;
				InformationStructureEdit edit = InformationStructureEdit
						.newSession(transferElement.getType());
				InformationUnit returnValue = edit.newInformationUnit();
				edit.setValue(returnValue, transferElement.getType(),
						transferElement.getValue());
				returnValue.setCreationDate(transferElement.getLastModified());

				return returnValue;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		throw new RemoteException(StatusCreator.newStatus("Undefined")); //$NON-NLS-1$
	}

	public RemoteObject commit(SynchronizableObject item2commit,
			IProgressMonitor monitor) throws CoreException {
		String url = item2commit.getSynchronizationMetaData().getUrl();
		if (item2commit instanceof Category
				&& url.startsWith(getRepositoryUrl() + "/" + POOL_URL)) { //$NON-NLS-1$
			PoolCategory cat = new PoolCategory();
			cat.setName(((Category) item2commit).getLabel());
			cat.setPath(StringUtils.replaceOnce(url, getRepositoryUrl() + "/", //$NON-NLS-1$
					"")); //$NON-NLS-1$
			try {
				PoolCategory addCategory = getApi().addCategory(REPOSITORY,
						getWorkspaceName(), cat);
				return buildPoolItem(addCategory);
			} catch (IOException e) {
				throw new RemoteException(StatusCreator.newStatus(
						Messages.ModeshapeConnector_ErrorUpdating
								+ cat.getName(), e));
			}
		} else if (item2commit instanceof InformationUnitListItem) {
			InformationUnit origElement = (InformationUnit) item2commit
					.getAdapter(InformationUnit.class);
			org.eclipse.remus.jcrjson.model.InformationUnit convertToTargetObject = convertToTargetObject(
					origElement,
					url.startsWith(getRepositoryUrl() + "/" + POOL_URL)); //$NON-NLS-1$
			convertToTargetObject.setPath(StringUtils.replaceOnce(url,
					getRepositoryUrl() + "/", "")); //$NON-NLS-1$ //$NON-NLS-2$
			try {
				org.eclipse.remus.jcrjson.model.InformationUnit addInformationUnit = getApi()
						.updateInformationUnit(REPOSITORY, getWorkspaceName(),
								convertToTargetObject);
				return buildSingleRemoteObject(addInformationUnit);
			} catch (JSONException e) {
				throw new RemoteException(StatusCreator.newStatus(NLS.bind(
						Messages.ModeshapeConnector_ErrorUpdating2,
						origElement.getLabel()), e));
			} catch (IOException e) {
				throw new RemoteException(StatusCreator.newStatus(NLS.bind(
						Messages.ModeshapeConnector_ErrorUpdating2,
						origElement.getLabel()), e));
			}
		}
		return null;
	}

	public RemoteObject addToRepository(SynchronizableObject item,
			IProgressMonitor monitor) throws RemoteException {
		RemoteObject parentRemoteCat = getRemoteObjectBySynchronizableObject(
				(SynchronizableObject) item.eContainer(), monitor);
		String url = parentRemoteCat.getUrl();
		if (item instanceof InformationUnitListItem
				&& parentRemoteCat.getWrappedObject() instanceof TransferCategory) {
			InformationUnit origElement = (InformationUnit) item
					.getAdapter(InformationUnit.class);
			org.eclipse.remus.jcrjson.model.InformationUnit convertToTargetObject = convertToTargetObject(
					origElement, false);
			convertToTargetObject.setPath(((Item) parentRemoteCat
					.getWrappedObject()).getPath() + "/" //$NON-NLS-1$
					+ System.currentTimeMillis());
			org.eclipse.remus.jcrjson.model.InformationUnit addInformationUnit;
			try {
				addInformationUnit = getApi().addInformationUnit(REPOSITORY,
						getWorkspaceName(), convertToTargetObject);
				return buildSingleRemoteObject(addInformationUnit);
			} catch (JSONException e) {
				throw new RemoteException(StatusCreator.newStatus(NLS.bind(
						Messages.ModeshapeConnector_ErrorAdding,
						origElement.getLabel()), e));
			} catch (IOException e) {
				throw new RemoteException(StatusCreator.newStatus(NLS.bind(
						Messages.ModeshapeConnector_ErrorAdding,
						origElement.getLabel()), e));
			}

		} else if (item instanceof InformationUnitListItem
				&& parentRemoteCat.getWrappedObject() instanceof PoolCategory) {
			InformationUnit origElement = (InformationUnit) item
					.getAdapter(InformationUnit.class);
			org.eclipse.remus.jcrjson.model.InformationUnit convertToPresentation = convertToTargetObject(
					origElement, true);
			convertToPresentation.setPath(((Item) parentRemoteCat
					.getWrappedObject()).getPath() + "/" //$NON-NLS-1$
					+ System.currentTimeMillis());
			try {
				org.eclipse.remus.jcrjson.model.InformationUnit addInformationUnit = getApi()
						.addInformationUnit(REPOSITORY, getWorkspaceName(),
								convertToPresentation);
				return buildSingleRemoteObject(addInformationUnit);
			} catch (JSONException e) {
				throw new RemoteException(StatusCreator.newStatus(NLS.bind(
						Messages.ModeshapeConnector_ErrorAdding,
						origElement.getLabel()), e));
			} catch (IOException e) {
				throw new RemoteException(StatusCreator.newStatus(NLS.bind(
						Messages.ModeshapeConnector_ErrorAdding,
						origElement.getLabel()), e));
			}
		}
		if (item instanceof Category
				&& parentRemoteCat.getWrappedObject() instanceof PoolCategory) {
			PoolCategory cat = new PoolCategory();
			cat.setName(((Category) item).getLabel());
			cat.setPath(((Item) parentRemoteCat.getWrappedObject()).getPath()
					+ "/" + System.currentTimeMillis()); //$NON-NLS-1$
			PoolCategory addCategory;
			try {
				addCategory = getApi().addCategory(REPOSITORY,
						getWorkspaceName(), cat);
				return buildPoolItem(addCategory);
			} catch (IOException e) {
				throw new RemoteException(StatusCreator.newStatus(
						NLS.bind(Messages.ModeshapeConnector_ErrorAdding,
								cat.getName()), e));
			}
		}
		return null;
	}

	private org.eclipse.remus.jcrjson.model.InformationUnit convertToTargetObject(
			InformationUnit origElement, boolean poolElement)
			throws RemoteException {
		InformationStructureRead read = InformationStructureRead
				.newSession(origElement);
		org.eclipse.remus.jcrjson.model.InformationUnit returnValue = new org.eclipse.remus.jcrjson.model.InformationUnit();

		if (poolElement) {
			returnValue = new InformationPresentation();
			IEmitter emitterByType = infoTypeService.getEmitterByType(
					origElement.getType(), UIXML_EMITTER);
			if (emitterByType != null) {
				emitterByType.preEmitt();
				try {
					Object emitt = emitterByType.emitt(origElement);
					if (emitt instanceof UiXml) {
						UiXml uiXml = (UiXml) emitt;
						((InformationPresentation) returnValue)
								.setHtmlPresenation(uiXml.getXmlString());
						if (uiXml.getBinaryReferences() != null) {
							Set<String> keySet = uiXml.getBinaryReferences()
									.keySet();
							returnValue
									.setBinaryReferences(new ArrayList<org.eclipse.remus.jcrjson.model.BinaryReference>());
							for (String string : keySet) {
								try {
									org.eclipse.remus.jcrjson.model.BinaryReference binaryReference = new org.eclipse.remus.jcrjson.model.BinaryReference();
									binaryReference.setReferenceId(string);
									IFile iFile = uiXml.getBinaryReferences()
											.get(string);
									binaryReference.setData(StreamUtil
											.convertStreamToByte(iFile
													.getContents()));
									binaryReference.setExtension(iFile
											.getFullPath().getFileExtension());
									returnValue.getBinaryReferences().add(
											binaryReference);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (CoreException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}

					}
					emitterByType.postEmitt();
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			List<BinaryReference> binaryReferences2 = read
					.getBinaryReferences();
			File tmpZip = ResourceUtil.createTempFileOnFileSystem("zip"); //$NON-NLS-1$
			List<Pair<File, String>> entries = new ArrayList<Pair<File, String>>();
			for (BinaryReference binaryReference : binaryReferences2) {
				IFile binaryReferenceToFile = InformationUtil
						.binaryReferenceToFile(binaryReference, origElement);
				entries.add(new Pair<File, String>(binaryReferenceToFile
						.getLocation().toFile(), new Path(binaryReference
						.getProjectRelativePath()).lastSegment()));
			}
			entries.add(new Pair<File, String>(((IFile) origElement
					.getAdapter(IFile.class)).getLocation().toFile(),
					INFO_UNIT_XML));
			ZipEntry zipEntry;
			try {
				ZipUtil.createZipFile(entries, tmpZip, 9);

				zipEntry = new ZipEntry();
				zipEntry.setData(StreamUtil
						.convertStreamToByte(new FileInputStream(tmpZip)));
				((InformationPresentation) returnValue).setOrigZip(zipEntry);
			} catch (IOException e) {
				throw new RemoteException(StatusCreator.newStatus(NLS.bind(
						Messages.ModeshapeConnector_ErrorPackaging,
						returnValue.getName()), e));
			}
		} else {
			// Transfer-Category-Add. We support only HTML, LINK and FILE
			returnValue.setValue((origElement).getStringValue());
			if (!"HTML".equals(origElement.getType()) //$NON-NLS-1$
					&& !"LINK".equals(origElement.getType()) //$NON-NLS-1$
					&& !"FILE".equals(origElement.getType())) { //$NON-NLS-1$
				throw new RemoteException(StatusCreator.newStatus(NLS.bind(
						Messages.ModeshapeConnector_NotSupported,
						origElement.getType())));
			}
			IFile file = InformationUtil.getBinaryReferenceFile(origElement);
			if (file != null && file.exists()) {
				try {
					org.eclipse.remus.jcrjson.model.BinaryReference binaryReference = new org.eclipse.remus.jcrjson.model.BinaryReference();
					binaryReference.setExtension(file.getProjectRelativePath()
							.getFileExtension());
					binaryReference.setId((origElement).getBinaryReferences()
							.getId());
					binaryReference.setData(StreamUtil.convertStreamToByte(file
							.getContents()));
					returnValue.setBinaryReferences(Collections
							.singletonList(binaryReference));
				} catch (IOException e) {
					throw new RemoteException(
							StatusCreator.newStatus(
									NLS.bind(
											Messages.ModeshapeConnector_ErrorAddingFileReference,
											returnValue.getName()), e));
				} catch (CoreException e) {
					throw new RemoteException(
							StatusCreator.newStatus(
									NLS.bind(
											Messages.ModeshapeConnector_ErrorAddingFileReference,
											returnValue.getName()), e));
				}
			}

		}
		returnValue.setName(origElement.getLabel());
		returnValue.setType(origElement.getType());
		return returnValue;
	}

	public void deleteFromRepository(SynchronizableObject item,
			IProgressMonitor monitor) throws CoreException {
		RemoteObject remote = getRemoteObjectBySynchronizableObject(item,
				monitor);
		if (remote != null && remote.getWrappedObject() != null) {
			try {
				getApi().deleteElement(REPOSITORY, getWorkspaceName(),
						((Item) remote.getWrappedObject()).getPath());
			} catch (JSONException e) {
				throw new RemoteException(StatusCreator.newStatus(
						Messages.ModeshapeConnector_ErrorDelete, e));
			} catch (IOException e) {
				throw new RemoteException(StatusCreator.newStatus(
						Messages.ModeshapeConnector_ErrorDelete, e));

			}
		}

	}

	@Override
	public IFile getBinaryReferences(InformationUnitListItem syncObject,
			InformationUnit localInfoFragment, IProgressMonitor monitor)
			throws RemoteException {
		BinaryReference binaryReferences = localInfoFragment
				.getBinaryReferences();
		if (binaryReferences != null) {
			String lastSegment = new Path(
					binaryReferences.getProjectRelativePath()).lastSegment();
			IFile file = tmpFile.get().getFile(lastSegment);
			if (file.exists()) {
				return file;
			}
		}
		return super
				.getBinaryReferences(syncObject, localInfoFragment, monitor);
	}

	@Override
	public boolean proceedLocalInformationUnitAfterSync(
			InformationUnit newOrUpdatedLocalInformationUnit,
			IProgressMonitor monitor) {
		try {
			if (tmpFile.get() != null) {
				tmpFile.get().delete(false, monitor);
			}
		} catch (CoreException e) {
			// We skip that.
		}
		tmpFile.remove();
		return false;
	}

	@Override
	public boolean hasBinaryReferences() {
		return true;
	}

	public RemoteObject getRemoteObjectBySynchronizableObject(
			SynchronizableObject object, IProgressMonitor monitor)
			throws RemoteException {
		String url = object.getSynchronizationMetaData().getUrl();
		IRepositoryService service = RemoteActivator.getDefault()
				.getServiceTracker().getService(IRepositoryService.class);
		RemoteRepository repositoryById = service
				.getRepositoryById(getLocalRepositoryId());
		if (url.equals(repositoryById.getUrl())) {
			if (!isTransfer()) {
				RemoteObject buildPoolItem = buildPoolItem(null);
				repositoryById.setWrappedObject(buildPoolItem
						.getWrappedObject());
			}
			return repositoryById;

		}
		url = StringUtils.replaceOnce(url, getRepositoryUrl() + "/", ""); //$NON-NLS-1$ //$NON-NLS-2$
		try {
			Item itemByPath = getApi().getItemByPath(REPOSITORY,
					getWorkspaceName(), url);
			if (itemByPath != null) {
				if (itemByPath instanceof TransferCategory) {
					return buildTransfer();
				} else if (itemByPath instanceof PoolCategory) {
					return buildPoolItem((PoolCategory) itemByPath);
				} else if (itemByPath instanceof org.eclipse.remus.jcrjson.model.InformationUnit) {
					return buildSingleRemoteObject((org.eclipse.remus.jcrjson.model.InformationUnit) itemByPath);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String getWorkspaceName() {
		if (((ModeshapeCredentialProvider) getCredentialProvider())
				.isUseSeparateWorkspace()) {
			return ((ModeshapeCredentialProvider) getCredentialProvider())
					.getWorkspace();
		}
		return getCredentialProvider().getUserName();

	}

	@Override
	public String getRepositoryUrl() {
		IRepositoryService service = RemoteActivator.getDefault()
				.getServiceTracker().getService(IRepositoryService.class);
		RemoteRepository repositoryById = service
				.getRepositoryById(getLocalRepositoryId());
		return repositoryById.getUrl();
	}

	private ModeshapeClient getApi() {
		if (api == null) {
			getCredentialProvider().setIdentifier(getLocalRepositoryId());

			IRepositoryService service = RemoteActivator.getDefault()
					.getServiceTracker().getService(IRepositoryService.class);
			RemoteRepository repositoryById = service
					.getRepositoryById(getLocalRepositoryId());

			api = new ModeshapeClient(getCredentialProvider().getUserName(),
					getCredentialProvider().getPassword(),
					repositoryById.getUrl());
			try {
				api.checkInitialization(REPOSITORY, getCredentialProvider()
						.getUserName());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			RemoteActivator.getDefault().getServiceTracker()
					.ungetService(service);
			getCredentialProvider().addPropertyChangeListener(
					credentialsMovedListener);

		}
		return api;
	}

	protected boolean isTransfer() {
		return false;
	}

}
