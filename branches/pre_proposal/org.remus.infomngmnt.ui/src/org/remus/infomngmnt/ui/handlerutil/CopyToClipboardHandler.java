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

package org.remus.infomngmnt.ui.handlerutil;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class CopyToClipboardHandler extends AbstractHandler {

	protected void copyToCliboard(final ExecutionEvent event, final Object[] contents,
			final Transfer[] transfers) {
		Clipboard clipboard = null;
		try {
			clipboard = new Clipboard(HandlerUtil.getActiveShell(event).getDisplay());
			clipboard.setContents(contents, transfers);
		} finally {
			if (clipboard != null) {
				clipboard.dispose();
			}
		}
	}

	protected void copyToCliboard(final ExecutionEvent event, final Object contents,
			final Transfer transfers) {
		copyToCliboard(event, new Object[] { contents }, new Transfer[] { transfers });
	}

}
