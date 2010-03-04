package org.remus.infomngmnt.ui.viewer.context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.BaseNewWizardMenu;
import org.eclipse.ui.internal.actions.NewWizardShortcutAction;
import org.eclipse.ui.internal.dialogs.WorkbenchWizardElement;
import org.eclipse.ui.wizards.IWizardDescriptor;

/**
 * A <code>NewWizardMenu</code> augments <code>BaseNewWizardMenu</code> with
 * IDE-specific actions: New Project... (always shown) and New Example... (shown
 * only if there are example wizards installed).
 */
public class NewWizardMenu extends BaseNewWizardMenu {

	private boolean enabled = true;

	/**
	 * Creates a new wizard shortcut menu for the IDE.
	 * 
	 * @param window
	 *            the window containing the menu
	 */
	public NewWizardMenu(final IWorkbenchWindow window) {
		this(window, null);

	}

	/**
	 * Creates a new wizard shortcut menu for the IDE.
	 * 
	 * @param window
	 *            the window containing the menu
	 * @param id
	 *            the identifier for this contribution item
	 */
	public NewWizardMenu(final IWorkbenchWindow window, final String id) {
		super(window, id);
	}

	/**
	 * Create a new wizard shortcut menu.
	 * <p>
	 * If the menu will appear on a semi-permanent basis, for instance within a
	 * toolbar or menubar, the value passed for <code>register</code> should be
	 * true. If set, the menu will listen to perspective activation and update
	 * itself to suit. In this case clients are expected to call
	 * <code>deregister</code> when the menu is no longer needed. This will
	 * unhook any perspective listeners.
	 * </p>
	 * 
	 * @param innerMgr
	 *            the location for the shortcut menu contents
	 * @param window
	 *            the window containing the menu
	 * @param register
	 *            if <code>true</code> the menu listens to perspective changes
	 *            in the window
	 * @deprecated use NewWizardMenu(IWorkbenchWindow) instead
	 */
	@Deprecated
	public NewWizardMenu(final IMenuManager innerMgr, final IWorkbenchWindow window,
			final boolean register) {
		this(window, null);
		fillMenu(innerMgr);
		// Must be done after constructor to ensure field initialization.
	}

	/*
	 * (non-Javadoc) Fills the menu with New Wizards.
	 */
	private void fillMenu(final IContributionManager innerMgr) {
		// Remove all.
		innerMgr.removeAll();

		IContributionItem[] items = getContributionItems();
		for (int i = 0; i < items.length; i++) {
			innerMgr.add(items[i]);
		}
	}

	/**
	 * Removes all listeners from the containing workbench window.
	 * <p>
	 * This method should only be called if the shortcut menu is created with
	 * <code>register = true</code>.
	 * </p>
	 * 
	 * @deprecated has no effect
	 */
	@Deprecated
	public void deregisterListeners() {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.actions.BaseNewWizardMenu#addItems(org.eclipse.jface.action
	 * .IContributionManager)
	 */
	@Override
	protected void addItems(final List list) {
		ArrayList shortCuts = new ArrayList();
		addShortcuts(shortCuts);

		for (Iterator iterator = shortCuts.iterator(); iterator.hasNext();) {
			Object curr = iterator.next();
			if (curr instanceof ActionContributionItem
					&& isNewProjectWizardAction(((ActionContributionItem) curr).getAction())) {
				iterator.remove();
				list.add(curr);
			}
		}
		list.add(new Separator());
		if (!shortCuts.isEmpty()) {
			list.addAll(shortCuts);
			list.add(new Separator());
		}

		list.add(new ActionContributionItem(getShowDialogAction()));
	}

	private boolean isNewProjectWizardAction(final IAction action) {
		if (action instanceof NewWizardShortcutAction) {
			IWizardDescriptor wizardDescriptor = ((NewWizardShortcutAction) action)
					.getWizardDescriptor();
			String[] tags = wizardDescriptor.getTags();
			for (int i = 0; i < tags.length; i++) {
				if (WorkbenchWizardElement.TAG_PROJECT.equals(tags[i])) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc) Method declared on IContributionItem.
	 */
	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	/**
	 * Sets the enabled state of the receiver.
	 * 
	 * @param enabledValue
	 *            if <code>true</code> the menu is enabled; else it is disabled
	 */
	public void setEnabled(final boolean enabledValue) {
		this.enabled = enabledValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.actions.BaseNewWizardMenu#getContributionItems()
	 */
	@Override
	protected IContributionItem[] getContributionItems() {
		if (isEnabled()) {
			return super.getContributionItems();
		}
		return new IContributionItem[0];
	}
}
