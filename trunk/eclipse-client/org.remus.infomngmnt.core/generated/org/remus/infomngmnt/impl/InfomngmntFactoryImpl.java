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
package org.remus.infomngmnt.impl;

import java.util.Map;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.remus.infomngmnt.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class InfomngmntFactoryImpl extends EFactoryImpl implements InfomngmntFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static InfomngmntFactory init() {
		try {
			InfomngmntFactory theInfomngmntFactory = (InfomngmntFactory)EPackage.Registry.INSTANCE.getEFactory("http://remus-software.org/infomngmnt"); 
			if (theInfomngmntFactory != null) {
				return theInfomngmntFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new InfomngmntFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfomngmntFactoryImpl() {
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
			case InfomngmntPackage.INFORMATION_UNIT: return createInformationUnit();
			case InfomngmntPackage.USAGE: return createUsage();
			case InfomngmntPackage.CATEGORY: return createCategory();
			case InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM: return createInformationUnitListItem();
			case InfomngmntPackage.APPLICATION_ROOT: return createApplicationRoot();
			case InfomngmntPackage.ANNOTATION: return createAnnotation();
			case InfomngmntPackage.LINK: return createLink();
			case InfomngmntPackage.LINK_TYPE: return createLinkType();
			case InfomngmntPackage.LINK_TYPE_COLLECTION: return createLinkTypeCollection();
			case InfomngmntPackage.STRING_TO_LINK_TYPE_MAP: return (EObject)createStringToLinkTypeMap();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InformationUnit createInformationUnit() {
		InformationUnitImpl informationUnit = new InformationUnitImpl();
		return informationUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Usage createUsage() {
		UsageImpl usage = new UsageImpl();
		return usage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Category createCategory() {
		CategoryImpl category = new CategoryImpl();
		return category;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InformationUnitListItem createInformationUnitListItem() {
		InformationUnitListItemImpl informationUnitListItem = new InformationUnitListItemImpl();
		return informationUnitListItem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApplicationRoot createApplicationRoot() {
		ApplicationRootImpl applicationRoot = new ApplicationRootImpl();
		return applicationRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Annotation createAnnotation() {
		AnnotationImpl annotation = new AnnotationImpl();
		return annotation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Link createLink() {
		LinkImpl link = new LinkImpl();
		return link;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkType createLinkType() {
		LinkTypeImpl linkType = new LinkTypeImpl();
		return linkType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkTypeCollection createLinkTypeCollection() {
		LinkTypeCollectionImpl linkTypeCollection = new LinkTypeCollectionImpl();
		return linkTypeCollection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, LinkType> createStringToLinkTypeMap() {
		StringToLinkTypeMapImpl stringToLinkTypeMap = new StringToLinkTypeMapImpl();
		return stringToLinkTypeMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfomngmntPackage getInfomngmntPackage() {
		return (InfomngmntPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static InfomngmntPackage getPackage() {
		return InfomngmntPackage.eINSTANCE;
	}

} //InfomngmntFactoryImpl
