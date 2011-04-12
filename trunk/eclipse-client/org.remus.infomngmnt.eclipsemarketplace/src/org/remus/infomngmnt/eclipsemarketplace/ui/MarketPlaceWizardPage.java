/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.eclipsemarketplace.ui;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.remus.InfomngmntPackage;
import org.eclipse.remus.RemoteRepository;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.remus.infomngmnt.eclipsemarketplace.connector.Messages;


public class MarketPlaceWizardPage extends WizardPage {

	private Text nameText;
	private Text apiUrlText;
	private RemoteRepository repository;

	/**
	 * Create the wizard
	 */
	public MarketPlaceWizardPage() {
		super("wizardPage"); //$NON-NLS-1$
		setTitle(Messages.MarketPlaceWizardPage_WizardTitle);
		setDescription(Messages.MarketPlaceWizardPage_WizardSubtitle);
	}

	/**
	 * Create contents of the wizard
	 * 
	 * @param parent
	 */
	public void createControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout());
		//

		final Group group = new Group(container, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		group.setLayout(gridLayout);

		final Label nameLabel = new Label(group, SWT.NONE);
		nameLabel.setText(Messages.MarketPlaceWizardPage_Name);

		this.nameText = new Text(group, SWT.BORDER);
		this.nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Label apiurlLabel = new Label(group, SWT.NONE);
		apiurlLabel.setText(Messages.MarketPlaceWizardPage_APIUrl);

		this.apiUrlText = new Text(group, SWT.BORDER);
		final GridData gd_apiUrlText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.apiUrlText.setLayoutData(gd_apiUrlText);
		this.apiUrlText.setEditable(false);

		bindValuesToUi();
		setControl(container);
	}

	public void setRemoteObject(final RemoteRepository repository) {
		this.repository = repository;

	}

	public void bindValuesToUi() {
		EMFDataBindingContext ectx = new EMFDataBindingContext();

		ISWTObservableValue swtName = SWTObservables.observeText(this.nameText, SWT.Modify);
		IObservableValue emfName = EMFObservables.observeValue(this.repository,
				InfomngmntPackage.Literals.REMOTE_OBJECT__NAME);
		ectx.bindValue(swtName, emfName, null, null);
		this.apiUrlText.setText(this.repository.getUrl());
		this.nameText.setText(Messages.MarketPlaceWizardPage_EclipseMarketplace);

	}
}
