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
package org.remus.infomngmnt.connector.dropbox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.remus.BinaryReference;
import org.eclipse.remus.Category;
import org.eclipse.remus.InfomngmntFactory;
import org.eclipse.remus.InfomngmntPackage;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.RemoteContainer;
import org.eclipse.remus.RemoteObject;
import org.eclipse.remus.SynchronizableObject;
import org.eclipse.remus.common.core.streams.StreamCloser;
import org.eclipse.remus.common.core.util.IdFactory;
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
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.remus.util.StatusCreator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.dropbox.client.Authenticator;
import com.dropbox.client.DropboxClient;
import com.dropbox.client.DropboxException;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DropboxConnector extends AbstractExtensionRepository implements
		IRepository {

	private static final String MODIFIED = "modified"; //$NON-NLS-1$
	private static final String PATH_NODE = "path"; //$NON-NLS-1$
	private static final String DATE_PATTERN = "EEE, dd MMM yyyy HH:mm:ss Z"; //$NON-NLS-1$
	private static final String IS_DIR = "is_dir"; //$NON-NLS-1$
	private static final String CONTENTS_NODE = "contents"; //$NON-NLS-1$
	private static final String DROPBOX = "dropbox"; //$NON-NLS-1$
	public static final String FOLDER_PREFIX_CAT = "CAT_"; //$NON-NLS-1$
	public static final String FOLDER_PREFIX_INFO = "INFO_"; //$NON-NLS-1$
	public static final String FOLDER_NAME_BINARIES = ".binaries"; //$NON-NLS-1$
	public static final String FILENAME_CAT = "category.xml"; //$NON-NLS-1$

	private final PropertyChangeListener credentialsMovedListener = new PropertyChangeListener() {
		public void propertyChange(final PropertyChangeEvent evt) {
			reset();
		}
	};
	private DropboxClient api;
	private IEditingHandler editingService;

	public DropboxConnector() {
		BundleContext bundleContext = DropboxActivator.getDefault().getBundle()
				.getBundleContext();
		ServiceReference serviceReference = bundleContext
				.getServiceReference(IEditingHandler.class.getName());
		if (serviceReference != null) {
			editingService = (IEditingHandler) bundleContext
					.getService(serviceReference);
		}
	}

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

	private static Date parseDate(String str) {
		if (str != null) {
			try {
				return new java.text.SimpleDateFormat(DATE_PATTERN,
						java.util.Locale.ENGLISH).parse(str);
			} catch (ParseException e) {
				//
			}
		}
		return new Date(0);

	}

	public String getTypeIdByObject(RemoteObject remoteObject) {
		Object wrappedObject = remoteObject.getWrappedObject();
		if (wrappedObject instanceof InformationUnit) {
			String type = ((InformationUnit) wrappedObject).getType();
			IInformationTypeHandler service = RemoteActivator.getDefault()
					.getServiceTracker()
					.getService(IInformationTypeHandler.class);
			IInfoType infoTypeByType = service.getInfoTypeByType(type);
			RemoteActivator.getDefault().getServiceTracker()
					.ungetService(service);
			if (infoTypeByType != null) {
				return type;
			}
		}
		return null;
	}

	private Collection<? extends RemoteObject> buildSubFolders(final String url)
			throws RemoteException {
		List<RemoteObject> returnValue = new ArrayList<RemoteObject>();
		try {
			Map metadata = getApi().metadata(DROPBOX, url, 10000, null, true,
					false, null);
			if (metadata.get(CONTENTS_NODE) != null
					&& metadata.get(CONTENTS_NODE) instanceof JSONArray) {
				JSONArray array = (JSONArray) metadata.get(CONTENTS_NODE);
				for (Object object : array) {
					if (object instanceof JSONObject
							&& ((JSONObject) object).get(PATH_NODE) != null) {
						String pathAttribute = (String) ((JSONObject) object)
								.get(PATH_NODE);
						if (new Path(pathAttribute).lastSegment().startsWith(
								FOLDER_PREFIX_CAT)) {
							RemoteContainer buildSingleCategory = buildSingleCategory((JSONObject) object);
							if (buildSingleCategory != null) {
								returnValue.add(buildSingleCategory);
							}
						}

					}
				}

			}
		} catch (DropboxException e) {
			throw new RemoteException(StatusCreator.newStatus(
					Messages.DropboxConnector_ErrorLoadingSubfolders, e));
		}
		return returnValue;
	}

	private RemoteContainer buildSingleCategory(Map object)
			throws RemoteException {
		if ((Boolean) object.get(IS_DIR)) {
			Map metadata;
			try {
				metadata = getApi().metadata(DROPBOX,
						(String) object.get(PATH_NODE), 10000, null, true,
						false, null);
			} catch (Exception e) {
				throw new RemoteException(StatusCreator.newStatus(
						Messages.DropboxConnector_ErrorBuildingSingleCategory,
						e));
			}
			if (metadata.get(CONTENTS_NODE) != null
					&& metadata.get(CONTENTS_NODE) instanceof JSONArray) {
				JSONArray array = (JSONArray) metadata.get(CONTENTS_NODE);
				for (Object object2 : array) {
					if (object2 instanceof JSONObject
							&& ((JSONObject) object2).get(PATH_NODE) != null) {
						String pathAttribute = (String) ((JSONObject) object2)
								.get(PATH_NODE);
						if (FILENAME_CAT.equals(new Path(pathAttribute)
								.lastSegment())) {
							IFile createTempFile = null;
							try {
								HttpResponse file = getApi().getFile(DROPBOX,
										pathAttribute);
								InputStream content = file.getEntity()
										.getContent();
								createTempFile = ResourceUtil.createTempFile();
								createTempFile.setContents(content, true,
										false, new NullProgressMonitor());
							} catch (Exception e) {
								throw new RemoteException(
										StatusCreator
												.newStatus(
														Messages.DropboxConnector_ErrorBuildingSingleCategory,
														e));
							}
							Category unit = editingService.getObjectFromFile(
									createTempFile,
									InfomngmntPackage.Literals.CATEGORY, null);
							if (unit != null) {
								RemoteContainer container = InfomngmntFactory.eINSTANCE
										.createRemoteContainer();
								container.setName(unit.getLabel());
								container
										.setUrl((String) object.get(PATH_NODE));
								container.setWrappedObject(unit);
								container.setId(unit.getId());
								container
										.setHash((String) object.get(MODIFIED));
								return container;
							}
							if (createTempFile != null
									&& createTempFile.exists()) {
								try {
									createTempFile.delete(false,
											new NullProgressMonitor());
								} catch (CoreException e) {
									// skip that
								}
							}
						}

					}
				}

			}
		}
		return null;
	}

	private Collection<? extends RemoteObject> buildItems(String url)
			throws RemoteException {
		List<RemoteObject> returnValue = new ArrayList<RemoteObject>();
		try {
			Map metadata = getApi().metadata(DROPBOX, url, 10000, null, true,
					false, null);
			if (metadata.get(CONTENTS_NODE) != null
					&& metadata.get(CONTENTS_NODE) instanceof JSONArray) {
				JSONArray array = (JSONArray) metadata.get(CONTENTS_NODE);
				for (Object object : array) {
					if (object instanceof JSONObject
							&& ((JSONObject) object).get(PATH_NODE) != null) {
						String pathAttribute = (String) ((JSONObject) object)
								.get(PATH_NODE);
						if (new Path(pathAttribute).lastSegment().startsWith(
								FOLDER_PREFIX_INFO)) {
							RemoteObject buildSingleInfoUnit = buildSingleInfoUnit((JSONObject) object);
							if (buildSingleInfoUnit != null) {
								returnValue.add(buildSingleInfoUnit);
							}
						}

					}
				}

			}
		} catch (DropboxException e) {
			throw new RemoteException(StatusCreator.newStatus(
					Messages.DropboxConnector_ErrorLoadingSubfolders, e));
		}
		return returnValue;
	}

	private Map getResourceFromUrl(final String url, boolean listing)
			throws RemoteException {
		try {

			return getApi().metadata(DROPBOX, url, 10000, null, listing, false,
					null);
		} catch (Exception e) {
			throw new RemoteException(StatusCreator.newStatus(
					Messages.DropboxConnector_ErrorResolving, e));
		}
	}

	@Override
	public InformationUnit getPrefetchedInformationUnit(
			final RemoteObject remoteObject) {
		return (InformationUnit) remoteObject.getWrappedObject();
	}

	private RemoteObject buildSingleInfoUnit(Map object) throws RemoteException {
		if ((Boolean) object.get(IS_DIR)) {
			Map metadata;
			try {
				metadata = getApi().metadata(DROPBOX,
						(String) object.get(PATH_NODE), 10000, null, true,
						false, null);
			} catch (Exception e) {
				throw new RemoteException(StatusCreator.newStatus(
						Messages.DropboxConnector_ErrorBuildingSingleCategory,
						e));
			}
			if (metadata.get(CONTENTS_NODE) != null
					&& metadata.get(CONTENTS_NODE) instanceof JSONArray) {
				JSONArray array = (JSONArray) metadata.get(CONTENTS_NODE);
				for (Object object2 : array) {
					if (object2 instanceof JSONObject
							&& ((JSONObject) object2).get(PATH_NODE) != null) {
						String pathAttribute = (String) ((JSONObject) object2)
								.get(PATH_NODE);
						if (new Path(pathAttribute).lastSegment().endsWith(
								".info")) { //$NON-NLS-1$
							IFile createTempFile = null;
							InputStream content = null;
							try {
								HttpResponse file = getApi().getFile(DROPBOX,
										pathAttribute);
								content = file.getEntity().getContent();
								createTempFile = ResourceUtil.createTempFile();
								createTempFile.setContents(content, true,
										false, new NullProgressMonitor());
							} catch (Exception e) {
								throw new RemoteException(
										StatusCreator
												.newStatus(
														Messages.DropboxConnector_ErrorBuildingSingleCategory,
														e));
							} finally {
								StreamCloser.closeStreams(content);
							}
							InformationUnit unit = editingService
									.getObjectFromFile(
											createTempFile,
											InfomngmntPackage.Literals.INFORMATION_UNIT,
											null);
							if (unit != null) {
								RemoteObject container = InfomngmntFactory.eINSTANCE
										.createRemoteObject();
								container.setName(unit.getLabel());
								container
										.setUrl((String) object.get(PATH_NODE));
								container.setWrappedObject(unit);
								container.setId(unit.getId());
								container
										.setHash((String) object.get(MODIFIED));
								return container;
							}
							if (createTempFile != null
									&& createTempFile.exists()) {
								try {
									createTempFile.delete(false,
											new NullProgressMonitor());
								} catch (CoreException e) {
									// skip that
								}
							}
						}

					}
				}

			}
		}
		return null;
	}

	private String getExistingInfoFile(String url) throws RemoteException {
		Map metadata;
		try {
			metadata = getApi().metadata(DROPBOX, url, 10000, null, true,
					false, null);
		} catch (Exception e) {
			throw new RemoteException(StatusCreator.newStatus(
					Messages.DropboxConnector_ErrorBuildingSingleCategory, e));
		}
		if (metadata.get(CONTENTS_NODE) != null
				&& metadata.get(CONTENTS_NODE) instanceof JSONArray) {
			JSONArray array = (JSONArray) metadata.get(CONTENTS_NODE);
			for (Object object2 : array) {
				if (object2 instanceof JSONObject
						&& ((JSONObject) object2).get(PATH_NODE) != null) {
					String pathAttribute = (String) ((JSONObject) object2)
							.get(PATH_NODE);
					if (new Path(pathAttribute).lastSegment().endsWith(".info")) { //$NON-NLS-1$
						return new Path(pathAttribute).lastSegment();
					}

				}
			}
		}
		return null;
	}

	@Override
	public IFile getBinaryReferences(
			final InformationUnitListItem remoteObject,
			final InformationUnit localInfoFragment,
			final IProgressMonitor monitor) throws RemoteException {
		if (localInfoFragment.getBinaryReferences() != null) {
			String url = remoteObject.getSynchronizationMetaData().getUrl();
			String remoteFilePath = new StringWriter()
					.append(url)
					.append("/") //$NON-NLS-1$
					.append(FOLDER_NAME_BINARIES)
					.append("/").append( //$NON-NLS-1$
							localInfoFragment.getBinaryReferences()
									.getProjectRelativePath()).toString();
			try {
				Map resourceFromUrl = getResourceFromUrl(remoteFilePath, false);

				if (resourceFromUrl != null
						&& !((Boolean) resourceFromUrl.get(IS_DIR))) {
					IFile createTempFile = ResourceUtil
							.createTempFile(new Path((String) resourceFromUrl
									.get(PATH_NODE)).getFileExtension());
					InputStream fileInputStream = null;

					try {
						HttpResponse file = getApi().getFile(DROPBOX,
								(String) resourceFromUrl.get(PATH_NODE));
						fileInputStream = file.getEntity().getContent();
						createTempFile.setContents(fileInputStream, true,
								false, new SubProgressMonitor(monitor,
										IProgressMonitor.UNKNOWN));
						return createTempFile;
					} finally {
						StreamCloser.closeStreams(fileInputStream);
					}
				}
			} catch (Exception e) {
				throw new RemoteException(
						StatusCreator
								.newStatus(
										Messages.DropboxConnector_ErrorResolvingBinaryReference,
										e));
			}
		}
		return null;

	}

	public InformationUnit getFullObject(
			InformationUnitListItem informationUnitListItem,
			IProgressMonitor monitor) throws RemoteException {
		// not relevant
		return null;
	}

	public RemoteObject commit(SynchronizableObject item2commit,
			IProgressMonitor monitor) throws CoreException {
		RemoteObject remoteObject = getRemoteObjectBySynchronizableObject(
				item2commit, monitor);
		if (remoteObject != null) {
			if (item2commit instanceof InformationUnitListItem) {
				try {
					InformationUnit adapter = (InformationUnit) item2commit
							.getAdapter(InformationUnit.class);
					// We have to check for the presence of a file
					String existingInfoFile = getExistingInfoFile(remoteObject
							.getUrl() + "/"); //$NON-NLS-1$
					if (existingInfoFile == null) {
						existingInfoFile = remoteObject.getId() + ".info"; //$NON-NLS-1$
					}
					String infoFile = new StringWriter()
							.append(remoteObject.getUrl()).append("/") //$NON-NLS-1$
							.append(existingInfoFile).toString();
					File file = ((IFile) adapter.getAdapter(IFile.class))
							.getLocation().toFile();
					getApi().putFile(DROPBOX, infoFile, file);
					String binaryFolder = new StringWriter()
							.append(remoteObject.getUrl())
							.append("/") //$NON-NLS-1$
							.append(FOLDER_NAME_BINARIES)
							.append("/").toString(); //$NON-NLS-1$

					InformationStructureRead read = InformationStructureRead
							.newSession(adapter);
					List<BinaryReference> binaryReferences = read
							.getBinaryReferences();
					try {
						Map resourceFromUrl = getResourceFromUrl(binaryFolder,
								false);
						if ((Boolean) resourceFromUrl.get(IS_DIR)) {
							getApi().fileDelete(DROPBOX, binaryFolder, null);
						}
					} catch (Exception e) {
						// do nothing.. we probably have no binary reference
						// folder...
					}
					if (binaryReferences.size() > 0) {
						getApi().fileCreateFolder(DROPBOX, binaryFolder, null);
						for (BinaryReference binaryReference : binaryReferences) {
							IFile binaryReferenceToFile = InformationUtil
									.binaryReferenceToFile(binaryReference,
											adapter);
							String projectRelativePath = binaryReference
									.getProjectRelativePath();
							String binaryRefFile = new StringWriter()
									.append(binaryFolder)
									.append("/").append(projectRelativePath).toString(); //$NON-NLS-1$

							try {
								File file2 = binaryReferenceToFile
										.getLocation().toFile();
								getApi().putFile(DROPBOX, binaryRefFile, file2);
							} finally {

							}
						}
					}
				} catch (Exception e) {
					throw new RemoteException(
							StatusCreator
									.newStatus(Messages.DropboxConnector_ErrorCommittingElement));
				}
				Map resourceFromUrl = getResourceFromUrl(remoteObject.getUrl(),
						true);
				if (resourceFromUrl != null) {
					return buildSingleInfoUnit(resourceFromUrl);
				}

			}
			if (item2commit instanceof Category) {
				Category copy = (Category) EcoreUtil.copy(item2commit);
				copy.getChildren().clear();
				copy.getInformationUnit().clear();
				copy.eUnset(InfomngmntPackage.Literals.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA);
				String infoFile = new StringWriter()
						.append(remoteObject.getUrl()).append("/") //$NON-NLS-1$
						.append(FILENAME_CAT).toString();
				File file = ResourceUtil.createTempFileOnFileSystem();
				try {
					byte[] saveObjectToByte = editingService
							.saveObjectToByte(copy);
					FileUtils.writeByteArrayToFile(file, saveObjectToByte);
					getApi().putFile(DROPBOX, infoFile, file);
				} catch (Exception e) {
					throw new RemoteException(
							StatusCreator
									.newStatus(
											Messages.DropboxConnector_ErrorComittingCategory,
											e));
				} finally {
					file.delete();
				}
				Map resourceFromUrl = getResourceFromUrl(remoteObject.getUrl(),
						true);
				if (resourceFromUrl != null) {
					return buildSingleCategory(resourceFromUrl);
				}
			}
		}
		return null;
	}

	public RemoteObject addToRepository(SynchronizableObject item,
			IProgressMonitor monitor) throws RemoteException {
		RemoteObject parentRemoteCat = getRemoteObjectBySynchronizableObject(
				(SynchronizableObject) item.eContainer(), monitor);
		String url = parentRemoteCat.getUrl();
		if (item instanceof InformationUnitListItem) {
			try {
				InformationUnit adapter = (InformationUnit) item
						.getAdapter(InformationUnit.class);

				String newPath = new StringWriter().append(url).append("/") //$NON-NLS-1$
						.append(FOLDER_PREFIX_INFO).append(adapter.getId())
						.append("/").toString(); //$NON-NLS-1$
				getApi().fileCreateFolder(DROPBOX, newPath, null);
				String remotePathName = new StringWriter().append(newPath)
						.append(adapter.getId()).append(".info").toString(); //$NON-NLS-1$
				File file = ((IFile) adapter.getAdapter(IFile.class))
						.getLocation().toFile();
				getApi().putFile(DROPBOX, remotePathName, file);
				InformationStructureRead read = InformationStructureRead
						.newSession(adapter);
				List<BinaryReference> binaryReferences = read
						.getBinaryReferences();
				if (binaryReferences.size() > 0) {
					String binaryFolder = new StringWriter().append(newPath)
							.append(FOLDER_NAME_BINARIES).toString();
					getApi().fileCreateFolder(DROPBOX, binaryFolder, null);
					for (BinaryReference binaryReference : binaryReferences) {
						IFile binaryReferenceToFile = InformationUtil
								.binaryReferenceToFile(binaryReference, adapter);
						String projectRelativePath = binaryReference
								.getProjectRelativePath();
						String binaryRefFile = new StringWriter()
								.append(binaryFolder).append("/") //$NON-NLS-1$
								.append(projectRelativePath).toString();
						InputStream contents = null;
						try {
							getApi().putFile(
									DROPBOX,
									binaryRefFile,
									binaryReferenceToFile.getLocation()
											.toFile());
						} finally {
							if (contents != null) {
								StreamCloser.closeStreams(contents);
							}
						}

					}
				}
				Map resourceFromUrl = getResourceFromUrl(newPath, true);
				if (resourceFromUrl != null) {
					return buildSingleInfoUnit(resourceFromUrl);
				}
			} catch (Exception e) {
				throw new RemoteException(StatusCreator.newStatus(
						Messages.DropboxConnector_ErrorAddingElement, e));
			}
		}
		if (item instanceof Category) {
			try {
				Category copy = (Category) EcoreUtil.copy(item);
				copy.getChildren().clear();
				copy.getInformationUnit().clear();
				copy.eUnset(InfomngmntPackage.Literals.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA);
				String newPath = new StringWriter().append(url).append("/") //$NON-NLS-1$
						.append(FOLDER_PREFIX_CAT).append(copy.getId())
						.append("/").toString(); //$NON-NLS-1$
				boolean exists = false;
				try {
					getApi().metadata(DROPBOX, newPath, 1000, null, false,
							false, null);
					exists = true;

				} catch (DropboxException e) {

				}
				if (exists) {
					newPath = new StringWriter()
							.append(url)
							.append("/") //$NON-NLS-1$
							.append(FOLDER_PREFIX_CAT)
							.append(IdFactory.createId())
							.append("/").toString(); //$NON-NLS-1$
				}
				getApi().fileCreateFolder(DROPBOX, newPath, null);
				String newRemoteCatPath = new StringWriter().append(newPath)
						.append(FILENAME_CAT).toString();
				byte[] saveObjectToByte = editingService.saveObjectToByte(copy);
				File tmpFile = ResourceUtil.createTempFileOnFileSystem();
				try {
					FileUtils.writeByteArrayToFile(tmpFile, saveObjectToByte);
					getApi().putFile(DROPBOX, newRemoteCatPath, tmpFile);
					Map resourceFromUrl = getResourceFromUrl(newPath, true);
					if (resourceFromUrl != null) {
						return buildSingleCategory(resourceFromUrl);
					}
				} finally {
					tmpFile.delete();
				}
			} catch (DropboxException e) {
				throw new RemoteException(StatusCreator.newStatus(
						Messages.DropboxConnector_ErrorAddingCategory, e));
			} catch (IOException e) {
				throw new RemoteException(StatusCreator.newStatus(
						Messages.DropboxConnector_ErrorAddingCategory, e));
			}
		}
		return null;
	}

	public void deleteFromRepository(SynchronizableObject item,
			IProgressMonitor monitor) throws CoreException {
		try {
			getApi().fileDelete(DROPBOX,
					item.getSynchronizationMetaData().getUrl(), null);
		} catch (DropboxException e) {
			throw new RemoteException(StatusCreator.newStatus(
					Messages.DropboxConnector_ErrorDeleing, e));
		}

	}

	public RemoteObject getRemoteObjectBySynchronizableObject(
			SynchronizableObject object, IProgressMonitor monitor)
			throws RemoteException {
		String url = object.getSynchronizationMetaData().getUrl();
		if (getRepositoryById(getLocalRepositoryId()).getUrl().equals(url)) {
			return getRepositoryById(getLocalRepositoryId());
		}
		Map resourceFromUrl = getResourceFromUrl(url, true);
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

	public DropboxClient getApi() throws RemoteException {
		if (api == null) {
			getCredentialProvider().setIdentifier(getLocalRepositoryId());
			HashMap<String, String> properties = new HashMap<String, String>(
					DropboxActivator.CONNECTION_PROPERTIES);
			properties.put("access_token_key", getCredentialProvider() //$NON-NLS-1$
					.getUserName());
			properties.put("access_token_secret", getCredentialProvider() //$NON-NLS-1$
					.getPassword());
			Authenticator authenticator = null;
			try {
				authenticator = new Authenticator(properties);
			} catch (Exception e) {
				throw new RemoteException(StatusCreator.newStatus(
						Messages.DropboxConnector_ErrorConnecting, e));
			}
			api = new DropboxClient(DropboxActivator.CONNECTION_PROPERTIES,
					authenticator);
			// we're creating always the remus folder here.
			try {
				api.fileCreateFolder(DROPBOX, "/Remus", null); //$NON-NLS-1$
			} catch (DropboxException e) {
				// we skipt that..exists already
			}
			getCredentialProvider().addPropertyChangeListener(
					credentialsMovedListener);
		}
		return api;
	}

}
