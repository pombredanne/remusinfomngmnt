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

import org.eclipse.emf.compare.diff.metamodel.AddModelElement;
import org.eclipse.emf.compare.diff.metamodel.DiffGroup;
import org.eclipse.emf.compare.diff.metamodel.RemoveModelElement;
import org.eclipse.emf.compare.util.AdapterUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.Image;

import org.remus.infomngmnt.AbstractInformationUnit;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSetItem;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.SynchronizationAction;
import org.remus.infomngmnt.common.core.util.StringUtils;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.sync.SyncUtil;
import org.remus.infomngmnt.ui.UIPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DiffLabelProvider extends LabelProvider {

	private ChangeSetItem changeSet;

	@Override
	public String getText(final Object element) {
		return getImageOrText(element, false).toString();
	}

	private Object getImageOrText(final Object element, final boolean returnImage) {
		if (element instanceof DiffGroup) {
			final DiffGroup group = (DiffGroup) element;
			final EObject parent = group.getLeftParent();
			if (parent != null) {
				if (parent instanceof Category && ((Category) parent).getId() == null) {
					if (!returnImage) {
						return StringUtils.join("Ready to apply the following changes (", String
								.valueOf(group.getSubchanges()), ")");
					} else {
						return ResourceManager.getPluginImage(UIPlugin.getDefault(),
								"icons/iconexperience/16/server_client_exchange.png");
					}
				} else if (parent instanceof Category && ((Category) parent).getId() != null) {
					if (returnImage) {
						return getImageFromModel(parent);
					} else {
						return NLS.bind("{0} ({1})", ((Category) parent).getLabel(), group
								.getSubchanges());
					}
				} else if (parent instanceof InformationUnitListItem) {
					IInfoType infoTypeByType = InformationExtensionManager.getInstance()
							.getInfoTypeByType(((AbstractInformationUnit) parent).getType());
					if (infoTypeByType != null) {
						Image baseImage;
						if (returnImage) {
							baseImage = infoTypeByType.getImage();
						}
						SynchronizationAction action = SyncUtil.getAction(this.changeSet, parent);
						switch (action) {
						case REPLACE_LOCAL:
							if (returnImage) {

							} else {
								return NLS.bind("Element \"{0}\" was updated at the repository",
										((AbstractInformationUnit) parent).getLabel());
							}
						case REPLACE_REMOTE:
							return NLS.bind("Element \"{0}\" was updated locally",
									((AbstractInformationUnit) parent).getLabel());
						default:
							break;
						}
					}
				}
				final String parentLabel = AdapterUtils.getItemProviderText(parent);
				return StringUtils.join(parentLabel, " (", String.valueOf(group.getSubchanges()),
						")");
			}
			if (returnImage) {
				return ResourceManager.getPluginImage(UIPlugin.getDefault(),
						"icons/iconexperience/16/server_client_exchange.png");
			} else {
				return NLS
						.bind("Ready to apply the following changes ({0})", group.getSubchanges());
			}
		} else if (element instanceof AddModelElement) {
			final AddModelElement addOp = (AddModelElement) element;
			EObject rightElement = addOp.getRightElement();
			SynchronizationAction action = SyncUtil.getAction(this.changeSet, rightElement);
			switch (action) {
			case ADD_LOCAL:
				if (rightElement instanceof Category) {
					return NLS.bind("Category \"{0}\" will be added locally",
							((Category) rightElement).getLabel());
				} else if (rightElement instanceof AbstractInformationUnit) {
					return NLS.bind("Element \"{0}\" will be added locally",
							((AbstractInformationUnit) rightElement).getLabel());
				}
				break;
			case DELETE_REMOTE:
				if (rightElement instanceof Category) {
					return NLS.bind("Category \"{0}\" was removed at the repository",
							((Category) rightElement).getLabel());
				} else if (rightElement instanceof AbstractInformationUnit) {
					return NLS.bind("Element \"{0}\" was removed at the repository",
							((AbstractInformationUnit) rightElement).getLabel());
				}
				break;
			default:
				break;
			}
		} else if (element instanceof RemoveModelElement) {
			RemoveModelElement removeOp = (RemoveModelElement) element;
			EObject leftElement = removeOp.getLeftElement();
			SynchronizationAction action = SyncUtil.getAction(this.changeSet, leftElement);
			switch (action) {
			case ADD_REMOTE:
				if (leftElement instanceof Category) {
					return NLS.bind("Category \"{0}\" will be added remotely",
							((Category) leftElement).getLabel());
				} else if (leftElement instanceof AbstractInformationUnit) {
					return NLS.bind("Element \"{0}\" will be added remotely",
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
		Object imageOrText = getImageOrText(element, true);
		if (imageOrText instanceof Image) {
			return (Image) imageOrText;
		}
		return null;
	}

	private Image getImageFromModel(final EObject object) {
		IItemLabelProvider adapt = (IItemLabelProvider) EditingUtil.getInstance()
				.getAdapterFactory().adapt(object, IItemLabelProvider.class);
		if (adapt != null && adapt.getImage(object) != null) {
			return ExtendedImageRegistry.getInstance().getImage(adapt.getImage(object));
		}
		return null;
	}

	/**
	 * @param setChangeSet
	 *            the syncActions to set
	 */
	public void setChangeSet(final ChangeSetItem setChangeSet) {
		this.changeSet = setChangeSet;
	}

}
