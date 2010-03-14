package org.remus.infomngmnt.ui.remote;

import java.util.Collections;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;

public class RepositoryView extends ViewPart {

	private Tree tree;
	public static final String ID = "org.remus.infomngmnt.ui.remote.RepositoryView"; //$NON-NLS-1$
	private TreeViewer treeViewer;

	/**
	 * Create contents of the view part
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		final StackLayout stackLayout = new StackLayout();
		container.setLayout(stackLayout);

		this.treeViewer = new TreeViewer(container, SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		this.tree = this.treeViewer.getTree();

		Link eclipseorgLink;

		Composite composite;
		composite = new Composite(container, SWT.NONE);
		final FillLayout fillLayout = new FillLayout();
		fillLayout.marginWidth = 5;
		fillLayout.marginHeight = 5;
		composite.setLayout(fillLayout);
		eclipseorgLink = new Link(composite, SWT.NONE);

		eclipseorgLink
				.setText("No repositories created. You can <a>add</a> a new Remote Repository.");
		eclipseorgLink.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				IHandlerService service = (IHandlerService) PlatformUI.getWorkbench().getService(
						IHandlerService.class);
				ICommandService commandService = (ICommandService) PlatformUI.getWorkbench()
						.getService(ICommandService.class);
				Command command = commandService.getCommand("Test");
				try {
					command.executeWithChecks(new ExecutionEvent(command, Collections.EMPTY_MAP,
							null, service.getCurrentState()));
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotDefinedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotEnabledException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotHandledException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		stackLayout.topControl = composite;

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
		IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
	}

	/**
	 * Initialize the menu
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

}
