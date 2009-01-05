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

package org.remus.infomngmnt.image.operation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.model.StatusCreator;
import org.remus.infomngmnt.core.progress.CancelableRunnable;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class LoadImageRunnable extends CancelableRunnable {
	
	private String imagePath;
	private InformationUnit rawImageDataNode;
	private File file;

	public File getFile() {
		return this.file;
	}

	public void setImagePath(final String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	protected IStatus runCancelableRunnable(final IProgressMonitor monitor) {
		monitor.beginTask("Checking filename...", IProgressMonitor.UNKNOWN);
		this.file = new File(this.imagePath);
		if (this.file.exists() && this.file.isFile()) {
			monitor.beginTask(NLS.bind("Reading file {0}", this.file.getName()), (int) this.file.length());
			try {
				this.rawImageDataNode.setBinaryValue(getBytesFromFile(this.file, monitor));
				return Status.OK_STATUS;
			} catch (IOException e) {
				return StatusCreator.newStatus("File not exisits or is not accessible");
			}
		}
		return StatusCreator.newStatus("File not exisits or is not accessible");
	}

	public void setRawDataNode(final InformationUnit rawImageDataNode) {
		this.rawImageDataNode = rawImageDataNode;
		// TODO Auto-generated method stub
		
	}
	
	 // Returns the contents of the file in a byte array.
    public static byte[] getBytesFromFile(final File file, final IProgressMonitor monitor) throws IOException {
        InputStream is = new FileInputStream(file);
    
        // Get the size of the file
        long length = file.length();
    
        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
    
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
    
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
            monitor.worked(1);
        }
    
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
    
        // Close the input stream and return bytes
        is.close();
        return bytes;
    }


}
