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

package org.remus.infomngmnt.image.ui;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.ecf.core.ContainerFactory;
import org.eclipse.ecf.core.IContainer;
import org.eclipse.ecf.filetransfer.IRetrieveFileTransferContainerAdapter;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.osgi.util.NLS;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.common.core.util.ResourceUtil;
import org.eclipse.remus.common.io.transfer.DownloadFileJob;
import org.eclipse.remus.common.ui.UIUtil;
import org.eclipse.remus.core.model.InformationStructureEdit;
import org.eclipse.remus.rules.RuleValue;
import org.eclipse.remus.ui.rules.internal.transfer.FileTransferWrapper;
import org.eclipse.remus.ui.rules.internal.transfer.URLTransferWrapper;
import org.eclipse.remus.ui.rules.transfer.TransferWrapper;
import org.eclipse.remus.ui.rules.wizard.NewObjectWizardDelegate;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.remus.util.StatusCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

import org.remus.infomngmnt.image.ImagePlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ImageCreationTrigger extends NewObjectWizardDelegate {

	/**
	 * 
	 */
	public ImageCreationTrigger() {
		super(new NewImageWizard());
	}

	@Override
	public void setDefaults(final Object value, final RuleValue ruleValue,
			final TransferWrapper transferType) throws CoreException {
		if (value instanceof ImageData) {
			ImageLoader loader = new ImageLoader();
			loader.data = new ImageData[] { (ImageData) value };
			IFile tmpFile = ResourceUtil.createTempFile("png");
			loader.save(tmpFile.getLocation().toOSString(), SWT.IMAGE_PNG);
			try {
				tmpFile.refreshLocal(IResource.DEPTH_INFINITE, null);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.wrappingWizard.setFiles(new IFile[] { tmpFile });

			InformationStructureEdit edit = InformationStructureEdit
					.newSession(ImagePlugin.TYPE_ID);
			edit.setValue(this.newInformationUnit, ImagePlugin.NODE_NAME_WIDTH,
					((ImageData) value).width);
			edit.setValue(this.newInformationUnit, ImagePlugin.NODE_NAME_HEIGHT,
					((ImageData) value).height);
			edit.setValue(this.newInformationUnit, ImagePlugin.ORIGINAL_FILEPATH, "clipboard.png");

		} else if (transferType instanceof FileTransferWrapper) {
			ImageLoader imageLoader = new ImageLoader();
			imageLoader.load(((String[]) value)[0]);
			ImageData data = imageLoader.data[0];
			IFile tmpFile = ResourceUtil.createTempFile("png");
			imageLoader.save(tmpFile.getLocation().toOSString(), SWT.IMAGE_PNG);
			try {
				tmpFile.refreshLocal(IResource.DEPTH_INFINITE, null);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.wrappingWizard.setFiles(new IFile[] { tmpFile });
			InformationStructureEdit edit = InformationStructureEdit
					.newSession(ImagePlugin.TYPE_ID);
			edit.setValue(this.newInformationUnit, ImagePlugin.NODE_NAME_WIDTH, data.width);
			edit.setValue(this.newInformationUnit, ImagePlugin.NODE_NAME_HEIGHT, data.height);
			edit.setValue(this.newInformationUnit, ImagePlugin.ORIGINAL_FILEPATH,
					((String[]) value)[0]);

		} else if (transferType instanceof URLTransferWrapper) {
			try {
				IContainer container = ContainerFactory.getDefault().createContainer();
				IRetrieveFileTransferContainerAdapter adapter = (IRetrieveFileTransferContainerAdapter) container
						.getAdapter(IRetrieveFileTransferContainerAdapter.class);
				final URL url = new URL(value.toString());
				final IFile tmpFile = ResourceUtil.createTempFile();
				final DownloadFileJob job = new DownloadFileJob(url, tmpFile, adapter);
				ProgressMonitorDialog pmd = new ProgressMonitorDialog(UIUtil.getDisplay()
						.getActiveShell());
				pmd.run(true, false, new IRunnableWithProgress() {

					public void run(final IProgressMonitor monitor)
							throws InvocationTargetException, InterruptedException {
						monitor.beginTask("Download requested image", IProgressMonitor.UNKNOWN);
						IStatus run = job.run(monitor);
						monitor.beginTask("Calculating width and height", IProgressMonitor.UNKNOWN);
						try {
							ImageLoader loader = new ImageLoader();
							InputStream contents = tmpFile.getContents();
							try {
								loader.load(contents);
							} catch (Exception e) {
								throw new InvocationTargetException(
										e,
										NLS
												.bind(
														"Error while processing the dragged data. Check if \'\'{0}\'\' is dragable image-source.",
														value.toString()));
							}
							InformationUnit width = InformationUtil.getChildByType(
									ImageCreationTrigger.this.newInformationUnit,
									ImagePlugin.NODE_NAME_WIDTH);
							width.setLongValue(loader.data[0].width);
							InformationUnit height = InformationUtil.getChildByType(
									ImageCreationTrigger.this.newInformationUnit,
									ImagePlugin.NODE_NAME_HEIGHT);
							height.setLongValue(loader.data[0].height);
							InformationUnit origFilePath = InformationUtil.getChildByType(
									ImageCreationTrigger.this.newInformationUnit,
									ImagePlugin.ORIGINAL_FILEPATH);
							origFilePath.setStringValue(url.getPath());
							ImageCreationTrigger.this.wrappingWizard
									.setFiles(new IFile[] { tmpFile });
							if (!run.isOK()) {
								throw new InvocationTargetException(run.getException(),
										"Error downloading image");
							}
						} catch (CoreException e) {
							throw new InvocationTargetException(e);
						}

					}

				});
			} catch (Exception e) {
				throw new CoreException(StatusCreator.newStatus(e.getMessage(), e));
			}
		}

	}
}
