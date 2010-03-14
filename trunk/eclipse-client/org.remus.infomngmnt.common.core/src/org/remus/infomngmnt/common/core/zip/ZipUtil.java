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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.remus.infomngmnt.common.core.streams.StreamUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ZipUtil {

	private static final int DEFAULT_COMPRESSION = 6;

	public static void main(final String[] args) throws IOException {

		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("Source folder: ");
		String sourceFolderPath = input.readLine();

		System.out.print("Target file: ");
		String targetFilePath = input.readLine();

		ZipUtil zipUtil = new ZipUtil((new File(sourceFolderPath)).listFiles(), new File(
				targetFilePath));
		zipUtil.createZipFile();

		System.out.println("ZIP file successfully created.");

	}

	public static void createZipFile(final List<Pair<File, String>> fileEntries, final File zipFile)
			throws IOException {
		ZipUtil.createZipFile(fileEntries, zipFile, ZipUtil.DEFAULT_COMPRESSION);
	}

	public static void createZipFile(final List<Pair<File, String>> fileEntries,
			final File zipFile, final int compression) throws IOException {

		ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
		zipOutputStream.setLevel(compression);

		try {
			for (Pair<File, String> fileEntry : fileEntries) {
				ZipEntry zipEntry = new ZipEntry(fileEntry.getValue());
				zipEntry.setTime(fileEntry.getKey().lastModified());
				zipOutputStream.putNextEntry(zipEntry);
				FileInputStream fileInputStream = new FileInputStream(fileEntry.getKey());
				StreamUtil.stream(fileInputStream, zipOutputStream);
				fileInputStream.close();
				zipOutputStream.closeEntry();
			}
		} finally {
			zipOutputStream.flush();
			zipOutputStream.close();
		}

	}

	public List<File> sources = null;

	public File zipFile = null;

	public Map<String, String> mapping = new HashMap<String, String>();

	public ZipUtil() {
	}

	public ZipUtil(final List<File> sources, final File zipFile) {
		this.setSources(sources);
		this.setZipFile(zipFile);
	}

	public ZipUtil(final File[] sources, final File zipFile) {
		this.setSources(sources);
		this.setZipFile(zipFile);
	}

	public List<File> getSources() {
		return this.sources;
	}

	public void setSources(final List<File> sources) {
		this.sources = sources;
	}

	public void setSources(final File[] sources) {
		this.setSources(Arrays.asList(sources));
	}

	public File getZipFile() {
		return this.zipFile;
	}

	public void setZipFile(final File zipFile) {
		this.zipFile = zipFile;
	}

	public Map<String, String> getMapping() {
		return this.mapping;
	}

	protected void setMapping(final Map<String, String> mapping) {
		this.mapping = mapping;
	}

	public void createZipFile() throws IOException {

		if (this.getSources() == null)
			throw new IllegalArgumentException("source files not set");

		if (this.getZipFile() == null)
			throw new IllegalArgumentException("zip file not set");

		ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(this
				.getZipFile()));
		zipOutputStream.setLevel(Deflater.BEST_COMPRESSION);

		for (File sourceFile : this.getSources())
			if (sourceFile.isFile())
				this.zipFile(sourceFile, sourceFile.getName(), zipOutputStream);
			else if (sourceFile.isDirectory())
				this.zipFolder(sourceFile, sourceFile.getName(), zipOutputStream);

		zipOutputStream.flush();
		zipOutputStream.close();

	}

	private void zipFile(final File file, String path, final ZipOutputStream zipOutputStream)
			throws IOException {

		if (file == null)
			throw new IllegalArgumentException("file cannot be null");
		else if (!file.exists())
			throw new IllegalArgumentException("file does not exist: " + file.getAbsolutePath());
		else if (!file.isFile())
			throw new IllegalArgumentException("file object does not represent a file: "
					+ file.getAbsolutePath());

		if (path == null)
			throw new IllegalArgumentException("path cannot be null");
		else if (path.length() == 0)
			throw new IllegalArgumentException("path cannot be empty");

		if (zipOutputStream == null)
			throw new IllegalArgumentException("zip output stream cannot be null");

		if (this.getMapping().get(path) != null)
			path = this.getMapping().get(path);
		ZipEntry zipEntry = new ZipEntry(path);
		zipEntry.setTime(file.lastModified());
		zipOutputStream.putNextEntry(zipEntry);

		FileInputStream fileInputStream = new FileInputStream(file);
		StreamUtil.stream(fileInputStream, zipOutputStream);
		fileInputStream.close();

		zipOutputStream.closeEntry();

	}

	private void zipFolder(final File folder, final String path,
			final ZipOutputStream zipOutputStream) throws IOException {

		if (folder == null)
			throw new IllegalArgumentException("folder cannot be null");
		else if (!folder.exists())
			throw new IllegalArgumentException("folder does not exist: " + folder.getAbsolutePath());
		else if (!folder.isDirectory())
			throw new IllegalArgumentException("folder object does not represent a folder: "
					+ folder.getAbsolutePath());

		if (path == null)
			throw new IllegalArgumentException("path cannot be null");
		else if (path.length() == 0)
			throw new IllegalArgumentException("path cannot be empty");

		if (zipOutputStream == null)
			throw new IllegalArgumentException("zip output stream cannot be null");

		for (File entry : folder.listFiles())
			if (entry.isFile())
				this.zipFile(entry, path + "/" + entry.getName(), zipOutputStream);
			else if (entry.isDirectory())
				this.zipFolder(entry, path + "/" + entry.getName(), zipOutputStream);

	}

}
