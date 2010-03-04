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

import java.io.File;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.ui.editor.EditorActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class EditorUtil {

	public static String computeBinFileLocation(final InformationEditorInput input) {
		IFile file = input.getFile();
		IPath binPath = file.getProject().getLocation().append(
				new Path(ResourceUtil.BIN_FOLDER + File.separator + file.getProjectRelativePath()));
		return Pattern.compile(ResourceUtil.FILE_EXTENSION + "$").matcher(binPath.toOSString())
				.replaceFirst(ResourceUtil.HTML_EXTENSION);
	}

	public static IFile getBinFile(final InformationEditorInput input) {
		return getBinFile(input.getFile());
	}

	public static IFile getBinFile(final IFile file) {
		return file.getProject().getFile(
				new Path(ResourceUtil.BIN_FOLDER).append(Pattern.compile(
						ResourceUtil.FILE_EXTENSION + "$").matcher(file.getName()).replaceFirst(
						ResourceUtil.HTML_EXTENSION)));
	}

	public static void openInfoUnit(final String id) {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
					new InformationEditorInput(EditorActivator.getDefault().getApplicationService()
							.getItemById(id, null)), InformationEditor.ID);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
