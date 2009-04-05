package org.remus.infomngmnt.contact.ui;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.remus.infomngmnt.ui.extension.AbstractInformationFormPage;

public class EditMiscPage extends AbstractInformationFormPage {

	public EditMiscPage() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void createFormContent(final IManagedForm managedForm) {

		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		new EncryptionSectionUI(body, toolkit);
		new MiscellaneousSectionUI(body, toolkit);
		new UserDefinedSectionUI(body, toolkit, getSite().getShell(), getModelObject(), editingDomain);
		doCreateSemanticSection(body, toolkit);
	}
}
