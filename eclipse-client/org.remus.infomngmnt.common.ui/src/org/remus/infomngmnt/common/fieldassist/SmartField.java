/*******************************************************************************
 * Copyright (c) 2006, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Sebastian Davids <sdavids@gmx.de> - bug 132479 - [FieldAssist] Field assist example improvements
 *     Tom Seidel - Integration
 *******************************************************************************/

package org.remus.infomngmnt.common.fieldassist;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.fieldassist.IControlContentAdapter;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class SmartField {

	private static final String DEC_CONTENTASSIST = "org.remus.infomngmnt.common.ui.contentassist";

	ControlDecoration controlDecoration;

	protected Control control;

	IControlContentAdapter contentAdapter;

	FieldDecoration errorDecoration, warningDecoration;

	protected SmartField(Control control,
			IControlContentAdapter adapter) {
		this.contentAdapter = adapter;
		this.control = control;
		this.control.addListener(SWT.Modify, new Listener() {
			public void handleEvent(Event event) {
				handleModify();
			}
		});
		this.controlDecoration = getControlDecoration();
		installContentProposalAdapter(this.control, new TextContentAdapter());
	}

	boolean isRequiredField() {
		return true;
	}

	boolean hasQuickFix() {
		return false;
	}

	public void quickFix() {
		// do nothing
	}

	public boolean hasContentAssist() {
		return false;
	}

	public void dispose() {
		// do nothing
	}

	public FieldDecoration getErrorDecoration() {
		if (this.errorDecoration == null) {
			FieldDecoration standardError;
			if (hasQuickFix()) {
				standardError = FieldDecorationRegistry.getDefault()
				.getFieldDecoration(
						FieldDecorationRegistry.DEC_ERROR_QUICKFIX);
			} else {
				standardError = FieldDecorationRegistry.getDefault()
				.getFieldDecoration(
						FieldDecorationRegistry.DEC_ERROR);
			}
			if (getErrorMessage() == null) {
				this.errorDecoration = standardError;
			} else {
				this.errorDecoration = new FieldDecoration(standardError
						.getImage(), getErrorMessage());
			}
		}
		return this.errorDecoration;

	}

	public FieldDecoration getWarningDecoration() {
		if (this.warningDecoration == null) {
			FieldDecoration standardWarning = FieldDecorationRegistry
			.getDefault().getFieldDecoration(
					FieldDecorationRegistry.DEC_WARNING);
			if (getWarningMessage() == null) {
				this.warningDecoration = standardWarning;
			} else {
				this.warningDecoration = new FieldDecoration(standardWarning
						.getImage(), getWarningMessage());
			}
		}
		return this.warningDecoration;
	}

	public String getContents() {
		return this.contentAdapter.getControlContents(this.control);
	}

	public void setContents(String contents) {
		this.contentAdapter.setControlContents(this.control, contents, contents
				.length());
	}

	public abstract boolean isValid();

	public  abstract boolean isWarning();

	public String getErrorMessage() {
		return null;
	}

	public String getWarningMessage() {
		return null;
	}

	protected int getDecorationStyle() {
		return SWT.CENTER | SWT.LEFT;
	}

	protected ControlDecoration getControlDecoration() {
		ControlDecoration dec = new ControlDecoration(this.control,
				getDecorationStyle());
		return dec;
	}

	protected void installContentProposalAdapter(Control control,
			IControlContentAdapter contentAdapter) {
		boolean propagate = true;
		int autoActivationDelay = 500;
		KeyStroke keyStroke;
		try {
			keyStroke = KeyStroke.getInstance("M1+SPACE");
		} catch (ParseException e) {
			keyStroke = KeyStroke.getInstance(SWT.CTRL, 32);
		}

		ContentProposalAdapter adapter = new ContentProposalAdapter(control,
				contentAdapter, getContentProposalProvider(), keyStroke,
				null) {
			@Override
			public void closeProposalPopup() {
				closeProposalPopup();
			}
		};
		adapter.setAutoActivationDelay(autoActivationDelay);
		adapter.setPropagateKeys(propagate);
		adapter.setFilterStyle(getContentAssistFilterStyle());
		adapter.setProposalAcceptanceStyle(getContentAssistAcceptance());
		adapter.setLabelProvider(getLabelProvider());
	}

	protected IContentProposalProvider getContentProposalProvider() {
		return null;
	}

	protected int getContentAssistFilterStyle() {
		return ContentProposalAdapter.FILTER_NONE;
	}

	protected int getContentAssistAcceptance() {
		return ContentProposalAdapter.PROPOSAL_REPLACE;
	}

	protected ILabelProvider getLabelProvider() {
		return null;
	}

	void handleModify() {
		// Error indicator supercedes all others
		if (isValid()) {
			//showError(smartField);
		} else {
			//hideError(smartField);
			if (isWarning()) {
				//showWarning(smartField);
			} else {
				//hideWarning(smartField);
				if (hasContentAssist()) {
					showContentAssistDecoration(true);
				} else {
					showContentAssistDecoration(false);
				}
			}
		}
	}

	void showContentAssistDecoration(boolean show) {
		FieldDecoration dec = getCueDecoration();
		if (show) {
			this.controlDecoration.setImage(dec.getImage());
			this.controlDecoration.setDescriptionText(dec.getDescription());
			this.controlDecoration.setShowOnlyOnFocus(true);
			this.controlDecoration.show();
		} else {
			this.controlDecoration.hide();
		}
	}

	protected FieldDecoration getCueDecoration() {
		// We use our own decoration which is based on the JFace version.
		FieldDecorationRegistry registry = FieldDecorationRegistry.getDefault();
		FieldDecoration dec = registry
		.getFieldDecoration(DEC_CONTENTASSIST);
		if (dec == null) {
			// Get the standard one. We use its image and our own customized
			// text.
			FieldDecoration standardDecoration = registry
			.getFieldDecoration(FieldDecorationRegistry.DEC_CONTENT_PROPOSAL);
			registry.registerFieldDecoration(
					DEC_CONTENTASSIST,"Content assist available (Ctrl+SPACE)", standardDecoration.getImage());
			dec = registry.getFieldDecoration(DEC_CONTENTASSIST);
		}
		return dec;
	}

}
