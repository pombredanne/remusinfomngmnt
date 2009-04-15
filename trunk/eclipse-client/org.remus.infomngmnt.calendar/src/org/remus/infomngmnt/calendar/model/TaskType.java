/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.remus.infomngmnt.calendar.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Task Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.remus.infomngmnt.calendar.model.ModelPackage#getTaskType()
 * @model
 * @generated
 */
public enum TaskType implements Enumerator {
	/**
	 * The '<em><b>ONE TIME</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ONE_TIME_VALUE
	 * @generated
	 * @ordered
	 */
	ONE_TIME(0, "ONE_TIME", "ONE_TIME"),

	/**
	 * The '<em><b>WEEKLY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WEEKLY_VALUE
	 * @generated
	 * @ordered
	 */
	WEEKLY(1, "WEEKLY", "WEEKLY"),

	/**
	 * The '<em><b>TWO WEEK</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TWO_WEEK_VALUE
	 * @generated
	 * @ordered
	 */
	TWO_WEEK(2, "TWO_WEEK", "TWO_WEEK"),

	/**
	 * The '<em><b>MONTHLY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MONTHLY_VALUE
	 * @generated
	 * @ordered
	 */
	MONTHLY(3, "MONTHLY", "MONTHLY"),

	/**
	 * The '<em><b>ANNUAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ANNUAL_VALUE
	 * @generated
	 * @ordered
	 */
	ANNUAL(4, "ANNUAL", "ANNUAL");

	/**
	 * The '<em><b>ONE TIME</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>ONE TIME</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ONE_TIME
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ONE_TIME_VALUE = 0;

	/**
	 * The '<em><b>WEEKLY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>WEEKLY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #WEEKLY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int WEEKLY_VALUE = 1;

	/**
	 * The '<em><b>TWO WEEK</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>TWO WEEK</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TWO_WEEK
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int TWO_WEEK_VALUE = 2;

	/**
	 * The '<em><b>MONTHLY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>MONTHLY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MONTHLY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int MONTHLY_VALUE = 3;

	/**
	 * The '<em><b>ANNUAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>ANNUAL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ANNUAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ANNUAL_VALUE = 4;

	/**
	 * An array of all the '<em><b>Task Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final TaskType[] VALUES_ARRAY =
		new TaskType[] {
			ONE_TIME,
			WEEKLY,
			TWO_WEEK,
			MONTHLY,
			ANNUAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Task Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<TaskType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Task Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TaskType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TaskType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Task Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TaskType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TaskType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Task Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TaskType get(int value) {
		switch (value) {
			case ONE_TIME_VALUE: return ONE_TIME;
			case WEEKLY_VALUE: return WEEKLY;
			case TWO_WEEK_VALUE: return TWO_WEEK;
			case MONTHLY_VALUE: return MONTHLY;
			case ANNUAL_VALUE: return ANNUAL;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private TaskType(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //TaskType
