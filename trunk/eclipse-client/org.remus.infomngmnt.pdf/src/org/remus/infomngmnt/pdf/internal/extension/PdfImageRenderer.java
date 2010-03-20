/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.pdf.internal.extension;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import org.remus.infomngmnt.pdf.extension.IPdf2ImageRenderer;
import org.remus.infomngmnt.pdf.extension.IPdfImageRenderer;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class PdfImageRenderer implements IPdfImageRenderer {

	private final IConfigurationElement element;

	private IPdf2ImageRenderer instance;

	public PdfImageRenderer(final IConfigurationElement element) {
		this.element = element;
	}

	public String getId() {
		return this.element.getAttribute(Pdf2ImageExtensionService.ID_ATT);
	}

	public IPdf2ImageRenderer getRenderer() {
		if (this.instance == null) {
			try {
				this.instance = (IPdf2ImageRenderer) this.element
						.createExecutableExtension(Pdf2ImageExtensionService.CLASS_ATT);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return this.instance;
	}

	public String getName() {
		return this.element.getAttribute(Pdf2ImageExtensionService.NAME_ATT);
	}

}
