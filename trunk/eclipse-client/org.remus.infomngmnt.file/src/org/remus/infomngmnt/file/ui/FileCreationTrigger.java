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

package org.remus.infomngmnt.file.ui;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.ecf.core.ContainerFactory;
import org.eclipse.ecf.core.IContainer;
import org.eclipse.ecf.filetransfer.IRetrieveFileTransferContainerAdapter;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.remus.common.core.util.ResourceUtil;
import org.eclipse.remus.common.io.transfer.DownloadFileJob;
import org.eclipse.remus.common.ui.UIUtil;
import org.eclipse.remus.core.model.InformationStructureEdit;
import org.eclipse.remus.rules.RuleValue;
import org.eclipse.remus.ui.operation.LoadFileToTmpFromPathRunnable;
import org.eclipse.remus.ui.rules.internal.transfer.FileTransferWrapper;
import org.eclipse.remus.ui.rules.internal.transfer.URLTransferWrapper;
import org.eclipse.remus.ui.rules.transfer.TransferWrapper;
import org.eclipse.remus.ui.rules.wizard.NewObjectWizardDelegate;
import org.eclipse.remus.util.StatusCreator;
import org.remus.infomngmnt.file.Activator;
import org.remus.infomngmnt.file.Messages;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class FileCreationTrigger extends NewObjectWizardDelegate {

	/**
	 * 
	 */
	public FileCreationTrigger() {
		super(new NewFileWizard());
	}

	@Override
	public void setDefaults(final Object value, final RuleValue ruleValue,
			final TransferWrapper transferType) throws CoreException {
		if (transferType instanceof FileTransferWrapper) {
			String[] paths = (String[]) value;
			String string = paths[0];
			File file = new File(string);
			// TODO support for directories
			if (!file.exists() || file.isDirectory()) {
				return;
			}

			LoadFileToTmpFromPathRunnable runnable = new LoadFileToTmpFromPathRunnable();
			runnable.setFilePath(string);
			InformationStructureEdit edit = InformationStructureEdit
					.newSession(Activator.TYPE_ID);
			edit.setValue(newInformationUnit, Activator.FILENAME, new Path(
					string).lastSegment());
			edit.setValue(newInformationUnit, Activator.ORIGIN, string);
			try {
				new ProgressMonitorDialog(UIUtil.getDisplay().getActiveShell())
						.run(true, false, runnable);
				wrappingWizard.setFiles(new IFile[] { runnable.getTmpFile() });
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (transferType instanceof URLTransferWrapper) {
			try {
				IContainer container = ContainerFactory.getDefault()
						.createContainer();
				IRetrieveFileTransferContainerAdapter adapter = (IRetrieveFileTransferContainerAdapter) container
						.getAdapter(IRetrieveFileTransferContainerAdapter.class);
				final URL url = new URL(value.toString());
				InformationStructureEdit edit = InformationStructureEdit
						.newSession(Activator.TYPE_ID);
				edit.setValue(newInformationUnit, Activator.FILENAME,
						"download"); //$NON-NLS-1$
				edit.setValue(newInformationUnit, Activator.ORIGIN,
						url.toString());
				final IFile tmpFile = ResourceUtil.createTempFile();
				final DownloadFileJob job = new DownloadFileJob(url, tmpFile,
						adapter);
				ProgressMonitorDialog pmd = new ProgressMonitorDialog(UIUtil
						.getDisplay().getActiveShell());
				pmd.run(true, false, new IRunnableWithProgress() {

					public void run(final IProgressMonitor monitor)
							throws InvocationTargetException,
							InterruptedException {
						monitor.beginTask(Messages.FileCreationTrigger_DownloadingFile,
								IProgressMonitor.UNKNOWN);
						IStatus run = job.run(monitor);

						FileCreationTrigger.this.wrappingWizard
								.setFiles(new IFile[] { tmpFile });
						if (!run.isOK()) {
							throw new InvocationTargetException(run
									.getException(), Messages.FileCreationTrigger_ErrorDownloading);
						}

					}

				});
			} catch (Exception e) {
				throw new CoreException(StatusCreator.newStatus(e.getMessage(),
						e));
			}
		}

	}
}
