/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.ui.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.part.FileEditorInput;
import org.remus.infomngmnt.AbstractInformationUnit;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.model.EditingUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InformationEditorInput extends FileEditorInput implements IFileEditorInput {


	private String type;
	private String label;


	public InformationEditorInput(InformationUnitListItem infoFile) {
		this(ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(infoFile.getWorkspacePath())));
		setLabels(infoFile);
	}


	public InformationEditorInput(IFile file) {
		super(file);
		InformationUnit objectFromFile = EditingUtil.getInstance().getObjectFromFile(
				file, InfomngmntPackage.eINSTANCE.getInformationUnit(), false);
		setLabels(objectFromFile);
		objectFromFile.eResource().unload();

	}

	private void setLabels(AbstractInformationUnit unit) {
		this.type = unit.getType();
		this.label = unit.getLabel();
	}


	@Override
	public ImageDescriptor getImageDescriptor() {
		IInfoType infoTypeByType = InformationExtensionManager.getInstance().getInfoTypeByType(this.type);
		return infoTypeByType.getImageDescriptor();
	}

	@Override
	public String getName() {
		return this.label;
	}

	@Override
	public String getToolTipText() {
		return this.label;
	}



}
