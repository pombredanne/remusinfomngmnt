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
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
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
	String eNS_URI = "http://remus-software.org/infomngmnt/1.0";

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
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.AdapterImpl <em>Adapter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.AdapterImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getAdapter()
	 * @generated
	 */
	int ADAPTER = 9;

	/**
	 * The number of structural features of the '<em>Adapter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.AbstractInformationUnitImpl <em>Abstract Information Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.AbstractInformationUnitImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getAbstractInformationUnit()
	 * @generated
	 */
	int ABSTRACT_INFORMATION_UNIT = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_INFORMATION_UNIT__ID = ADAPTER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_INFORMATION_UNIT__LABEL = ADAPTER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_INFORMATION_UNIT__TYPE = ADAPTER_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Abstract Information Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT = ADAPTER_FEATURE_COUNT + 3;

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
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__REFERENCES = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__LINKS = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__CREATION_DATE = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Usage Data</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__USAGE_DATA = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__DESCRIPTION = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Keywords</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__KEYWORDS = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Calendar Entry</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__CALENDAR_ENTRY = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Binary References</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__BINARY_REFERENCES = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 13;

	/**
	 * The number of structural features of the '<em>Information Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT_FEATURE_COUNT = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 14;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.BinaryReferenceImpl <em>Binary Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.BinaryReferenceImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getBinaryReference()
	 * @generated
	 */
	int BINARY_REFERENCE = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_REFERENCE__ID = 0;

	/**
	 * The feature id for the '<em><b>Project Relative Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_REFERENCE__PROJECT_RELATIVE_PATH = 1;

	/**
	 * The feature id for the '<em><b>Dirty</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_REFERENCE__DIRTY = 2;

	/**
	 * The number of structural features of the '<em>Binary Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_REFERENCE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.UsageImpl <em>Usage</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.UsageImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getUsage()
	 * @generated
	 */
	int USAGE = 2;

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
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.SynchronizableObjectImpl <em>Synchronizable Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.SynchronizableObjectImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getSynchronizableObject()
	 * @generated
	 */
	int SYNCHRONIZABLE_OBJECT = 29;

	/**
	 * The feature id for the '<em><b>Synchronization Meta Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA = ADAPTER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Synchronizable Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONIZABLE_OBJECT_FEATURE_COUNT = ADAPTER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.CategoryImpl <em>Category</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.CategoryImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getCategory()
	 * @generated
	 */
	int CATEGORY = 3;

	/**
	 * The feature id for the '<em><b>Synchronization Meta Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__SYNCHRONIZATION_META_DATA = SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__ID = SYNCHRONIZABLE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__LABEL = SYNCHRONIZABLE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__CHILDREN = SYNCHRONIZABLE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Information Unit</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__INFORMATION_UNIT = SYNCHRONIZABLE_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__DESCRIPTION = SYNCHRONIZABLE_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Category</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_FEATURE_COUNT = SYNCHRONIZABLE_OBJECT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.InformationUnitListItemImpl <em>Information Unit List Item</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.InformationUnitListItemImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getInformationUnitListItem()
	 * @generated
	 */
	int INFORMATION_UNIT_LIST_ITEM = 5;

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
	 * The feature id for the '<em><b>Synchronization Meta Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT_LIST_ITEM__SYNCHRONIZATION_META_DATA = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Workspace Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT_LIST_ITEM__WORKSPACE_PATH = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Information Unit List Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT_LIST_ITEM_FEATURE_COUNT = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 2;


	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.ApplicationRootImpl <em>Application Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.ApplicationRootImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getApplicationRoot()
	 * @generated
	 */
	int APPLICATION_ROOT = 6;

	/**
	 * The feature id for the '<em><b>Root Categories</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_ROOT__ROOT_CATEGORIES = 0;

	/**
	 * The feature id for the '<em><b>Available Tags</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_ROOT__AVAILABLE_TAGS = 1;

	/**
	 * The number of structural features of the '<em>Application Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_ROOT_FEATURE_COUNT = 2;


	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.AnnotationImpl <em>Annotation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.AnnotationImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getAnnotation()
	 * @generated
	 */
	int ANNOTATION = 7;

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
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.LinkImpl <em>Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.LinkImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getLink()
	 * @generated
	 */
	int LINK = 8;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__TARGET = 0;

	/**
	 * The number of structural features of the '<em>Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.RecentlyUsedKeywordsImpl <em>Recently Used Keywords</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.RecentlyUsedKeywordsImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRecentlyUsedKeywords()
	 * @generated
	 */
	int RECENTLY_USED_KEYWORDS = 10;

	/**
	 * The feature id for the '<em><b>Maxlength</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECENTLY_USED_KEYWORDS__MAXLENGTH = 0;

	/**
	 * The feature id for the '<em><b>Keywords</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECENTLY_USED_KEYWORDS__KEYWORDS = 1;

	/**
	 * The number of structural features of the '<em>Recently Used Keywords</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECENTLY_USED_KEYWORDS_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.NewElementRulesImpl <em>New Element Rules</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.NewElementRulesImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getNewElementRules()
	 * @generated
	 */
	int NEW_ELEMENT_RULES = 11;

	/**
	 * The feature id for the '<em><b>Transfer Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_ELEMENT_RULES__TRANSFER_TYPES = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_ELEMENT_RULES__NAME = 1;

	/**
	 * The feature id for the '<em><b>Deletable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_ELEMENT_RULES__DELETABLE = 2;

	/**
	 * The number of structural features of the '<em>New Element Rules</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_ELEMENT_RULES_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.RuleValueImpl <em>Rule Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.RuleValueImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRuleValue()
	 * @generated
	 */
	int RULE_VALUE = 12;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_VALUE__ID = INFORMATION_UNIT__ID;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_VALUE__LABEL = INFORMATION_UNIT__LABEL;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_VALUE__TYPE = INFORMATION_UNIT__TYPE;

	/**
	 * The feature id for the '<em><b>String Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_VALUE__STRING_VALUE = INFORMATION_UNIT__STRING_VALUE;

	/**
	 * The feature id for the '<em><b>Long Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_VALUE__LONG_VALUE = INFORMATION_UNIT__LONG_VALUE;

	/**
	 * The feature id for the '<em><b>Bool Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_VALUE__BOOL_VALUE = INFORMATION_UNIT__BOOL_VALUE;

	/**
	 * The feature id for the '<em><b>Binary Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_VALUE__BINARY_VALUE = INFORMATION_UNIT__BINARY_VALUE;

	/**
	 * The feature id for the '<em><b>Date Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_VALUE__DATE_VALUE = INFORMATION_UNIT__DATE_VALUE;

	/**
	 * The feature id for the '<em><b>Child Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_VALUE__CHILD_VALUES = INFORMATION_UNIT__CHILD_VALUES;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_VALUE__REFERENCES = INFORMATION_UNIT__REFERENCES;

	/**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_VALUE__LINKS = INFORMATION_UNIT__LINKS;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_VALUE__CREATION_DATE = INFORMATION_UNIT__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Usage Data</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_VALUE__USAGE_DATA = INFORMATION_UNIT__USAGE_DATA;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_VALUE__DESCRIPTION = INFORMATION_UNIT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Keywords</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_VALUE__KEYWORDS = INFORMATION_UNIT__KEYWORDS;

	/**
	 * The feature id for the '<em><b>Calendar Entry</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_VALUE__CALENDAR_ENTRY = INFORMATION_UNIT__CALENDAR_ENTRY;

	/**
	 * The feature id for the '<em><b>Binary References</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_VALUE__BINARY_REFERENCES = INFORMATION_UNIT__BINARY_REFERENCES;

	/**
	 * The number of structural features of the '<em>Rule Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_VALUE_FEATURE_COUNT = INFORMATION_UNIT_FEATURE_COUNT + 0;


	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.AvailableRuleDefinitionsImpl <em>Available Rule Definitions</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.AvailableRuleDefinitionsImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getAvailableRuleDefinitions()
	 * @generated
	 */
	int AVAILABLE_RULE_DEFINITIONS = 13;

	/**
	 * The feature id for the '<em><b>New Element Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AVAILABLE_RULE_DEFINITIONS__NEW_ELEMENT_RULES = 0;

	/**
	 * The number of structural features of the '<em>Available Rule Definitions</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AVAILABLE_RULE_DEFINITIONS_FEATURE_COUNT = 1;


	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.RemusTransferTypeImpl <em>Remus Transfer Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.RemusTransferTypeImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRemusTransferType()
	 * @generated
	 */
	int REMUS_TRANSFER_TYPE = 14;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMUS_TRANSFER_TYPE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMUS_TRANSFER_TYPE__ID = 1;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMUS_TRANSFER_TYPE__ACTIONS = 2;

	/**
	 * The number of structural features of the '<em>Remus Transfer Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMUS_TRANSFER_TYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.RuleActionImpl <em>Rule Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.RuleActionImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRuleAction()
	 * @generated
	 */
	int RULE_ACTION = 15;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ACTION__NAME = 0;

	/**
	 * The feature id for the '<em><b>Info Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ACTION__INFO_TYPE_ID = 1;

	/**
	 * The feature id for the '<em><b>Rule Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ACTION__RULE_VALUE = 2;

	/**
	 * The feature id for the '<em><b>Groovy Matcher</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ACTION__GROOVY_MATCHER = 3;

	/**
	 * The feature id for the '<em><b>Post Processing Instructions</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ACTION__POST_PROCESSING_INSTRUCTIONS = 4;

	/**
	 * The number of structural features of the '<em>Rule Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ACTION_FEATURE_COUNT = 5;


	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.RuleResultImpl <em>Rule Result</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.RuleResultImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRuleResult()
	 * @generated
	 */
	int RULE_RESULT = 16;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_RESULT__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_RESULT__ACTIONS = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_RESULT__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Transfer Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_RESULT__TRANSFER_TYPE = 3;

	/**
	 * The number of structural features of the '<em>Rule Result</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_RESULT_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.RemoteRepositoryImpl <em>Remote Repository</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.RemoteRepositoryImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRemoteRepository()
	 * @generated
	 */
	int REMOTE_REPOSITORY = 17;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.RemoteObjectImpl <em>Remote Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.RemoteObjectImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRemoteObject()
	 * @generated
	 */
	int REMOTE_OBJECT = 18;

	/**
	 * The feature id for the '<em><b>Possible Info Type Id</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_OBJECT__POSSIBLE_INFO_TYPE_ID = ADAPTER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_OBJECT__ID = ADAPTER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_OBJECT__URL = ADAPTER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_OBJECT__NAME = ADAPTER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Repository Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_OBJECT__REPOSITORY_TYPE_ID = ADAPTER_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Repository Type Object Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_OBJECT__REPOSITORY_TYPE_OBJECT_ID = ADAPTER_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Wrapped Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_OBJECT__WRAPPED_OBJECT = ADAPTER_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Hash</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_OBJECT__HASH = ADAPTER_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Remote Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_OBJECT_FEATURE_COUNT = ADAPTER_FEATURE_COUNT + 8;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.RemoteContainerImpl <em>Remote Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.RemoteContainerImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRemoteContainer()
	 * @generated
	 */
	int REMOTE_CONTAINER = 19;

	/**
	 * The feature id for the '<em><b>Possible Info Type Id</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_CONTAINER__POSSIBLE_INFO_TYPE_ID = REMOTE_OBJECT__POSSIBLE_INFO_TYPE_ID;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_CONTAINER__ID = REMOTE_OBJECT__ID;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_CONTAINER__URL = REMOTE_OBJECT__URL;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_CONTAINER__NAME = REMOTE_OBJECT__NAME;

	/**
	 * The feature id for the '<em><b>Repository Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_CONTAINER__REPOSITORY_TYPE_ID = REMOTE_OBJECT__REPOSITORY_TYPE_ID;

	/**
	 * The feature id for the '<em><b>Repository Type Object Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_CONTAINER__REPOSITORY_TYPE_OBJECT_ID = REMOTE_OBJECT__REPOSITORY_TYPE_OBJECT_ID;

	/**
	 * The feature id for the '<em><b>Wrapped Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_CONTAINER__WRAPPED_OBJECT = REMOTE_OBJECT__WRAPPED_OBJECT;

	/**
	 * The feature id for the '<em><b>Hash</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_CONTAINER__HASH = REMOTE_OBJECT__HASH;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_CONTAINER__CHILDREN = REMOTE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Exclusion Children</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_CONTAINER__EXCLUSION_CHILDREN = REMOTE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Remote Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_CONTAINER_FEATURE_COUNT = REMOTE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Possible Info Type Id</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_REPOSITORY__POSSIBLE_INFO_TYPE_ID = REMOTE_CONTAINER__POSSIBLE_INFO_TYPE_ID;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_REPOSITORY__ID = REMOTE_CONTAINER__ID;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_REPOSITORY__URL = REMOTE_CONTAINER__URL;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_REPOSITORY__NAME = REMOTE_CONTAINER__NAME;

	/**
	 * The feature id for the '<em><b>Repository Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_REPOSITORY__REPOSITORY_TYPE_ID = REMOTE_CONTAINER__REPOSITORY_TYPE_ID;

	/**
	 * The feature id for the '<em><b>Repository Type Object Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_REPOSITORY__REPOSITORY_TYPE_OBJECT_ID = REMOTE_CONTAINER__REPOSITORY_TYPE_OBJECT_ID;

	/**
	 * The feature id for the '<em><b>Wrapped Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_REPOSITORY__WRAPPED_OBJECT = REMOTE_CONTAINER__WRAPPED_OBJECT;

	/**
	 * The feature id for the '<em><b>Hash</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_REPOSITORY__HASH = REMOTE_CONTAINER__HASH;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_REPOSITORY__CHILDREN = REMOTE_CONTAINER__CHILDREN;

	/**
	 * The feature id for the '<em><b>Exclusion Children</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_REPOSITORY__EXCLUSION_CHILDREN = REMOTE_CONTAINER__EXCLUSION_CHILDREN;

	/**
	 * The number of structural features of the '<em>Remote Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_REPOSITORY_FEATURE_COUNT = REMOTE_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.RepositoryCollectionImpl <em>Repository Collection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.RepositoryCollectionImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRepositoryCollection()
	 * @generated
	 */
	int REPOSITORY_COLLECTION = 20;

	/**
	 * The feature id for the '<em><b>Repositories</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_COLLECTION__REPOSITORIES = 0;

	/**
	 * The number of structural features of the '<em>Repository Collection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_COLLECTION_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.SynchronizationMetadataImpl <em>Synchronization Metadata</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.SynchronizationMetadataImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getSynchronizationMetadata()
	 * @generated
	 */
	int SYNCHRONIZATION_METADATA = 21;

	/**
	 * The feature id for the '<em><b>Repository Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONIZATION_METADATA__REPOSITORY_ID = ADAPTER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONIZATION_METADATA__URL = ADAPTER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONIZATION_METADATA__READONLY = ADAPTER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Last Synchronisation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONIZATION_METADATA__LAST_SYNCHRONISATION = ADAPTER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Hash</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONIZATION_METADATA__HASH = ADAPTER_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Sync State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONIZATION_METADATA__SYNC_STATE = ADAPTER_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Synchronization Metadata</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONIZATION_METADATA_FEATURE_COUNT = ADAPTER_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.ChangeSetImpl <em>Change Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.ChangeSetImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getChangeSet()
	 * @generated
	 */
	int CHANGE_SET = 22;

	/**
	 * The feature id for the '<em><b>Target Category</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_SET__TARGET_CATEGORY = ADAPTER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Change Set Items</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_SET__CHANGE_SET_ITEMS = ADAPTER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Repository</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_SET__REPOSITORY = ADAPTER_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Change Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_SET_FEATURE_COUNT = ADAPTER_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.ChangeSetItemImpl <em>Change Set Item</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.ChangeSetItemImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getChangeSetItem()
	 * @generated
	 */
	int CHANGE_SET_ITEM = 23;

	/**
	 * The feature id for the '<em><b>Remote Converted Container</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_SET_ITEM__REMOTE_CONVERTED_CONTAINER = ADAPTER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Remote Original Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_SET_ITEM__REMOTE_ORIGINAL_OBJECT = ADAPTER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Local Container</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_SET_ITEM__LOCAL_CONTAINER = ADAPTER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Sync Category Action Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_SET_ITEM__SYNC_CATEGORY_ACTION_MAP = ADAPTER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Sync Object Action Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_SET_ITEM__SYNC_OBJECT_ACTION_MAP = ADAPTER_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Remote Full Object Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_SET_ITEM__REMOTE_FULL_OBJECT_MAP = ADAPTER_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Change Set Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_SET_ITEM_FEATURE_COUNT = ADAPTER_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.CategoryToSynchronizationActionMapImpl <em>Category To Synchronization Action Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.CategoryToSynchronizationActionMapImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getCategoryToSynchronizationActionMap()
	 * @generated
	 */
	int CATEGORY_TO_SYNCHRONIZATION_ACTION_MAP = 24;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_TO_SYNCHRONIZATION_ACTION_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_TO_SYNCHRONIZATION_ACTION_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Category To Synchronization Action Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_TO_SYNCHRONIZATION_ACTION_MAP_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.InformationUnitListItemToInformationUnitMapImpl <em>Information Unit List Item To Information Unit Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.InformationUnitListItemToInformationUnitMapImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getInformationUnitListItemToInformationUnitMap()
	 * @generated
	 */
	int INFORMATION_UNIT_LIST_ITEM_TO_INFORMATION_UNIT_MAP = 25;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT_LIST_ITEM_TO_INFORMATION_UNIT_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT_LIST_ITEM_TO_INFORMATION_UNIT_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Information Unit List Item To Information Unit Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT_LIST_ITEM_TO_INFORMATION_UNIT_MAP_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.SynchronizableObjectToSynchronizationActionMapImpl <em>Synchronizable Object To Synchronization Action Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.SynchronizableObjectToSynchronizationActionMapImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getSynchronizableObjectToSynchronizationActionMap()
	 * @generated
	 */
	int SYNCHRONIZABLE_OBJECT_TO_SYNCHRONIZATION_ACTION_MAP = 26;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONIZABLE_OBJECT_TO_SYNCHRONIZATION_ACTION_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONIZABLE_OBJECT_TO_SYNCHRONIZATION_ACTION_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Synchronizable Object To Synchronization Action Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONIZABLE_OBJECT_TO_SYNCHRONIZATION_ACTION_MAP_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.TagImpl <em>Tag</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.TagImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getTag()
	 * @generated
	 */
	int TAG = 27;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG__NAME = ADAPTER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Info Units</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG__INFO_UNITS = ADAPTER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Tag</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_FEATURE_COUNT = ADAPTER_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.AvailableTagsImpl <em>Available Tags</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.AvailableTagsImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getAvailableTags()
	 * @generated
	 */
	int AVAILABLE_TAGS = 28;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AVAILABLE_TAGS__TAGS = 0;

	/**
	 * The number of structural features of the '<em>Available Tags</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AVAILABLE_TAGS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.CalendarEntryImpl <em>Calendar Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.CalendarEntryImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getCalendarEntry()
	 * @generated
	 */
	int CALENDAR_ENTRY = 30;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALENDAR_ENTRY__ID = 0;

	/**
	 * The feature id for the '<em><b>Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALENDAR_ENTRY__START = 1;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALENDAR_ENTRY__END = 2;

	/**
	 * The feature id for the '<em><b>Entry Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALENDAR_ENTRY__ENTRY_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Reminder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALENDAR_ENTRY__REMINDER = 4;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALENDAR_ENTRY__TITLE = 5;

	/**
	 * The number of structural features of the '<em>Calendar Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALENDAR_ENTRY_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.NotificationImpl <em>Notification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.NotificationImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getNotification()
	 * @generated
	 */
	int NOTIFICATION = 31;

	/**
	 * The feature id for the '<em><b>Time Stamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION__TIME_STAMP = ADAPTER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Importance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION__IMPORTANCE = ADAPTER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Severity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION__SEVERITY = ADAPTER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Noticed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION__NOTICED = ADAPTER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION__MESSAGE = ADAPTER_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Details</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION__DETAILS = ADAPTER_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION__CHILDREN = ADAPTER_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Affected Info Unit Ids</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION__AFFECTED_INFO_UNIT_IDS = ADAPTER_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION__SOURCE = ADAPTER_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Image</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION__IMAGE = ADAPTER_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>Notification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION_FEATURE_COUNT = ADAPTER_FEATURE_COUNT + 10;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.NotificationCollectionImpl <em>Notification Collection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.NotificationCollectionImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getNotificationCollection()
	 * @generated
	 */
	int NOTIFICATION_COLLECTION = 32;

	/**
	 * The feature id for the '<em><b>Notifcations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION_COLLECTION__NOTIFCATIONS = 0;

	/**
	 * The number of structural features of the '<em>Notification Collection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION_COLLECTION_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.SynchronizationState <em>Synchronization State</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.SynchronizationState
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getSynchronizationState()
	 * @generated
	 */
	int SYNCHRONIZATION_STATE = 33;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.SynchronizationAction <em>Synchronization Action</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.SynchronizationAction
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getSynchronizationAction()
	 * @generated
	 */
	int SYNCHRONIZATION_ACTION = 34;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.CalendarEntryType <em>Calendar Entry Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.CalendarEntryType
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getCalendarEntryType()
	 * @generated
	 */
	int CALENDAR_ENTRY_TYPE = 35;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.NotificationImportance <em>Notification Importance</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.NotificationImportance
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getNotificationImportance()
	 * @generated
	 */
	int NOTIFICATION_IMPORTANCE = 36;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.Severity <em>Severity</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.Severity
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getSeverity()
	 * @generated
	 */
	int SEVERITY = 37;

	/**
	 * The meta object id for the '<em>Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.Object
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getObject()
	 * @generated
	 */
	int OBJECT = 38;


	/**
	 * The meta object id for the '<em>IRepository</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.core.remote.IRepository
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getIRepository()
	 * @generated
	 */
	int IREPOSITORY = 39;


	/**
	 * The meta object id for the '<em>Transfer Wrapper</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.core.extension.TransferWrapper
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getTransferWrapper()
	 * @generated
	 */
	int TRANSFER_WRAPPER = 40;


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
	 * Returns the meta object for the reference list '{@link org.remus.infomngmnt.InformationUnit#getReferences <em>References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>References</em>'.
	 * @see org.remus.infomngmnt.InformationUnit#getReferences()
	 * @see #getInformationUnit()
	 * @generated
	 */
	EReference getInformationUnit_References();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.infomngmnt.InformationUnit#getLinks <em>Links</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Links</em>'.
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
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.InformationUnit#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.remus.infomngmnt.InformationUnit#getDescription()
	 * @see #getInformationUnit()
	 * @generated
	 */
	EAttribute getInformationUnit_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.InformationUnit#getKeywords <em>Keywords</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Keywords</em>'.
	 * @see org.remus.infomngmnt.InformationUnit#getKeywords()
	 * @see #getInformationUnit()
	 * @generated
	 */
	EAttribute getInformationUnit_Keywords();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.infomngmnt.InformationUnit#getCalendarEntry <em>Calendar Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Calendar Entry</em>'.
	 * @see org.remus.infomngmnt.InformationUnit#getCalendarEntry()
	 * @see #getInformationUnit()
	 * @generated
	 */
	EReference getInformationUnit_CalendarEntry();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.infomngmnt.InformationUnit#getBinaryReferences <em>Binary References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Binary References</em>'.
	 * @see org.remus.infomngmnt.InformationUnit#getBinaryReferences()
	 * @see #getInformationUnit()
	 * @generated
	 */
	EReference getInformationUnit_BinaryReferences();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.BinaryReference <em>Binary Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Binary Reference</em>'.
	 * @see org.remus.infomngmnt.BinaryReference
	 * @generated
	 */
	EClass getBinaryReference();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.BinaryReference#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.remus.infomngmnt.BinaryReference#getId()
	 * @see #getBinaryReference()
	 * @generated
	 */
	EAttribute getBinaryReference_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.BinaryReference#getProjectRelativePath <em>Project Relative Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Project Relative Path</em>'.
	 * @see org.remus.infomngmnt.BinaryReference#getProjectRelativePath()
	 * @see #getBinaryReference()
	 * @generated
	 */
	EAttribute getBinaryReference_ProjectRelativePath();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.BinaryReference#isDirty <em>Dirty</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Dirty</em>'.
	 * @see org.remus.infomngmnt.BinaryReference#isDirty()
	 * @see #getBinaryReference()
	 * @generated
	 */
	EAttribute getBinaryReference_Dirty();

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
	 * Returns the meta object for the reference '{@link org.remus.infomngmnt.ApplicationRoot#getAvailableTags <em>Available Tags</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Available Tags</em>'.
	 * @see org.remus.infomngmnt.ApplicationRoot#getAvailableTags()
	 * @see #getApplicationRoot()
	 * @generated
	 */
	EReference getApplicationRoot_AvailableTags();

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
	 * Returns the meta object for class '{@link org.remus.infomngmnt.Link <em>Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Link</em>'.
	 * @see org.remus.infomngmnt.Link
	 * @generated
	 */
	EClass getLink();

	/**
	 * Returns the meta object for the reference '{@link org.remus.infomngmnt.Link#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.remus.infomngmnt.Link#getTarget()
	 * @see #getLink()
	 * @generated
	 */
	EReference getLink_Target();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.Adapter <em>Adapter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Adapter</em>'.
	 * @see org.remus.infomngmnt.Adapter
	 * @generated
	 */
	EClass getAdapter();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.RecentlyUsedKeywords <em>Recently Used Keywords</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Recently Used Keywords</em>'.
	 * @see org.remus.infomngmnt.RecentlyUsedKeywords
	 * @generated
	 */
	EClass getRecentlyUsedKeywords();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.RecentlyUsedKeywords#getMaxlength <em>Maxlength</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Maxlength</em>'.
	 * @see org.remus.infomngmnt.RecentlyUsedKeywords#getMaxlength()
	 * @see #getRecentlyUsedKeywords()
	 * @generated
	 */
	EAttribute getRecentlyUsedKeywords_Maxlength();

	/**
	 * Returns the meta object for the attribute list '{@link org.remus.infomngmnt.RecentlyUsedKeywords#getKeywords <em>Keywords</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Keywords</em>'.
	 * @see org.remus.infomngmnt.RecentlyUsedKeywords#getKeywords()
	 * @see #getRecentlyUsedKeywords()
	 * @generated
	 */
	EAttribute getRecentlyUsedKeywords_Keywords();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.NewElementRules <em>New Element Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>New Element Rules</em>'.
	 * @see org.remus.infomngmnt.NewElementRules
	 * @generated
	 */
	EClass getNewElementRules();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.infomngmnt.NewElementRules#getTransferTypes <em>Transfer Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Transfer Types</em>'.
	 * @see org.remus.infomngmnt.NewElementRules#getTransferTypes()
	 * @see #getNewElementRules()
	 * @generated
	 */
	EReference getNewElementRules_TransferTypes();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.NewElementRules#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.remus.infomngmnt.NewElementRules#getName()
	 * @see #getNewElementRules()
	 * @generated
	 */
	EAttribute getNewElementRules_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.NewElementRules#isDeletable <em>Deletable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Deletable</em>'.
	 * @see org.remus.infomngmnt.NewElementRules#isDeletable()
	 * @see #getNewElementRules()
	 * @generated
	 */
	EAttribute getNewElementRules_Deletable();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.RuleValue <em>Rule Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Value</em>'.
	 * @see org.remus.infomngmnt.RuleValue
	 * @generated
	 */
	EClass getRuleValue();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.AvailableRuleDefinitions <em>Available Rule Definitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Available Rule Definitions</em>'.
	 * @see org.remus.infomngmnt.AvailableRuleDefinitions
	 * @generated
	 */
	EClass getAvailableRuleDefinitions();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.infomngmnt.AvailableRuleDefinitions#getNewElementRules <em>New Element Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>New Element Rules</em>'.
	 * @see org.remus.infomngmnt.AvailableRuleDefinitions#getNewElementRules()
	 * @see #getAvailableRuleDefinitions()
	 * @generated
	 */
	EReference getAvailableRuleDefinitions_NewElementRules();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.RemusTransferType <em>Remus Transfer Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Remus Transfer Type</em>'.
	 * @see org.remus.infomngmnt.RemusTransferType
	 * @generated
	 */
	EClass getRemusTransferType();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.RemusTransferType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.remus.infomngmnt.RemusTransferType#getName()
	 * @see #getRemusTransferType()
	 * @generated
	 */
	EAttribute getRemusTransferType_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.RemusTransferType#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.remus.infomngmnt.RemusTransferType#getId()
	 * @see #getRemusTransferType()
	 * @generated
	 */
	EAttribute getRemusTransferType_Id();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.infomngmnt.RemusTransferType#getActions <em>Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Actions</em>'.
	 * @see org.remus.infomngmnt.RemusTransferType#getActions()
	 * @see #getRemusTransferType()
	 * @generated
	 */
	EReference getRemusTransferType_Actions();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.RuleAction <em>Rule Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Action</em>'.
	 * @see org.remus.infomngmnt.RuleAction
	 * @generated
	 */
	EClass getRuleAction();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.RuleAction#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.remus.infomngmnt.RuleAction#getName()
	 * @see #getRuleAction()
	 * @generated
	 */
	EAttribute getRuleAction_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.RuleAction#getInfoTypeId <em>Info Type Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Info Type Id</em>'.
	 * @see org.remus.infomngmnt.RuleAction#getInfoTypeId()
	 * @see #getRuleAction()
	 * @generated
	 */
	EAttribute getRuleAction_InfoTypeId();

	/**
	 * Returns the meta object for the containment reference '{@link org.remus.infomngmnt.RuleAction#getRuleValue <em>Rule Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rule Value</em>'.
	 * @see org.remus.infomngmnt.RuleAction#getRuleValue()
	 * @see #getRuleAction()
	 * @generated
	 */
	EReference getRuleAction_RuleValue();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.RuleAction#getGroovyMatcher <em>Groovy Matcher</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Groovy Matcher</em>'.
	 * @see org.remus.infomngmnt.RuleAction#getGroovyMatcher()
	 * @see #getRuleAction()
	 * @generated
	 */
	EAttribute getRuleAction_GroovyMatcher();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.RuleAction#getPostProcessingInstructions <em>Post Processing Instructions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Post Processing Instructions</em>'.
	 * @see org.remus.infomngmnt.RuleAction#getPostProcessingInstructions()
	 * @see #getRuleAction()
	 * @generated
	 */
	EAttribute getRuleAction_PostProcessingInstructions();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.RuleResult <em>Rule Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Result</em>'.
	 * @see org.remus.infomngmnt.RuleResult
	 * @generated
	 */
	EClass getRuleResult();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.RuleResult#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.remus.infomngmnt.RuleResult#getValue()
	 * @see #getRuleResult()
	 * @generated
	 */
	EAttribute getRuleResult_Value();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.infomngmnt.RuleResult#getActions <em>Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Actions</em>'.
	 * @see org.remus.infomngmnt.RuleResult#getActions()
	 * @see #getRuleResult()
	 * @generated
	 */
	EReference getRuleResult_Actions();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.RuleResult#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.remus.infomngmnt.RuleResult#getDescription()
	 * @see #getRuleResult()
	 * @generated
	 */
	EAttribute getRuleResult_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.RuleResult#getTransferType <em>Transfer Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Transfer Type</em>'.
	 * @see org.remus.infomngmnt.RuleResult#getTransferType()
	 * @see #getRuleResult()
	 * @generated
	 */
	EAttribute getRuleResult_TransferType();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.RemoteRepository <em>Remote Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Remote Repository</em>'.
	 * @see org.remus.infomngmnt.RemoteRepository
	 * @generated
	 */
	EClass getRemoteRepository();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.RemoteObject <em>Remote Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Remote Object</em>'.
	 * @see org.remus.infomngmnt.RemoteObject
	 * @generated
	 */
	EClass getRemoteObject();

	/**
	 * Returns the meta object for the attribute list '{@link org.remus.infomngmnt.RemoteObject#getPossibleInfoTypeId <em>Possible Info Type Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Possible Info Type Id</em>'.
	 * @see org.remus.infomngmnt.RemoteObject#getPossibleInfoTypeId()
	 * @see #getRemoteObject()
	 * @generated
	 */
	EAttribute getRemoteObject_PossibleInfoTypeId();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.RemoteObject#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.remus.infomngmnt.RemoteObject#getId()
	 * @see #getRemoteObject()
	 * @generated
	 */
	EAttribute getRemoteObject_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.RemoteObject#getUrl <em>Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Url</em>'.
	 * @see org.remus.infomngmnt.RemoteObject#getUrl()
	 * @see #getRemoteObject()
	 * @generated
	 */
	EAttribute getRemoteObject_Url();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.RemoteObject#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.remus.infomngmnt.RemoteObject#getName()
	 * @see #getRemoteObject()
	 * @generated
	 */
	EAttribute getRemoteObject_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.RemoteObject#getRepositoryTypeId <em>Repository Type Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Repository Type Id</em>'.
	 * @see org.remus.infomngmnt.RemoteObject#getRepositoryTypeId()
	 * @see #getRemoteObject()
	 * @generated
	 */
	EAttribute getRemoteObject_RepositoryTypeId();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.RemoteObject#getRepositoryTypeObjectId <em>Repository Type Object Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Repository Type Object Id</em>'.
	 * @see org.remus.infomngmnt.RemoteObject#getRepositoryTypeObjectId()
	 * @see #getRemoteObject()
	 * @generated
	 */
	EAttribute getRemoteObject_RepositoryTypeObjectId();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.RemoteObject#getWrappedObject <em>Wrapped Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Wrapped Object</em>'.
	 * @see org.remus.infomngmnt.RemoteObject#getWrappedObject()
	 * @see #getRemoteObject()
	 * @generated
	 */
	EAttribute getRemoteObject_WrappedObject();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.RemoteObject#getHash <em>Hash</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hash</em>'.
	 * @see org.remus.infomngmnt.RemoteObject#getHash()
	 * @see #getRemoteObject()
	 * @generated
	 */
	EAttribute getRemoteObject_Hash();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.RemoteContainer <em>Remote Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Remote Container</em>'.
	 * @see org.remus.infomngmnt.RemoteContainer
	 * @generated
	 */
	EClass getRemoteContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.infomngmnt.RemoteContainer#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Children</em>'.
	 * @see org.remus.infomngmnt.RemoteContainer#getChildren()
	 * @see #getRemoteContainer()
	 * @generated
	 */
	EReference getRemoteContainer_Children();

	/**
	 * Returns the meta object for the reference list '{@link org.remus.infomngmnt.RemoteContainer#getExclusionChildren <em>Exclusion Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Exclusion Children</em>'.
	 * @see org.remus.infomngmnt.RemoteContainer#getExclusionChildren()
	 * @see #getRemoteContainer()
	 * @generated
	 */
	EReference getRemoteContainer_ExclusionChildren();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.RepositoryCollection <em>Repository Collection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Repository Collection</em>'.
	 * @see org.remus.infomngmnt.RepositoryCollection
	 * @generated
	 */
	EClass getRepositoryCollection();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.infomngmnt.RepositoryCollection#getRepositories <em>Repositories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Repositories</em>'.
	 * @see org.remus.infomngmnt.RepositoryCollection#getRepositories()
	 * @see #getRepositoryCollection()
	 * @generated
	 */
	EReference getRepositoryCollection_Repositories();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.SynchronizationMetadata <em>Synchronization Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Synchronization Metadata</em>'.
	 * @see org.remus.infomngmnt.SynchronizationMetadata
	 * @generated
	 */
	EClass getSynchronizationMetadata();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.SynchronizationMetadata#getRepositoryId <em>Repository Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Repository Id</em>'.
	 * @see org.remus.infomngmnt.SynchronizationMetadata#getRepositoryId()
	 * @see #getSynchronizationMetadata()
	 * @generated
	 */
	EAttribute getSynchronizationMetadata_RepositoryId();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.SynchronizationMetadata#getUrl <em>Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Url</em>'.
	 * @see org.remus.infomngmnt.SynchronizationMetadata#getUrl()
	 * @see #getSynchronizationMetadata()
	 * @generated
	 */
	EAttribute getSynchronizationMetadata_Url();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.SynchronizationMetadata#isReadonly <em>Readonly</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Readonly</em>'.
	 * @see org.remus.infomngmnt.SynchronizationMetadata#isReadonly()
	 * @see #getSynchronizationMetadata()
	 * @generated
	 */
	EAttribute getSynchronizationMetadata_Readonly();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.SynchronizationMetadata#getLastSynchronisation <em>Last Synchronisation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Synchronisation</em>'.
	 * @see org.remus.infomngmnt.SynchronizationMetadata#getLastSynchronisation()
	 * @see #getSynchronizationMetadata()
	 * @generated
	 */
	EAttribute getSynchronizationMetadata_LastSynchronisation();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.SynchronizationMetadata#getHash <em>Hash</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hash</em>'.
	 * @see org.remus.infomngmnt.SynchronizationMetadata#getHash()
	 * @see #getSynchronizationMetadata()
	 * @generated
	 */
	EAttribute getSynchronizationMetadata_Hash();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.SynchronizationMetadata#getSyncState <em>Sync State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sync State</em>'.
	 * @see org.remus.infomngmnt.SynchronizationMetadata#getSyncState()
	 * @see #getSynchronizationMetadata()
	 * @generated
	 */
	EAttribute getSynchronizationMetadata_SyncState();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.ChangeSet <em>Change Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Change Set</em>'.
	 * @see org.remus.infomngmnt.ChangeSet
	 * @generated
	 */
	EClass getChangeSet();

	/**
	 * Returns the meta object for the reference '{@link org.remus.infomngmnt.ChangeSet#getTargetCategory <em>Target Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Category</em>'.
	 * @see org.remus.infomngmnt.ChangeSet#getTargetCategory()
	 * @see #getChangeSet()
	 * @generated
	 */
	EReference getChangeSet_TargetCategory();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.infomngmnt.ChangeSet#getChangeSetItems <em>Change Set Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Change Set Items</em>'.
	 * @see org.remus.infomngmnt.ChangeSet#getChangeSetItems()
	 * @see #getChangeSet()
	 * @generated
	 */
	EReference getChangeSet_ChangeSetItems();

	/**
	 * Returns the meta object for the reference '{@link org.remus.infomngmnt.ChangeSet#getRepository <em>Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Repository</em>'.
	 * @see org.remus.infomngmnt.ChangeSet#getRepository()
	 * @see #getChangeSet()
	 * @generated
	 */
	EReference getChangeSet_Repository();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.ChangeSetItem <em>Change Set Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Change Set Item</em>'.
	 * @see org.remus.infomngmnt.ChangeSetItem
	 * @generated
	 */
	EClass getChangeSetItem();

	/**
	 * Returns the meta object for the containment reference '{@link org.remus.infomngmnt.ChangeSetItem#getRemoteConvertedContainer <em>Remote Converted Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Remote Converted Container</em>'.
	 * @see org.remus.infomngmnt.ChangeSetItem#getRemoteConvertedContainer()
	 * @see #getChangeSetItem()
	 * @generated
	 */
	EReference getChangeSetItem_RemoteConvertedContainer();

	/**
	 * Returns the meta object for the containment reference '{@link org.remus.infomngmnt.ChangeSetItem#getRemoteOriginalObject <em>Remote Original Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Remote Original Object</em>'.
	 * @see org.remus.infomngmnt.ChangeSetItem#getRemoteOriginalObject()
	 * @see #getChangeSetItem()
	 * @generated
	 */
	EReference getChangeSetItem_RemoteOriginalObject();

	/**
	 * Returns the meta object for the reference '{@link org.remus.infomngmnt.ChangeSetItem#getLocalContainer <em>Local Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Local Container</em>'.
	 * @see org.remus.infomngmnt.ChangeSetItem#getLocalContainer()
	 * @see #getChangeSetItem()
	 * @generated
	 */
	EReference getChangeSetItem_LocalContainer();

	/**
	 * Returns the meta object for the map '{@link org.remus.infomngmnt.ChangeSetItem#getSyncCategoryActionMap <em>Sync Category Action Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Sync Category Action Map</em>'.
	 * @see org.remus.infomngmnt.ChangeSetItem#getSyncCategoryActionMap()
	 * @see #getChangeSetItem()
	 * @generated
	 */
	EReference getChangeSetItem_SyncCategoryActionMap();

	/**
	 * Returns the meta object for the map '{@link org.remus.infomngmnt.ChangeSetItem#getSyncObjectActionMap <em>Sync Object Action Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Sync Object Action Map</em>'.
	 * @see org.remus.infomngmnt.ChangeSetItem#getSyncObjectActionMap()
	 * @see #getChangeSetItem()
	 * @generated
	 */
	EReference getChangeSetItem_SyncObjectActionMap();

	/**
	 * Returns the meta object for the map '{@link org.remus.infomngmnt.ChangeSetItem#getRemoteFullObjectMap <em>Remote Full Object Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Remote Full Object Map</em>'.
	 * @see org.remus.infomngmnt.ChangeSetItem#getRemoteFullObjectMap()
	 * @see #getChangeSetItem()
	 * @generated
	 */
	EReference getChangeSetItem_RemoteFullObjectMap();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Category To Synchronization Action Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Category To Synchronization Action Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="org.remus.infomngmnt.Category"
	 *        valueDataType="org.remus.infomngmnt.SynchronizationAction"
	 * @generated
	 */
	EClass getCategoryToSynchronizationActionMap();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getCategoryToSynchronizationActionMap()
	 * @generated
	 */
	EReference getCategoryToSynchronizationActionMap_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getCategoryToSynchronizationActionMap()
	 * @generated
	 */
	EAttribute getCategoryToSynchronizationActionMap_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Information Unit List Item To Information Unit Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Information Unit List Item To Information Unit Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="org.remus.infomngmnt.InformationUnitListItem"
	 *        valueType="org.remus.infomngmnt.InformationUnit" valueContainment="true"
	 * @generated
	 */
	EClass getInformationUnitListItemToInformationUnitMap();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getInformationUnitListItemToInformationUnitMap()
	 * @generated
	 */
	EReference getInformationUnitListItemToInformationUnitMap_Key();

	/**
	 * Returns the meta object for the containment reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getInformationUnitListItemToInformationUnitMap()
	 * @generated
	 */
	EReference getInformationUnitListItemToInformationUnitMap_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Synchronizable Object To Synchronization Action Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Synchronizable Object To Synchronization Action Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="org.remus.infomngmnt.SynchronizableObject"
	 *        valueDataType="org.remus.infomngmnt.SynchronizationAction"
	 * @generated
	 */
	EClass getSynchronizableObjectToSynchronizationActionMap();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getSynchronizableObjectToSynchronizationActionMap()
	 * @generated
	 */
	EReference getSynchronizableObjectToSynchronizationActionMap_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getSynchronizableObjectToSynchronizationActionMap()
	 * @generated
	 */
	EAttribute getSynchronizableObjectToSynchronizationActionMap_Value();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.Tag <em>Tag</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tag</em>'.
	 * @see org.remus.infomngmnt.Tag
	 * @generated
	 */
	EClass getTag();

	/**
	 * Returns the meta object for the attribute list '{@link org.remus.infomngmnt.Tag#getInfoUnits <em>Info Units</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Info Units</em>'.
	 * @see org.remus.infomngmnt.Tag#getInfoUnits()
	 * @see #getTag()
	 * @generated
	 */
	EAttribute getTag_InfoUnits();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.Tag#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.remus.infomngmnt.Tag#getName()
	 * @see #getTag()
	 * @generated
	 */
	EAttribute getTag_Name();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.AvailableTags <em>Available Tags</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Available Tags</em>'.
	 * @see org.remus.infomngmnt.AvailableTags
	 * @generated
	 */
	EClass getAvailableTags();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.infomngmnt.AvailableTags#getTags <em>Tags</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tags</em>'.
	 * @see org.remus.infomngmnt.AvailableTags#getTags()
	 * @see #getAvailableTags()
	 * @generated
	 */
	EReference getAvailableTags_Tags();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.SynchronizableObject <em>Synchronizable Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Synchronizable Object</em>'.
	 * @see org.remus.infomngmnt.SynchronizableObject
	 * @generated
	 */
	EClass getSynchronizableObject();

	/**
	 * Returns the meta object for the containment reference '{@link org.remus.infomngmnt.SynchronizableObject#getSynchronizationMetaData <em>Synchronization Meta Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Synchronization Meta Data</em>'.
	 * @see org.remus.infomngmnt.SynchronizableObject#getSynchronizationMetaData()
	 * @see #getSynchronizableObject()
	 * @generated
	 */
	EReference getSynchronizableObject_SynchronizationMetaData();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.CalendarEntry <em>Calendar Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Calendar Entry</em>'.
	 * @see org.remus.infomngmnt.CalendarEntry
	 * @generated
	 */
	EClass getCalendarEntry();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.CalendarEntry#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.remus.infomngmnt.CalendarEntry#getId()
	 * @see #getCalendarEntry()
	 * @generated
	 */
	EAttribute getCalendarEntry_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.CalendarEntry#getStart <em>Start</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start</em>'.
	 * @see org.remus.infomngmnt.CalendarEntry#getStart()
	 * @see #getCalendarEntry()
	 * @generated
	 */
	EAttribute getCalendarEntry_Start();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.CalendarEntry#getEnd <em>End</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>End</em>'.
	 * @see org.remus.infomngmnt.CalendarEntry#getEnd()
	 * @see #getCalendarEntry()
	 * @generated
	 */
	EAttribute getCalendarEntry_End();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.CalendarEntry#getEntryType <em>Entry Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Entry Type</em>'.
	 * @see org.remus.infomngmnt.CalendarEntry#getEntryType()
	 * @see #getCalendarEntry()
	 * @generated
	 */
	EAttribute getCalendarEntry_EntryType();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.CalendarEntry#getReminder <em>Reminder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reminder</em>'.
	 * @see org.remus.infomngmnt.CalendarEntry#getReminder()
	 * @see #getCalendarEntry()
	 * @generated
	 */
	EAttribute getCalendarEntry_Reminder();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.CalendarEntry#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see org.remus.infomngmnt.CalendarEntry#getTitle()
	 * @see #getCalendarEntry()
	 * @generated
	 */
	EAttribute getCalendarEntry_Title();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.Notification <em>Notification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Notification</em>'.
	 * @see org.remus.infomngmnt.Notification
	 * @generated
	 */
	EClass getNotification();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.Notification#getTimeStamp <em>Time Stamp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time Stamp</em>'.
	 * @see org.remus.infomngmnt.Notification#getTimeStamp()
	 * @see #getNotification()
	 * @generated
	 */
	EAttribute getNotification_TimeStamp();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.Notification#getImportance <em>Importance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Importance</em>'.
	 * @see org.remus.infomngmnt.Notification#getImportance()
	 * @see #getNotification()
	 * @generated
	 */
	EAttribute getNotification_Importance();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.Notification#getSeverity <em>Severity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Severity</em>'.
	 * @see org.remus.infomngmnt.Notification#getSeverity()
	 * @see #getNotification()
	 * @generated
	 */
	EAttribute getNotification_Severity();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.Notification#isNoticed <em>Noticed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Noticed</em>'.
	 * @see org.remus.infomngmnt.Notification#isNoticed()
	 * @see #getNotification()
	 * @generated
	 */
	EAttribute getNotification_Noticed();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.Notification#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see org.remus.infomngmnt.Notification#getMessage()
	 * @see #getNotification()
	 * @generated
	 */
	EAttribute getNotification_Message();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.Notification#getDetails <em>Details</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Details</em>'.
	 * @see org.remus.infomngmnt.Notification#getDetails()
	 * @see #getNotification()
	 * @generated
	 */
	EAttribute getNotification_Details();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.infomngmnt.Notification#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Children</em>'.
	 * @see org.remus.infomngmnt.Notification#getChildren()
	 * @see #getNotification()
	 * @generated
	 */
	EReference getNotification_Children();

	/**
	 * Returns the meta object for the attribute list '{@link org.remus.infomngmnt.Notification#getAffectedInfoUnitIds <em>Affected Info Unit Ids</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Affected Info Unit Ids</em>'.
	 * @see org.remus.infomngmnt.Notification#getAffectedInfoUnitIds()
	 * @see #getNotification()
	 * @generated
	 */
	EAttribute getNotification_AffectedInfoUnitIds();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.Notification#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source</em>'.
	 * @see org.remus.infomngmnt.Notification#getSource()
	 * @see #getNotification()
	 * @generated
	 */
	EAttribute getNotification_Source();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.Notification#getImage <em>Image</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Image</em>'.
	 * @see org.remus.infomngmnt.Notification#getImage()
	 * @see #getNotification()
	 * @generated
	 */
	EAttribute getNotification_Image();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.NotificationCollection <em>Notification Collection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Notification Collection</em>'.
	 * @see org.remus.infomngmnt.NotificationCollection
	 * @generated
	 */
	EClass getNotificationCollection();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.infomngmnt.NotificationCollection#getNotifcations <em>Notifcations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Notifcations</em>'.
	 * @see org.remus.infomngmnt.NotificationCollection#getNotifcations()
	 * @see #getNotificationCollection()
	 * @generated
	 */
	EReference getNotificationCollection_Notifcations();

	/**
	 * Returns the meta object for enum '{@link org.remus.infomngmnt.SynchronizationState <em>Synchronization State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Synchronization State</em>'.
	 * @see org.remus.infomngmnt.SynchronizationState
	 * @generated
	 */
	EEnum getSynchronizationState();

	/**
	 * Returns the meta object for enum '{@link org.remus.infomngmnt.SynchronizationAction <em>Synchronization Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Synchronization Action</em>'.
	 * @see org.remus.infomngmnt.SynchronizationAction
	 * @generated
	 */
	EEnum getSynchronizationAction();

	/**
	 * Returns the meta object for enum '{@link org.remus.infomngmnt.CalendarEntryType <em>Calendar Entry Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Calendar Entry Type</em>'.
	 * @see org.remus.infomngmnt.CalendarEntryType
	 * @generated
	 */
	EEnum getCalendarEntryType();

	/**
	 * Returns the meta object for enum '{@link org.remus.infomngmnt.NotificationImportance <em>Notification Importance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Notification Importance</em>'.
	 * @see org.remus.infomngmnt.NotificationImportance
	 * @generated
	 */
	EEnum getNotificationImportance();

	/**
	 * Returns the meta object for enum '{@link org.remus.infomngmnt.Severity <em>Severity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Severity</em>'.
	 * @see org.remus.infomngmnt.Severity
	 * @generated
	 */
	EEnum getSeverity();

	/**
	 * Returns the meta object for data type '{@link java.lang.Object <em>Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Object</em>'.
	 * @see java.lang.Object
	 * @model instanceClass="java.lang.Object"
	 * @generated
	 */
	EDataType getObject();

	/**
	 * Returns the meta object for data type '{@link org.remus.infomngmnt.core.remote.IRepository <em>IRepository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>IRepository</em>'.
	 * @see org.remus.infomngmnt.core.remote.IRepository
	 * @model instanceClass="org.remus.infomngmnt.core.remote.IRepository" serializeable="false"
	 * @generated
	 */
	EDataType getIRepository();

	/**
	 * Returns the meta object for data type '{@link org.remus.infomngmnt.core.extension.TransferWrapper <em>Transfer Wrapper</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Transfer Wrapper</em>'.
	 * @see org.remus.infomngmnt.core.extension.TransferWrapper
	 * @model instanceClass="org.remus.infomngmnt.core.extension.TransferWrapper" serializeable="false"
	 * @generated
	 */
	EDataType getTransferWrapper();

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
		 * The meta object literal for the '<em><b>References</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INFORMATION_UNIT__REFERENCES = eINSTANCE.getInformationUnit_References();

		/**
		 * The meta object literal for the '<em><b>Links</b></em>' containment reference list feature.
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
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INFORMATION_UNIT__DESCRIPTION = eINSTANCE.getInformationUnit_Description();

		/**
		 * The meta object literal for the '<em><b>Keywords</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INFORMATION_UNIT__KEYWORDS = eINSTANCE.getInformationUnit_Keywords();

		/**
		 * The meta object literal for the '<em><b>Calendar Entry</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INFORMATION_UNIT__CALENDAR_ENTRY = eINSTANCE.getInformationUnit_CalendarEntry();

		/**
		 * The meta object literal for the '<em><b>Binary References</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INFORMATION_UNIT__BINARY_REFERENCES = eINSTANCE.getInformationUnit_BinaryReferences();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.BinaryReferenceImpl <em>Binary Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.BinaryReferenceImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getBinaryReference()
		 * @generated
		 */
		EClass BINARY_REFERENCE = eINSTANCE.getBinaryReference();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BINARY_REFERENCE__ID = eINSTANCE.getBinaryReference_Id();

		/**
		 * The meta object literal for the '<em><b>Project Relative Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BINARY_REFERENCE__PROJECT_RELATIVE_PATH = eINSTANCE.getBinaryReference_ProjectRelativePath();

		/**
		 * The meta object literal for the '<em><b>Dirty</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BINARY_REFERENCE__DIRTY = eINSTANCE.getBinaryReference_Dirty();

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
		 * The meta object literal for the '<em><b>Available Tags</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLICATION_ROOT__AVAILABLE_TAGS = eINSTANCE.getApplicationRoot_AvailableTags();

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

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.LinkImpl <em>Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.LinkImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getLink()
		 * @generated
		 */
		EClass LINK = eINSTANCE.getLink();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK__TARGET = eINSTANCE.getLink_Target();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.AdapterImpl <em>Adapter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.AdapterImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getAdapter()
		 * @generated
		 */
		EClass ADAPTER = eINSTANCE.getAdapter();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.RecentlyUsedKeywordsImpl <em>Recently Used Keywords</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.RecentlyUsedKeywordsImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRecentlyUsedKeywords()
		 * @generated
		 */
		EClass RECENTLY_USED_KEYWORDS = eINSTANCE.getRecentlyUsedKeywords();

		/**
		 * The meta object literal for the '<em><b>Maxlength</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RECENTLY_USED_KEYWORDS__MAXLENGTH = eINSTANCE.getRecentlyUsedKeywords_Maxlength();

		/**
		 * The meta object literal for the '<em><b>Keywords</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RECENTLY_USED_KEYWORDS__KEYWORDS = eINSTANCE.getRecentlyUsedKeywords_Keywords();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.NewElementRulesImpl <em>New Element Rules</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.NewElementRulesImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getNewElementRules()
		 * @generated
		 */
		EClass NEW_ELEMENT_RULES = eINSTANCE.getNewElementRules();

		/**
		 * The meta object literal for the '<em><b>Transfer Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NEW_ELEMENT_RULES__TRANSFER_TYPES = eINSTANCE.getNewElementRules_TransferTypes();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NEW_ELEMENT_RULES__NAME = eINSTANCE.getNewElementRules_Name();

		/**
		 * The meta object literal for the '<em><b>Deletable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NEW_ELEMENT_RULES__DELETABLE = eINSTANCE.getNewElementRules_Deletable();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.RuleValueImpl <em>Rule Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.RuleValueImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRuleValue()
		 * @generated
		 */
		EClass RULE_VALUE = eINSTANCE.getRuleValue();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.AvailableRuleDefinitionsImpl <em>Available Rule Definitions</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.AvailableRuleDefinitionsImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getAvailableRuleDefinitions()
		 * @generated
		 */
		EClass AVAILABLE_RULE_DEFINITIONS = eINSTANCE.getAvailableRuleDefinitions();

		/**
		 * The meta object literal for the '<em><b>New Element Rules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AVAILABLE_RULE_DEFINITIONS__NEW_ELEMENT_RULES = eINSTANCE.getAvailableRuleDefinitions_NewElementRules();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.RemusTransferTypeImpl <em>Remus Transfer Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.RemusTransferTypeImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRemusTransferType()
		 * @generated
		 */
		EClass REMUS_TRANSFER_TYPE = eINSTANCE.getRemusTransferType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REMUS_TRANSFER_TYPE__NAME = eINSTANCE.getRemusTransferType_Name();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REMUS_TRANSFER_TYPE__ID = eINSTANCE.getRemusTransferType_Id();

		/**
		 * The meta object literal for the '<em><b>Actions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REMUS_TRANSFER_TYPE__ACTIONS = eINSTANCE.getRemusTransferType_Actions();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.RuleActionImpl <em>Rule Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.RuleActionImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRuleAction()
		 * @generated
		 */
		EClass RULE_ACTION = eINSTANCE.getRuleAction();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_ACTION__NAME = eINSTANCE.getRuleAction_Name();

		/**
		 * The meta object literal for the '<em><b>Info Type Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_ACTION__INFO_TYPE_ID = eINSTANCE.getRuleAction_InfoTypeId();

		/**
		 * The meta object literal for the '<em><b>Rule Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_ACTION__RULE_VALUE = eINSTANCE.getRuleAction_RuleValue();

		/**
		 * The meta object literal for the '<em><b>Groovy Matcher</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_ACTION__GROOVY_MATCHER = eINSTANCE.getRuleAction_GroovyMatcher();

		/**
		 * The meta object literal for the '<em><b>Post Processing Instructions</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_ACTION__POST_PROCESSING_INSTRUCTIONS = eINSTANCE.getRuleAction_PostProcessingInstructions();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.RuleResultImpl <em>Rule Result</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.RuleResultImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRuleResult()
		 * @generated
		 */
		EClass RULE_RESULT = eINSTANCE.getRuleResult();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_RESULT__VALUE = eINSTANCE.getRuleResult_Value();

		/**
		 * The meta object literal for the '<em><b>Actions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_RESULT__ACTIONS = eINSTANCE.getRuleResult_Actions();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_RESULT__DESCRIPTION = eINSTANCE.getRuleResult_Description();

		/**
		 * The meta object literal for the '<em><b>Transfer Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_RESULT__TRANSFER_TYPE = eINSTANCE.getRuleResult_TransferType();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.RemoteRepositoryImpl <em>Remote Repository</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.RemoteRepositoryImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRemoteRepository()
		 * @generated
		 */
		EClass REMOTE_REPOSITORY = eINSTANCE.getRemoteRepository();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.RemoteObjectImpl <em>Remote Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.RemoteObjectImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRemoteObject()
		 * @generated
		 */
		EClass REMOTE_OBJECT = eINSTANCE.getRemoteObject();

		/**
		 * The meta object literal for the '<em><b>Possible Info Type Id</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REMOTE_OBJECT__POSSIBLE_INFO_TYPE_ID = eINSTANCE.getRemoteObject_PossibleInfoTypeId();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REMOTE_OBJECT__ID = eINSTANCE.getRemoteObject_Id();

		/**
		 * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REMOTE_OBJECT__URL = eINSTANCE.getRemoteObject_Url();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REMOTE_OBJECT__NAME = eINSTANCE.getRemoteObject_Name();

		/**
		 * The meta object literal for the '<em><b>Repository Type Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REMOTE_OBJECT__REPOSITORY_TYPE_ID = eINSTANCE.getRemoteObject_RepositoryTypeId();

		/**
		 * The meta object literal for the '<em><b>Repository Type Object Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REMOTE_OBJECT__REPOSITORY_TYPE_OBJECT_ID = eINSTANCE.getRemoteObject_RepositoryTypeObjectId();

		/**
		 * The meta object literal for the '<em><b>Wrapped Object</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REMOTE_OBJECT__WRAPPED_OBJECT = eINSTANCE.getRemoteObject_WrappedObject();

		/**
		 * The meta object literal for the '<em><b>Hash</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REMOTE_OBJECT__HASH = eINSTANCE.getRemoteObject_Hash();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.RemoteContainerImpl <em>Remote Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.RemoteContainerImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRemoteContainer()
		 * @generated
		 */
		EClass REMOTE_CONTAINER = eINSTANCE.getRemoteContainer();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REMOTE_CONTAINER__CHILDREN = eINSTANCE.getRemoteContainer_Children();

		/**
		 * The meta object literal for the '<em><b>Exclusion Children</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REMOTE_CONTAINER__EXCLUSION_CHILDREN = eINSTANCE.getRemoteContainer_ExclusionChildren();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.RepositoryCollectionImpl <em>Repository Collection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.RepositoryCollectionImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRepositoryCollection()
		 * @generated
		 */
		EClass REPOSITORY_COLLECTION = eINSTANCE.getRepositoryCollection();

		/**
		 * The meta object literal for the '<em><b>Repositories</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORY_COLLECTION__REPOSITORIES = eINSTANCE.getRepositoryCollection_Repositories();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.SynchronizationMetadataImpl <em>Synchronization Metadata</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.SynchronizationMetadataImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getSynchronizationMetadata()
		 * @generated
		 */
		EClass SYNCHRONIZATION_METADATA = eINSTANCE.getSynchronizationMetadata();

		/**
		 * The meta object literal for the '<em><b>Repository Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYNCHRONIZATION_METADATA__REPOSITORY_ID = eINSTANCE.getSynchronizationMetadata_RepositoryId();

		/**
		 * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYNCHRONIZATION_METADATA__URL = eINSTANCE.getSynchronizationMetadata_Url();

		/**
		 * The meta object literal for the '<em><b>Readonly</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYNCHRONIZATION_METADATA__READONLY = eINSTANCE.getSynchronizationMetadata_Readonly();

		/**
		 * The meta object literal for the '<em><b>Last Synchronisation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYNCHRONIZATION_METADATA__LAST_SYNCHRONISATION = eINSTANCE.getSynchronizationMetadata_LastSynchronisation();

		/**
		 * The meta object literal for the '<em><b>Hash</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYNCHRONIZATION_METADATA__HASH = eINSTANCE.getSynchronizationMetadata_Hash();

		/**
		 * The meta object literal for the '<em><b>Sync State</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYNCHRONIZATION_METADATA__SYNC_STATE = eINSTANCE.getSynchronizationMetadata_SyncState();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.ChangeSetImpl <em>Change Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.ChangeSetImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getChangeSet()
		 * @generated
		 */
		EClass CHANGE_SET = eINSTANCE.getChangeSet();

		/**
		 * The meta object literal for the '<em><b>Target Category</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_SET__TARGET_CATEGORY = eINSTANCE.getChangeSet_TargetCategory();

		/**
		 * The meta object literal for the '<em><b>Change Set Items</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_SET__CHANGE_SET_ITEMS = eINSTANCE.getChangeSet_ChangeSetItems();

		/**
		 * The meta object literal for the '<em><b>Repository</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_SET__REPOSITORY = eINSTANCE.getChangeSet_Repository();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.ChangeSetItemImpl <em>Change Set Item</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.ChangeSetItemImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getChangeSetItem()
		 * @generated
		 */
		EClass CHANGE_SET_ITEM = eINSTANCE.getChangeSetItem();

		/**
		 * The meta object literal for the '<em><b>Remote Converted Container</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_SET_ITEM__REMOTE_CONVERTED_CONTAINER = eINSTANCE.getChangeSetItem_RemoteConvertedContainer();

		/**
		 * The meta object literal for the '<em><b>Remote Original Object</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_SET_ITEM__REMOTE_ORIGINAL_OBJECT = eINSTANCE.getChangeSetItem_RemoteOriginalObject();

		/**
		 * The meta object literal for the '<em><b>Local Container</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_SET_ITEM__LOCAL_CONTAINER = eINSTANCE.getChangeSetItem_LocalContainer();

		/**
		 * The meta object literal for the '<em><b>Sync Category Action Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_SET_ITEM__SYNC_CATEGORY_ACTION_MAP = eINSTANCE.getChangeSetItem_SyncCategoryActionMap();

		/**
		 * The meta object literal for the '<em><b>Sync Object Action Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_SET_ITEM__SYNC_OBJECT_ACTION_MAP = eINSTANCE.getChangeSetItem_SyncObjectActionMap();

		/**
		 * The meta object literal for the '<em><b>Remote Full Object Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHANGE_SET_ITEM__REMOTE_FULL_OBJECT_MAP = eINSTANCE.getChangeSetItem_RemoteFullObjectMap();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.CategoryToSynchronizationActionMapImpl <em>Category To Synchronization Action Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.CategoryToSynchronizationActionMapImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getCategoryToSynchronizationActionMap()
		 * @generated
		 */
		EClass CATEGORY_TO_SYNCHRONIZATION_ACTION_MAP = eINSTANCE.getCategoryToSynchronizationActionMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CATEGORY_TO_SYNCHRONIZATION_ACTION_MAP__KEY = eINSTANCE.getCategoryToSynchronizationActionMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CATEGORY_TO_SYNCHRONIZATION_ACTION_MAP__VALUE = eINSTANCE.getCategoryToSynchronizationActionMap_Value();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.InformationUnitListItemToInformationUnitMapImpl <em>Information Unit List Item To Information Unit Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.InformationUnitListItemToInformationUnitMapImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getInformationUnitListItemToInformationUnitMap()
		 * @generated
		 */
		EClass INFORMATION_UNIT_LIST_ITEM_TO_INFORMATION_UNIT_MAP = eINSTANCE.getInformationUnitListItemToInformationUnitMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INFORMATION_UNIT_LIST_ITEM_TO_INFORMATION_UNIT_MAP__KEY = eINSTANCE.getInformationUnitListItemToInformationUnitMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INFORMATION_UNIT_LIST_ITEM_TO_INFORMATION_UNIT_MAP__VALUE = eINSTANCE.getInformationUnitListItemToInformationUnitMap_Value();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.SynchronizableObjectToSynchronizationActionMapImpl <em>Synchronizable Object To Synchronization Action Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.SynchronizableObjectToSynchronizationActionMapImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getSynchronizableObjectToSynchronizationActionMap()
		 * @generated
		 */
		EClass SYNCHRONIZABLE_OBJECT_TO_SYNCHRONIZATION_ACTION_MAP = eINSTANCE.getSynchronizableObjectToSynchronizationActionMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYNCHRONIZABLE_OBJECT_TO_SYNCHRONIZATION_ACTION_MAP__KEY = eINSTANCE.getSynchronizableObjectToSynchronizationActionMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYNCHRONIZABLE_OBJECT_TO_SYNCHRONIZATION_ACTION_MAP__VALUE = eINSTANCE.getSynchronizableObjectToSynchronizationActionMap_Value();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.TagImpl <em>Tag</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.TagImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getTag()
		 * @generated
		 */
		EClass TAG = eINSTANCE.getTag();

		/**
		 * The meta object literal for the '<em><b>Info Units</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TAG__INFO_UNITS = eINSTANCE.getTag_InfoUnits();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TAG__NAME = eINSTANCE.getTag_Name();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.AvailableTagsImpl <em>Available Tags</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.AvailableTagsImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getAvailableTags()
		 * @generated
		 */
		EClass AVAILABLE_TAGS = eINSTANCE.getAvailableTags();

		/**
		 * The meta object literal for the '<em><b>Tags</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AVAILABLE_TAGS__TAGS = eINSTANCE.getAvailableTags_Tags();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.SynchronizableObjectImpl <em>Synchronizable Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.SynchronizableObjectImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getSynchronizableObject()
		 * @generated
		 */
		EClass SYNCHRONIZABLE_OBJECT = eINSTANCE.getSynchronizableObject();

		/**
		 * The meta object literal for the '<em><b>Synchronization Meta Data</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA = eINSTANCE.getSynchronizableObject_SynchronizationMetaData();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.CalendarEntryImpl <em>Calendar Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.CalendarEntryImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getCalendarEntry()
		 * @generated
		 */
		EClass CALENDAR_ENTRY = eINSTANCE.getCalendarEntry();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CALENDAR_ENTRY__ID = eINSTANCE.getCalendarEntry_Id();

		/**
		 * The meta object literal for the '<em><b>Start</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CALENDAR_ENTRY__START = eINSTANCE.getCalendarEntry_Start();

		/**
		 * The meta object literal for the '<em><b>End</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CALENDAR_ENTRY__END = eINSTANCE.getCalendarEntry_End();

		/**
		 * The meta object literal for the '<em><b>Entry Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CALENDAR_ENTRY__ENTRY_TYPE = eINSTANCE.getCalendarEntry_EntryType();

		/**
		 * The meta object literal for the '<em><b>Reminder</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CALENDAR_ENTRY__REMINDER = eINSTANCE.getCalendarEntry_Reminder();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CALENDAR_ENTRY__TITLE = eINSTANCE.getCalendarEntry_Title();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.NotificationImpl <em>Notification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.NotificationImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getNotification()
		 * @generated
		 */
		EClass NOTIFICATION = eINSTANCE.getNotification();

		/**
		 * The meta object literal for the '<em><b>Time Stamp</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NOTIFICATION__TIME_STAMP = eINSTANCE.getNotification_TimeStamp();

		/**
		 * The meta object literal for the '<em><b>Importance</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NOTIFICATION__IMPORTANCE = eINSTANCE.getNotification_Importance();

		/**
		 * The meta object literal for the '<em><b>Severity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NOTIFICATION__SEVERITY = eINSTANCE.getNotification_Severity();

		/**
		 * The meta object literal for the '<em><b>Noticed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NOTIFICATION__NOTICED = eINSTANCE.getNotification_Noticed();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NOTIFICATION__MESSAGE = eINSTANCE.getNotification_Message();

		/**
		 * The meta object literal for the '<em><b>Details</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NOTIFICATION__DETAILS = eINSTANCE.getNotification_Details();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NOTIFICATION__CHILDREN = eINSTANCE.getNotification_Children();

		/**
		 * The meta object literal for the '<em><b>Affected Info Unit Ids</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NOTIFICATION__AFFECTED_INFO_UNIT_IDS = eINSTANCE.getNotification_AffectedInfoUnitIds();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NOTIFICATION__SOURCE = eINSTANCE.getNotification_Source();

		/**
		 * The meta object literal for the '<em><b>Image</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NOTIFICATION__IMAGE = eINSTANCE.getNotification_Image();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.NotificationCollectionImpl <em>Notification Collection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.NotificationCollectionImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getNotificationCollection()
		 * @generated
		 */
		EClass NOTIFICATION_COLLECTION = eINSTANCE.getNotificationCollection();

		/**
		 * The meta object literal for the '<em><b>Notifcations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NOTIFICATION_COLLECTION__NOTIFCATIONS = eINSTANCE.getNotificationCollection_Notifcations();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.SynchronizationState <em>Synchronization State</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.SynchronizationState
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getSynchronizationState()
		 * @generated
		 */
		EEnum SYNCHRONIZATION_STATE = eINSTANCE.getSynchronizationState();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.SynchronizationAction <em>Synchronization Action</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.SynchronizationAction
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getSynchronizationAction()
		 * @generated
		 */
		EEnum SYNCHRONIZATION_ACTION = eINSTANCE.getSynchronizationAction();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.CalendarEntryType <em>Calendar Entry Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.CalendarEntryType
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getCalendarEntryType()
		 * @generated
		 */
		EEnum CALENDAR_ENTRY_TYPE = eINSTANCE.getCalendarEntryType();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.NotificationImportance <em>Notification Importance</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.NotificationImportance
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getNotificationImportance()
		 * @generated
		 */
		EEnum NOTIFICATION_IMPORTANCE = eINSTANCE.getNotificationImportance();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.Severity <em>Severity</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.Severity
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getSeverity()
		 * @generated
		 */
		EEnum SEVERITY = eINSTANCE.getSeverity();

		/**
		 * The meta object literal for the '<em>Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.Object
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getObject()
		 * @generated
		 */
		EDataType OBJECT = eINSTANCE.getObject();

		/**
		 * The meta object literal for the '<em>IRepository</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.core.remote.IRepository
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getIRepository()
		 * @generated
		 */
		EDataType IREPOSITORY = eINSTANCE.getIRepository();

		/**
		 * The meta object literal for the '<em>Transfer Wrapper</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.core.extension.TransferWrapper
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getTransferWrapper()
		 * @generated
		 */
		EDataType TRANSFER_WRAPPER = eINSTANCE.getTransferWrapper();

	}

} //InfomngmntPackage
