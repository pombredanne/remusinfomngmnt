package org.remus.infomngmnt.spellengine;

import java.text.BreakIterator;
import java.util.LinkedList;
import java.util.Locale;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TextUtilities;

/**
 * Iterator to spell check javadoc comment regions.
 * 
 * @since 3.0
 */
public class SpellCheckIterator implements ISpellCheckIterator {

	/** The content of the region */
	protected final String fContent;

	/** The line delimiter */
	private final String fDelimiter;

	/** The last token */
	protected String fLastToken = null;

	/** The next break */
	protected int fNext = 1;

	/** The offset of the region */
	protected final int fOffset;

	/** The predecessor break */
	private int fPredecessor;

	/** The previous break */
	protected int fPrevious = 0;

	/** The sentence breaks */
	private final LinkedList fSentenceBreaks = new LinkedList();

	/** Does the current word start a sentence? */
	private boolean fStartsSentence = false;

	/** The successor break */
	protected int fSuccessor;

	/** The word iterator */
	private final BreakIterator fWordIterator;

	private boolean fIsIgnoringSingleLetters;

	/**
	 * Creates a new spell check iterator.
	 * 
	 * @param document
	 *            the document containing the specified partition
	 * @param region
	 *            the region to spell check
	 * @param locale
	 *            the locale to use for spell checking
	 */
	public SpellCheckIterator(final IDocument document, final IRegion region, final Locale locale) {
		this(document, region, locale, BreakIterator.getWordInstance(locale));
	}

	/**
	 * Creates a new spell check iterator.
	 * 
	 * @param document
	 *            the document containing the specified partition
	 * @param region
	 *            the region to spell check
	 * @param locale
	 *            the locale to use for spell checking
	 * @param breakIterator
	 *            the break-iterator
	 */
	public SpellCheckIterator(final IDocument document, final IRegion region, final Locale locale,
			final BreakIterator breakIterator) {
		this.fOffset = region.getOffset();
		this.fWordIterator = breakIterator;
		this.fDelimiter = TextUtilities.getDefaultLineDelimiter(document);

		String content;
		try {

			content = document.get(region.getOffset(), region.getLength());

		} catch (Exception exception) {
			content = ""; //$NON-NLS-1$
		}
		this.fContent = content;

		this.fWordIterator.setText(content);
		this.fPredecessor = this.fWordIterator.first();
		this.fSuccessor = this.fWordIterator.next();

		final BreakIterator iterator = BreakIterator.getSentenceInstance(locale);
		iterator.setText(content);

		int offset = iterator.current();
		while (offset != BreakIterator.DONE) {

			this.fSentenceBreaks.add(new Integer(offset));
			offset = iterator.next();
		}
	}

	/*
	 * @see
	 * org.eclipse.jdt.internal.ui.text.spelling.engine.ISpellCheckIterator#
	 * setIgnoreSingleLetters(boolean)
	 * 
	 * @since 3.3
	 */
	public void setIgnoreSingleLetters(final boolean state) {
		this.fIsIgnoringSingleLetters = state;
	}

	/*
	 * @see org.eclipse.spelling.done.ISpellCheckIterator#getBegin()
	 */
	public final int getBegin() {
		return this.fPrevious + this.fOffset;
	}

	/*
	 * @see org.eclipse.spelling.done.ISpellCheckIterator#getEnd()
	 */
	public final int getEnd() {
		return this.fNext + this.fOffset - 1;
	}

	/*
	 * @see java.util.Iterator#hasNext()
	 */
	public final boolean hasNext() {
		return this.fSuccessor != BreakIterator.DONE;
	}

	/**
	 * Does the specified token consist of at least one letter and digits only?
	 * 
	 * @param begin
	 *            the begin index
	 * @param end
	 *            the end index
	 * @return <code>true</code> iff the token consists of digits and at least
	 *         one letter only, <code>false</code> otherwise
	 */
	protected final boolean isAlphaNumeric(final int begin, final int end) {

		char character = 0;

		boolean letter = false;
		for (int index = begin; index < end; index++) {

			character = this.fContent.charAt(index);
			if (Character.isLetter(character))
				letter = true;

			if (!Character.isLetterOrDigit(character))
				return false;
		}
		return letter;
	}

	/**
	 * Checks the last token against the given tags?
	 * 
	 * @param tags
	 *            the tags to check
	 * @return <code>true</code> iff the last token is in the given array
	 */
	protected final boolean isToken(final String[] tags) {
		return isToken(this.fLastToken, tags);
	}

	/**
	 * Checks the given token against the given tags?
	 * 
	 * @param token
	 *            the token to check
	 * @param tags
	 *            the tags to check
	 * @return <code>true</code> iff the last token is in the given array
	 * @since 3.3
	 */
	protected final boolean isToken(final String token, final String[] tags) {

		if (token != null) {

			for (int index = 0; index < tags.length; index++) {

				if (token.equals(tags[index]))
					return true;
			}
		}
		return false;
	}

	/**
	 * Is the current token a single letter token surrounded by non-whitespace
	 * characters?
	 * 
	 * @param begin
	 *            the begin index
	 * @return <code>true</code> iff the token is a single letter token,
	 *         <code>false</code> otherwise
	 */
	protected final boolean isSingleLetter(final int begin) {
		if (!Character.isLetter(this.fContent.charAt(begin)))
			return false;

		if (begin > 0 && !Character.isWhitespace(this.fContent.charAt(begin - 1)))
			return false;

		if (begin < this.fContent.length() - 1
				&& !Character.isWhitespace(this.fContent.charAt(begin + 1)))
			return false;

		return true;
	}

	/**
	 * Does the specified token look like an URL?
	 * 
	 * @param begin
	 *            the begin index
	 * @return <code>true</code> iff this token look like an URL,
	 *         <code>false</code> otherwise
	 */
	protected final boolean isUrlToken(final int begin) {

		for (int index = 0; index < DefaultSpellChecker.URL_PREFIXES.length; index++) {

			if (this.fContent.startsWith(DefaultSpellChecker.URL_PREFIXES[index], begin))
				return true;
		}
		return false;
	}

	/**
	 * Does the specified token consist of whitespace only?
	 * 
	 * @param begin
	 *            the begin index
	 * @param end
	 *            the end index
	 * @return <code>true</code> iff the token consists of whitespace only,
	 *         <code>false</code> otherwise
	 */
	protected final boolean isWhitespace(final int begin, final int end) {

		for (int index = begin; index < end; index++) {

			if (!Character.isWhitespace(this.fContent.charAt(index)))
				return false;
		}
		return true;
	}

	/*
	 * @see java.util.Iterator#next()
	 */
	public Object next() {

		String token = nextToken();
		while (token == null && this.fSuccessor != BreakIterator.DONE)
			token = nextToken();

		this.fLastToken = token;

		return token;
	}

	/**
	 * Advances the end index to the next word break.
	 */
	protected final void nextBreak() {

		this.fNext = this.fSuccessor;
		this.fPredecessor = this.fSuccessor;

		this.fSuccessor = this.fWordIterator.next();
	}

	/**
	 * Returns the next sentence break.
	 * 
	 * @return the next sentence break
	 */
	protected final int nextSentence() {
		return ((Integer) this.fSentenceBreaks.getFirst()).intValue();
	}

	/**
	 * Determines the next token to be spell checked.
	 * 
	 * @return the next token to be spell checked, or <code>null</code> iff the
	 *         next token is not a candidate for spell checking.
	 */
	protected String nextToken() {

		String token = null;

		this.fPrevious = this.fPredecessor;
		this.fStartsSentence = false;

		nextBreak();

		boolean update = false;
		if (this.fNext - this.fPrevious > 0) {

			if (!isWhitespace(this.fPrevious, this.fNext)
					&& isAlphaNumeric(this.fPrevious, this.fNext)) {

				if (isUrlToken(this.fPrevious))
					skipTokens(this.fPrevious, ' ');
				else if (this.fNext - this.fPrevious > 1 || isSingleLetter(this.fPrevious)
						&& !this.fIsIgnoringSingleLetters)
					token = this.fContent.substring(this.fPrevious, this.fNext);

				update = true;
			}
		}

		if (update && this.fSentenceBreaks.size() > 0) {

			if (this.fPrevious >= nextSentence()) {

				while (this.fSentenceBreaks.size() > 0 && this.fPrevious >= nextSentence())
					this.fSentenceBreaks.removeFirst();

				this.fStartsSentence = (this.fLastToken == null) || (token != null);
			}
		}
		return token;
	}

	/*
	 * @see java.util.Iterator#remove()
	 */
	public final void remove() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Skip the tokens until the stop character is reached.
	 * 
	 * @param begin
	 *            the begin index
	 * @param stop
	 *            the stop character
	 */
	protected final void skipTokens(final int begin, final char stop) {

		int end = begin;

		while (end < this.fContent.length() && this.fContent.charAt(end) != stop)
			end++;

		if (end < this.fContent.length()) {

			this.fNext = end;
			this.fPredecessor = this.fNext;

			this.fSuccessor = this.fWordIterator.following(this.fNext);
		} else
			this.fSuccessor = BreakIterator.DONE;
	}

	/*
	 * @see org.eclipse.spelling.done.ISpellCheckIterator#startsSentence()
	 */
	public final boolean startsSentence() {
		return this.fStartsSentence;
	}
}
