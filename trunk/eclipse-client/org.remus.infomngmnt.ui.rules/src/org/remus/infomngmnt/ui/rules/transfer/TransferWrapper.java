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

package org.remus.infomngmnt.ui.rules.transfer;

import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class TransferWrapper {

	private String name;

	public abstract Transfer getTransfer();

	public abstract Object nativeToJava(TransferData transfer);

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
