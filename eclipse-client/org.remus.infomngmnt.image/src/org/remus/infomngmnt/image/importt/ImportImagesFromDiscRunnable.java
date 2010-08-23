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

package org.remus.infomngmnt.image.importt;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.jface.operation.ModalContext;
import org.eclipse.osgi.util.NLS;
import org.eclipse.remus.Category;
import org.eclipse.remus.InfomngmntFactory;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.common.core.util.IdFactory;
import org.eclipse.remus.core.commands.CommandFactory;
import org.eclipse.remus.core.edit.DisposableEditingDomain;
import org.eclipse.remus.core.model.InformationStructureEdit;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.ui.operation.LoadFileToTmpFromPathRunnable;
import org.eclipse.remus.ui.progress.CancelableRunnable;
import org.eclipse.remus.util.CategoryUtil;

import org.remus.infomngmnt.image.ImagePlugin;
import org.remus.infomngmnt.image.operation.LoadImageRunnable;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ImportImagesFromDiscRunnable extends CancelableRunnable {

	public static final String[] VALID_IMAGE_EXTENSIONS = new String[] { "png", "gif", "jpg",
			"jpeg" };

	private final ImportImageObject obj;

	private File rootDirectory;

	private DisposableEditingDomain editingDomain;

	private Category rootCateogry;

	public ImportImagesFromDiscRunnable(final ImportImageObject obj) {
		this.obj = obj;

	}

	private void prepare(final File directory, final IProgressMonitor monitor, final Category cat)
			throws InvocationTargetException, InterruptedException {
		ModalContext.checkCanceled(monitor);
		File[] listFiles = directory.listFiles();
		monitor.beginTask(NLS.bind("Searching through folder \"{0}\"", directory.getName()),
				listFiles.length);
		for (File file : listFiles) {
			Path path = new Path(file.getAbsolutePath());
			if (isValidImageExtension(path)) {
				LoadFileToTmpFromPathRunnable copy2Tmp = new LoadFileToTmpFromPathRunnable();
				copy2Tmp.setFilePath(file.getAbsolutePath());
				copy2Tmp.run(new SubProgressMonitor(monitor, IProgressMonitor.UNKNOWN));

				monitor.setTaskName(NLS.bind("Adding image \"{0}\"", path.lastSegment()));
				InformationUnit createNewObject = InformationStructureEdit.newSession(
						ImagePlugin.TYPE_ID).newInformationUnit();

				LoadImageRunnable loadImageRunnable = new LoadImageRunnable(true);
				loadImageRunnable.setImagePath(copy2Tmp.getTmpFile().getLocation().toOSString());
				loadImageRunnable.setDomain(this.editingDomain);
				loadImageRunnable.setImageNode(createNewObject);
				// loadImageRunnable.setExecuteOnEditingDomain(false);
				loadImageRunnable.run(new SubProgressMonitor(monitor, IProgressMonitor.UNKNOWN));
				createNewObject.setLabel(file.getName());
				CompoundCommand createInfotype = CommandFactory.CREATE_INFOTYPE(createNewObject,
						cat, monitor);
				createInfotype.append(CommandFactory.addFileToInfoUnit(copy2Tmp.getTmpFile(),
						createNewObject, this.editingDomain));
				createInfotype.execute();
				this.editingDomain.getCommandStack().execute(createInfotype);
				this.editingDomain.getCommandStack().flush();
				monitor.worked(1);
			}
			if (file.isDirectory() && file.list(new FilenameFilter() {

				public boolean accept(final File dir, final String name) {
					return isValidImageExtension(new Path(name)) || dir.isDirectory();
				}

			}).length > 0) {
				if (this.obj.isCreateFolderStructure()) {
					Category createCategory = InfomngmntFactory.eINSTANCE.createCategory();
					createCategory.setId(IdFactory.createNewId(null));
					createCategory.setLabel(file.getName());
					Command command = CommandFactory.CREATE_CATEGORY(cat, createCategory,
							this.editingDomain);
					this.editingDomain.getCommandStack().execute(command);
					prepare(file, monitor, createCategory);
				} else {
					prepare(file, monitor, cat);
				}
			}
			if (listFiles.length == 0) {
				Command deleteCategory = CommandFactory.DELETE_CATEGORY(cat, this.editingDomain);
				deleteCategory.execute();
			}
		}

	}

	/**
	 * Checks wherether the given file is a valid and processable image.
	 * 
	 * @param fileName
	 *            the name of the file
	 * @return true if the file can be imported, else false.
	 */
	private boolean isValidImageExtension(final Path fileName) {
		for (String str : VALID_IMAGE_EXTENSIONS) {
			if (str.equalsIgnoreCase(fileName.getFileExtension())) {
				return true;
			}
		}
		return false;

	}

	@Override
	protected IStatus runCancelableRunnable(final IProgressMonitor monitor) {
		IEditingHandler service = ImagePlugin.getDefault().getServiceTracker().getService(
				IEditingHandler.class);
		this.editingDomain = service.createNewEditingDomain();
		ImagePlugin.getDefault().getServiceTracker().ungetService(service);
		this.rootDirectory = new File(this.obj.getDirectory());
		this.rootCateogry = CategoryUtil.findCategory(this.obj.getCategory(), true);
		try {
			prepare(this.rootDirectory, monitor, this.rootCateogry);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.editingDomain.dispose();
		return Status.OK_STATUS;
	}
}
