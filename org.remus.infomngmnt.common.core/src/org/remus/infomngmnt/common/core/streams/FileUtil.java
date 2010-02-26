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

package org.remus.infomngmnt.common.core.streams;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class FileUtil {
	// Returns the contents of the file in a byte array.
	public static byte[] getBytesFromFile(final File file, final IProgressMonitor monitor)
			throws IOException {
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
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
			monitor.worked(1);
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	/**
	 * Recursive delete files.
	 */
	static void recursiveDelete(final File dirPath) {
		String[] ls = dirPath.list();
		if (ls != null) {
			for (int idx = 0; idx < ls.length; idx++) {
				File file = new File(dirPath, ls[idx]);
				if (file.isDirectory())
					recursiveDelete(file);
				file.delete();
			}
		}
	}

	/**
	 * Delete the directory and its contents
	 * 
	 * @exception java.io.IOException
	 *                if unable to remove the directory.
	 */
	public static void delete(final File dir) throws IOException {
		recursiveDelete(dir);
		if (dir.exists()) {
			dir.delete();
			// throw new IOException("Unable to delete directory hierarchy \"" +
			// dir.getAbsolutePath()
			// + "\"");
		}
	}

}
