/**
 * 
 */
package org.remus.infomngmnt.efs.file;

import java.io.File;
import java.net.URI;

import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ide.fileSystem.FileSystemContributor;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.dialogs.IDEResourceInfoUtils;

/**
 * @author tmseidel
 * 
 */
@SuppressWarnings("restriction")
public class EncryptedFileSystemContributor extends FileSystemContributor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.ide.fileSystem.FileSystemContributor#browseFileSystem(
	 * java.lang.String, org.eclipse.swt.widgets.Shell)
	 */
	@Override
	public URI browseFileSystem(final String initialPath, final Shell shell) {

		DirectoryDialog dialog = new DirectoryDialog(shell);
		dialog.setMessage(IDEWorkbenchMessages.ProjectLocationSelectionDialog_directoryLabel);

		if (!initialPath.equals(IDEResourceInfoUtils.EMPTY_STRING)) {
			IFileInfo info = IDEResourceInfoUtils.getFileInfo(initialPath);
			if (info != null && info.exists()) {
				dialog.setFilterPath(initialPath);
			}
		}

		String selectedDirectory = dialog.open();
		if (selectedDirectory == null) {
			return null;
		}
		return convertURI(new File(selectedDirectory).toURI());

	}

	private URI convertURI(final URI fileURI) {
		String decodedUriString = URIUtil.toDecodedString(fileURI);
		if (fileURI.getScheme() != null) {
			return URIUtil.toURI(decodedUriString.replaceFirst(fileURI.getScheme(),
					SecurityFileSystem.ENCRYPTION_FILE_SCHEME), false);
		}
		return URIUtil.toURI(SecurityFileSystem.ENCRYPTION_FILE_SCHEME + ":" + decodedUriString,
				false);
	}

	@Override
	public URI getURI(final String string) {
		return URIUtil.toURI(string, false);
	}

}
