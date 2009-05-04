/*******************************************************************************
 * Copyright (c) 2009 Jan Hartwig, FEB Radebeul
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Jan Hartwig - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.contact.ui.general;
/**
 * @author Jan Hartwig <jhartwig@feb-radebeul.de>
 * 
 */
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.Binding;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.common.ui.databinding.BindingWidgetFactory;
import org.remus.infomngmnt.common.ui.databinding.TextBindingWidget;
import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.contact.core.ContactSettings;
import org.remus.infomngmnt.core.model.InformationUtil;

public class InstantMessagingComposite extends Composite {
	
	private Binding currentBinding;
	private TextBindingWidget createTextBindingWidget;
	
	public InstantMessagingComposite(Composite parent, int style, FormToolkit toolkit, int iTextValue, final InformationUnit informationUnit, EditGeneralPage editGeneralPage) {
		super(parent, style);

		final GridLayout gl_Numbers = new GridLayout();
		gl_Numbers.numColumns = 3;
		this.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		this.setLayout(gl_Numbers);

		List<KeyValueObject> values = new ArrayList<KeyValueObject>();
		String[] str = ContactSettings.DEFAULT_ITMES_COMBO_IM_ADDRESS_CHOOSER;
		values.add(new KeyValueObject(ContactActivator.NODE_INSTMESS_AIM, str[0]));
		values.add(new KeyValueObject(ContactActivator.NODE_INSTMESS_GADU, str[1]));		
		values.add(new KeyValueObject(ContactActivator.NODE_INSTMESS_GROUPWISE, str[2]));
		values.add(new KeyValueObject(ContactActivator.NODE_INSTMESS_ICQ, str[3]));
		values.add(new KeyValueObject(ContactActivator.NODE_INSTMESS_IRC, str[4]));
		values.add(new KeyValueObject(ContactActivator.NODE_INSTMESS_JABBER, str[5]));
		values.add(new KeyValueObject(ContactActivator.NODE_INSTMESS_MSN, str[6]));
		values.add(new KeyValueObject(ContactActivator.NODE_INSTMESS_MEANWHILE, str[7]));
		values.add(new KeyValueObject(ContactActivator.NODE_INSTMESS_SMS, str[8]));
		values.add(new KeyValueObject(ContactActivator.NODE_INSTMESS_SKYPE, str[9]));
		values.add(new KeyValueObject(ContactActivator.NODE_INSTMESS_YAHOO, str[10]));
		
		final Combo cb = new Combo(this, SWT.DROP_DOWN | SWT.READ_ONLY);
		ComboViewer comboViewer = new ComboViewer(cb);
		comboViewer.setContentProvider(UIUtil.getArrayContentProviderInstance());
		comboViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((KeyValueObject) element).getValue();
			}
		});
		comboViewer.setInput(values);
		final Text tx = new Text(this, SWT.BORDER);
		GridData gd_text = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd_text.horizontalSpan = 2;
		tx.setLayoutData(gd_text);
		comboViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				if (!event.getSelection().isEmpty()) {
					if (currentBinding != null) {
						currentBinding.dispose();						
					}
					createTextBindingWidget.bindModel(InformationUtil.getChildByType(informationUnit,
							((KeyValueObject) ((IStructuredSelection) event.getSelection()).getFirstElement()).getId()),
							InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
					currentBinding = createTextBindingWidget.getBinding();
				}
			}
		});
		createTextBindingWidget = BindingWidgetFactory.createTextBinding(tx, editGeneralPage.getDatabindingContext(), editGeneralPage.getEditingDomain());
		comboViewer.setSelection(new StructuredSelection(values.get(iTextValue)));
	}
	private static class KeyValueObject {
		public KeyValueObject(String id, String value) {
			super();
			this.id = id;
			this.value = value;
		}

		private String id;

		public String getId() {
			return id;
		}

		public String getValue() {
			return value;
		}

		private String value;
	}
}
