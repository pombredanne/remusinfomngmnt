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

package org.remus.infomngmnt.connector.podcast;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.RemoteObject;
import org.eclipse.remus.common.core.util.ResourceUtil;
import org.eclipse.remus.common.io.transfer.DownloadFileJob;
import org.eclipse.remus.core.model.InformationStructureEdit;
import org.eclipse.remus.core.remote.RemoteException;
import org.eclipse.remus.util.StatusCreator;

import org.remus.infomngmnt.audio.AudioActivator;
import org.remus.infomngmnt.connector.rss.RssConnector;

import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndEntry;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class PodcastConnector extends RssConnector {

	@Override
	public String getTypeIdByObject(final RemoteObject remoteObject) {
		return AudioActivator.TYPE_ID;
	}

	@Override
	public boolean proceedLocalInformationUnitAfterSync(
			final InformationUnit newOrUpdatedLocalInformationUnit, final IProgressMonitor monitor) {
		return false;
	}

	@Override
	public IFile getBinaryReferences(final InformationUnitListItem syncObject,
			final InformationUnit localInfoFragment, final IProgressMonitor monitor)
			throws RemoteException {
		if (AudioActivator.TYPE_ID.equals(localInfoFragment.getType())) {
			RemoteObject remoteObject = getRemoteObjectBySynchronizableObject(syncObject, monitor);
			IPath mediaUrl = null;
			if (remoteObject.getWrappedObject() instanceof SyndEntry) {
				SyndEntry entry = (SyndEntry) remoteObject.getWrappedObject();
				mediaUrl = getMediaUrl(entry);
			}
			if (mediaUrl == null) {
				return null;
			}
			try {
				URL downloadUrl = new URL(mediaUrl.toString());
				if (downloadUrl == null) {
					throw new RemoteException(StatusCreator
							.newStatus("Error calculating download url."));
				}
				IFile videoFile = ResourceUtil.createTempFile(new Path(syncObject
						.getSynchronizationMetaData().getUrl()).getFileExtension());

				DownloadFileJob downloadVidJob = new DownloadFileJob(downloadUrl, videoFile,
						getFileReceiveAdapter());
				IStatus run = downloadVidJob.run(monitor);
				if (run.isOK()) {
					return videoFile;
				}

			} catch (Exception e) {

			}
		}
		return null;

	}

	private IPath getMediaUrl(final SyndEntry entry) {
		List enclosures = entry.getEnclosures();
		if (enclosures.size() > 0) {
			SyndEnclosure object = (SyndEnclosure) enclosures.get(0);
			String url = object.getUrl();
			if (url != null && url.trim().length() > 0) {
				return new Path(url);
			}
		}
		if (entry.getLink() != null) {
			return new Path(entry.getLink());
		}
		return null;
	}

	@Override
	public InformationUnit getPrefetchedInformationUnit(final RemoteObject remoteObject) {
		Object wrappedObject = remoteObject.getWrappedObject();
		if (wrappedObject instanceof SyndEntry) {
			SyndEntry entry = (SyndEntry) wrappedObject;
			InformationStructureEdit edit = InformationStructureEdit
					.newSession(AudioActivator.TYPE_ID);
			InformationUnit newInformationUnit = edit.newInformationUnit();
			newInformationUnit.setLabel(entry.getTitle());
			List contents = entry.getContents();
			if (contents.size() > 0 && contents.get(0) != null
					&& contents.get(0) instanceof SyndContent) {
				SyndContent object = (SyndContent) contents.get(0);
				edit.setValue(newInformationUnit, "@description", object.getValue());
			} else if (entry.getDescription() != null) {
				edit
						.setValue(newInformationUnit, "@description", entry.getDescription()
								.getValue());
				IPath mediaUrl = getMediaUrl(entry);
				if (mediaUrl != null) {
					edit.setValue(newInformationUnit, AudioActivator.NODE_NAME_MEDIATYPE, mediaUrl
							.getFileExtension());
				}
			}

			List categories = entry.getCategories();
			List<String> cats = new ArrayList<String>();
			for (Object object : categories) {
				cats.add(((SyndCategory) object).getName());
			}
			String join = org.apache.commons.lang.StringUtils.join(cats, " ");
			newInformationUnit.setKeywords(join);

			return newInformationUnit;
		}
		return null;
	}

	@Override
	public boolean hasBinaryReferences() {
		return true;
	}

}
