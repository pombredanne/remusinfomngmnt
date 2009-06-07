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

package org.remus.infomngmnt.core.remote;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.swt.graphics.Image;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class AbstractRepository implements IRepository {

	private String label;

	private Image image;

	private String id;

	private String localRepositoryId;

	private ICredentialProvider credentialProvider;

	final ISchedulingRule mutexRule = new ISchedulingRule() {
		public boolean isConflicting(final ISchedulingRule rule) {
			return rule == AbstractRepository.this.mutexRule;
		}

		public boolean contains(final ISchedulingRule rule) {
			return rule == AbstractRepository.this.mutexRule;
		}
	};

	public IStatus validate(final IProgressMonitor monitor) {
		return Status.OK_STATUS;

	}

	public ISchedulingRule getRule() {
		return this.mutexRule;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	public Image getImage() {
		return this.image;
	}

	public void setImage(final Image image) {
		this.image = image;
	}

	public ICredentialProvider getCredentialProvider() {
		return this.credentialProvider;
	}

	public void setCredentialProvider(final ICredentialProvider credentialProvider) {
		this.credentialProvider = credentialProvider;
	}

	public IFile[] getBinaryReferences() {
		return new IFile[0];
	}

	public String getId() {
		return this.id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getLocalRepositoryId() {
		return this.localRepositoryId;
	}

	public void setLocalRepositoryId(final String localRepositoryId) {
		this.localRepositoryId = localRepositoryId;
	}

	public boolean hasBinaryReferences() {
		return false;
	}

}
