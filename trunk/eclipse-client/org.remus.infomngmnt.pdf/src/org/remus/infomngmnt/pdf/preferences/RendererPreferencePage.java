package org.remus.infomngmnt.pdf.preferences;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import org.remus.infomngmnt.pdf.Activator;
import org.remus.infomngmnt.pdf.extension.IPdfImageRenderer;
import org.remus.infomngmnt.pdf.service.IPDF2ImageExtensionService;

public class RendererPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	private IPDF2ImageExtensionService service;
	private String[][] values;

	/**
	 * Create the preference page.
	 */
	public RendererPreferencePage() {
		super(FLAT);
	}

	/**
	 * Create contents of the preference page.
	 */
	@Override
	protected void createFieldEditors() {
		// Create the field editors
		addField(new ComboFieldEditor(PreferenceInitializer.DEFAULT_RENDERER, "PDF Image Renderer",
				this.values, getFieldEditorParent()));
	}

	/**
	 * Initialize the preference page.
	 */
	public void init(final IWorkbench workbench) {
		this.service = Activator.getDefault().getServiceTracker().getService(
				IPDF2ImageExtensionService.class);
		IPdfImageRenderer[] allRender = this.service.getAllRender();
		this.values = new String[allRender.length][2];
		for (int i = 0, n = allRender.length; i < n; i++) {
			this.values[i][0] = allRender[i].getName();
			this.values[i][1] = allRender[i].getId();
		}

	}

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return Activator.getDefault().getPreferenceStore();
	}

	@Override
	public void dispose() {
		Activator.getDefault().getServiceTracker().ungetService(this.service);
		super.dispose();
	}

	@Override
	protected void performApply() {

		super.performApply();
		Job job = new Job("Refreshing all Pdfs") {

			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				Set<? extends EObject> allItemsByType = InformationUtil
						.getAllItemsByType(Activator.TYPE_ID);
				monitor.beginTask("Rerendering all PDFs", allItemsByType.size());
				for (EObject eObject : allItemsByType) {
					if (eObject instanceof InformationUnitListItem) {
						InformationUnit adapter = (InformationUnit) ((InformationUnitListItem) eObject)
								.getAdapter(InformationUnit.class);
						IFile file = (IFile) adapter.getAdapter(IFile.class);
						try {
							file.touch(monitor);
						} catch (CoreException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						monitor.worked(1);
					}
				}

				return Status.OK_STATUS;
			}

		};
		job.setUser(true);
		job.schedule();
	}

}
