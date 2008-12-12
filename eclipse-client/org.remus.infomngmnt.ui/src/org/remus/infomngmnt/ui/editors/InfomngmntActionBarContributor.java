/**
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * 
 * Contributors:
 *      Tom Seidel - initial API and implementation
 * 
 *
 * $Id$
 */
package org.remus.infomngmnt.ui.editors;

import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.ui.action.DeleteAction;
import org.eclipse.emf.edit.ui.action.RedoAction;
import org.eclipse.emf.edit.ui.action.UndoAction;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;
import org.eclipse.ui.texteditor.IUpdate;
import org.eclipse.ui.views.properties.PropertySheet;


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
public class InfomngmntActionBarContributor
extends
MultiPageEditorActionBarContributor
implements
IMenuListener,
IPropertyListener
{

	class GlobalAction extends Action implements IUpdate {
		private final String id;

		public GlobalAction(String id) {
			this.id = id;
		}

		@Override
		public void run() {
			InfomngmntActionBarContributor.this.activeEditor.performGlobalAction(this.id);
			updateSelectableActions(InfomngmntActionBarContributor.this.activeEditor.getSelection());
		}

		public void update() {
			getActionBars().updateActionBars();
		}
	}

	class ClipboardAction extends GlobalAction {
		public ClipboardAction(String id) {
			super(id);
			setEnabled(false);
		}

		public void selectionChanged(ISelection selection) {
		}


	}

	class CutAction extends ClipboardAction {
		public CutAction() {
			super(ActionFactory.CUT.getId());
			setText("CUT");

			setActionDefinitionId("org.eclipse.ui.edit.cut"); //$NON-NLS-1$
		}

		@Override
		public void selectionChanged(ISelection selection) {
			setEnabled(isEditable() && InfomngmntActionBarContributor.this.activeEditor.canCut(selection));
		}
	}

	class CopyAction extends ClipboardAction {
		public CopyAction() {
			super(ActionFactory.COPY.getId());
			setText("COPY");
			setActionDefinitionId("org.eclipse.ui.edit.copy"); //$NON-NLS-1$
		}

		@Override
		public void selectionChanged(ISelection selection) {
			setEnabled(InfomngmntActionBarContributor.this.activeEditor.canCopy(selection));
		}
	}

	class PasteAction extends ClipboardAction {
		public PasteAction() {
			super(ActionFactory.PASTE.getId());
			setText("PASTE");
			setActionDefinitionId("org.eclipse.ui.edit.paste"); //$NON-NLS-1$
		}

		@Override
		public void selectionChanged(ISelection selection) {
			setEnabled(isEditable() && InfomngmntActionBarContributor.this.activeEditor.canPasteFromClipboard());
			System.out.println(isEnabled());
		}
	}

	protected IFormPage fPage;

	/**
	 * This keeps track of the current editor part.
	 */
	protected InformationEditor activeEditor;

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
	 * This is the action used to implement undo.
	 */
	protected UndoAction undoAction;

	/**
	 * This is the action used to implement redo.
	 */
	protected RedoAction redoAction;



	/**
	 * This style bit indicates that the "additions" separator should come after the "edit" separator.
	 */
	public static final int ADDITIONS_LAST_STYLE = 0x1;

	/**
	 * This is used to encode the style bits.
	 */
	protected int style;

	/**
	 * This creates an instance of the contributor.
	 */
	public InfomngmntActionBarContributor()
	{
		super();
	}

	public void updateSelectableActions(ISelection selection) {
		if (this.activeEditor != null) {
			this.cutAction.selectionChanged(selection);
			this.copyAction.selectionChanged(selection);
			this.pasteAction.selectionChanged(selection);
		}

	}

	public boolean isEditable() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * This creates an instance of the contributor.
	 */
	public InfomngmntActionBarContributor(int style)
	{
		super();
		this.style = style;
	}

	@Override
	public void init(IActionBars actionBars)
	{
		super.init(actionBars);
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();

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

		this.undoAction = new UndoAction();
		this.undoAction.setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_UNDO));
		actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(), this.undoAction);

		this.redoAction = new RedoAction();
		this.redoAction.setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_REDO));
		actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(), this.redoAction);
	}

	/**
	 * This determines whether or not the delete action should clean up all references to the deleted objects.
	 * It is false by default, to provide the same beahviour, by default, as in EMF 2.1 and before.
	 * You should probably override this method to return true, in order to get the new, more useful beahviour.
	 * @since 2.2
	 */
	protected boolean removeAllReferencesOnDelete()
	{
		return false;
	}

	@Override
	public void contributeToMenu(IMenuManager menuManager)
	{
		super.contributeToMenu(menuManager);
	}

	@Override
	public void contributeToStatusLine(IStatusLineManager statusLineManager)
	{
		super.contributeToStatusLine(statusLineManager);
	}

	@Override
	public void contributeToToolBar(IToolBarManager toolBarManager)
	{
		super.contributeToToolBar(toolBarManager);
	}

	public void shareGlobalActions(IPage page, IActionBars actionBars)
	{
		if ((page instanceof InformationEditor))
		{
			actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(), this.deleteAction);
			actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(), this.cutAction);
			actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(), this.copyAction);
			actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(), this.pasteAction);
		}
		actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(), this.undoAction);
		actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(), this.redoAction);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public void setActiveView(IViewPart part)
	{
		IActionBars actionBars = part.getViewSite().getActionBars();
		if (!(part instanceof PropertySheet))
		{
			actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(), this.deleteAction);
			actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(), this.cutAction);
			actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(), this.copyAction);
			actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(), this.pasteAction);
		}
		actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(), this.undoAction);
		actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(), this.redoAction);

		actionBars.updateActionBars();
	}

	public IEditorPart getActiveEditor()
	{
		return this.activeEditor;
	}

	@Override
	public void setActiveEditor(IEditorPart part)
	{
		super.setActiveEditor(part);

		if (part != this.activeEditor)
		{
			if (this.activeEditor != null)
			{
				deactivate();
			}

			if (part instanceof InformationEditor)
			{
				this.activeEditor = (InformationEditor) part;
				activate();

			}
		}
	}

	@Override
	public void setActivePage(IEditorPart part)
	{


	}

	public void deactivate()
	{
		this.activeEditor.removePropertyListener(this);
		this.deleteAction.setActiveWorkbenchPart(null);
		this.undoAction.setActiveWorkbenchPart(null);
		this.redoAction.setActiveWorkbenchPart(null);

		ISelectionProvider selectionProvider =
			this.activeEditor instanceof ISelectionProvider ?
					(ISelectionProvider)this.activeEditor :
						this.activeEditor.getEditorSite().getSelectionProvider();

					if (selectionProvider != null)
					{
						selectionProvider.removeSelectionChangedListener(this.deleteAction);
					}
	}

	public void activate()
	{
		this.activeEditor.addPropertyListener(this);

		this.deleteAction.setActiveWorkbenchPart(this.activeEditor);
		this.undoAction.setActiveWorkbenchPart(this.activeEditor);
		this.redoAction.setActiveWorkbenchPart(this.activeEditor);



		ISelectionProvider selectionProvider =
			this.activeEditor instanceof ISelectionProvider ?
					(ISelectionProvider)this.activeEditor :
						this.activeEditor.getEditorSite().getSelectionProvider();

					if (selectionProvider != null)
					{
						selectionProvider.addSelectionChangedListener(this.deleteAction);
					}

					update();
	}

	public void update()
	{
		ISelectionProvider selectionProvider =
			this.activeEditor instanceof ISelectionProvider ?
					(ISelectionProvider)this.activeEditor :
						this.activeEditor.getEditorSite().getSelectionProvider();

					if (selectionProvider != null)
					{
						ISelection selection = selectionProvider.getSelection();
						IStructuredSelection structuredSelection =
							selection instanceof IStructuredSelection ?  (IStructuredSelection)selection : StructuredSelection.EMPTY;
							this.deleteAction.updateSelection(structuredSelection);
							updateSelectableActions(selection);
					}

					this.undoAction.update();
					this.redoAction.update();


	}

	/**
	 * This implements {@link org.eclipse.jface.action.IMenuListener} to help fill the context menus with contributions from the Edit menu.
	 */
	public void menuAboutToShow(IMenuManager menuManager)
	{
		// Add our standard marker.
		//
		if ((this.style & ADDITIONS_LAST_STYLE) == 0)
		{
			menuManager.add(new Separator("additions"));
		}
		menuManager.add(new Separator("edit"));

		// Add the edit menu actions.
		//
		menuManager.add(this.undoAction);
		menuManager.add(this.redoAction);
		menuManager.add(new Separator());
		menuManager.add(this.cutAction);
		menuManager.add(this.copyAction);
		menuManager.add(this.pasteAction);
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
		menuManager.add(new Separator("additions-end"));

		addGlobalActions(menuManager);
	}

	/**
	 * This inserts global actions before the "additions-end" separator.
	 */
	protected void addGlobalActions(IMenuManager menuManager)
	{
		String key = (this.style & ADDITIONS_LAST_STYLE) == 0 ? "additions-end" : "additions";

	}

	public void propertyChanged(Object source, int id)
	{
		update();
	}


}
