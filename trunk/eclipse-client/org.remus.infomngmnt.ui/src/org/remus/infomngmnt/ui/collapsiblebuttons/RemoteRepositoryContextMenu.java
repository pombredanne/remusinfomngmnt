package org.remus.infomngmnt.ui.collapsiblebuttons;


import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.ui.action.DeleteAction;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.SubContributionItem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.views.properties.IPropertySheetPage;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.common.core.util.ModelUtil;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.remote.IRepositoryActionContributor;
import org.remus.infomngmnt.ui.service.IRepositoryExtensionService;


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
public class RemoteRepositoryContextMenu
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
	 * This style bit indicates that the "additions" separator should come after the "edit" separator.
	 */
	public static final int ADDITIONS_LAST_STYLE = 0x1;



	/**
	 * This is used to encode the style bits.
	 */
	protected int style;

	protected IMenuManager checkOutAsChildsMenuManager;

	/**
	 * This will contain one {@link org.eclipse.emf.edit.ui.action.CreateChildAction} corresponding to each descriptor
	 * generated for the current selection by the item provider.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection<IAction> checkOutAsActions;

	private RefreshRemoteObjectAction refreshAction;



	/**
	 * This creates an instance of the contributor.
	 */
	public RemoteRepositoryContextMenu()
	{
		super();
	}

	/**
	 * This creates an instance of the contributor.
	 */
	public RemoteRepositoryContextMenu(final int style)
	{
		super();
		this.style = style;
	}


	public void init(final IActionBars actionBars)
	{
		final ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();


		this.deleteAction = new DeleteAction(removeAllReferencesOnDelete());
		this.deleteAction.setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		this.deleteAction.setActionDefinitionId("org.eclipse.ui.edit.delete");
		this.refreshAction = new RefreshRemoteObjectAction();
		actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(), this.deleteAction);




		actionBars.updateActionBars();
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
			actionBars.setGlobalActionHandler(ActionFactory.REFRESH.getId(), this.refreshAction);
			
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
		this.deleteAction.setEditingDomain(null);

		final ISelectionProvider selectionProvider =
			this.activeEditor instanceof ISelectionProvider ?
					(ISelectionProvider)this.activeEditor :
						null ;

					if (selectionProvider != null)
					{
						selectionProvider.removeSelectionChangedListener(this.deleteAction);
						selectionProvider.removeSelectionChangedListener(this.refreshAction);
					}
	}

	public void activate()
	{
		this.deleteAction.setEditingDomain(this.activeEditor.getEditingDomain());
		this.refreshAction.setViewer((StructuredViewer) ((IViewerProvider) this.activeEditor).getViewer());


		final ISelectionProvider selectionProvider =
			this.activeEditor instanceof ISelectionProvider ?
					(ISelectionProvider)this.activeEditor :
						null;

					if (selectionProvider != null)
					{
						selectionProvider.addSelectionChangedListener(this.deleteAction);
						selectionProvider.addSelectionChangedListener(this.refreshAction);
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
							this.refreshAction.updateSelection(structuredSelection);
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
		String newText = "Check out as...";
		String newId = "org.remus.infomngmnt.remote.checkout";
		this.checkOutAsChildsMenuManager = new MenuManager(newText, newId);
		this.checkOutAsChildsMenuManager.add(new Separator(newId));
		populateManager(this.checkOutAsChildsMenuManager, this.checkOutAsActions, null);


		this.checkOutAsChildsMenuManager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		menuManager.add(this.checkOutAsChildsMenuManager);
		//menuManager.add(new Separator(ActionFactory.NEW.getId()));
		menuManager.add(new Separator("edit"));

		// Add the edit menu actions.
		//
		menuManager.add(new Separator());
		menuManager.add(this.refreshAction);
		menuManager.add(new Separator());
		menuManager.add(this.deleteAction);
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
		if (this.checkOutAsChildsMenuManager != null) {
			depopulateManager(this.checkOutAsChildsMenuManager, this.checkOutAsActions);
		}
		this.checkOutAsActions = generateCheckoutActions((IStructuredSelection) event.getSelection());
		if (this.checkOutAsChildsMenuManager != null) {
			populateManager(this.checkOutAsChildsMenuManager, this.checkOutAsActions, null);
			this.checkOutAsChildsMenuManager.update(true);
		}
	}

	private Collection<IAction> generateCheckoutActions(final IStructuredSelection selection) {
		if (!selection.isEmpty() &&
				ModelUtil.hasEqualAttribute(selection.toList(), InfomngmntPackage.Literals.REMOTE_OBJECT__REPOSITORY_TYPE_ID)) {
			String repositoryId = (String) ((EObject) selection.toList().get(0)).eGet(InfomngmntPackage.Literals.REMOTE_OBJECT__REPOSITORY_TYPE_ID);
			IRepositoryActionContributor actionContributor = UIPlugin.getDefault().getService(IRepositoryExtensionService.class).getItemByRepositoryId(repositoryId).getActionContributor();
			return Arrays.asList(actionContributor.createCheckOutAsActions(selection));
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * This removes from the specified <code>manager</code> all {@link org.eclipse.jface.action.ActionContributionItem}s
	 * based on the {@link org.eclipse.jface.action.IAction}s contained in the <code>actions</code> collection.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void depopulateManager(final IContributionManager manager, final Collection<? extends IAction> actions) {
		if (actions != null) {
			IContributionItem[] items = manager.getItems();
			for (int i = 0; i < items.length; i++) {
				// Look into SubContributionItems
				//
				IContributionItem contributionItem = items[i];
				while (contributionItem instanceof SubContributionItem) {
					contributionItem = ((SubContributionItem)contributionItem).getInnerItem();
				}

				// Delete the ActionContributionItems with matching action.
				//
				if (contributionItem instanceof ActionContributionItem) {
					IAction action = ((ActionContributionItem)contributionItem).getAction();
					if (actions.contains(action)) {
						manager.remove(contributionItem);
					}
				}
			}
		}
	}


	/**
	 * This populates the specified <code>manager</code> with {@link org.eclipse.jface.action.ActionContributionItem}s
	 * based on the {@link org.eclipse.jface.action.IAction}s contained in the <code>actions</code> collection,
	 * by inserting them before the specified contribution item <code>contributionID</code>.
	 * If <code>contributionID</code> is <code>null</code>, they are simply added.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void populateManager(final IContributionManager manager, final Collection<? extends IAction> actions, final String contributionID) {
		if (actions != null) {
			for (IAction action : actions) {
				if (contributionID != null) {
					manager.insertBefore(contributionID, action);
				}
				else {
					manager.add(action);
				}
			}
		}
	}


}
