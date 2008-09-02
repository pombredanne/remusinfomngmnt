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

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.part.FileEditorInput;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.core.extension.InfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InformationEditorInput extends FileEditorInput implements IFileEditorInput {

	private final InformationUnitListItem infoFile;


	public InformationEditorInput(InformationUnitListItem infoFile) {
		super(ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(infoFile.getWorkspacePath())));
		this.infoFile = infoFile;

	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		InfoType infoTypeByType = InformationExtensionManager.getInstance().getInfoTypeByType(this.infoFile.getType());
		return infoTypeByType.getImage();
	}

	@Override
	public String getName() {
		return this.infoFile.getLabel();
	}



}
