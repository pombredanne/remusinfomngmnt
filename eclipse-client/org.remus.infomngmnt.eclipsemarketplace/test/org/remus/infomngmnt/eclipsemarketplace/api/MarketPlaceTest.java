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

package org.remus.infomngmnt.eclipsemarketplace.api;

import java.io.IOException;

import junit.framework.TestCase;

import org.remus.infomngmnt.eclipsemarketplace.api.beans.CategoryEntry;
import org.remus.infomngmnt.eclipsemarketplace.api.beans.MarketPlaceElement;
import org.remus.infomngmnt.eclipsemarketplace.api.beans.MarketPlaceRoot;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MarketPlaceTest extends TestCase {

	/**
	 * Test method for
	 * {@link org.remus.infomngmnt.eclipsemarketplace.api.MarketPlace#getRoot()}
	 * .
	 * 
	 * @throws Exception
	 * @throws MarketPlaceException
	 */
	public void testGetRoot() throws MarketPlaceException, Exception {
		MarketPlace marketPlace = new MarketPlace();
		MarketPlaceRoot root = marketPlace.getRoot();
		System.out.println(root);
	}

	public void testGetCategoryEntry() throws Exception, IOException {
		MarketPlace marketPlace = new MarketPlace();
		CategoryEntry[] entriesForCategory = marketPlace.getEntriesForCategory(153);
		for (CategoryEntry categoryEntry : entriesForCategory) {
			System.out.println(categoryEntry);
		}

	}

	public void testGetMarketPlaceEntry() throws Exception, IOException {
		MarketPlace marketPlace = new MarketPlace();
		MarketPlaceElement marketPlaceElementById = marketPlace.getMarketPlaceElementById(87);
		System.out.println(marketPlaceElementById);

	}

}
