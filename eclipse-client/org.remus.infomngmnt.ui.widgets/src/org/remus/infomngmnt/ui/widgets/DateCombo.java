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

package org.remus.infomngmnt.ui.widgets;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.ui.internal.widgets.ResourceManager;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DateCombo extends Composite implements ISelectionProvider {

	private Date date;

	private final Text dateText;
	private final Button pickButton;
	private final List<ISelectionChangedListener> listeners;

	public DateCombo(final Composite parent, final int style) {
		this(parent, style, false);
	}

	public DateCombo(final Composite parent, final int style, final boolean applyStyleToPicker) {
		super(parent, style);
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.horizontalSpacing = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		this.setLayout(gridLayout);

		this.dateText = new Text(this, SWT.FLAT);
		this.dateText.setEditable(false);
		GridData dateTextGridData = new GridData(SWT.FILL, SWT.FILL, false, false);
		dateTextGridData.grabExcessHorizontalSpace = true;
		dateTextGridData.widthHint = SWT.FILL;
		dateTextGridData.verticalIndent = 0;

		this.dateText.setLayoutData(dateTextGridData);

		if (applyStyleToPicker) {
			this.pickButton = new Button(this, style | SWT.ARROW | SWT.DOWN);
		} else {
			this.pickButton = new Button(this, SWT.ARROW | SWT.DOWN);

		}
		GridData pickButtonGridData = new GridData(SWT.RIGHT, SWT.FILL, false, true);
		pickButtonGridData.verticalIndent = 0;
		this.pickButton.setLayoutData(pickButtonGridData);
		this.pickButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent arg0) {

				MenuManager menuManager = new MenuManager();
				Menu menu = menuManager.createContextMenu(DateCombo.this.pickButton);
				createMenu(menuManager);
				DateCombo.this.pickButton.setMenu(menu);
				menu.setVisible(true);
			}
		});
		this.listeners = new ArrayList<ISelectionChangedListener>();
		updateDateText();
		pack();
	}

	protected void createMenu(final MenuManager menuManager) {
		for (int i = 0, n = 7; i < n; i++) {
			menuManager.add(new DateSetAction(i));
		}
		menuManager.add(new Separator());
		menuManager.add(new Action("Choose...") {
			@Override
			public void run() {
				DatePickerDialog datePickerDialog = new DatePickerDialog(getShell(),
						DateCombo.this.date);
				if (datePickerDialog.open() == IDialogConstants.OK_ID) {
					setSelection(new StructuredSelection(datePickerDialog.getSelectedDate()));
				}

			}

			@Override
			public ImageDescriptor getImageDescriptor() {
				return ResourceManager.getPluginImageDescriptor(Platform.getBundle(
						ResourceManager.PLUGIN_ID).getBundleContext(), "icons/calendar.png");
			}
		});
		menuManager.add(new Action("None", IAction.AS_CHECK_BOX) {
			@Override
			public void run() {
				setSelection(StructuredSelection.EMPTY);
			}

			@Override
			public boolean isChecked() {
				return getSelection().isEmpty();
			}
		});

	}

	private String getDayString(final int offset) {
		Calendar instance = Calendar.getInstance();
		if (offset > 0) {
			instance.add(Calendar.DAY_OF_YEAR, offset);
		}
		String format = new SimpleDateFormat("EEEE").format(instance.getTime());
		if (offset == 0) {
			format = format + " - Today";
		}
		return format;
	}

	private Date getDate(final int offset) {
		Calendar instance = Calendar.getInstance();
		if (offset > 0) {
			instance.add(Calendar.DAY_OF_YEAR, offset);
		}
		return instance.getTime();
	}

	protected String convertDate() {
		return SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT).format(this.date);
	}

	private void updateDateText() {
		if (this.date != null) {
			this.dateText.setText(convertDate());
		} else {
			this.dateText.setEnabled(false);
			this.dateText.setText("Choose Date");
			this.dateText.setEnabled(true);
		}

	}

	private void notifyListeners() {
		for (ISelectionChangedListener listener : this.listeners) {
			listener.selectionChanged(new SelectionChangedEvent(this, getSelection()));
		}

	}

	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		if (listener != null) {
			this.listeners.add(listener);
		}

	}

	public ISelection getSelection() {
		if (this.date == null) {
			return StructuredSelection.EMPTY;
		}
		return new StructuredSelection(this.date);
	}

	public void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		if (listener != null) {
			this.listeners.remove(listener);
		}

	}

	public void setSelection(final ISelection selection) {
		if (selection == null || selection.isEmpty()) {
			this.date = null;
			updateDateText();
			notifyListeners();
			return;
		}
		if (selection instanceof IStructuredSelection) {
			Object firstElement = ((IStructuredSelection) selection).getFirstElement();
			if (firstElement instanceof Date) {
				this.date = (Date) firstElement;
				updateDateText();
				notifyListeners();
			}
		}
	}

	@Override
	public void setBackground(final Color backgroundColor) {
		this.dateText.setBackground(backgroundColor);
		this.pickButton.setBackground(backgroundColor);
		super.setBackground(backgroundColor);
	}

	private class DateSetAction extends Action {
		private final int offset;

		public DateSetAction(final int offset) {
			this.offset = offset;

		}

		@Override
		public String getText() {
			return getDayString(this.offset);
		}

		@Override
		public void run() {
			setSelection(new StructuredSelection(getDate(this.offset)));
		}
	}

}
