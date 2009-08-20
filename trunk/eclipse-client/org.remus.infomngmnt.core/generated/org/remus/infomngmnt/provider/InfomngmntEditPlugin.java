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
package org.remus.infomngmnt.provider;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import org.remus.infomngmnt.core.services.INotificationManagerManager;
import org.remus.infomngmnt.resources.util.ResourceUtil;

/**
 * This is the central singleton for the Infomngmnt edit plugin. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public final class InfomngmntEditPlugin extends EMFPlugin {
	/**
	 * Keep track of the singleton. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public static final InfomngmntEditPlugin INSTANCE = new InfomngmntEditPlugin();

	/**
	 * Keep track of the singleton. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	private static Implementation plugin;

	/**
	 * Create the instance. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public InfomngmntEditPlugin() {
		super(new ResourceLocator[] {});
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the singleton instance.
	 * @generated
	 */
	@Override
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the singleton instance.
	 * @generated
	 */
	public static Implementation getPlugin() {
		return plugin;
	}

	/**
	 * The actual implementation of the Eclipse <b>Plugin</b>. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated not
	 */
	public static class Implementation extends EclipsePlugin {

		private IPreferenceStore preferenceStore;
		private INotificationManagerManager service;

		/**
		 * Creates an instance. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
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
			this.preferenceStore = null;
			if (this.service != null) {
				this.service.shutdown();
			}
			ResourceUtil.cleanUp(new NullProgressMonitor());
			super.stop(context);
		}

		@Override
		public void start(final BundleContext context) throws Exception {
			super.start(context);
			this.service = getService(INotificationManagerManager.class);
			if (this.service != null) {
				this.service.init();
			}
		}

		public <T> T getService(final Class<T> serviceClass) {
			ServiceReference serviceReference = getBundle().getBundleContext().getServiceReference(
					serviceClass.getName());
			if (serviceReference != null) {
				Object service = getBundle().getBundleContext().getService(serviceReference);
				return (T) service;
			}
			return null;
		}

		/**
		 * Returns the preference store for this UI plug-in. This preference
		 * store is used to hold persistent settings for this plug-in in the
		 * context of a workbench. Some of these settings will be user
		 * controlled, whereas others may be internal setting that are never
		 * exposed to the user.
		 * <p>
		 * If an error occurs reading the preference store, an empty preference
		 * store is quietly created, initialized with defaults, and returned.
		 * </p>
		 * <p>
		 * <strong>NOTE:</strong> As of Eclipse 3.1 this method is no longer
		 * referring to the core runtime compatibility layer and so plug-ins
		 * relying on Plugin#initializeDefaultPreferences will have to access
		 * the compatibility layer themselves.
		 * </p>
		 * 
		 * @return the preference store
		 */
		public IPreferenceStore getPreferenceStore() {
			// Create the preference store lazily.
			if (this.preferenceStore == null) {
				this.preferenceStore = new ScopedPreferenceStore(new InstanceScope(), getBundle()
						.getSymbolicName());

			}
			return this.preferenceStore;
		}
	}

}
