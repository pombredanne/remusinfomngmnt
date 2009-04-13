/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.remus.infomngmnt.calendar.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Task</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.calendar.model.Task#getName <em>Name</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.Task#getPriority <em>Priority</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.Task#getDetails <em>Details</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.Task#getOwner <em>Owner</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.Task#getStart <em>Start</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.Task#getEnd <em>End</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.Task#getDue <em>Due</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.Task#getCleared <em>Cleared</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.Task#getProgress <em>Progress</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.Task#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getTask()
 * @model
 * @generated
 */
public interface Task extends EObject {
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
	 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getTask_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.calendar.model.Task#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Priority</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Priority</em>' attribute.
	 * @see #setPriority(int)
	 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getTask_Priority()
	 * @model
	 * @generated
	 */
	int getPriority();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.calendar.model.Task#getPriority <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Priority</em>' attribute.
	 * @see #getPriority()
	 * @generated
	 */
	void setPriority(int value);

	/**
	 * Returns the value of the '<em><b>Details</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Details</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Details</em>' attribute.
	 * @see #setDetails(String)
	 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getTask_Details()
	 * @model
	 * @generated
	 */
	String getDetails();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.calendar.model.Task#getDetails <em>Details</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Details</em>' attribute.
	 * @see #getDetails()
	 * @generated
	 */
	void setDetails(String value);

	/**
	 * Returns the value of the '<em><b>Owner</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.remus.infomngmnt.calendar.model.Tasklist#getTasks <em>Tasks</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner</em>' container reference.
	 * @see #setOwner(Tasklist)
	 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getTask_Owner()
	 * @see org.remus.infomngmnt.calendar.model.Tasklist#getTasks
	 * @model opposite="tasks" transient="false"
	 * @generated
	 */
	Tasklist getOwner();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.calendar.model.Task#getOwner <em>Owner</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' container reference.
	 * @see #getOwner()
	 * @generated
	 */
	void setOwner(Tasklist value);

	/**
	 * Returns the value of the '<em><b>Start</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.remus.infomngmnt.calendar.model.StartEvent#getTask <em>Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start</em>' containment reference.
	 * @see #setStart(StartEvent)
	 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getTask_Start()
	 * @see org.remus.infomngmnt.calendar.model.StartEvent#getTask
	 * @model opposite="task" containment="true"
	 * @generated
	 */
	StartEvent getStart();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.calendar.model.Task#getStart <em>Start</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start</em>' containment reference.
	 * @see #getStart()
	 * @generated
	 */
	void setStart(StartEvent value);

	/**
	 * Returns the value of the '<em><b>End</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.remus.infomngmnt.calendar.model.EndEvent#getTask <em>Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End</em>' containment reference.
	 * @see #setEnd(EndEvent)
	 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getTask_End()
	 * @see org.remus.infomngmnt.calendar.model.EndEvent#getTask
	 * @model opposite="task" containment="true"
	 * @generated
	 */
	EndEvent getEnd();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.calendar.model.Task#getEnd <em>End</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End</em>' containment reference.
	 * @see #getEnd()
	 * @generated
	 */
	void setEnd(EndEvent value);

	/**
	 * Returns the value of the '<em><b>Due</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.remus.infomngmnt.calendar.model.DueEvent#getTask <em>Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Due</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Due</em>' containment reference.
	 * @see #setDue(DueEvent)
	 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getTask_Due()
	 * @see org.remus.infomngmnt.calendar.model.DueEvent#getTask
	 * @model opposite="task" containment="true"
	 * @generated
	 */
	DueEvent getDue();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.calendar.model.Task#getDue <em>Due</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Due</em>' containment reference.
	 * @see #getDue()
	 * @generated
	 */
	void setDue(DueEvent value);

	/**
	 * Returns the value of the '<em><b>Cleared</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.remus.infomngmnt.calendar.model.ClearedEvent#getTask <em>Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cleared</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cleared</em>' containment reference.
	 * @see #setCleared(ClearedEvent)
	 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getTask_Cleared()
	 * @see org.remus.infomngmnt.calendar.model.ClearedEvent#getTask
	 * @model opposite="task" containment="true"
	 * @generated
	 */
	ClearedEvent getCleared();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.calendar.model.Task#getCleared <em>Cleared</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cleared</em>' containment reference.
	 * @see #getCleared()
	 * @generated
	 */
	void setCleared(ClearedEvent value);

	/**
	 * Returns the value of the '<em><b>Progress</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Progress</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Progress</em>' attribute.
	 * @see #setProgress(double)
	 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getTask_Progress()
	 * @model
	 * @generated
	 */
	double getProgress();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.calendar.model.Task#getProgress <em>Progress</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Progress</em>' attribute.
	 * @see #getProgress()
	 * @generated
	 */
	void setProgress(double value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getTask_Id()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.calendar.model.Task#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

} // Task
