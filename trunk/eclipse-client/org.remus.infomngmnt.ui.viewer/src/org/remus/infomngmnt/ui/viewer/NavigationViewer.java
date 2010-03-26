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

package org.remus.infomngmnt.ui.viewer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.ui.celleditor.AdapterFactoryTreeEditor;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.UnwrappingSelectionProvider;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.part.ISetSelectionTarget;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.SynchronizationState;
import org.remus.infomngmnt.core.model.AllProjectContentAdapter;
import org.remus.infomngmnt.ui.viewer.context.NavigationContextMenu;
import org.remus.infomngmnt.ui.viewer.dnd.NavigationDropAdapter;
import org.remus.infomngmnt.ui.viewer.provider.InformationUnitLabelProvider;
import org.remus.infomngmnt.ui.viewer.provider.NavigatorDecoratingLabelProvider;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NavigationViewer implements ISelectionProvider, IEditingDomainProvider,
		ISetSelectionTarget, IViewerProvider {

	private AdapterFactoryContentProvider contentProvider;
	private ILabelProvider labelProvider;
	private TreeViewer viewer;
	private NavigationContextMenu actionBar;
	private String contextMenuId;

	private MenuManager contextMenu;

	private final ArrayList<Object> expandedElements;

	private final ArrayList<Object> selectedElements;

	private final AllProjectContentAdapter unreadAdapter = new AllProjectContentAdapter() {

		@Override
		protected void notifyProjectChange(
				final org.eclipse.emf.common.notify.Notification notification) {
			if (notification.getFeature() == InfomngmntPackage.Literals.INFORMATION_UNIT_LIST_ITEM__UNREAD) {
				refreshElementAndParents(notification.getNotifier());
			}
		}

	};

	private IViewSite parentcontainer;

	public NavigationViewer() {
		this.expandedElements = new ArrayList<Object>();
		this.selectedElements = new ArrayList<Object>();

	}

	protected void refreshElementAndParents(final Object notifier) {
		this.parentcontainer.getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				NavigationViewer.this.viewer.refresh(notifier);
				EObject object = (EObject) notifier;
				if (object instanceof EObject) {
					while ((object).eContainer() != null) {
						NavigationViewer.this.viewer.refresh(object.eContainer());
						object = object.eContainer();
					}
				}

			}
		});

	}

	/**
	 * Create contents of the view part
	 * 
	 * @param parent
	 */

	public void createControl(final IViewSite viewsite, final Composite parent) {

		this.parentcontainer = viewsite;
		//
		Tree tree = new Tree(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		// tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		this.viewer = new TreeViewer(tree);
		initProvider();
		initInput();
		initOpen();
		createActions();
		initDrag();
		initDrop();
		hookActionBars();
		hookContextMenu();
	}

	protected void initOpen() {
		this.viewer.addOpenListener(new IOpenListener() {
			public void open(final OpenEvent event) {
				List list = ((IStructuredSelection) event.getSelection()).toList();
				for (Object object : list) {
					if (object instanceof InformationUnitListItem) {
						handleOpen((InformationUnitListItem) object);
					} else if (object instanceof Category) {
						if (NavigationViewer.this.viewer.getExpandedState(object)) {
							NavigationViewer.this.viewer.collapseToLevel(object, 1);
						} else {
							NavigationViewer.this.viewer.expandToLevel(object, 1);
						}
					}
				}
			}

		});

	}

	protected void handleOpen(final InformationUnitListItem object) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the contextMenuId
	 */
	protected String getContextMenuId() {
		if (this.contextMenuId == null && this.parentcontainer != null) {
			return this.parentcontainer.getId();
		}
		return this.contextMenuId;
	}

	/**
	 * @param contextMenuId
	 *            the contextMenuId to set
	 */
	public final void setContextMenuId(final String contextMenuId) {
		this.contextMenuId = contextMenuId;
	}

	public void init(final IViewSite site, final IMemento memento) {
		if (memento != null) {
			ViewerActivator.getDefault().getApplicationService();
			IMemento[] children = memento.getChildren("expanded");
			for (IMemento iMemento : children) {
				try {
					String textData = iMemento.getTextData();
					EObject eObject = ViewerActivator.getDefault().getEditService()
							.getNavigationEditingDomain().getResourceSet().getEObject(
									URI.createURI(textData), true);
					Object wrapper = ViewerActivator.getDefault().getEditService()
							.getNavigationEditingDomain().getWrapper(eObject);
					if (wrapper != null) {
						this.expandedElements.add(wrapper);
					}
				} catch (Exception e) {
					// if any exception occurs, e.g.
					// the project is not open we have to skip
					// here.
				}
			}
			children = memento.getChildren("selected");
			for (IMemento iMemento : children) {
				try {
					String textData = iMemento.getTextData();
					EObject eObject = ViewerActivator.getDefault().getEditService()
							.getNavigationEditingDomain().getResourceSet().getEObject(
									URI.createURI(textData), true);
					this.selectedElements.add(ViewerActivator.getDefault().getEditService()
							.getNavigationEditingDomain().getWrapper(eObject));
				} catch (Exception e) {
					// if any exception occurs, e.g.
					// the project is not open we have to skip
					// here.
				}
			}
		}
	}

	public void saveState(final IMemento child) {
		Object[] expandedElements = this.viewer.getExpandedElements();
		for (Object object : expandedElements) {
			if (object instanceof EObject) {
				URI uri = EcoreUtil.getURI((EObject) object);
				IMemento createChild = child.createChild("expanded");
				createChild.putTextData(uri.toString());
			}
		}
		Object[] array = ((IStructuredSelection) this.viewer.getSelection()).toArray();
		for (Object object : array) {
			URI uri = EcoreUtil.getURI((EObject) object);
			IMemento createChild = child.createChild("selected");
			createChild.putTextData(uri.toString());
		}
	}

	private void initDrag() {
		final int dndOperations = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
		final Transfer[] transfers = new Transfer[] { LocalTransfer.getInstance() };
		this.viewer.addDragSupport(dndOperations, transfers, new ViewerDragAdapter(this.viewer));

	}

	private void initDrop() {
		final int dndOperations = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
		this.viewer.addDropSupport(dndOperations, getTransferTypes(), new NavigationDropAdapter(
				getEditingDomain(), this.viewer));
		// this.viewer.addDropSupport(dndOperations, getTransferTypes(), new
		// EditingDomainViewerDropAdapter(getEditingDomain(),this.viewer));
	}

	protected void initInput() {
		this.viewer.setUseHashlookup(true);
		this.viewer.setInput(ViewerActivator.getDefault().getApplicationService().getModel());
		this.viewer.setExpandedElements(this.expandedElements.toArray(new Object[0]));
		this.viewer.setSelection(new StructuredSelection(this.selectedElements), true);
		this.unreadAdapter.initialize();
	}

	protected void initProvider() {
		this.contentProvider = new AdapterFactoryContentProvider(ViewerActivator.getDefault()
				.getEditService().getAdapterFactory()) {
			@Override
			public boolean hasChildren(final Object object) {
				if (object instanceof InformationUnitListItem) {
					return false;
				}
				return super.hasChildren(object);
			}

			@Override
			public Object[] getChildren(final Object object) {
				return super.getChildren(object);
			}
		};

		this.labelProvider = new NavigatorDecoratingLabelProvider(new InformationUnitLabelProvider(
				ViewerActivator.getDefault().getEditService().getAdapterFactory()));

		this.viewer.setContentProvider(this.contentProvider);
		this.viewer.setLabelProvider(this.labelProvider);
		this.viewer.addFilter(new ViewerFilter() {
			@Override
			public boolean select(final Viewer viewer, final Object parentElement,
					final Object element) {
				return !(element instanceof SynchronizationMetadata);
			}
		});
		this.viewer.addFilter(new ViewerFilter() {

			@Override
			public boolean select(final Viewer viewer, final Object parentElement,
					final Object element) {
				if (element instanceof SynchronizableObject
						&& ((SynchronizableObject) element).getSynchronizationMetaData() != null
						&& ((SynchronizableObject) element).getSynchronizationMetaData()
								.getSyncState() == SynchronizationState.LOCAL_DELETED) {
					return false;
				}
				return true;
			}

		});

		new AdapterFactoryTreeEditor(this.viewer.getTree(), ViewerActivator.getDefault()
				.getEditService().getAdapterFactory());

	}

	/**
	 * Create the actions
	 */
	private void createActions() {
		// Create the actions
	}

	public void initToolbar(final IToolBarManager toolbarManager) {

	}

	private void hookActionBars() {
		this.actionBar = new NavigationContextMenu();
		this.actionBar.init(this.parentcontainer.getActionBars());
		this.actionBar.setActiveDomain(this);
	}

	/**
	 * @param viewer2
	 */
	private void hookContextMenu() {
		this.contextMenu = new MenuManager("#PopUpMenu");
		this.contextMenu.setRemoveAllWhenShown(true);
		this.contextMenu.addMenuListener(this.actionBar);
		final Menu menu = this.contextMenu.createContextMenu(this.viewer.getControl());
		this.viewer.getControl().setMenu(menu);
		this.parentcontainer.registerContextMenu(getContextMenuId(), this.contextMenu,
				new UnwrappingSelectionProvider(this));

	}

	public void handleSelect() {
		this.actionBar.setGlobalActionHandler();
		this.parentcontainer.setSelectionProvider(this);
	}

	public void handleDeselect() {

	}

	public boolean setFocus() {
		return this.viewer.getControl().setFocus();
	}

	public void selectReveal(final ISelection selection) {
		this.viewer.setSelection(selection, true);

	}

	public EditingDomain getEditingDomain() {
		return ViewerActivator.getDefault().getEditService().getNavigationEditingDomain();
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

	public void setSelection(final ISelection selection) {
		this.viewer.setSelection(selection);

	}

	public Viewer getViewer() {
		return this.viewer;
	}

	private Transfer[] getTransferTypes() {
		List<Transfer> returnValue = new ArrayList<Transfer>();
		// FIXME
		// Collection<TransferWrapper> values =
		// InfomngmntEditPlugin.getPlugin().getService(
		// IRuleExtensionService.class).getAllTransferTypes().values();
		// for (TransferWrapper transferWrapper : values) {
		// returnValue.add(transferWrapper.getTransfer());
		// }
		// add local transfer
		returnValue.add(LocalTransfer.getInstance());
		return returnValue.toArray(new Transfer[returnValue.size()]);
	}

	public void dispose() {
		this.unreadAdapter.dispose();

	}

}
