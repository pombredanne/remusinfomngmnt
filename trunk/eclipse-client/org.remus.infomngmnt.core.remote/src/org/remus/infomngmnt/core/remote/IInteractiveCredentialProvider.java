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

package org.remus.infomngmnt.core.remote;

import org.remus.infomngmnt.model.remote.ICredentialProvider;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface IInteractiveCredentialProvider extends ICredentialProvider {

	public static enum LoginSate {
		IN_INTERACTION, BEFORE_INTERACTION, INTERACTION_FAILED, INTERACTION_SUCCESS
	}

	void startInterAction();

	LoginSate getInterActionResult();
}
