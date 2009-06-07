/**
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * 
 * Contributors:
 *      Tom Seidel - initial API and implementation
 * 
 *
 * $Id$
 */
package org.remus.infomngmnt;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Synchronization State</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.remus.infomngmnt.InfomngmntPackage#getSynchronizationState()
 * @model
 * @generated
 */
public enum SynchronizationState implements Enumerator {
	/**
	 * The '<em><b>LOCAL DELETED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LOCAL_DELETED_VALUE
	 * @generated
	 * @ordered
	 */
	LOCAL_DELETED(0, "LOCAL_DELETED", "LOCAL_DELETED"),

	/**
	 * The '<em><b>TARGET DELETED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TARGET_DELETED_VALUE
	 * @generated
	 * @ordered
	 */
	TARGET_DELETED(1, "TARGET_DELETED", "TARGET_DELETED"),

	/**
	 * The '<em><b>NOT ADDED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NOT_ADDED_VALUE
	 * @generated
	 * @ordered
	 */
	NOT_ADDED(2, "NOT_ADDED", "NOT_ADDED"),

	/**
	 * The '<em><b>LOCAL EDITED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LOCAL_EDITED_VALUE
	 * @generated
	 * @ordered
	 */
	LOCAL_EDITED(3, "LOCAL_EDITED", "LOCAL_EDITED"),

	/**
	 * The '<em><b>TARGET EDITED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TARGET_EDITED_VALUE
	 * @generated
	 * @ordered
	 */
	TARGET_EDITED(4, "TARGET_EDITED", "TARGET_EDITED"), /**
	 * The '<em><b>IN SYNC</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #IN_SYNC_VALUE
	 * @generated
	 * @ordered
	 */
	IN_SYNC(5, "IN_SYNC", "IN_SYNC"), /**
	 * The '<em><b>IGNORED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #IGNORED_VALUE
	 * @generated
	 * @ordered
	 */
	IGNORED(6, "IGNORED", "IGNORED");

	/**
	 * The '<em><b>LOCAL DELETED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>LOCAL DELETED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LOCAL_DELETED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int LOCAL_DELETED_VALUE = 0;

	/**
	 * The '<em><b>TARGET DELETED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>TARGET DELETED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TARGET_DELETED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int TARGET_DELETED_VALUE = 1;

	/**
	 * The '<em><b>NOT ADDED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>NOT ADDED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NOT_ADDED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int NOT_ADDED_VALUE = 2;

	/**
	 * The '<em><b>LOCAL EDITED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>LOCAL EDITED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LOCAL_EDITED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int LOCAL_EDITED_VALUE = 3;

	/**
	 * The '<em><b>TARGET EDITED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>TARGET EDITED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TARGET_EDITED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int TARGET_EDITED_VALUE = 4;

	/**
	 * The '<em><b>IN SYNC</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>IN SYNC</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #IN_SYNC
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int IN_SYNC_VALUE = 5;

	/**
	 * The '<em><b>IGNORED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>IGNORED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #IGNORED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int IGNORED_VALUE = 6;

	/**
	 * An array of all the '<em><b>Synchronization State</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final SynchronizationState[] VALUES_ARRAY =
		new SynchronizationState[] {
			LOCAL_DELETED,
			TARGET_DELETED,
			NOT_ADDED,
			LOCAL_EDITED,
			TARGET_EDITED,
			IN_SYNC,
			IGNORED,
		};

	/**
	 * A public read-only list of all the '<em><b>Synchronization State</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<SynchronizationState> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Synchronization State</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SynchronizationState get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			SynchronizationState result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Synchronization State</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SynchronizationState getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			SynchronizationState result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Synchronization State</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SynchronizationState get(int value) {
		switch (value) {
			case LOCAL_DELETED_VALUE: return LOCAL_DELETED;
			case TARGET_DELETED_VALUE: return TARGET_DELETED;
			case NOT_ADDED_VALUE: return NOT_ADDED;
			case LOCAL_EDITED_VALUE: return LOCAL_EDITED;
			case TARGET_EDITED_VALUE: return TARGET_EDITED;
			case IN_SYNC_VALUE: return IN_SYNC;
			case IGNORED_VALUE: return IGNORED;
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
	private SynchronizationState(int value, String name, String literal) {
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
	
} //SynchronizationState
