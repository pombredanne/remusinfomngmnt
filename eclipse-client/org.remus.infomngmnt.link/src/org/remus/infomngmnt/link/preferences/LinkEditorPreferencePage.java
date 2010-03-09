package org.remus.infomngmnt.link.preferences;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import org.remus.infomngmnt.BinaryReference;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.common.core.util.ResourceUtil;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.commands.DeleteBinaryReferenceCommand;
import org.remus.infomngmnt.core.edit.DisposableEditingDomain;
import org.remus.infomngmnt.core.services.IEditingHandler;
import org.remus.infomngmnt.link.LinkActivator;
import org.remus.infomngmnt.link.webshot.WebshotUtil;
import org.remus.infomngmnt.util.InformationUtil;

public class LinkEditorPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	private Group createGroup;
	private Group editingGroup;

	/**
	 * Create the preference page
	 */
	public LinkEditorPreferencePage() {
		super();
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return LinkActivator.getDefault().getPreferenceStore();
	}

	/**
	 * Initialize the preference page
	 */
	public void init(final IWorkbench workbench) {

	}

	@Override
	protected Control createContents(final Composite parent) {
		Composite fieldEditorParent = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		fieldEditorParent.setLayout(layout);
		fieldEditorParent.setFont(parent.getFont());

		this.createGroup = new Group(fieldEditorParent, SWT.NONE);
		this.createGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		this.createGroup.setText("Creation");

		this.editingGroup = new Group(fieldEditorParent, SWT.NONE);
		this.editingGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		this.editingGroup.setText("Edit && View");

		createFieldEditors();

		Button button = new Button(this.createGroup, SWT.PUSH);
		button.setText("Refresh webshots of all links");
		button.setLayoutData(new GridData(SWT.END, SWT.TOP, true, false, 2, 1));
		button.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				Job job = new Job("Refreshing all webshots") {

					@Override
					protected IStatus run(final IProgressMonitor monitor) {
						IEditingHandler service = LinkActivator.getDefault().getServiceTracker()
								.getService(IEditingHandler.class);
						DisposableEditingDomain editingDomain = service.createNewEditingDomain();
						Set<? extends EObject> allItemsByType = InformationUtil
								.getAllItemsByType(LinkActivator.LINK_INFO_ID);
						monitor.beginTask("Refreshing webshots", allItemsByType.size());
						for (EObject eObject : allItemsByType) {
							if (eObject instanceof InformationUnitListItem) {
								InformationUnit adapter = (InformationUnit) ((InformationUnitListItem) eObject)
										.getAdapter(InformationUnit.class);
								monitor.subTask(NLS.bind("Refreshing \"{0}\"", adapter.getLabel()));
								if (adapter != null) {
									IFile tmpFile = ResourceUtil.createTempFile("png");
									WebshotUtil.performWebShot(adapter.getStringValue(), tmpFile
											.getLocation().toOSString());
									BinaryReference binaryReferences = adapter
											.getBinaryReferences();
									CompoundCommand cc = new CompoundCommand();
									if (binaryReferences != null) {
										cc.append(new DeleteBinaryReferenceCommand(
												binaryReferences, editingDomain));
									}
									cc.append(CommandFactory.addFileToInfoUnit(tmpFile, adapter,
											editingDomain));
									editingDomain.getCommandStack().execute(cc);
									service.saveObjectToResource(adapter);
								}
								monitor.worked(1);
							}
						}
						editingDomain.getCommandStack().flush();
						editingDomain.dispose();
						LinkActivator.getDefault().getServiceTracker().ungetService(service);
						return Status.OK_STATUS;
					}

				};
				job.setUser(true);
				job.schedule();
			}

		});
		// adjustGridLayout();
		initialize();
		checkState();
		GridLayout gridLayout = new GridLayout(2, false);
		this.createGroup.setLayout(gridLayout);
		this.editingGroup.setLayout(new GridLayout(1, false));
		return fieldEditorParent;
	}

	@Override
	protected void createFieldEditors() {
		BooleanFieldEditor fieldEditor = new BooleanFieldEditor(
				LinkPreferenceInitializer.INDEX_DOCUMENT, "Index html webpages by default",
				this.createGroup);
		fieldEditor.fillIntoGrid(this.createGroup, 2);
		addField(fieldEditor);
		BooleanFieldEditor fieldEditor2 = new BooleanFieldEditor(
				LinkPreferenceInitializer.MAKE_SCREENSHOT, "Make Webshot by default",
				this.createGroup);
		fieldEditor2.fillIntoGrid(this.createGroup, 2);
		addField(fieldEditor2);
		addField(new StringFieldEditor(LinkPreferenceInitializer.SCREENSHOT_CMD,
				"Path to Webshot:", this.createGroup));
		addField(new BooleanFieldEditor("", "Show Webshot", this.editingGroup));
	}

}
