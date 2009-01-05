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

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.RemoteObject;

/**
 * This is the item provider adapter for a {@link org.remus.infomngmnt.RemoteObject} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class RemoteObjectItemProvider
	extends AdapterItemProvider
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RemoteObjectItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addPossibleInfoTypeIdPropertyDescriptor(object);
			addIdPropertyDescriptor(object);
			addUrlPropertyDescriptor(object);
			addNamePropertyDescriptor(object);
			addRepositoryTypeIdPropertyDescriptor(object);
			addRepositoryTypeObjectIdPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Possible Info Type Id feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPossibleInfoTypeIdPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RemoteObject_possibleInfoTypeId_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RemoteObject_possibleInfoTypeId_feature", "_UI_RemoteObject_type"),
				 InfomngmntPackage.Literals.REMOTE_OBJECT__POSSIBLE_INFO_TYPE_ID,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Id feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addIdPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RemoteObject_id_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RemoteObject_id_feature", "_UI_RemoteObject_type"),
				 InfomngmntPackage.Literals.REMOTE_OBJECT__ID,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Url feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addUrlPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RemoteObject_url_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RemoteObject_url_feature", "_UI_RemoteObject_type"),
				 InfomngmntPackage.Literals.REMOTE_OBJECT__URL,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RemoteObject_name_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RemoteObject_name_feature", "_UI_RemoteObject_type"),
				 InfomngmntPackage.Literals.REMOTE_OBJECT__NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Repository Type Id feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addRepositoryTypeIdPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RemoteObject_repositoryTypeId_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RemoteObject_repositoryTypeId_feature", "_UI_RemoteObject_type"),
				 InfomngmntPackage.Literals.REMOTE_OBJECT__REPOSITORY_TYPE_ID,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Repository Type Object Id feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addRepositoryTypeObjectIdPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RemoteObject_repositoryTypeObjectId_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RemoteObject_repositoryTypeObjectId_feature", "_UI_RemoteObject_type"),
				 InfomngmntPackage.Literals.REMOTE_OBJECT__REPOSITORY_TYPE_OBJECT_ID,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((RemoteObject)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_RemoteObject_type") :
			getString("_UI_RemoteObject_type") + " " + label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(RemoteObject.class)) {
			case InfomngmntPackage.REMOTE_OBJECT__POSSIBLE_INFO_TYPE_ID:
			case InfomngmntPackage.REMOTE_OBJECT__ID:
			case InfomngmntPackage.REMOTE_OBJECT__URL:
			case InfomngmntPackage.REMOTE_OBJECT__NAME:
			case InfomngmntPackage.REMOTE_OBJECT__REPOSITORY_TYPE_ID:
			case InfomngmntPackage.REMOTE_OBJECT__REPOSITORY_TYPE_OBJECT_ID:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
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
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

}
