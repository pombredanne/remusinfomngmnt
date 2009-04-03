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

package org.remus.infomngmnt.link.ui;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.databinding.EObjectObservableValue;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.link.LinkActivator;
import org.remus.infomngmnt.link.webshot.WebshotUtil;
import org.remus.infomngmnt.ui.newwizards.GeneralPage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class GeneralLinkPage extends GeneralPage {

	private Text urlText;
	private Button obTainTextFromHtml;
	private String tmpText;
	private final IRunnableWithProgress obtainJob = new IRunnableWithProgress() {
		public void run(final IProgressMonitor monitor) throws InvocationTargetException,
				InterruptedException {
			monitor.beginTask("Obtaining title", IProgressMonitor.UNKNOWN);
			Thread runThread = new Thread() {
				@Override
				public void run() {

					final String newTitle = WebshotUtil
							.obtainHtmlTitle(GeneralLinkPage.this.tmpText);
					getShell().getDisplay().asyncExec(new Runnable() {
						public void run() {
							GeneralLinkPage.this.nameText.setText(newTitle);
						}
					});
				}
			};
			runThread.start();
			while (runThread.isAlive() && !monitor.isCanceled()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
			}
		}
	};

	public GeneralLinkPage(final InformationUnitListItem selection) {
		super(selection);
	}

	public GeneralLinkPage(final Category category) {
		super(category);
	}

	@Override
	public void createControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout());

		setTitle("New Link");
		setMessage("This wizard enables you to create a new link from a url.");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(LinkActivator.getDefault(),
				"icons/iconexperience/link_wizard_title.png"));

		doCreateParentElementGroup(container);
		Group group = new Group(container, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		group.setLayout(new GridLayout(3, false));
		group.setText("Name && URL");
		doCreateNameElements(group);
		this.obTainTextFromHtml = new Button(group, SWT.PUSH);
		this.obTainTextFromHtml.setText("Obtain title");
		this.obTainTextFromHtml.addListener(SWT.Selection, new Listener() {

			public void handleEvent(final Event event) {
				GeneralLinkPage.this.tmpText = GeneralLinkPage.this.urlText.getText();
				try {
					getContainer().run(true, true, GeneralLinkPage.this.obtainJob);
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});

		final Label nameLabel = new Label(group, SWT.NONE);
		nameLabel.setText("URL");
		this.urlText = new Text(group, SWT.BORDER);
		final GridData gd_nameText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_nameText.horizontalSpan = 2;
		this.urlText.setLayoutData(gd_nameText);

		doCreatePropertiesGroup(container);
		initDatabinding();
		presetValues();
		initValidation();
		setControl(container);
	}

	@Override
	protected void initDatabinding() {
		super.initDatabinding();
		ISWTObservableValue swtUrl = SWTObservables.observeText(this.urlText, SWT.Modify);
		IObservableValue emfUrl = EMFObservables.observeValue(this.unit,
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		emfUrl.addValueChangeListener(new IValueChangeListener() {

			public void handleValueChange(final ValueChangeEvent event) {
				Object value = ((EObjectObservableValue) event.getSource()).getValue();
				GeneralLinkPage.this.obTainTextFromHtml.setEnabled(value.toString().length() > 0);
			}

		});
		this.ctx.bindValue(swtUrl, emfUrl, null, null);

	}

}
