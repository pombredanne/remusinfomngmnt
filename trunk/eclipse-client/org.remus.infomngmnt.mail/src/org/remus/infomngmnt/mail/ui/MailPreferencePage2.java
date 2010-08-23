package org.remus.infomngmnt.mail.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FontFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.remus.resources.util.ResourceUtil;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import org.remus.infomngmnt.mail.MailActivator;
import org.remus.infomngmnt.mail.preferences.MailPreferenceInitializer;

public class MailPreferencePage2 extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	/**
	 * Create the preference page.
	 */
	public MailPreferencePage2() {
		super(FLAT);
	}

	/**
	 * Create contents of the preference page.
	 */
	@Override
	protected void createFieldEditors() {
		// Create the field editors
		addField(new FontFieldEditor(MailPreferenceInitializer.BODY_FONT, "Body Font", "Body",
				getFieldEditorParent()));
		addField(new FontFieldEditor(MailPreferenceInitializer.SUB_HEADER_FONT, "Subheader Font",
				"Subheader", getFieldEditorParent()));
		addField(new FontFieldEditor(MailPreferenceInitializer.HEADER_FONT, "Header Font",
				"Header", getFieldEditorParent()));
	}

	@Override
	public boolean performOk() {
		new Job("Rebuild") {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				IProject[] relevantProjects = ResourceUtil.getRelevantProjects();
				IStatus returnValue = Status.OK_STATUS;
				for (IProject iProject : relevantProjects) {
					try {
						iProject.build(IncrementalProjectBuilder.CLEAN_BUILD, monitor);
					} catch (CoreException e) {
						returnValue = e.getStatus();
					}
				}
				return returnValue;
			}

		}.schedule();

		return super.performOk();
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return MailActivator.getDefault().getPreferenceStore();
	}

	/**
	 * Initialize the preference page.
	 */
	public void init(final IWorkbench workbench) {
		// Initialize the preference page
	}

}
