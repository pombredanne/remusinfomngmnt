/*******************************************************************************
 * Copyright (c) 2009 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.mail.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;

import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.mail.MailActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MailPreferenceInitializer extends AbstractPreferenceInitializer {

	private final IPreferenceStore store;

	public static final String BODY_FONT = "BODY_FONT"; //$NON-NLS-1$
	public static final String HEADER_FONT = "HEADER_FONT"; //$NON-NLS-1$
	public static final String SUB_HEADER_FONT = "SUB_HEADER_FONT"; //$NON-NLS-1$

	/**
	 * 
	 */
	public MailPreferenceInitializer() {
		this.store = MailActivator.getDefault().getPreferenceStore();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		FontData[] fontData = UIUtil.getDisplay().getSystemFont().getFontData();
		PreferenceConverter.setDefault(this.store, BODY_FONT, fontData);

		for (FontData fontData2 : fontData) {
			fontData2.setHeight(fontData2.getHeight() + 2);
			fontData2.setStyle(SWT.BOLD);
		}
		PreferenceConverter.setDefault(this.store, SUB_HEADER_FONT, fontData);
		for (FontData fontData2 : fontData) {
			fontData2.setHeight(fontData2.getHeight() + 2);
			fontData2.setStyle(SWT.BOLD);
		}
		PreferenceConverter.setDefault(this.store, HEADER_FONT, fontData);

	}

}
