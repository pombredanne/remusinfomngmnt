/*******************************************************************************
 * Copyright (c) 2009 Jan Hartwig, FEB Radebeul
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Jan Hartwig - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.password.generator;

/**
 * @author Jan Hartwig <jhartwig@feb-radebeul.de>
 * 
 */
public class PGSettings {
	public static final String[] ITEMS_DEFAULT_PASSWORD_LENGTH = { "8", "10", "12", "16", "20", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
			"64", "128", "256" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	public static final String CHARACTERS_WIDE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //$NON-NLS-1$
	public static final String CHARACTERS_SMALL = "abcdefghijklmnopqrstuvwxyz"; //$NON-NLS-1$
	public static final String CHARACTERS_NUMBERS = "0123456789"; //$NON-NLS-1$
	public static final int QUANTITY_PASSWORDS = 5;
	public static final String AC_DEFAULT_ADDITIONAL_CHARACTERS = "@<({[/=]})>!?$%&#*-+.,;:_"; //$NON-NLS-1$
	public static final String AC_RADIO_DEFAULT_PASSWORD = "raddefpwd"; //$NON-NLS-1$
	public static final String AC_RADIO_USER_DEFINED_PASSWORD = "raduserdefpwd"; //$NON-NLS-1$
	public static final String AC_CURRENT_PASSWORD_LENGTH = "curpwdlen"; //$NON-NLS-1$
	public static final String AC_CHECK_WIDE = "ckwide"; //$NON-NLS-1$
	public static final String AC_CHECK_NUMBER = "chnumber"; //$NON-NLS-1$
	public static final String AC_CHECK_SMALL = "cksmall"; //$NON-NLS-1$
	public static final String AC_CHECK_ADDITIONAL = "ckadd"; //$NON-NLS-1$
	public static final String AC_TX_ADDITIONAL_CHARACTERS = "txaddchar"; //$NON-NLS-1$
	public static final String AC_COMBO_DEFAULT_PASSWORD_INDEX = "cbdefpwdi"; //$NON-NLS-1$
	public static final String AC_COMBO_DEFAULT_PASSWORD_ENABLED = "cbdefpwden"; //$NON-NLS-1$
	public static final String AC_SP_PASSWORD_LENGTH = "sppwdlen"; //$NON-NLS-1$
	public static final String AC_TX_ADDITIONAL_CHARACTERS_ENABLED = "txaddcharen"; //$NON-NLS-1$
	public static final String AC_USER_SETTINGS = "defsettings"; //$NON-NLS-1$
	public static final String AC_TV_PASSWORDS_ENABLED = "tvpwden"; //$NON-NLS-1$
	public static final String AC_BT_GENERATE_PASSWORDS_ENABLED = "btgenpwden"; //$NON-NLS-1$
}
