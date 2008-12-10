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

package org.remus.infomngmnt.search.context;

import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;

import org.remus.infomngmnt.common.ui.editor.EditorUtil;
import org.remus.infomngmnt.search.SearchResult;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SearchContextProvider {

	public static final String CONTEXT_ID = "org.remus.infomngmnt.search.searchcontext"; //$NON-NLS-1$

	private IContextService contextService;

	private IContextActivation contextActivation;

	private EList<SearchResult> searchResultList;

	private int index;


	public void activate(EList<SearchResult> list, int index) {
		deactivate();
		this.searchResultList = list;
		this.index = index;
		this.contextActivation = getContextService().activateContext(CONTEXT_ID);
	}

	public boolean isActivated() {
		return this.contextActivation != null;
	}

	public void deactivate() {
		if (isActivated()) {
			getContextService().deactivateContext(this.contextActivation);
		}
	}

	private IContextService getContextService() {
		if (this.contextService == null) {
			this.contextService = (IContextService) PlatformUI.getWorkbench().getService(IContextService.class);
		}
		return this.contextService;
	}

	public void openNextResult() {
		if (isActivated()) {
			this.index = (this.index + 1) % this.searchResultList.size();
			EditorUtil.openEditor(new Path(this.searchResultList.get(this.index).getPath()));
		}
	}

	public void openPreviousResult() {
		if (isActivated()) {
			this.index = Math.abs((this.index - 1) % this.searchResultList.size());
			EditorUtil.openEditor(new Path(this.searchResultList.get(this.index).getPath()));
		}
	}

}
