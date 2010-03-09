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

package org.remus.infomngmnt.mindmap.extension;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.xmind.ui.internal.editor.MindMapEditor;

import org.remus.infomngmnt.BinaryReference;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.mindmap.MindmapActivator;
import org.remus.infomngmnt.ui.editors.editpage.AbstractDelegationEditorPart;
import org.remus.infomngmnt.util.InformationUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MindMapPageContributor extends AbstractDelegationEditorPart {

	/**
	 * 
	 */
	public MindMapPageContributor() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.extension.AbstractDelegationEditorPart#createEditor
	 * ()
	 */
	@Override
	public IEditorPart createEditor() {
		return new MindMapEditor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.ui.extension.AbstractDelegationEditorPart#
	 * createEditorInput(org.remus.infomngmnt.InformationUnit)
	 */
	@Override
	public IEditorInput createEditorInput(final InformationUnit unit) {
		InformationStructureRead read = InformationStructureRead.newSession(unit);
		List<BinaryReference> binaryReferences = read.getBinaryReferences(
				MindmapActivator.INFO_TYPE_ID, false);
		if (binaryReferences.size() > 0) {
			IFile binaryReferenceToFile = InformationUtil.binaryReferenceToFile(binaryReferences
					.get(0), unit);
			return new FileEditorInput(binaryReferenceToFile);
		}
		return null;
	}

}
