/****************************************************************************
* Copyright (c) 2005-2006 Jeremy Dowdall
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*    Jeremy Dowdall <aspencloud@users.sourceforge.net> - initial API and implementation
*****************************************************************************/

package org.remus.infomngmnt.ccalendar.actions;

import java.util.Calendar;
import java.util.Date;

import org.aspencloud.calypso.util.CTimeSpan;
import org.aspencloud.calypso.util.TimeSpan;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.remus.infomngmnt.calendar.messages.Messages;
import org.remus.infomngmnt.calendar.model.Task;

/**
 * @author jeremy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MoveToTodayAction extends Action implements ISelectionChangedListener {
	public static final String ID = "org.aspencloud.calypso.ui.actions.ClearTaskAction"; //$NON-NLS-1$

	private final ISelectionProvider sprovider;

	public MoveToTodayAction(final ISelectionProvider sprovider) {
		super();
		setText(Messages.MoveToTodayAction_MoveToToday);
		setToolTipText(Messages.MoveToTodayAction_MoveTaskToToday);
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ELEMENT));
		updateSelection();
		this.sprovider = sprovider;
		sprovider.addSelectionChangedListener(this);
	}
	
	@Override
	public void run() {
		if((this.sprovider != null) && !this.sprovider.getSelection().isEmpty()) {
			Object sel = ((IStructuredSelection) this.sprovider.getSelection()).getFirstElement();
			if(sel instanceof Task) {
				Task task = (Task) sel;

				Calendar cal = Calendar.getInstance();
				int y = cal.get(Calendar.YEAR);
				int m = cal.get(Calendar.MONTH);
				int d = cal.get(Calendar.DATE);
				
				cal.setTime(task.getStart().getDate());
				cal.set(y,m,d);
				task.getStart().setDate(cal.getTime());

				cal.setTime(task.getEnd().getDate());
				cal.set(y,m,d);
				task.getEnd().setDate(cal.getTime());
			}
		}
	}
	
	public void selectionChanged(final SelectionChangedEvent event) {
		updateSelection();
	}
	
	private void updateSelection() {
		if((this.sprovider != null) && !this.sprovider.getSelection().isEmpty()) {
			EObject eObject = (EObject) ((IStructuredSelection) this.sprovider.getSelection()).getFirstElement();
			if(eObject instanceof Task && !(new CTimeSpan((Task) eObject).isMultiDay())) {
				TimeSpan ts = new TimeSpan();
				ts.setAllDay(new Date());
				if(!ts.overlaps(new CTimeSpan((Task) eObject))) {
					setEnabled(true);
					return;
				}
			}
		}
		setEnabled(false);
	}
}
