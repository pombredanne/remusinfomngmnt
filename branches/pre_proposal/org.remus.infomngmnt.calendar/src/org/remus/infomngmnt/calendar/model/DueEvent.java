/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.remus.infomngmnt.calendar.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Due Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.calendar.model.DueEvent#getTask <em>Task</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getDueEvent()
 * @model
 * @generated
 */
public interface DueEvent extends CEvent {
	/**
	 * Returns the value of the '<em><b>Task</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.remus.infomngmnt.calendar.model.Task#getDue <em>Due</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Task</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Task</em>' container reference.
	 * @see #setTask(Task)
	 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getDueEvent_Task()
	 * @see org.remus.infomngmnt.calendar.model.Task#getDue
	 * @model opposite="due"
	 * @generated
	 */
	Task getTask();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.calendar.model.DueEvent#getTask <em>Task</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Task</em>' container reference.
	 * @see #getTask()
	 * @generated
	 */
	void setTask(Task value);

} // DueEvent
