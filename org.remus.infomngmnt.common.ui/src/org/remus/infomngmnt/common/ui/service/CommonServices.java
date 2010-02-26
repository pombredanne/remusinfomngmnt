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

package org.remus.infomngmnt.common.ui.service;


/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CommonServices {

	private static CommonServices INSTANCE;

	public static CommonServices getInstance() {
		if (INSTANCE == null) {
			synchronized (CommonServices.class) {
				if (INSTANCE == null) {
					INSTANCE = new CommonServices();
				}
			}
		}
		return INSTANCE;
	}

	private LocalTrayActionService localTrayActionService;
	private ToolbarContributionExtensionService toolbarContributionExtensionService;


	public ToolbarContributionExtensionService getToolbarContributionExtensionService() {
		if (this.toolbarContributionExtensionService == null) {
			this.toolbarContributionExtensionService = new ToolbarContributionExtensionService();
		}
		return this.toolbarContributionExtensionService;
	}

	public LocalTrayActionService getLocalTrayActionService() {
		if (this.localTrayActionService == null) {
			this.localTrayActionService = new LocalTrayActionService();
		}
		return this.localTrayActionService;
	}

}
