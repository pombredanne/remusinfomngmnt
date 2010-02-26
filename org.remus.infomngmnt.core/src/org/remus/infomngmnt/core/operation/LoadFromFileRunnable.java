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
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.core.streams.FileUtil;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.progress.CancelableRunnable;
import org.remus.infomngmnt.util.EditingUtil;
import org.remus.infomngmnt.util.StatusCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @deprecated Setting a binary value to the information unit causes memory
 *             leaks. Use {@link InformationUnit#getBinaryReferences()} instead.
 *             See
 *             {@link CommandFactory#addFileToInfoUnit(org.eclipse.core.resources.IFile, InformationUnit, EditingDomain)}
 *             for usage.
 */
@Deprecated
public class LoadFromFileRunnable extends CancelableRunnable {

	private final String filePath;

	private final InformationUnit rawDataNode;

	private final EditingDomain domain;

	private File file;

	public LoadFromFileRunnable(final String filePath, final InformationUnit rawDataNode,
			final EditingDomain domain) {
		super();
		this.filePath = filePath;
		this.rawDataNode = rawDataNode;
		if (domain == null) {
			this.domain = EditingUtil.getInstance().createNewEditingDomain();
		} else {
			this.domain = domain;
		}
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
			monitor.beginTask(NLS.bind("Reading file {0}", this.file.getName()), (int) this.file
					.length());
			try {
				if (this.domain != null) {
					final CompoundCommand cc = new CompoundCommand();
					cc.append(SetCommand.create(this.domain, this.rawDataNode,
							InfomngmntPackage.Literals.INFORMATION_UNIT__BINARY_VALUE, FileUtil
									.getBytesFromFile(this.file, monitor)));
					cc.setLabel("Set new bytes");
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							LoadFromFileRunnable.this.domain.getCommandStack().execute(cc);
						}
					});

				} else {
					this.rawDataNode.setBinaryValue(FileUtil.getBytesFromFile(this.file, monitor));
				}
			} catch (IOException e) {
				return StatusCreator.newStatus("File not exisits or is not accessible");
			}
			return Status.OK_STATUS;
		}
		return StatusCreator.newStatus("File not exisits or is not accessible");
	}

}
