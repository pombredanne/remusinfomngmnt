/*******************************************************************************
 * Copyright (c) 2000, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.spellengine;

import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.TextInvocationContext;
import org.eclipse.ui.IEditorPart;

public class AssistContext extends TextInvocationContext implements IInvocationContext {

	private final IEditorPart fEditor;

	/*
	 * @since 3.5
	 */
	public AssistContext(final ISourceViewer sourceViewer, final IEditorPart editor,
			final int offset, final int length) {
		super(sourceViewer, offset, length);
		this.fEditor = editor;
	}

	/*
	 * @since 3.5
	 */
	public AssistContext(final ISourceViewer sourceViewer, final int offset, final int length) {
		this(sourceViewer, null, offset, length);
	}

	/*
	 * Constructor for CorrectionContext.
	 */
	public AssistContext(final int offset, final int length) {
		this(null, offset, length);
	}

	/**
	 * Returns the editor or <code>null</code> if none.
	 * 
	 * @return an <code>IEditorPart</code> or <code>null</code> if none
	 * @since 3.5
	 */
	public IEditorPart getEditor() {
		return this.fEditor;
	}

	/**
	 * Returns the length.
	 * 
	 * @return int
	 */
	public int getSelectionLength() {
		return Math.max(getLength(), 0);
	}

	/**
	 * Returns the offset.
	 * 
	 * @return int
	 */
	public int getSelectionOffset() {
		return getOffset();
	}

}
