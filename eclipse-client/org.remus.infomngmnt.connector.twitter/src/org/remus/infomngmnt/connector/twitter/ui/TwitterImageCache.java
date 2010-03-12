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

package org.remus.infomngmnt.connector.twitter.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.ecf.core.ContainerCreateException;
import org.eclipse.ecf.core.ContainerFactory;
import org.eclipse.ecf.core.IContainer;
import org.eclipse.ecf.filetransfer.IRetrieveFileTransferContainerAdapter;

import org.remus.infomngmnt.common.core.streams.StreamUtil;
import org.remus.infomngmnt.common.core.util.ResourceUtil;
import org.remus.infomngmnt.commons.io.transfer.DownloadFileJob;
import org.remus.infomngmnt.connector.twitter.TwitterActivator;
import org.remus.infomngmnt.connector.twitter.TwitterRepository;
import org.remus.infomngmnt.core.remote.sync.SyncUtil;
import org.remus.infomngmnt.model.remote.IRepository;

import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TwitterImageCache {

	private IContainer container;
	private IRetrieveFileTransferContainerAdapter fileReceiveAdapter;

	public void checkCache(final String userName, final URL imageUrl,
			final ImageCacheCallBack callback) {
		File file = TwitterActivator.getDefault().getStateLocation().append("avatars").toFile();
		if (!file.exists()) {
			file.mkdir();
		}
		final File file2 = TwitterActivator.getDefault().getStateLocation().append("avatars")
				.append(userName).addFileExtension("avatar").toFile();
		if (!file2.exists()) {
			try {
				file2.createNewFile();
				final IFile tempFile = ResourceUtil.createTempFile();
				DownloadFileJob job = new DownloadFileJob(imageUrl, tempFile,
						getFileReceiveAdapter());
				job.schedule();
				job.addJobChangeListener(new JobChangeAdapter() {
					@Override
					public void done(final IJobChangeEvent event) {
						try {
							FileOutputStream fileOutputStream = new FileOutputStream(file2);
							InputStream contents = tempFile.getContents();
							StreamUtil.stream(contents, fileOutputStream);
							fileOutputStream.flush();
							fileOutputStream.close();
							contents.close();
							tempFile.delete(true, new NullProgressMonitor());
							if (callback != null) {
								callback.callBack(file2.getAbsolutePath());
							}
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (CoreException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void getImageByUser(final String getDefault, final String repositoryId,
			final ImageCacheCallBack callback) {
		File file = TwitterActivator.getDefault().getStateLocation().append("avatars").toFile();
		if (!file.exists()) {
			file.mkdir();
		}
		final File file2 = TwitterActivator.getDefault().getStateLocation().append("avatars")
				.append(getDefault).addFileExtension("avatar").toFile();
		if (file2.exists()) {
			if (callback != null) {
				callback.callBack(file2.getAbsolutePath());
			}
		} else {
			IRepository repositoryImplementation = SyncUtil
					.getRepositoryImplemenationByRepositoryId(repositoryId);
			if (repositoryImplementation != null
					&& repositoryImplementation instanceof TwitterRepository) {
				Twitter api = ((TwitterRepository) repositoryImplementation).getApi();
				try {
					URL profileImageUrl = api.showUser(getDefault).getProfileImageURL();
					checkCache(getDefault, profileImageUrl, new ImageCacheCallBack() {
						public void callBack(final String path2File) {
							if (callback != null) {
								callback.callBack(path2File);
							}
						}
					});

				} catch (TwitterException e) {
					if (callback != null) {
						callback.callBack(null);
					}
				}
			}
		}
	}

	private IRetrieveFileTransferContainerAdapter getFileReceiveAdapter() {
		if (this.container == null) {
			try {
				this.container = ContainerFactory.getDefault().createContainer();
			} catch (final ContainerCreateException e) {
				throw new RuntimeException("Error initializing sync-container", e);
			}
			this.fileReceiveAdapter = (IRetrieveFileTransferContainerAdapter) this.container
					.getAdapter(IRetrieveFileTransferContainerAdapter.class);
		}
		return this.fileReceiveAdapter;
	}

}
