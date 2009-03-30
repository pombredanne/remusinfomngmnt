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
	public static final String[] ITEMS_DEFAULT_PASSWORD_LENGTH = { "8", "10", "12", "16", "20",
			"64", "128", "256" };
	public static final String CHARACTERS_WIDE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String CHARACTERS_SMALL = "abcdefghijklmnopqrstuvwxyz";
	public static final String CHARACTERS_NUMBERS = "0123456789";
	public static final int QUANTITY_PASSWORDS = 5;
	public static final String AC_DEFAULT_ADDITIONAL_CHARACTERS = "@<({[/=]})>!?$%&#*-+.,;:_";
	public static final String AC_RADIO_DEFAULT_PASSWORD = "raddefpwd";
	public static final String AC_RADIO_USER_DEFINED_PASSWORD = "raduserdefpwd";
	public static final String AC_CURRENT_PASSWORD_LENGTH = "curpwdlen";
	public static final String AC_CHECK_WIDE = "ckwide";
	public static final String AC_CHECK_NUMBER = "chnumber";
	public static final String AC_CHECK_SMALL = "cksmall";
	public static final String AC_CHECK_ADDITIONAL = "ckadd";
	public static final String AC_TX_ADDITIONAL_CHARACTERS = "txaddchar";
	public static final String AC_COMBO_DEFAULT_PASSWORD_INDEX = "cbdefpwdi";
	public static final String AC_COMBO_DEFAULT_PASSWORD_ENABLED = "cbdefpwden";
	public static final String AC_SP_PASSWORD_LENGTH = "sppwdlen";
	public static final String AC_TX_ADDITIONAL_CHARACTERS_ENABLED = "txaddcharen";
	public static final String AC_USER_SETTINGS = "defsettings";
}
