package org.remus.infomngmnt.ui.editors;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.core.streams.StreamCloser;
import org.remus.infomngmnt.common.core.streams.StreamUtil;
import org.remus.infomngmnt.jslib.JavaScriptSnippets;
import org.remus.infomngmnt.jslib.TemplateLocation;
import org.remus.infomngmnt.util.EditingUtil;

public class ViewPage extends InformationFormPage {

	private final InformationUnit infoUnit;

	private IFile binFile = null;

	private final PropertyChangeListener loadingMessageListener = new PropertyChangeListener() {

		public void propertyChange(final PropertyChangeEvent evt) {
			getSite().getShell().getDisplay().asyncExec(new Runnable() {
				public void run() {
					JavaScriptSnippets.setNewLoadingBarMessage(ViewPage.this.browser, (String) evt
							.getNewValue());

				}
			});
		}

	};

	private final IResourceChangeListener binFileListener = new IResourceChangeListener() {

		public void resourceChanged(final IResourceChangeEvent event) {
			if (event.getDelta() != null) {
				IResourceDelta[] affectedChildren = event.getDelta().getAffectedChildren();
				visit(affectedChildren);
			}
		}

		private void visit(final IResourceDelta[] affectedChildren) {
			for (IResourceDelta resourceDelta : affectedChildren) {
				if (resourceDelta.getResource().equals(ViewPage.this.binFile)) {
					getSite().getShell().getDisplay().asyncExec(new Runnable() {
						public void run() {
							if (!ViewPage.this.browser.isDisposed()) {
								if (ViewPage.this.binFile.exists()) {
									if (ViewPage.this.binFile.getLocationURI().getScheme() != null
											&& EditingUtil.FILEHISTORYKEEPINGSCHEME
													.equals(ViewPage.this.binFile.getLocationURI()
															.getScheme())) {
										ViewPage.this.browser
												.setUrl(EditorUtil
														.computeBinFileLocation((IFileEditorInput) getEditorInput()));
									} else {
										try {
											InputStream contents = ViewPage.this.binFile
													.getContents();
											ViewPage.this.browser.setText(StreamUtil
													.convertStreamToString(contents));
											StreamCloser.closeStreams(contents);
										} catch (CoreException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
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
	 * 
	 * @param editor
	 * @param id
	 * @param title
	 */
	public ViewPage(final FormEditor editor, final InformationUnit infoUnit) {
		super(editor, "view", "Viewer");
		this.infoUnit = infoUnit;
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) {
		super.init(site, input);
		this.binFile = EditorUtil.getBinFile((IFileEditorInput) getEditorInput());
	}

	/**
	 * Create contents of the form
	 * 
	 * @param managedForm
	 */
	@Override
	protected void createFormContent(final IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		this.browser = new Browser(form.getBody(), SWT.NONE);
		this.browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		toolkit.paintBordersFor(this.browser);

		if (this.binFile.exists()) {
			if (this.binFile.getLocationURI().getScheme() != null
					&& EditingUtil.FILEHISTORYKEEPINGSCHEME.equals(this.binFile.getLocationURI()
							.getScheme())) {
				this.browser.setUrl(EditorUtil
						.computeBinFileLocation((IFileEditorInput) getEditorInput()));
			} else {
				try {
					InputStream contents = this.binFile.getContents();
					this.browser.setText(StreamUtil.convertStreamToString(contents));
					StreamCloser.closeStreams(contents);
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			this.browser.setUrl(TemplateLocation.getLoadingUrl());
		}
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this.binFileListener);
		LoadingBarMessageProvider.getInstance().addPropertyChangeListener(getInfoId(),
				this.loadingMessageListener);
		new OpenFileFunction(this.browser, "openFile");
	}

	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this.binFileListener);
		LoadingBarMessageProvider.getInstance().removePropertyChangeListener(getInfoId(),
				this.loadingMessageListener);
		super.dispose();
	}

	private String getInfoId() {
		return ((IFileEditorInput) getEditorInput()).getFile().getProjectRelativePath()
				.removeFileExtension().lastSegment();
	}

	private class OpenFileFunction extends BrowserFunction {

		public OpenFileFunction(final Browser browser, final String name) {
			super(browser, name);
		}

		@Override
		public Object function(final Object[] arguments) {
			if (arguments.length == 1) {
				String string = arguments[0].toString();
				Program.launch(string);
			}
			return null;
		}

	}

}
