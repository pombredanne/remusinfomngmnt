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

package org.remus.infomngmnt.ui.newwizards;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.model.EditingUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class GeneralCheckoutPage extends GeneralPage implements IMultipleInfoObjectSetter{

	private EList<InformationUnit> infoUnits;

	public GeneralCheckoutPage() {
		super((Category)null);
	}


	@Override
	protected void doCreateNameElements(final Composite container) {
		final Label nameLabel = new Label(container, SWT.NONE);
		nameLabel.setText("Checkout elements");

		TableViewer tableViewer = new TableViewer(container, SWT.V_SCROLL | SWT.H_SCROLL);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.setLabelProvider(new AdapterFactoryLabelProvider(EditingUtil.getInstance().getAdapterFactory()));
		tableViewer.setInput(this.infoUnits);

	}

	@Override
	protected void initDatabinding() {

	}

	public EList<InformationUnit> getInformationUnits() {
		return this.infoUnits;
	}

	public void setInformationUnits(final EList<InformationUnit> infoUnits) {
		this.infoUnits = infoUnits;


	}

}
