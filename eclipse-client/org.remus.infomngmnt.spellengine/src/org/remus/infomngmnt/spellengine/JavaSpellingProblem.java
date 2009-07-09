/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.spellengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.quickassist.IQuickAssistInvocationContext;
import org.eclipse.ui.texteditor.spelling.SpellingProblem;

/**
 * A {@link SpellingProblem} that adapts a {@link ISpellEvent}.
 * <p>
 * TODO: remove {@link ISpellEvent} notification mechanism
 * </p>
 */
public class JavaSpellingProblem extends SpellingProblem {

	/** Spell event */
	private final ISpellEvent fSpellEvent;

	/**
	 * The associated document.
	 * 
	 * @since 3.3
	 */
	private final IDocument fDocument;

	/**
	 * Initialize with the given spell event.
	 * 
	 * @param spellEvent
	 *            the spell event
	 * @param document
	 *            the document
	 */
	public JavaSpellingProblem(final ISpellEvent spellEvent, final IDocument document) {
		Assert.isLegal(document != null);
		Assert.isLegal(spellEvent != null);
		this.fSpellEvent = spellEvent;
		this.fDocument = document;
	}

	/*
	 * @see org.eclipse.ui.texteditor.spelling.SpellingProblem#getOffset()
	 */
	@Override
	public int getOffset() {
		return this.fSpellEvent.getBegin();
	}

	/*
	 * @see org.eclipse.ui.texteditor.spelling.SpellingProblem#getLength()
	 */
	@Override
	public int getLength() {
		return this.fSpellEvent.getEnd() - this.fSpellEvent.getBegin() + 1;
	}

	/*
	 * @see org.eclipse.ui.texteditor.spelling.SpellingProblem#getMessage()
	 */
	@Override
	public String getMessage() {
		if (isSentenceStart() && isDictionaryMatch())
			return "change case " + this.fSpellEvent.getWord();

		return "error " + this.fSpellEvent.getWord();
	}

	/*
	 * @see org.eclipse.ui.texteditor.spelling.SpellingProblem#getProposals()
	 */
	@Override
	public ICompletionProposal[] getProposals() {
		return getProposals(null);
	}

	/*
	 * @see
	 * org.eclipse.ui.texteditor.spelling.SpellingProblem#getProposals(org.eclipse
	 * .jface.text.quickassist.IQuickAssistInvocationContext)
	 * 
	 * @since 3.4
	 */
	@Override
	public ICompletionProposal[] getProposals(final IQuickAssistInvocationContext originalContext) {
		String[] arguments = getArguments();
		if (arguments == null)
			return new ICompletionProposal[0];

		if (arguments[0].indexOf('&') != -1 && isIgnoringAmpersand())
			return new ICompletionProposal[0]; // no proposals for now

		final int threshold = PreferenceConstants.getPreferenceStore().getInt(
				PreferenceConstants.SPELLING_PROPOSAL_THRESHOLD);
		int size = 0;
		List proposals = null;

		RankedWordProposal proposal = null;
		IJavaCompletionProposal[] result = null;
		int index = 0;

		boolean fixed = false;
		boolean match = false;
		boolean sentence = false;

		final ISpellCheckEngine engine = SpellCheckEngine.getInstance();
		final ISpellChecker checker = engine.getSpellChecker();

		if (checker != null) {

			IInvocationContext context;
			if (originalContext == null)
				context = new AssistContext(null, getOffset(), getLength());
			else
				context = new AssistContext(originalContext.getSourceViewer(), getOffset(),
						getLength());

			// FIXME: this is a pretty ugly hack

			proposals = new ArrayList(checker.getProposals(arguments[0], sentence));
			size = proposals.size();

			if (threshold > 0 && size > threshold) {

				Collections.sort(proposals);
				proposals = proposals.subList(size - threshold - 1, size - 1);
				size = proposals.size();
			}

			boolean extendable = !fixed ? (checker.acceptsWords() || AddWordProposal
					.canAskToConfigure()) : false;
			result = new IJavaCompletionProposal[size + (extendable ? 3 : 2)];

			for (index = 0; index < size; index++) {

				proposal = (RankedWordProposal) proposals.get(index);
				result[index] = new WordCorrectionProposal(proposal.getText(), arguments,
						getOffset(), getLength(), context, proposal.getRank());
			}

			if (extendable)
				result[index++] = new AddWordProposal(arguments[0], context);

			result[index++] = new WordIgnoreProposal(arguments[0], context);
			result[index++] = new DisableSpellCheckingProposal(context);

		}

		return result;
	}

	private boolean isIgnoringAmpersand() {
		return PreferenceConstants.getPreferenceStore().getBoolean(
				PreferenceConstants.SPELLING_IGNORE_AMPERSAND_IN_PROPERTIES);
	}

	public String[] getArguments() {

		String prefix = ""; //$NON-NLS-1$
		String postfix = ""; //$NON-NLS-1$
		String word;
		try {
			word = this.fDocument.get(getOffset(), getLength());
		} catch (BadLocationException e) {
			return null;
		}

		try {

			IRegion line = this.fDocument.getLineInformationOfOffset(getOffset());
			prefix = this.fDocument.get(line.getOffset(), getOffset() - line.getOffset());
			int postfixStart = getOffset() + getLength();
			postfix = this.fDocument.get(postfixStart, line.getOffset() + line.getLength()
					- postfixStart);

		} catch (BadLocationException exception) {
			// Do nothing
		}
		return new String[] { word, prefix, postfix,
				isSentenceStart() ? Boolean.toString(true) : Boolean.toString(false),
				isDictionaryMatch() ? Boolean.toString(true) : Boolean.toString(false) };
	}

	/**
	 * Returns <code>true</code> iff the corresponding word was found in the
	 * dictionary.
	 * <p>
	 * NOTE: to be removed, see {@link #getProposals()}
	 * </p>
	 * 
	 * @return <code>true</code> iff the corresponding word was found in the
	 *         dictionary
	 */
	public boolean isDictionaryMatch() {
		return this.fSpellEvent.isMatch();
	}

	/**
	 * Returns <code>true</code> iff the corresponding word starts a sentence.
	 * <p>
	 * NOTE: to be removed, see {@link #getProposals()}
	 * </p>
	 * 
	 * @return <code>true</code> iff the corresponding word starts a sentence
	 */
	public boolean isSentenceStart() {
		return this.fSpellEvent.isStart();
	}

}
