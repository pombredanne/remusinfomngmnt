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

package org.remus.infomngmnt.ui.remote.auth;

import org.remus.infomngmnt.core.remote.IInteractiveCredentialProvider;
import org.remus.infomngmnt.core.security.CredentialProvider;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class InteractiveAuthDialogCredentialProvider extends CredentialProvider implements
		IInteractiveCredentialProvider {

	private LoginSate loginState;

	protected InteractiveAuthDialogCredentialProvider() {
		setLoginState(LoginSate.BEFORE_INTERACTION);
	}

	public final void startInterAction() {
		setLoginState(LoginSate.IN_INTERACTION);
		doStartInteraction();

	}

	protected void setLoginState(final LoginSate loginState) {
		this.loginState = loginState;
	}

	protected abstract void doStartInteraction();

}
