package org.remus.infomngmnt.plaintext.ui;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.ui.extension.AbstractInformationFormPage;

public class FormPage extends AbstractInformationFormPage {

	private Text styledText;

	@Override
	protected void createFormContent(final IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		this.styledText = toolkit.createText(body,
				"", SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL); //$NON-NLS-1$
		this.styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		addDirtyOnTextModifyListener(this.styledText,
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		toolkit.adapt(this.styledText, true, true);

	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		// getModelObject().setStringValue(this.styledText.getText());
		setDirty(false);
		super.doSave(monitor);
	}

}
