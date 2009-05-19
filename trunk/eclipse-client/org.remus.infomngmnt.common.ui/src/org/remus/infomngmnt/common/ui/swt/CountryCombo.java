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

package org.remus.infomngmnt.common.ui.swt;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import org.remus.infomngmnt.common.ui.UIUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CountryCombo extends Composite {

	private final ComboViewer combo;

	public CountryCombo(final Composite parent, final int style) {
		super(parent, style);
		setLayout(UIUtil.createMarginLessGridLayout(1));
		this.combo = new ComboViewer(this);
		List<Country> countries = new ArrayList<Country>();
		this.combo.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Locale[] locales = Locale.getAvailableLocales();
		List<String> isos = new ArrayList<String>();
		for (Locale locale : locales) {
			String iso = locale.getISO3Country();
			String code = locale.getCountry();
			String name = locale.getDisplayCountry();

			if (!"".equals(iso) && !"".equals(code) && !"".equals(name) && !isos.contains(iso)) {
				countries.add(new Country(iso, code, name));
				isos.add(iso);
			}
		}

		Collections.sort(countries, new CountryComparator());
		this.combo.setContentProvider(UIUtil.getArrayContentProviderInstance());
		this.combo.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((Country) element).getName();
			}
		});
		this.combo.setInput(countries);
	}

	public static class Country {
		private final String iso;

		private final String code;

		public String name;

		Country(final String iso, final String code, final String name) {
			this.iso = iso;
			this.code = code;
			this.name = name;
		}

		@Override
		public String toString() {
			return this.iso + " - " + this.code + " - " + this.name.toUpperCase();
		}

		/**
		 * @return the iso
		 */
		public String getIso() {
			return this.iso;
		}

		/**
		 * @return the code
		 */
		public String getCode() {
			return this.code;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return this.name;
		}
	}

	private class CountryComparator implements Comparator<Country> {
		private final Comparator comparator;

		CountryComparator() {
			this.comparator = Collator.getInstance();
		}

		public int compare(final Country o1, final Country o2) {
			return this.comparator.compare(o1.name, o2.name);
		}
	}

	/**
	 * @return the combo
	 */
	public ComboViewer getCombo() {
		return this.combo;
	}

}
