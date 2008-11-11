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

package org.remus.infomngmnt.ui.tray;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.widgets.Composite;

import org.remus.infomngmnt.common.ui.extension.AbstractTraySection;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DropSection extends AbstractTraySection {

	@Override
	public void createDetailsPart(Composite parent) {
		int dropOperations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;
		Composite dropComposite = this.toolkit.createComposite(parent, SWT.BORDER | SWT.NO_FOCUS);
		DropTarget dropTarget = new DropTarget(dropComposite, dropOperations);


	}

}
