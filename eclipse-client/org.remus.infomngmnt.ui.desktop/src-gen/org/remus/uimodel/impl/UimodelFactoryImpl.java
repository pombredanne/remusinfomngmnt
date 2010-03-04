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
package org.remus.uimodel.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.swt.graphics.Image;

import org.remus.infomngmnt.ui.desktop.extension.IToolbarItemProvider;

import org.remus.uimodel.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class UimodelFactoryImpl extends EFactoryImpl implements UimodelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static UimodelFactory init() {
		try {
			UimodelFactory theUimodelFactory = (UimodelFactory)EPackage.Registry.INSTANCE.getEFactory("http://remus-software.org/uimodel"); 
			if (theUimodelFactory != null) {
				return theUimodelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new UimodelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UimodelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case UimodelPackage.STRING_TO_STRING_MAP: return (EObject)createStringToStringMap();
			case UimodelPackage.TRAY_SECTION: return createTraySection();
			case UimodelPackage.TRAY_SECTION_COLLECTION: return createTraySectionCollection();
			case UimodelPackage.DESKTOP_TOOL_ITEM_COLLECTION: return createDesktopToolItemCollection();
			case UimodelPackage.DESKTOP_TOOL_ITEM: return createDesktopToolItem();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case UimodelPackage.ITOOLBAR_ITEM_PROVIDER:
				return createIToolbarItemProviderFromString(eDataType, initialValue);
			case UimodelPackage.IMAGE:
				return createImageFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case UimodelPackage.ITOOLBAR_ITEM_PROVIDER:
				return convertIToolbarItemProviderToString(eDataType, instanceValue);
			case UimodelPackage.IMAGE:
				return convertImageToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, String> createStringToStringMap() {
		StringToStringMapImpl stringToStringMap = new StringToStringMapImpl();
		return stringToStringMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TraySection createTraySection() {
		TraySectionImpl traySection = new TraySectionImpl();
		return traySection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TraySectionCollection createTraySectionCollection() {
		TraySectionCollectionImpl traySectionCollection = new TraySectionCollectionImpl();
		return traySectionCollection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DesktopToolItemCollection createDesktopToolItemCollection() {
		DesktopToolItemCollectionImpl desktopToolItemCollection = new DesktopToolItemCollectionImpl();
		return desktopToolItemCollection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DesktopToolItem createDesktopToolItem() {
		DesktopToolItemImpl desktopToolItem = new DesktopToolItemImpl();
		return desktopToolItem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IToolbarItemProvider createIToolbarItemProviderFromString(EDataType eDataType, String initialValue) {
		return (IToolbarItemProvider)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIToolbarItemProviderToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Image createImageFromString(EDataType eDataType, String initialValue) {
		return (Image)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertImageToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UimodelPackage getUimodelPackage() {
		return (UimodelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static UimodelPackage getPackage() {
		return UimodelPackage.eINSTANCE;
	}

} //UimodelFactoryImpl
