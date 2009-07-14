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

import org.remus.infomngmnt.AbstractInformationUnit;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;

/**
 * This is the item provider adapter for a {@link org.remus.infomngmnt.InformationUnit} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class InformationUnitItemProvider extends AbstractInformationUnitItemProvider implements
		IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider,
		IItemLabelProvider, IItemPropertySource, IItemColorProvider {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public InformationUnitItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addStringValuePropertyDescriptor(object);
			addLongValuePropertyDescriptor(object);
			addBoolValuePropertyDescriptor(object);
			addBinaryValuePropertyDescriptor(object);
			addDateValuePropertyDescriptor(object);
			addDoubleValuePropertyDescriptor(object);
			addReferencesPropertyDescriptor(object);
			addLinksPropertyDescriptor(object);
			addCreationDatePropertyDescriptor(object);
			addUsageDataPropertyDescriptor(object);
			addDescriptionPropertyDescriptor(object);
			addKeywordsPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the String Value feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addStringValuePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_InformationUnit_stringValue_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_InformationUnit_stringValue_feature", "_UI_InformationUnit_type"),
				 InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Long Value feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addLongValuePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_InformationUnit_longValue_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_InformationUnit_longValue_feature", "_UI_InformationUnit_type"),
				 InfomngmntPackage.Literals.INFORMATION_UNIT__LONG_VALUE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Bool Value feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addBoolValuePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_InformationUnit_boolValue_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_InformationUnit_boolValue_feature", "_UI_InformationUnit_type"),
				 InfomngmntPackage.Literals.INFORMATION_UNIT__BOOL_VALUE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Binary Value feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addBinaryValuePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_InformationUnit_binaryValue_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_InformationUnit_binaryValue_feature", "_UI_InformationUnit_type"),
				 InfomngmntPackage.Literals.INFORMATION_UNIT__BINARY_VALUE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Date Value feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addDateValuePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_InformationUnit_dateValue_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_InformationUnit_dateValue_feature", "_UI_InformationUnit_type"),
				 InfomngmntPackage.Literals.INFORMATION_UNIT__DATE_VALUE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Double Value feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDoubleValuePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_InformationUnit_doubleValue_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_InformationUnit_doubleValue_feature", "_UI_InformationUnit_type"),
				 InfomngmntPackage.Literals.INFORMATION_UNIT__DOUBLE_VALUE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.REAL_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the References feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addReferencesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_InformationUnit_references_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_InformationUnit_references_feature", "_UI_InformationUnit_type"),
				 InfomngmntPackage.Literals.INFORMATION_UNIT__REFERENCES,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Links feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addLinksPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_InformationUnit_links_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_InformationUnit_links_feature", "_UI_InformationUnit_type"),
				 InfomngmntPackage.Literals.INFORMATION_UNIT__LINKS,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Creation Date feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addCreationDatePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_InformationUnit_creationDate_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_InformationUnit_creationDate_feature", "_UI_InformationUnit_type"),
				 InfomngmntPackage.Literals.INFORMATION_UNIT__CREATION_DATE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Usage Data feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addUsageDataPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_InformationUnit_usageData_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_InformationUnit_usageData_feature", "_UI_InformationUnit_type"),
				 InfomngmntPackage.Literals.INFORMATION_UNIT__USAGE_DATA,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Description feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addDescriptionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_InformationUnit_description_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_InformationUnit_description_feature", "_UI_InformationUnit_type"),
				 InfomngmntPackage.Literals.INFORMATION_UNIT__DESCRIPTION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Keywords feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addKeywordsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_InformationUnit_keywords_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_InformationUnit_keywords_feature", "_UI_InformationUnit_type"),
				 InfomngmntPackage.Literals.INFORMATION_UNIT__KEYWORDS,
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(InfomngmntPackage.Literals.INFORMATION_UNIT__CHILD_VALUES);
			childrenFeatures.add(InfomngmntPackage.Literals.INFORMATION_UNIT__CALENDAR_ENTRY);
			childrenFeatures.add(InfomngmntPackage.Literals.INFORMATION_UNIT__BINARY_REFERENCES);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns InformationUnit.gif. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated not
	 */
	@Override
	public Object getImage(final Object object) {
		IInfoType infoTypeByType = InformationExtensionManager.getInstance().getInfoTypeByType(
				((AbstractInformationUnit) object).getType());
		if (infoTypeByType != null) {
			return infoTypeByType.getImage();
		}
		return null;
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((InformationUnit)object).getLabel();
		return label == null || label.length() == 0 ?
			getString("_UI_InformationUnit_type") :
			getString("_UI_InformationUnit_type") + " " + label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(InformationUnit.class)) {
			case InfomngmntPackage.INFORMATION_UNIT__STRING_VALUE:
			case InfomngmntPackage.INFORMATION_UNIT__LONG_VALUE:
			case InfomngmntPackage.INFORMATION_UNIT__BOOL_VALUE:
			case InfomngmntPackage.INFORMATION_UNIT__BINARY_VALUE:
			case InfomngmntPackage.INFORMATION_UNIT__DATE_VALUE:
			case InfomngmntPackage.INFORMATION_UNIT__DOUBLE_VALUE:
			case InfomngmntPackage.INFORMATION_UNIT__CREATION_DATE:
			case InfomngmntPackage.INFORMATION_UNIT__DESCRIPTION:
			case InfomngmntPackage.INFORMATION_UNIT__KEYWORDS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case InfomngmntPackage.INFORMATION_UNIT__CHILD_VALUES:
			case InfomngmntPackage.INFORMATION_UNIT__CALENDAR_ENTRY:
			case InfomngmntPackage.INFORMATION_UNIT__BINARY_REFERENCES:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
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
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(InfomngmntPackage.Literals.INFORMATION_UNIT__CHILD_VALUES,
				 InfomngmntFactory.eINSTANCE.createInformationUnit()));

		newChildDescriptors.add
			(createChildParameter
				(InfomngmntPackage.Literals.INFORMATION_UNIT__CHILD_VALUES,
				 InfomngmntFactory.eINSTANCE.createRuleValue()));

		newChildDescriptors.add
			(createChildParameter
				(InfomngmntPackage.Literals.INFORMATION_UNIT__CALENDAR_ENTRY,
				 InfomngmntFactory.eINSTANCE.createCalendarEntry()));

		newChildDescriptors.add
			(createChildParameter
				(InfomngmntPackage.Literals.INFORMATION_UNIT__BINARY_REFERENCES,
				 InfomngmntFactory.eINSTANCE.createBinaryReference()));
	}

}
