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

package org.remus.infomngmnt.ui.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class InformationFormPage extends FormPage {

	private final List<Control> registeredControls;

	private final FocusAdapter focusAdapter = new FocusAdapter() {
		@Override
		public void focusGained(final FocusEvent e) {
			ITextSelection selection = new TextSelection(1, 1);
			getInfoEditor().getActionBarContributor().updateSelectableActions(selection);
		}

		@Override
		public void focusLost(final FocusEvent e) {
			// do nothing
		}
	};

	public InformationFormPage(final FormEditor editor, final String id, final String title) {
		super(editor, id, title);
		this.registeredControls = new ArrayList<Control>();
	}

	protected void addControl(final Control control) {
		control.addFocusListener(this.focusAdapter);
	}

	protected Control getFocusControl() {
		IManagedForm form = getManagedForm();
		if (form == null) {
			return null;
		}
		Control control = form.getForm();
		if (control == null || control.isDisposed()) {
			return null;
		}
		Display display = control.getDisplay();
		Control focusControl = display.getFocusControl();
		if (focusControl == null || focusControl.isDisposed()) {
			return null;
		}
		return focusControl;
	}

	public boolean performGlobalAction(final String actionId) {
		Control focusControl = getFocusControl();
		if (focusControl == null) {
			return false;
		}

		if (canPerformDirectly(actionId, focusControl)) {
			return true;
		}

		return false;
	}

	public boolean canPaste(final Clipboard clipboard) {
		return getFocusControl() instanceof Text || getFocusControl() instanceof StyledText;
	}

	/**
	 * @param selection
	 * @return
	 */
	public boolean canCopy(final ISelection selection) {
		// clients overwrite
		return false;
	}

	/**
	 * @param selection
	 * @return
	 */
	public boolean canCut(final ISelection selection) {
		// clients overwrite
		return false;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}

	protected boolean canPerformDirectly(final String id, final Control control) {
		if (control instanceof Text) {
			Text text = (Text) control;
			if (id.equals(ActionFactory.CUT.getId())) {
				text.cut();
				return true;
			}
			if (id.equals(ActionFactory.COPY.getId())) {
				text.copy();
				return true;
			}
			if (id.equals(ActionFactory.PASTE.getId())) {
				text.paste();
				return true;
			}
			if (id.equals(ActionFactory.SELECT_ALL.getId())) {
				text.selectAll();
				return true;
			}
			if (id.equals(ActionFactory.DELETE.getId())) {
				int count = text.getSelectionCount();
				if (count == 0) {
					int caretPos = text.getCaretPosition();
					text.setSelection(caretPos, caretPos + 1);
				}
				text.insert(""); //$NON-NLS-1$
				return true;
			}
		}
		if (control instanceof StyledText) {
			StyledText text = (StyledText) control;
			if (id.equals(ActionFactory.CUT.getId())) {
				text.cut();
				return true;
			}
			if (id.equals(ActionFactory.COPY.getId())) {
				text.copy();
				return true;
			}
			if (id.equals(ActionFactory.PASTE.getId())) {
				text.paste();
				return true;
			}
			if (id.equals(ActionFactory.SELECT_ALL.getId())) {
				text.selectAll();
				return true;
			}
		}
		return false;
	}

	protected InformationEditor getInfoEditor() {
		return (InformationEditor) getEditor();
	}

}
