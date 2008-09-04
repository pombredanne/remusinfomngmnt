package org.remus.infomngmnt.ui.editors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.remus.infomngmnt.InformationUnit;

public class ViewPage extends FormPage {

	private final InformationUnit infoUnit;


	/**
	 * Create the form page
	 * @param editor
	 * @param id
	 * @param title
	 */
	public ViewPage(FormEditor editor, InformationUnit infoUnit) {
		super(editor, "view","TEST");
		this.infoUnit = infoUnit;
	}

	/**
	 * Create contents of the form
	 * @param managedForm
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		final Section section_1 = toolkit.createSection(body, ExpandableComposite.TITLE_BAR);
		section_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		section_1.setText("Data");

		Browser browser = new Browser(section_1, SWT.NONE);
		toolkit.paintBordersFor(browser);
		section_1.setClient(browser);

		final Section section = toolkit.createSection(body, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		section.setText("Actions");

		final Composite composite = toolkit.createComposite(section, SWT.NONE);
		toolkit.paintBordersFor(composite);
		section.setClient(composite);


		browser.setUrl(EditorUtil.computeBinFileLocation((IFileEditorInput) getEditorInput()));

	}



}
