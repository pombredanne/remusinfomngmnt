/**
 * 
 */
package org.remus.infomngmnt.efs.file;

import java.io.File;
import java.net.URI;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.internal.filesystem.local.LocalFileSystem;
import org.eclipse.core.runtime.IPath;

/**
 * @author tmseidel
 * 
 */
@SuppressWarnings("restriction")
public class SecurityFileSystem extends LocalFileSystem {

	/**
	 * @return the cryptomanager
	 */
	static FileCryptoManager getCryptomanager() {
		return FileEncryptedSecurityProvider.getCryptoManager();
	}

	public static final String ENCRYPTION_FILE_SCHEME = "encryptedfile";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.filesystem.IFileSystem#fromLocalFile(java.io.File)
	 */
	@Override
	public IFileStore fromLocalFile(final File file) {
		return new EncryptedLocalFile(file, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.filesystem.IFileSystem#getStore(org.eclipse.core.runtime
	 * .IPath)
	 */
	@Override
	public IFileStore getStore(final IPath path) {
		return new EncryptedLocalFile(path.toFile(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.filesystem.IFileSystem#getStore(java.net.URI)
	 */
	@Override
	public IFileStore getStore(final URI uri) {
		return new EncryptedLocalFile(new File(uri.getSchemeSpecificPart()), this);
	}

}
