/*******************************************************************************
 * Copyright (c) 2009 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.ui.collapsiblebuttons;

import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.action.DeleteAction;
import org.eclipse.emf.query.conditions.eobjects.EObjectCondition;
import org.eclipse.emf.query.conditions.eobjects.EObjectTypeRelationCondition;
import org.eclipse.emf.query.conditions.eobjects.TypeRelation;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.IQueryResult;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;
import org.eclipse.jface.dialogs.MessageDialog;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.RepositoryCollection;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.core.extension.AbstractExtensionRepository;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.core.services.IRepositoryExtensionService;
import org.remus.infomngmnt.core.services.IRepositoryService;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.util.EditingUtil;

/**
 * JFace Action which unsets all {@link SynchronizationMetadata} on the
 * {@link SynchronizableObject}s which are affected by the deletion of this
 * repository.
 * 
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 * @since 1.0
 */
public class DeleteRepositoryAction extends DeleteAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.edit.ui.action.CommandActionHandler#run()
	 */
	@Override
	public void run() {

		if (MessageDialog.openConfirm(UIUtil.getDisplay().getActiveShell(), "Confirm delte",
				"All associated information units will be disconnected from repository. Continue?")) {
			List list = getStructuredSelection().toList();
			for (Object object : list) {
				if (object instanceof RemoteRepository) {
					final String reproId = ((RemoteRepository) object).getId();
					EList<Category> model = ApplicationModelPool.getInstance().getModel()
							.getRootCategories();
					EObjectCondition condition = new EObjectTypeRelationCondition(
							InfomngmntPackage.Literals.SYNCHRONIZABLE_OBJECT,
							TypeRelation.SUBTYPE_LITERAL);
					EObjectCondition condition2 = new EObjectCondition() {
						@Override
						public boolean isSatisfied(final EObject eObject) {
							return ((SynchronizableObject) eObject).getSynchronizationMetaData() != null
									&& reproId.equals(((SynchronizableObject) eObject)
											.getSynchronizationMetaData().getRepositoryId());
						}
					};
					SELECT select = new SELECT(new FROM(model),
							new WHERE(condition.AND(condition2)));
					IQueryResult execute = select.execute();
					Set<? extends EObject> eObjects = execute.getEObjects();
					for (EObject eObject : eObjects) {
						eObject
								.eUnset(InfomngmntPackage.Literals.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA);
					}
					try {
						AbstractExtensionRepository itemByRepository = InfomngmntEditPlugin
								.getPlugin().getService(IRepositoryExtensionService.class)
								.getItemByRepository((RemoteRepository) object);
						if (itemByRepository != null
								&& itemByRepository.getCredentialProvider() != null) {
							itemByRepository.getCredentialProvider().delete();
						}
					} catch (CoreException e) {
						// do nothing
					}
				}
			}
		}
		super.run();
		RepositoryCollection repositories = InfomngmntEditPlugin.getPlugin().getService(
				IRepositoryService.class).getRepositories();
		EditingUtil.getInstance().saveObjectToResource(repositories);

	}

	public DeleteRepositoryAction() {
		super();
	}

	public DeleteRepositoryAction(final boolean removeAllReferences) {
		super(removeAllReferences);
	}

	public DeleteRepositoryAction(final EditingDomain domain, final boolean removeAllReferences) {
		super(domain, removeAllReferences);
	}

	public DeleteRepositoryAction(final EditingDomain domain) {
		super(domain);
	}

}
