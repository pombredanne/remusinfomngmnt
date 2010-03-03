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
	 * The meta object id for the '{@link org.remus.infomngmnt.V__________InformationUnit__________V <em>VInformation Unit V</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.V__________InformationUnit__________V
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getV__________InformationUnit__________V()
	 * @generated
	 */
	int VINFORMATION_UNIT_V = 0;

	/**
	 * The number of structural features of the '<em>VInformation Unit V</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VINFORMATION_UNIT_V_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.AdapterImpl <em>Adapter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.AdapterImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getAdapter()
	 * @generated
	 */
	int ADAPTER = 1;

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
	int ABSTRACT_INFORMATION_UNIT = 2;

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
	int INFORMATION_UNIT = 3;

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
	 * The feature id for the '<em><b>Double Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__DOUBLE_VALUE = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Child Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__CHILD_VALUES = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__REFERENCES = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__LINKS = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__CREATION_DATE = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 9;

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
	 * The feature id for the '<em><b>Binary References</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__BINARY_REFERENCES = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT__COMMENTS = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 14;

	/**
	 * The number of structural features of the '<em>Information Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT_FEATURE_COUNT = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 15;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.BinaryReferenceImpl <em>Binary Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.BinaryReferenceImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getBinaryReference()
	 * @generated
	 */
	int BINARY_REFERENCE = 4;

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
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.LinkImpl <em>Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.LinkImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getLink()
	 * @generated
	 */
	int LINK = 5;

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
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.CommentImpl <em>Comment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.CommentImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getComment()
	 * @generated
	 */
	int COMMENT = 6;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__AUTHOR = 0;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__COMMENT = 1;

	/**
	 * The feature id for the '<em><b>Rating</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__RATING = 2;

	/**
	 * The number of structural features of the '<em>Comment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.CalendarEntryImpl <em>Calendar Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.CalendarEntryImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getCalendarEntry()
	 * @generated
	 */
	int CALENDAR_ENTRY = 7;

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
	 * The meta object id for the '{@link org.remus.infomngmnt.V__________NavigationObjects__________V <em>VNavigation Objects V</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.V__________NavigationObjects__________V
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getV__________NavigationObjects__________V()
	 * @generated
	 */
	int VNAVIGATION_OBJECTS_V = 8;

	/**
	 * The number of structural features of the '<em>VNavigation Objects V</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VNAVIGATION_OBJECTS_V_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.ApplicationRootImpl <em>Application Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.ApplicationRootImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getApplicationRoot()
	 * @generated
	 */
	int APPLICATION_ROOT = 9;

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
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.SynchronizableObjectImpl <em>Synchronizable Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.SynchronizableObjectImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getSynchronizableObject()
	 * @generated
	 */
	int SYNCHRONIZABLE_OBJECT = 27;

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
	int CATEGORY = 10;

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
	int INFORMATION_UNIT_LIST_ITEM = 11;

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
	 * The feature id for the '<em><b>Unread</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT_LIST_ITEM__UNREAD = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Information Unit List Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_UNIT_LIST_ITEM_FEATURE_COUNT = ABSTRACT_INFORMATION_UNIT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.V__________Semantics__________V <em>VSemantics V</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.V__________Semantics__________V
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getV__________Semantics__________V()
	 * @generated
	 */
	int VSEMANTICS_V = 12;

	/**
	 * The number of structural features of the '<em>VSemantics V</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSEMANTICS_V_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.RecentlyUsedKeywordsImpl <em>Recently Used Keywords</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.RecentlyUsedKeywordsImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRecentlyUsedKeywords()
	 * @generated
	 */
	int RECENTLY_USED_KEYWORDS = 13;

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
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.AvailableTagsImpl <em>Available Tags</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.AvailableTagsImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getAvailableTags()
	 * @generated
	 */
	int AVAILABLE_TAGS = 14;

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
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.TagImpl <em>Tag</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.TagImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getTag()
	 * @generated
	 */
	int TAG = 15;

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
	 * The meta object id for the '{@link org.remus.infomngmnt.V__________Synchronization__________V <em>VSynchronization V</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.V__________Synchronization__________V
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getV__________Synchronization__________V()
	 * @generated
	 */
	int VSYNCHRONIZATION_V = 16;

	/**
	 * The number of structural features of the '<em>VSynchronization V</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSYNCHRONIZATION_V_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.RepositoryCollectionImpl <em>Repository Collection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.RepositoryCollectionImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRepositoryCollection()
	 * @generated
	 */
	int REPOSITORY_COLLECTION = 17;

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
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.RemoteObjectImpl <em>Remote Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.RemoteObjectImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRemoteObject()
	 * @generated
	 */
	int REMOTE_OBJECT = 19;

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
	int REMOTE_CONTAINER = 20;

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
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.RemoteRepositoryImpl <em>Remote Repository</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.RemoteRepositoryImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRemoteRepository()
	 * @generated
	 */
	int REMOTE_REPOSITORY = 18;

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
	 * The feature id for the '<em><b>Options</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_REPOSITORY__OPTIONS = REMOTE_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Remote Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOTE_REPOSITORY_FEATURE_COUNT = REMOTE_CONTAINER_FEATURE_COUNT + 1;

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
	 * The feature id for the '<em><b>Currently Syncing</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONIZATION_METADATA__CURRENTLY_SYNCING = ADAPTER_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Synchronization Metadata</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONIZATION_METADATA_FEATURE_COUNT = ADAPTER_FEATURE_COUNT + 7;

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
	 * The meta object id for the '{@link org.remus.infomngmnt.V__________InformationStructureDefinition__________V <em>VInformation Structure Definition V</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.V__________InformationStructureDefinition__________V
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getV__________InformationStructureDefinition__________V()
	 * @generated
	 */
	int VINFORMATION_STRUCTURE_DEFINITION_V = 28;

	/**
	 * The number of structural features of the '<em>VInformation Structure Definition V</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VINFORMATION_STRUCTURE_DEFINITION_V_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.InformationStructureImpl <em>Information Structure</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.InformationStructureImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getInformationStructure()
	 * @generated
	 */
	int INFORMATION_STRUCTURE = 31;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_STRUCTURE__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Structure Items</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_STRUCTURE__STRUCTURE_ITEMS = 1;

	/**
	 * The feature id for the '<em><b>Referenced Structure Items</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_STRUCTURE__REFERENCED_STRUCTURE_ITEMS = 2;

	/**
	 * The feature id for the '<em><b>Can Have Binary References</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_STRUCTURE__CAN_HAVE_BINARY_REFERENCES = 3;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_STRUCTURE__LABEL = 4;

	/**
	 * The number of structural features of the '<em>Information Structure</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_STRUCTURE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.InformationStructureDefinitionImpl <em>Information Structure Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.InformationStructureDefinitionImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getInformationStructureDefinition()
	 * @generated
	 */
	int INFORMATION_STRUCTURE_DEFINITION = 29;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_STRUCTURE_DEFINITION__TYPE = INFORMATION_STRUCTURE__TYPE;

	/**
	 * The feature id for the '<em><b>Structure Items</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_STRUCTURE_DEFINITION__STRUCTURE_ITEMS = INFORMATION_STRUCTURE__STRUCTURE_ITEMS;

	/**
	 * The feature id for the '<em><b>Referenced Structure Items</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_STRUCTURE_DEFINITION__REFERENCED_STRUCTURE_ITEMS = INFORMATION_STRUCTURE__REFERENCED_STRUCTURE_ITEMS;

	/**
	 * The feature id for the '<em><b>Can Have Binary References</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_STRUCTURE_DEFINITION__CAN_HAVE_BINARY_REFERENCES = INFORMATION_STRUCTURE__CAN_HAVE_BINARY_REFERENCES;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_STRUCTURE_DEFINITION__LABEL = INFORMATION_STRUCTURE__LABEL;

	/**
	 * The feature id for the '<em><b>Structure Pool</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_STRUCTURE_DEFINITION__STRUCTURE_POOL = INFORMATION_STRUCTURE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Information Structure Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_STRUCTURE_DEFINITION_FEATURE_COUNT = INFORMATION_STRUCTURE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.InformationStructureItemImpl <em>Information Structure Item</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.InformationStructureItemImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getInformationStructureItem()
	 * @generated
	 */
	int INFORMATION_STRUCTURE_ITEM = 30;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_STRUCTURE_ITEM__TYPE = INFORMATION_STRUCTURE__TYPE;

	/**
	 * The feature id for the '<em><b>Structure Items</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_STRUCTURE_ITEM__STRUCTURE_ITEMS = INFORMATION_STRUCTURE__STRUCTURE_ITEMS;

	/**
	 * The feature id for the '<em><b>Referenced Structure Items</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_STRUCTURE_ITEM__REFERENCED_STRUCTURE_ITEMS = INFORMATION_STRUCTURE__REFERENCED_STRUCTURE_ITEMS;

	/**
	 * The feature id for the '<em><b>Can Have Binary References</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_STRUCTURE_ITEM__CAN_HAVE_BINARY_REFERENCES = INFORMATION_STRUCTURE__CAN_HAVE_BINARY_REFERENCES;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_STRUCTURE_ITEM__LABEL = INFORMATION_STRUCTURE__LABEL;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_STRUCTURE_ITEM__ID = INFORMATION_STRUCTURE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Create Always</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_STRUCTURE_ITEM__CREATE_ALWAYS = INFORMATION_STRUCTURE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Information Structure Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFORMATION_STRUCTURE_ITEM_FEATURE_COUNT = INFORMATION_STRUCTURE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.DynamicStructureImpl <em>Dynamic Structure</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.DynamicStructureImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getDynamicStructure()
	 * @generated
	 */
	int DYNAMIC_STRUCTURE = 32;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_STRUCTURE__TYPE = INFORMATION_STRUCTURE_ITEM__TYPE;

	/**
	 * The feature id for the '<em><b>Structure Items</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_STRUCTURE__STRUCTURE_ITEMS = INFORMATION_STRUCTURE_ITEM__STRUCTURE_ITEMS;

	/**
	 * The feature id for the '<em><b>Referenced Structure Items</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_STRUCTURE__REFERENCED_STRUCTURE_ITEMS = INFORMATION_STRUCTURE_ITEM__REFERENCED_STRUCTURE_ITEMS;

	/**
	 * The feature id for the '<em><b>Can Have Binary References</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_STRUCTURE__CAN_HAVE_BINARY_REFERENCES = INFORMATION_STRUCTURE_ITEM__CAN_HAVE_BINARY_REFERENCES;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_STRUCTURE__LABEL = INFORMATION_STRUCTURE_ITEM__LABEL;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_STRUCTURE__ID = INFORMATION_STRUCTURE_ITEM__ID;

	/**
	 * The feature id for the '<em><b>Create Always</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_STRUCTURE__CREATE_ALWAYS = INFORMATION_STRUCTURE_ITEM__CREATE_ALWAYS;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_STRUCTURE__LOWER_BOUND = INFORMATION_STRUCTURE_ITEM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_STRUCTURE__UPPER_BOUND = INFORMATION_STRUCTURE_ITEM_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Dynamic Structure</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_STRUCTURE_FEATURE_COUNT = INFORMATION_STRUCTURE_ITEM_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.V__________DesktopNotifications_________V <em>VDesktop Notifications V</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.V__________DesktopNotifications_________V
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getV__________DesktopNotifications_________V()
	 * @generated
	 */
	int VDESKTOP_NOTIFICATIONS_V = 33;

	/**
	 * The number of structural features of the '<em>VDesktop Notifications V</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VDESKTOP_NOTIFICATIONS_V_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.NotificationImpl <em>Notification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.NotificationImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getNotification()
	 * @generated
	 */
	int NOTIFICATION = 34;

	/**
	 * The feature id for the '<em><b>Time Stamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION__TIME_STAMP = 0;

	/**
	 * The feature id for the '<em><b>Importance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION__IMPORTANCE = 1;

	/**
	 * The feature id for the '<em><b>Severity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION__SEVERITY = 2;

	/**
	 * The feature id for the '<em><b>Noticed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION__NOTICED = 3;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION__MESSAGE = 4;

	/**
	 * The feature id for the '<em><b>Details</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION__DETAILS = 5;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION__CHILDREN = 6;

	/**
	 * The feature id for the '<em><b>Affected Info Unit Ids</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION__AFFECTED_INFO_UNIT_IDS = 7;

	/**
	 * The feature id for the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION__SOURCE = 8;

	/**
	 * The feature id for the '<em><b>Image</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION__IMAGE = 9;

	/**
	 * The number of structural features of the '<em>Notification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTIFICATION_FEATURE_COUNT = 10;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.NotificationCollectionImpl <em>Notification Collection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.NotificationCollectionImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getNotificationCollection()
	 * @generated
	 */
	int NOTIFICATION_COLLECTION = 35;

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
	 * The meta object id for the '{@link org.remus.infomngmnt.V__________Other__________V <em>VOther V</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.V__________Other__________V
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getV__________Other__________V()
	 * @generated
	 */
	int VOTHER_V = 36;

	/**
	 * The number of structural features of the '<em>VOther V</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VOTHER_V_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.impl.StringToStringMapImpl <em>String To String Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.impl.StringToStringMapImpl
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getStringToStringMap()
	 * @generated
	 */
	int STRING_TO_STRING_MAP = 37;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_STRING_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_STRING_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>String To String Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_STRING_MAP_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.Rating <em>Rating</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.Rating
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRating()
	 * @generated
	 */
	int RATING = 38;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.CalendarEntryType <em>Calendar Entry Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.CalendarEntryType
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getCalendarEntryType()
	 * @generated
	 */
	int CALENDAR_ENTRY_TYPE = 39;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.SynchronizationState <em>Synchronization State</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.SynchronizationState
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getSynchronizationState()
	 * @generated
	 */
	int SYNCHRONIZATION_STATE = 40;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.SynchronizationAction <em>Synchronization Action</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.SynchronizationAction
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getSynchronizationAction()
	 * @generated
	 */
	int SYNCHRONIZATION_ACTION = 41;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.InformationStructureType <em>Information Structure Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.InformationStructureType
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getInformationStructureType()
	 * @generated
	 */
	int INFORMATION_STRUCTURE_TYPE = 42;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.NotificationImportance <em>Notification Importance</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.NotificationImportance
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getNotificationImportance()
	 * @generated
	 */
	int NOTIFICATION_IMPORTANCE = 43;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.Severity <em>Severity</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.Severity
	 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getSeverity()
	 * @generated
	 */
	int SEVERITY = 44;

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.V__________InformationUnit__________V <em>VInformation Unit V</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>VInformation Unit V</em>'.
	 * @see org.remus.infomngmnt.V__________InformationUnit__________V
	 * @generated
	 */
	EClass getV__________InformationUnit__________V();

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
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.InformationUnit#getDoubleValue <em>Double Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Double Value</em>'.
	 * @see org.remus.infomngmnt.InformationUnit#getDoubleValue()
	 * @see #getInformationUnit()
	 * @generated
	 */
	EAttribute getInformationUnit_DoubleValue();

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
	 * Returns the meta object for the containment reference '{@link org.remus.infomngmnt.InformationUnit#getBinaryReferences <em>Binary References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Binary References</em>'.
	 * @see org.remus.infomngmnt.InformationUnit#getBinaryReferences()
	 * @see #getInformationUnit()
	 * @generated
	 */
	EReference getInformationUnit_BinaryReferences();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.infomngmnt.InformationUnit#getComments <em>Comments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Comments</em>'.
	 * @see org.remus.infomngmnt.InformationUnit#getComments()
	 * @see #getInformationUnit()
	 * @generated
	 */
	EReference getInformationUnit_Comments();

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
	 * Returns the meta object for class '{@link org.remus.infomngmnt.Comment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Comment</em>'.
	 * @see org.remus.infomngmnt.Comment
	 * @generated
	 */
	EClass getComment();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.Comment#getAuthor <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Author</em>'.
	 * @see org.remus.infomngmnt.Comment#getAuthor()
	 * @see #getComment()
	 * @generated
	 */
	EAttribute getComment_Author();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.Comment#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see org.remus.infomngmnt.Comment#getComment()
	 * @see #getComment()
	 * @generated
	 */
	EAttribute getComment_Comment();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.Comment#getRating <em>Rating</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rating</em>'.
	 * @see org.remus.infomngmnt.Comment#getRating()
	 * @see #getComment()
	 * @generated
	 */
	EAttribute getComment_Rating();

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
	 * Returns the meta object for class '{@link org.remus.infomngmnt.V__________NavigationObjects__________V <em>VNavigation Objects V</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>VNavigation Objects V</em>'.
	 * @see org.remus.infomngmnt.V__________NavigationObjects__________V
	 * @generated
	 */
	EClass getV__________NavigationObjects__________V();

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
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.InformationUnitListItem#isUnread <em>Unread</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unread</em>'.
	 * @see org.remus.infomngmnt.InformationUnitListItem#isUnread()
	 * @see #getInformationUnitListItem()
	 * @generated
	 */
	EAttribute getInformationUnitListItem_Unread();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.V__________Semantics__________V <em>VSemantics V</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>VSemantics V</em>'.
	 * @see org.remus.infomngmnt.V__________Semantics__________V
	 * @generated
	 */
	EClass getV__________Semantics__________V();

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
	 * Returns the meta object for class '{@link org.remus.infomngmnt.Tag <em>Tag</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tag</em>'.
	 * @see org.remus.infomngmnt.Tag
	 * @generated
	 */
	EClass getTag();

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
	 * Returns the meta object for class '{@link org.remus.infomngmnt.V__________Synchronization__________V <em>VSynchronization V</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>VSynchronization V</em>'.
	 * @see org.remus.infomngmnt.V__________Synchronization__________V
	 * @generated
	 */
	EClass getV__________Synchronization__________V();

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
	 * Returns the meta object for class '{@link org.remus.infomngmnt.RemoteRepository <em>Remote Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Remote Repository</em>'.
	 * @see org.remus.infomngmnt.RemoteRepository
	 * @generated
	 */
	EClass getRemoteRepository();

	/**
	 * Returns the meta object for the map '{@link org.remus.infomngmnt.RemoteRepository#getOptions <em>Options</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Options</em>'.
	 * @see org.remus.infomngmnt.RemoteRepository#getOptions()
	 * @see #getRemoteRepository()
	 * @generated
	 */
	EReference getRemoteRepository_Options();

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
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.SynchronizationMetadata#isCurrentlySyncing <em>Currently Syncing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Currently Syncing</em>'.
	 * @see org.remus.infomngmnt.SynchronizationMetadata#isCurrentlySyncing()
	 * @see #getSynchronizationMetadata()
	 * @generated
	 */
	EAttribute getSynchronizationMetadata_CurrentlySyncing();

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
	 * Returns the meta object for class '{@link org.remus.infomngmnt.V__________InformationStructureDefinition__________V <em>VInformation Structure Definition V</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>VInformation Structure Definition V</em>'.
	 * @see org.remus.infomngmnt.V__________InformationStructureDefinition__________V
	 * @generated
	 */
	EClass getV__________InformationStructureDefinition__________V();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.InformationStructureDefinition <em>Information Structure Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Information Structure Definition</em>'.
	 * @see org.remus.infomngmnt.InformationStructureDefinition
	 * @generated
	 */
	EClass getInformationStructureDefinition();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.infomngmnt.InformationStructureDefinition#getStructurePool <em>Structure Pool</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Structure Pool</em>'.
	 * @see org.remus.infomngmnt.InformationStructureDefinition#getStructurePool()
	 * @see #getInformationStructureDefinition()
	 * @generated
	 */
	EReference getInformationStructureDefinition_StructurePool();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.InformationStructureItem <em>Information Structure Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Information Structure Item</em>'.
	 * @see org.remus.infomngmnt.InformationStructureItem
	 * @generated
	 */
	EClass getInformationStructureItem();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.InformationStructureItem#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.remus.infomngmnt.InformationStructureItem#getId()
	 * @see #getInformationStructureItem()
	 * @generated
	 */
	EAttribute getInformationStructureItem_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.InformationStructureItem#isCreateAlways <em>Create Always</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Create Always</em>'.
	 * @see org.remus.infomngmnt.InformationStructureItem#isCreateAlways()
	 * @see #getInformationStructureItem()
	 * @generated
	 */
	EAttribute getInformationStructureItem_CreateAlways();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.InformationStructure <em>Information Structure</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Information Structure</em>'.
	 * @see org.remus.infomngmnt.InformationStructure
	 * @generated
	 */
	EClass getInformationStructure();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.InformationStructure#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.remus.infomngmnt.InformationStructure#getType()
	 * @see #getInformationStructure()
	 * @generated
	 */
	EAttribute getInformationStructure_Type();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.infomngmnt.InformationStructure#getStructureItems <em>Structure Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Structure Items</em>'.
	 * @see org.remus.infomngmnt.InformationStructure#getStructureItems()
	 * @see #getInformationStructure()
	 * @generated
	 */
	EReference getInformationStructure_StructureItems();

	/**
	 * Returns the meta object for the reference list '{@link org.remus.infomngmnt.InformationStructure#getReferencedStructureItems <em>Referenced Structure Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Referenced Structure Items</em>'.
	 * @see org.remus.infomngmnt.InformationStructure#getReferencedStructureItems()
	 * @see #getInformationStructure()
	 * @generated
	 */
	EReference getInformationStructure_ReferencedStructureItems();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.InformationStructure#isCanHaveBinaryReferences <em>Can Have Binary References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Can Have Binary References</em>'.
	 * @see org.remus.infomngmnt.InformationStructure#isCanHaveBinaryReferences()
	 * @see #getInformationStructure()
	 * @generated
	 */
	EAttribute getInformationStructure_CanHaveBinaryReferences();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.InformationStructure#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.remus.infomngmnt.InformationStructure#getLabel()
	 * @see #getInformationStructure()
	 * @generated
	 */
	EAttribute getInformationStructure_Label();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.DynamicStructure <em>Dynamic Structure</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dynamic Structure</em>'.
	 * @see org.remus.infomngmnt.DynamicStructure
	 * @generated
	 */
	EClass getDynamicStructure();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.DynamicStructure#getLowerBound <em>Lower Bound</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lower Bound</em>'.
	 * @see org.remus.infomngmnt.DynamicStructure#getLowerBound()
	 * @see #getDynamicStructure()
	 * @generated
	 */
	EAttribute getDynamicStructure_LowerBound();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.DynamicStructure#getUpperBound <em>Upper Bound</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Upper Bound</em>'.
	 * @see org.remus.infomngmnt.DynamicStructure#getUpperBound()
	 * @see #getDynamicStructure()
	 * @generated
	 */
	EAttribute getDynamicStructure_UpperBound();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.V__________DesktopNotifications_________V <em>VDesktop Notifications V</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>VDesktop Notifications V</em>'.
	 * @see org.remus.infomngmnt.V__________DesktopNotifications_________V
	 * @generated
	 */
	EClass getV__________DesktopNotifications_________V();

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
	 * Returns the meta object for class '{@link org.remus.infomngmnt.V__________Other__________V <em>VOther V</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>VOther V</em>'.
	 * @see org.remus.infomngmnt.V__________Other__________V
	 * @generated
	 */
	EClass getV__________Other__________V();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>String To String Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String To String Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueDataType="org.eclipse.emf.ecore.EString"
	 * @generated
	 */
	EClass getStringToStringMap();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToStringMap()
	 * @generated
	 */
	EAttribute getStringToStringMap_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToStringMap()
	 * @generated
	 */
	EAttribute getStringToStringMap_Value();

	/**
	 * Returns the meta object for enum '{@link org.remus.infomngmnt.Rating <em>Rating</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Rating</em>'.
	 * @see org.remus.infomngmnt.Rating
	 * @generated
	 */
	EEnum getRating();

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
	 * Returns the meta object for enum '{@link org.remus.infomngmnt.InformationStructureType <em>Information Structure Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Information Structure Type</em>'.
	 * @see org.remus.infomngmnt.InformationStructureType
	 * @generated
	 */
	EEnum getInformationStructureType();

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
		 * The meta object literal for the '{@link org.remus.infomngmnt.V__________InformationUnit__________V <em>VInformation Unit V</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.V__________InformationUnit__________V
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getV__________InformationUnit__________V()
		 * @generated
		 */
		EClass VINFORMATION_UNIT_V = eINSTANCE.getV__________InformationUnit__________V();

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
		 * The meta object literal for the '<em><b>Double Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INFORMATION_UNIT__DOUBLE_VALUE = eINSTANCE.getInformationUnit_DoubleValue();

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
		 * The meta object literal for the '<em><b>Binary References</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INFORMATION_UNIT__BINARY_REFERENCES = eINSTANCE.getInformationUnit_BinaryReferences();

		/**
		 * The meta object literal for the '<em><b>Comments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INFORMATION_UNIT__COMMENTS = eINSTANCE.getInformationUnit_Comments();

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
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.CommentImpl <em>Comment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.CommentImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getComment()
		 * @generated
		 */
		EClass COMMENT = eINSTANCE.getComment();

		/**
		 * The meta object literal for the '<em><b>Author</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT__AUTHOR = eINSTANCE.getComment_Author();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT__COMMENT = eINSTANCE.getComment_Comment();

		/**
		 * The meta object literal for the '<em><b>Rating</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT__RATING = eINSTANCE.getComment_Rating();

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
		 * The meta object literal for the '{@link org.remus.infomngmnt.V__________NavigationObjects__________V <em>VNavigation Objects V</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.V__________NavigationObjects__________V
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getV__________NavigationObjects__________V()
		 * @generated
		 */
		EClass VNAVIGATION_OBJECTS_V = eINSTANCE.getV__________NavigationObjects__________V();

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
		 * The meta object literal for the '<em><b>Unread</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INFORMATION_UNIT_LIST_ITEM__UNREAD = eINSTANCE.getInformationUnitListItem_Unread();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.V__________Semantics__________V <em>VSemantics V</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.V__________Semantics__________V
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getV__________Semantics__________V()
		 * @generated
		 */
		EClass VSEMANTICS_V = eINSTANCE.getV__________Semantics__________V();

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
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.TagImpl <em>Tag</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.TagImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getTag()
		 * @generated
		 */
		EClass TAG = eINSTANCE.getTag();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TAG__NAME = eINSTANCE.getTag_Name();

		/**
		 * The meta object literal for the '<em><b>Info Units</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TAG__INFO_UNITS = eINSTANCE.getTag_InfoUnits();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.V__________Synchronization__________V <em>VSynchronization V</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.V__________Synchronization__________V
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getV__________Synchronization__________V()
		 * @generated
		 */
		EClass VSYNCHRONIZATION_V = eINSTANCE.getV__________Synchronization__________V();

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
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.RemoteRepositoryImpl <em>Remote Repository</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.RemoteRepositoryImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRemoteRepository()
		 * @generated
		 */
		EClass REMOTE_REPOSITORY = eINSTANCE.getRemoteRepository();

		/**
		 * The meta object literal for the '<em><b>Options</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REMOTE_REPOSITORY__OPTIONS = eINSTANCE.getRemoteRepository_Options();

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
		 * The meta object literal for the '<em><b>Currently Syncing</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYNCHRONIZATION_METADATA__CURRENTLY_SYNCING = eINSTANCE.getSynchronizationMetadata_CurrentlySyncing();

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
		 * The meta object literal for the '{@link org.remus.infomngmnt.V__________InformationStructureDefinition__________V <em>VInformation Structure Definition V</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.V__________InformationStructureDefinition__________V
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getV__________InformationStructureDefinition__________V()
		 * @generated
		 */
		EClass VINFORMATION_STRUCTURE_DEFINITION_V = eINSTANCE.getV__________InformationStructureDefinition__________V();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.InformationStructureDefinitionImpl <em>Information Structure Definition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.InformationStructureDefinitionImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getInformationStructureDefinition()
		 * @generated
		 */
		EClass INFORMATION_STRUCTURE_DEFINITION = eINSTANCE.getInformationStructureDefinition();

		/**
		 * The meta object literal for the '<em><b>Structure Pool</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INFORMATION_STRUCTURE_DEFINITION__STRUCTURE_POOL = eINSTANCE.getInformationStructureDefinition_StructurePool();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.InformationStructureItemImpl <em>Information Structure Item</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.InformationStructureItemImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getInformationStructureItem()
		 * @generated
		 */
		EClass INFORMATION_STRUCTURE_ITEM = eINSTANCE.getInformationStructureItem();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INFORMATION_STRUCTURE_ITEM__ID = eINSTANCE.getInformationStructureItem_Id();

		/**
		 * The meta object literal for the '<em><b>Create Always</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INFORMATION_STRUCTURE_ITEM__CREATE_ALWAYS = eINSTANCE.getInformationStructureItem_CreateAlways();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.InformationStructureImpl <em>Information Structure</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.InformationStructureImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getInformationStructure()
		 * @generated
		 */
		EClass INFORMATION_STRUCTURE = eINSTANCE.getInformationStructure();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INFORMATION_STRUCTURE__TYPE = eINSTANCE.getInformationStructure_Type();

		/**
		 * The meta object literal for the '<em><b>Structure Items</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INFORMATION_STRUCTURE__STRUCTURE_ITEMS = eINSTANCE.getInformationStructure_StructureItems();

		/**
		 * The meta object literal for the '<em><b>Referenced Structure Items</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INFORMATION_STRUCTURE__REFERENCED_STRUCTURE_ITEMS = eINSTANCE.getInformationStructure_ReferencedStructureItems();

		/**
		 * The meta object literal for the '<em><b>Can Have Binary References</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INFORMATION_STRUCTURE__CAN_HAVE_BINARY_REFERENCES = eINSTANCE.getInformationStructure_CanHaveBinaryReferences();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INFORMATION_STRUCTURE__LABEL = eINSTANCE.getInformationStructure_Label();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.DynamicStructureImpl <em>Dynamic Structure</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.DynamicStructureImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getDynamicStructure()
		 * @generated
		 */
		EClass DYNAMIC_STRUCTURE = eINSTANCE.getDynamicStructure();

		/**
		 * The meta object literal for the '<em><b>Lower Bound</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DYNAMIC_STRUCTURE__LOWER_BOUND = eINSTANCE.getDynamicStructure_LowerBound();

		/**
		 * The meta object literal for the '<em><b>Upper Bound</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DYNAMIC_STRUCTURE__UPPER_BOUND = eINSTANCE.getDynamicStructure_UpperBound();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.V__________DesktopNotifications_________V <em>VDesktop Notifications V</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.V__________DesktopNotifications_________V
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getV__________DesktopNotifications_________V()
		 * @generated
		 */
		EClass VDESKTOP_NOTIFICATIONS_V = eINSTANCE.getV__________DesktopNotifications_________V();

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
		 * The meta object literal for the '{@link org.remus.infomngmnt.V__________Other__________V <em>VOther V</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.V__________Other__________V
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getV__________Other__________V()
		 * @generated
		 */
		EClass VOTHER_V = eINSTANCE.getV__________Other__________V();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.impl.StringToStringMapImpl <em>String To String Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.impl.StringToStringMapImpl
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getStringToStringMap()
		 * @generated
		 */
		EClass STRING_TO_STRING_MAP = eINSTANCE.getStringToStringMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TO_STRING_MAP__KEY = eINSTANCE.getStringToStringMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TO_STRING_MAP__VALUE = eINSTANCE.getStringToStringMap_Value();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.Rating <em>Rating</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.Rating
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getRating()
		 * @generated
		 */
		EEnum RATING = eINSTANCE.getRating();

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
		 * The meta object literal for the '{@link org.remus.infomngmnt.InformationStructureType <em>Information Structure Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.InformationStructureType
		 * @see org.remus.infomngmnt.impl.InfomngmntPackageImpl#getInformationStructureType()
		 * @generated
		 */
		EEnum INFORMATION_STRUCTURE_TYPE = eINSTANCE.getInformationStructureType();

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

	}

} //InfomngmntPackage
