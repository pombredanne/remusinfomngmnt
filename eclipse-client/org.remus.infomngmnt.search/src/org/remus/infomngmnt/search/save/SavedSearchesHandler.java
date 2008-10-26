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

package org.remus.infomngmnt.search.save;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;

import org.remus.infomngmnt.search.LatestSearchStrings;
import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.search.SearchFactory;
import org.remus.infomngmnt.search.SearchPackage;
import org.remus.infomngmnt.search.provider.SearchPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SavedSearchesHandler {

	public static final String LATEST_SEARCH_FILENAME = "latestSearch.xml"; //$NON-NLS-1$
	public static final String LATEST_SEARCHSTRINGS_FILENAME = "latestSearchStrings.xml"; //$NON-NLS-1$

	public Search getLatestSearch() {
		Search search = getObjectFromUri(
				SearchPlugin.getPlugin().getStateLocation().append(LATEST_SEARCH_FILENAME), SearchPackage.Literals.SEARCH);
		return search;
	}

	public void saveLatestSearch(Search search) {
		saveObjectToResource(
				SearchPlugin.getPlugin().getStateLocation().append(LATEST_SEARCH_FILENAME), search);
	}

	public LatestSearchStrings getLatestSearchStrings() {
		return getObjectFromUri(
				SearchPlugin.getPlugin().getStateLocation().append(LATEST_SEARCHSTRINGS_FILENAME), SearchPackage.Literals.LATEST_SEARCH_STRINGS);
	}

	public void saveSearchStrings(LatestSearchStrings strings) {
		saveObjectToResource(
				SearchPlugin.getPlugin().getStateLocation().append(LATEST_SEARCHSTRINGS_FILENAME), strings);
	}

	/**
	 * Returns a {@link Resource} object from the given uri
	 * @param uri the uri
	 * @param cache
	 * @return the contents of the file
	 */
	@SuppressWarnings("unchecked")
	public <T extends EObject> T getObjectFromUri(final IPath uri, final EClass objectClas) {
		final org.eclipse.emf.common.util.URI createURI = org.eclipse.emf.common.util.URI.createFileURI(uri.toString());
		return getObjectFromUri(createURI, objectClas);
	}

	public void saveObjectToResource(final IPath target, final EObject object) {
		try {
			final org.eclipse.emf.common.util.URI createURI = org.eclipse.emf.common.util.URI.createFileURI(target.toString());
			ResourceSet resourceSet = new ResourceSetImpl();
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put
			(Resource.Factory.Registry.DEFAULT_EXTENSION,
					new XMLResourceFactoryImpl());
			resourceSet.getPackageRegistry().put
			(SearchPackage.eNS_URI,
					SearchPackage.eINSTANCE);
			File file = new File(createURI.toFileString());
			if (file.exists()) {
				file.delete();
			}
			Resource resource = resourceSet.createResource(createURI);
			resource.getContents().add(object);
			resource.save(Collections.singletonMap(XMLResource.OPTION_ENCODING, "UTF-8"));
		} catch (final IOException e) {
			// FIXME Error-Handling
			e.printStackTrace();
		}
	}

	public <T extends EObject> T getObjectFromUri(final URI uri, final EClass objectClas) {
		Resource resource = null;
		ResourceSet resourceSet = null;
		T returnValue;
		File file = new File(uri.toFileString());
		resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put
		(org.eclipse.emf.ecore.resource.Resource.Factory.Registry.DEFAULT_EXTENSION,
				new XMLResourceFactoryImpl());
		resourceSet.getPackageRegistry().put
		(SearchPackage.eNS_URI,
				SearchPackage.eINSTANCE);

		if (file.exists()) {
			try {
				resource = resourceSet.getResource(uri, true);
			} catch (final Exception e) {
				resource = resourceSet.getResource(uri, false);
			}
			returnValue = (T) resource.getContents().get(0);
		} else {
			final EObject create = SearchFactory.eINSTANCE.create(objectClas);
			resource = resourceSet.createResource(uri);
			resource.getContents().add(create);
			try {
				resource.save(Collections.singletonMap(XMLResource.OPTION_ENCODING, "UTF-8"));
			} catch (final IOException e) {
				// FIXME What to do here?
			}
			returnValue = (T) create;
		}
		return returnValue;
	}

}
