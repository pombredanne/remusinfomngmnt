package org.remus.infomngmnt.ui.views;


import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.ui.action.ControlAction;
import org.eclipse.emf.edit.ui.action.CopyAction;
import org.eclipse.emf.edit.ui.action.CutAction;
import org.eclipse.emf.edit.ui.action.DeleteAction;
import org.eclipse.emf.edit.ui.action.LoadResourceAction;
import org.eclipse.emf.edit.ui.action.PasteAction;
import org.eclipse.emf.edit.ui.action.ValidateAction;
import org.eclipse.jface.action.ActionContributionItem;
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
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.views.properties.IPropertySheetPage;


/**
 * This is a contributor for an editor, multi-page or otherwise,
 * that implements {@link IEditingDomainProvider}.
 * It automatically hooks up the Undo, Redo, Cut, Copy, Paste, and Delete actions on the Edit menu
 * to the corresponding commands supported by the {@link org.eclipse.emf.edit.domain.EditingDomain}.
 * The editor site'selection provider is used to keep the Cut, Copy, Paste, and Delete actions up-to-date.
 * The actions are also refreshed every time the editor fires to its {@link IPropertyListener}s.
 * <p>
 * Another very useful feature of this contributor is that it can be used as follows:
 * <pre>
 *   ((IMenuListener)((IEditorSite)getSite()).getActionBarContributor()).menuAboutToShow(menuManager);
 * </pre>
 * to contribute the Edit menu actions to a pop-up menu.
 */
public class NavigationContextMenu
implements
IMenuListener,
IPropertyListener,
ISelectionChangedListener
{
	/**
	 * This keeps track of the current editor part.
	 */
	protected IEditingDomainProvider activeEditor;

	/**
	 * This is the action used to implement delete.
	 */
	protected DeleteAction deleteAction;

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

	/**
	 * This is the action used to load a resource.
	 */
	protected LoadResourceAction loadResourceAction;

	/**
	 * This is the action used to control or uncontrol a contained object.
	 */
	protected ControlAction controlAction;

	/**
	 * This is the action used to perform validation.
	 */
	protected ValidateAction validateAction;

	/**
	 * This style bit indicates that the "additions" separator should come after the "edit" separator.
	 */
	public static final int ADDITIONS_LAST_STYLE = 0x1;


	/**
	 * This is the action that creates a pulldown with the available wizards.
	 */
	IWorkbenchAction create = ActionFactory.NEW_WIZARD_DROP_DOWN.create(PlatformUI.getWorkbench().getActiveWorkbenchWindow());

	/**
	 * This is used to encode the style bits.
	 */
	protected int style;

	private NewWizardMenu newWizardMenu;

	/**
	 * This creates an instance of the contributor.
	 */
	public NavigationContextMenu()
	{
		super();
	}

	/**
	 * This creates an instance of the contributor.
	 */
	public NavigationContextMenu(final int style)
	{
		super();
		this.style = style;
	}


	public void init(final IActionBars actionBars)
	{
		final ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();

		this.deleteAction = new DeleteAction(removeAllReferencesOnDelete());
		this.deleteAction.setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(), this.deleteAction);

		this.cutAction = new CutAction();
		this.cutAction.setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_CUT));
		actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(), this.cutAction);

		this.copyAction = new CopyAction();
		this.copyAction.setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(), this.copyAction);

		this.pasteAction = new PasteAction();
		this.pasteAction.setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(), this.pasteAction);

	}

	/**
	 * This determines whether or not the delete action should clean up all references to the deleted objects.
	 * It is false by default, to provide the same beahviour, by default, as in EMF 2.1 and before.
	 * You should probably override this method to return true, in order to get the new, more useful beahviour.
	 * @since 2.2
	 */
	protected boolean removeAllReferencesOnDelete()
	{
		return true;
	}



	public void shareGlobalActions(final IPage page, final IActionBars actionBars)
	{
		if (!(page instanceof IPropertySheetPage))
		{
			actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(), this.deleteAction);
			actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(), this.cutAction);
			actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(), this.copyAction);
			actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(), this.pasteAction);
		}

	}


	public void setActiveDomain(final IEditingDomainProvider domain)
	{
		if (domain != this.activeEditor)
		{
			if (this.activeEditor != null)
			{
				deactivate();
			}

			if (domain instanceof IEditingDomainProvider)
			{
				this.activeEditor = domain;
				activate();

			}
		}
	}



	public void deactivate()
	{
		this.deleteAction.setActiveWorkbenchPart(null);
		this.cutAction.setActiveWorkbenchPart(null);
		this.copyAction.setActiveWorkbenchPart(null);
		this.pasteAction.setActiveWorkbenchPart(null);

		if (this.loadResourceAction != null)
		{
			this.loadResourceAction.setActiveWorkbenchPart(null);
		}

		if (this.controlAction != null)
		{
			this.controlAction.setActiveWorkbenchPart(null);
		}

		if (this.validateAction != null)
		{
			this.validateAction.setActiveWorkbenchPart(null);
		}

		final ISelectionProvider selectionProvider =
			this.activeEditor instanceof ISelectionProvider ?
					(ISelectionProvider)this.activeEditor :
						null ;

					if (selectionProvider != null)
					{
						selectionProvider.removeSelectionChangedListener(this.deleteAction);
						selectionProvider.removeSelectionChangedListener(this.cutAction);
						selectionProvider.removeSelectionChangedListener(this.copyAction);
						selectionProvider.removeSelectionChangedListener(this.pasteAction);

						if (this.validateAction != null)
						{
							selectionProvider.removeSelectionChangedListener(this.validateAction);
						}

						if (this.controlAction != null)
						{
							selectionProvider.removeSelectionChangedListener(this.controlAction);
						}
					}
	}

	public void activate()
	{
		this.deleteAction.setActiveWorkbenchPart((IWorkbenchPart) this.activeEditor);
		this.cutAction.setActiveWorkbenchPart((IWorkbenchPart) this.activeEditor);
		this.copyAction.setActiveWorkbenchPart((IWorkbenchPart) this.activeEditor);
		this.pasteAction.setActiveWorkbenchPart((IWorkbenchPart) this.activeEditor);

		if (this.loadResourceAction != null)
		{
			this.loadResourceAction.setActiveWorkbenchPart((IWorkbenchPart) this.activeEditor);
		}

		if (this.controlAction != null)
		{
			this.controlAction.setActiveWorkbenchPart((IWorkbenchPart) this.activeEditor);
		}

		if (this.validateAction != null)
		{
			this.validateAction.setActiveWorkbenchPart((IWorkbenchPart) this.activeEditor);
		}

		final ISelectionProvider selectionProvider =
			this.activeEditor instanceof ISelectionProvider ?
					(ISelectionProvider)this.activeEditor :
						null;

					if (selectionProvider != null)
					{
						selectionProvider.addSelectionChangedListener(this.deleteAction);
						selectionProvider.addSelectionChangedListener(this.cutAction);
						selectionProvider.addSelectionChangedListener(this.copyAction);
						selectionProvider.addSelectionChangedListener(this.pasteAction);

						if (this.validateAction != null)
						{
							selectionProvider.addSelectionChangedListener(this.validateAction);
						}

						if (this.controlAction != null)
						{
							selectionProvider.addSelectionChangedListener(this.controlAction);
						}
					}

					update();
	}

	public void update()
	{
		final ISelectionProvider selectionProvider =
			this.activeEditor instanceof ISelectionProvider ?
					(ISelectionProvider)this.activeEditor :
						null;

					if (selectionProvider != null)
					{
						final ISelection selection = selectionProvider.getSelection();
						final IStructuredSelection structuredSelection =
							selection instanceof IStructuredSelection ?  (IStructuredSelection)selection : StructuredSelection.EMPTY;

							this.deleteAction.updateSelection(structuredSelection);
							this.cutAction.updateSelection(structuredSelection);
							this.copyAction.updateSelection(structuredSelection);
							this.pasteAction.updateSelection(structuredSelection);

							if (this.validateAction != null)
							{
								this.validateAction.updateSelection(structuredSelection);
							}

							if (this.controlAction != null)
							{
								this.controlAction.updateSelection(structuredSelection);
							}
					}


					if (this.loadResourceAction != null)
					{
						this.loadResourceAction.update();
					}
	}

	/**
	 * This implements {@link org.eclipse.jface.action.IMenuListener} to help fill the context menus with contributions from the Edit menu.
	 */
	public void menuAboutToShow(final IMenuManager menuManager)
	{
		// Add our standard marker.
		//
		if ((this.style & ADDITIONS_LAST_STYLE) == 0)
		{
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
		//menuManager.add(new Separator(ActionFactory.NEW.getId()));
		menuManager.add(new Separator("edit"));

		// Add the edit menu actions.
		//

		menuManager.add(new ActionContributionItem(this.cutAction));
		menuManager.add(new ActionContributionItem(this.copyAction));
		menuManager.add(new ActionContributionItem(this.pasteAction));
		menuManager.add(new Separator());
		menuManager.add(new ActionContributionItem(this.deleteAction));
		menuManager.add(new Separator());

		if ((this.style & ADDITIONS_LAST_STYLE) != 0)
		{
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
	protected void addGlobalActions(final IMenuManager menuManager)
	{
		final String key = (this.style & ADDITIONS_LAST_STYLE) == 0 ? "additions-end" : "additions";
		if (this.validateAction != null)
		{
			menuManager.insertBefore(key, new ActionContributionItem(this.validateAction));
		}

		if (this.controlAction != null)
		{
			menuManager.insertBefore(key, new ActionContributionItem(this.controlAction));
		}

		if (this.validateAction != null || this.controlAction != null)
		{
			menuManager.insertBefore(key, new Separator());
		}

		if (this.loadResourceAction != null)
		{
			menuManager.insertBefore("additions-end", new ActionContributionItem(this.loadResourceAction));
			menuManager.insertBefore("additions-end", new Separator());
		}
	}

	public void propertyChanged(final Object source, final int id)
	{
		update();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	public void selectionChanged(final SelectionChangedEvent event) {
		update();

	}
}
