package org.remus.infomngmnt.ui.views;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.core.model.EditingUtil;

public class NavigationView extends ViewPart {

	public static final String ID = "org.remus.infomngmnt.ui.views.NavigationView"; //$NON-NLS-1$
	private AdapterFactoryContentProvider contentProvider;
	private AdapterFactoryLabelProvider labelProvider;
	private TreeViewer viewer;

	/**
	 * Create contents of the view part
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

		//
		Tree tree = new Tree(parent, SWT.SINGLE | SWT.FULL_SELECTION);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		this.viewer = new TreeViewer(tree);


		initProvider();
		initInput();
		createActions();
		initializeToolBar();
		initializeMenu();
	}

	private void initInput() {
		this.viewer.setInput(ApplicationModelPool.getInstance().getModel());

	}

	private void initProvider() {
		this.contentProvider = new AdapterFactoryContentProvider(EditingUtil.getInstance().getAdapterFactory());
		this.labelProvider = new AdapterFactoryLabelProvider(EditingUtil.getInstance().getAdapterFactory());
		this.viewer.setContentProvider(this.contentProvider);
		this.viewer.setLabelProvider(this.labelProvider);

	}

	/**
	 * Create the actions
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars()
		.getToolBarManager();
	}

	/**
	 * Initialize the menu
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars()
		.getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

}
