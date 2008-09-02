package org.remus.infomngmnt.ui.editors;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

public class ViewPage extends FormPage {

	/**
	 * Create the form page
	 * @param id
	 * @param title
	 */
	public ViewPage(String id, String title) {
		super(id, title);
	}

	/**
	 * Create the form page
	 * @param editor
	 * @param id
	 * @param title
	 */
	public ViewPage(FormEditor editor) {
		super(editor, "view","TEST");
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
		toolkit.paintBordersFor(body);
	}

}
