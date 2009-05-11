package org.remus.infomngmnt.link.ui;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.ui.extension.AbstractInformationFormPage;

public class EditLinkPage extends AbstractInformationFormPage {

	private Text text;

	@Override
	protected void createFormContent(final IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		final Section generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.CENTER, true, false);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText("General");

		final Composite composite = toolkit.createComposite(generalSection, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		composite.setLayout(gridLayout);
		toolkit.paintBordersFor(composite);
		generalSection.setClient(composite);

		toolkit.createLabel(composite, "Url:", SWT.NONE);

		this.text = toolkit.createText(composite, null, SWT.NONE);
		this.text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		new Label(composite, SWT.NONE);

		toolkit.createHyperlink(composite, "Open Url in System-Browser", SWT.NONE);
		new Label(composite, SWT.NONE);

		final ImageHyperlink refreshWebshotImageHyperlink = toolkit.createImageHyperlink(composite,
				SWT.NONE);
		refreshWebshotImageHyperlink.setText("Refresh Webshot");
		new Label(composite, SWT.NONE);

		final ImageHyperlink refreshSearchableContentImageHyperlink = toolkit.createImageHyperlink(
				composite, SWT.NONE);
		refreshSearchableContentImageHyperlink.setText("Refresh searchable content");

		doCreateSemanticSection(body, toolkit);
		bindValuesToUi();

	}

	@Override
	protected void bindValuesToUi() {
		super.bindValuesToUi();
		ISWTObservableValue swtLink = SWTObservables.observeDelayedValue(500, SWTObservables
				.observeText(this.text, SWT.Modify));
		IObservableValue emfLink = EMFEditObservables.observeValue(Realm.getDefault(),
				this.editingDomain, getModelObject(),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		this.dataBindingContext.bindValue(swtLink, emfLink, null, null);
	}

}
