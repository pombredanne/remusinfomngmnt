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

package org.remus.infomngmnt.core.transfertypes;

import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;

import org.remus.infomngmnt.core.extension.TransferWrapper;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TextTransferWrapper extends TransferWrapper {


	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.extension.TransferWrapper#getTransfer()
	 */
	@Override
	public Transfer getTransfer() {
		return TextTransfer.getInstance();
	}

	@Override
	public Object nativeToJava(TransferData transfer) {
		return TextTransfer.getInstance().nativeToJava(transfer);
	}

}
