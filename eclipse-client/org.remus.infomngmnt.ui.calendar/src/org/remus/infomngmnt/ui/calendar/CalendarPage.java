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

package org.remus.infomngmnt.ui.calendar;

import java.util.Date;
import java.util.List;

import org.aspencloud.calypso.ui.workbench.views.calendar.actions.ShowGridAction;
import org.aspencloud.calypso.ui.workbench.views.calendar.actions.ZoomInAction;
import org.aspencloud.calypso.ui.workbench.views.calendar.actions.ZoomOutAction;
import org.aspencloud.calypso.util.CalypsoEdit;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.remus.ui.editors.EditorUtil;
import org.eclipse.remus.ui.editors.InformationFormPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.internal.WorkbenchMessages;

import org.remus.infomgmnt.provider.CalendarContentProvider;
import org.remus.infomngmnt.calendar.model.Task;
import org.remus.infomngmnt.ccalendar.CCalendar;
import org.remus.infomngmnt.ui.calendar.messages.Messages;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CalendarPage extends InformationFormPage {

	private CCalendar calendar;
	private Form form;

	private ZoomInAction zoomInAction;
	private ZoomOutAction zoomOutAction;
	private ShowGridAction gridAction;
	private IAction createAction;
	private BaseSelectionListenerAction removeAction;
	private BaseSelectionListenerAction openInfoUnitAction;
	private BaseSelectionListenerAction editAction;

	public CalendarPage(final FormEditor editor, final String id, final String title) {
		super(editor, id, title);
	}

	@Override
	public void createPartControl(final Composite parent) {
		this.form = getEditor().getToolkit().createForm(parent);
		BusyIndicator.showWhile(parent.getDisplay(), new Runnable() {
			public void run() {
				createFormContent(CalendarPage.this.form);
			}
		});
	}

	@Override
	public Control getPartControl() {
		return this.form;
	}

	@Override
	public void setActive(final boolean active) {
		if (active) {
			// We are switching to this page - refresh it
			// if needed.

		}
	}

	protected void createFormContent(final Form form2) {
		FormToolkit toolkit = getEditor().getToolkit();
		Composite body = this.form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		this.calendar = new CCalendar(body, SWT.BORDER);
		this.calendar.setContentProvider(new CalendarContentProvider());
		this.calendar.setCalendarToWeekContaining(new Date());

		this.calendar.addOpenListener(new Listener() {
			public void handleEvent(final Event event) {
				Task task = (Task) event.data;
				if (task != null) {
					// ModelDialog dialog = new
					// ModelDialog(CalendarView.this.calendar.getShell(), task);
					// dialog.open();
				}
			}
		});
		makeActions();
		createContextMenu();
		this.calendar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		getSite().setSelectionProvider(this.calendar);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().addSelectionListener(
				new ISelectionListener() {

					public void selectionChanged(final IWorkbenchPart part,
							final ISelection selection) {
						if (selection instanceof IStructuredSelection) {
							Object firstElement = ((IStructuredSelection) selection)
									.getFirstElement();
							if (firstElement instanceof Date) {
								CalendarPage.this.calendar
										.setCalendarToWeekContaining((Date) firstElement);
							}
						}

					}

				});

	}

	private void makeActions() {
		((CalendarEditor) getEditor()).getZoomInAction().setCalendar(this.calendar);
		((CalendarEditor) getEditor()).getZoomOutAction().setCalendar(this.calendar);
		((CalendarEditor) getEditor()).getShowGridAction().setViewers(this.calendar);

		this.createAction = this.calendar.getCreateAction();
		// FIXME
		// this.createAction.setImageDescriptor(ResourceManager.getPluginImageDescriptor(UIPlugin
		// .getDefault(), "icons/iconexperience/16/calendar_new.png"));
		this.createAction.setText(Messages.CalendarPage_CreateCalendarEntry);
		this.removeAction = new BaseSelectionListenerAction(Messages.CalendarPage_Delete) {

			@Override
			protected boolean updateSelection(final IStructuredSelection selection) {
				return !selection.isEmpty();
			}

			@Override
			public void run() {
				List list = getStructuredSelection().toList();
				CalypsoEdit.delete(list);
			}
		};
		this.removeAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		this.removeAction.setId(Messages.CalendarPage_2);

		this.openInfoUnitAction = new BaseSelectionListenerAction(Messages.CalendarPage_OpenInfoUnit) {
			@Override
			protected boolean updateSelection(final IStructuredSelection selection) {
				return !selection.isEmpty();
			}

			@Override
			public void run() {
				List list = getStructuredSelection().toList();
				for (Object object : list) {
					if (object instanceof Task) {
						String taskId = ((Task) object).getId();
						String infoId = taskId.split(Messages.CalendarPage_4)[0];
						EditorUtil.openInfoUnit(infoId);
					}
				}
			}
		};
		this.editAction = new BaseSelectionListenerAction("") { //$NON-NLS-1$
			@Override
			protected boolean updateSelection(final IStructuredSelection selection) {
				return selection.toList().size() == 1;
			}

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
			}
		};

		this.calendar.addSelectionChangedListener(this.removeAction);
		this.calendar.addSelectionChangedListener(this.openInfoUnitAction);

	}

	private void createContextMenu() {
		Control[] controlsForContextMenu = this.calendar.getControlsForContextMenu();
		for (Control control : controlsForContextMenu) {
			if (control != null) {
				MenuManager manager = new MenuManager();
				Menu menu = manager.createContextMenu(control);
				control.setMenu(menu);
				manager.add(this.createAction);
				manager.add(new Separator());
				manager.add(this.openInfoUnitAction);
				manager.add(new Separator());
				manager.add(this.removeAction);
			}
		}

	}

}
