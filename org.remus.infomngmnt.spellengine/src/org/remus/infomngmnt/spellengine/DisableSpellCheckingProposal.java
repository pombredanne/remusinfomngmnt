/*******************************************************************************
 * Copyright (c) 2007, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.spellengine;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.spelling.SpellingService;

/**
 * Proposal to disable spell checking.
 * 
 * @since 3.3
 */
public class DisableSpellCheckingProposal implements IJavaCompletionProposal {

	/** The invocation context */
	private final IInvocationContext fContext;

	/**
	 * Creates a new proposal.
	 * 
	 * @param context
	 *            the invocation context
	 */
	public DisableSpellCheckingProposal(final IInvocationContext context) {
		this.fContext = context;
	}

	/*
	 * @see
	 * org.eclipse.jface.text.contentassist.ICompletionProposal#apply(org.eclipse
	 * .jface.text.IDocument)
	 */
	public final void apply(final IDocument document) {
		IPreferenceStore store = EditorsUI.getPreferenceStore();
		store.setValue(SpellingService.PREFERENCE_SPELLING_ENABLED, false);
	}

	/*
	 * @seeorg.eclipse.jface.text.contentassist.ICompletionProposal#
	 * getAdditionalProposalInfo()
	 */
	public String getAdditionalProposalInfo() {
		return getDisplayString();
	}

	/*
	 * @seeorg.eclipse.jface.text.contentassist.ICompletionProposal#
	 * getContextInformation()
	 */
	public final IContextInformation getContextInformation() {
		return null;
	}

	/*
	 * @see
	 * org.eclipse.jface.text.contentassist.ICompletionProposal#getDisplayString
	 * ()
	 */
	public String getDisplayString() {
		return Messages.DisableSpellCheckingProposal_DisplayString;
	}

	/*
	 * @see org.eclipse.jface.text.contentassist.ICompletionProposal#getImage()
	 */
	public Image getImage() {
		return CommonImageRegistry.getInstance().get(CommonImageRegistry.SPELLING_IGNORE);
	}

	/*
	 * @see org.eclipse.jdt.ui.text.java.IJavaCompletionProposal#getRelevance()
	 */
	public final int getRelevance() {
		return Integer.MIN_VALUE + 1;
	}

	/*
	 * @see
	 * org.eclipse.jface.text.contentassist.ICompletionProposal#getSelection
	 * (org.eclipse.jface.text.IDocument)
	 */
	public final Point getSelection(final IDocument document) {
		return new Point(this.fContext.getSelectionOffset(), this.fContext.getSelectionLength());
	}
}
