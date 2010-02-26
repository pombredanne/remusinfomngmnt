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

package org.remus.infomngmnt.common.ui.databinding;

import java.util.List;

import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Image;

import org.remus.infomngmnt.common.flags.Activator;
import org.remus.infomngmnt.common.flags.FlagUtil;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.common.ui.swt.CountryCombo;
import org.remus.infomngmnt.common.ui.swt.CountryCombo.Country;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CountryComboObservable extends AbstractObservableValue {

	private final CountryCombo combo;

	private String oldValue;

	private String newValue;

	private ISelectionChangedListener selectionListener;

	public CountryComboObservable(final CountryCombo combo) {
		this.combo = combo;
		combo.getCombo().addSelectionChangedListener(
				this.selectionListener = new ISelectionChangedListener() {
					public void selectionChanged(final SelectionChangedEvent event) {
						if (event.getSelection().isEmpty()) {
							CountryComboObservable.this.newValue = null;
						} else {
							Country firstElement = (Country) ((IStructuredSelection) event
									.getSelection()).getFirstElement();
							CountryComboObservable.this.newValue = firstElement.getIso();
						}
						setImage(CountryComboObservable.this.newValue);
						fireValueChange(new ValueDiff() {
							@Override
							public Object getNewValue() {
								return CountryComboObservable.this.newValue;
							}

							@Override
							public Object getOldValue() {
								return CountryComboObservable.this.oldValue;
							}
						});
						CountryComboObservable.this.oldValue = CountryComboObservable.this.newValue;
					}

				});
	}

	@Override
	public synchronized void dispose() {
		this.combo.getCombo().removeSelectionChangedListener(this.selectionListener);
		super.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.databinding.observable.value.AbstractObservableValue
	 * #doGetValue()
	 */
	@Override
	protected Object doGetValue() {
		ISelection selection = this.combo.getCombo().getSelection();
		if (selection.isEmpty()) {
			return null;
		} else {
			return ((Country) ((IStructuredSelection) selection).getFirstElement()).getIso();
		}
	}

	@Override
	protected void doSetValue(final Object value) {
		List<Country> input = CountryCombo.getAllCountries();
		boolean foundCountry = false;
		for (Country country : input) {
			if (country.getIso().equals(value)) {
				this.combo.getCombo().setSelection(new StructuredSelection(country));
				foundCountry = true;
				break;
			}
		}
		if (!foundCountry) {
			this.combo.getCombo().setSelection(StructuredSelection.EMPTY);
			if (this.combo.isShowFlag()) {
				setImage(null);
			}
		}
		if (this.combo.isShowFlag() && foundCountry) {
			setImage(value.toString());
		}
	}

	public void setImage(final String iso) {
		if (iso != null) {
			Image pluginImage = ResourceManager.getPluginImage(Activator.getDefault(), FlagUtil
					.getPathByIsoCode(iso));
			this.combo.updateImage(pluginImage);
		}
	}

	public Object getValueType() {
		return String.class;
	}

}
