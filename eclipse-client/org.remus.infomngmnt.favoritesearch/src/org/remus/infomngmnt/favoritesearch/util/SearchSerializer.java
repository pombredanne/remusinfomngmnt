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

package org.remus.infomngmnt.favoritesearch.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.model.service.ResourceConstants;
import org.eclipse.remus.search.Search;
import org.eclipse.remus.search.SearchFactory;
import org.eclipse.remus.search.SearchPackage;
import org.eclipse.remus.search.SearchResult;

import org.remus.infomngmnt.favoritesearch.FavoriteSearchActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SearchSerializer {

	public static byte[] serialize(final Search search) {
		IEditingHandler service = FavoriteSearchActivator.getDefault().getServiceTracker()
				.getService(IEditingHandler.class);
		EList<SearchResult> result = search.getResult();
		for (SearchResult searchResult : result) {
			searchResult.eUnset(SearchPackage.Literals.SEARCH_RESULT__HIGHLIGHT_ATTRIBUTES);
			searchResult.eUnset(SearchPackage.Literals.SEARCH_RESULT__KEYWORDS);
			searchResult.eUnset(SearchPackage.Literals.SEARCH_RESULT__TEXT);
		}
		byte[] saveObjectToByte = service.saveObjectToByte(search);
		FavoriteSearchActivator.getDefault().getServiceTracker().ungetService(service);
		return saveObjectToByte;
	}

	public static Search deserialize(final byte[] bytes) {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				org.eclipse.emf.ecore.resource.Resource.Factory.Registry.DEFAULT_EXTENSION,
				new XMLResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(SearchPackage.eNS_URI, SearchPackage.eINSTANCE);

		Resource createResource = resourceSet.createResource(URI.createURI("deserialized"));
		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		try {
			createResource.load(inputStream, ResourceConstants.SAVE_OPTIONS);
			return (Search) createResource.getContents().get(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SearchFactory.eINSTANCE.createSearch();

	}

}
