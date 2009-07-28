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
import org.eclipse.emf.common.command.Command;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.ui.IWorkbench;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RuleValue;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.extension.TransferWrapper;
import org.remus.infomngmnt.core.operation.DownloadFileJob;
import org.remus.infomngmnt.core.transfertypes.URLTransferWrapper;
import org.remus.infomngmnt.image.ImagePlugin;
import org.remus.infomngmnt.image.operation.LoadImageRunnable;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard;
import org.remus.infomngmnt.util.DisposableEditingDomain;
import org.remus.infomngmnt.util.EditingUtil;
import org.remus.infomngmnt.util.InformationUtil;
import org.remus.infomngmnt.util.StatusCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewImageWizard extends NewInfoObjectWizard {

	/**
	 * 
	 */
	public NewImageWizard() {
		setNeedsProgressMonitor(true);
		setWindowTitle("New photo/graphic");

	}

	@Override
	protected Command getAdditionalCommands() {
		IFile tmpFile = ((GeneralImagePage) this.page1).getTmpFile();

		if (tmpFile != null) {
			DisposableEditingDomain editingDomain = EditingUtil.getInstance()
					.createNewEditingDomain();
			LoadImageRunnable loadImageRunnable = new LoadImageRunnable();
			loadImageRunnable.setImagePath(tmpFile.getLocation().toOSString());
			loadImageRunnable.setImageNode(this.newElement);
			loadImageRunnable.setDomain(editingDomain);
			editingDomain.getCommandStack().flush();
			editingDomain.dispose();
			try {
				getContainer().run(true, false, loadImageRunnable);
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return CommandFactory.addFileToInfoUnit(tmpFile, this.newElement, EditingUtil
					.getInstance().getNavigationEditingDomain());
		}
		return super.getAdditionalCommands();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof Category) {
			this.page1 = new GeneralImagePage((Category) firstElement);
		} else if (firstElement instanceof InformationUnitListItem) {
			this.page1 = new GeneralImagePage((InformationUnitListItem) firstElement);
		} else {
			this.page1 = new GeneralImagePage((Category) null);
		}
		setCategoryToPage();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard#getInfoTypeId()
	 */
	@Override
	protected String getInfoTypeId() {
		return ImagePlugin.TYPE_ID;
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
			((GeneralImagePage) this.page1).setTmpFile(tmpFile);

			InformationUnit width = InformationUtil.getChildByType(this.newElement,
					ImagePlugin.NODE_NAME_WIDTH);
			width.setLongValue(((ImageData) value).width);
			InformationUnit height = InformationUtil.getChildByType(this.newElement,
					ImagePlugin.NODE_NAME_HEIGHT);
			height.setLongValue(((ImageData) value).height);
			InformationUnit origFilePath = InformationUtil.getChildByType(this.newElement,
					ImagePlugin.ORIGINAL_FILEPATH);
			origFilePath.setStringValue("clipboard.png");

		} else if (transferType instanceof URLTransferWrapper) {
			try {
				IContainer container = ContainerFactory.getDefault().createContainer();
				IRetrieveFileTransferContainerAdapter adapter = (IRetrieveFileTransferContainerAdapter) container
						.getAdapter(IRetrieveFileTransferContainerAdapter.class);
				final URL url = new URL(value.toString());
				final IFile tmpFile = ResourceUtil.createTempFile("jpeg");
				final DownloadFileJob job = new DownloadFileJob(url, tmpFile, adapter);
				ProgressMonitorDialog pmd = new ProgressMonitorDialog(getShell());
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
									NewImageWizard.this.newElement, ImagePlugin.NODE_NAME_WIDTH);
							width.setLongValue(loader.data[0].width);
							InformationUnit height = InformationUtil.getChildByType(
									NewImageWizard.this.newElement, ImagePlugin.NODE_NAME_HEIGHT);
							height.setLongValue(loader.data[0].height);
							InformationUnit origFilePath = InformationUtil.getChildByType(
									NewImageWizard.this.newElement, ImagePlugin.ORIGINAL_FILEPATH);
							origFilePath.setStringValue(url.getPath());
							((GeneralImagePage) NewImageWizard.this.page1).setTmpFile(tmpFile);
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
