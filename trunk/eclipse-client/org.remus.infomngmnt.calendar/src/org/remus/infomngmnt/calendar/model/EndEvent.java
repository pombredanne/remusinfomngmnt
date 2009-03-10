/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.remus.infomngmnt.calendar.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>End Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.calendar.model.EndEvent#getTask <em>Task</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getEndEvent()
 * @model
 * @generated
 */
public interface EndEvent extends CEvent {
	/**
	 * Returns the value of the '<em><b>Task</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.remus.infomngmnt.calendar.model.Task#getEnd <em>End</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Task</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Task</em>' container reference.
	 * @see #setTask(Task)
	 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getEndEvent_Task()
	 * @see org.remus.infomngmnt.calendar.model.Task#getEnd
	 * @model opposite="end"
	 * @generated
	 */
	Task getTask();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.calendar.model.EndEvent#getTask <em>Task</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Task</em>' container reference.
	 * @see #getTask()
	 * @generated
	 */
	void setTask(Task value);

} // EndEvent
