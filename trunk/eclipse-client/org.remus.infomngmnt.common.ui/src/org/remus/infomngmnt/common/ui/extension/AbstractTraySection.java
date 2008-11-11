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

package org.remus.infomngmnt.common.ui.extension;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class AbstractTraySection {

	protected FormToolkit toolkit;
	private String title;
	private Image image;

	public void init(FormToolkit pToolkit, String title, Image image) {
		this.toolkit = pToolkit;
		this.title = title;
		this.image = image;
	}

	public abstract void createDetailsPart(Composite parent);


	public String getTitle() {
		return this.title;
	}

	public Image getImage() {
		return this.image;
	}

	public void dispose() {
		// does nothing by default.
	}



}
