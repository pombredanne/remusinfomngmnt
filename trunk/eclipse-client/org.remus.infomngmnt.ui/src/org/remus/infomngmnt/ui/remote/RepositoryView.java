package org.remus.infomngmnt.ui.remote;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;

public class RepositoryView extends ViewPart {

	private Tree tree;
	public static final String ID = "org.remus.infomngmnt.ui.remote.RepositoryView"; //$NON-NLS-1$
	private TreeViewer treeViewer;

	/**
	 * Create contents of the view part
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		final StackLayout stackLayout = new StackLayout();
		container.setLayout(stackLayout);

		this.treeViewer = new TreeViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		this.tree = this.treeViewer.getTree();

		Link eclipseorgLink;

		Composite composite;
		composite = new Composite(container, SWT.NONE);
		final FillLayout fillLayout = new FillLayout();
		fillLayout.marginWidth = 5;
		fillLayout.marginHeight = 5;
		composite.setLayout(fillLayout);
		eclipseorgLink = new Link(composite, SWT.NONE);
		eclipseorgLink.setText("No repositories created. You can <a>add</a> a new Remote Repository.");
		stackLayout.topControl = this.tree;
		//
		//		getSite().registerContextMenu(menuMgr, this.viewer);
		getSite().setSelectionProvider(this.treeViewer);


		createActions();
		initializeToolBar();
		initializeMenu();
	}

	private void initProvider() {
		// TODO Auto-generated method stub

	}


	private void initInput() {
		// TODO Auto-generated method stub

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
