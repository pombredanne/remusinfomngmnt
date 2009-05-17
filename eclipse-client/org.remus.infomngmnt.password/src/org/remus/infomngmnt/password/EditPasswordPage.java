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
package org.remus.infomngmnt.password;

import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.common.ui.databinding.BindingWidgetFactory;
import org.remus.infomngmnt.common.ui.databinding.TextBindingWidget;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.password.generator.PasswordGenerationDialog;
import org.remus.infomngmnt.ui.extension.AbstractInformationFormPage;

/**
 * @author Jan Hartwig <jhartwig@feb-radebeul.de>
 * 
 */
public class EditPasswordPage extends AbstractInformationFormPage {

	public EditPasswordPage() {
		// Asuto-generated constructor stub
	}

	// private Text styledText;
	private Text textUrl;
	private Text textUsername;
	private Text textPassword;
	private Text textPasswordDecrypted;

	@Override
	protected void renderPage(final IManagedForm managedForm) {

		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		doCreateGeneralSection(body, toolkit);
		doCreateAdditionalSection(body, toolkit);
		doCreateSemanticSection(body, toolkit);

		addControl(this.textUrl);
		addControl(this.textUsername);

	}

	private void doCreateAdditionalSection(final Composite body, final FormToolkit toolkit) {

		final Section section_1 = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		section_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		section_1.setText("Additional");

		final Composite compositeAdditional = toolkit.createComposite(section_1, SWT.NONE);
		final GridLayout gridLayoutAdditional = new GridLayout();
		gridLayoutAdditional.numColumns = 2;
		compositeAdditional.setLayout(gridLayoutAdditional);
		toolkit.paintBordersFor(compositeAdditional);
		section_1.setClient(compositeAdditional);

		// Url
		toolkit.createLabel(compositeAdditional, "Url:", SWT.NONE);

		this.textUrl = toolkit.createText(compositeAdditional, null, SWT.NONE);
		this.textUrl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		new Label(compositeAdditional, SWT.NONE);

		Hyperlink hyperlink = toolkit.createHyperlink(compositeAdditional,
				"Open Url in System-Browser", SWT.NONE);
		hyperlink.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				Program.launch(EditPasswordPage.this.textUrl.getText());
			}
		});
	}

	private void doCreateGeneralSection(final Composite body, final FormToolkit toolkit) {
		final Section generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.CENTER, true, false);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText("General");

		final Composite composite = toolkit.createComposite(generalSection, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		composite.setLayout(gridLayout);
		toolkit.paintBordersFor(composite);
		generalSection.setClient(composite);

		// Username
		toolkit.createLabel(composite, "Username:", SWT.NONE);

		this.textUsername = toolkit.createText(composite, null, SWT.NONE);
		this.textUsername.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		// Password
		toolkit.createLabel(composite, "Password:", SWT.NONE);

		final Composite compositePassword = toolkit.createComposite(composite, SWT.NONE);
		compositePassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		final StackLayout layout = new StackLayout();
		compositePassword.setLayout(layout);

		this.textPassword = toolkit.createText(compositePassword, null, SWT.NONE);

		this.textPasswordDecrypted = toolkit.createText(compositePassword, null, SWT.PASSWORD);

		layout.topControl = this.textPasswordDecrypted;

		toolkit.paintBordersFor(compositePassword);

		// create the button that will switch between the pages
		final Button pageButton = toolkit.createButton(composite, "*****", SWT.TOGGLE);
		pageButton.setSelection(true);
		pageButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				layout.topControl = EditPasswordPage.this.textPassword.isVisible() ? EditPasswordPage.this.textPasswordDecrypted
						: EditPasswordPage.this.textPassword;
				compositePassword.layout();
			}
		});
		new Label(composite, SWT.NONE);

		// create the button that will open an password generation dialog
		final Button generatePasswordButton = toolkit.createButton(composite, "Ge&nerate Password",
				SWT.NONE);
		generatePasswordButton
				.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1));
		generatePasswordButton.addListener(SWT.Selection, new Listener() {

			public void handleEvent(final Event event) {
				PasswordGenerationDialog dialog = new PasswordGenerationDialog(
						getSite().getShell(), getModelObject());
				if (dialog.open() == IDialogConstants.OK_ID) {
					SetCommand command = new SetCommand(EditPasswordPage.this.editingDomain,
							getModelObject(),
							InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE, dialog
									.getSelectedPassword());
					EditPasswordPage.this.editingDomain.getCommandStack().execute(command);
				}
			}
		});
	}

	@Override
	public void bindValuesToUi() {
		super.bindValuesToUi();
		TextBindingWidget createTextBindingWidget = BindingWidgetFactory.createTextBindingWidget(
				this.textUsername, this);
		createTextBindingWidget.bindModel(InformationUtil.getChildByType(getModelObject(),
				PasswordPlugin.NODE_USERNAME),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

		createTextBindingWidget = BindingWidgetFactory.createTextBindingWidget(this.textUrl, this);
		createTextBindingWidget
				.bindModel(InformationUtil
						.getChildByType(getModelObject(), PasswordPlugin.NODE_URL),
						InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

		createTextBindingWidget = BindingWidgetFactory.createTextBindingWidget(this.textPassword,
				this);
		createTextBindingWidget.bindModel(getModelObject(),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		createTextBindingWidget = BindingWidgetFactory.createTextBindingWidget(
				this.textPasswordDecrypted, this);
		createTextBindingWidget.bindModel(getModelObject(),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
	}
}
