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

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Shell;

import org.remus.infomngmnt.efs.extension.AbstractSecurityProvider;
import org.remus.infomngmnt.efs.ui.FolderPrompt;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class FileEncryptedSecurityProvider extends AbstractSecurityProvider {

	private static FileCryptoManager cryptoManager;

	private final IPreferenceStore store;

	public FileEncryptedSecurityProvider() {
		this.store = EFSFileActivator.getDefault().getPreferenceStore();
	}

	private File getKeyFile() {
		return new File(this.store.getString(PreferenceInitializer.LOCATION_KEY_FILE)
				+ File.separator + this.store.getString(PreferenceInitializer.KEY_FILENAME));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.efs.extension.AbstractSecurityProvider#initialize
	 * (org.eclipse.swt.widgets.Shell)
	 */
	@Override
	public boolean initialize(final Shell parentShell) {
		FolderPrompt prompt = new FolderPrompt(parentShell, true);
		if (prompt.open() == IDialogConstants.OK_ID) {
			try {
				cryptoManager = new FileCryptoManager(getKeyFile());
				return true;
			} catch (Exception e) {
				// do nothing.
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.efs.extension.AbstractSecurityProvider#
	 * initializeForProject(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	public boolean initializeForProject(final Shell shell) {
		try {
			cryptoManager = new FileCryptoManager(getKeyFile());
			return true;
		} catch (Exception e) {
			FolderPrompt prompt = new FolderPrompt(shell, false);
			if (prompt.open() == IDialogConstants.OK_ID) {
				try {
					cryptoManager = new FileCryptoManager(getKeyFile());
					return true;
				} catch (Exception e1) {
					// do nothing
				}
			}
		}

		return false;
	}

	static FileCryptoManager getCryptoManager() {
		return cryptoManager;
	}

}
