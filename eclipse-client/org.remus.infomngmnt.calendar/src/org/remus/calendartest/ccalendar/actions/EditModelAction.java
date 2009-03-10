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

package org.remus.calendartest.ccalendar.actions;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * @author jeremy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EditModelAction extends Action implements ISelectionChangedListener {
	public static final String ID = "org.aspencloud.calypso.ui.actions.EditModelAction";

	private final ISelectionProvider sprovider;

	public EditModelAction(final ISelectionProvider sprovider) {
		super();
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ELEMENT));
		updateSelection();
		this.sprovider = sprovider;
		sprovider.addSelectionChangedListener(this);
	}
	
	@Override
	public void run() {
		if((this.sprovider != null) && !this.sprovider.getSelection().isEmpty()) {
			Object sel = ((IStructuredSelection) this.sprovider.getSelection()).getFirstElement();
//			if(sel instanceof ChannelWrapper) {
//				ChannelWrapperDialog dlg = new ChannelWrapperDialog(
//						Display.getCurrent().getActiveShell(), (ChannelWrapper) sel);
//				dlg.open();
//			} else {
//				ModelDialog dlg = new ModelDialog(
//						Display.getCurrent().getActiveShell(), (EObject) sel);
//				dlg.open();
//			}
		}
	}
	
	public void selectionChanged(final SelectionChangedEvent event) {
		updateSelection();
	}
	
	private void updateSelection() {
		if((this.sprovider != null) && !this.sprovider.getSelection().isEmpty()) {
			EObject eObject = (EObject) ((IStructuredSelection) this.sprovider.getSelection()).getFirstElement();
			setText("Edit...");
			setToolTipText("Edit...");
			setEnabled(true);
		} else {
			setEnabled(false);
		}			
	}
}
