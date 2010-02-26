package org.remus.infomngmnt.plaintext.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

public class FormPage2 extends FormPage {

	private StyledText styledText;
	/**
	 * Create the form page
	 * @param id
	 * @param title
	 */
	public FormPage2(String id, String title) {
		super(id, title);
	}

	/**
	 * Create the form page
	 * @param editor
	 * @param id
	 * @param title
	 */
	public FormPage2(FormEditor editor, String id, String title) {
		super(editor, id, title);
	}

	/**
	 * Create contents of the form
	 * @param managedForm
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		form.setText("Empty FormPage");
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		this.styledText = new StyledText(body, SWT.BORDER);
		this.styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		toolkit.adapt(this.styledText, true, true);
	}

}
