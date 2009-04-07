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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.contact.ui.general.LoadImageRunnable;

public class ImageManipulation {

	public static ImageData scaleImageToTarget(ImageData inputData, double targetX, double targetY) {
		
		double imageX = inputData.width;
		double imageY = inputData.height;
		ImageData imageScaled = inputData;
		
		double deltaX = imageX/targetX;
		double deltaY = imageY/targetY;
		
		if (deltaX > deltaY) {
			int scaledX = (int) (imageX/deltaX);
			int scaledY = (int) (imageY/deltaX);
			imageScaled = inputData.scaledTo(scaledX, scaledY);
		}
		else if (deltaX <= deltaY) {
			int scaledX = (int) (imageX/deltaY);
			int scaledY = (int) (imageY/deltaY);
			imageScaled = inputData.scaledTo(scaledX, scaledY);
		}	
		return imageScaled;	
	}
	
	public static Image selectImageFromDialog(Shell shell, InformationUnit informationUnit, String nodeName, AdapterFactoryEditingDomain editingDomain, int imageTargetX, int imageTargetY) {
		
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
			ImageData imageData = new ImageData(loadImage.getFile().getPath());
			ImageData imageScaled = ImageManipulation.scaleImageToTarget(imageData, imageTargetX, imageTargetY);
			Image image = new Image(null, imageScaled);
//			lb_Image.setImage(image);
			
//			InformationUnit childByType = InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_NAME_RAWDATA);
//			if(childByType != null && childByType.getBinaryValue() != null) {
//				childByType.setBinaryValue(image.getImageData().data);
//			}
//			loadImage.setImageNode(informationUnit);
			return image;
		}
		else 
			return null;
	}
}
