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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.core.extension.AbstractExtensionRepository;
import org.remus.infomngmnt.core.model.InformationStructureEdit;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.core.remote.ILoginCallBack;
import org.remus.infomngmnt.core.remote.IRepository;
import org.remus.infomngmnt.core.remote.RemoteException;
import org.remus.infomngmnt.mail.ContentType;
import org.remus.infomngmnt.mail.MailActivator;
import org.remus.infomngmnt.util.StatusCreator;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RssConnector extends AbstractExtensionRepository implements IRepository {

	private SyndFeedInput api;

	private final PropertyChangeListener credentialsMovedListener = new PropertyChangeListener() {
		public void propertyChange(final PropertyChangeEvent evt) {
			reset();
		}
	};

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
		throw new RemoteException(StatusCreator.newStatus("Deletion is not supported"));
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
			try {
				SyndFeed build = getApi().build(new XmlReader(new URL(getRepositoryUrl())));
				List entries = build.getEntries();
				for (Object object : entries) {
					returnValue.add(buildFeedEntry((SyndEntry) object));
				}
				return returnValue.toArray(new RemoteObject[returnValue.size()]);
			} catch (Exception e) {
				throw new RemoteException(StatusCreator.newStatus("Error getting feed entries", e));
			}
		}
		return new RemoteObject[0];
	}

	private RemoteObject buildFeedEntry(final SyndEntry object) {
		RemoteObject returnValue = InfomngmntFactory.eINSTANCE.createRemoteObject();
		returnValue.setHash(String.valueOf(object.getPublishedDate().getTime()));
		returnValue.setUrl(object.getUri());
		returnValue.setId(object.getUri());
		returnValue.setWrappedObject(object);
		returnValue.setName(object.getTitle());
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
	public InformationUnit getFullObject(final InformationUnitListItem informationUnitListItem,
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
	public RemoteObject getRemoteObjectBySynchronizableObject(final SynchronizableObject object,
			final IProgressMonitor monitor) throws RemoteException {
		String url = object.getSynchronizationMetaData().getUrl();
		if (url.equals(getRepositoryUrl())) {
			return getRepositoryById(object.getSynchronizationMetaData().getRepositoryId());
		} else {
			RemoteRepository repositoryById = getRepositoryById(object.getSynchronizationMetaData()
					.getRepositoryId());
			RemoteObject[] children = getChildren(monitor, repositoryById, false);
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
	public String getRepositoryUrl() {
		return ((RssCredentialProvider) getCredentialProvider()).getUrl();
	}

	public String getFeedTitle() {
		try {
			return getApi().build(new XmlReader(new URL(getRepositoryUrl()))).getTitle();
		} catch (Exception e) {

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
	public InformationUnit getPrefetchedInformationUnit(final RemoteObject remoteObject) {
		Object wrappedObject = remoteObject.getWrappedObject();
		if (wrappedObject instanceof SyndEntry) {
			SyndEntry entry = (SyndEntry) wrappedObject;
			InformationStructureEdit edit = InformationStructureEdit
					.newSession(MailActivator.INFO_TYPE_ID);
			InformationUnit newInformationUnit = edit.newInformationUnit();
			newInformationUnit.setLabel(entry.getTitle());
			edit.setValue(newInformationUnit, MailActivator.NODE_NAME_RECEIVED, entry
					.getPublishedDate());
			edit.setValue(newInformationUnit, MailActivator.INFO_TYPE_ID, entry.getTitle());
			edit.setValue(newInformationUnit, MailActivator.NODE_NAME_CONTENT_TYPE,
					ContentType.HTML.getKey());
			edit.setValue(newInformationUnit, MailActivator.NODE_NAME_SENDER, entry.getAuthor());
			edit.setValue(newInformationUnit, MailActivator.NODE_NAME_MORE_INFO, entry.getLink());
			List contents = entry.getContents();
			if (contents.size() > 0 && contents.get(0) != null
					&& contents.get(0) instanceof SyndContent) {
				SyndContent object = (SyndContent) contents.get(0);
				edit.setValue(newInformationUnit, MailActivator.NODE_NAME_CONTENT, object
						.getValue());
			}
			if (entry.getDescription() != null) {
				edit.setValue(newInformationUnit, MailActivator.NODE_NAME_CONTENT, entry
						.getDescription().getValue());
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
	public void proceedLocalInformationUnitAfterSync(
			final InformationUnit newOrUpdatedLocalInformationUnit, final IProgressMonitor monitor) {
		InformationStructureEdit edit = InformationStructureEdit
				.newSession(newOrUpdatedLocalInformationUnit.getType());
		InformationStructureRead read = InformationStructureRead
				.newSession(newOrUpdatedLocalInformationUnit);

		String valueByNodeId = (String) read.getValueByNodeId(MailActivator.NODE_NAME_CONTENT);

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
		this.api = null;
		getCredentialProvider().removePropertyChangeListener(this.credentialsMovedListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.core.remote.IRepository#validate()
	 */
	public IStatus validate() {
		// TODO Auto-generated method stub
		return null;
	}

	private synchronized SyndFeedInput getApi() {
		if (this.api == null) {
			getCredentialProvider().addPropertyChangeListener(this.credentialsMovedListener);
			this.api = new SyndFeedInput();
		}
		return this.api;
	}

	private void parseContent(final InformationUnit unit) {
		// final DOMParser parser = new DOMParser();
		// try {
		// InputStream contents = tempFile.getContents();
		// parser.parse(new org.apache.xerces.xni.parser.XMLInputSource(null,
		// null, null,
		// contents, "UTF-8"));
		// final Document document = parser.getDocument();
		// NodeList elementsByTagName = document.getElementsByTagName("head");
		// for (int i = 0; i < elementsByTagName.getLength(); i++) {
		// final Node node = elementsByTagName.item(i);
		//
		// NodeList childNodes = node.getChildNodes();
		// while (childNodes.getLength() > 0) {
		// node.removeChild(childNodes.item(0));
		// }
		// Element createElement = document.createElement("style");
		// Attr createAttribute = document.createAttribute("type");
		// createAttribute.setNodeValue("text/css");
		// InputStream resourceAsStream = DownloadLatestNewsJob.class
		// .getResourceAsStream("template.css");
		// Text createComment = document.createTextNode(StreamUtil
		// .convertStreamToString(resourceAsStream));
		// createElement.setAttributeNode(createAttribute);
		// createElement.appendChild(createComment);
		// node.appendChild(createElement);
		// break;
		// }
		// elementsByTagName = document.getElementsByTagName("a");
		// for (int i = 0; i < elementsByTagName.getLength(); i++) {
		// final Node node = elementsByTagName.item(i);
		// ((Element) node).setAttribute("target", "_blank");
		// String attribute = ((Element) node).getAttribute("href");
		// // Joomla cuts all internal links...
		// if (attribute.startsWith("/")) {
		// ((Element) node).setAttribute("href", URL_PREFIX + attribute);
		// }
		// }
		// final Transformer transformer =
		// TransformerFactory.newInstance().newTransformer();
		// transformer.setOutputProperty("omit-xml-declaration", "yes");
		//
		// final DOMSource source = new DOMSource(document);
		// final StringWriter writer = new StringWriter();
		//
		// final StreamResult result = new StreamResult(writer);
		// transformer.transform(source, result);
		// tempFile.setContents(new
		// ByteArrayInputStream(writer.toString().getBytes("UTF-8")),
		// true, false, monitor);
		//
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (TransformerFactoryConfigurationError e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	@Override
	public boolean onlyDownload() {
		return true;
	}

}
