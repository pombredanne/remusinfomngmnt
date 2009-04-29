package org.remus.infomngmnt.application;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.SWT;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.actions.NewWizardMenu;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.ide.IDEActionFactory;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.handlers.IActionCommandMappingService;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.osgi.framework.BundleContext;

import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmt.common.ui.uimodel.provider.UimodelEditPlugin;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of
 * the actions added to a workbench window. Each window will be populated with
 * new actions.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	// Actions - important to allocate these only in makeActions, and then use
	// them
	// in the fill methods. This ensures that the actions aren't recreated
	// when fillActionBars is called with FILL_PROXY.

	private IWorkbenchAction saveAction;
	private final IWorkbenchWindow window;
	private NewWizardMenu newWizardMenu;
	private IWorkbenchAction saveAsAction;
	private IWorkbenchAction saveAllAction;
	private IWorkbenchAction closeAction;
	private IWorkbenchAction closeAllAction;
	private IWorkbenchAction openWorkspaceAction;
	private IWorkbenchAction undoAction;
	private IWorkbenchAction redoAction;
	private IWorkbenchAction importResourcesAction;
	private IWorkbenchAction exportResourcesAction;
	private IWorkbenchAction quitAction;
	private IWorkbenchAction preferenceAction;
	private IWorkbenchAction cleanAction;

	/**
	 * Constructs a new action builder which contributes actions to the given
	 * window.
	 * 
	 * @param configurer
	 *            the action bar configurer for the window
	 */
	public ApplicationActionBarAdvisor(final IActionBarConfigurer configurer) {
		super(configurer);
		this.window = configurer.getWindowConfigurer().getWindow();
	}

	/**
	 * Returns the window to which this action builder is contributing.
	 */
	private IWorkbenchWindow getWindow() {
		return this.window;
	}

	@Override
	protected void makeActions(final IWorkbenchWindow window) {
		// Creates the actions and registers them.
		// Registering is needed to ensure that key bindings work.
		// The corresponding commands keybindings are defined in the plugin.xml
		// file.
		// Registering also provides automatic disposal of the actions when
		// the window is closed.

		this.saveAction = ActionFactory.SAVE.create(window);
		register(this.saveAction);

		this.saveAction = ActionFactory.SAVE.create(window);
		register(this.saveAction);

		this.saveAsAction = ActionFactory.SAVE_AS.create(window);
		register(this.saveAsAction);

		this.saveAllAction = ActionFactory.SAVE_ALL.create(window);
		register(this.saveAllAction);

		this.closeAction = ActionFactory.CLOSE.create(window);
		register(this.closeAction);

		this.closeAllAction = ActionFactory.CLOSE_ALL.create(window);
		register(this.closeAllAction);

		this.openWorkspaceAction = IDEActionFactory.OPEN_WORKSPACE.create(window);
		register(this.openWorkspaceAction);

		this.preferenceAction = ActionFactory.PREFERENCES.create(window);
		register(this.preferenceAction);

		this.undoAction = ActionFactory.UNDO.create(window);
		this.undoAction.setImageDescriptor(ResourceManager.getPluginImageDescriptor(
				UimodelEditPlugin.getPlugin(), "icons/iconexperience/undo.png"));
		register(this.undoAction);

		this.redoAction = ActionFactory.REDO.create(window);
		this.redoAction.setImageDescriptor(ResourceManager.getPluginImageDescriptor(
				UimodelEditPlugin.getPlugin(), "icons/iconexperience/redo.png"));
		register(this.redoAction);

		this.importResourcesAction = ActionFactory.IMPORT.create(window);
		register(this.importResourcesAction);

		this.exportResourcesAction = ActionFactory.EXPORT.create(window);
		register(this.exportResourcesAction);

		this.quitAction = ActionFactory.QUIT.create(window);
		register(this.quitAction);

		this.cleanAction = IDEActionFactory.BUILD_CLEAN.create(window);
		register(this.cleanAction);

	}

	@Override
	protected void fillMenuBar(final IMenuManager menuBar) {
		menuBar.add(createFileMenu());
		menuBar.add(createEditMenu());
		menuBar.add(createExtraMenu());
		menuBar.add(createHelpMenu());
	}

	private MenuManager createHelpMenu() {
		MenuManager menu = new MenuManager(IDEWorkbenchMessages.Workbench_help,
				IWorkbenchActionConstants.M_HELP);
		menu.add(new GroupMarker("help-primary-additions-start"));
		menu.add(new GroupMarker("help-primary-additions-end"));
		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		menu.add(new Separator());

		return menu;
	}

	private MenuManager createExtraMenu() {
		MenuManager menu = new MenuManager("Extra", IWorkbenchActionConstants.M_WINDOW);
		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		menu.add(new Separator());
		menu.add(this.cleanAction);
		menu.add(this.preferenceAction);
		return menu;
	}

	/**
	 * @param trayItem
	 */
	protected void fillTrayItem(final IMenuManager trayItem) {
		trayItem.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		trayItem.add(getAction(ActionFactory.QUIT.getId()));
	}

	/**
	 * Creates and returns the File menu.
	 */
	private MenuManager createFileMenu() {
		MenuManager menu = new MenuManager(IDEWorkbenchMessages.Workbench_file,
				IWorkbenchActionConstants.M_FILE);
		menu.add(new GroupMarker(IWorkbenchActionConstants.FILE_START));
		{
			// create the New submenu, using the same id for it as the New
			// action
			String newText = IDEWorkbenchMessages.Workbench_new;
			String newId = ActionFactory.NEW.getId();
			MenuManager newMenu = new MenuManager(newText, newId);
			newMenu.setActionDefinitionId("org.eclipse.ui.file.newQuickMenu"); //$NON-NLS-1$
			newMenu.add(new Separator(newId));
			this.newWizardMenu = new NewWizardMenu(getWindow());
			newMenu.add(this.newWizardMenu);
			newMenu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
			menu.add(newMenu);
		}

		menu.add(new GroupMarker(IWorkbenchActionConstants.NEW_EXT));
		menu.add(new Separator());

		menu.add(this.closeAction);
		menu.add(this.closeAllAction);
		// menu.add(closeAllSavedAction);
		menu.add(new GroupMarker(IWorkbenchActionConstants.CLOSE_EXT));
		menu.add(new Separator());
		menu.add(this.saveAction);
		menu.add(this.saveAsAction);
		menu.add(this.saveAllAction);
		menu.add(new Separator());
		menu.add(getMoveItem());
		menu.add(getRenameItem());

		menu.add(new GroupMarker(IWorkbenchActionConstants.SAVE_EXT));
		menu.add(new Separator());
		menu.add(getPrintItem());
		menu.add(new GroupMarker(IWorkbenchActionConstants.PRINT_EXT));
		menu.add(new Separator());
		menu.add(this.openWorkspaceAction);
		menu.add(new GroupMarker(IWorkbenchActionConstants.OPEN_EXT));
		menu.add(new Separator());
		menu.add(this.importResourcesAction);
		menu.add(this.exportResourcesAction);
		menu.add(new GroupMarker(IWorkbenchActionConstants.IMPORT_EXT));
		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

		menu.add(new Separator());

		menu.add(ContributionItemFactory.REOPEN_EDITORS.create(getWindow()));
		menu.add(new GroupMarker(IWorkbenchActionConstants.MRU));
		menu.add(new Separator());

		// If we're on OS X we shouldn't show this command in the File menu. It
		// should be invisible to the user. However, we should not remove it -
		// the carbon UI code will do a search through our menu structure
		// looking for it when Cmd-Q is invoked (or Quit is chosen from the
		// application menu.
		ActionContributionItem quitItem = new ActionContributionItem(this.quitAction);
		quitItem.setVisible(!"carbon".equals(SWT.getPlatform())); //$NON-NLS-1$
		menu.add(quitItem);
		menu.add(new GroupMarker(IWorkbenchActionConstants.FILE_END));
		return menu;
	}

	/**
	 * Creates and returns the Edit menu.
	 */
	private MenuManager createEditMenu() {
		MenuManager menu = new MenuManager(IDEWorkbenchMessages.Workbench_edit,
				IWorkbenchActionConstants.M_EDIT);
		menu.add(new GroupMarker(IWorkbenchActionConstants.EDIT_START));

		menu.add(this.undoAction);
		menu.add(this.redoAction);
		menu.add(new GroupMarker(IWorkbenchActionConstants.UNDO_EXT));
		menu.add(new Separator());

		menu.add(getCutItem());
		menu.add(getCopyItem());
		menu.add(getPasteItem());
		menu.add(new GroupMarker(IWorkbenchActionConstants.CUT_EXT));
		menu.add(new Separator());

		menu.add(getDeleteItem());
		menu.add(getSelectAllItem());
		menu.add(new Separator());

		menu.add(getFindItem());
		menu.add(new GroupMarker(IWorkbenchActionConstants.FIND_EXT));
		menu.add(new Separator());

		menu.add(new GroupMarker(IWorkbenchActionConstants.ADD_EXT));

		menu.add(new GroupMarker(IWorkbenchActionConstants.EDIT_END));
		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		return menu;
	}

	private IContributionItem getCutItem() {
		return getItem(ActionFactory.CUT.getId(),
				"org.eclipse.ui.edit.cut", //$NON-NLS-1$
				ISharedImages.IMG_TOOL_CUT, ISharedImages.IMG_TOOL_CUT_DISABLED,
				WorkbenchMessages.Workbench_cut, WorkbenchMessages.Workbench_cutToolTip, null);
	}

	private IContributionItem getCopyItem() {
		return getItem(ActionFactory.COPY.getId(),
				"org.eclipse.ui.edit.copy", //$NON-NLS-1$
				ISharedImages.IMG_TOOL_COPY, ISharedImages.IMG_TOOL_COPY_DISABLED,
				WorkbenchMessages.Workbench_copy, WorkbenchMessages.Workbench_copyToolTip, null);
	}

	private IContributionItem getPasteItem() {
		return getItem(
				ActionFactory.PASTE.getId(),
				"org.eclipse.ui.edit.paste", ISharedImages.IMG_TOOL_PASTE, //$NON-NLS-1$
				ISharedImages.IMG_TOOL_PASTE_DISABLED, WorkbenchMessages.Workbench_paste,
				WorkbenchMessages.Workbench_pasteToolTip, null);
	}

	private IContributionItem getPrintItem() {
		return getItem(
				ActionFactory.PRINT.getId(),
				"org.eclipse.ui.file.print", ISharedImages.IMG_ETOOL_PRINT_EDIT, //$NON-NLS-1$
				ISharedImages.IMG_ETOOL_PRINT_EDIT_DISABLED, WorkbenchMessages.Workbench_print,
				WorkbenchMessages.Workbench_printToolTip, null);
	}

	private IContributionItem getSelectAllItem() {
		return getItem(ActionFactory.SELECT_ALL.getId(),
				"org.eclipse.ui.edit.selectAll", //$NON-NLS-1$
				null, null, WorkbenchMessages.Workbench_selectAll,
				WorkbenchMessages.Workbench_selectAllToolTip, null);
	}

	private IContributionItem getFindItem() {
		return getItem(ActionFactory.FIND.getId(),
				"org.eclipse.ui.edit.findReplace", //$NON-NLS-1$
				null, null, WorkbenchMessages.Workbench_findReplace,
				WorkbenchMessages.Workbench_findReplaceToolTip, null);
	}

	private IContributionItem getDeleteItem() {
		return getItem(
				ActionFactory.DELETE.getId(),
				"org.eclipse.ui.edit.delete", //$NON-NLS-1$
				ISharedImages.IMG_TOOL_DELETE, ISharedImages.IMG_TOOL_DELETE_DISABLED,
				WorkbenchMessages.Workbench_delete, WorkbenchMessages.Workbench_deleteToolTip,
				IWorkbenchHelpContextIds.DELETE_RETARGET_ACTION);
	}

	private IContributionItem getMoveItem() {
		return getItem(ActionFactory.MOVE.getId(),
				"org.eclipse.ui.edit.move", //$NON-NLS-1$
				null, null, WorkbenchMessages.Workbench_move,
				WorkbenchMessages.Workbench_moveToolTip, null);
	}

	private IContributionItem getRenameItem() {
		return getItem(ActionFactory.RENAME.getId(), "org.eclipse.ui.edit.rename", null, null, //$NON-NLS-1$
				WorkbenchMessages.Workbench_rename, WorkbenchMessages.Workbench_renameToolTip, null);
	}

	private IContributionItem getItem(final String actionId, final String commandId,
			final String image, final String disabledImage, final String label,
			final String tooltip, final String helpContextId) {
		ISharedImages sharedImages = getWindow().getWorkbench().getSharedImages();

		IActionCommandMappingService acms = (IActionCommandMappingService) getWindow().getService(
				IActionCommandMappingService.class);
		acms.map(actionId, commandId);

		CommandContributionItemParameter commandParm = new CommandContributionItemParameter(
				getWindow(), actionId, commandId, null, sharedImages.getImageDescriptor(image),
				sharedImages.getImageDescriptor(disabledImage), null, label, null, tooltip,
				CommandContributionItem.STYLE_PUSH, null, false);
		return new CommandContributionItem(commandParm);
	}

	private BundleContext getBundleById(final String id) {
		return Platform.getBundle(id).getBundleContext();
	}

}
