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

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MarketPlaceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7870568802008389474L;

	public MarketPlaceException() {
		super();
	}

	public MarketPlaceException(final String arg0, final Throwable arg1) {
		super(arg0, arg1);
	}

	public MarketPlaceException(final String arg0) {
		super(arg0);
	}

	public MarketPlaceException(final Throwable arg0) {
		super(arg0);
	}

}
