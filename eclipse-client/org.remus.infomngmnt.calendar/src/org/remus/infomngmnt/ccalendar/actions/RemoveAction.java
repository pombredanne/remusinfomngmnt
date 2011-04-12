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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.aspencloud.calypso.util.CalypsoEdit;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import org.remus.infomngmnt.calendar.messages.Messages;
import org.remus.infomngmnt.calendar.model.Task;

/**
 * @author jeremy
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class RemoveAction extends Action implements ISelectionChangedListener {
	public static final String ID = "org.aspencloud.calypso.ui.actions.RemoveAction"; //$NON-NLS-1$

	private final ISelectionProvider provider;

	public RemoveAction(final ISelectionProvider provider) {
		super();
		this.provider = provider;
		provider.addSelectionChangedListener(this);

		setText(Messages.RemoveAction_Delete);
		setToolTipText(Messages.RemoveAction_Delete);
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		setDisabledImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE_DISABLED));

		setEnabled(false);
	}

	@Override
	public void run() {
		if (!this.provider.getSelection().isEmpty()) {
			Shell shell = new Shell(Display.getCurrent().getActiveShell());
			MessageBox msg = new MessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
			msg.setText(Messages.RemoveAction_Delete);
			msg.setMessage(Messages.RemoveAction_DeleteConfirm);
			int rval = msg.open();
			if (rval == SWT.NO) {
				return;
			}

			List list = new ArrayList();
			for (Iterator i = ((IStructuredSelection) this.provider.getSelection()).iterator(); i
					.hasNext();) {
				Object o = i.next();
				if (o instanceof Task) {
					Task task = (Task) o;

					list.add(task);

				}
			}

			if (!list.isEmpty()) {
				CalypsoEdit.delete(list);
			}
		}
	}

	public void selectionChanged(final SelectionChangedEvent event) {
		List list = ((IStructuredSelection) event.getSelection()).toList();
		for (Object object : list) {
			if (object instanceof Task) {
				if (((Task) object).isReadonly()) {
					setEnabled(false);
					return;
				}
			}
		}
		setEnabled(!this.provider.getSelection().isEmpty());
	}
}
