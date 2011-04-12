/*******************************************************************************
 * Copyright (c) 2009 Jan Hartwig, FEB Radebeul
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Jan Hartwig - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.contact.core;

/**
 * @author Jan Hartwig <jhartwig@feb-radebeul.de>
 * 
 */
import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.remus.InformationUnit;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import org.remus.infomngmnt.contact.messages.Messages;
import org.remus.infomngmnt.contact.ui.general.LoadImageRunnable;

public class ImageManipulation {

	public static void selectImageFromDialog(final Shell shell,
			final InformationUnit informationUnit, final String nodeName,
			final AdapterFactoryEditingDomain editingDomain, final int imageTargetX,
			final int imageTargetY) {

		FileDialog fd = new FileDialog(shell);
		fd.setFilterExtensions(new String[] { "*.jpg;*.jpeg;*.png;*.gif;*.bmp" }); //$NON-NLS-1$
		fd.setFilterNames(new String[] { Messages.ImageManipulation_SupportedImages });
		String open = fd.open();

		if (open != null) {
			LoadImageRunnable loadImage = new LoadImageRunnable();
			loadImage.setImagePath(open);
			loadImage.setImageNode(informationUnit, nodeName);
			loadImage.setDomain(editingDomain);
			loadImage.setScaling(new Point(imageTargetX, imageTargetY));
			ProgressMonitorDialog pmd = new ProgressMonitorDialog(shell);
			try {
				pmd.run(true, false, loadImage);
			} catch (InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
