package org.remus.infomngmnt.ui.editors;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.jslib.JavaScriptSnippets;
import org.remus.infomngmnt.jslib.TemplateLocation;

public class ViewPage extends FormPage {

	private final InformationUnit infoUnit;

	private IFile binFile = null;

	private final PropertyChangeListener loadingMessageListener = new PropertyChangeListener() {

		public void propertyChange(final PropertyChangeEvent evt) {
			getSite().getShell().getDisplay().asyncExec(new Runnable() {
				public void run() {
					JavaScriptSnippets.setNewLoadingBarMessage(
							ViewPage.this.browser, (String) evt.getNewValue());

				}
			});
		}

	};

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
							if (!ViewPage.this.browser.isDisposed()) {
								if (ViewPage.this.binFile.exists()) {
									ViewPage.this.browser.setUrl(EditorUtil.computeBinFileLocation((IFileEditorInput) getEditorInput()));
								} else {
									ViewPage.this.browser.setUrl(TemplateLocation.getLoadingUrl());
								}
							}
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

		this.browser = new Browser(form.getBody(), SWT.NONE);
		this.browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		toolkit.paintBordersFor(this.browser);

		if (this.binFile.exists()) {
			this.browser.setUrl(EditorUtil.computeBinFileLocation((IFileEditorInput) getEditorInput()));
		} else {
			this.browser.setUrl(TemplateLocation.getLoadingUrl());
		}
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this.binFileListener);
		LoadingBarMessageProvider.getInstance().addPropertyChangeListener(getInfoId(), this.loadingMessageListener);
	}

	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this.binFileListener);
		LoadingBarMessageProvider.getInstance().removePropertyChangeListener(
				getInfoId(), this.loadingMessageListener);
		super.dispose();
	}

	private String getInfoId() {
		return ((IFileEditorInput) getEditorInput()).getFile().getProjectRelativePath().removeFileExtension().lastSegment();
	}



}
