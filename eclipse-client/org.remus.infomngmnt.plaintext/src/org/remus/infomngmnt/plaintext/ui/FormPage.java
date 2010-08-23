package org.remus.infomngmnt.plaintext.ui;

import org.eclipse.remus.common.ui.jface.AnnotatingQuickFixTextBox;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.ui.databinding.BindingWidgetFactory;
import org.eclipse.remus.ui.databinding.StyledTextBindingWidget;
import org.eclipse.remus.ui.editors.editpage.AbstractInformationFormPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;


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
		InformationStructureRead read = InformationStructureRead.newSession(getModelObject());
		StyledTextBindingWidget textBindingWidget = BindingWidgetFactory.createStyledText(
				this.richtext.getFTextField(), this);
		textBindingWidget.bindModel(read.getChildByNodeId("contents"), read
				.getFeatureByNodeId("contents"));
		super.bindValuesToUi();
	}
}
