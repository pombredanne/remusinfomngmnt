package org.remus.infomngmnt.plaintext.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.common.ui.databinding.BindingWidgetFactory;
import org.remus.infomngmnt.common.ui.databinding.StyledTextBindingWidget;
import org.remus.infomngmnt.common.ui.jface.AnnotatingQuickFixTextBox;
import org.remus.infomngmnt.ui.extension.AbstractInformationFormPage;

public class FormPage extends AbstractInformationFormPage {

	private AnnotatingQuickFixTextBox richtext;

	@Override
	protected void renderPage(final IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		final Section generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.EXPANDED);
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.FILL, true, true);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText("General");

		final Composite client = toolkit.createComposite(generalSection, SWT.NONE);
		client.setLayout(new GridLayout());
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);

		client.setLayoutData(gridData);

		generalSection.setClient(client);

		this.richtext = new AnnotatingQuickFixTextBox(client, "", "");

		addControl(this.richtext.getFTextField());
		doCreateSemanticSection(body, toolkit);
		form.reflow(true);

	}

	@Override
	public void bindValuesToUi() {
		StyledTextBindingWidget textBindingWidget = BindingWidgetFactory.createStyledText(
				this.richtext.getFTextField(), this);
		textBindingWidget.bindModel(getModelObject(),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		super.bindValuesToUi();
	}

}
