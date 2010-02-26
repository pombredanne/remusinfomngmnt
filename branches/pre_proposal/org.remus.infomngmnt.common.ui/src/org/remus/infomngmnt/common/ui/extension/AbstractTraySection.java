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

import org.remus.infomngmt.common.ui.uimodel.TraySection;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class AbstractTraySection {

	protected FormToolkit toolkit;
	private String title;
	private Image image;
	private TraySection section;

	public void init(FormToolkit pToolkit, TraySection section) {
		this.toolkit = pToolkit;
		this.section = section;
		this.title = section.getName();
		this.image = section.getImage();
	}

	public abstract void createDetailsPart(Composite parent);


	public String getTitle() {
		return this.title;
	}

	public Image getImage() {
		return this.image;
	}

	protected TraySection getSectionDefinition() {
		return this.section;
	}

	public void dispose() {
		// does nothing by default.
	}



}
