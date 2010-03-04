/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.core.internal.extension;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationStructureDefinition;
import org.remus.infomngmnt.core.CorePlugin;
import org.remus.infomngmnt.core.create.PostCreationHandler;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.model.service.IResourceLoader;

/**
 * A object represenation of a registered information type. This class is
 * 
 * @author Tom Seidel <toms@tomosch.de>
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 */
public class InfoType implements IInfoType {

	/** The configuration element which comes from the plugin-registry **/
	private final IConfigurationElement configurationElement;
	/** The info-types image **/
	private final String type;
	private PostCreationHandler createFactory;
	private final String contributor;
	private final String createFactoryClass;
	private final String name;
	private final boolean buildHtml;
	private final boolean excludeFromIndex;
	private final String strucuturePath;
	private volatile InformationStructureDefinition structureDefinition;

	/**
	 * Creates
	 * 
	 * @param configurationElement
	 * @param contributor
	 * @param type
	 * @param createFactoryClass
	 * @param imageFilePath
	 */
	public InfoType(final IConfigurationElement configurationElement, final String contributor,
			final String name, final String type, final String createFactoryClass,
			final String imageFilePath, final boolean buildHtml, final boolean excludeFromIndex,
			final String strucuturePath) {
		this.configurationElement = configurationElement;
		this.contributor = contributor;
		this.name = name;
		this.type = type;
		this.createFactoryClass = createFactoryClass;
		this.buildHtml = buildHtml;
		this.excludeFromIndex = excludeFromIndex;
		this.strucuturePath = strucuturePath;

	}

	/**
	 * Returns the creation factory. Every info-type contributes a separate
	 * implementation of how-to create new information objects
	 * 
	 * @return the factory which creates new info-objects.
	 */
	public PostCreationHandler getPostCreationHandler() {
		if (this.createFactory == null) {
			try {
				if (this.createFactoryClass != null && this.createFactoryClass.trim().length() > 0) {
					this.createFactory = (PostCreationHandler) this.configurationElement
							.createExecutableExtension(this.createFactoryClass);
					this.createFactory.setInfoTypeId(this.type);
					this.createFactory.setStrucutureDefinition(getStructureDefinition());
				}
			} catch (final CoreException e) {
				// TODO Logging
			}
		}
		return this.createFactory;
	}

	public AbstractInformationRepresentation getInformationRepresentation() {
		try {
			return (AbstractInformationRepresentation) this.configurationElement
					.createExecutableExtension(InformationExtensionManager.PRESENTATION_ATT);
		} catch (CoreException e) {
			return null;
		}
	}

	public String getType() {
		return this.type;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * @return the buildHtml
	 */
	public boolean isBuildHtml() {
		return this.buildHtml;
	}

	public boolean isExcludeFromIndex() {
		return this.excludeFromIndex;
	}

	public final InformationStructureDefinition getStructureDefinition() {
		if (this.structureDefinition == null) {
			ServiceReference resourceLoader = null;
			try {
				IPath append = new Path(this.contributor).append(this.strucuturePath);
				// URL openStream =
				// FileLocator.find(Platform.getBundle(this.contributor), new
				// Path(
				// this.strucuturePath), null);
				resourceLoader = getResourceLoader();
				IResourceLoader loader = (IResourceLoader) Platform.getBundle(CorePlugin.PLUGIN_ID)
						.getBundleContext().getService(resourceLoader);
				this.structureDefinition = loader.getObjectFromPlatformUri(append.toString(),
						InfomngmntPackage.Literals.INFORMATION_STRUCTURE_DEFINITION, null);
			} catch (Exception e) {
				throw new IllegalStateException(NLS.bind(
						"Strucuture definition not accessible for Information-Type \'\'{0}\'\'",
						this.type));
			} finally {
				if (resourceLoader != null) {
					Platform.getBundle(CorePlugin.PLUGIN_ID).getBundleContext().ungetService(
							resourceLoader);
				}
			}
		}
		return this.structureDefinition;
	}

	private ServiceReference getResourceLoader() {
		Bundle bundle = Platform.getBundle(CorePlugin.PLUGIN_ID);
		ServiceReference serviceReference = bundle.getBundleContext().getServiceReference(
				IResourceLoader.class.getName());
		if (serviceReference != null) {
			return serviceReference;
		}
		return null;
	}

}
