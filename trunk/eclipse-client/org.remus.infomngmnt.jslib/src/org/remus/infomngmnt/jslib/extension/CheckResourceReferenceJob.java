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

package org.remus.infomngmnt.jslib.extension;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.Constants;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CheckResourceReferenceJob extends Job {

	public static final Map<String, String> map = new HashMap<String, String>();

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.jslib";

	public static final String REF_PROJECT_NAME = "__internal_referencedLibraries"; //$NON-NLS-1$

	public static final String EXTENSION_POINT_ID =	PLUGIN_ID + ".browserReferenceResource"; //$NON-NLS-1$

	public static final String ATT_ID = "id"; //$NON-NLS-1$
	public static final String ATT_RESOURCE = "resource"; //$NON-NLS-1$

	public CheckResourceReferenceJob() {
		super("Updating resource cache");

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus run(final IProgressMonitor monitor) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(REF_PROJECT_NAME);
		try {
			if (!project.exists()) {
				project.create(monitor);
			}
			if (!project.isOpen()) {
				project.open(monitor);
			}
		} catch (CoreException e1) {
			new Status(
					IStatus.ERROR,
					PLUGIN_ID,
					NLS.bind("Error creating/opening project {0}", project.getName()));
		}
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
				EXTENSION_POINT_ID);
		final IConfigurationElement[] configurationElements = extensionPoint.getConfigurationElements();
		for (IConfigurationElement iConfigurationElement : configurationElements) {
			String contributor = iConfigurationElement.getContributor().getName();
			String contributorVersion = (String) Platform.getBundle(contributor).getHeaders().get(Constants.BUNDLE_VERSION);
			Path resourcePath = new Path(iConfigurationElement.getAttribute(
					ATT_RESOURCE));
			String id = iConfigurationElement.getAttribute(
					ATT_ID);

			IFolder folder = project.getFolder(contributor);
			if (!folder.exists()) {
				try {
					folder.create(true, true, monitor);
				} catch (CoreException e) {
					new Status(
							IStatus.ERROR,
							PLUGIN_ID,
							NLS.bind("Error creating folder {0}", folder.getName()));
				}
			} else {
				try {
					IResource[] members = folder.members();
					for (IResource iResource : members) {
						if (!iResource.getName().equals(contributorVersion)) {
							iResource.delete(true, monitor);
						}
					}
				} catch (CoreException e) {
					// skip..
				}
			}
			IFolder versionFolder = folder.getFolder(contributorVersion);
			if (!versionFolder.exists()) {
				try {
					versionFolder.create(true, true, monitor);
				} catch (CoreException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			IFile file = null;
			InputStream openStream = null;
			try {
				file = versionFolder.getFile(new Path(id).addFileExtension(resourcePath.getFileExtension()));
				if (!file.exists()) {
					openStream = FileLocator.openStream(
							Platform.getBundle(contributor),
							resourcePath, false);
					file.create(openStream, true, monitor);
				}
				map.put(
						id.replaceAll("\\.", "_"), 
						file.getLocation().toOSString());
			} catch (Exception e) {
//				Activator.getDefault().getLog().log(new Status(
//						IStatus.ERROR,
//						Activator.PLUGIN_ID,
//						NLS.bind("Error extracting {0} to {1}", id, file)));
			} finally {
				if (openStream != null) {
					try {
						openStream.close();
					} catch (IOException e) {
						// do nothing
					}
				}
			}

		}
		return Status.OK_STATUS;
	}

}
