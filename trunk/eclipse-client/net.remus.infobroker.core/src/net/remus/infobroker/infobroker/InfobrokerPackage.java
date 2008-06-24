/**
 * Copyright Tom Seidel 2008
 * All rights reserved
 *
 * $Id$
 */
package net.remus.infobroker.infobroker;

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
 * @see net.remus.infobroker.infobroker.InfobrokerFactory
 * @model kind="package"
 * @generated
 */
public interface InfobrokerPackage extends EPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright Tom Seidel 2008\r\nAll rights reserved";

    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "infobroker";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://remus-software.net/infobroker";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "infobroker";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    InfobrokerPackage eINSTANCE = net.remus.infobroker.infobroker.impl.InfobrokerPackageImpl.init();

    /**
     * The meta object id for the '{@link net.remus.infobroker.infobroker.impl.BaseImpl <em>Base</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.remus.infobroker.infobroker.impl.BaseImpl
     * @see net.remus.infobroker.infobroker.impl.InfobrokerPackageImpl#getBase()
     * @generated
     */
    int BASE = 3;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASE__LABEL = 0;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASE__DESCRIPTION = 1;

    /**
     * The feature id for the '<em><b>Creation Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASE__CREATION_TIME = 2;

    /**
     * The feature id for the '<em><b>Tags</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASE__TAGS = 3;

    /**
     * The number of structural features of the '<em>Base</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASE_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link net.remus.infobroker.infobroker.impl.NodeImpl <em>Node</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.remus.infobroker.infobroker.impl.NodeImpl
     * @see net.remus.infobroker.infobroker.impl.InfobrokerPackageImpl#getNode()
     * @generated
     */
    int NODE = 0;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NODE__LABEL = BASE__LABEL;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NODE__DESCRIPTION = BASE__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Creation Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NODE__CREATION_TIME = BASE__CREATION_TIME;

    /**
     * The feature id for the '<em><b>Tags</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NODE__TAGS = BASE__TAGS;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NODE__TYPE = BASE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Node</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NODE_FEATURE_COUNT = BASE_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link net.remus.infobroker.infobroker.impl.ValueImpl <em>Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.remus.infobroker.infobroker.impl.ValueImpl
     * @see net.remus.infobroker.infobroker.impl.InfobrokerPackageImpl#getValue()
     * @generated
     */
    int VALUE = 1;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VALUE__LABEL = NODE__LABEL;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VALUE__DESCRIPTION = NODE__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Creation Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VALUE__CREATION_TIME = NODE__CREATION_TIME;

    /**
     * The feature id for the '<em><b>Tags</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VALUE__TAGS = NODE__TAGS;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VALUE__TYPE = NODE__TYPE;

    /**
     * The feature id for the '<em><b>String</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VALUE__STRING = NODE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Boolean</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VALUE__BOOLEAN = NODE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VALUE__DATE = NODE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Long</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VALUE__LONG = NODE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Int</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VALUE__INT = NODE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Double</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VALUE__DOUBLE = NODE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Referenced Child Nodes</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VALUE__REFERENCED_CHILD_NODES = NODE_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Value</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VALUE_FEATURE_COUNT = NODE_FEATURE_COUNT + 7;

    /**
     * The meta object id for the '{@link net.remus.infobroker.infobroker.impl.CredentialsImpl <em>Credentials</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.remus.infobroker.infobroker.impl.CredentialsImpl
     * @see net.remus.infobroker.infobroker.impl.InfobrokerPackageImpl#getCredentials()
     * @generated
     */
    int CREDENTIALS = 2;

    /**
     * The number of structural features of the '<em>Credentials</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CREDENTIALS_FEATURE_COUNT = 0;

    /**
     * The meta object id for the '{@link net.remus.infobroker.infobroker.impl.PrimaryNodeImpl <em>Primary Node</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see net.remus.infobroker.infobroker.impl.PrimaryNodeImpl
     * @see net.remus.infobroker.infobroker.impl.InfobrokerPackageImpl#getPrimaryNode()
     * @generated
     */
    int PRIMARY_NODE = 4;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIMARY_NODE__LABEL = VALUE__LABEL;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIMARY_NODE__DESCRIPTION = VALUE__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Creation Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIMARY_NODE__CREATION_TIME = VALUE__CREATION_TIME;

    /**
     * The feature id for the '<em><b>Tags</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIMARY_NODE__TAGS = VALUE__TAGS;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIMARY_NODE__TYPE = VALUE__TYPE;

    /**
     * The feature id for the '<em><b>String</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIMARY_NODE__STRING = VALUE__STRING;

    /**
     * The feature id for the '<em><b>Boolean</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIMARY_NODE__BOOLEAN = VALUE__BOOLEAN;

    /**
     * The feature id for the '<em><b>Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIMARY_NODE__DATE = VALUE__DATE;

    /**
     * The feature id for the '<em><b>Long</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIMARY_NODE__LONG = VALUE__LONG;

    /**
     * The feature id for the '<em><b>Int</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIMARY_NODE__INT = VALUE__INT;

    /**
     * The feature id for the '<em><b>Double</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIMARY_NODE__DOUBLE = VALUE__DOUBLE;

    /**
     * The feature id for the '<em><b>Referenced Child Nodes</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIMARY_NODE__REFERENCED_CHILD_NODES = VALUE__REFERENCED_CHILD_NODES;

    /**
     * The feature id for the '<em><b>Contained Child Nodes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIMARY_NODE__CONTAINED_CHILD_NODES = VALUE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Primary Node</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIMARY_NODE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

    /**
     * Returns the meta object for class '{@link net.remus.infobroker.infobroker.Node <em>Node</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Node</em>'.
     * @see net.remus.infobroker.infobroker.Node
     * @generated
     */
    EClass getNode();

    /**
     * Returns the meta object for the attribute '{@link net.remus.infobroker.infobroker.Node#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see net.remus.infobroker.infobroker.Node#getType()
     * @see #getNode()
     * @generated
     */
    EAttribute getNode_Type();

    /**
     * Returns the meta object for class '{@link net.remus.infobroker.infobroker.Value <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Value</em>'.
     * @see net.remus.infobroker.infobroker.Value
     * @generated
     */
    EClass getValue();

    /**
     * Returns the meta object for the attribute '{@link net.remus.infobroker.infobroker.Value#getString <em>String</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>String</em>'.
     * @see net.remus.infobroker.infobroker.Value#getString()
     * @see #getValue()
     * @generated
     */
    EAttribute getValue_String();

    /**
     * Returns the meta object for the attribute '{@link net.remus.infobroker.infobroker.Value#getBoolean <em>Boolean</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Boolean</em>'.
     * @see net.remus.infobroker.infobroker.Value#getBoolean()
     * @see #getValue()
     * @generated
     */
    EAttribute getValue_Boolean();

    /**
     * Returns the meta object for the attribute '{@link net.remus.infobroker.infobroker.Value#getDate <em>Date</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Date</em>'.
     * @see net.remus.infobroker.infobroker.Value#getDate()
     * @see #getValue()
     * @generated
     */
    EAttribute getValue_Date();

    /**
     * Returns the meta object for the attribute '{@link net.remus.infobroker.infobroker.Value#getLong <em>Long</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Long</em>'.
     * @see net.remus.infobroker.infobroker.Value#getLong()
     * @see #getValue()
     * @generated
     */
    EAttribute getValue_Long();

    /**
     * Returns the meta object for the attribute '{@link net.remus.infobroker.infobroker.Value#getInt <em>Int</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Int</em>'.
     * @see net.remus.infobroker.infobroker.Value#getInt()
     * @see #getValue()
     * @generated
     */
    EAttribute getValue_Int();

    /**
     * Returns the meta object for the attribute '{@link net.remus.infobroker.infobroker.Value#getDouble <em>Double</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Double</em>'.
     * @see net.remus.infobroker.infobroker.Value#getDouble()
     * @see #getValue()
     * @generated
     */
    EAttribute getValue_Double();

    /**
     * Returns the meta object for the reference list '{@link net.remus.infobroker.infobroker.Value#getReferencedChildNodes <em>Referenced Child Nodes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Referenced Child Nodes</em>'.
     * @see net.remus.infobroker.infobroker.Value#getReferencedChildNodes()
     * @see #getValue()
     * @generated
     */
    EReference getValue_ReferencedChildNodes();

    /**
     * Returns the meta object for class '{@link net.remus.infobroker.infobroker.Credentials <em>Credentials</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Credentials</em>'.
     * @see net.remus.infobroker.infobroker.Credentials
     * @generated
     */
    EClass getCredentials();

    /**
     * Returns the meta object for class '{@link net.remus.infobroker.infobroker.Base <em>Base</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Base</em>'.
     * @see net.remus.infobroker.infobroker.Base
     * @generated
     */
    EClass getBase();

    /**
     * Returns the meta object for the attribute '{@link net.remus.infobroker.infobroker.Base#getLabel <em>Label</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Label</em>'.
     * @see net.remus.infobroker.infobroker.Base#getLabel()
     * @see #getBase()
     * @generated
     */
    EAttribute getBase_Label();

    /**
     * Returns the meta object for the attribute '{@link net.remus.infobroker.infobroker.Base#getDescription <em>Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Description</em>'.
     * @see net.remus.infobroker.infobroker.Base#getDescription()
     * @see #getBase()
     * @generated
     */
    EAttribute getBase_Description();

    /**
     * Returns the meta object for the attribute '{@link net.remus.infobroker.infobroker.Base#getCreationTime <em>Creation Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Creation Time</em>'.
     * @see net.remus.infobroker.infobroker.Base#getCreationTime()
     * @see #getBase()
     * @generated
     */
    EAttribute getBase_CreationTime();

    /**
     * Returns the meta object for the attribute list '{@link net.remus.infobroker.infobroker.Base#getTags <em>Tags</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Tags</em>'.
     * @see net.remus.infobroker.infobroker.Base#getTags()
     * @see #getBase()
     * @generated
     */
    EAttribute getBase_Tags();

    /**
     * Returns the meta object for class '{@link net.remus.infobroker.infobroker.PrimaryNode <em>Primary Node</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Primary Node</em>'.
     * @see net.remus.infobroker.infobroker.PrimaryNode
     * @generated
     */
    EClass getPrimaryNode();

    /**
     * Returns the meta object for the containment reference list '{@link net.remus.infobroker.infobroker.PrimaryNode#getContainedChildNodes <em>Contained Child Nodes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Contained Child Nodes</em>'.
     * @see net.remus.infobroker.infobroker.PrimaryNode#getContainedChildNodes()
     * @see #getPrimaryNode()
     * @generated
     */
    EReference getPrimaryNode_ContainedChildNodes();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    InfobrokerFactory getInfobrokerFactory();

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
         * The meta object literal for the '{@link net.remus.infobroker.infobroker.impl.NodeImpl <em>Node</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see net.remus.infobroker.infobroker.impl.NodeImpl
         * @see net.remus.infobroker.infobroker.impl.InfobrokerPackageImpl#getNode()
         * @generated
         */
        EClass NODE = eINSTANCE.getNode();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NODE__TYPE = eINSTANCE.getNode_Type();

        /**
         * The meta object literal for the '{@link net.remus.infobroker.infobroker.impl.ValueImpl <em>Value</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see net.remus.infobroker.infobroker.impl.ValueImpl
         * @see net.remus.infobroker.infobroker.impl.InfobrokerPackageImpl#getValue()
         * @generated
         */
        EClass VALUE = eINSTANCE.getValue();

        /**
         * The meta object literal for the '<em><b>String</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute VALUE__STRING = eINSTANCE.getValue_String();

        /**
         * The meta object literal for the '<em><b>Boolean</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute VALUE__BOOLEAN = eINSTANCE.getValue_Boolean();

        /**
         * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute VALUE__DATE = eINSTANCE.getValue_Date();

        /**
         * The meta object literal for the '<em><b>Long</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute VALUE__LONG = eINSTANCE.getValue_Long();

        /**
         * The meta object literal for the '<em><b>Int</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute VALUE__INT = eINSTANCE.getValue_Int();

        /**
         * The meta object literal for the '<em><b>Double</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute VALUE__DOUBLE = eINSTANCE.getValue_Double();

        /**
         * The meta object literal for the '<em><b>Referenced Child Nodes</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference VALUE__REFERENCED_CHILD_NODES = eINSTANCE.getValue_ReferencedChildNodes();

        /**
         * The meta object literal for the '{@link net.remus.infobroker.infobroker.impl.CredentialsImpl <em>Credentials</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see net.remus.infobroker.infobroker.impl.CredentialsImpl
         * @see net.remus.infobroker.infobroker.impl.InfobrokerPackageImpl#getCredentials()
         * @generated
         */
        EClass CREDENTIALS = eINSTANCE.getCredentials();

        /**
         * The meta object literal for the '{@link net.remus.infobroker.infobroker.impl.BaseImpl <em>Base</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see net.remus.infobroker.infobroker.impl.BaseImpl
         * @see net.remus.infobroker.infobroker.impl.InfobrokerPackageImpl#getBase()
         * @generated
         */
        EClass BASE = eINSTANCE.getBase();

        /**
         * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BASE__LABEL = eINSTANCE.getBase_Label();

        /**
         * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BASE__DESCRIPTION = eINSTANCE.getBase_Description();

        /**
         * The meta object literal for the '<em><b>Creation Time</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BASE__CREATION_TIME = eINSTANCE.getBase_CreationTime();

        /**
         * The meta object literal for the '<em><b>Tags</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BASE__TAGS = eINSTANCE.getBase_Tags();

        /**
         * The meta object literal for the '{@link net.remus.infobroker.infobroker.impl.PrimaryNodeImpl <em>Primary Node</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see net.remus.infobroker.infobroker.impl.PrimaryNodeImpl
         * @see net.remus.infobroker.infobroker.impl.InfobrokerPackageImpl#getPrimaryNode()
         * @generated
         */
        EClass PRIMARY_NODE = eINSTANCE.getPrimaryNode();

        /**
         * The meta object literal for the '<em><b>Contained Child Nodes</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PRIMARY_NODE__CONTAINED_CHILD_NODES = eINSTANCE.getPrimaryNode_ContainedChildNodes();

    }

} //InfobrokerPackage
