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

package org.remus.infomngmnt.common.core.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;

import org.remus.infomngmnt.common.core.streams.StreamCloser;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ResourceUtil {

	public static final String PROJECT_NAME_TMP = "__tmp"; //$NON-NLS-1$

	/**
	 * Adds a builder to the project
	 * 
	 * @param builder
	 *            the builder id
	 * @throws CoreException
	 *             if the project-descriptiion is invalid
	 */
	public static void addBuilder(final IProject project, final String builder)
			throws CoreException {
		// Get project description and then the associated build commands
		final IProjectDescription desc = project.getDescription();
		addBuilder(desc, builder);

	}

	public static void addBuilder(final IProjectDescription desc, final String builder) {
		final ICommand[] commands = desc.getBuildSpec();

		// Determine if builder already associated
		boolean found = hasBuilder(desc, builder);

		// Add builder if not already in project
		if (!found) {
			final ICommand command = desc.newCommand();
			command.setBuilderName(builder);
			// Create map with arguments specific to builder in project here
			// command.setArguments(Map args);
			final ICommand[] newCommands = new ICommand[commands.length + 1];

			// Add it before other builders.
			System.arraycopy(commands, 0, newCommands, 1, commands.length);
			newCommands[0] = command;
			desc.setBuildSpec(newCommands);
		}

	}

	public static boolean hasBuilder(final IProjectDescription desc, final String builder) {
		// Determine if builder already associated
		final ICommand[] commands = desc.getBuildSpec();
		boolean found = false;
		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(builder)) {
				found = true;
				break;
			}
		}
		return found;
	}

	public static void removeBuilder(final IProject project, final String builder)
			throws CoreException {

		final IProjectDescription desp = project.getDescription();
		final ICommand[] commands = desp.getBuildSpec();
		boolean found = false;

		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(builder)) {
				found = true;
				break;
			}
		}

		if (found) { // Remove builder from project

			final ICommand[] newCommands = new ICommand[commands.length - 1];

			// Add it before other builders.

			System.arraycopy(commands, 1, newCommands, 0, commands.length - 1);

			desp.setBuildSpec(newCommands);
			project.setDescription(desp, null);
			// Confirm Builder Remove
		}
	}

	public static File getPreviousVersion(final IFile file, final IProgressMonitor monitor)
			throws CoreException {
		IFileState[] history = file.getHistory(monitor);
		File createTempFile;
		if (history.length > 0) {
			InputStream contents;
			FileOutputStream fos = null;
			contents = history[0].getContents();
			try {
				createTempFile = File.createTempFile("history", ".tmp");
				fos = new FileOutputStream(createTempFile);
				byte buf[] = new byte[1024];
				int len;
				while ((len = contents.read(buf)) > 0) {
					fos.write(buf, 0, len);
				}

			} catch (Exception e) {
				return null;
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						// do nothing. we've done our best.
					}
				}
				if (contents != null) {
					try {
						contents.close();
					} catch (IOException e) {
						// do nothing. we've done our best.
					}
				}
			}
			return createTempFile;
		} else {
			return null;
		}
	}

	public static IFile createTempFile(final String extension) {
		NullProgressMonitor nullProgressMonitor = new NullProgressMonitor();
		IContainer project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT_NAME_TMP);
		ISchedulingRule rule = Job.getJobManager().currentJob() != null ? Job.getJobManager()
				.currentJob().getRule() : null;
		IFile file = null;
		if (rule instanceof IProject) {
			IFolder folder = ((IProject) rule).getFolder(PROJECT_NAME_TMP);
			if (!folder.exists()) {
				try {
					folder.create(true, true, nullProgressMonitor);
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			project = folder;
		} else {
			if (!project.exists()) {
				try {
					((IProject) project).create(nullProgressMonitor);
					((IProject) project).open(nullProgressMonitor);
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (extension != null) {
			file = project.getFile(new Path(IdFactory.createId()).addFileExtension(extension));
		} else {
			file = project.getFile(new Path(IdFactory.createId()));
		}
		if (!file.exists()) {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[0]);
			try {
				file.create(inputStream, true, nullProgressMonitor);
			} catch (CoreException e) {

			} finally {
				StreamCloser.closeStreams(inputStream);
			}
		} else {
			throw new IllegalArgumentException("tmp file already exisits.");
		}
		return file;
	}

	public static IFile createTempFile() {
		return createTempFile(null);
	}

	public static File createTempFileOnFileSystem() {
		return createTempFileOnFileSystem("remus");
	}

	public static File createTempFileOnFileSystem(final String extension) {
		File tempFile;
		try {
			tempFile = File.createTempFile("tmp", extension);
		} catch (IOException e) {
			throw new IllegalStateException("Error creating temp file");
		}
		return tempFile;
	}

	public static URI getFileSystemURI(final String text) {
		return URIUtil.toURI(text);
	}

	public static boolean isProjectPathEqual(final URI oldPath, final URI newPath) {
		return URIUtil.equals(oldPath, newPath);
	}

	public static IFileStore getFileStore(final String string) {
		return getFileStore(new Path(string).toFile().toURI());
	}

	/**
	 * Get the file store for the URI.
	 * 
	 * @param uri
	 * @return IFileStore or <code>null</code> if there is a
	 *         {@link CoreException}.
	 */
	public static IFileStore getFileStore(final URI uri) {
		try {
			return EFS.getStore(uri);
		} catch (CoreException e) {

			return null;
		}
	}

	/**
	 * Return the fileInfo at pathName or <code>null</code> if the format is
	 * invalid or if the file info cannot be determined.
	 * 
	 * @param pathName
	 * @return IFileInfo or <code>null</code>
	 */
	public static IFileInfo getFileInfo(final String pathName) {
		IFileStore store = getFileStore(pathName);
		if (store == null) {
			return null;
		}
		return store.fetchInfo();
	}

	public static boolean fileExists(final String pathName) {
		IFileInfo fileInfo = getFileInfo(pathName);
		return fileInfo != null && fileInfo.exists();
	}

}
