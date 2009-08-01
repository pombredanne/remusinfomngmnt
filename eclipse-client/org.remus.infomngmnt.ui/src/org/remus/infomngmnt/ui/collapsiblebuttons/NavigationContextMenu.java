package org.remus.infomngmnt.ui.collapsiblebuttons;

import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.ui.action.CopyAction;
import org.eclipse.emf.edit.ui.action.CutAction;
import org.eclipse.emf.edit.ui.action.PasteAction;
import org.eclipse.emf.edit.ui.action.RedoAction;
import org.eclipse.emf.edit.ui.action.UndoAction;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.views.properties.IPropertySheetPage;

import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.views.NewWizardMenu;
import org.remus.infomngmnt.ui.views.action.DeleteNavigationAction;
import org.remus.infomngmnt.ui.views.action.RefreshTreeAction;
import org.remus.infomngmnt.ui.views.action.RenameAction;
import org.remus.infomngmnt.util.EditingUtil;
import org.remus.infomngmt.common.ui.uimodel.provider.UimodelEditPlugin;

/**
 * This is a contributor for an editor, multi-page or otherwise, that implements
 * {@link IEditingDomainProvider}. It automatically hooks up the Undo, Redo,
 * Cut, Copy, Paste, and Delete actions on the Edit menu to the corresponding
 * commands supported by the {@link org.eclipse.emf.edit.domain.EditingDomain}.
 * The editor site'selection provider is used to keep the Cut, Copy, Paste, and
 * Delete actions up-to-date. The actions are also refreshed every time the
 * editor fires to its {@link IPropertyListener}s.
 * <p>
 * Another very useful feature of this contributor is that it can be used as
 * follows:
 * 
 * <pre>
 * ((IMenuListener) ((IEditorSite) getSite()).getActionBarContributor()).menuAboutToShow(menuManager);
 * </pre>
 * 
 * to contribute the Edit menu actions to a pop-up menu.
 */
public class NavigationContextMenu implements IMenuListener, IPropertyListener,
		ISelectionChangedListener {

	private RenameAction renameAction;
	/**
	 * This is the action used to implement undo.
	 */
	protected UndoAction undoAction;

	/**
	 * This is the action used to implement redo.
	 */
	protected RedoAction redoAction;
	/**
	 * This keeps track of the current editor part.
	 */
	protected IEditingDomainProvider activeEditor;

	/**
	 * This is the action used to implement delete.
	 */
	protected DeleteNavigationAction deleteAction;

	/**
	 * This is the action used to implement cut.
	 */
	protected CutAction cutAction;

	/**
	 * This is the action used to implement copy.
	 */
	protected CopyAction copyAction;

	/**
	 * This is the action used to implement paste.
	 */
	protected PasteAction pasteAction;

	protected RefreshTreeAction refreshAction;

	/**
	 * This style bit indicates that the "additions" separator should come after
	 * the "edit" separator.
	 */
	public static final int ADDITIONS_LAST_STYLE = 0x1;

	/**
	 * This is the action that creates a pulldown with the available wizards.
	 */
	IWorkbenchAction create = ActionFactory.NEW_WIZARD_DROP_DOWN.create(PlatformUI.getWorkbench()
			.getActiveWorkbenchWindow());

	/**
	 * This is used to encode the style bits.
	 */
	protected int style;

	private NewWizardMenu newWizardMenu;
	private IActionBars actionBars;

	/**
	 * This creates an instance of the contributor.
	 */
	public NavigationContextMenu() {
		super();
	}

	/**
	 * This creates an instance of the contributor.
	 */
	public NavigationContextMenu(final int style) {
		super();
		this.style = style;
	}

	public void init(final IActionBars actionBars) {
		this.actionBars = actionBars;
		final ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();

		this.deleteAction = new DeleteNavigationAction(EditingUtil.getInstance()
				.getNavigationEditingDomain(), removeAllReferencesOnDelete());
		this.deleteAction.setImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		this.deleteAction.setActionDefinitionId("org.eclipse.ui.edit.delete");

		this.cutAction = new CutAction();
		this.cutAction.setImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_CUT));

		this.copyAction = new CopyAction();
		this.copyAction.setImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));

		this.pasteAction = new PasteAction();
		this.pasteAction.setImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));

		this.undoAction = new UndoAction();
		this.undoAction.setImageDescriptor(ResourceManager.getPluginImageDescriptor(
				UimodelEditPlugin.getPlugin(), "icons/iconexperience/undo.png"));
		actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(), this.undoAction);

		this.redoAction = new RedoAction();
		this.redoAction.setImageDescriptor(ResourceManager.getPluginImageDescriptor(
				UimodelEditPlugin.getPlugin(), "icons/iconexperience/redo.png"));
		actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(), this.redoAction);

		this.refreshAction = new RefreshTreeAction();
		this.refreshAction.setImageDescriptor(ResourceManager.getPluginImageDescriptor(UIPlugin
				.getDefault(), "icons/iconexperience/16/nav_refresh_blue.png"));
		actionBars.setGlobalActionHandler(ActionFactory.REFRESH.getId(), this.refreshAction);

		this.renameAction = new RenameAction();
		setGlobalActionHandler();

	}

	public void setGlobalActionHandler() {
		this.actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(), this.deleteAction);
		this.actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(), this.cutAction);
		this.actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(), this.copyAction);
		this.actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(), this.pasteAction);
		this.actionBars.setGlobalActionHandler(ActionFactory.RENAME.getId(), this.renameAction);
		this.actionBars.updateActionBars();
	}

	/**
	 * This determines whether or not the delete action should clean up all
	 * references to the deleted objects. It is false by default, to provide the
	 * same beahviour, by default, as in EMF 2.1 and before. You should probably
	 * override this method to return true, in order to get the new, more useful
	 * beahviour.
	 * 
	 * @since 2.2
	 */
	protected boolean removeAllReferencesOnDelete() {
		return false;
	}

	public void shareGlobalActions(final IPage page, final IActionBars actionBars) {
		if (!(page instanceof IPropertySheetPage)) {
			actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(), this.deleteAction);
			actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(), this.cutAction);
			actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(), this.copyAction);
			actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(), this.pasteAction);
		}
		actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(), this.undoAction);
		actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(), this.redoAction);

	}

	public void setActiveDomain(final IEditingDomainProvider domain) {
		if (domain != this.activeEditor) {
			if (this.activeEditor != null) {
				deactivate();
			}

			if (domain instanceof IEditingDomainProvider) {
				this.activeEditor = domain;
				activate();

			}
		}
	}

	public void deactivate() {
		this.deleteAction.setEditingDomain(null);
		this.cutAction.setEditingDomain(null);
		this.copyAction.setEditingDomain(null);
		this.pasteAction.setEditingDomain(null);
		this.undoAction.setEditingDomain(null);
		this.redoAction.setEditingDomain(null);

		final ISelectionProvider selectionProvider = this.activeEditor instanceof ISelectionProvider ? (ISelectionProvider) this.activeEditor
				: null;

		if (selectionProvider != null) {
			selectionProvider.removeSelectionChangedListener(this.deleteAction);
			selectionProvider.removeSelectionChangedListener(this.cutAction);
			selectionProvider.removeSelectionChangedListener(this.copyAction);
			selectionProvider.removeSelectionChangedListener(this.pasteAction);

		}
	}

	public void activate() {
		this.deleteAction.setEditingDomain(this.activeEditor.getEditingDomain());
		this.cutAction.setEditingDomain(this.activeEditor.getEditingDomain());
		this.copyAction.setEditingDomain(this.activeEditor.getEditingDomain());
		this.pasteAction.setEditingDomain(this.activeEditor.getEditingDomain());
		this.undoAction.setEditingDomain(this.activeEditor.getEditingDomain());
		this.redoAction.setEditingDomain(this.activeEditor.getEditingDomain());
		this.renameAction.setEditingDomain(this.activeEditor.getEditingDomain());

		final ISelectionProvider selectionProvider = this.activeEditor instanceof ISelectionProvider ? (ISelectionProvider) this.activeEditor
				: null;

		if (selectionProvider != null) {
			selectionProvider.addSelectionChangedListener(this.deleteAction);
			selectionProvider.addSelectionChangedListener(this.cutAction);
			selectionProvider.addSelectionChangedListener(this.copyAction);
			selectionProvider.addSelectionChangedListener(this.pasteAction);
			selectionProvider.addSelectionChangedListener(this.renameAction);
		}

		update();
	}

	public void update() {
		final ISelectionProvider selectionProvider = this.activeEditor instanceof ISelectionProvider ? (ISelectionProvider) this.activeEditor
				: null;

		if (selectionProvider != null) {
			final ISelection selection = selectionProvider.getSelection();
			final IStructuredSelection structuredSelection = selection instanceof IStructuredSelection ? (IStructuredSelection) selection
					: StructuredSelection.EMPTY;

			this.deleteAction.updateSelection(structuredSelection);
			this.cutAction.updateSelection(structuredSelection);
			this.copyAction.updateSelection(structuredSelection);
			this.pasteAction.updateSelection(structuredSelection);
			this.renameAction.updateSelection(structuredSelection);
		}
		final Viewer viewerProvider = this.activeEditor instanceof IViewerProvider ? ((IViewerProvider) this.activeEditor)
				.getViewer()
				: null;
		if (viewerProvider != null) {
			this.refreshAction.setViewer(viewerProvider);
		}

		this.undoAction.update();
		this.redoAction.update();
	}

	/**
	 * This implements {@link org.eclipse.jface.action.IMenuListener} to help
	 * fill the context menus with contributions from the Edit menu.
	 */
	public void menuAboutToShow(final IMenuManager menuManager) {

		// refresh undo/redo
		this.undoAction.update();
		this.redoAction.update();
		// Add our standard marker.
		//
		if ((this.style & ADDITIONS_LAST_STYLE) == 0) {
			menuManager.add(new Separator("additions"));
		}
		// create the New submenu, using the same id for it as the New action
		String newText = IDEWorkbenchMessages.Workbench_new;
		String newId = ActionFactory.NEW.getId();
		MenuManager newMenu = new MenuManager(newText, newId);
		newMenu.add(new Separator(newId));
		this.newWizardMenu = new NewWizardMenu(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		newMenu.add(this.newWizardMenu);
		newMenu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		menuManager.add(newMenu);
		// menuManager.add(new Separator(ActionFactory.NEW.getId()));
		menuManager.add(new Separator("edit"));

		// Add the edit menu actions.
		//
		menuManager.add(this.undoAction);
		menuManager.add(this.redoAction);
		menuManager.add(new Separator());
		menuManager.add(this.renameAction);
		menuManager.add(this.refreshAction);
		menuManager.add(new Separator());
		menuManager.add(this.cutAction);
		menuManager.add(this.copyAction);
		menuManager.add(this.pasteAction);
		menuManager.add(new Separator());
		menuManager.add(new GroupMarker("iuspecific"));
		menuManager.add(new Separator());
		menuManager.add(new GroupMarker("sync"));
		menuManager.add(new Separator());
		menuManager.add(this.deleteAction);

		if ((this.style & ADDITIONS_LAST_STYLE) != 0) {
			menuManager.add(new Separator("additions"));
			menuManager.add(new Separator());
		}
		// Add our other standard marker.
		//
		menuManager.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));

		addGlobalActions(menuManager);
	}

	/**
	 * This inserts global actions before the "additions-end" separator.
	 */
	protected void addGlobalActions(final IMenuManager menuManager) {
		final String key = (this.style & ADDITIONS_LAST_STYLE) == 0 ? "additions-end" : "additions";
	}

	public void propertyChanged(final Object source, final int id) {
		update();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(
	 * org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	public void selectionChanged(final SelectionChangedEvent event) {
		update();

	}

}
