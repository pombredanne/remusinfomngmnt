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
import org.eclipse.emf.common.util.EList;
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
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.model.ApplicationModelPool;

/**
 * This is the item provider adapter for a
 * {@link org.remus.infomngmnt.Notification} object. <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class NotificationItemProvider extends AdapterItemProvider implements
		IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider,
		IItemLabelProvider, IItemPropertySource, IItemColorProvider {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationItemProvider(final AdapterFactory adapterFactory) {
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

			addTimeStampPropertyDescriptor(object);
			addImportancePropertyDescriptor(object);
			addSeverityPropertyDescriptor(object);
			addNoticedPropertyDescriptor(object);
			addMessagePropertyDescriptor(object);
			addDetailsPropertyDescriptor(object);
			addChildrenPropertyDescriptor(object);
			addAffectedInfoUnitIdsPropertyDescriptor(object);
			addSourcePropertyDescriptor(object);
			addImagePropertyDescriptor(object);
		}
		return this.itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Time Stamp feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addTimeStampPropertyDescriptor(final Object object) {
		this.itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
				getResourceLocator(), getString("_UI_Notification_timeStamp_feature"), getString(
						"_UI_PropertyDescriptor_description", "_UI_Notification_timeStamp_feature",
						"_UI_Notification_type"),
				InfomngmntPackage.Literals.NOTIFICATION__TIME_STAMP, true, false, false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Importance feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addImportancePropertyDescriptor(final Object object) {
		this.itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
				getResourceLocator(), getString("_UI_Notification_importance_feature"), getString(
						"_UI_PropertyDescriptor_description",
						"_UI_Notification_importance_feature", "_UI_Notification_type"),
				InfomngmntPackage.Literals.NOTIFICATION__IMPORTANCE, true, false, false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Severity feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addSeverityPropertyDescriptor(final Object object) {
		this.itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
				getResourceLocator(), getString("_UI_Notification_severity_feature"), getString(
						"_UI_PropertyDescriptor_description", "_UI_Notification_severity_feature",
						"_UI_Notification_type"),
				InfomngmntPackage.Literals.NOTIFICATION__SEVERITY, true, false, false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Noticed feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addNoticedPropertyDescriptor(final Object object) {
		this.itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
				getResourceLocator(), getString("_UI_Notification_noticed_feature"), getString(
						"_UI_PropertyDescriptor_description", "_UI_Notification_noticed_feature",
						"_UI_Notification_type"), InfomngmntPackage.Literals.NOTIFICATION__NOTICED,
				true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Message feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMessagePropertyDescriptor(final Object object) {
		this.itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
				getResourceLocator(), getString("_UI_Notification_message_feature"), getString(
						"_UI_PropertyDescriptor_description", "_UI_Notification_message_feature",
						"_UI_Notification_type"), InfomngmntPackage.Literals.NOTIFICATION__MESSAGE,
				true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Details feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addDetailsPropertyDescriptor(final Object object) {
		this.itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
				getResourceLocator(), getString("_UI_Notification_details_feature"), getString(
						"_UI_PropertyDescriptor_description", "_UI_Notification_details_feature",
						"_UI_Notification_type"), InfomngmntPackage.Literals.NOTIFICATION__DETAILS,
				true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Children feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addChildrenPropertyDescriptor(final Object object) {
		this.itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
				getResourceLocator(), getString("_UI_Notification_children_feature"), getString(
						"_UI_PropertyDescriptor_description", "_UI_Notification_children_feature",
						"_UI_Notification_type"),
				InfomngmntPackage.Literals.NOTIFICATION__CHILDREN, true, false, true, null, null,
				null));
	}

	/**
	 * This adds a property descriptor for the Affected Info Unit Ids feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addAffectedInfoUnitIdsPropertyDescriptor(final Object object) {
		this.itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
				getResourceLocator(), getString("_UI_Notification_affectedInfoUnitIds_feature"),
				getString("_UI_PropertyDescriptor_description",
						"_UI_Notification_affectedInfoUnitIds_feature", "_UI_Notification_type"),
				InfomngmntPackage.Literals.NOTIFICATION__AFFECTED_INFO_UNIT_IDS, true, false,
				false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Source feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addSourcePropertyDescriptor(final Object object) {
		this.itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
				getResourceLocator(), getString("_UI_Notification_source_feature"), getString(
						"_UI_PropertyDescriptor_description", "_UI_Notification_source_feature",
						"_UI_Notification_type"), InfomngmntPackage.Literals.NOTIFICATION__SOURCE,
				true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Image feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addImagePropertyDescriptor(final Object object) {
		this.itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
				getResourceLocator(), getString("_UI_Notification_image_feature"), getString(
						"_UI_PropertyDescriptor_description", "_UI_Notification_image_feature",
						"_UI_Notification_type"), InfomngmntPackage.Literals.NOTIFICATION__IMAGE,
				true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This returns Notification.gif. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Object getImage(final Object object) {
		EList<String> affectedInfoUnitIds = ((org.remus.infomngmnt.Notification) object)
				.getAffectedInfoUnitIds();
		if (affectedInfoUnitIds.size() > 0
				&& ApplicationModelPool.getInstance().getItemById(affectedInfoUnitIds.get(0), null) != null) {
			IInfoType infoTypeByType = InformationExtensionManager.getInstance().getInfoTypeByType(
					ApplicationModelPool.getInstance()
							.getItemById(affectedInfoUnitIds.get(0), null).getType());
			if (infoTypeByType != null) {
				return infoTypeByType.getImage();
			}
		}
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Notification"));
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(final Object object) {
		String label = ((org.remus.infomngmnt.Notification) object).getMessage();
		return label == null || label.length() == 0 ? getString("_UI_Notification_type")
				: getString("_UI_Notification_type") + " " + label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to
	 * update any cached children and by creating a viewer notification, which
	 * it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void notifyChanged(final Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(org.remus.infomngmnt.Notification.class)) {
		case InfomngmntPackage.NOTIFICATION__TIME_STAMP:
		case InfomngmntPackage.NOTIFICATION__IMPORTANCE:
		case InfomngmntPackage.NOTIFICATION__SEVERITY:
		case InfomngmntPackage.NOTIFICATION__NOTICED:
		case InfomngmntPackage.NOTIFICATION__MESSAGE:
		case InfomngmntPackage.NOTIFICATION__DETAILS:
		case InfomngmntPackage.NOTIFICATION__AFFECTED_INFO_UNIT_IDS:
		case InfomngmntPackage.NOTIFICATION__SOURCE:
		case InfomngmntPackage.NOTIFICATION__IMAGE:
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
