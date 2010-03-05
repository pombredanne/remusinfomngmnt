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

import org.remus.infomngmnt.services.RemusServiceTracker;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author Andreas Deinlein <dev@deasw.com>
 * 
 */
public class BibliographicActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.bibliographic";

	// Bibliographic entry types
	public static final String BOOK_TYPE_ID = "BOOK"; //$NON-NLS-1$	
	public static final String ARTICLE_TYPE_ID = "ARTICLE"; //$NON-NLS-1$
	public static final String BOOKLET_TYPE_ID = "BOOKLET"; //$NON-NLS-1$
	public static final String CONFERENCE_TYPE_ID = "CONFERENCE"; //$NON-NLS-1$
	public static final String INBOOK_TYPE_ID = "INBOOK"; //$NON-NLS-1$
	public static final String INCOLLECTION_TYPE_ID = "INCOLLECTION"; //$NON-NLS-1$
	public static final String INPROCEEDINGS_TYPE_ID = "INPROCEEDINGS"; //$NON-NLS-1$
	public static final String MANUAL_TYPE_ID = "MANUAL"; //$NON-NLS-1$
	public static final String MASTERSTHESIS_TYPE_ID = "MASTERSTHESIS"; //$NON-NLS-1$
	public static final String MISC_TYPE_ID = "MISC"; //$NON-NLS-1$
	public static final String PHDTHESIS_TYPE_ID = "PHDTHESIS"; //$NON-NLS-1$
	public static final String PROCEEDINGS_TYPE_ID = "PROCEEDINGS"; //$NON-NLS-1$
	public static final String TECHREPORT_TYPE_ID = "TECHREPORT"; //$NON-NLS-1$
	public static final String UNPUBLISHED_TYPE_ID = "UNPUBLISHED"; //$NON-NLS-1$

	// Official bibliographic fields
	public static final String NODE_NAME_BIBTEXKEY = "bibtexkey"; //$NON-NLS-1$
	public static final String NODE_NAME_ADDRESS = "address"; //$NON-NLS-1$
	public static final String NODE_NAME_ANNOTE = "annote"; //$NON-NLS-1$
	public static final String NODE_NAME_AUTHOR = "author"; //$NON-NLS-1$
	public static final String NODE_NAME_BOOKTITLE = "booktitle"; //$NON-NLS-1$
	public static final String NODE_NAME_CHAPTER = "chapter"; //$NON-NLS-1$
	public static final String NODE_NAME_CROSSREF = "crossref"; //$NON-NLS-1$
	public static final String NODE_NAME_EDITION = "edition"; //$NON-NLS-1$
	public static final String NODE_NAME_EDITOR = "editor"; //$NON-NLS-1$
	public static final String NODE_NAME_EPRINT = "eprint"; //$NON-NLS-1$
	public static final String NODE_NAME_HOWPUBLISHED = "howpublished"; //$NON-NLS-1$
	public static final String NODE_NAME_INSTITUTION = "institution"; //$NON-NLS-1$
	public static final String NODE_NAME_JOURNAL = "journal"; //$NON-NLS-1$
	public static final String NODE_NAME_KEY = "key"; //$NON-NLS-1$
	public static final String NODE_NAME_MONTH = "month"; //$NON-NLS-1$	
	public static final String NODE_NAME_NOTE = "note"; //$NON-NLS-1$
	public static final String NODE_NAME_NUMBER = "number"; //$NON-NLS-1$
	public static final String NODE_NAME_ORGANIZATION = "organization"; //$NON-NLS-1$
	public static final String NODE_NAME_PAGES = "pages"; //$NON-NLS-1$	
	public static final String NODE_NAME_PUBLISHER = "publisher"; //$NON-NLS-1$
	public static final String NODE_NAME_SCHOOL = "school"; //$NON-NLS-1$
	public static final String NODE_NAME_SERIES = "series"; //$NON-NLS-1$
	public static final String NODE_NAME_TYPE = "type"; //$NON-NLS-1$
	public static final String NODE_NAME_URL = "url"; //$NON-NLS-1$
	public static final String NODE_NAME_VOLUME = "volume"; //$NON-NLS-1$	
	public static final String NODE_NAME_YEAR = "year"; //$NON-NLS-1$

	// Other fields
	public static final String NODE_NAME_ABSTRACT = "abstract"; //$NON-NLS-1$	
	public static final String NODE_NAME_CONTENTS = "contents"; //$NON-NLS-1$
	public static final String NODE_NAME_SUMMARY = "summary"; //$NON-NLS-1$
	public static final String NODE_NAME_ISBN = "isbn"; //$NON-NLS-1$		
	public static final String NODE_NAME_ISSN = "issn"; //$NON-NLS-1$
	public static final String NODE_NAME_DOI = "doi"; //$NON-NLS-1$
	public static final String NODE_NAME_FILE = "file"; //$NON-NLS-1$	
	public static final String NODE_NAME_FILES = "files"; //$NON-NLS-1$
	public static final String NODE_NAME_FILES_LABEL = "fileLabel"; //$NON-NLS-1$	

	// The shared instance
	private static BibliographicActivator plugin;

	private RemusServiceTracker serviceTracker;

	/**
	 * The constructor
	 */
	public BibliographicActivator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		this.serviceTracker = new RemusServiceTracker(getBundle());
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
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

	public RemusServiceTracker getServiceTracker() {
		return this.serviceTracker;
	}

}
