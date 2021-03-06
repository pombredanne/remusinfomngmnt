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

package org.remus.infomngmnt.connector.slideshare;

import org.eclipse.remus.core.remote.security.CredentialProvider;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SlideshareCredentialProvider extends CredentialProvider {

	public SlideshareCredentialProvider() {
		super();
		setGroup("slideshare"); //$NON-NLS-1$
	}

}
