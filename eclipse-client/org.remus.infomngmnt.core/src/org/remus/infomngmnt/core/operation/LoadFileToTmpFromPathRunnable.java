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

package org.remus.infomngmnt.core.operation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.core.progress.CancelableRunnable;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.util.StatusCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class LoadFileToTmpFromPathRunnable extends CancelableRunnable {

	private File file;

	private String filePath;

	private IFile tmpFile;

	public LoadFileToTmpFromPathRunnable() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.progress.CancelableRunnable#runCancelableRunnable
	 * (org.eclipse.core.runtime.IProgressMonitor)
	 */

	@Override
	protected IStatus runCancelableRunnable(final IProgressMonitor monitor) {
		monitor.beginTask("Checking filename...", IProgressMonitor.UNKNOWN);
		this.file = new File(this.filePath);
		if (this.file.exists() && this.file.isFile()) {
			monitor.beginTask(NLS.bind("Reading file {0}", this.file.getName()),
					IProgressMonitor.UNKNOWN);
			try {
				if (this.tmpFile != null && this.tmpFile.exists()) {
					this.tmpFile.delete(true, monitor);
				}
				this.tmpFile = ResourceUtil.createTempFile(new Path(this.filePath)
						.getFileExtension());
				FileInputStream fis = new FileInputStream(this.file);
				this.tmpFile.setContents(fis, true, false, monitor);
				return Status.OK_STATUS;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return StatusCreator.newStatus("File not exisits or is not accessible");
	}

	/**
	 * @return the tmpFile
	 */
	public IFile getTmpFile() {
		return this.tmpFile;
	}

	/**
	 * @param filePath
	 *            the filePath to set
	 */
	public void setFilePath(final String filePath) {
		this.filePath = filePath;
	}

}
