/*******************************************************************************
 * Copyright (c) 2009 Andreas Deinlein
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Andreas Deinlein - bibliographic extensions
 *******************************************************************************/
package org.remus.infomngmnt.bibliographic;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author Andreas Deinlein <dev@deasw.com>
 *
 */
public class BibliographicActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.bibliographic";
	
	public static final String BOOK_TYPE_ID = "BOOK"; //$NON-NLS-1$
	
	public static final String ARTICLE_TYPE_ID = "ARTICLE"; //$NON-NLS-1$
	
	public static final String NODE_NAME_BIBTEXKEY = "bibtexkey"; //$NON-NLS-1$
	
	public static final String NODE_NAME_URL = "url"; //$NON-NLS-1$
	
	public static final String NODE_NAME_FILE = "file"; //$NON-NLS-1$
	
	public static final String NODE_NAME_FILES = "files";
	
	public static final String NODE_NAME_FILES_LABEL = "fileLabel";
	
	public static final String NODE_NAME_ABSTRACT = "abstract";
	
	public static final String NODE_NAME_AUTHOR = "author"; //$NON-NLS-1$

	public static final String NODE_NAME_PUBLISHER = "publisher"; //$NON-NLS-1$
	
	public static final String NODE_NAME_YEAR = "year"; //$NON-NLS-1$
	
	public static final String NODE_NAME_JOURNAL = "journal"; //$NON-NLS-1$
	
	public static final String NODE_NAME_VOLUME = "volume"; //$NON-NLS-1$
	
	public static final String NODE_NAME_SERIES = "series"; //$NON-NLS-1$
	
	public static final String NODE_NAME_ADDRESS = "address"; //$NON-NLS-1$
	
	public static final String NODE_NAME_EDITION = "edition"; //$NON-NLS-1$
	
	public static final String NODE_NAME_MONTH = "month"; //$NON-NLS-1$
	
	public static final String NODE_NAME_NOTE = "note"; //$NON-NLS-1$
	
	public static final String NODE_NAME_KEY = "key"; //$NON-NLS-1$
	
	public static final String NODE_NAME_PAGES = "pages"; //$NON-NLS-1$
	
	public static final String NODE_NAME_NUMBER = "number";
		

	// The shared instance
	private static BibliographicActivator plugin;
	
	/**
	 * The constructor
	 */
	public BibliographicActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static BibliographicActivator getDefault() {
		return plugin;
	}

}
