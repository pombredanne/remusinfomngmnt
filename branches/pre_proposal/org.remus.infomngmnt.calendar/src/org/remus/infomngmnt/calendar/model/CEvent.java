/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.remus.infomngmnt.calendar.model;

import java.util.Date;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>CEvent</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.calendar.model.CEvent#getName <em>Name</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.CEvent#isDateAbsolute <em>Date Absolute</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.CEvent#getDate <em>Date</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.CEvent#getReferenceEvent <em>Reference Event</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.CEvent#isAlarm <em>Alarm</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.CEvent#getPredecessors <em>Predecessors</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.CEvent#getSuccessors <em>Successors</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.CEvent#isAlarmDue <em>Alarm Due</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getCEvent()
 * @model
 * @generated
 */
public interface CEvent extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getCEvent_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.calendar.model.CEvent#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Date Absolute</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Date Absolute</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Date Absolute</em>' attribute.
	 * @see #setDateAbsolute(boolean)
	 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getCEvent_DateAbsolute()
	 * @model default="true"
	 * @generated
	 */
	boolean isDateAbsolute();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.calendar.model.CEvent#isDateAbsolute <em>Date Absolute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Date Absolute</em>' attribute.
	 * @see #isDateAbsolute()
	 * @generated
	 */
	void setDateAbsolute(boolean value);

	/**
	 * Returns the value of the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Date</em>' attribute.
	 * @see #setDate(Date)
	 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getCEvent_Date()
	 * @model
	 * @generated
	 */
	Date getDate();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.calendar.model.CEvent#getDate <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Date</em>' attribute.
	 * @see #getDate()
	 * @generated
	 */
	void setDate(Date value);

	/**
	 * Returns the value of the '<em><b>Reference Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reference Event</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reference Event</em>' reference.
	 * @see #setReferenceEvent(CEvent)
	 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getCEvent_ReferenceEvent()
	 * @model
	 * @generated
	 */
	CEvent getReferenceEvent();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.calendar.model.CEvent#getReferenceEvent <em>Reference Event</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reference Event</em>' reference.
	 * @see #getReferenceEvent()
	 * @generated
	 */
	void setReferenceEvent(CEvent value);

	/**
	 * Returns the value of the '<em><b>Alarm</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Alarm</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alarm</em>' attribute.
	 * @see #setAlarm(boolean)
	 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getCEvent_Alarm()
	 * @model default="false"
	 * @generated
	 */
	boolean isAlarm();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.calendar.model.CEvent#isAlarm <em>Alarm</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Alarm</em>' attribute.
	 * @see #isAlarm()
	 * @generated
	 */
	void setAlarm(boolean value);

	/**
	 * Returns the value of the '<em><b>Predecessors</b></em>' reference list.
	 * The list contents are of type {@link org.remus.infomngmnt.calendar.model.CEvent}.
	 * It is bidirectional and its opposite is '{@link org.remus.infomngmnt.calendar.model.CEvent#getSuccessors <em>Successors</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Predecessors</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Predecessors</em>' reference list.
	 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getCEvent_Predecessors()
	 * @see org.remus.infomngmnt.calendar.model.CEvent#getSuccessors
	 * @model opposite="successors"
	 * @generated
	 */
	EList<CEvent> getPredecessors();

	/**
	 * Returns the value of the '<em><b>Successors</b></em>' reference list.
	 * The list contents are of type {@link org.remus.infomngmnt.calendar.model.CEvent}.
	 * It is bidirectional and its opposite is '{@link org.remus.infomngmnt.calendar.model.CEvent#getPredecessors <em>Predecessors</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Successors</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Successors</em>' reference list.
	 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getCEvent_Successors()
	 * @see org.remus.infomngmnt.calendar.model.CEvent#getPredecessors
	 * @model opposite="predecessors"
	 * @generated
	 */
	EList<CEvent> getSuccessors();

	/**
	 * Returns the value of the '<em><b>Alarm Due</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Alarm Due</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alarm Due</em>' attribute.
	 * @see #setAlarmDue(boolean)
	 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getCEvent_AlarmDue()
	 * @model
	 * @generated
	 */
	boolean isAlarmDue();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.calendar.model.CEvent#isAlarmDue <em>Alarm Due</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Alarm Due</em>' attribute.
	 * @see #isAlarmDue()
	 * @generated
	 */
	void setAlarmDue(boolean value);

} // CEvent
