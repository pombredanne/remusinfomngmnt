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
package org.remus.infomngmnt.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemColorProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnitListItem;

/**
 * This is the item provider adapter for a {@link org.remus.infomngmnt.Category} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class CategoryItemProvider
extends SynchronizableObjectItemProvider
implements
IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource, IItemColorProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CategoryItemProvider(final AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(final Object object) {
		if (this.itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addIdPropertyDescriptor(object);
			addLabelPropertyDescriptor(object);
			addDescriptionPropertyDescriptor(object);
		}
		return this.itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Id feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addIdPropertyDescriptor(final Object object) {
		this.itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)this.adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Category_id_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Category_id_feature", "_UI_Category_type"),
				 InfomngmntPackage.Literals.CATEGORY__ID,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Label feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addLabelPropertyDescriptor(final Object object) {
		this.itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)this.adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Category_label_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Category_label_feature", "_UI_Category_type"),
				 InfomngmntPackage.Literals.CATEGORY__LABEL,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Description feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDescriptionPropertyDescriptor(final Object object) {
		this.itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)this.adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Category_description_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Category_description_feature", "_UI_Category_type"),
				 InfomngmntPackage.Literals.CATEGORY__DESCRIPTION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(final Object object) {
		if (this.childrenFeatures == null) {
			super.getChildrenFeatures(object);
			this.childrenFeatures.add(InfomngmntPackage.Literals.CATEGORY__CHILDREN);
			this.childrenFeatures.add(InfomngmntPackage.Literals.CATEGORY__INFORMATION_UNIT);
		}
		return this.childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	protected EStructuralFeature getChildFeature(final Object object, final Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.
		if (child instanceof Category) {
			return InfomngmntPackage.Literals.CATEGORY__CHILDREN;
		} else if (child instanceof InformationUnitListItem) {
			return InfomngmntPackage.Literals.CATEGORY__INFORMATION_UNIT;
		} else {
			return super.getChildFeature(object, child);
		}
	}

	/**
	 * This returns Category.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	@Override
	public Object getImage(final Object object) {
		if (((IAdaptable) object).getAdapter(IProject.class) != null) {
			return overlayImage(object, getResourceLocator().getImage("iconexperience/folder_green.png"));
		}
		return overlayImage(object, getResourceLocator().getImage("iconexperience/folder_blue.png"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	@Override
	public String getText(final Object object) {
		String label = ((Category)object).getLabel();
		return label == null || label.length() == 0 ?
				getString("_UI_Category_type") :
					label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	@Override
	public void notifyChanged(final Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(Category.class)) {
			case InfomngmntPackage.CATEGORY__ID:
			case InfomngmntPackage.CATEGORY__LABEL:
			case InfomngmntPackage.CATEGORY__DESCRIPTION:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case InfomngmntPackage.CATEGORY__CHILDREN:
			case InfomngmntPackage.CATEGORY__INFORMATION_UNIT:
			case InfomngmntPackage.CATEGORY__SYNCHRONIZATION_META_DATA:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, true));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(final Collection<Object> newChildDescriptors, final Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(InfomngmntPackage.Literals.CATEGORY__CHILDREN,
				 InfomngmntFactory.eINSTANCE.createCategory()));

		newChildDescriptors.add
			(createChildParameter
				(InfomngmntPackage.Literals.CATEGORY__INFORMATION_UNIT,
				 InfomngmntFactory.eINSTANCE.createInformationUnitListItem()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreateChildText(final Object owner, final Object feature, final Object child, final Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify =
			childFeature == InfomngmntPackage.Literals.SYNCHRONIZABLE_OBJECT__MARKED_AS_DELETE_ITEMS ||
			childFeature == InfomngmntPackage.Literals.CATEGORY__CHILDREN ||
			childFeature == InfomngmntPackage.Literals.CATEGORY__INFORMATION_UNIT;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

}
