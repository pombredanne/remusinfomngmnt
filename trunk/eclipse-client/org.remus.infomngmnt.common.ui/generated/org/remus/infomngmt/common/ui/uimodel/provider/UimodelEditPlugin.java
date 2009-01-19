/**
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * 
 * Contributors:
 *      Tom Seidel - initial API and implementation
 * 
 *
 * $Id$
 */
package org.remus.infomngmt.common.ui.uimodel.provider;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.ui.internal.util.BundleUtility;
import org.osgi.framework.BundleContext;

import org.remus.infomngmnt.common.ui.image.ResourceManager;

/**
 * This is the central singleton for the Uimodel edit plugin.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public final class UimodelEditPlugin extends EMFPlugin {
	/**
	 * Keep track of the singleton.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final UimodelEditPlugin INSTANCE = new UimodelEditPlugin();

	/**
	 * Keep track of the singleton.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static Implementation plugin;

	/**
	 * Create the instance.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UimodelEditPlugin() {
		super
		  (new ResourceLocator [] {
		   });
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the singleton instance.
	 * @generated
	 */
	@Override
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the singleton instance.
	 * @generated
	 */
	public static Implementation getPlugin() {
		return plugin;
	}

	/**
	 * The actual implementation of the Eclipse <b>Plugin</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static class Implementation extends EclipsePlugin {

		// The plug-in ID
		public static final String PLUGIN_ID = "org.remus.infomngmnt.common.ui"; //$NON-NLS-1$

		private IDialogSettings dialogSettings;

		/**
		 * The name of the dialog settings file (value
		 * <code>"dialog_settings.xml"</code>).
		 */
		private static final String FN_DIALOG_SETTINGS = "dialog_settings.xml"; //$NON-NLS-1$


		/**
		 * Creates an instance.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public Implementation() {
			super();

			// Remember the static instance.
			//
			plugin = this;
		}
		
		@Override
		public void stop(final BundleContext context) throws Exception {
			ResourceManager.dispose();
			super.stop(context);
		}

		/**
		 * Returns the dialog settings for this UI plug-in.
		 * The dialog settings is used to hold persistent state data for the various
		 * wizards and dialogs of this plug-in in the context of a workbench.
		 * <p>
		 * If an error occurs reading the dialog store, an empty one is quietly created
		 * and returned.
		 * </p>
		 * <p>
		 * Subclasses may override this method but are not expected to.
		 * </p>
		 *
		 * @return the dialog settings
		 */
		public IDialogSettings getDialogSettings() {
			if (this.dialogSettings == null) {
				loadDialogSettings();
			}
			return this.dialogSettings;
		}

		/**
		 * Loads the dialog settings for this plug-in.
		 * The default implementation first looks for a standard named file in the
		 * plug-in's read/write state area; if no such file exists, the plug-in's
		 * install directory is checked to see if one was installed with some default
		 * settings; if no file is found in either place, a new empty dialog settings
		 * is created. If a problem occurs, an empty settings is silently used.
		 * <p>
		 * This framework method may be overridden, although this is typically
		 * unnecessary.
		 * </p>
		 */
		protected void loadDialogSettings() {
			this.dialogSettings = new DialogSettings("Workbench"); //$NON-NLS-1$

			// bug 69387: The instance area should not be created (in the call to
			// #getStateLocation) if -data @none or -data @noDefault was used
			IPath dataLocation = getStateLocationOrNull();
			if (dataLocation != null) {
				// try r/w state area in the local file system
				String readWritePath = dataLocation.append(FN_DIALOG_SETTINGS)
				.toOSString();
				File settingsFile = new File(readWritePath);
				if (settingsFile.exists()) {
					try {
						this.dialogSettings.load(readWritePath);
					} catch (IOException e) {
						// load failed so ensure we have an empty settings
						this.dialogSettings = new DialogSettings("Workbench"); //$NON-NLS-1$
					}

					return;
				}
			}

			// otherwise look for bundle specific dialog settings
			URL dsURL = BundleUtility.find(getBundle(), FN_DIALOG_SETTINGS);
			if (dsURL == null) {
				return;
			}
		}

		/**
		 * FOR INTERNAL WORKBENCH USE ONLY.
		 * 
		 * Returns the path to a location in the file system that can be used
		 * to persist/restore state between workbench invocations.
		 * If the location did not exist prior to this call it will  be created.
		 * Returns <code>null</code> if no such location is available.
		 * 
		 * @return path to a location in the file system where this plug-in can
		 * persist data between sessions, or <code>null</code> if no such
		 * location is available.
		 * @since 3.1
		 */
		private IPath getStateLocationOrNull() {
			try {
				return getStateLocation();
			} catch (IllegalStateException e) {
				// This occurs if -data=@none is explicitly specified, so ignore this silently.
				// Is this OK? See bug 85071.
				return null;
			}
		}

	}
}
