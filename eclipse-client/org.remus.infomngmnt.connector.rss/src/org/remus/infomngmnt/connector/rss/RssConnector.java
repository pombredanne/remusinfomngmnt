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

package org.remus.infomngmnt.connector.rss;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.cyberneko.html.parsers.DOMParser;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.ecf.core.ContainerCreateException;
import org.eclipse.ecf.core.ContainerFactory;
import org.eclipse.ecf.core.IContainer;
import org.eclipse.ecf.core.security.ConnectContextFactory;
import org.eclipse.ecf.filetransfer.IRetrieveFileTransferContainerAdapter;
import org.eclipse.remus.InfomngmntFactory;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.RemoteContainer;
import org.eclipse.remus.RemoteObject;
import org.eclipse.remus.RemoteRepository;
import org.eclipse.remus.SynchronizableObject;
import org.eclipse.remus.common.core.streams.StreamCloser;
import org.eclipse.remus.common.core.util.ResourceUtil;
import org.eclipse.remus.common.io.transfer.DownloadFileJob;
import org.eclipse.remus.core.commands.CommandFactory;
import org.eclipse.remus.core.commands.CreateBinaryReferenceCommand;
import org.eclipse.remus.core.edit.DisposableEditingDomain;
import org.eclipse.remus.core.model.InformationStructureEdit;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.core.remote.AbstractExtensionRepository;
import org.eclipse.remus.core.remote.RemoteActivator;
import org.eclipse.remus.core.remote.RemoteException;
import org.eclipse.remus.core.remote.services.IRepositoryService;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.model.remote.ILoginCallBack;
import org.eclipse.remus.model.remote.IRepository;
import org.eclipse.remus.util.StatusCreator;
import org.remus.infomngmnt.mail.ContentType;
import org.remus.infomngmnt.mail.MailActivator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RssConnector extends AbstractExtensionRepository implements
		IRepository {

	private SyndFeedInput api;

	private final PropertyChangeListener credentialsMovedListener = new PropertyChangeListener() {
		public void propertyChange(final PropertyChangeEvent evt) {
			reset();
		}
	};

	private IContainer container;

	private IRetrieveFileTransferContainerAdapter fileReceiveAdapter;

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
	public void deleteFromRepository(final SynchronizableObject item,
			final IProgressMonitor monitor) throws RemoteException {
		throw new RemoteException(
				StatusCreator.newStatus(Messages.RssConnector_NotSupported));
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
		if (container instanceof RemoteRepository && !showOnlyContainers) {
			List<RemoteObject> returnValue = new ArrayList<RemoteObject>();
			InputStream xmlReader = null;
			InputSource is = null;
			try {
				File tempFile = ResourceUtil.createTempFileOnFileSystem();
				DownloadFileJob downloadFileJob = new DownloadFileJob(new URL(
						getRepositoryUrl()), tempFile, getFileReceiveAdapter());
				downloadFileJob.run(new SubProgressMonitor(monitor,
						IProgressMonitor.UNKNOWN));
				FileInputStream fileInputStream = new FileInputStream(tempFile);
				is = new InputSource(fileInputStream);
				SyndFeed build = getApi().build(is);
				List entries = build.getEntries();
				for (Object object : entries) {
					returnValue.add(buildFeedEntry((SyndEntry) object));
				}
				StreamCloser.closeStreams(fileInputStream);
				tempFile.delete();

				return returnValue
						.toArray(new RemoteObject[returnValue.size()]);
			} catch (Exception e) {
				throw new RemoteException(StatusCreator.newStatus(
						Messages.RssConnector_ErrorLoading, e));
			} finally {
				StreamCloser.closeStreams(xmlReader);
				is = null;
			}
		}
		return new RemoteObject[0];
	}

	private RemoteObject buildFeedEntry(final SyndEntry object) {
		RemoteObject returnValue = InfomngmntFactory.eINSTANCE
				.createRemoteObject();
		if (object.getPublishedDate() != null) {
			returnValue.setHash(String.valueOf(object.getPublishedDate()
					.getTime()));
		} else {
			returnValue.setHash(object.getUri());
		}
		returnValue.setUrl(object.getUri());
		returnValue.setId(object.getUri());
		returnValue.setWrappedObject(object);
		returnValue.setName(StringUtils.remove(object.getTitle(), "\n")); //$NON-NLS-1$
		return returnValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.IRepository#getFullObject(org.remus.
	 * infomngmnt.InformationUnitListItem,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public InformationUnit getFullObject(
			final InformationUnitListItem informationUnitListItem,
			final IProgressMonitor monitor) throws RemoteException {
		// TODO Auto-generated method stub
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
	public RemoteObject getRemoteObjectBySynchronizableObject(
			final SynchronizableObject object, final IProgressMonitor monitor)
			throws RemoteException {
		String url = object.getSynchronizationMetaData().getUrl();
		if (url.equals(getRepositoryUrl())) {
			return getRepositoryById(object.getSynchronizationMetaData()
					.getRepositoryId());
		} else {
			RemoteRepository repositoryById = getRepositoryById(object
					.getSynchronizationMetaData().getRepositoryId());
			RemoteObject[] children = getChildren(monitor, repositoryById,
					false);
			for (RemoteObject remoteObject : children) {
				if (remoteObject.getUrl().equals(url)) {
					return remoteObject;
				}
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.core.remote.IRepository#getRepositoryUrl()
	 */
	@Override
	public String getRepositoryUrl() {
		return getRepositoryById(getLocalRepositoryId()).getUrl();
	}

	public String getFeedTitle() {
		XmlReader xmlReader = null;
		try {
			xmlReader = new XmlReader(new URL(getRepositoryUrl()));
			return getApi().build(xmlReader).getTitle();
		} catch (Exception e) {

		} finally {
			if (xmlReader != null) {
				try {
					xmlReader.close();
				} catch (IOException e) {
					// do nothing
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
		return MailActivator.INFO_TYPE_ID;
	}

	@Override
	public InformationUnit getPrefetchedInformationUnit(
			final RemoteObject remoteObject) {
		Object wrappedObject = remoteObject.getWrappedObject();
		if (wrappedObject instanceof SyndEntry) {
			SyndEntry entry = (SyndEntry) wrappedObject;
			DisposableEditingDomain domain = RemoteActivator.getDefault()
					.getServiceTracker().getService(IEditingHandler.class)
					.createNewEditingDomain();
			InformationStructureEdit edit = InformationStructureEdit
					.newSession(MailActivator.INFO_TYPE_ID, domain);
			InformationUnit newInformationUnit = edit.newInformationUnit();
			newInformationUnit.setLabel(entry.getTitle());
			edit.setValue(newInformationUnit, MailActivator.NODE_NAME_RECEIVED,
					entry.getPublishedDate());
			edit.setValue(newInformationUnit, MailActivator.INFO_TYPE_ID,
					entry.getTitle());
			edit.setValue(newInformationUnit,
					MailActivator.NODE_NAME_CONTENT_TYPE,
					ContentType.HTML.getKey());
			edit.setValue(newInformationUnit, MailActivator.NODE_NAME_SENDER,
					entry.getAuthor());
			edit.setValue(newInformationUnit,
					MailActivator.NODE_NAME_MORE_INFO, entry.getLink());
			List contents = entry.getContents();
			if (contents.size() > 0 && contents.get(0) != null
					&& contents.get(0) instanceof SyndContent) {
				SyndContent object = (SyndContent) contents.get(0);
				edit.setValue(newInformationUnit,
						MailActivator.NODE_NAME_CONTENT, object.getValue());
			} else if (entry.getDescription() != null) {
				edit.setValue(newInformationUnit,
						MailActivator.NODE_NAME_CONTENT, entry.getDescription()
								.getValue());
			}
			List categories = entry.getCategories();
			List<String> cats = new ArrayList<String>();
			for (Object object : categories) {
				cats.add(((SyndCategory) object).getName());
			}
			String join = org.apache.commons.lang.StringUtils.join(cats, " "); //$NON-NLS-1$
			newInformationUnit.setKeywords(join);
			try {
				domain.dispose();
			} catch (Exception e) {
				// skip
			}
			return newInformationUnit;
		}
		return null;

	}

	/**
	 * After we have created the new information unit we grab all images, etc
	 * for offline-reading.
	 */
	@Override
	public boolean proceedLocalInformationUnitAfterSync(
			final InformationUnit newOrUpdatedLocalInformationUnit,
			final IProgressMonitor monitor) {
		return parseContent(newOrUpdatedLocalInformationUnit, monitor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.IRepository#login(org.remus.infomngmnt
	 * .core.remote.ILoginCallBack, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void login(final ILoginCallBack callback,
			final IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.core.remote.IRepository#reset()
	 */
	public void reset() {
		api = null;
		container = null;
		getCredentialProvider().removePropertyChangeListener(
				credentialsMovedListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.core.remote.IRepository#validate()
	 */
	@Override
	public IStatus validate() {
		// TODO Auto-generated method stub
		return null;
	}

	private synchronized SyndFeedInput getApi() {
		/*
		 * We have to do this, else the SynndFeedInput is eating more and more
		 * memory and we end up with OutOfMemoryError :(
		 */
		if (api != null) {
			reset();
		}
		getCredentialProvider().addPropertyChangeListener(
				credentialsMovedListener);
		api = new SyndFeedInput();

		return api;
	}

	private boolean parseContent(final InformationUnit unit,
			final IProgressMonitor monitor) {
		IEditingHandler service = RemoteActivator.getDefault()
				.getServiceTracker().getService(IEditingHandler.class);
		InformationStructureRead read = InformationStructureRead
				.newSession(unit);
		DisposableEditingDomain editingDomain = service
				.createNewEditingDomain();
		InformationStructureEdit edit = InformationStructureEdit.newSession(
				MailActivator.INFO_TYPE_ID, editingDomain);
		String content = (String) read
				.getValueByNodeId(MailActivator.NODE_NAME_CONTENT);
		final DOMParser parser = new DOMParser();

		boolean changed = false;
		try {
			parser.parse(new InputSource(new StringReader(content)));
			final Document document = parser.getDocument();

			/*
			 * Replace all imgs with
			 */
			NodeList elementsByTagName = document.getElementsByTagName("img"); //$NON-NLS-1$
			for (int i = 0; i < elementsByTagName.getLength(); i++) {
				final Node node = elementsByTagName.item(i);
				String src = ((Element) node).getAttribute("src"); //$NON-NLS-1$
				URL url = new URL(src);

				IFile tmpFile;
				String fileExtension = new Path(src).getFileExtension();
				if (fileExtension == null || fileExtension.indexOf('?') != -1) {
					fileExtension = ""; //$NON-NLS-1$
					tmpFile = ResourceUtil.createTempFile();
				} else {
					tmpFile = ResourceUtil.createTempFile(fileExtension);
				}
				DownloadFileJob downloadFileJob = new DownloadFileJob(url,
						tmpFile, getFileReceiveAdapter());
				IStatus run = downloadFileJob.run(monitor);
				if (run.isOK()) {
					InformationUnit createSubType = edit.createSubType(
							MailActivator.NODE_NAME_EMBEDDED, null);
					edit.addDynamicNode(unit, createSubType, editingDomain);

					CreateBinaryReferenceCommand addFileToInfoUnit = CommandFactory
							.addFileToInfoUnit(tmpFile, createSubType,
									editingDomain);
					editingDomain.getCommandStack().execute(addFileToInfoUnit);
					IFile targetFile = addFileToInfoUnit.getTargetFile();
					if (targetFile.exists()) {
						((Element) node).setAttribute("src", //$NON-NLS-1$
								org.eclipse.remus.common.core.util.StringUtils
										.join("cid:", addFileToInfoUnit //$NON-NLS-1$
												.getCreatedId()));
						changed = true;
					}
				}

			}
			final Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
			transformer.setOutputProperty("omit-xml-declaration", "yes"); //$NON-NLS-1$ //$NON-NLS-2$

			final DOMSource source = new DOMSource(document);
			final StringWriter writer = new StringWriter();

			final StreamResult result = new StreamResult(writer);
			transformer.transform(source, result);

			edit.setValue(unit, MailActivator.NODE_NAME_CONTENT,
					writer.toString());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			editingDomain.dispose();
		}
		RemoteActivator.getDefault().getServiceTracker().ungetService(service);
		return changed;
	}

	@Override
	public boolean onlyDownload() {
		return true;
	}

	protected IRetrieveFileTransferContainerAdapter getFileReceiveAdapter() {
		if (container == null) {
			try {
				container = ContainerFactory.getDefault().createContainer();
			} catch (final ContainerCreateException e) {
				throw new RuntimeException(Messages.RssConnector_ErrorInitECF,
						e);
			}
		}
		IRepositoryService service = RemoteActivator.getDefault()
				.getServiceTracker().getService(IRepositoryService.class);
		fileReceiveAdapter = (IRetrieveFileTransferContainerAdapter) container
				.getAdapter(IRetrieveFileTransferContainerAdapter.class);
		RemoteRepository repositoryById = service
				.getRepositoryById(getLocalRepositoryId());
		if (Boolean.valueOf(repositoryById.getOptions().get(
				RssActivator.REPOSITORY_OPTIONS_BASIC_AUTHENTICATION))) {
			fileReceiveAdapter
					.setConnectContextForAuthentication(ConnectContextFactory
							.createUsernamePasswordConnectContext(
									getCredentialProvider().getUserName(),
									getCredentialProvider().getPassword()));
		}
		RemoteActivator.getDefault().getServiceTracker().ungetService(service);
		return fileReceiveAdapter;
	}
}
