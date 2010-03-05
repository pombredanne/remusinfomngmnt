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

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import org.remus.infomngmnt.core.services.IEditingHandler;
import org.remus.infomngmnt.services.RemusServiceTracker;

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
	 * Create the instance.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public InfomngmntEditPlugin() {
		super
		  (new ResourceLocator [] {
		   });
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the singleton instance.
	 * @generated
	 */
	@Override
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
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
	 * @generated
	 */
	public static class Implementation extends EclipsePlugin {

		private RemusServiceTracker serviceTracker;
		private IEditingHandler editService;

		/**
		 * Creates an instance.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		public Implementation() {
			super();

			// Remember the static instance.
			//
			plugin = this;
		}

		@Override
		public void start(final BundleContext context) throws Exception {
			super.start(context);
			this.serviceTracker = new RemusServiceTracker(getBundle());
		}

		/**
		 * @param <T>
		 * @param serviceClass
		 * @return
		 * @generated NOT
		 */
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
		 * @return the serviceTracker
		 */
		public final RemusServiceTracker getServiceTracker() {
			return this.serviceTracker;
		}

		/**
		 * @return the editService
		 */
		public final IEditingHandler getEditService() {
			if (this.editService == null) {
				this.editService = this.serviceTracker.getService(IEditingHandler.class);
			}
			return this.editService;
		}

	}

}
