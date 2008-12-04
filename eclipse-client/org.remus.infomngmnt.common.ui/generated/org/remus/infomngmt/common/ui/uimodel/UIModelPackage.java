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
package org.remus.infomngmt.common.ui.uimodel;

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
 * @see org.remus.infomngmt.common.ui.uimodel.UIModelFactory
 * @model kind="package"
 * @generated
 */
public interface UIModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "uimodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://remus-software.org/uimodel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "uimodel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	UIModelPackage eINSTANCE = org.remus.infomngmt.common.ui.uimodel.impl.UIModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.remus.infomngmt.common.ui.uimodel.impl.TraySectionImpl <em>Tray Section</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmt.common.ui.uimodel.impl.TraySectionImpl
	 * @see org.remus.infomngmt.common.ui.uimodel.impl.UIModelPackageImpl#getTraySection()
	 * @generated
	 */
	int TRAY_SECTION = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRAY_SECTION__NAME = 0;

	/**
	 * The feature id for the '<em><b>Image</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRAY_SECTION__IMAGE = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRAY_SECTION__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Implementation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRAY_SECTION__IMPLEMENTATION = 3;

	/**
	 * The feature id for the '<em><b>Template Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRAY_SECTION__TEMPLATE_ID = 4;

	/**
	 * The feature id for the '<em><b>Preference Options</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRAY_SECTION__PREFERENCE_OPTIONS = 5;

	/**
	 * The number of structural features of the '<em>Tray Section</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRAY_SECTION_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link org.remus.infomngmt.common.ui.uimodel.impl.TraySectionCollectionImpl <em>Tray Section Collection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmt.common.ui.uimodel.impl.TraySectionCollectionImpl
	 * @see org.remus.infomngmt.common.ui.uimodel.impl.UIModelPackageImpl#getTraySectionCollection()
	 * @generated
	 */
	int TRAY_SECTION_COLLECTION = 1;

	/**
	 * The feature id for the '<em><b>Sections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRAY_SECTION_COLLECTION__SECTIONS = 0;

	/**
	 * The number of structural features of the '<em>Tray Section Collection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRAY_SECTION_COLLECTION_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.remus.infomngmt.common.ui.uimodel.impl.StringToStringMapImpl <em>String To String Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmt.common.ui.uimodel.impl.StringToStringMapImpl
	 * @see org.remus.infomngmt.common.ui.uimodel.impl.UIModelPackageImpl#getStringToStringMap()
	 * @generated
	 */
	int STRING_TO_STRING_MAP = 2;

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
	 * The meta object id for the '<em>Image</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.swt.graphics.Image
	 * @see org.remus.infomngmt.common.ui.uimodel.impl.UIModelPackageImpl#getImage()
	 * @generated
	 */
	int IMAGE = 3;

	/**
	 * The meta object id for the '<em>Abstract Tray Section</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.common.ui.extension.AbstractTraySection
	 * @see org.remus.infomngmt.common.ui.uimodel.impl.UIModelPackageImpl#getAbstractTraySection()
	 * @generated
	 */
	int ABSTRACT_TRAY_SECTION = 4;


	/**
	 * Returns the meta object for class '{@link org.remus.infomngmt.common.ui.uimodel.TraySection <em>Tray Section</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tray Section</em>'.
	 * @see org.remus.infomngmt.common.ui.uimodel.TraySection
	 * @generated
	 */
	EClass getTraySection();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmt.common.ui.uimodel.TraySection#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.remus.infomngmt.common.ui.uimodel.TraySection#getName()
	 * @see #getTraySection()
	 * @generated
	 */
	EAttribute getTraySection_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmt.common.ui.uimodel.TraySection#getImage <em>Image</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Image</em>'.
	 * @see org.remus.infomngmt.common.ui.uimodel.TraySection#getImage()
	 * @see #getTraySection()
	 * @generated
	 */
	EAttribute getTraySection_Image();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmt.common.ui.uimodel.TraySection#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.remus.infomngmt.common.ui.uimodel.TraySection#getDescription()
	 * @see #getTraySection()
	 * @generated
	 */
	EAttribute getTraySection_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmt.common.ui.uimodel.TraySection#getImplementation <em>Implementation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Implementation</em>'.
	 * @see org.remus.infomngmt.common.ui.uimodel.TraySection#getImplementation()
	 * @see #getTraySection()
	 * @generated
	 */
	EAttribute getTraySection_Implementation();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmt.common.ui.uimodel.TraySection#getTemplateId <em>Template Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Template Id</em>'.
	 * @see org.remus.infomngmt.common.ui.uimodel.TraySection#getTemplateId()
	 * @see #getTraySection()
	 * @generated
	 */
	EAttribute getTraySection_TemplateId();

	/**
	 * Returns the meta object for the map '{@link org.remus.infomngmt.common.ui.uimodel.TraySection#getPreferenceOptions <em>Preference Options</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Preference Options</em>'.
	 * @see org.remus.infomngmt.common.ui.uimodel.TraySection#getPreferenceOptions()
	 * @see #getTraySection()
	 * @generated
	 */
	EReference getTraySection_PreferenceOptions();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmt.common.ui.uimodel.TraySectionCollection <em>Tray Section Collection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tray Section Collection</em>'.
	 * @see org.remus.infomngmt.common.ui.uimodel.TraySectionCollection
	 * @generated
	 */
	EClass getTraySectionCollection();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.infomngmt.common.ui.uimodel.TraySectionCollection#getSections <em>Sections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sections</em>'.
	 * @see org.remus.infomngmt.common.ui.uimodel.TraySectionCollection#getSections()
	 * @see #getTraySectionCollection()
	 * @generated
	 */
	EReference getTraySectionCollection_Sections();

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
	 * Returns the meta object for data type '{@link org.eclipse.swt.graphics.Image <em>Image</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Image</em>'.
	 * @see org.eclipse.swt.graphics.Image
	 * @model instanceClass="org.eclipse.swt.graphics.Image"
	 * @generated
	 */
	EDataType getImage();

	/**
	 * Returns the meta object for data type '{@link org.remus.infomngmnt.common.ui.extension.AbstractTraySection <em>Abstract Tray Section</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Abstract Tray Section</em>'.
	 * @see org.remus.infomngmnt.common.ui.extension.AbstractTraySection
	 * @model instanceClass="org.remus.infomngmnt.common.ui.extension.AbstractTraySection" serializeable="false"
	 * @generated
	 */
	EDataType getAbstractTraySection();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	UIModelFactory getUIModelFactory();

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
		 * The meta object literal for the '{@link org.remus.infomngmt.common.ui.uimodel.impl.TraySectionImpl <em>Tray Section</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmt.common.ui.uimodel.impl.TraySectionImpl
		 * @see org.remus.infomngmt.common.ui.uimodel.impl.UIModelPackageImpl#getTraySection()
		 * @generated
		 */
		EClass TRAY_SECTION = eINSTANCE.getTraySection();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRAY_SECTION__NAME = eINSTANCE.getTraySection_Name();

		/**
		 * The meta object literal for the '<em><b>Image</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRAY_SECTION__IMAGE = eINSTANCE.getTraySection_Image();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRAY_SECTION__DESCRIPTION = eINSTANCE.getTraySection_Description();

		/**
		 * The meta object literal for the '<em><b>Implementation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRAY_SECTION__IMPLEMENTATION = eINSTANCE.getTraySection_Implementation();

		/**
		 * The meta object literal for the '<em><b>Template Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRAY_SECTION__TEMPLATE_ID = eINSTANCE.getTraySection_TemplateId();

		/**
		 * The meta object literal for the '<em><b>Preference Options</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRAY_SECTION__PREFERENCE_OPTIONS = eINSTANCE.getTraySection_PreferenceOptions();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmt.common.ui.uimodel.impl.TraySectionCollectionImpl <em>Tray Section Collection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmt.common.ui.uimodel.impl.TraySectionCollectionImpl
		 * @see org.remus.infomngmt.common.ui.uimodel.impl.UIModelPackageImpl#getTraySectionCollection()
		 * @generated
		 */
		EClass TRAY_SECTION_COLLECTION = eINSTANCE.getTraySectionCollection();

		/**
		 * The meta object literal for the '<em><b>Sections</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRAY_SECTION_COLLECTION__SECTIONS = eINSTANCE.getTraySectionCollection_Sections();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmt.common.ui.uimodel.impl.StringToStringMapImpl <em>String To String Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmt.common.ui.uimodel.impl.StringToStringMapImpl
		 * @see org.remus.infomngmt.common.ui.uimodel.impl.UIModelPackageImpl#getStringToStringMap()
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
		 * The meta object literal for the '<em>Image</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.swt.graphics.Image
		 * @see org.remus.infomngmt.common.ui.uimodel.impl.UIModelPackageImpl#getImage()
		 * @generated
		 */
		EDataType IMAGE = eINSTANCE.getImage();

		/**
		 * The meta object literal for the '<em>Abstract Tray Section</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.common.ui.extension.AbstractTraySection
		 * @see org.remus.infomngmt.common.ui.uimodel.impl.UIModelPackageImpl#getAbstractTraySection()
		 * @generated
		 */
		EDataType ABSTRACT_TRAY_SECTION = eINSTANCE.getAbstractTraySection();

	}

} //UIModelPackage
