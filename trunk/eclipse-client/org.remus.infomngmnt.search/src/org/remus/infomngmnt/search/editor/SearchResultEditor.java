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

package org.remus.infomngmnt.search.editor;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.SharedHeaderFormEditor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.search.SearchPackage;
import org.remus.infomngmnt.search.provider.SearchPlugin;
import org.remus.infomngmnt.search.save.SavedSearchesHandler;
import org.remus.infomngmnt.search.ui.view.SearchOutline;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SearchResultEditor extends SharedHeaderFormEditor {

	private SearchResultPage page1;
	private Search model;
	private SearchOutline contentOutlinePage;

	final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //$NON-NLS-1$


	/**
	 * 
	 */
	public SearchResultEditor() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormEditor#addPages()
	 */
	@Override
	protected void addPages() {
		try {
			addPage(this.page1 = new SearchResultPage(this,this.model));
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void createPages()
	{
		super.createPages();
		if ( getPageCount() == 1 && getContainer() instanceof CTabFolder )
		{
			( (CTabFolder) getContainer() ).setTabHeight( 0 );
		}
	}

	@Override
	protected void createHeaderContents(IManagedForm headerForm) {
		headerForm.getForm().setText(getTitleLabel());
		headerForm.getForm().setImage(AbstractUIPlugin.imageDescriptorFromPlugin(SearchPlugin.PLUGIN_ID, "images/text_find.png").createImage());
		getToolkit().decorateFormHeading(headerForm.getForm().getForm());
		//headerForm.getForm().getToolBarManager().add(action)
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		// save not allowed.

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
		// does nothing

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
	throws PartInitException {
		if (input instanceof URIEditorInput) {
			this.model = new SavedSearchesHandler().getObjectFromUri(((URIEditorInput)input).getURI(), SearchPackage.Literals.SEARCH);
		}
		setPartName(getTitleLabel());
		super.init(site, input);

	}

	@Override
	public String getTitleToolTip() {
		return getTitle();
	}

	private String getTitleLabel() {
		String returnValue = this.model.getSearchString()== null || this.model.getSearchString().length() == 0 ? "Empty" : "\'" + this.model.getSearchString() + "\'";
		returnValue += " \u00AD " + SDF.format(new Date(Long.parseLong(this.model.getId())));
		returnValue += " \u00AD " + "Search results";
		return returnValue;
	}

	@Override
	public Object getAdapter(Class adapter) {
		if (adapter.equals(IContentOutlinePage.class)) {
			return getContentOutline();
		}
		return super.getAdapter(adapter);
	}

	public ContentOutlinePage getContentOutline() {
		if (this.contentOutlinePage == null) {
			this.contentOutlinePage = new SearchOutline(this.model) {
				@Override
				public void setActionBars(IActionBars actionBars) {
					super.setActionBars(actionBars);
					//getActionBarContributor().shareGlobalActions(this, actionBars);
				}
			};
		}
		return this.contentOutlinePage;
	}


}
