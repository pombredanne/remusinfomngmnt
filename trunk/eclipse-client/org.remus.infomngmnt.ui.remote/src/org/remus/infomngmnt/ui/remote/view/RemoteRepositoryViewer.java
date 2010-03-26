/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.ui.remote.view;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.provider.UnwrappingSelectionProvider;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PlatformUI;

import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.RepositoryCollection;
import org.remus.infomngmnt.core.edit.DisposableEditingDomain;
import org.remus.infomngmnt.core.remote.RemoteActivator;
import org.remus.infomngmnt.core.remote.services.IRepositoryService;
import org.remus.infomngmnt.ui.remote.NewRepositoryWizard;
import org.remus.infomngmnt.ui.remote.RemoteUiActivator;
import org.remus.infomngmnt.ui.remote.deferred.DeferredContentProvider;
import org.remus.infomngmnt.ui.remote.service.IRepositoryExtensionService;
import org.remus.infomngmnt.ui.remote.service.IRepositoryUI;
import org.remus.infomngmnt.ui.remote.view.action.AddRemoteRepositoryAction;
import org.remus.infomngmnt.ui.remote.view.action.CheckoutAction;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RemoteRepositoryViewer implements ISelectionProvider, IEditingDomainProvider,
		IViewerProvider {

	private TreeViewer viewer;
	private RemoteRepositoryContextMenu actionBar;
	private final DisposableEditingDomain editingDomain;
	private AddRemoteRepositoryAction addRepAction;
	private StackLayout stackLayout;
	private final AdapterImpl checkRepositoryCountAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification msg) {
			int eventType = msg.getEventType();
			switch (eventType) {
			case Notification.ADD:
			case Notification.ADD_MANY:
			case Notification.REMOVE:
			case Notification.REMOVE_MANY:
				checkRepositories();
				break;

			default:
				break;
			}
		};
	};
	private Composite descriptionText;
	private Composite mainComp;
	private RepositoryCollection repositories;
	private IViewSite parentContainer;

	public RemoteRepositoryViewer() {
		this.editingDomain = RemoteUiActivator.getDefault().getEditService()
				.createNewEditingDomain();
	}

	protected void checkRepositories() {
		if (this.repositories.getRepositories().size() == 0
				&& this.stackLayout.topControl != this.descriptionText) {
			this.stackLayout.topControl = this.descriptionText;
			this.mainComp.layout(true);
		} else if (this.repositories.getRepositories().size() != 0
				&& this.stackLayout.topControl != this.viewer.getControl()) {
			this.stackLayout.topControl = this.viewer.getControl();
			this.mainComp.layout(true);
		}
	}

	public void createControl(final Composite parent, final IViewSite parentContainer) {

		this.parentContainer = parentContainer;
		this.mainComp = new Composite(parent, SWT.NONE);
		this.mainComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.mainComp.setLayout(this.stackLayout = new StackLayout());

		this.descriptionText = new Composite(this.mainComp, SWT.NONE);
		this.descriptionText.setBackground(parentContainer.getShell().getDisplay().getSystemColor(
				SWT.COLOR_LIST_BACKGROUND));
		this.descriptionText.setLayout(new GridLayout());
		Link link = new Link(this.descriptionText, SWT.WRAP);
		link.setBackground(this.descriptionText.getBackground());
		link.setText("No repositories available. Click <a>here</a> to create a new repository.");
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.verticalIndent = 5;
		gridData.horizontalIndent = 5;
		link.setLayoutData(gridData);
		link.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				RemoteRepositoryViewer.this.addRepAction.run();
			}
		});

		Tree tree = new Tree(this.mainComp, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.viewer = new TreeViewer(tree);

		initProvider();
		initInput();
		initOpen();
		hookContextMenu();
		hookActions();
		checkRepositories();
	}

	private void hookActions() {
		this.addRepAction = new AddRemoteRepositoryAction();
		this.addRepAction.setViewSite(this.parentContainer);

	}

	private void initOpen() {
		this.viewer.addOpenListener(new IOpenListener() {
			public void open(final OpenEvent event) {
				Object object = ((IStructuredSelection) event.getSelection()).getFirstElement();
				if (object instanceof RemoteRepository) {
					IRepositoryUI itemByRepositoryId = RemoteUiActivator.getDefault()
							.getServiceTracker().getService(IRepositoryExtensionService.class)
							.getItemByRepositoryId(
									((RemoteRepository) object).getRepositoryTypeId());
					if (itemByRepositoryId != null) {
						NewRepositoryWizard wizardClass = itemByRepositoryId.getWizardClass();
						WizardDialog wizDialog = new WizardDialog(
								RemoteRepositoryViewer.this.parentContainer.getShell(), wizardClass);
						wizardClass.init(new StructuredSelection(object));
						wizDialog.open();
					}
				} else if (object instanceof RemoteContainer) {
					CheckoutAction checkoutAction = new CheckoutAction();
					checkoutAction.selectionChanged(new StructuredSelection(object));
					if (checkoutAction.isEnabled()) {
						checkoutAction.run();
					}
				}
			}

		});

	}

	private void initInput() {
		this.repositories = RemoteUiActivator.getDefault().getServiceTracker().getService(
				IRepositoryService.class).getRepositories();
		this.repositories.eAdapters().add(this.checkRepositoryCountAdapter);
		this.viewer.setInput(this.repositories);
	}

	private void initProvider() {
		IStructuredContentProvider contentProvider = new DeferredContentProvider(RemoteUiActivator
				.getDefault().getEditService().getAdapterFactory(), this.parentContainer);
		LabelProvider labelProvider = new DecoratingLabelProvider(new AdapterFactoryLabelProvider(
				RemoteUiActivator.getDefault().getEditService().getAdapterFactory()) {
			@Override
			public Image getImage(final Object object) {
				if (object instanceof RemoteRepository) {
					RemoteRepository obj = (RemoteRepository) object;
					IRepositoryExtensionService service = RemoteActivator.getDefault()
							.getServiceTracker().getService(IRepositoryExtensionService.class);
					IRepositoryUI itemByRepositoryId = service.getItemByRepositoryId(obj
							.getRepositoryTypeId());
					if (itemByRepositoryId != null) {
						return itemByRepositoryId.getImage();
					}
				} else if (object instanceof RemoteContainer) {

				}
				return super.getImage(object);
			}
		}, PlatformUI.getWorkbench().getDecoratorManager().getLabelDecorator());
		this.viewer.setContentProvider(contentProvider);
		this.viewer.setLabelProvider(labelProvider);
	}

	/**
	 * @param viewer2
	 */
	private void hookContextMenu() {
		final MenuManager contextMenu = new MenuManager("#PopUpMenu");
		contextMenu.setRemoveAllWhenShown(true);
		this.actionBar = new RemoteRepositoryContextMenu();
		this.actionBar.init(this.parentContainer.getActionBars());
		this.actionBar.setActiveDomain(this);
		this.actionBar.setViewSite(this.parentContainer);
		this.viewer.addSelectionChangedListener(this.actionBar);
		contextMenu.addMenuListener(this.actionBar);
		final Menu menu = contextMenu.createContextMenu(this.viewer.getControl());
		this.viewer.getControl().setMenu(menu);
		this.parentContainer.registerContextMenu(getId(), contextMenu,
				new UnwrappingSelectionProvider(this));

	}

	private String getId() {
		return this.parentContainer.getId();
	}

	public void initToolbar(final IToolBarManager toolbarManager) {
		toolbarManager.add(this.addRepAction);
		toolbarManager.update(true);
	}

	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		this.viewer.addSelectionChangedListener(listener);

	}

	public ISelection getSelection() {
		return this.viewer.getSelection();
	}

	public void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		this.viewer.removeSelectionChangedListener(listener);

	}

	public void dispose() {
		if (this.viewer != null) {
			this.viewer.removeSelectionChangedListener(this.actionBar);
			this.repositories.eAdapters().remove(this.checkRepositoryCountAdapter);
			this.editingDomain.dispose();
		}
	}

	public void setSelection(final ISelection selection) {
		this.viewer.setSelection(selection);

	}

	public EditingDomain getEditingDomain() {
		return this.editingDomain;
	}

	public Viewer getViewer() {
		return this.viewer;
	}

	public boolean setFocus() {
		return this.viewer.getControl().setFocus();
	}

	public Control getTopControl() {
		return this.mainComp;
	}

}
