package org.remus.infomngmnt.ui.remote;

import java.util.Collections;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.diff.service.DiffService;
import org.eclipse.emf.compare.match.metamodel.MatchModel;
import org.eclipse.emf.compare.match.service.MatchService;
import org.eclipse.emf.compare.util.AdapterUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.ui.UIPlugin;

public class SynchronizeChangeSetWizardPage extends WizardPage implements DiffWizard {

	private Tree tree;
	private DiffModel diffModel;
	private ChangeSet changeSet;
	private TreeViewer treeViewer;

	/**
	 * Create the wizard
	 * @param changeSet2 
	 */
	public SynchronizeChangeSetWizardPage(final ChangeSet changeSet) {
		super("wizardPage");
		this.changeSet = changeSet;
		setTitle("Wizard Page title");
		setDescription("Wizard Page description");
	}

	/**
	 * Create contents of the wizard
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
		
		this.treeViewer.setLabelProvider(new AdapterFactoryLabelProvider(AdapterUtils.getAdapterFactory()));
		this.treeViewer.setContentProvider(new AdapterFactoryContentProvider(AdapterUtils.getAdapterFactory()));
		
		initDiff();
		this.treeViewer.setInput(this.diffModel);
		this.treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				System.out.println(event.getSelection());
				
			}
		});
		
		setControl(container);
	}

	protected void initDiff() {
		
		/*
		 * We have to unset all local ids
		 * and replace them with the remote-hash.
		 */
		Category remoteCopy = (Category) EcoreUtil.copy(this.changeSet.getChangeSetItems().get(0).getRemoteConvertedContainer());
//		TreeIterator<EObject> eAllContents = remoteCopy.eAllContents();
//		while (eAllContents.hasNext()) {
//			EObject eObject = eAllContents.next();
//			if (eObject instanceof InformationUnitListItem) {
//				((InformationUnitListItem) eObject).setId(((InformationUnitListItem) eObject).getSynchronizationMetaData().getHash());
//			} if (eObject instanceof Category) {
//				((Category) eObject).setId(((Category) eObject).getSynchronizationMetaData().getHash());
//			}
//		}

		Category copy = (Category) EcoreUtil.copy(this.changeSet.getTargetCategory());
		TreeIterator<EObject> eAllContents = copy.eAllContents();
		while (eAllContents.hasNext()) {
			EObject eObject = eAllContents.next();
			if (eObject instanceof InformationUnitListItem) {
				/*
				 * Additionally we have to unset the workspace-path
				 */
				eObject.eUnset(InfomngmntPackage.Literals.INFORMATION_UNIT_LIST_ITEM__WORKSPACE_PATH);
			} 
		}
//		remoteCopy.setId(remoteCopy.getSynchronizationMetaData().getHash());
//		copy.setId(copy.getSynchronizationMetaData().getHash());
//		
		EditingUtil.getInstance().saveObjectToResource(
				remoteCopy, 
				UIPlugin.getDefault().getStateLocation().append("compare").append("remote.xml").toOSString());
		
		EditingUtil.getInstance().saveObjectToResource(
				copy, 
				UIPlugin.getDefault().getStateLocation().append("compare").append("local.xml").toOSString());
		
		MatchModel match;
		try {
			match = MatchService.doMatch(remoteCopy
					,copy , Collections.<String, Object> emptyMap());
			
			this.diffModel = DiffService.doDiff(match, false);
			this.treeViewer.setInput(this.diffModel);
			//performDiff(ownedElements, changeSetItem);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Computing differences
		
		
	}

	protected void validatePage() {
		// TODO Auto-generated method stub
		
	}

	public void setChangeSet(final ChangeSet changeSet) {
		this.changeSet = changeSet;
	}

	public DiffModel getDiffModel() {
		return this.diffModel;
	}

}
