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
package org.remus.infomngmnt.efs.file;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.internal.filesystem.local.LocalFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;

import org.remus.infomngmnt.core.model.StatusCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
@SuppressWarnings("restriction")
public class EncryptedLocalFile extends LocalFile {

	private final IFileSystem fileSystem;

	public EncryptedLocalFile(final File file, final IFileSystem fileSystem) {
		super(file);
		this.fileSystem = fileSystem;
	}

	@Override
	public InputStream openInputStream(final int options, final IProgressMonitor monitor)
			throws CoreException {
		// TODO implement error handling and monitor
		try {
			return SecurityFileSystem.getCryptomanager().getDecryptedInputStream(this.file);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CoreException(StatusCreator.newStatus(e.getMessage()));
		}
	}

	@Override
	public OutputStream openOutputStream(final int options, final IProgressMonitor monitor)
			throws CoreException {
		// TODO implement error handling and monitor
		try {
			return SecurityFileSystem.getCryptomanager().getEncryptedOutputStream(this.file);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CoreException(StatusCreator.newStatus(e.getMessage()));
		}
		// return super.openOutputStream(options, monitor);
	}

	private URI convertURI(final URI fileURI) {
		String decodedUriString = URIUtil.toDecodedString(fileURI);
		if (fileURI.getScheme() == null) {
			return URIUtil.toURI(decodedUriString.replaceFirst(fileURI.getScheme(),
					SecurityFileSystem.ENCRYPTION_FILE_SCHEME));
		}
		return URIUtil.toURI(SecurityFileSystem.ENCRYPTION_FILE_SCHEME + ":" + decodedUriString);
	}

	@Override
	public IFileStore getChild(final IPath path) {
		return new EncryptedLocalFile(new File(this.file, path.toOSString()), this.fileSystem);
	}

	@Override
	public IFileStore getFileStore(final IPath path) {
		return new EncryptedLocalFile(new Path(this.file.getPath()).append(path).toFile(),
				this.fileSystem);
	}

	@Override
	public IFileStore getChild(final String name) {
		return new EncryptedLocalFile(new File(this.file, name), this.fileSystem);
	}

	@Override
	public IFileInfo fetchInfo() {
		// IFileInfo fetchInfo = super.fetchInfo();
		// return new DelegatingFileInfo(fetchInfo);
		return super.fetchInfo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.filesystem.IFileStore#getFileSystem()
	 */
	@Override
	public IFileSystem getFileSystem() {
		return this.fileSystem;
	}

	@Override
	public URI toURI() {
		URI fileURI = URIUtil.toURI(this.filePath);
		String decodedUriString = URIUtil.toDecodedString(fileURI);
		if (fileURI.getScheme() != null) {
			return URIUtil.toURI(decodedUriString.replaceFirst(fileURI.getScheme(),
					SecurityFileSystem.ENCRYPTION_FILE_SCHEME), false);
		}
		return URIUtil.toURI(SecurityFileSystem.ENCRYPTION_FILE_SCHEME + ":" + decodedUriString,
				false);
	}
}
