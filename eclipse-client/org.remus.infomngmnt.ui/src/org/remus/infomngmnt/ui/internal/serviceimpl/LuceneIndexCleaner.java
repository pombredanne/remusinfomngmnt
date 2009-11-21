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

package org.remus.infomngmnt.ui.internal.serviceimpl;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import org.remus.infomngmnt.common.core.streams.FileUtil;
import org.remus.infomngmnt.core.ref.IIndexCleaner;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class LuceneIndexCleaner implements IIndexCleaner {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.ref.IIndexCleaner#cleanIndexAndRebuild(java
	 * .lang.String)
	 */
	public void cleanIndexAndRebuild(final String fileUrl) {
		try {
			File file = new File(fileUrl);
			FileUtil.delete(file);
			ResourcesPlugin.getWorkspace().build(IncrementalProjectBuilder.CLEAN_BUILD,
					new NullProgressMonitor());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.core.ref.IIndexCleaner#proceedCleanUp()
	 */
	public boolean proceedCleanUp() {
		final boolean[] returnValue = new boolean[1];
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				returnValue[0] = MessageDialog
						.openQuestion(
								Display.getDefault().getActiveShell(),
								"Internal error",
								"The application detected an internal misconfiguration. It is highly recommended to execute a repair. Execute repair now?");

			}
		});
		return returnValue[0];
	}

}
