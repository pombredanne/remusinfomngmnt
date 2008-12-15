package org.remus.infomngmnt.ui.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.ui.celleditor.AdapterFactoryTreeEditor;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.UnwrappingSelectionProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
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
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.GridData;
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
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.core.extension.RuleExtensionManager;
import org.remus.infomngmnt.core.extension.TransferWrapper;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.dnd.NavigationDropAdapter;
import org.remus.infomngmnt.ui.editors.InformationEditor;
import org.remus.infomngmnt.ui.editors.InformationEditorInput;
import org.remus.infomngmnt.ui.provider.NavigationCellLabelProvider;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NavigationView extends ViewPart implements ISetSelectionTarget, IEditingDomainProvider, ISelectionProvider, IViewerProvider {

	IPartListener partListener = new IPartListener() {
		public void partActivated(IWorkbenchPart part) {
			if (part instanceof EditorPart) {
				IEditorInput input = ((EditorPart) part).getEditorInput();
				if (input instanceof FileEditorInput) {
					Object adapter = Platform.getAdapterManager().getAdapter(((FileEditorInput) input).getFile(), InformationUnitListItem.class);
					if (adapter != null) {
						setSelection(new StructuredSelection(adapter));
					}
				}
			}
			if (part == NavigationView.this) {
				//NavigationView.this.actionBar.setActiveDomain(NavigationView.this);
			}

		}

		public void partBroughtToTop(IWorkbenchPart part) {

		}

		public void partClosed(IWorkbenchPart part) {

		}

		public void partDeactivated(IWorkbenchPart part) {

		}

		public void partOpened(IWorkbenchPart part) {

		}
	};

	public static final String ID = "org.remus.infomngmnt.ui.views.NavigationView"; //$NON-NLS-1$
	private AdapterFactoryContentProvider contentProvider;
	private DelegatingStyledCellLabelProvider labelProvider;
	private TreeViewer viewer;
	private TreeViewerColumn tvc1;
	private Action linkEditorAction;
	private boolean linkEditor;
	private IDialogSettings settings;
	private NavigationContextMenu actionBar;

	/**
	 * Create contents of the view part
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

		//
		Tree tree = new Tree(parent, SWT.MULTI);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		this.viewer = new TreeViewer(tree);

		initProvider();
		initInput();
		initOpen();
		initLinkWithEditor();
		createActions();
		initializeToolBar();
		initializeMenu();
		initDrag();
		initDrop();
		hookContextMenu();
	}

	private void initOpen() {
		this.viewer.addOpenListener(new IOpenListener() {
			public void open(OpenEvent event) {
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
	public void init(IViewSite site, IMemento memento) throws PartInitException {
		super.init(site, memento);
		this.settings = UIPlugin.getDefault().getDialogSettings();
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

	}

	private void initProvider() {
		this.contentProvider = new AdapterFactoryContentProvider(EditingUtil.getInstance().getAdapterFactory());
		this.labelProvider = new DelegatingStyledCellLabelProvider(new NavigationCellLabelProvider());

		this.viewer.setContentProvider(this.contentProvider);
		this.viewer.setLabelProvider(this.labelProvider);
		getSite().setSelectionProvider(this);
		new AdapterFactoryTreeEditor(this.viewer.getTree(), EditingUtil.getInstance().getAdapterFactory());


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
		toolbarManager.add(this.linkEditorAction);
	}

	/**
	 * @param viewer2
	 */
	private void hookContextMenu() {
		final MenuManager contextMenu = new MenuManager("#PopUpMenu");
		contextMenu.setRemoveAllWhenShown(true);
		this.actionBar = new NavigationContextMenu();
		this.actionBar.init(getViewSite().getActionBars());
		this.actionBar.setActiveDomain(this);
		contextMenu.addMenuListener(this.actionBar);
		final Menu menu = contextMenu.createContextMenu(this.viewer.getControl());
		this.viewer.getControl().setMenu(menu);
		getSite().registerContextMenu("de.spiritlink.facebook.ui.views.View.context",contextMenu, new UnwrappingSelectionProvider(this));

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

	public void selectReveal(ISelection selection) {
		this.viewer.setSelection(selection, true);

	}

	public EditingDomain getEditingDomain() {
		return EditingUtil.getInstance().getNavigationEditingDomain();
	}

	public void addSelectionChangedListener(ISelectionChangedListener listener) {
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

	}

	public void setLinkingEnabled(final boolean enabled) {
		this.linkEditor = enabled;

		// remember the last settings in the dialog settings
		this.settings.put(IWorkbenchPreferenceConstants.LINK_NAVIGATOR_TO_EDITOR,
				enabled);

		// if turning linking on, update the selection to correspond to the active editor
		if (enabled) {
			IEditorInput editorInput = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getEditorInput();
			if (editorInput instanceof FileEditorInput) {
				Object adapter = Platform.getAdapterManager().getAdapter(((FileEditorInput) editorInput).getFile(), InformationUnitListItem.class);
				if (adapter != null) {
					setSelection(new StructuredSelection(adapter));
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
			ISelectionChangedListener listener) {
		this.viewer.removeSelectionChangedListener(listener);
	}

	public void setSelection(ISelection selection) {
		this.viewer.setSelection(selection);

	}

	public Viewer getViewer() {
		return this.viewer;
	}

	private Transfer[] getTransferTypes() {
		List<Transfer> returnValue = new ArrayList<Transfer>();
		Collection<TransferWrapper> values = RuleExtensionManager.getInstance().getAllTransferTypes().values();
		for (TransferWrapper transferWrapper : values) {
			returnValue.add(transferWrapper.getTransfer());
		}
		// add local transfer
		returnValue.add(LocalTransfer.getInstance());
		return returnValue.toArray(new Transfer[returnValue.size()]);
	}

}
