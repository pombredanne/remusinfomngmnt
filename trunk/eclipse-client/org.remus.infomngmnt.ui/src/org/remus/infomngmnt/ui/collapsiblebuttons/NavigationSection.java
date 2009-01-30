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

package org.remus.infomngmnt.ui.collapsiblebuttons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.Platform;
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
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.provider.UnwrappingSelectionProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ISetSelectionTarget;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.core.extension.TransferWrapper;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.services.IRuleExtensionService;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.dnd.NavigationDropAdapter;
import org.remus.infomngmnt.ui.editors.InformationEditor;
import org.remus.infomngmnt.ui.editors.InformationEditorInput;
import org.remus.infomngmnt.ui.extension.CollapsibleButtonBar;
import org.remus.infomngmnt.ui.provider.NavigatorDecoratingLabelProvider;
import org.remus.infomngmnt.ui.views.MainViewPart;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NavigationSection extends CollapsibleButtonBar implements ISelectionProvider, IEditingDomainProvider, ISetSelectionTarget, IViewerProvider {

	IPartListener partListener = new IPartListener() {
		public void partActivated(final IWorkbenchPart part) {
			if (part instanceof EditorPart) {
				IEditorInput input = ((EditorPart) part).getEditorInput();
				if (input instanceof FileEditorInput) {
					Object adapter = Platform.getAdapterManager().getAdapter(((FileEditorInput) input).getFile(), InformationUnitListItem.class);
					if (adapter != null) {
						setSelection(new StructuredSelection(adapter));
					}
				}
			}
			if (part instanceof MainViewPart) {
				//NavigationView.this.actionBar.setActiveDomain(NavigationView.this);
			}

		}

		public void partBroughtToTop(final IWorkbenchPart part) {

		}

		public void partClosed(final IWorkbenchPart part) {

		}

		public void partDeactivated(final IWorkbenchPart part) {

		}

		public void partOpened(final IWorkbenchPart part) {

		}
	};

	public static final String ID = "org.remus.infomngmnt.ui.views.NavigationView"; //$NON-NLS-1$
	private AdapterFactoryContentProvider contentProvider;
	private ILabelProvider labelProvider;
	private TreeViewer viewer;
	private TreeViewerColumn tvc1;
	private Action linkEditorAction;
	private boolean linkEditor;
	private IDialogSettings settings;
	private NavigationContextMenu actionBar;

	private MenuManager contextMenu;

	private final ArrayList<Object> expandedElements;

	private final ArrayList<Object> selectedElements;

	public NavigationSection() {
		this.expandedElements = new ArrayList<Object>();
		this.selectedElements = new ArrayList<Object>();
	}
	/**
	 * Create contents of the view part
	 * @param parent
	 */
	@Override
	public void createControl(final Composite parent) {

		//
		Tree tree = new Tree(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		//tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		this.viewer = new TreeViewer(tree);
		initProvider();
		initInput();
		initOpen();
		initLinkWithEditor();
		createActions();
		initDrag();
		initDrop();
		hookActionBars();
		hookContextMenu();
		setControl(tree);
	}

	private void initOpen() {
		this.viewer.addOpenListener(new IOpenListener() {
			public void open(final OpenEvent event) {
				List list = ((IStructuredSelection) event.getSelection()).toList();
				for (Object object : list) {
					if (object instanceof InformationUnitListItem) {
						try {
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
									new InformationEditorInput((InformationUnitListItem) object), InformationEditor.ID);
						} catch (PartInitException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

		});

	}

	@Override
	public void init(final IViewSite site, final IMemento memento) {
		super.init(site, memento);
		this.settings = UIPlugin.getDefault().getDialogSettings();
		if (memento != null) {
			ApplicationModelPool.getInstance().getModel();
			IMemento[] children = memento.getChildren("expanded");
			for (IMemento iMemento : children) {
				String textData = iMemento.getTextData();
				EObject eObject = EditingUtil.getInstance()
					.getNavigationEditingDomain().getResourceSet()
					.getEObject(URI.createURI(textData), true);
				this.expandedElements.add(EditingUtil.getInstance()
						.getNavigationEditingDomain().getWrapper(eObject));
			}
			children = memento.getChildren("selected");
			for (IMemento iMemento : children) {
				String textData = iMemento.getTextData();
				EObject eObject = EditingUtil.getInstance()
				.getNavigationEditingDomain().getResourceSet()
				.getEObject(URI.createURI(textData), true);
				this.selectedElements.add(EditingUtil.getInstance()
						.getNavigationEditingDomain().getWrapper(eObject));
			}
		}
	}
	
	@Override
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
		this.viewer.addDropSupport(dndOperations, getTransferTypes(), new NavigationDropAdapter(getEditingDomain(),this.viewer));
		//this.viewer.addDropSupport(dndOperations, getTransferTypes(), new EditingDomainViewerDropAdapter(getEditingDomain(),this.viewer));
	}

	private void initInput() {
		this.viewer.setInput(ApplicationModelPool.getInstance().getModel());
		this.viewer.setExpandedElements(this.expandedElements.toArray());
		this.viewer.setSelection(new StructuredSelection(this.selectedElements), true);

	}

	private void initProvider() {
		this.contentProvider = new AdapterFactoryContentProvider(EditingUtil.getInstance().getAdapterFactory()) {
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
		
		this.labelProvider = new NavigatorDecoratingLabelProvider(
				new AdapterFactoryLabelProvider(EditingUtil.getInstance().getAdapterFactory()));
				

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
				return ((EObject)element).eContainingFeature() != InfomngmntPackage
					.Literals.SYNCHRONIZABLE_OBJECT__MARKED_AS_DELETE_ITEMS;
			}
			
		});
		
		new AdapterFactoryTreeEditor(this.viewer.getTree(), EditingUtil.getInstance().getAdapterFactory());


	}

	/**
	 * Create the actions
	 */
	private void createActions() {
		// Create the actions
	}

	@Override
	public void initToolbar(final IToolBarManager toolbarManager) {
		toolbarManager.add(this.linkEditorAction);
		toolbarManager.update(true);
	}
	

	private void hookActionBars() {
		this.actionBar = new NavigationContextMenu();
		this.actionBar.init(getViewSite().getActionBars());
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
		getViewSite().registerContextMenu(getId(), this.contextMenu, new UnwrappingSelectionProvider(this));
		
		
	}

	@Override
	public void handleSelect() {
		this.actionBar.setGlobalActionHandler();
		getViewSite().setSelectionProvider(this);
		super.handleSelect();
	}
	
	@Override
	public void handleDeselect() {
		super.handleDeselect();
		
		
	}

	@Override
	public void setFocus() {
		this.viewer.getControl().setFocus();
	}
	

	public void selectReveal(final ISelection selection) {
		this.viewer.setSelection(selection, true);

	}

	public EditingDomain getEditingDomain() {
		return EditingUtil.getInstance().getNavigationEditingDomain();
	}

	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		this.viewer.addSelectionChangedListener(listener);

	}



	/**
	 * 
	 */
	private void initLinkWithEditor() {
		// Try the dialog settings first, which remember the last choice.
		final String setting = this.settings
		.get(IWorkbenchPreferenceConstants.LINK_NAVIGATOR_TO_EDITOR);
		if (setting != null) {
			this.linkEditor = setting.equals("true"); //$NON-NLS-1$
		}
		// Link with editor

		this.linkEditorAction = new Action("Link with editor", IAction.AS_CHECK_BOX) {
			/* (non-Javadoc)
			 * @see org.eclipse.jface.action.Action#run()
			 */
			@Override
			public void run() {
				setLinkingEnabled(isChecked());
			}

			/* (non-Javadoc)
			 * @see org.eclipse.jface.action.Action#getToolTipText()
			 */
			@Override
			public String getToolTipText() {
				return getText();
			}
			/* (non-Javadoc)
			 * @see org.eclipse.jface.action.Action#getImageDescriptor()
			 */
			@Override
			public ImageDescriptor getImageDescriptor() {
				return AbstractUIPlugin.imageDescriptorFromPlugin(UIPlugin.PLUGIN_ID, "icons/synced.gif");
			}


		};
		this.linkEditorAction.setChecked(this.linkEditor);
		//setLinkingEnabled(true);

	}

	public void setLinkingEnabled(final boolean enabled) {
		this.linkEditor = enabled;

		// remember the last settings in the dialog settings
		this.settings.put(IWorkbenchPreferenceConstants.LINK_NAVIGATOR_TO_EDITOR,
				enabled);

		// if turning linking on, update the selection to correspond to the active editor
		if (enabled) {
			if (PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() != null) {
				IEditorInput editorInput = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getEditorInput();
				if (editorInput instanceof FileEditorInput) {
					Object adapter = Platform.getAdapterManager().getAdapter(((FileEditorInput) editorInput).getFile(), InformationUnitListItem.class);
					if (adapter != null) {
						setSelection(new StructuredSelection(adapter));
					}
				}
			}
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().addPartListener(this.partListener);
		} else {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().removePartListener(this.partListener);
		}
	}
	

	public ISelection getSelection() {
		return this.viewer.getSelection();
	}

	public void removeSelectionChangedListener(
			final ISelectionChangedListener listener) {
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
		Collection<TransferWrapper> values = InfomngmntEditPlugin.getPlugin().getService(IRuleExtensionService.class).getAllTransferTypes().values();
		for (TransferWrapper transferWrapper : values) {
			returnValue.add(transferWrapper.getTransfer());
		}
		// add local transfer
		returnValue.add(LocalTransfer.getInstance());
		return returnValue.toArray(new Transfer[returnValue.size()]);
	}
	
	
}
