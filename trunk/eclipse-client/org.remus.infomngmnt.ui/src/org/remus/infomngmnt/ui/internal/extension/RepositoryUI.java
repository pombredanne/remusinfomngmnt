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

package org.remus.infomngmnt.ui.internal.extension;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import org.remus.infomngmnt.core.remote.IRepository;
import org.remus.infomngmnt.ui.extension.IRepositoryUI;
import org.remus.infomngmnt.ui.extension.RepositoryExtensionService;
import org.remus.infomngmnt.ui.remote.IRepositoryActionContributor;
import org.remus.infomngmnt.ui.remote.NewRepositoryWizard;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 * @since 1.0
 */
public class RepositoryUI implements IRepositoryUI {

	public RepositoryUI(final IConfigurationElement configurationElement, final String id,
			final String repositoryId) {
		super();
		this.configurationElement = configurationElement;
		this.id = id;
		this.repositoryId = repositoryId;
	}
	private IRepository repositoryImpemenentation;

	/** The configuration element which comes from the plugin-registry **/
	private final IConfigurationElement configurationElement;

	private final String id;

	private final String repositoryId;

	private NewRepositoryWizard wizardClass;
	
	private IRepositoryActionContributor actionContributor;


	public String getId() {
		return this.id;
	}

	public String getRepositoryId() {
		return this.repositoryId;
	}

	public NewRepositoryWizard getWizardClass() {
		try {
			this.wizardClass = (NewRepositoryWizard) this.configurationElement.createExecutableExtension(RepositoryExtensionService.WIZARD_CLASSID);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this.wizardClass;
	}
	
	public IRepositoryActionContributor getActionContributor() {
		if (this.actionContributor == null) {
			try {
				this.actionContributor = (IRepositoryActionContributor) this.configurationElement.createExecutableExtension(RepositoryExtensionService.ACTIONCONTRIBUTOR_CLASSID);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return this.actionContributor;
	}

	
	
	


}
