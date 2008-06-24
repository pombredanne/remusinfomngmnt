/**
 * Copyright Tom Seidel 2008
 * All rights reserved
 *
 * $Id$
 */
package net.remus.infobroker.infobroker.impl;

import java.util.Collection;
import java.util.Date;

import net.remus.infobroker.infobroker.Base;
import net.remus.infobroker.infobroker.InfobrokerPackage;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Base</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.remus.infobroker.infobroker.impl.BaseImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link net.remus.infobroker.infobroker.impl.BaseImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link net.remus.infobroker.infobroker.impl.BaseImpl#getCreationTime <em>Creation Time</em>}</li>
 *   <li>{@link net.remus.infobroker.infobroker.impl.BaseImpl#getTags <em>Tags</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class BaseImpl extends EObjectImpl implements Base {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright Tom Seidel 2008\r\nAll rights reserved";

    /**
     * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLabel()
     * @generated
     * @ordered
     */
    protected static final String LABEL_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLabel()
     * @generated
     * @ordered
     */
    protected String label = LABEL_EDEFAULT;

    /**
     * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected static final String DESCRIPTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected String description = DESCRIPTION_EDEFAULT;

    /**
     * The default value of the '{@link #getCreationTime() <em>Creation Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCreationTime()
     * @generated
     * @ordered
     */
    protected static final Date CREATION_TIME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getCreationTime() <em>Creation Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCreationTime()
     * @generated
     * @ordered
     */
    protected Date creationTime = CREATION_TIME_EDEFAULT;

    /**
     * The cached value of the '{@link #getTags() <em>Tags</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTags()
     * @generated
     * @ordered
     */
    protected EList<String> tags;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected BaseImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return InfobrokerPackage.Literals.BASE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLabel(final String newLabel) {
        final String oldLabel = this.label;
        this.label = newLabel;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, InfobrokerPackage.BASE__LABEL, oldLabel, this.label));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDescription(final String newDescription) {
        final String oldDescription = this.description;
        this.description = newDescription;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, InfobrokerPackage.BASE__DESCRIPTION, oldDescription, this.description));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Date getCreationTime() {
        return this.creationTime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCreationTime(final Date newCreationTime) {
        final Date oldCreationTime = this.creationTime;
        this.creationTime = newCreationTime;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, InfobrokerPackage.BASE__CREATION_TIME, oldCreationTime, this.creationTime));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<String> getTags() {
        if (this.tags == null) {
            this.tags = new EDataTypeUniqueEList<String>(String.class, this, InfobrokerPackage.BASE__TAGS);
        }
        return this.tags;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
        switch (featureID) {
            case InfobrokerPackage.BASE__LABEL:
                return getLabel();
            case InfobrokerPackage.BASE__DESCRIPTION:
                return getDescription();
            case InfobrokerPackage.BASE__CREATION_TIME:
                return getCreationTime();
            case InfobrokerPackage.BASE__TAGS:
                return getTags();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(final int featureID, final Object newValue) {
        switch (featureID) {
            case InfobrokerPackage.BASE__LABEL:
                setLabel((String)newValue);
                return;
            case InfobrokerPackage.BASE__DESCRIPTION:
                setDescription((String)newValue);
                return;
            case InfobrokerPackage.BASE__CREATION_TIME:
                setCreationTime((Date)newValue);
                return;
            case InfobrokerPackage.BASE__TAGS:
                getTags().clear();
                getTags().addAll((Collection<? extends String>)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(final int featureID) {
        switch (featureID) {
            case InfobrokerPackage.BASE__LABEL:
                setLabel(LABEL_EDEFAULT);
                return;
            case InfobrokerPackage.BASE__DESCRIPTION:
                setDescription(DESCRIPTION_EDEFAULT);
                return;
            case InfobrokerPackage.BASE__CREATION_TIME:
                setCreationTime(CREATION_TIME_EDEFAULT);
                return;
            case InfobrokerPackage.BASE__TAGS:
                getTags().clear();
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(final int featureID) {
        switch (featureID) {
            case InfobrokerPackage.BASE__LABEL:
                return LABEL_EDEFAULT == null ? this.label != null : !LABEL_EDEFAULT.equals(this.label);
            case InfobrokerPackage.BASE__DESCRIPTION:
                return DESCRIPTION_EDEFAULT == null ? this.description != null : !DESCRIPTION_EDEFAULT.equals(this.description);
            case InfobrokerPackage.BASE__CREATION_TIME:
                return CREATION_TIME_EDEFAULT == null ? this.creationTime != null : !CREATION_TIME_EDEFAULT.equals(this.creationTime);
            case InfobrokerPackage.BASE__TAGS:
                return this.tags != null && !this.tags.isEmpty();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        final StringBuffer result = new StringBuffer(super.toString());
        result.append(" (label: ");
        result.append(this.label);
        result.append(", description: ");
        result.append(this.description);
        result.append(", creationTime: ");
        result.append(this.creationTime);
        result.append(", tags: ");
        result.append(this.tags);
        result.append(')');
        return result.toString();
    }
    
    
    public Object getAdapter(final Class adapter) {
        return Platform.getAdapterManager().getAdapter(this, adapter);
    }

} //BaseImpl
