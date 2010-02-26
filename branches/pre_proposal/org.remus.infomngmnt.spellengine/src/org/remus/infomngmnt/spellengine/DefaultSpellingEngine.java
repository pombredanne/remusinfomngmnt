package org.remus.infomngmnt.spellengine;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.content.IContentTypeManager;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.ui.texteditor.spelling.ISpellingEngine;
import org.eclipse.ui.texteditor.spelling.ISpellingProblemCollector;
import org.eclipse.ui.texteditor.spelling.SpellingContext;

/**
 * Default spelling engine.
 * <p>
 * Internally this spelling engine uses a different spelling engine depending on
 * the {@linkplain IContentType content type}. Currently this engine supports
 * the text, Java and Java properties file content types.
 * </p>
 * 
 * @since 3.1
 */
public class DefaultSpellingEngine implements ISpellingEngine {

	/** Text content type */
	private static final IContentType TEXT_CONTENT_TYPE = Platform.getContentTypeManager()
			.getContentType(IContentTypeManager.CT_TEXT);

	/** Available spelling engines by content type */
	private final Map fEngines = new HashMap();

	/**
	 * Initialize concrete engines.
	 */
	public DefaultSpellingEngine() {
		if (TEXT_CONTENT_TYPE != null)
			this.fEngines.put(TEXT_CONTENT_TYPE, new TextSpellingEngine());
	}

	/*
	 * @see
	 * org.eclipse.ui.texteditor.spelling.ISpellingEngine#check(org.eclipse.
	 * jface.text.IDocument, org.eclipse.jface.text.IRegion[],
	 * org.eclipse.ui.texteditor.spelling.SpellingContext,
	 * org.eclipse.ui.texteditor.spelling.ISpellingProblemCollector,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void check(final IDocument document, final IRegion[] regions,
			final SpellingContext context, final ISpellingProblemCollector collector,
			final IProgressMonitor monitor) {
		ISpellingEngine engine = getEngine(context.getContentType());
		if (engine == null)
			engine = getEngine(TEXT_CONTENT_TYPE);
		if (engine != null)
			engine.check(document, regions, context, collector, monitor);
	}

	/**
	 * Returns a spelling engine for the given content type or <code>null</code>
	 * if none could be found.
	 * 
	 * @param contentType
	 *            the content type
	 * @return a spelling engine for the given content type or <code>null</code>
	 *         if none could be found
	 */
	private ISpellingEngine getEngine(final IContentType contentType) {
		if (contentType == null)
			return null;

		if (this.fEngines.containsKey(contentType))
			return (ISpellingEngine) this.fEngines.get(contentType);

		return getEngine(contentType.getBaseType());
	}
}
