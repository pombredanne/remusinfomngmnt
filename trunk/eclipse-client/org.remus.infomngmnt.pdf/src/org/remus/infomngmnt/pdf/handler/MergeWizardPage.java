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

package org.remus.infomngmnt.pdf.handler;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.remus.AbstractInformationUnit;
import org.eclipse.remus.Category;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.ui.newwizards.GeneralPage;
import org.eclipse.remus.ui.viewer.ViewerActivator;
import org.eclipse.remus.ui.viewer.provider.InformationUnitLabelProvider;
import org.eclipse.remus.ui.viewer.provider.NavigatorDecoratingLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import org.remus.infomngmnt.pdf.Activator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MergeWizardPage extends GeneralPage {

	private List<InformationUnitListItem> items2Merge;
	private TableViewer viewer;

	public MergeWizardPage(final Category category) {
		super(category);
	}

	public MergeWizardPage(final Object[] items2Merge) {
		this((Category) null);
		this.items2Merge = new LinkedList<InformationUnitListItem>();
		for (Object informationUnitListItem : items2Merge) {
			if (informationUnitListItem instanceof InformationUnitListItem
					&& Activator.TYPE_ID.equals(((AbstractInformationUnit) informationUnitListItem)
							.getType())) {
				this.items2Merge.add((InformationUnitListItem) informationUnitListItem);
			}
		}
	}

	@Override
	public void createControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout());
		setTitle("Merge PDF Documents");
		setMessage("This wizard enables you to merge several PDF documents.");

		doCreateParentElementGroup(container);
		Group group = new Group(container, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		group.setLayout(new GridLayout(3, false));
		group.setText("Name");
		doCreateNameElements(group);

		Group group2 = new Group(container, SWT.NONE);
		group2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		group2.setLayout(new GridLayout(2, false));
		group2.setText("Order");

		this.viewer = new TableViewer(group2);
		this.viewer.setContentProvider(ArrayContentProvider.getInstance());
		this.viewer.setLabelProvider(new NavigatorDecoratingLabelProvider(
				new InformationUnitLabelProvider(ViewerActivator.getDefault().getEditService()
						.getAdapterFactory())));
		this.viewer.setInput(this.items2Merge);
		this.viewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		ToolBar tb = new ToolBar(group2, SWT.VERTICAL | SWT.FLAT);
		final ToolItem toolItem = new ToolItem(tb, SWT.PUSH);
		toolItem.setText("Up");

		final ToolItem toolItem2 = new ToolItem(tb, SWT.PUSH);
		toolItem2.setText("Down");

		toolItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				Object[] array = ((IStructuredSelection) MergeWizardPage.this.viewer.getSelection())
						.toArray();
				for (Object object : array) {
					int indexOf = MergeWizardPage.this.items2Merge.indexOf(object);
					MergeWizardPage.this.items2Merge.remove(object);
					MergeWizardPage.this.items2Merge.add(indexOf - 1,
							(InformationUnitListItem) object);
				}
				toolItem.setEnabled(isUpEnabled(array));
				toolItem2.setEnabled(isDownEnabled(array));
				MergeWizardPage.this.viewer.refresh();
			}
		});
		toolItem2.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				Object[] array = ((IStructuredSelection) MergeWizardPage.this.viewer.getSelection())
						.toArray();
				for (Object object : array) {
					int indexOf = MergeWizardPage.this.items2Merge.indexOf(object);
					MergeWizardPage.this.items2Merge.remove(object);
					MergeWizardPage.this.items2Merge.add(indexOf + 1,
							(InformationUnitListItem) object);
				}
				toolItem.setEnabled(isUpEnabled(array));
				toolItem2.setEnabled(isDownEnabled(array));
				MergeWizardPage.this.viewer.refresh();
			}
		});
		tb.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, true));
		toolItem.setEnabled(false);
		toolItem2.setEnabled(false);

		this.viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(final SelectionChangedEvent event) {
				Object[] array = ((IStructuredSelection) event.getSelection()).toArray();
				toolItem.setEnabled(isUpEnabled(array));
				toolItem2.setEnabled(isDownEnabled(array));
			}

		});

		doCreatePropertiesGroup(container);
		initDatabinding();
		presetValues();
		initValidation();
		setControl(container);
	}

	private boolean isDownEnabled(final Object[] array) {
		if (array.length == 0) {
			return false;
		}
		for (Object object : array) {
			int indexOf = MergeWizardPage.this.items2Merge.indexOf(object);
			if (indexOf == MergeWizardPage.this.items2Merge.size() - 1) {
				return false;
			}
		}
		return true;

	}

	public List<InformationUnitListItem> getElements() {
		return this.items2Merge;
	}

	private boolean isUpEnabled(final Object[] array) {
		if (array.length == 0) {
			return false;
		}
		for (Object object : array) {
			int indexOf = MergeWizardPage.this.items2Merge.indexOf(object);
			if (indexOf <= 0) {
				return false;
			}
		}
		return true;

	}
}
