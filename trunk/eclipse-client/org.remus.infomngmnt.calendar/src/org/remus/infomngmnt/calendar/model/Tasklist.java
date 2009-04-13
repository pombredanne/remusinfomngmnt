/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.remus.infomngmnt.calendar.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tasklist</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.calendar.model.Tasklist#getTasks <em>Tasks</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getTasklist()
 * @model
 * @generated
 */
public interface Tasklist extends EObject {
	/**
	 * Returns the value of the '<em><b>Tasks</b></em>' containment reference list.
	 * The list contents are of type {@link org.remus.infomngmnt.calendar.model.Task}.
	 * It is bidirectional and its opposite is '{@link org.remus.infomngmnt.calendar.model.Task#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tasks</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tasks</em>' containment reference list.
	 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getTasklist_Tasks()
	 * @see org.remus.infomngmnt.calendar.model.Task#getOwner
	 * @model opposite="owner" containment="true"
	 * @generated
	 */
	EList<Task> getTasks();

} // Tasklist
