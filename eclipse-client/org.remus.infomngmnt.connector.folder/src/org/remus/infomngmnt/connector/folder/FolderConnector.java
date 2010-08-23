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

package org.remus.infomngmnt.connector.folder;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
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
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.remus.BinaryReference;
import org.eclipse.remus.Category;
import org.eclipse.remus.InfomngmntFactory;
import org.eclipse.remus.InfomngmntPackage;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.RemoteContainer;
import org.eclipse.remus.RemoteObject;
import org.eclipse.remus.SynchronizableObject;
import org.eclipse.remus.common.core.streams.FileUtil;
import org.eclipse.remus.common.core.streams.StreamCloser;
import org.eclipse.remus.common.core.streams.StreamUtil;
import org.eclipse.remus.common.core.util.ResourceUtil;
import org.eclipse.remus.core.extension.IInfoType;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.core.remote.AbstractExtensionRepository;
import org.eclipse.remus.core.remote.RemoteActivator;
import org.eclipse.remus.core.remote.RemoteException;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.core.services.IInformationTypeHandler;
import org.eclipse.remus.model.remote.ILoginCallBack;
import org.eclipse.remus.model.remote.IRepository;
import org.eclipse.remus.model.service.ResourceConstants;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.remus.util.StatusCreator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;


/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class FolderConnector extends AbstractExtensionRepository implements IRepository {

	public static final String FOLDER_PREFIX_CAT = "CAT_"; //$NON-NLS-1$
	public static final String FOLDER_PREFIX_INFO = "INFO_"; //$NON-NLS-1$
	public static final String FOLDER_NAME_BINARIES = ".binaries"; //$NON-NLS-1$
	public static final String FILENAME_CAT = "category.xml"; //$NON-NLS-1$
	private IEditingHandler editingService;

	/**
	 * 
	 */
	public FolderConnector() {
		BundleContext bundleContext = FolderActivator.getDefault().getBundle().getBundleContext();
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
	 * org.remus.infomngmnt.core.remote.IRepository#addToRepository(org.remus
	 * .infomngmnt.SynchronizableObject,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public RemoteObject addToRepository(final SynchronizableObject item,
			final IProgressMonitor monitor) throws RemoteException {
		RemoteObject parentRemoteCat = getRemoteObjectBySynchronizableObject(
				(SynchronizableObject) item.eContainer(), monitor);
		String url = parentRemoteCat.getUrl();
		if (item instanceof InformationUnitListItem) {
			try {
				InformationUnit adapter = (InformationUnit) item.getAdapter(InformationUnit.class);
				InformationUnit copy = (InformationUnit) EcoreUtil.copy(adapter);
				String newPath = new StringWriter().append(url).append(File.separator).append(
						FOLDER_PREFIX_INFO).append(adapter.getId()).toString();
				File file = new File(newPath);
				file.mkdirs();
				Resource res = new XMLResourceImpl(URI.createFileURI(new StringWriter().append(
						newPath).append(File.separator).append(copy.getId()).append(".info")
						.toString()));
				res.getContents().add(copy);
				this.editingService.saveObjectToResource(copy);
				res.unload();
				InformationStructureRead read = InformationStructureRead.newSession(adapter);
				List<BinaryReference> binaryReferences = read.getBinaryReferences();
				if (binaryReferences.size() > 0) {
					String binaryFolder = new StringWriter().append(newPath).append(File.separator)
							.append(FOLDER_NAME_BINARIES).toString();
					new File(binaryFolder).mkdirs();
					for (BinaryReference binaryReference : binaryReferences) {
						IFile binaryReferenceToFile = InformationUtil.binaryReferenceToFile(
								binaryReference, adapter);
						String projectRelativePath = binaryReference.getProjectRelativePath();
						String binaryRefFile = new StringWriter().append(binaryFolder).append(
								File.separator).append(projectRelativePath).toString();
						FileOutputStream fos = null;
						InputStream contents = null;
						try {
							fos = new FileOutputStream(binaryRefFile);
							contents = binaryReferenceToFile.getContents();
							StreamUtil.stream(contents, fos);
						} finally {
							if (fos != null) {
								fos.flush();
								fos.close();
							}
							if (contents != null) {
								StreamCloser.closeStreams(contents);
							}
						}

					}
				}
				return buildSingleInfoUnit(new File(newPath));
			} catch (Exception e) {
				throw new RemoteException(StatusCreator.newStatus(
						"Error adding element to repository", e));
			}
		}
		if (item instanceof Category) {
			Category copy = (Category) EcoreUtil.copy(item);
			copy.getChildren().clear();
			copy.getInformationUnit().clear();
			copy
					.eUnset(InfomngmntPackage.Literals.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA);
			String newPath = new StringWriter().append(url).append(File.separator).append(
					FOLDER_PREFIX_CAT).append(copy.getId()).toString();
			File file = new File(newPath);
			file.mkdirs();
			Resource res = new XMLResourceImpl(URI.createFileURI(new StringWriter().append(newPath)
					.append(File.separator).append(FILENAME_CAT).toString()));
			res.getContents().add(copy);
			this.editingService.saveObjectToResource(copy);
			return buildSingleCategory(new File(newPath));
		}
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
		RemoteObject remoteObject = getRemoteObjectBySynchronizableObject(item2commit, monitor);
		if (remoteObject != null) {
			if (item2commit instanceof InformationUnitListItem) {
				try {
					InformationUnit adapter = (InformationUnit) item2commit
							.getAdapter(InformationUnit.class);
					InformationUnit copy = (InformationUnit) EcoreUtil.copy(adapter);
					Resource res = new XMLResourceImpl();
					res.getContents().add(copy);
					String infoFile = new StringWriter().append(remoteObject.getUrl()).append(
							File.separator).append(remoteObject.getId()).append(".info").toString();
					FileOutputStream fileos = null;
					try {
						fileos = new FileOutputStream(infoFile);
						res.save(fileos, ResourceConstants.SAVE_OPTIONS);
					} finally {
						if (fileos != null) {
							fileos.flush();
							fileos.close();
						}
					}
					String binaryFolder = new StringWriter().append(remoteObject.getUrl()).append(
							File.separator).append(FOLDER_NAME_BINARIES).toString();
					File binaryRemoteFolder = new File(binaryFolder);
					InformationStructureRead read = InformationStructureRead.newSession(adapter);
					List<BinaryReference> binaryReferences = read.getBinaryReferences();
					if (binaryReferences.size() == 0) {
						if (binaryRemoteFolder.exists()) {
							binaryRemoteFolder.delete();
						}
					} else {
						if (binaryRemoteFolder.exists()) {
							File[] listFiles = binaryRemoteFolder.listFiles();
							for (File file : listFiles) {
								file.delete();
							}
						}
						for (BinaryReference binaryReference : binaryReferences) {
							IFile binaryReferenceToFile = InformationUtil.binaryReferenceToFile(
									binaryReference, adapter);
							String projectRelativePath = binaryReference.getProjectRelativePath();
							String binaryRefFile = new StringWriter().append(binaryFolder).append(
									File.separator).append(projectRelativePath).toString();
							FileOutputStream fos = null;
							InputStream contents = null;
							try {
								fos = new FileOutputStream(binaryRefFile);
								contents = binaryReferenceToFile.getContents();
								StreamUtil.stream(contents, fos);
							} finally {
								if (fos != null) {
									fos.flush();
									fos.close();
								}
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
				return buildSingleInfoUnit(new File(remoteObject.getUrl()));

			}
			if (item2commit instanceof Category) {
				Category copy = (Category) EcoreUtil.copy(item2commit);
				copy.getChildren().clear();
				copy.getInformationUnit().clear();
				copy
						.eUnset(InfomngmntPackage.Literals.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA);
				Resource res = new XMLResourceImpl();
				res.getContents().add(copy);
				String infoFile = new StringWriter().append(remoteObject.getUrl()).append(
						File.separator).append(FILENAME_CAT).toString();
				FileOutputStream fileos = null;
				try {
					fileos = new FileOutputStream(infoFile);
					res.save(fileos, ResourceConstants.SAVE_OPTIONS);
					res.unload();
				} catch (Exception e) {
					throw new RemoteException(StatusCreator.newStatus("Error committing category",
							e));
				} finally {
					if (fileos != null) {
						try {
							fileos.flush();
							fileos.close();
						} catch (IOException e) {
							// do nothing...we've done our best.
						}
					}
				}
				return buildSingleCategory(new File(remoteObject.getUrl()));
			}
		}
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
		File file = new File(item.getSynchronizationMetaData().getUrl());
		if (file.exists()) {
			try {
				FileUtil.delete(file);
			} catch (IOException e) {
				throw new RemoteException(StatusCreator.newStatus("Error deleting item", e));
			}

		} else {
			throw new RemoteException(StatusCreator.newStatus("Remote object was not found."));
		}

	}

	@Override
	public InformationUnit getPrefetchedInformationUnit(final RemoteObject remoteObject) {
		return (InformationUnit) remoteObject.getWrappedObject();
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
		List<RemoteObject> returnValue = new ArrayList<RemoteObject>();
		returnValue.addAll(buildSubFolders(container.getUrl()));
		if (!showOnlyContainers) {
			returnValue.addAll(buildItems(container.getUrl()));
		}
		return returnValue.toArray(new RemoteObject[returnValue.size()]);
	}

	private Collection<? extends RemoteObject> buildItems(final String url) {
		File file = new File(url);
		List<RemoteObject> returnValue = new ArrayList<RemoteObject>();
		File[] listFiles = file.listFiles(new FilenameFilter() {
			public boolean accept(final File arg0, final String arg1) {
				return arg1.startsWith(FOLDER_PREFIX_INFO);
			}
		});
		if (listFiles != null) {
			for (File file2 : listFiles) {
				RemoteObject buildSingleInfoUnit = buildSingleInfoUnit(file2);
				if (buildSingleInfoUnit != null) {
					returnValue.add(buildSingleInfoUnit);
				}
			}

		}
		return returnValue;
	}

	private RemoteObject buildSingleInfoUnit(final File file2) {
		if (file2.exists() && file2.isDirectory()) {
			File[] listInfo = file2.listFiles(new FilenameFilter() {
				public boolean accept(final File dir, final String name) {
					return name.endsWith(".info");
				}
			});
			if (listInfo.length == 1) {
				FileInputStream fileInputStream = null;
				BufferedInputStream bufferedInputStream = null;
				try {
					fileInputStream = new FileInputStream(listInfo[0]);
					bufferedInputStream = new BufferedInputStream(fileInputStream);
					IFile createTempFile = ResourceUtil.createTempFile();
					createTempFile.setContents(bufferedInputStream, true, false,
							new NullProgressMonitor());
					InformationUnit unit = this.editingService.getObjectFromFile(createTempFile,
							InfomngmntPackage.Literals.INFORMATION_UNIT, null);
					if (unit != null) {
						RemoteObject container = InfomngmntFactory.eINSTANCE.createRemoteObject();
						container.setName(unit.getLabel());
						container.setUrl(file2.getAbsolutePath());
						container.setWrappedObject(unit);
						container.setId(unit.getId());
						container.setHash(String.valueOf(listInfo[0].lastModified()));
						return container;
					}
				} catch (Exception e) {
					// return just null.
				} finally {
					StreamCloser.closeStreams(fileInputStream, bufferedInputStream);
				}
			}
		}
		return null;
	}

	private Collection<? extends RemoteObject> buildSubFolders(final String url) {
		File file = new File(url);
		List<RemoteObject> returnValue = new ArrayList<RemoteObject>();
		File[] listFiles = file.listFiles(new FilenameFilter() {
			public boolean accept(final File arg0, final String arg1) {
				return arg1.startsWith(FOLDER_PREFIX_CAT);
			}
		});
		if (listFiles != null) {
			for (File file2 : listFiles) {
				RemoteContainer buildSingleCategory = buildSingleCategory(file2);
				if (buildSingleCategory != null) {
					returnValue.add(buildSingleCategory);
				}
			}
		}
		return returnValue;
	}

	private RemoteContainer buildSingleCategory(final File remoteFolder) {
		if (remoteFolder.exists() && remoteFolder.isDirectory()) {
			File[] listCat = remoteFolder.listFiles(new FilenameFilter() {
				public boolean accept(final File dir, final String name) {
					return FILENAME_CAT.equals(name);
				}
			});
			if (listCat.length == 1) {
				FileInputStream fileInputStream = null;
				BufferedInputStream bufferedInputStream = null;
				try {
					fileInputStream = new FileInputStream(listCat[0]);
					bufferedInputStream = new BufferedInputStream(fileInputStream);
					IFile createTempFile = ResourceUtil.createTempFile();
					createTempFile.setContents(bufferedInputStream, true, false,
							new NullProgressMonitor());
					Category unit = this.editingService.getObjectFromFile(createTempFile,
							InfomngmntPackage.Literals.CATEGORY, null);
					if (unit != null) {
						RemoteContainer container = InfomngmntFactory.eINSTANCE
								.createRemoteContainer();
						container.setName(unit.getLabel());
						container.setUrl(remoteFolder.getAbsolutePath());
						container.setWrappedObject(unit);
						container.setId(unit.getId());
						container.setHash(String.valueOf(listCat[0].lastModified()));
						return container;
					}
				} catch (Exception e) {
					e.printStackTrace();
					// return just null.
				} finally {
					StreamCloser.closeStreams(fileInputStream, bufferedInputStream);
				}

			}
		}
		return null;
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
	public IFile getBinaryReferences(final InformationUnitListItem remoteObject,
			final InformationUnit localInfoFragment, final IProgressMonitor monitor)
			throws RemoteException {
		if (localInfoFragment.getBinaryReferences() != null) {
			String url = remoteObject.getSynchronizationMetaData().getUrl();
			String remoteFilePath = new StringWriter().append(url).append(File.separator).append(
					FOLDER_NAME_BINARIES).append(File.separator).append(
					localInfoFragment.getBinaryReferences().getProjectRelativePath()).toString();
			File remoteReference = new File(remoteFilePath);
			if (remoteReference.exists() && remoteReference.isFile()) {
				IFile createTempFile = ResourceUtil.createTempFile(new Path(remoteReference
						.getAbsolutePath()).getFileExtension());
				FileInputStream fileInputStream = null;
				BufferedInputStream bufferedInputStream = null;
				try {
					fileInputStream = new FileInputStream(remoteReference);
					bufferedInputStream = new BufferedInputStream(fileInputStream);
					createTempFile.setContents(bufferedInputStream, true, false,
							new SubProgressMonitor(monitor, IProgressMonitor.UNKNOWN));
					return createTempFile;
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					StreamCloser.closeStreams(fileInputStream, bufferedInputStream);
				}
			}
		}
		return null;

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
		// validate whether Directory exists, otherwise access is not possible
		if (validate(url).isOK()){
			if (getRepositoryById(getLocalRepositoryId()).getUrl().equals(url)) {
				return getRepositoryById(getLocalRepositoryId());
			}
			if (object instanceof Category) {
				return buildSingleCategory(new File(url));
			}
			if (object instanceof InformationUnitListItem) {
				return buildSingleInfoUnit(new File(url));
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

	@Override
	public boolean hasBinaryReferences() {
		return true;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.core.remote.IRepository#validate()
	 */
	public IStatus validate(final String url) {
		File file = new File(url);
		if (file.exists() && file.isDirectory() && file.canWrite()) {
			return Status.OK_STATUS;
		}
		return StatusCreator.newStatus("Not a valid directory: " + url);
	}
}
