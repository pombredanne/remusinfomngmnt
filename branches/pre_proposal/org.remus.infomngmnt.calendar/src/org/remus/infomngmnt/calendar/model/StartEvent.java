/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.remus.infomngmnt.calendar.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Start Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.calendar.model.StartEvent#getTask <em>Task</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getStartEvent()
 * @model
 * @generated
 */
public interface StartEvent extends CEvent {
	/**
	 * Returns the value of the '<em><b>Task</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.remus.infomngmnt.calendar.model.Task#getStart <em>Start</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Task</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Task</em>' container reference.
	 * @see #setTask(Task)
	 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getStartEvent_Task()
	 * @see org.remus.infomngmnt.calendar.model.Task#getStart
	 * @model opposite="start"
	 * @generated
	 */
	Task getTask();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.calendar.model.StartEvent#getTask <em>Task</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Task</em>' container reference.
	 * @see #getTask()
	 * @generated
	 */
	void setTask(Task value);

} // StartEvent
