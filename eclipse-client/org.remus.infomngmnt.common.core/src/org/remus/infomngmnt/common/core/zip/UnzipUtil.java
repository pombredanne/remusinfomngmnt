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
package org.remus.infomngmnt.common.core.zip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.remus.infomngmnt.common.core.streams.StreamUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class UnzipUtil {

	private File zipFile = null;
	private File outputFolder = null;

	public UnzipUtil() {
	}

	public UnzipUtil(final File zipFile) {
		this.setZipFile(zipFile);
	}

	public UnzipUtil(final File zipFile, final File outputFolder) {
		this.setZipFile(zipFile);
		this.setOutputFolder(outputFolder);
	}

	public File getZipFile() {
		return this.zipFile;
	}

	public void setZipFile(final File zipFile) {
		this.zipFile = zipFile;
	}

	public File getOutputFolder() {
		return this.outputFolder;
	}

	public void setOutputFolder(final File outputFolder) {
		this.outputFolder = outputFolder;
	}

	public void unzipAll() throws IOException {

		if (this.getZipFile() == null)
			throw new IllegalArgumentException("zip file not set");

		if (this.getOutputFolder() == null)
			throw new IllegalArgumentException("output folder not set");

		ZipFile zipFile = new ZipFile(this.getZipFile());
		for (Enumeration<? extends ZipEntry> zipEntries = zipFile.entries(); zipEntries
				.hasMoreElements();) {

			ZipEntry zipEntry = zipEntries.nextElement();

			if (zipEntry.isDirectory())
				continue;

			File outputFile = new File(this.getOutputFolder().getAbsolutePath() + File.separator
					+ zipEntry.getName());

			File outputFileFolder = outputFile.getParentFile();

			if (!outputFileFolder.exists())
				outputFileFolder.mkdirs();

			InputStream zipEntryInputStream = zipFile.getInputStream(zipEntry);

			FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

			StreamUtil.stream(zipEntryInputStream, fileOutputStream);

			fileOutputStream.flush();
			fileOutputStream.close();

			zipEntryInputStream.close();

			if (zipEntry.getTime() >= 0)
				outputFile.setLastModified(zipEntry.getTime());

		}

	}

	public List<String> getFileNames() throws IOException {

		if (this.getZipFile() == null)
			throw new IllegalArgumentException("zip file not set");

		List<String> entryNames = new ArrayList<String>();

		ZipFile zipFile = new ZipFile(this.getZipFile());
		for (Enumeration<? extends ZipEntry> zipEntries = zipFile.entries(); zipEntries
				.hasMoreElements();) {
			ZipEntry zipEntry = zipEntries.nextElement();
			if (!zipEntry.isDirectory())
				entryNames.add(zipEntry.getName());
		}

		return entryNames;

	}

	public ZipEntry getZipEntry(final String name) throws IOException {
		return (new ZipFile(this.getZipFile())).getEntry(name);
	}

}
