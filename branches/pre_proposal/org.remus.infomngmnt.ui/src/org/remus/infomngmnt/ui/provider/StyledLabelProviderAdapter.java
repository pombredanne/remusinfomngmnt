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

package org.remus.infomngmnt.ui.provider;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

public class StyledLabelProviderAdapter implements IStyledLabelProvider, IColorProvider,
		IFontProvider {

	private final ILabelProvider provider;

	public StyledLabelProviderAdapter(final ILabelProvider provider) {
		this.provider = provider;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.
	 * IStyledLabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(final Object element) {
		return this.provider.getImage(element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.
	 * IStyledLabelProvider#getStyledText(java.lang.Object)
	 */
	public StyledString getStyledText(final Object element) {
		if (this.provider instanceof IStyledLabelProvider) {
			return ((IStyledLabelProvider) this.provider).getStyledText(element);
		}
		String text = this.provider.getText(element);
		if (text == null) {
			text = ""; //$NON-NLS-1$
		}
		return new StyledString(text);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse
	 * .jface.viewers.ILabelProviderListener)
	 */
	public void addListener(final ILabelProviderListener listener) {
		this.provider.addListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	public void dispose() {
		this.provider.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java
	 * .lang.Object, java.lang.String)
	 */
	public boolean isLabelProperty(final Object element, final String property) {
		return this.provider.isLabelProperty(element, property);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse
	 * .jface.viewers.ILabelProviderListener)
	 */
	public void removeListener(final ILabelProviderListener listener) {
		this.provider.removeListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IColorProvider#getBackground(java.lang.
	 * Object)
	 */
	public Color getBackground(final Object element) {
		if (this.provider instanceof IColorProvider) {
			return ((IColorProvider) this.provider).getBackground(element);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IColorProvider#getForeground(java.lang.
	 * Object)
	 */
	public Color getForeground(final Object element) {
		if (this.provider instanceof IColorProvider) {
			return ((IColorProvider) this.provider).getForeground(element);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IFontProvider#getFont(java.lang.Object)
	 */
	public Font getFont(final Object element) {
		if (this.provider instanceof IFontProvider) {
			return ((IFontProvider) this.provider).getFont(element);
		}
		return null;
	}
}