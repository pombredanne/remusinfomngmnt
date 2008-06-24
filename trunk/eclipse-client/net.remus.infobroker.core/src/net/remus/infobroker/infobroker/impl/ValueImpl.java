/**
 * Copyright Tom Seidel 2008
 * All rights reserved
 *
 * $Id$
 */
package net.remus.infobroker.infobroker.impl;

import java.util.Collection;
import java.util.Date;

import net.remus.infobroker.infobroker.InfobrokerPackage;
import net.remus.infobroker.infobroker.Node;
import net.remus.infobroker.infobroker.Value;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.remus.infobroker.infobroker.impl.ValueImpl#getString <em>String</em>}</li>
 *   <li>{@link net.remus.infobroker.infobroker.impl.ValueImpl#getBoolean <em>Boolean</em>}</li>
 *   <li>{@link net.remus.infobroker.infobroker.impl.ValueImpl#getDate <em>Date</em>}</li>
 *   <li>{@link net.remus.infobroker.infobroker.impl.ValueImpl#getLong <em>Long</em>}</li>
 *   <li>{@link net.remus.infobroker.infobroker.impl.ValueImpl#getInt <em>Int</em>}</li>
 *   <li>{@link net.remus.infobroker.infobroker.impl.ValueImpl#getDouble <em>Double</em>}</li>
 *   <li>{@link net.remus.infobroker.infobroker.impl.ValueImpl#getReferencedChildNodes <em>Referenced Child Nodes</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ValueImpl extends NodeImpl implements Value {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright Tom Seidel 2008\r\nAll rights reserved";

    /**
     * The default value of the '{@link #getString() <em>String</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getString()
     * @generated
     * @ordered
     */
    protected static final String STRING_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getString() <em>String</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getString()
     * @generated
     * @ordered
     */
    protected String string = STRING_EDEFAULT;

    /**
     * The default value of the '{@link #getBoolean() <em>Boolean</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBoolean()
     * @generated
     * @ordered
     */
    protected static final String BOOLEAN_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getBoolean() <em>Boolean</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBoolean()
     * @generated
     * @ordered
     */
    protected String boolean_ = BOOLEAN_EDEFAULT;

    /**
     * The default value of the '{@link #getDate() <em>Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDate()
     * @generated
     * @ordered
     */
    protected static final Date DATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDate() <em>Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDate()
     * @generated
     * @ordered
     */
    protected Date date = DATE_EDEFAULT;

    /**
     * The default value of the '{@link #getLong() <em>Long</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLong()
     * @generated
     * @ordered
     */
    protected static final long LONG_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getLong() <em>Long</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLong()
     * @generated
     * @ordered
     */
    protected long long_ = LONG_EDEFAULT;

    /**
     * The default value of the '{@link #getInt() <em>Int</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInt()
     * @generated
     * @ordered
     */
    protected static final int INT_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getInt() <em>Int</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInt()
     * @generated
     * @ordered
     */
    protected int int_ = INT_EDEFAULT;

    /**
     * The default value of the '{@link #getDouble() <em>Double</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDouble()
     * @generated
     * @ordered
     */
    protected static final double DOUBLE_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getDouble() <em>Double</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDouble()
     * @generated
     * @ordered
     */
    protected double double_ = DOUBLE_EDEFAULT;

    /**
     * The cached value of the '{@link #getReferencedChildNodes() <em>Referenced Child Nodes</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getReferencedChildNodes()
     * @generated
     * @ordered
     */
    protected EList<Node> referencedChildNodes;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ValueImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return InfobrokerPackage.Literals.VALUE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getString() {
        return string;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setString(String newString) {
        String oldString = string;
        string = newString;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, InfobrokerPackage.VALUE__STRING, oldString, string));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getBoolean() {
        return boolean_;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setBoolean(String newBoolean) {
        String oldBoolean = boolean_;
        boolean_ = newBoolean;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, InfobrokerPackage.VALUE__BOOLEAN, oldBoolean, boolean_));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Date getDate() {
        return date;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDate(Date newDate) {
        Date oldDate = date;
        date = newDate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, InfobrokerPackage.VALUE__DATE, oldDate, date));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getLong() {
        return long_;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLong(long newLong) {
        long oldLong = long_;
        long_ = newLong;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, InfobrokerPackage.VALUE__LONG, oldLong, long_));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getInt() {
        return int_;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setInt(int newInt) {
        int oldInt = int_;
        int_ = newInt;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, InfobrokerPackage.VALUE__INT, oldInt, int_));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getDouble() {
        return double_;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDouble(double newDouble) {
        double oldDouble = double_;
        double_ = newDouble;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, InfobrokerPackage.VALUE__DOUBLE, oldDouble, double_));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Node> getReferencedChildNodes() {
        if (referencedChildNodes == null) {
            referencedChildNodes = new EObjectResolvingEList<Node>(Node.class, this, InfobrokerPackage.VALUE__REFERENCED_CHILD_NODES);
        }
        return referencedChildNodes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case InfobrokerPackage.VALUE__STRING:
                return getString();
            case InfobrokerPackage.VALUE__BOOLEAN:
                return getBoolean();
            case InfobrokerPackage.VALUE__DATE:
                return getDate();
            case InfobrokerPackage.VALUE__LONG:
                return new Long(getLong());
            case InfobrokerPackage.VALUE__INT:
                return new Integer(getInt());
            case InfobrokerPackage.VALUE__DOUBLE:
                return new Double(getDouble());
            case InfobrokerPackage.VALUE__REFERENCED_CHILD_NODES:
                return getReferencedChildNodes();
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
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case InfobrokerPackage.VALUE__STRING:
                setString((String)newValue);
                return;
            case InfobrokerPackage.VALUE__BOOLEAN:
                setBoolean((String)newValue);
                return;
            case InfobrokerPackage.VALUE__DATE:
                setDate((Date)newValue);
                return;
            case InfobrokerPackage.VALUE__LONG:
                setLong(((Long)newValue).longValue());
                return;
            case InfobrokerPackage.VALUE__INT:
                setInt(((Integer)newValue).intValue());
                return;
            case InfobrokerPackage.VALUE__DOUBLE:
                setDouble(((Double)newValue).doubleValue());
                return;
            case InfobrokerPackage.VALUE__REFERENCED_CHILD_NODES:
                getReferencedChildNodes().clear();
                getReferencedChildNodes().addAll((Collection<? extends Node>)newValue);
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
    public void eUnset(int featureID) {
        switch (featureID) {
            case InfobrokerPackage.VALUE__STRING:
                setString(STRING_EDEFAULT);
                return;
            case InfobrokerPackage.VALUE__BOOLEAN:
                setBoolean(BOOLEAN_EDEFAULT);
                return;
            case InfobrokerPackage.VALUE__DATE:
                setDate(DATE_EDEFAULT);
                return;
            case InfobrokerPackage.VALUE__LONG:
                setLong(LONG_EDEFAULT);
                return;
            case InfobrokerPackage.VALUE__INT:
                setInt(INT_EDEFAULT);
                return;
            case InfobrokerPackage.VALUE__DOUBLE:
                setDouble(DOUBLE_EDEFAULT);
                return;
            case InfobrokerPackage.VALUE__REFERENCED_CHILD_NODES:
                getReferencedChildNodes().clear();
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
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case InfobrokerPackage.VALUE__STRING:
                return STRING_EDEFAULT == null ? string != null : !STRING_EDEFAULT.equals(string);
            case InfobrokerPackage.VALUE__BOOLEAN:
                return BOOLEAN_EDEFAULT == null ? boolean_ != null : !BOOLEAN_EDEFAULT.equals(boolean_);
            case InfobrokerPackage.VALUE__DATE:
                return DATE_EDEFAULT == null ? date != null : !DATE_EDEFAULT.equals(date);
            case InfobrokerPackage.VALUE__LONG:
                return long_ != LONG_EDEFAULT;
            case InfobrokerPackage.VALUE__INT:
                return int_ != INT_EDEFAULT;
            case InfobrokerPackage.VALUE__DOUBLE:
                return double_ != DOUBLE_EDEFAULT;
            case InfobrokerPackage.VALUE__REFERENCED_CHILD_NODES:
                return referencedChildNodes != null && !referencedChildNodes.isEmpty();
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

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (string: ");
        result.append(string);
        result.append(", boolean: ");
        result.append(boolean_);
        result.append(", date: ");
        result.append(date);
        result.append(", long: ");
        result.append(long_);
        result.append(", int: ");
        result.append(int_);
        result.append(", double: ");
        result.append(double_);
        result.append(')');
        return result.toString();
    }

} //ValueImpl
