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

package org.remus.infomngmnt.search.ui.editor;

import java.text.DateFormat;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.SharedHeaderFormEditor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.search.SearchPackage;
import org.remus.infomngmnt.search.save.SavedSearchesHandler;
import org.remus.infomngmnt.search.service.IFavoriteSearchHandler;
import org.remus.infomngmnt.search.ui.SearchUIActivator;
import org.remus.infomngmnt.search.ui.view.SearchOutline;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SearchResultEditor extends SharedHeaderFormEditor {

	private SearchResultPage page1;
	private Search model;
	private SearchOutline contentOutlinePage;

	public final static DateFormat SDF = DateFormat.getDateTimeInstance(DateFormat.SHORT,
			DateFormat.FULL);
	private static final String CONTEXT_ID = "org.remus.infomngmnt.search.searchEditorContext";

	protected IPartListener partListener = new IPartListener() {
		public void partActivated(final IWorkbenchPart p) {
			if (p == SearchResultEditor.this) {
				handleActivate();
			}
		}

		public void partBroughtToTop(final IWorkbenchPart p) {
			// Ignore.
		}

		public void partClosed(final IWorkbenchPart p) {
			// Ignore.
		}

		public void partDeactivated(final IWorkbenchPart p) {
			if (p == SearchResultEditor.this) {
				handleDeActivate();
			}
		}

		public void partOpened(final IWorkbenchPart p) {
			// Ignore.
		}
	};
	private IContextService contextService;
	private IContextActivation contextActivation;

	/**
	 * 
	 */
	public SearchResultEditor() {
		// TODO Auto-generated constructor stub
	}

	protected void handleDeActivate() {
		if (this.contextActivation != null) {
			getContextService().deactivateContext(this.contextActivation);
		}

	}

	protected void handleActivate() {
		this.contextActivation = getContextService().activateContext(CONTEXT_ID);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.editor.FormEditor#addPages()
	 */
	@Override
	protected void addPages() {
		try {
			addPage(this.page1 = new SearchResultPage(this, this.model));
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void createPages() {
		super.createPages();
		if (getPageCount() == 1 && getContainer() instanceof CTabFolder) {
			((CTabFolder) getContainer()).setTabHeight(0);
		}
	}

	@Override
	protected void createHeaderContents(final IManagedForm headerForm) {
		headerForm.getForm().setText(getTitleLabel());
		headerForm.getForm().setImage(
				AbstractUIPlugin.imageDescriptorFromPlugin(SearchUIActivator.PLUGIN_ID,
						"images/text_find.png").createImage());
		getToolkit().decorateFormHeading(headerForm.getForm().getForm());
		headerForm.getForm().getToolBarManager().add(new Action("Add to watched searches") {
			@Override
			public void run() {
				IFavoriteSearchHandler service = SearchUIActivator.getDefault().getServiceTracker()
						.getService(IFavoriteSearchHandler.class);
				if (service == null) {
					MessageDialog.openWarning(getSite().getShell(), "No handler found",
							"No handler for creating watched searches found.");
				} else {
					service.addToFavorites(SearchResultEditor.this.model);
				}

			}

			@Override
			public ImageDescriptor getImageDescriptor() {
				return ResourceManager.getPluginImageDescriptor(SearchUIActivator.getDefault(),
						"icons/iconexperience/star_blue.png");
			}
		});
		IContributionItem item = UIUtil.getCommandContribution(
				"org.remus.infomngmnt.search.searchAgain", ResourceManager
						.getPluginImageDescriptor(SearchUIActivator.getDefault(),
								"icons/iconexperience/nav_refresh_blue.png"), null, null, null,
				null);
		headerForm.getForm().getToolBarManager().add(item);
		headerForm.getForm().getToolBarManager().update(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	@Override
	public void doSave(final IProgressMonitor monitor) {
		// save not allowed.

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
		// does nothing

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		if (input instanceof URIEditorInput) {
			this.model = new SavedSearchesHandler().getObjectFromUri(((URIEditorInput) input)
					.getURI(), SearchPackage.Literals.SEARCH);
		}
		site.getPage().addPartListener(this.partListener);
		setPartName(getTitleLabel());
		super.init(site, input);

	}

	@Override
	public String getTitleToolTip() {
		return getTitle();
	}

	private String getTitleLabel() {
		String returnValue;
		if (this.model.isIdSearch()) {
			returnValue = "Predefined search";
		} else {
			returnValue = this.model.getSearchString() == null
					|| this.model.getSearchString().length() == 0 ? "Empty" : "\'"
					+ this.model.getSearchString() + "\'";
			// returnValue += " \u00AD " + SDF.format(new
			// Date(Long.parseLong(this.model.getId())));
			returnValue += " \u00AD " + "Search results";
		}
		return returnValue;
	}

	@Override
	public Object getAdapter(final Class adapter) {
		if (adapter.equals(IContentOutlinePage.class)) {
			return getContentOutline();
		}
		return super.getAdapter(adapter);
	}

	public ContentOutlinePage getContentOutline() {
		if (this.contentOutlinePage == null) {
			this.contentOutlinePage = new SearchOutline(this.model) {
				@Override
				public void setActionBars(final IActionBars actionBars) {
					super.setActionBars(actionBars);
					// getActionBarContributor().shareGlobalActions(this,
					// actionBars);
				}
			};
		}
		return this.contentOutlinePage;
	}

	private IContextService getContextService() {
		if (this.contextService == null) {
			this.contextService = (IContextService) PlatformUI.getWorkbench().getService(
					IContextService.class);
		}
		return this.contextService;
	}

	@Override
	public void dispose() {
		getSite().getPage().removePartListener(this.partListener);
		super.dispose();
	}

}
