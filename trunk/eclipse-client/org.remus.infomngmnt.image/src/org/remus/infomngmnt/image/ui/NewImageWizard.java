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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.ui.IWorkbench;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RuleValue;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.image.ImagePlugin;
import org.remus.infomngmnt.image.operation.LoadImageRunnable;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.ui.extension.AbstractCreationPreferencePage;
import org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard;

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
			EditingDomain editingDomain = EditingUtil.getInstance().createNewEditingDomain();
			LoadImageRunnable loadImageRunnable = new LoadImageRunnable();
			loadImageRunnable.setImagePath(tmpFile.getLocation().toOSString());
			loadImageRunnable.setImageNode(this.newElement);
			loadImageRunnable.setDomain(editingDomain);
			editingDomain.getCommandStack().flush();
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

	public void setDefaults(final Object value, final RuleValue ruleValue) {
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

		}
		InformationUnit predefinedCategory = InformationUtil.getChildByType(ruleValue,
				AbstractCreationPreferencePage.NODENAME_PREDEFINED_CATEGORY);
		InformationUnit predefinedName = InformationUtil.getChildByType(ruleValue,
				AbstractCreationPreferencePage.NODENAME_PREDEFINED_NAME);
		if (predefinedCategory != null) {
			this.page1.setCategoryString(predefinedCategory.getStringValue());
		}
		if (predefinedName != null) {
			this.newElement.setLabel(predefinedName.getStringValue());
		}

	}

}
