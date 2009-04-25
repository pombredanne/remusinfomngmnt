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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.contact.ui.general.LoadImageRunnable;
import org.remus.infomngmnt.core.model.InformationUtil;

public class ImageManipulation {

	public static ImageData scaleImageToTarget(final ImageData inputData, final double targetX,
			final double targetY) {

		double imageX = inputData.width;
		double imageY = inputData.height;
		ImageData imageScaled = inputData;

		double deltaX = imageX / targetX;
		double deltaY = imageY / targetY;

		if (deltaX > deltaY) {
			int scaledX = (int) (imageX / deltaX);
			int scaledY = (int) (imageY / deltaX);
			imageScaled = inputData.scaledTo(scaledX, scaledY);
		} else if (deltaX <= deltaY) {
			int scaledX = (int) (imageX / deltaY);
			int scaledY = (int) (imageY / deltaY);
			imageScaled = inputData.scaledTo(scaledX, scaledY);
		}
		return imageScaled;
	}
	
	public static Image selectImageFromDialog(final Shell shell,
			final InformationUnit informationUnit, final String nodeName,
			final AdapterFactoryEditingDomain editingDomain, final int imageTargetX,
			final int imageTargetY) {

		FileDialog fd = new FileDialog(shell);
		fd.setFilterExtensions(new String[] { "*.jpg;*.jpeg;*.png;*.gif;*.bmp" });
		fd.setFilterNames(new String[] { "Supported Images (JPG,PNG,GIF,BMP)" });
		String open = fd.open();

		if (open != null) {
			LoadImageRunnable loadImage = new LoadImageRunnable();
			loadImage.setImagePath(open);
			loadImage.setImageNode(informationUnit, nodeName);
			loadImage.setDomain(editingDomain);
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

			ImageLoader loader = new ImageLoader();
			loader.load(loadImage.getFile().getPath());
			ImageData imageScaled = ImageManipulation.scaleImageToTarget(loader.data[0],
					imageTargetX, imageTargetY);
			loader.data[0] = imageScaled;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			loader.save(baos, SWT.IMAGE_PNG);

			Image image = new Image(null, imageScaled);

			InformationUnit childByType = InformationUtil.getChildByType(informationUnit, nodeName);
			SetCommand setCommand = new SetCommand(editingDomain, childByType,
					InfomngmntPackage.Literals.INFORMATION_UNIT__BINARY_VALUE, baos.toByteArray());
			editingDomain.getCommandStack().execute(setCommand);
			try {
				baos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return image;
		} else {
			return null;
		}
	}
}
