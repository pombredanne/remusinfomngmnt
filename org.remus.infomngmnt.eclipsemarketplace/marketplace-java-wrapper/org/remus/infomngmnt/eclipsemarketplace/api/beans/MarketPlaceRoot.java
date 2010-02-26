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

package org.remus.infomngmnt.eclipsemarketplace.api.beans;

import java.util.List;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MarketPlaceRoot {

	private List<Market> markets;

	/**
	 * @return the markets
	 */
	public final List<Market> getMarkets() {
		return this.markets;
	}

	/**
	 * @param markets
	 *            the markets to set
	 */
	public final void setMarkets(final List<Market> markets) {
		this.markets = markets;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MarketPlaceRoot [markets=" + this.markets + "]";
	}

}
