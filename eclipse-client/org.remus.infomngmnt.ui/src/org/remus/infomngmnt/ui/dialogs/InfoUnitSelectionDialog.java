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

package org.remus.infomngmnt.ui.dialogs;

import java.text.Collator;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;

import org.remus.infomngmnt.AbstractInformationUnit;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.core.model.CategoryUtil;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.provider.NavigatorDecoratingLabelProvider;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InfoUnitSelectionDialog extends FilteredItemsSelectionDialog {

	public static final String SECTION_DIALOG = "ds_infounitselectiondialog"; //$NON-NLS-1$
	public static final String SELECTION_MEMENTO = "memento_infounitselectiondialog"; //$NON-NLS-1$
	private IInputValidator validator;

	public InfoUnitSelectionDialog(final Shell shell, final boolean multi) {
		super(shell, multi);
		setSelectionHistory(new ItemSelectionHistory());
		// setMessage("Select an information item to open" + getMessage());
		setTitle("Open Information Item");
	}

	public InfoUnitSelectionDialog(final Shell shell, final boolean multi,
			final IInputValidator validator) {
		this(shell, multi);
		this.validator = validator;
	}

	class ItemSelectionHistory extends SelectionHistory {

		@Override
		protected Object restoreItemFromMemento(final IMemento memento) {
			return ApplicationModelPool.getInstance().getItemById(memento.getTextData(), null);
		}

		@Override
		protected void storeItemToMemento(final Object item, final IMemento memento) {
			memento.putTextData(((AbstractInformationUnit) item).getId());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.dialogs.FilteredItemsSelectionDialog#createExtendedContentArea
	 * (org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createExtendedContentArea(final Composite parent) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.dialogs.FilteredItemsSelectionDialog#createFilter()
	 */
	@Override
	protected ItemsFilter createFilter() {

		return new ItemsFilter() {

			@Override
			public boolean isConsistentItem(final Object item) {
				return true;
			}

			@Override
			public boolean matchItem(final Object item) {
				return matches(((AbstractInformationUnit) item).getLabel());
			}

		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.dialogs.FilteredItemsSelectionDialog#fillContentProvider
	 * (org
	 * .eclipse.ui.dialogs.FilteredItemsSelectionDialog.AbstractContentProvider,
	 * org.eclipse.ui.dialogs.FilteredItemsSelectionDialog.ItemsFilter,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected void fillContentProvider(final AbstractContentProvider contentProvider,
			final ItemsFilter itemsFilter, final IProgressMonitor progressMonitor)
			throws CoreException {
		Map<String, InformationUnitListItem> allItems = ApplicationModelPool.getInstance()
				.getAllItems(progressMonitor);
		Collection<InformationUnitListItem> values = allItems.values();
		for (InformationUnitListItem informationUnitListItem : values) {
			contentProvider.add(informationUnitListItem, itemsFilter);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.dialogs.FilteredItemsSelectionDialog#getDialogSettings()
	 */
	@Override
	protected IDialogSettings getDialogSettings() {
		IDialogSettings section = UIPlugin.getDefault().getDialogSettings().getSection(
				SECTION_DIALOG);
		if (section == null) {
			UIPlugin.getDefault().getDialogSettings().addNewSection(SECTION_DIALOG);
			section = UIPlugin.getDefault().getDialogSettings().getSection(SECTION_DIALOG);
		}
		return section;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.dialogs.FilteredItemsSelectionDialog#getElementName(java
	 * .lang.Object)
	 */
	@Override
	public String getElementName(final Object item) {
		return ((AbstractInformationUnit) item).getLabel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.dialogs.FilteredItemsSelectionDialog#getItemsComparator()
	 */
	@Override
	protected Comparator getItemsComparator() {
		return new Comparator<InformationUnitListItem>() {

			public int compare(final InformationUnitListItem o1, final InformationUnitListItem o2) {
				return Collator.getInstance().compare(o1.getLabel(), o2.getLabel());
			}
		};
	}

	public static InfoUnitSelectionDialog create(final Shell shell, final IInputValidator validator) {
		InfoUnitSelectionDialog diag = new InfoUnitSelectionDialog(shell, false, validator);
		diag.setListLabelProvider(new NavigatorDecoratingLabelProvider(
				new AdapterFactoryLabelProvider(EditingUtil.getInstance().getAdapterFactory())));
		diag.setDetailsLabelProvider(new NavigatorDecoratingLabelProvider(
				new AdapterFactoryLabelProvider(EditingUtil.getInstance().getAdapterFactory())) {
			@Override
			public String getText(final Object element) {
				InformationUnitListItem adapter = ((InformationUnitListItem) ((IAdaptable) element)
						.getAdapter(InformationUnitListItem.class));
				if (adapter != null) {
					return CategoryUtil.categoryToString((Category) adapter.eContainer()) + "/"
							+ adapter.getLabel();
				} else {
					return ((AbstractInformationUnit) element).getLabel();
				}

			}
		});
		return diag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.dialogs.FilteredItemsSelectionDialog#validateItem(java
	 * .lang.Object)
	 */
	@Override
	protected IStatus validateItem(final Object item) {
		if (this.validator != null) {
			return this.validator.validate(item);
		}
		return Status.OK_STATUS;
	}

}
