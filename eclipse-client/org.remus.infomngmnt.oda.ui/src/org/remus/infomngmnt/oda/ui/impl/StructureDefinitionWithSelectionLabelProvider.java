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

package org.remus.infomngmnt.oda.ui.impl;

import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.swt.graphics.Font;

import org.remus.infomngmnt.InformationStructureItem;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.ui.viewer.provider.StructureDefinitionLabelProvider;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class StructureDefinitionWithSelectionLabelProvider extends StructureDefinitionLabelProvider
		implements IFontProvider {

	private final Font defaultFont;

	public StructureDefinitionWithSelectionLabelProvider(final Font defaultFont) {
		this.defaultFont = defaultFont;
	}

	private String selection;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IFontProvider#getFont(java.lang.Object)
	 */
	public Font getFont(final Object arg0) {
		if (((InformationStructureItem) arg0).getId().equals(this.selection)) {
			return ResourceManager.getBoldFont(this.defaultFont);
		}
		return null;
	}

	/**
	 * @param selection
	 *            the selection to set
	 */
	public final void setSelection(final String selection) {
		this.selection = selection;
	}

}
