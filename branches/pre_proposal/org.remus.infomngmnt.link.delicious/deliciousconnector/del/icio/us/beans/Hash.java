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

package del.icio.us.beans;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class Hash {
	
	public Hash(final String hashValue, final String urlHash) {
		super();
		this.hashValue = hashValue;
		this.urlHash = urlHash;
	}

	private final String hashValue;
	
	private final String urlHash;

	public String getHashValue() {
		return this.hashValue;
	}


	public String getUrlHash() {
		return this.urlHash;
	}

	
}
