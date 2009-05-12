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

import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.search.SearchFactory;
import org.remus.infomngmnt.search.SearchPackage;
import org.remus.infomngmnt.search.SearchResult;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SearchSerializer {

	public static byte[] serialize(final Search search) {
		EList<SearchResult> result = search.getResult();
		for (SearchResult searchResult : result) {
			searchResult.eUnset(SearchPackage.Literals.SEARCH_RESULT__HIGHLIGHT_ATTRIBUTES);
			searchResult.eUnset(SearchPackage.Literals.SEARCH_RESULT__KEYWORDS);
			searchResult.eUnset(SearchPackage.Literals.SEARCH_RESULT__TEXT);
		}
		return EditingUtil.getInstance().saveObjectToByte(search);
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
			createResource.load(inputStream, EditingUtil.SAVE_OPTIONS);
			return (Search) createResource.getContents().get(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SearchFactory.eINSTANCE.createSearch();

	}

}
