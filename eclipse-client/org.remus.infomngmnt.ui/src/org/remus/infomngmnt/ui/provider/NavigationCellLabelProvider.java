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

package org.remus.infomngmnt.ui.provider;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;
import org.remus.infomngmnt.core.model.EditingUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NavigationCellLabelProvider extends ColumnLabelProvider implements IStyledLabelProvider{

	private final LabelProvider delegatingLabelProvider;

	public NavigationCellLabelProvider() {
		this.delegatingLabelProvider = new DecoratingLabelProvider(new AdapterFactoryLabelProvider(EditingUtil.getInstance().getAdapterFactory()),
				PlatformUI.getWorkbench().getDecoratorManager().getLabelDecorator());

	}

	@Override
	public String getText(Object element) {
		return this.delegatingLabelProvider.getText(element);
	}

	@Override
	public Image getImage(Object element) {
		return this.delegatingLabelProvider.getImage(element);
	}



	public StyledString getStyledText(Object element) {
		StyledString styledString= new StyledString();
		styledString.append(getText(element));
		return styledString;
	}





}
