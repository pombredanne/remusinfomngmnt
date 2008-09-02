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
package org.remus.infomngmnt;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.remus.infomngmnt.InfomngmntFactory
 * @model kind="package"
 * @generated
 */
public interface InfomngmntPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "infomngmnt";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://remus-software.org/infomngmnt";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "infomngmnt";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	InfomngmntPackage eINSTANCE = org.remus.infomngmnt.impl.InfomngmntPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.AbstractInformationUnitImpl <em>Abstract Information Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.AbstractInformationUnitImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getAbstractInformationUnit()
	 * @generated
	 */
	int ABSTRACT_INFORMATION_UNIT = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_INFORMATION_UNIT__ID = 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_INFORMATION_UNIT__LABEL = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_INFORMATION_UNIT__TYPE = 2;

	/**
	 * The number of structural features of the '<em>Abstract Information Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.InformationUnitImpl <em>Information Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.InformationUnitImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getInformationUnit()
	 * @generated
	 */
	int INFORMATION_UNIT = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__ID = ABSTRACT_INFORMATION_UNIT__ID;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__LABEL = ABSTRACT_INFORMATION_UNIT__LABEL;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__TYPE = ABSTRACT_INFORMATION_UNIT__TYPE;

	/**
	 * The feature id for the '<em><b>String Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__STRING_VALUE = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Long Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__LONG_VALUE = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Bool Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__BOOL_VALUE = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Binary Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__BINARY_VALUE = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Date Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__DATE_VALUE = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Child Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__CHILD_VALUES = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Links</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__LINKS = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__CREATION_DATE = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Usage Data</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__USAGE_DATA = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Information Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT_FEATURE_COUNT = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 9;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.UsageImpl <em>Usage</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.UsageImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getUsage()
	 * @generated
	 */
	int USAGE = 1;

	/**
	 * The feature id for the '<em><b>Last Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USAGE__LAST_ACCESS = 0;

	/**
	 * The feature id for the '<em><b>Access Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USAGE__ACCESS_COUNT = 1;

	/**
	 * The number of structural features of the '<em>Usage</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USAGE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.CategoryImpl <em>Category</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.CategoryImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getCategory()
	 * @generated
	 */
	int CATEGORY = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__ID = 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__LABEL = 1;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__CHILDREN = 2;

	/**
	 * The feature id for the '<em><b>Information Unit</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__INFORMATION_UNIT = 3;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__DESCRIPTION = 4;

	/**
	 * The number of structural features of the '<em>Category</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.InformationUnitListItemImpl <em>Information Unit List Item</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.InformationUnitListItemImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getInformationUnitListItem()
	 * @generated
	 */
	int INFORMATION_UNIT_LIST_ITEM = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT_LIST_ITEM__ID = ABSTRACT_INFORMATION_UNIT__ID;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT_LIST_ITEM__LABEL = ABSTRACT_INFORMATION_UNIT__LABEL;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT_LIST_ITEM__TYPE = ABSTRACT_INFORMATION_UNIT__TYPE;

	/**
	 * The feature id for the '<em><b>Workspace Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT_LIST_ITEM__WORKSPACE_PATH = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Information Unit List Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT_LIST_ITEM_FEATURE_COUNT = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 1;


	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.ApplicationRootImpl <em>Application Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.ApplicationRootImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getApplicationRoot()
	 * @generated
	 */
	int APPLICATION_ROOT = 5;

	/**
	 * The feature id for the '<em><b>Root Categories</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_ROOT__ROOT_CATEGORIES = 0;

	/**
	 * The number of structural features of the '<em>Application Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_ROOT_FEATURE_COUNT = 1;


	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.AnnotationImpl <em>Annotation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.AnnotationImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getAnnotation()
	 * @generated
	 */
	int ANNOTATION = 6;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__DESCRIPTION = 0;

	/**
	 * The number of structural features of the '<em>Annotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_FEATURE_COUNT = 1;


	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.InformationUnit <em>Information Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Information Unit</em>'.
	 * @see org.remus.infomngmnt.InformationUnit
	 * @generated
	 */
	EClass getInformationUnit();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.InformationUnit#getStringValue <em>String Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>String Value</em>'.
	 * @see org.remus.infomngmnt.InformationUnit#getStringValue()
	 * @see #getInformationUnit()
	 * @generated
	 */
	EAttribute getInformationUnit_StringValue();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.InformationUnit#getLongValue <em>Long Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Long Value</em>'.
	 * @see org.remus.infomngmnt.InformationUnit#getLongValue()
	 * @see #getInformationUnit()
	 * @generated
	 */
	EAttribute getInformationUnit_LongValue();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.InformationUnit#isBoolValue <em>Bool Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Bool Value</em>'.
	 * @see org.remus.infomngmnt.InformationUnit#isBoolValue()
	 * @see #getInformationUnit()
	 * @generated
	 */
	EAttribute getInformationUnit_BoolValue();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.InformationUnit#getBinaryValue <em>Binary Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Binary Value</em>'.
	 * @see org.remus.infomngmnt.InformationUnit#getBinaryValue()
	 * @see #getInformationUnit()
	 * @generated
	 */
	EAttribute getInformationUnit_BinaryValue();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.InformationUnit#getDateValue <em>Date Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Date Value</em>'.
	 * @see org.remus.infomngmnt.InformationUnit#getDateValue()
	 * @see #getInformationUnit()
	 * @generated
	 */
	EAttribute getInformationUnit_DateValue();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.infomngmnt.InformationUnit#getChildValues <em>Child Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Child Values</em>'.
	 * @see org.remus.infomngmnt.InformationUnit#getChildValues()
	 * @see #getInformationUnit()
	 * @generated
	 */
	EReference getInformationUnit_ChildValues();

	/**
	 * Returns the meta object for the reference list '{@link org.remus.infomngmnt.InformationUnit#getLinks <em>Links</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Links</em>'.
	 * @see org.remus.infomngmnt.InformationUnit#getLinks()
	 * @see #getInformationUnit()
	 * @generated
	 */
	EReference getInformationUnit_Links();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.InformationUnit#getCreationDate <em>Creation Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Creation Date</em>'.
	 * @see org.remus.infomngmnt.InformationUnit#getCreationDate()
	 * @see #getInformationUnit()
	 * @generated
	 */
	EAttribute getInformationUnit_CreationDate();

	/**
	 * Returns the meta object for the reference '{@link org.remus.infomngmnt.InformationUnit#getUsageData <em>Usage Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Usage Data</em>'.
	 * @see org.remus.infomngmnt.InformationUnit#getUsageData()
	 * @see #getInformationUnit()
	 * @generated
	 */
	EReference getInformationUnit_UsageData();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.Usage <em>Usage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Usage</em>'.
	 * @see org.remus.infomngmnt.Usage
	 * @generated
	 */
	EClass getUsage();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.Usage#getLastAccess <em>Last Access</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Access</em>'.
	 * @see org.remus.infomngmnt.Usage#getLastAccess()
	 * @see #getUsage()
	 * @generated
	 */
	EAttribute getUsage_LastAccess();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.Usage#getAccessCount <em>Access Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Access Count</em>'.
	 * @see org.remus.infomngmnt.Usage#getAccessCount()
	 * @see #getUsage()
	 * @generated
	 */
	EAttribute getUsage_AccessCount();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.Category <em>Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Category</em>'.
	 * @see org.remus.infomngmnt.Category
	 * @generated
	 */
	EClass getCategory();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.Category#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.remus.infomngmnt.Category#getId()
	 * @see #getCategory()
	 * @generated
	 */
	EAttribute getCategory_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.Category#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.remus.infomngmnt.Category#getLabel()
	 * @see #getCategory()
	 * @generated
	 */
	EAttribute getCategory_Label();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.infomngmnt.Category#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Children</em>'.
	 * @see org.remus.infomngmnt.Category#getChildren()
	 * @see #getCategory()
	 * @generated
	 */
	EReference getCategory_Children();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.infomngmnt.Category#getInformationUnit <em>Information Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Information Unit</em>'.
	 * @see org.remus.infomngmnt.Category#getInformationUnit()
	 * @see #getCategory()
	 * @generated
	 */
	EReference getCategory_InformationUnit();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.Category#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.remus.infomngmnt.Category#getDescription()
	 * @see #getCategory()
	 * @generated
	 */
	EAttribute getCategory_Description();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.AbstractInformationUnit <em>Abstract Information Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Information Unit</em>'.
	 * @see org.remus.infomngmnt.AbstractInformationUnit
	 * @generated
	 */
	EClass getAbstractInformationUnit();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.AbstractInformationUnit#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.remus.infomngmnt.AbstractInformationUnit#getId()
	 * @see #getAbstractInformationUnit()
	 * @generated
	 */
	EAttribute getAbstractInformationUnit_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.AbstractInformationUnit#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.remus.infomngmnt.AbstractInformationUnit#getLabel()
	 * @see #getAbstractInformationUnit()
	 * @generated
	 */
	EAttribute getAbstractInformationUnit_Label();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.AbstractInformationUnit#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.remus.infomngmnt.AbstractInformationUnit#getType()
	 * @see #getAbstractInformationUnit()
	 * @generated
	 */
	EAttribute getAbstractInformationUnit_Type();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.InformationUnitListItem <em>Information Unit List Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Information Unit List Item</em>'.
	 * @see org.remus.infomngmnt.InformationUnitListItem
	 * @generated
	 */
	EClass getInformationUnitListItem();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.InformationUnitListItem#getWorkspacePath <em>Workspace Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Workspace Path</em>'.
	 * @see org.remus.infomngmnt.InformationUnitListItem#getWorkspacePath()
	 * @see #getInformationUnitListItem()
	 * @generated
	 */
	EAttribute getInformationUnitListItem_WorkspacePath();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.ApplicationRoot <em>Application Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Application Root</em>'.
	 * @see org.remus.infomngmnt.ApplicationRoot
	 * @generated
	 */
	EClass getApplicationRoot();

	/**
	 * Returns the meta object for the reference list '{@link org.remus.infomngmnt.ApplicationRoot#getRootCategories <em>Root Categories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Root Categories</em>'.
	 * @see org.remus.infomngmnt.ApplicationRoot#getRootCategories()
	 * @see #getApplicationRoot()
	 * @generated
	 */
	EReference getApplicationRoot_RootCategories();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.Annotation <em>Annotation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotation</em>'.
	 * @see org.remus.infomngmnt.Annotation
	 * @generated
	 */
	EClass getAnnotation();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.Annotation#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.remus.infomngmnt.Annotation#getDescription()
	 * @see #getAnnotation()
	 * @generated
	 */
	EAttribute getAnnotation_Description();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	InfomngmntFactory getInfomngmntFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.InformationUnitImpl <em>Information Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.InformationUnitImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getInformationUnit()
		 * @generated
		 */
		EClass INFORMATION_UNIT = eINSTANCE.getInformationUnit();

		/**
		 * The meta object literal for the '<em><b>String Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INFORMATION_UNIT__STRING_VALUE = eINSTANCE.getInformationUnit_StringValue();

		/**
		 * The meta object literal for the '<em><b>Long Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INFORMATION_UNIT__LONG_VALUE = eINSTANCE.getInformationUnit_LongValue();

		/**
		 * The meta object literal for the '<em><b>Bool Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INFORMATION_UNIT__BOOL_VALUE = eINSTANCE.getInformationUnit_BoolValue();

		/**
		 * The meta object literal for the '<em><b>Binary Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INFORMATION_UNIT__BINARY_VALUE = eINSTANCE.getInformationUnit_BinaryValue();

		/**
		 * The meta object literal for the '<em><b>Date Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INFORMATION_UNIT__DATE_VALUE = eINSTANCE.getInformationUnit_DateValue();

		/**
		 * The meta object literal for the '<em><b>Child Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INFORMATION_UNIT__CHILD_VALUES = eINSTANCE.getInformationUnit_ChildValues();

		/**
		 * The meta object literal for the '<em><b>Links</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INFORMATION_UNIT__LINKS = eINSTANCE.getInformationUnit_Links();

		/**
		 * The meta object literal for the '<em><b>Creation Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INFORMATION_UNIT__CREATION_DATE = eINSTANCE.getInformationUnit_CreationDate();

		/**
		 * The meta object literal for the '<em><b>Usage Data</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INFORMATION_UNIT__USAGE_DATA = eINSTANCE.getInformationUnit_UsageData();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.UsageImpl <em>Usage</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.UsageImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getUsage()
		 * @generated
		 */
		EClass USAGE = eINSTANCE.getUsage();

		/**
		 * The meta object literal for the '<em><b>Last Access</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USAGE__LAST_ACCESS = eINSTANCE.getUsage_LastAccess();

		/**
		 * The meta object literal for the '<em><b>Access Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USAGE__ACCESS_COUNT = eINSTANCE.getUsage_AccessCount();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.CategoryImpl <em>Category</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.CategoryImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getCategory()
		 * @generated
		 */
		EClass CATEGORY = eINSTANCE.getCategory();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CATEGORY__ID = eINSTANCE.getCategory_Id();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CATEGORY__LABEL = eINSTANCE.getCategory_Label();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CATEGORY__CHILDREN = eINSTANCE.getCategory_Children();

		/**
		 * The meta object literal for the '<em><b>Information Unit</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CATEGORY__INFORMATION_UNIT = eINSTANCE.getCategory_InformationUnit();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CATEGORY__DESCRIPTION = eINSTANCE.getCategory_Description();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.AbstractInformationUnitImpl <em>Abstract Information Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.AbstractInformationUnitImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getAbstractInformationUnit()
		 * @generated
		 */
		EClass ABSTRACT_INFORMATION_UNIT = eINSTANCE.getAbstractInformationUnit();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_INFORMATION_UNIT__ID = eINSTANCE.getAbstractInformationUnit_Id();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_INFORMATION_UNIT__LABEL = eINSTANCE.getAbstractInformationUnit_Label();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_INFORMATION_UNIT__TYPE = eINSTANCE.getAbstractInformationUnit_Type();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.InformationUnitListItemImpl <em>Information Unit List Item</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.InformationUnitListItemImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getInformationUnitListItem()
		 * @generated
		 */
		EClass INFORMATION_UNIT_LIST_ITEM = eINSTANCE.getInformationUnitListItem();

		/**
		 * The meta object literal for the '<em><b>Workspace Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INFORMATION_UNIT_LIST_ITEM__WORKSPACE_PATH = eINSTANCE.getInformationUnitListItem_WorkspacePath();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.ApplicationRootImpl <em>Application Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.ApplicationRootImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getApplicationRoot()
		 * @generated
		 */
		EClass APPLICATION_ROOT = eINSTANCE.getApplicationRoot();

		/**
		 * The meta object literal for the '<em><b>Root Categories</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLICATION_ROOT__ROOT_CATEGORIES = eINSTANCE.getApplicationRoot_RootCategories();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.AnnotationImpl <em>Annotation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.AnnotationImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getAnnotation()
		 * @generated
		 */
		EClass ANNOTATION = eINSTANCE.getAnnotation();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ANNOTATION__DESCRIPTION = eINSTANCE.getAnnotation_Description();

	}

} //InfomngmntPackage
