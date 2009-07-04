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
package org.remus.infomngmnt.contact.ui.general;

/**
 * @author Jan Hartwig <jhartwig@feb-radebeul.de>
 * 
 */
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.ui.databinding.BindingWidgetFactory;
import org.remus.infomngmnt.common.ui.databinding.CountryComboObservable;
import org.remus.infomngmnt.common.ui.databinding.IEMFEditBindingProvider;
import org.remus.infomngmnt.common.ui.databinding.TextBindingWidget;
import org.remus.infomngmnt.common.ui.jface.BindingStatusDialog;
import org.remus.infomngmnt.common.ui.swt.CountryCombo;
import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.core.services.IGeoData;
import org.remus.infomngmnt.ui.UIPlugin;

public class EditContactAddressDialog extends BindingStatusDialog {

	private final InformationUnit adress;
	private final IEMFEditBindingProvider provider;

	private Text tx_Street;
	private Text tx_Pob;
	private Text tx_Locality;
	private Text tx_Region;
	private Text tx_Postal;
	private Text tx_Latitude;
	private Text tx_Longitude;
	private Button getCoordinatesFromButton;
	private CountryCombo countryCombo;

	public EditContactAddressDialog(final Shell parentShell, final InformationUnit adress,
			final IEMFEditBindingProvider provider) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);
		this.adress = adress;
		this.provider = provider;
	}

	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.CLOSE_LABEL, true);
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite comp = new Composite((Composite) super.createDialogArea(parent), SWT.NONE);
		comp.setLayout(new GridLayout(2, false));
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		GridData gd_text_span_2 = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd_text_span_2.horizontalSpan = 2;
		GridData gd_text_fill_horizontal = new GridData(SWT.FILL, SWT.BEGINNING, true, false);

		final Label lb_Street = new Label(comp, SWT.NONE);
		lb_Street.setText("Street");
		this.tx_Street = new Text(comp, SWT.BORDER);
		this.tx_Street.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

		final Label lb_Pob = new Label(comp, SWT.NONE);
		lb_Pob.setText("Post Office Box");
		this.tx_Pob = new Text(comp, SWT.BORDER);
		this.tx_Pob.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

		final Label lb_Locality = new Label(comp, SWT.NONE);
		lb_Locality.setText("Locality");
		this.tx_Locality = new Text(comp, SWT.BORDER);
		this.tx_Locality.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

		final Label lb_Region = new Label(comp, SWT.NONE);
		lb_Region.setText("Region");
		this.tx_Region = new Text(comp, SWT.BORDER);
		this.tx_Region.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

		final Label lb_Postal = new Label(comp, SWT.NONE);
		lb_Postal.setText("Postal Code");
		this.tx_Postal = new Text(comp, SWT.BORDER);
		this.tx_Postal.setLayoutData(gd_text_fill_horizontal);

		final Label lb_Country = new Label(comp, SWT.NONE);
		lb_Country.setText("Country");
		this.countryCombo = new CountryCombo(comp, SWT.NONE, true);
		this.countryCombo.getCombo().getControl().setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Label label = new Label(comp, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));

		final Label longtitudeLabel = new Label(comp, SWT.NONE);
		longtitudeLabel.setText("Longtitude");

		this.tx_Longitude = new Text(comp, SWT.BORDER);
		this.tx_Longitude.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Label latitudeLabel = new Label(comp, SWT.NONE);
		latitudeLabel.setText("Latitude");

		this.tx_Latitude = new Text(comp, SWT.BORDER);
		this.tx_Latitude.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		new Label(comp, SWT.NONE);

		this.getCoordinatesFromButton = new Button(comp, SWT.NONE);
		final GridData gd_getCoordinatesFromButton = new GridData(SWT.RIGHT, SWT.CENTER, false,
				false);
		this.getCoordinatesFromButton.setLayoutData(gd_getCoordinatesFromButton);
		this.getCoordinatesFromButton.setText("Get Coordinates");

		createTextValueBindings();
		createListener();
		return comp;
	}

	private void createListener() {

		this.getCoordinatesFromButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				try {
					IGeoData geoData = UIPlugin.getDefault().getService(IGeoData.class);
					if (geoData == null) {
						return;
					}
					if (geoData.canRetreiveGeoData() != null) {
						showGMapsApiKeyErrorMessageBox();
						return;
					}

					Map<String, Object> options = new HashMap<String, Object>();
					options.put(IGeoData.KEY_LOCALITY, EditContactAddressDialog.this.tx_Locality
							.getText());
					options.put(IGeoData.KEY_POST_CODE, EditContactAddressDialog.this.tx_Postal
							.getText());
					options.put(IGeoData.KEY_REGION, EditContactAddressDialog.this.tx_Region
							.getText());
					options.put(IGeoData.KEY_STREET, EditContactAddressDialog.this.tx_Street
							.getText());
					options.put(IGeoData.KEY_POST_OFFICE_BOX, EditContactAddressDialog.this.tx_Pob
							.getText());
					Point2D p2d = geoData.getCoordinates(options);
					EditContactAddressDialog.this.tx_Longitude.setText(String.valueOf(p2d.getX()));
					EditContactAddressDialog.this.tx_Latitude.setText(String.valueOf(p2d.getY()));
				} catch (Exception e2) {
					showGMapsApiKeyErrorMessageBox();
				}
			}
		});

	}

	private void showGMapsApiKeyErrorMessageBox() {
		MessageDialog.openError(getShell(), "No or incorrect Maps Key.",
				"Please go to Extras -> Preferences -> GeoData -> Google Maps API Key");

	}

	private void createTextValueBindings() {
		TextBindingWidget bindingWidget = BindingWidgetFactory.createTextBindingWidget(
				this.tx_Street, this.provider);
		bindingWidget.bindModel(InformationUtil.getChildByType(this.adress,
				ContactActivator.NODE_NAME_ADRESS_STREET),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		addBinding(bindingWidget.getBinding());

		bindingWidget = BindingWidgetFactory.createTextBindingWidget(this.tx_Pob, this.provider);
		bindingWidget.bindModel(InformationUtil.getChildByType(this.adress,
				ContactActivator.NODE_NAME_ADRESS_POST_OFFICE_BOX),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		addBinding(bindingWidget.getBinding());

		bindingWidget = BindingWidgetFactory.createTextBindingWidget(this.tx_Locality,
				this.provider);
		bindingWidget.bindModel(InformationUtil.getChildByType(this.adress,
				ContactActivator.NODE_NAME_ADRESS_LOCALITY),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		addBinding(bindingWidget.getBinding());

		bindingWidget = BindingWidgetFactory.createTextBindingWidget(this.tx_Region, this.provider);
		bindingWidget.bindModel(InformationUtil.getChildByType(this.adress,
				ContactActivator.NODE_NAME_ADRESS_REGION),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		addBinding(bindingWidget.getBinding());

		bindingWidget = BindingWidgetFactory.createTextBindingWidget(this.tx_Postal, this.provider);
		bindingWidget.bindModel(InformationUtil.getChildByType(this.adress,
				ContactActivator.NODE_NAME_ADRESS_POSTAL),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		addBinding(bindingWidget.getBinding());

		bindingWidget = BindingWidgetFactory.createTextBindingWidget(this.tx_Longitude,
				this.provider);
		bindingWidget.bindModel(InformationUtil.getChildByType(this.adress,
				ContactActivator.NODE_NAME_ADRESS_LONGITUDE),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		addBinding(bindingWidget.getBinding());

		bindingWidget = BindingWidgetFactory.createTextBindingWidget(this.tx_Latitude,
				this.provider);
		bindingWidget.bindModel(InformationUtil.getChildByType(this.adress,
				ContactActivator.NODE_NAME_ADRESS_LATITUDE),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		addBinding(bindingWidget.getBinding());

		IObservableValue emfCountry = EMFEditObservables.observeValue(this.provider
				.getEditingDomain(), InformationUtil.getChildByType(this.adress,
				ContactActivator.NODE_NAME_ADRESS_COUNTRY),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		CountryComboObservable swtCountry = new CountryComboObservable(this.countryCombo);
		addBinding(this.provider.getDatabindingContext().bindValue(swtCountry, emfCountry, null,
				null));

	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Edit Adress");
	}
}
