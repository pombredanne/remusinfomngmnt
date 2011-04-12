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

import org.aspencloud.calypso.ui.workbench.views.calendar.actions.ShowGridAction;
import org.aspencloud.calypso.ui.workbench.views.calendar.actions.ZoomInAction;
import org.aspencloud.calypso.ui.workbench.views.calendar.actions.ZoomOutAction;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.SharedHeaderFormEditor;
import org.remus.infomngmnt.ui.calendar.messages.Messages;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CalendarEditor extends SharedHeaderFormEditor {

	private CalendarPage page1;

	private ZoomInAction zoomInAction;

	private ZoomOutAction zoomOutAction;

	private ShowGridAction showGridAction;

	public static final String EDITOR_ID = "org.remus.infomngmnt.ui.calendar.CalendarEditor"; //$NON-NLS-1$

	@Override
	protected void createHeaderContents(final IManagedForm headerForm) {
		headerForm.getForm().setText(Messages.CalendarEditor_Calendar);
		// headerForm.getForm().setImage(InformationExtensionManager.getInstance().getInfoTypeByType(getPrimaryModel().getType()).getImageDescriptor().createImage());
		getToolkit().decorateFormHeading(headerForm.getForm().getForm());
		headerForm.getForm().getToolBarManager().add(this.zoomInAction = new ZoomInAction());
		headerForm.getForm().getToolBarManager().add(this.zoomOutAction = new ZoomOutAction());
		headerForm.getForm().getToolBarManager().add(new Separator());
		headerForm.getForm().getToolBarManager().add(this.showGridAction = new ShowGridAction());
		headerForm.getForm().getToolBarManager().update(true);
	}

	/**
	 * 
	 */
	public CalendarEditor() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.editor.FormEditor#addPages()
	 */
	@Override
	protected void addPages() {
		try {
			addPage(this.page1 = new CalendarPage(this, "cal", "cal")); //$NON-NLS-1$ //$NON-NLS-2$
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void createPages() {
		super.createPages();
		if (getPageCount() == 1 && getContainer() instanceof CTabFolder) {
			((CTabFolder) getContainer()).setTabHeight(0);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	@Override
	public void doSave(final IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
		// does nothing

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	/**
	 * @return the zoomInAction
	 */
	public ZoomInAction getZoomInAction() {
		return this.zoomInAction;
	}

	/**
	 * @return the zoomOutAction
	 */
	public ZoomOutAction getZoomOutAction() {
		return this.zoomOutAction;
	}

	/**
	 * @return the showGridAction
	 */
	public ShowGridAction getShowGridAction() {
		return this.showGridAction;
	}

}
