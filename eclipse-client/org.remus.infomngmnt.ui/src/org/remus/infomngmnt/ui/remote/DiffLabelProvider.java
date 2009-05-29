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

package org.remus.infomngmnt.ui.remote;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.compare.diff.metamodel.AddModelElement;
import org.eclipse.emf.compare.diff.metamodel.DiffGroup;
import org.eclipse.emf.compare.diff.metamodel.RemoveModelElement;
import org.eclipse.emf.compare.util.AdapterUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.Image;

import org.remus.infomngmnt.AbstractInformationUnit;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationAction;
import org.remus.infomngmnt.common.core.util.StringUtils;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DiffLabelProvider extends LabelProvider {

	private EMap<SynchronizableObject, SynchronizationAction> syncActions;

	@Override
	public String getText(final Object element) {
		if (element instanceof DiffGroup) {
			final DiffGroup group = (DiffGroup) element;
			final EObject parent = group.getLeftParent();
			if (parent != null) {
				if (parent instanceof Category && ((Category) parent).getId() == null) {
					return StringUtils.join("Ready to apply the following changes (", String
							.valueOf(group.getSubchanges()), ")");
				}
				final String parentLabel = AdapterUtils.getItemProviderText(parent);
				return StringUtils.join(parentLabel, " (", String.valueOf(group.getSubchanges()),
						")");
			}
			return NLS.bind("Ready to apply the following changes ({0})", group.getSubchanges());
		} else if (element instanceof AddModelElement) {
			SynchronizationAction action = this.syncActions.get(element);
			final AddModelElement addOp = (AddModelElement) element;
			EObject rightElement = addOp.getRightElement();
			switch (action) {
			case ADD_LOCAL:
				if (rightElement instanceof Category) {
					return NLS.bind("Category \"{0}\" will be added", ((Category) rightElement)
							.getLabel());
				} else if (rightElement instanceof AbstractInformationUnit) {
					return NLS.bind("Element \'{0}\' will be added",
							((AbstractInformationUnit) rightElement).getLabel());
				}
				break;
			case DELETE_REMOTE:
				if (rightElement instanceof Category) {
					return NLS.bind("Category \"{0}\" was removed at the repository",
							((Category) rightElement).getLabel());
				} else if (rightElement instanceof AbstractInformationUnit) {
					return NLS.bind("Element \'{0}\' was removed at the repository",
							((AbstractInformationUnit) rightElement).getLabel());
				}
				break;
			default:
				break;
			}
		} else if (element instanceof RemoveModelElement) {
			RemoveModelElement removeOp = (RemoveModelElement) element;
			EObject leftElement = removeOp.getLeftElement();
			SynchronizationAction action = SyncUtil.getAction(this.syncActions, leftElement);
			switch (action) {
			case ADD_REMOTE:
				if (leftElement instanceof Category) {
					return NLS.bind("Category \"{0}\" will be added", ((Category) leftElement)
							.getLabel());
				} else if (leftElement instanceof AbstractInformationUnit) {
					return NLS.bind("Element \'{0}\' will be added",
							((AbstractInformationUnit) leftElement).getLabel());
				}
				break;
			case REPLACE_REMOTE:
				break;

			default:
				break;
			}
		}
		return super.getText(element);
	}

	@Override
	public Image getImage(final Object element) {
		// TODO Auto-generated method stub
		return super.getImage(element);
	}

	/**
	 * @param syncActions
	 *            the syncActions to set
	 */
	public void setSyncActions(final EMap<SynchronizableObject, SynchronizationAction> syncActions) {
		this.syncActions = syncActions;
	}

}
