/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.spellengine;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Persistent modifiable word-list based dictionary.
 * 
 * @since 3.0
 */
public class PersistentSpellDictionary extends AbstractSpellDictionary {

	/** The word list location */
	private final URL fLocation;

	/**
	 * Creates a new persistent spell dictionary.
	 * 
	 * @param url
	 *            The URL of the word list for this dictionary
	 */
	public PersistentSpellDictionary(final URL url) {
		this.fLocation = url;
	}

	/*
	 * @see
	 * org.eclipse.jdt.ui.text.spelling.engine.AbstractSpellDictionary#acceptsWords
	 * ()
	 */
	@Override
	public boolean acceptsWords() {
		return true;
	}

	/*
	 * @see
	 * com.onpositive.internal.ui.text.spelling.engine.ISpellDictionary#addWord
	 * (java.lang.String)
	 */
	@Override
	public void addWord(final String word) {
		if (this.isCorrect(word)) {
			return;
		}

		final OutputStreamWriter writer = null;
		try {
			final Charset charset = Charset.forName(this.getEncoding());
			final ByteBuffer byteBuffer = charset.encode(word + "\n"); //$NON-NLS-1$
			final int size = byteBuffer.limit();
			final byte[] byteArray;
			if (byteBuffer.hasArray()) {
				byteArray = byteBuffer.array();
			} else {
				byteArray = new byte[size];
				byteBuffer.get(byteArray);
			}

			final FileOutputStream fileStream = new FileOutputStream(this.fLocation.getPath(), true);

			// Encoding UTF-16 charset writes a BOM. In which case we need to
			// cut it away if the file isn't empty
			int bomCutSize = 0;
			if (!this.isEmpty() && "UTF-16".equals(charset.name())) {
				bomCutSize = 2;
			}

			fileStream.write(byteArray, bomCutSize, size - bomCutSize);
		} catch (final IOException exception) {
			SpellActivator.log(exception);
			return;
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (final IOException e) {
			}
		}

		this.hashWord(word);
	}

	/*
	 * @see
	 * com.onpositive.internal.ui.text.spelling.engine.AbstractSpellDictionary
	 * #getURL()
	 */
	@Override
	protected final URL getURL() {
		return this.fLocation;
	}
}
