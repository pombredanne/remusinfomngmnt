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
import org.eclipse.emf.ecore.EObject;
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

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.SynchronizationMetadata;

/**
 * This is the item provider adapter for a
 * {@link org.remus.infomngmnt.SynchronizationMetadata} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class SynchronizationMetadataItemProvider extends AdapterItemProvider implements
		IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider,
		IItemLabelProvider, IItemPropertySource, IItemColorProvider {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SynchronizationMetadataItemProvider(final AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(final Object object) {
		if (this.itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addRepositoryIdPropertyDescriptor(object);
			addUrlPropertyDescriptor(object);
			addReadonlyPropertyDescriptor(object);
			addLastSynchronisationPropertyDescriptor(object);
			addHashPropertyDescriptor(object);
			addSyncStatePropertyDescriptor(object);
			addCurrentlySyncingPropertyDescriptor(object);
		}
		return this.itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Repository Id feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addRepositoryIdPropertyDescriptor(final Object object) {
		this.itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_SynchronizationMetadata_repositoryId_feature"), getString(
						"_UI_PropertyDescriptor_description",
						"_UI_SynchronizationMetadata_repositoryId_feature",
						"_UI_SynchronizationMetadata_type"),
				InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__REPOSITORY_ID, true, false,
				false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Url feature. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addUrlPropertyDescriptor(final Object object) {
		this.itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
				getResourceLocator(), getString("_UI_SynchronizationMetadata_url_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_SynchronizationMetadata_url_feature",
						"_UI_SynchronizationMetadata_type"),
				InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__URL, true, false, false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Readonly feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addReadonlyPropertyDescriptor(final Object object) {
		this.itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
				getResourceLocator(), getString("_UI_SynchronizationMetadata_readonly_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_SynchronizationMetadata_readonly_feature",
						"_UI_SynchronizationMetadata_type"),
				InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__READONLY, true, false, false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Last Synchronisation feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addLastSynchronisationPropertyDescriptor(final Object object) {
		this.itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_SynchronizationMetadata_lastSynchronisation_feature"), getString(
						"_UI_PropertyDescriptor_description",
						"_UI_SynchronizationMetadata_lastSynchronisation_feature",
						"_UI_SynchronizationMetadata_type"),
				InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__LAST_SYNCHRONISATION, true,
				false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Hash feature. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addHashPropertyDescriptor(final Object object) {
		this.itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
				getResourceLocator(), getString("_UI_SynchronizationMetadata_hash_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_SynchronizationMetadata_hash_feature",
						"_UI_SynchronizationMetadata_type"),
				InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__HASH, true, false, false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Sync State feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addSyncStatePropertyDescriptor(final Object object) {
		this.itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
				getResourceLocator(), getString("_UI_SynchronizationMetadata_syncState_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_SynchronizationMetadata_syncState_feature",
						"_UI_SynchronizationMetadata_type"),
				InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__SYNC_STATE, true, false,
				false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Currently Syncing feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addCurrentlySyncingPropertyDescriptor(final Object object) {
		this.itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_SynchronizationMetadata_currentlySyncing_feature"), getString(
						"_UI_PropertyDescriptor_description",
						"_UI_SynchronizationMetadata_currentlySyncing_feature",
						"_UI_SynchronizationMetadata_type"),
				InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__CURRENTLY_SYNCING, true,
				false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This returns SynchronizationMetadata.gif. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(final Object object) {
		return overlayImage(object, getResourceLocator().getImage(
				"full/obj16/SynchronizationMetadata"));
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(final Object object) {
		String label = ((SynchronizationMetadata) object).getRepositoryId();
		return label == null || label.length() == 0 ? getString("_UI_SynchronizationMetadata_type")
				: getString("_UI_SynchronizationMetadata_type") + " " + label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to
	 * update any cached children and by creating a viewer notification, which
	 * it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public void notifyChanged(final Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(SynchronizationMetadata.class)) {
		case InfomngmntPackage.SYNCHRONIZATION_METADATA__REPOSITORY_ID:
		case InfomngmntPackage.SYNCHRONIZATION_METADATA__URL:
		case InfomngmntPackage.SYNCHRONIZATION_METADATA__READONLY:
		case InfomngmntPackage.SYNCHRONIZATION_METADATA__LAST_SYNCHRONISATION:
		case InfomngmntPackage.SYNCHRONIZATION_METADATA__HASH:
		case InfomngmntPackage.SYNCHRONIZATION_METADATA__SYNC_STATE:
		case InfomngmntPackage.SYNCHRONIZATION_METADATA__CURRENTLY_SYNCING:
			// We also have to update the parent item.
			fireNotifyChanged(new ViewerNotification(notification, ((EObject) notification
					.getNotifier()).eContainer().eContainer(), true, true));
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(),
					false, true));
			return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing the children that can be created under this object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(final Collection<Object> newChildDescriptors,
			final Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

}
