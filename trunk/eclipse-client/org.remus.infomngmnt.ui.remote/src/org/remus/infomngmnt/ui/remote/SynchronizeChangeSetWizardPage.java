package org.remus.infomngmnt.ui.remote;

import java.util.Collection;

import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.util.AdapterUtils;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;

import org.remus.infomngmnt.ChangeSetItem;
import org.remus.infomngmnt.SynchronizationAction;
import org.remus.infomngmnt.common.ui.image.ResourceManager;

public class SynchronizeChangeSetWizardPage extends WizardPage implements IMenuListener {

	private Tree tree;
	private final DiffModel diffModel;
	private TreeViewer treeViewer;
	private DiffLabelProvider diffLabelProvider;
	private final ChangeSetItem changeSet;

	public SynchronizeChangeSetWizardPage(final DiffModel diffModel2, final ChangeSetItem item) {
		super("wizardPage");
		this.diffModel = diffModel2;
		this.changeSet = item;
		setTitle("Synchronization of selected elements");
		setDescription("This page shows you a changeset of your local data and the repository data.");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(RemoteUiActivator.getDefault()
				.getBundle().getBundleContext(),
				"icons/iconexperience/wizards/synchronization_wizard.png"));
	}

	/**
	 * Create contents of the wizard
	 * 
	 * @param parent
	 */
	public void createControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		container.setLayout(gridLayout);
		//

		final Label changesetLabel = new Label(container, SWT.NONE);
		final GridData gd_changesetLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1);
		changesetLabel.setLayoutData(gd_changesetLabel);
		changesetLabel.setText("Change-Set");

		this.treeViewer = new TreeViewer(container, SWT.BORDER);

		this.tree = this.treeViewer.getTree();

		this.tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));

		// this.treeViewer.setLabelProvider(new
		// AdapterFactoryLabelProvider(AdapterUtils
		// .getAdapterFactory()));
		// this.treeViewer.setContentProvider(new
		// AdapterFactoryContentProvider(AdapterUtils
		// .getAdapterFactory()));

		this.diffLabelProvider = new DiffLabelProvider();
		this.diffLabelProvider.setChangeSet(this.changeSet);
		this.treeViewer.setLabelProvider(this.diffLabelProvider);

		this.treeViewer
				.setContentProvider(new DiffContentProvider(AdapterUtils.getAdapterFactory()));

		this.treeViewer.setInput(this.diffModel);
		MenuManager contextMenu = new MenuManager("#PopUpMenu");
		contextMenu.setRemoveAllWhenShown(true);
		SyncActionsContextMenu syncActionsContextMenu = new SyncActionsContextMenu(this);
		syncActionsContextMenu.setChangeSet(this.changeSet);
		contextMenu.addMenuListener(syncActionsContextMenu);
		final Menu menu = contextMenu.createContextMenu(this.treeViewer.getControl());
		this.treeViewer.getControl().setMenu(menu);

		this.treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				System.out.println(event.getSelection());

			}
		});

		setControl(container);
		checkConflicts();
	}

	void checkConflicts() {
		Collection<SynchronizationAction> values = this.changeSet.getSyncObjectActionMap().values();
		if (values.contains(SynchronizationAction.RESOLVE_CONFLICT)) {
			setErrorMessage("Please resolve the conflicts manually");
			setPageComplete(false);
		} else {
			setErrorMessage(null);
			setPageComplete(true);
		}

	}

	protected void validatePage() {
		// TODO Auto-generated method stub

	}

	public void menuAboutToShow(final IMenuManager manager) {
		// TODO Auto-generated method stub

	}

	public Viewer getViewer() {
		return this.treeViewer;
	}

}
