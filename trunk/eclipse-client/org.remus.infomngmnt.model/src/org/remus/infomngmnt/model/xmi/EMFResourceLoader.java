/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.model.xmi;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.model.service.IResourceLoader;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class EMFResourceLoader implements IResourceLoader {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.model.service.IResourceLoader#getObjectFromPlatformUri
	 * (java.lang.String, org.eclipse.emf.ecore.EClass)
	 */
	public <T extends EObject> T getObjectFromPlatformUri(final String uri,
			final EClass objectClas, final Map<String, ? extends EPackage> packageRegistry) {
		Resource resource = null;
		ResourceSet resourceSet = null;
		T returnValue = null;
		final org.eclipse.emf.common.util.URI createURI = org.eclipse.emf.common.util.URI
				.createPlatformPluginURI(uri, false);
		resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				org.eclipse.emf.ecore.resource.Resource.Factory.Registry.DEFAULT_EXTENSION,
				new XMLResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi",
				new XMIResourceFactoryImpl());
		if (packageRegistry == null) {
			resourceSet.getPackageRegistry().put(InfomngmntPackage.eNS_URI,
					InfomngmntPackage.eINSTANCE);
		} else {
			resourceSet.getPackageRegistry().putAll(packageRegistry);
		}
		resource = resourceSet.getResource(createURI, true);
		if (resource.getContents() != null && resource.getContents().size() > 0) {
			returnValue = (T) resource.getContents().get(0);
		}
		return returnValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.model.service.IResourceLoader#getObjectFromResourceUri
	 * (java.lang.String, org.eclipse.emf.ecore.EClass)
	 */
	public <T extends EObject> T getObjectFromResourceUri(final String uri,
			final EClass objectClas, final Map<String, ? extends EPackage> packageRegistry) {
		Resource resource = null;
		ResourceSet resourceSet = null;
		T returnValue = null;
		final org.eclipse.emf.common.util.URI createURI = org.eclipse.emf.common.util.URI
				.createFileURI(uri);
		resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				org.eclipse.emf.ecore.resource.Resource.Factory.Registry.DEFAULT_EXTENSION,
				new XMLResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi",
				new XMIResourceFactoryImpl());
		if (packageRegistry == null) {
			resourceSet.getPackageRegistry().put(InfomngmntPackage.eNS_URI,
					InfomngmntPackage.eINSTANCE);
		} else {
			resourceSet.getPackageRegistry().putAll(packageRegistry);
		}
		resource = resourceSet.getResource(createURI, true);
		if (resource.getContents() != null && resource.getContents().size() > 0) {
			returnValue = (T) resource.getContents().get(0);
		}
		return returnValue;
	}

}
