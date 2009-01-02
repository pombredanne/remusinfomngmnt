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
	int ABSTRACT_INFORMATION_UNIT = 3;

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
	 * The number of structural features of the '<em>Information Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT_FEATURE_COUNT = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 12;

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
	int CATEGORY__ID = ADAPTER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__LABEL = ADAPTER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__CHILDREN = ADAPTER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Information Unit</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__INFORMATION_UNIT = ADAPTER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__DESCRIPTION = ADAPTER_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Category</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_FEATURE_COUNT = ADAPTER_FEATURE_COUNT + 5;

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
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.LinkImpl <em>Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.LinkImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getLink()
	 * @generated
	 */
	int LINK = 7;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__TARGET = 0;

	/**
	 * The feature id for the '<em><b>Linktype</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__LINKTYPE = 1;

	/**
	 * The number of structural features of the '<em>Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.LinkTypeImpl <em>Link Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.LinkTypeImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getLinkType()
	 * @generated
	 */
	int LINK_TYPE = 8;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_TYPE__DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_TYPE__ID = 1;

	/**
	 * The feature id for the '<em><b>Image Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_TYPE__IMAGE_PATH = 2;

	/**
	 * The feature id for the '<em><b>Editable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_TYPE__EDITABLE = 3;

	/**
	 * The number of structural features of the '<em>Link Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_TYPE_FEATURE_COUNT = 4;


	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.LinkTypeCollectionImpl <em>Link Type Collection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.LinkTypeCollectionImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getLinkTypeCollection()
	 * @generated
	 */
	int LINK_TYPE_COLLECTION = 10;

	/**
	 * The feature id for the '<em><b>Available Link Types</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_TYPE_COLLECTION__AVAILABLE_LINK_TYPES = 0;

	/**
	 * The number of structural features of the '<em>Link Type Collection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_TYPE_COLLECTION_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.StringToLinkTypeMapImpl <em>String To Link Type Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.StringToLinkTypeMapImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getStringToLinkTypeMap()
	 * @generated
	 */
	int STRING_TO_LINK_TYPE_MAP = 11;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_LINK_TYPE_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_LINK_TYPE_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>String To Link Type Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_LINK_TYPE_MAP_FEATURE_COUNT = 2;


	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.RecentlyUsedKeywordsImpl <em>Recently Used Keywords</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.RecentlyUsedKeywordsImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRecentlyUsedKeywords()
	 * @generated
	 */
	int RECENTLY_USED_KEYWORDS = 12;

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
	int NEW_ELEMENT_RULES = 13;

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
	int RULE_VALUE = 14;

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
	int AVAILABLE_RULE_DEFINITIONS = 15;

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
	int REMUS_TRANSFER_TYPE = 16;

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
	int RULE_ACTION = 17;

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
	 * The number of structural features of the '<em>Rule Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ACTION_FEATURE_COUNT = 3;


	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.RuleResultImpl <em>Rule Result</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.RuleResultImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRuleResult()
	 * @generated
	 */
	int RULE_RESULT = 18;

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
	 * The number of structural features of the '<em>Rule Result</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_RESULT_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.RemoteRepositoryImpl <em>Remote Repository</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.RemoteRepositoryImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRemoteRepository()
	 * @generated
	 */
	int REMOTE_REPOSITORY = 19;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_REPOSITORY__ID = ADAPTER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_REPOSITORY__TYPE_ID = ADAPTER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_REPOSITORY__URL = ADAPTER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_REPOSITORY__NAME = ADAPTER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Children</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_REPOSITORY__CHILDREN = ADAPTER_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Remote Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_REPOSITORY_FEATURE_COUNT = ADAPTER_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.RemoteObjectImpl <em>Remote Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.RemoteObjectImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRemoteObject()
	 * @generated
	 */
	int REMOTE_OBJECT = 20;

	/**
	 * The feature id for the '<em><b>Possible Info Type Id</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_OBJECT__POSSIBLE_INFO_TYPE_ID = ADAPTER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Remote Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_OBJECT_FEATURE_COUNT = ADAPTER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.RemoteContainerImpl <em>Remote Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.RemoteContainerImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRemoteContainer()
	 * @generated
	 */
	int REMOTE_CONTAINER = 21;

	/**
	 * The feature id for the '<em><b>Possible Info Type Id</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_CONTAINER__POSSIBLE_INFO_TYPE_ID = REMOTE_OBJECT__POSSIBLE_INFO_TYPE_ID;

	/**
	 * The number of structural features of the '<em>Remote Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_CONTAINER_FEATURE_COUNT = REMOTE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.RepositoryCollectionImpl <em>Repository Collection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.RepositoryCollectionImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRepositoryCollection()
	 * @generated
	 */
	int REPOSITORY_COLLECTION = 22;

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
	 * The meta object id for the '<em>Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.Object
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getObject()
	 * @generated
	 */
	int OBJECT = 23;


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
	 * Returns the meta object for the reference '{@link org.remus.infomngmnt.Link#getLinktype <em>Linktype</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Linktype</em>'.
	 * @see org.remus.infomngmnt.Link#getLinktype()
	 * @see #getLink()
	 * @generated
	 */
	EReference getLink_Linktype();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.LinkType <em>Link Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Link Type</em>'.
	 * @see org.remus.infomngmnt.LinkType
	 * @generated
	 */
	EClass getLinkType();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.LinkType#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.remus.infomngmnt.LinkType#getDescription()
	 * @see #getLinkType()
	 * @generated
	 */
	EAttribute getLinkType_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.LinkType#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.remus.infomngmnt.LinkType#getId()
	 * @see #getLinkType()
	 * @generated
	 */
	EAttribute getLinkType_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.LinkType#getImagePath <em>Image Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Image Path</em>'.
	 * @see org.remus.infomngmnt.LinkType#getImagePath()
	 * @see #getLinkType()
	 * @generated
	 */
	EAttribute getLinkType_ImagePath();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.LinkType#isEditable <em>Editable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Editable</em>'.
	 * @see org.remus.infomngmnt.LinkType#isEditable()
	 * @see #getLinkType()
	 * @generated
	 */
	EAttribute getLinkType_Editable();

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
	 * Returns the meta object for class '{@link org.remus.infomngmnt.LinkTypeCollection <em>Link Type Collection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Link Type Collection</em>'.
	 * @see org.remus.infomngmnt.LinkTypeCollection
	 * @generated
	 */
	EClass getLinkTypeCollection();

	/**
	 * Returns the meta object for the map '{@link org.remus.infomngmnt.LinkTypeCollection#getAvailableLinkTypes <em>Available Link Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Available Link Types</em>'.
	 * @see org.remus.infomngmnt.LinkTypeCollection#getAvailableLinkTypes()
	 * @see #getLinkTypeCollection()
	 * @generated
	 */
	EReference getLinkTypeCollection_AvailableLinkTypes();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>String To Link Type Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String To Link Type Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueType="org.remus.infomngmnt.LinkType" valueContainment="true"
	 * @generated
	 */
	EClass getStringToLinkTypeMap();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToLinkTypeMap()
	 * @generated
	 */
	EAttribute getStringToLinkTypeMap_Key();

	/**
	 * Returns the meta object for the containment reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToLinkTypeMap()
	 * @generated
	 */
	EReference getStringToLinkTypeMap_Value();

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
	 * Returns the meta object for class '{@link org.remus.infomngmnt.RemoteRepository <em>Remote Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Remote Repository</em>'.
	 * @see org.remus.infomngmnt.RemoteRepository
	 * @generated
	 */
	EClass getRemoteRepository();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.RemoteRepository#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.remus.infomngmnt.RemoteRepository#getId()
	 * @see #getRemoteRepository()
	 * @generated
	 */
	EAttribute getRemoteRepository_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.RemoteRepository#getTypeId <em>Type Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Id</em>'.
	 * @see org.remus.infomngmnt.RemoteRepository#getTypeId()
	 * @see #getRemoteRepository()
	 * @generated
	 */
	EAttribute getRemoteRepository_TypeId();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.RemoteRepository#getUrl <em>Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Url</em>'.
	 * @see org.remus.infomngmnt.RemoteRepository#getUrl()
	 * @see #getRemoteRepository()
	 * @generated
	 */
	EAttribute getRemoteRepository_Url();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.RemoteRepository#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.remus.infomngmnt.RemoteRepository#getName()
	 * @see #getRemoteRepository()
	 * @generated
	 */
	EAttribute getRemoteRepository_Name();

	/**
	 * Returns the meta object for the reference list '{@link org.remus.infomngmnt.RemoteRepository#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Children</em>'.
	 * @see org.remus.infomngmnt.RemoteRepository#getChildren()
	 * @see #getRemoteRepository()
	 * @generated
	 */
	EReference getRemoteRepository_Children();

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
	 * Returns the meta object for class '{@link org.remus.infomngmnt.RemoteContainer <em>Remote Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Remote Container</em>'.
	 * @see org.remus.infomngmnt.RemoteContainer
	 * @generated
	 */
	EClass getRemoteContainer();

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
		 * The meta object literal for the '<em><b>Linktype</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK__LINKTYPE = eINSTANCE.getLink_Linktype();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.LinkTypeImpl <em>Link Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.LinkTypeImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getLinkType()
		 * @generated
		 */
		EClass LINK_TYPE = eINSTANCE.getLinkType();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LINK_TYPE__DESCRIPTION = eINSTANCE.getLinkType_Description();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LINK_TYPE__ID = eINSTANCE.getLinkType_Id();

		/**
		 * The meta object literal for the '<em><b>Image Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LINK_TYPE__IMAGE_PATH = eINSTANCE.getLinkType_ImagePath();

		/**
		 * The meta object literal for the '<em><b>Editable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LINK_TYPE__EDITABLE = eINSTANCE.getLinkType_Editable();

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
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.LinkTypeCollectionImpl <em>Link Type Collection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.LinkTypeCollectionImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getLinkTypeCollection()
		 * @generated
		 */
		EClass LINK_TYPE_COLLECTION = eINSTANCE.getLinkTypeCollection();

		/**
		 * The meta object literal for the '<em><b>Available Link Types</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK_TYPE_COLLECTION__AVAILABLE_LINK_TYPES = eINSTANCE.getLinkTypeCollection_AvailableLinkTypes();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.StringToLinkTypeMapImpl <em>String To Link Type Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.StringToLinkTypeMapImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getStringToLinkTypeMap()
		 * @generated
		 */
		EClass STRING_TO_LINK_TYPE_MAP = eINSTANCE.getStringToLinkTypeMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TO_LINK_TYPE_MAP__KEY = eINSTANCE.getStringToLinkTypeMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRING_TO_LINK_TYPE_MAP__VALUE = eINSTANCE.getStringToLinkTypeMap_Value();

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
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.RemoteRepositoryImpl <em>Remote Repository</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.RemoteRepositoryImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRemoteRepository()
		 * @generated
		 */
		EClass REMOTE_REPOSITORY = eINSTANCE.getRemoteRepository();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REMOTE_REPOSITORY__ID = eINSTANCE.getRemoteRepository_Id();

		/**
		 * The meta object literal for the '<em><b>Type Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REMOTE_REPOSITORY__TYPE_ID = eINSTANCE.getRemoteRepository_TypeId();

		/**
		 * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REMOTE_REPOSITORY__URL = eINSTANCE.getRemoteRepository_Url();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REMOTE_REPOSITORY__NAME = eINSTANCE.getRemoteRepository_Name();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REMOTE_REPOSITORY__CHILDREN = eINSTANCE.getRemoteRepository_Children();

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
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.RemoteContainerImpl <em>Remote Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.RemoteContainerImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRemoteContainer()
		 * @generated
		 */
		EClass REMOTE_CONTAINER = eINSTANCE.getRemoteContainer();

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
		 * The meta object literal for the '<em>Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.Object
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getObject()
		 * @generated
		 */
		EDataType OBJECT = eINSTANCE.getObject();

	}

} //InfomngmntPackage
