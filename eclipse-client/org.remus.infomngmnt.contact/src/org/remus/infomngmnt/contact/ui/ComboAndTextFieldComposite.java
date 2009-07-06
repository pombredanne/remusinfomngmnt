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

package org.remus.infomngmnt.contact.ui;

import org.eclipse.core.databinding.Binding;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.core.util.KeyValueObject;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.common.ui.databinding.BindingWidgetFactory;
import org.remus.infomngmnt.common.ui.databinding.IEMFEditBindingProvider;
import org.remus.infomngmnt.common.ui.databinding.TextBindingWidget;
import org.remus.infomngmnt.core.model.InformationUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ComboAndTextFieldComposite extends Composite {

	private final EAttribute attribute;

	private InformationUnit informationUnit;

	private final IEMFEditBindingProvider provider;

	private final Text tx;

	private TextBindingWidget createTextBindingWidget;

	private final ComboViewer comboViewer;

	private Binding currentBinding;

	private final ISelectionChangedListener listener = new ISelectionChangedListener() {
		public void selectionChanged(final SelectionChangedEvent event) {
			if (!event.getSelection().isEmpty()) {
				if (ComboAndTextFieldComposite.this.currentBinding != null) {
					ComboAndTextFieldComposite.this.currentBinding.dispose();
				}
				ComboAndTextFieldComposite.this.createTextBindingWidget.bindModel(InformationUtil
						.getChildByType(ComboAndTextFieldComposite.this.informationUnit,
								((KeyValueObject) ((IStructuredSelection) event.getSelection())
										.getFirstElement()).getId()),
						ComboAndTextFieldComposite.this.attribute);
				ComboAndTextFieldComposite.this.currentBinding = ComboAndTextFieldComposite.this.createTextBindingWidget
						.getBinding();
			}
		}
	};

	public ComboAndTextFieldComposite(final Composite parent, final int style,
			final KeyValueObject[] inputData, final EAttribute attribute,
			final IEMFEditBindingProvider provider) {
		super(parent, style);
		this.attribute = attribute;
		this.provider = provider;
		setLayout(UIUtil.createMarginLessGridLayout(2));

		final Combo cb = new Combo(this, SWT.DROP_DOWN | SWT.READ_ONLY);
		cb.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		this.comboViewer = new ComboViewer(cb);
		this.comboViewer.setContentProvider(UIUtil.getArrayContentProviderInstance());
		this.comboViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((KeyValueObject) element).getValue();
			}
		});
		this.comboViewer.setInput(inputData);
		this.tx = new Text(this, SWT.BORDER);
		GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.tx.setLayoutData(gd_text);
	}

	public void bindValuesToUi() {
		this.createTextBindingWidget = BindingWidgetFactory.createTextBinding(this.tx,
				this.provider);
		this.comboViewer.addSelectionChangedListener(this.listener);
	}

	public void disposeBinding() {
		this.comboViewer.removeSelectionChangedListener(this.listener);
	}

	/**
	 * @param informationUnit
	 *            the informationUnit to set
	 */
	public void setInformationUnit(final InformationUnit informationUnit) {
		this.informationUnit = informationUnit;
	}

	public void select(final int i) {
		this.comboViewer.setSelection(new StructuredSelection(((KeyValueObject[]) this.comboViewer
				.getInput())[i]));
	}

	/**
	 * @return the tx
	 */
	public Text getTx() {
		return this.tx;
	}

}
