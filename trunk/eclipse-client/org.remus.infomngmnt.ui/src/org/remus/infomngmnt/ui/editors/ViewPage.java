package org.remus.infomngmnt.ui.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.jslib.TemplateLocation;

public class ViewPage extends FormPage {

	private final InformationUnit infoUnit;

	private IFile binFile = null;

	private final IResourceChangeListener binFileListener = new IResourceChangeListener() {

		public void resourceChanged(IResourceChangeEvent event) {
			IResourceDelta[] affectedChildren = event.getDelta().getAffectedChildren();
			visit(affectedChildren);
		}

		private void visit(IResourceDelta[] affectedChildren) {
			for (IResourceDelta resourceDelta : affectedChildren) {
				if (resourceDelta.getResource().equals(ViewPage.this.binFile)) {
					getSite().getShell().getDisplay().asyncExec(new Runnable() {
						public void run() {
							if (!ViewPage.this.browser.isDisposed())
								ViewPage.this.browser.setUrl(EditorUtil.computeBinFileLocation((IFileEditorInput) getEditorInput()));
						}

					});
					return;
				}
				visit(resourceDelta.getAffectedChildren());

			}
		}
	};

	Browser browser;
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

	@Override
	public void init(IEditorSite site, IEditorInput input) {
		super.init(site, input);
		this.binFile = EditorUtil.getBinFile((IFileEditorInput) getEditorInput());
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

		this.browser = new Browser(section_1, SWT.NONE);
		toolkit.paintBordersFor(this.browser);
		section_1.setClient(this.browser);

		final Section section = toolkit.createSection(body, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		section.setText("Actions");

		final Composite composite = toolkit.createComposite(section, SWT.NONE);
		toolkit.paintBordersFor(composite);
		section.setClient(composite);

		if (this.binFile.exists()) {
			this.browser.setUrl(EditorUtil.computeBinFileLocation((IFileEditorInput) getEditorInput()));
		} else {
			this.browser.setUrl(TemplateLocation.getLoadingUrl());
		}
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this.binFileListener);

	}

	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this.binFileListener);
		super.dispose();
	}



}
