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

package org.remus.infomngmnt.ui.remote.wizards;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.newwizards.GeneralPage;
import org.remus.infomngmnt.util.IMultipleInfoObjectSetter;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class GeneralCheckoutPage extends GeneralPage implements IMultipleInfoObjectSetter {

	private EList<InformationUnit> infoUnits;

	public GeneralCheckoutPage() {
		super((Category) null);
	}

	@Override
	protected void doCreateNameElements(final Composite container) {
		final Label nameLabel = new Label(container, SWT.NONE);
		nameLabel.setText("Checkout elements");

		TableViewer tableViewer = new TableViewer(container, SWT.BORDER | SWT.V_SCROLL
				| SWT.H_SCROLL);
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gridData.heightHint = 60;
		;
		gridData.widthHint = SWT.DEFAULT;
		tableViewer.getControl().setLayoutData(gridData);

		tableViewer.setContentProvider(UIUtil.getArrayContentProviderInstance());
		tableViewer.setLabelProvider(new AdapterFactoryLabelProvider(UIPlugin.getDefault()
				.getEditService().getAdapterFactory()));
		tableViewer.setInput(this.infoUnits);
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			private final EMFDataBindingContext innerCtx = new EMFDataBindingContext();

			public void selectionChanged(final SelectionChangedEvent event) {
				this.innerCtx.dispose();
				ISWTObservableValue swtKeyWords = SWTObservables.observeText(
						GeneralCheckoutPage.this.keywordsText, SWT.Modify);
				IObservableValue emfKeywords = EMFObservables.observeValue(
						(EObject) ((IStructuredSelection) event.getSelection()).getFirstElement(),
						InfomngmntPackage.Literals.INFORMATION_UNIT__KEYWORDS);
				this.innerCtx.bindValue(swtKeyWords, emfKeywords, null, null);
				ISWTObservableValue swtDescription = SWTObservables.observeText(
						GeneralCheckoutPage.this.descriptionText, SWT.Modify);
				IObservableValue emfDescription = EMFObservables.observeValue(
						(EObject) ((IStructuredSelection) event.getSelection()).getFirstElement(),
						InfomngmntPackage.Literals.INFORMATION_UNIT__DESCRIPTION);
				this.innerCtx.bindValue(swtDescription, emfDescription, null, null);
			}
		});

	}

	@Override
	protected void doCreatePropertiesGroup(final Composite container) {
		super.doCreatePropertiesGroup(container);
		this.keywordsText.setEditable(false);
		this.descriptionText.setEditable(false);
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
