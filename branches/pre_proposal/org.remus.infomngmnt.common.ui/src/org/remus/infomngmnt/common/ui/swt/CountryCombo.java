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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import org.remus.infomngmnt.common.ui.UIUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CountryCombo extends Composite {

	private final ComboViewer combo;
	private Label flagLabel;
	private final boolean showFlag;
	private static List<Country> countries;

	public CountryCombo(final Composite parent, final int style) {
		this(parent, style, false);
	}

	public CountryCombo(final Composite parent, final int style, final boolean showFlag) {
		super(parent, style);
		this.showFlag = showFlag;
		int columns = 1;
		if (showFlag) {
			columns = 2;
		}
		setLayout(UIUtil.createMarginLessGridLayout(columns));
		if (showFlag) {
			this.flagLabel = new Label(this, SWT.NONE);
			GridData gridData = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
			gridData.widthHint = 16;
			gridData.heightHint = 16;
			this.flagLabel.setLayoutData(gridData);
			this.flagLabel.setText("");
		}
		this.combo = new ComboViewer(this);
		this.combo.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		this.combo.setContentProvider(UIUtil.getArrayContentProviderInstance());
		this.combo.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((Country) element).getName();
			}
		});
		this.combo.setInput(getAllCountries());
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

	private static class CountryComparator implements Comparator<Country> {
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

	/**
	 * @return the showFlag
	 */
	public boolean isShowFlag() {
		return this.showFlag;
	}

	/**
	 * @return the flagLabel
	 */
	public Label getFlagLabel() {
		return this.flagLabel;
	}

	public void updateImage(final Image image) {
		if (this.flagLabel != null) {
			this.flagLabel.setImage(image);
			layout();
		}
	}

	public synchronized static List<Country> getAllCountries() {
		if (countries == null) {
			countries = new ArrayList<Country>();
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
		}
		return countries;

	}

}
