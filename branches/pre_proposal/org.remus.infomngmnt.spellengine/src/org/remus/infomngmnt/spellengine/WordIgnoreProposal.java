package org.remus.infomngmnt.spellengine;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.quickassist.IQuickAssistInvocationContext;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.texteditor.spelling.SpellingProblem;

/**
 * Proposal to ignore the word during the current editing session.
 * 
 * @since 3.0
 */
public class WordIgnoreProposal implements IJavaCompletionProposal {

	/** The invocation context */
	private final IInvocationContext fContext;

	/** The word to ignore */
	private final String fWord;

	/**
	 * Creates a new spell ignore proposal.
	 * 
	 * @param word
	 *            The word to ignore
	 * @param context
	 *            The invocation context
	 */
	public WordIgnoreProposal(final String word, final IInvocationContext context) {
		this.fWord = word;
		this.fContext = context;
	}

	/*
	 * @see
	 * org.eclipse.jface.text.contentassist.ICompletionProposal#apply(org.eclipse
	 * .jface.text.IDocument)
	 */
	public final void apply(final IDocument document) {

		final ISpellCheckEngine engine = SpellCheckEngine.getInstance();
		final ISpellChecker checker = engine.getSpellChecker();

		if (checker != null) {
			checker.ignoreWord(this.fWord);
			if (this.fContext instanceof IQuickAssistInvocationContext) {
				ISourceViewer sourceViewer = ((IQuickAssistInvocationContext) this.fContext)
						.getSourceViewer();
				if (sourceViewer != null)
					SpellingProblem.removeAll(sourceViewer, this.fWord);
			}
		}
	}

	/*
	 * @seeorg.eclipse.jface.text.contentassist.ICompletionProposal#
	 * getAdditionalProposalInfo()
	 */
	public String getAdditionalProposalInfo() {
		return NLS.bind(Messages.WordIgnoreProposal_IgnoreMessage, WordCorrectionProposal.getHtmlRepresentation(this.fWord));
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
		return NLS.bind(Messages.WordIgnoreProposal_IgnoreMessage1, this.fWord);
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
