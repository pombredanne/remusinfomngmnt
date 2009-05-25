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

package org.remus.infomngmnt.common.ui.jface;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.Binding;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class BindingStatusDialog extends StatusDialog {

	protected BindingStatusDialog(final Shell parent) {
		super(parent);
		this.bindings = new ArrayList<Binding>();
	}

	private final List<Binding> bindings;

	public void addBinding(final Binding binding) {
		this.bindings.add(binding);
	}

	@Override
	public boolean close() {
		for (Binding binding : this.bindings) {
			binding.dispose();
		}
		return super.close();
	}

}
