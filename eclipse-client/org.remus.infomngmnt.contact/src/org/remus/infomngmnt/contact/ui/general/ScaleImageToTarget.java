package org.remus.infomngmnt.contact.ui.general;

import org.eclipse.swt.graphics.ImageData;

public class ScaleImageToTarget {

	public static ImageData scale(ImageData inputData, double targetX, double targetY) {
		
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
	
}
