package org.eclipse.remus.application;

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
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.actions.NewWizardMenu;
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
	private IWorkbenchAction aboutaction;
	private IWorkbenchAction resetPerspectiveAction;

	/**
	 * Constructs a new action builder which contributes actions to the given
	 * window.
	 * 
	 * @param configurer
	 *            the action bar configurer for the window
	 */
	public ApplicationActionBarAdvisor(final IActionBarConfigurer configurer) {
		super(configurer);
		window = configurer.getWindowConfigurer().getWindow();
	}

	/**
	 * Returns the window to which this action builder is contributing.
	 */
	private IWorkbenchWindow getWindow() {
		return window;
	}

	@Override
	protected void makeActions(final IWorkbenchWindow window) {
		// Creates the actions and registers them.
		// Registering is needed to ensure that key bindings work.
		// The corresponding commands keybindings are defined in the plugin.xml
		// file.
		// Registering also provides automatic disposal of the actions when
		// the window is closed.

		saveAction = ActionFactory.SAVE.create(window);
		register(saveAction);

		saveAction = ActionFactory.SAVE.create(window);
		register(saveAction);

		saveAsAction = ActionFactory.SAVE_AS.create(window);
		register(saveAsAction);

		saveAllAction = ActionFactory.SAVE_ALL.create(window);
		register(saveAllAction);

		closeAction = ActionFactory.CLOSE.create(window);
		register(closeAction);

		closeAllAction = ActionFactory.CLOSE_ALL.create(window);
		register(closeAllAction);

		openWorkspaceAction = IDEActionFactory.OPEN_WORKSPACE.create(window);
		register(openWorkspaceAction);

		preferenceAction = ActionFactory.PREFERENCES.create(window);
		register(preferenceAction);

		undoAction = ActionFactory.UNDO.create(window);
		undoAction.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_TOOL_UNDO));
		register(undoAction);

		redoAction = ActionFactory.REDO.create(window);
		redoAction.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_TOOL_UNDO));
		register(redoAction);

		importResourcesAction = ActionFactory.IMPORT.create(window);
		register(importResourcesAction);

		exportResourcesAction = ActionFactory.EXPORT.create(window);
		register(exportResourcesAction);

		quitAction = ActionFactory.QUIT.create(window);
		register(quitAction);

		cleanAction = IDEActionFactory.BUILD_CLEAN.create(window);
		register(cleanAction);

		aboutaction = ActionFactory.ABOUT.create(window);
		register(aboutaction);

		resetPerspectiveAction = ActionFactory.RESET_PERSPECTIVE.create(window);
		register(resetPerspectiveAction);

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
		menu.add(new GroupMarker("help-primary-additions-start")); //$NON-NLS-1$
		menu.add(new GroupMarker("help-primary-additions-end")); //$NON-NLS-1$
		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		menu.add(new Separator());
		menu.add(aboutaction);
		return menu;
	}

	private MenuManager createExtraMenu() {
		MenuManager menu = new MenuManager(
				org.eclipse.remus.application.messages.IDEWorkbenchMessages.ApplicationActionBarAdvisor_Extra,
				IWorkbenchActionConstants.M_WINDOW);
		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		menu.add(new Separator());
		menu.add(cleanAction);
		menu.add(resetPerspectiveAction);
		menu.add(new Separator());
		menu.add(preferenceAction);
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
			newWizardMenu = new NewWizardMenu(getWindow());
			newMenu.add(newWizardMenu);
			newMenu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
			menu.add(newMenu);
		}

		menu.add(new GroupMarker(IWorkbenchActionConstants.NEW_EXT));
		menu.add(new Separator());

		menu.add(closeAction);
		menu.add(closeAllAction);
		// menu.add(closeAllSavedAction);
		menu.add(new GroupMarker(IWorkbenchActionConstants.CLOSE_EXT));
		menu.add(new Separator());
		menu.add(saveAction);
		menu.add(saveAsAction);
		menu.add(saveAllAction);
		menu.add(new Separator());
		menu.add(getMoveItem());
		menu.add(getRenameItem());

		menu.add(new GroupMarker(IWorkbenchActionConstants.SAVE_EXT));
		menu.add(new Separator());
		menu.add(getPrintItem());
		menu.add(new GroupMarker(IWorkbenchActionConstants.PRINT_EXT));
		menu.add(new Separator());
		menu.add(openWorkspaceAction);
		menu.add(new GroupMarker(IWorkbenchActionConstants.OPEN_EXT));
		menu.add(new Separator());
		menu.add(importResourcesAction);
		menu.add(exportResourcesAction);
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
		ActionContributionItem quitItem = new ActionContributionItem(quitAction);
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

		menu.add(undoAction);
		menu.add(redoAction);
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
		return getItem(
				ActionFactory.CUT.getId(),
				"org.eclipse.ui.edit.cut", //$NON-NLS-1$
				ISharedImages.IMG_TOOL_CUT,
				ISharedImages.IMG_TOOL_CUT_DISABLED,
				WorkbenchMessages.Workbench_cut,
				WorkbenchMessages.Workbench_cutToolTip, null);
	}

	private IContributionItem getCopyItem() {
		return getItem(
				ActionFactory.COPY.getId(),
				"org.eclipse.ui.edit.copy", //$NON-NLS-1$
				ISharedImages.IMG_TOOL_COPY,
				ISharedImages.IMG_TOOL_COPY_DISABLED,
				WorkbenchMessages.Workbench_copy,
				WorkbenchMessages.Workbench_copyToolTip, null);
	}

	private IContributionItem getPasteItem() {
		return getItem(
				ActionFactory.PASTE.getId(),
				"org.eclipse.ui.edit.paste", ISharedImages.IMG_TOOL_PASTE, //$NON-NLS-1$
				ISharedImages.IMG_TOOL_PASTE_DISABLED,
				WorkbenchMessages.Workbench_paste,
				WorkbenchMessages.Workbench_pasteToolTip, null);
	}

	private IContributionItem getPrintItem() {
		return getItem(
				ActionFactory.PRINT.getId(),
				"org.eclipse.ui.file.print", ISharedImages.IMG_ETOOL_PRINT_EDIT, //$NON-NLS-1$
				ISharedImages.IMG_ETOOL_PRINT_EDIT_DISABLED,
				WorkbenchMessages.Workbench_print,
				WorkbenchMessages.Workbench_printToolTip, null);
	}

	private IContributionItem getSelectAllItem() {
		return getItem(
				ActionFactory.SELECT_ALL.getId(),
				"org.eclipse.ui.edit.selectAll", //$NON-NLS-1$
				null, null, WorkbenchMessages.Workbench_selectAll,
				WorkbenchMessages.Workbench_selectAllToolTip, null);
	}

	private IContributionItem getFindItem() {
		return getItem(
				ActionFactory.FIND.getId(),
				"org.eclipse.ui.edit.findReplace", //$NON-NLS-1$
				null, null, WorkbenchMessages.Workbench_findReplace,
				WorkbenchMessages.Workbench_findReplaceToolTip, null);
	}

	private IContributionItem getDeleteItem() {
		return getItem(
				ActionFactory.DELETE.getId(),
				"org.eclipse.ui.edit.delete", //$NON-NLS-1$
				ISharedImages.IMG_TOOL_DELETE,
				ISharedImages.IMG_TOOL_DELETE_DISABLED,
				WorkbenchMessages.Workbench_delete,
				WorkbenchMessages.Workbench_deleteToolTip,
				IWorkbenchHelpContextIds.DELETE_RETARGET_ACTION);
	}

	private IContributionItem getMoveItem() {
		return getItem(ActionFactory.MOVE.getId(),
				"org.eclipse.ui.edit.move", //$NON-NLS-1$
				null, null, WorkbenchMessages.Workbench_move,
				WorkbenchMessages.Workbench_moveToolTip, null);
	}

	private IContributionItem getRenameItem() {
		return getItem(ActionFactory.RENAME.getId(),
				"org.eclipse.ui.edit.rename", null, null, //$NON-NLS-1$
				WorkbenchMessages.Workbench_rename,
				WorkbenchMessages.Workbench_renameToolTip, null);
	}

	private IContributionItem getItem(final String actionId,
			final String commandId, final String image,
			final String disabledImage, final String label,
			final String tooltip, final String helpContextId) {
		ISharedImages sharedImages = getWindow().getWorkbench()
				.getSharedImages();

		IActionCommandMappingService acms = (IActionCommandMappingService) getWindow()
				.getService(IActionCommandMappingService.class);
		acms.map(actionId, commandId);

		CommandContributionItemParameter commandParm = new CommandContributionItemParameter(
				getWindow(), actionId, commandId, null,
				sharedImages.getImageDescriptor(image),
				sharedImages.getImageDescriptor(disabledImage), null, label,
				null, tooltip, CommandContributionItem.STYLE_PUSH, null, false);
		return new CommandContributionItem(commandParm);
	}

	private BundleContext getBundleById(final String id) {
		return Platform.getBundle(id).getBundleContext();
	}

}
