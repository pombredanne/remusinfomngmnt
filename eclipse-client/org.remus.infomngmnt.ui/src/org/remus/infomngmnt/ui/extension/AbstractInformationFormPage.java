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

package org.remus.infomngmnt.ui.extension;

import org.eclipse.ui.forms.editor.FormPage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class AbstractInformationFormPage extends FormPage {

	public AbstractInformationFormPage() {
		super(null, null, null);

	}

	@Override
	public void setPartName(String partName) {
		super.setPartName(partName);
	}

	protected abstract String getString();

}
