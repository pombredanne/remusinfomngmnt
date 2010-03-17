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
 * A representation of the literals of the enumeration '<em><b>Synchronization Action</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.remus.infomngmnt.InfomngmntPackage#getSynchronizationAction()
 * @model
 * @generated
 */
public enum SynchronizationAction implements Enumerator {
	/**
	 * The '<em><b>REPLACE LOCAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REPLACE_LOCAL_VALUE
	 * @generated
	 * @ordered
	 */
	REPLACE_LOCAL(0, "REPLACE_LOCAL", "REPLACE_LOCAL"),

	/**
	 * The '<em><b>REPLACE REMOTE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REPLACE_REMOTE_VALUE
	 * @generated
	 * @ordered
	 */
	REPLACE_REMOTE(1, "REPLACE_REMOTE", "REPLACE_REMOTE"),

	/**
	 * The '<em><b>DELETE LOCAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DELETE_LOCAL_VALUE
	 * @generated
	 * @ordered
	 */
	DELETE_LOCAL(2, "DELETE_LOCAL", "DELETE_LOCAL"),

	/**
	 * The '<em><b>DELETE REMOTE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DELETE_REMOTE_VALUE
	 * @generated
	 * @ordered
	 */
	DELETE_REMOTE(3, "DELETE_REMOTE", "DELETE_REMOTE"),

	/**
	 * The '<em><b>ADD LOCAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ADD_LOCAL_VALUE
	 * @generated
	 * @ordered
	 */
	ADD_LOCAL(4, "ADD_LOCAL", "ADD_LOCAL"),

	/**
	 * The '<em><b>ADD REMOTE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ADD_REMOTE_VALUE
	 * @generated
	 * @ordered
	 */
	ADD_REMOTE(5, "ADD_REMOTE", "ADD_REMOTE"),

	/**
	 * The '<em><b>RESOLVE CONFLICT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RESOLVE_CONFLICT_VALUE
	 * @generated
	 * @ordered
	 */
	RESOLVE_CONFLICT(6, "RESOLVE_CONFLICT", "RESOLVE_CONFLICT"), /**
	 * The '<em><b>SKIP LOCAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SKIP_LOCAL_VALUE
	 * @generated
	 * @ordered
	 */
	SKIP_LOCAL(7, "SKIP_LOCAL", "SKIP_LOCAL"), /**
	 * The '<em><b>SKIP REMOTE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SKIP_REMOTE_VALUE
	 * @generated
	 * @ordered
	 */
	SKIP_REMOTE(8, "SKIP_REMOTE", "SKIP_REMOTE");

	/**
	 * The '<em><b>REPLACE LOCAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>REPLACE LOCAL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #REPLACE_LOCAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int REPLACE_LOCAL_VALUE = 0;

	/**
	 * The '<em><b>REPLACE REMOTE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>REPLACE REMOTE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #REPLACE_REMOTE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int REPLACE_REMOTE_VALUE = 1;

	/**
	 * The '<em><b>DELETE LOCAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DELETE LOCAL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DELETE_LOCAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int DELETE_LOCAL_VALUE = 2;

	/**
	 * The '<em><b>DELETE REMOTE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DELETE REMOTE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DELETE_REMOTE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int DELETE_REMOTE_VALUE = 3;

	/**
	 * The '<em><b>ADD LOCAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>ADD LOCAL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ADD_LOCAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ADD_LOCAL_VALUE = 4;

	/**
	 * The '<em><b>ADD REMOTE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>ADD REMOTE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ADD_REMOTE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ADD_REMOTE_VALUE = 5;

	/**
	 * The '<em><b>RESOLVE CONFLICT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>RESOLVE CONFLICT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RESOLVE_CONFLICT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int RESOLVE_CONFLICT_VALUE = 6;

	/**
	 * The '<em><b>SKIP LOCAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SKIP LOCAL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SKIP_LOCAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SKIP_LOCAL_VALUE = 7;

	/**
	 * The '<em><b>SKIP REMOTE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SKIP REMOTE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SKIP_REMOTE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SKIP_REMOTE_VALUE = 8;

	/**
	 * An array of all the '<em><b>Synchronization Action</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final SynchronizationAction[] VALUES_ARRAY =
		new SynchronizationAction[] {
			REPLACE_LOCAL,
			REPLACE_REMOTE,
			DELETE_LOCAL,
			DELETE_REMOTE,
			ADD_LOCAL,
			ADD_REMOTE,
			RESOLVE_CONFLICT,
			SKIP_LOCAL,
			SKIP_REMOTE,
		};

	/**
	 * A public read-only list of all the '<em><b>Synchronization Action</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<SynchronizationAction> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Synchronization Action</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SynchronizationAction get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			SynchronizationAction result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Synchronization Action</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SynchronizationAction getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			SynchronizationAction result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Synchronization Action</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SynchronizationAction get(int value) {
		switch (value) {
			case REPLACE_LOCAL_VALUE: return REPLACE_LOCAL;
			case REPLACE_REMOTE_VALUE: return REPLACE_REMOTE;
			case DELETE_LOCAL_VALUE: return DELETE_LOCAL;
			case DELETE_REMOTE_VALUE: return DELETE_REMOTE;
			case ADD_LOCAL_VALUE: return ADD_LOCAL;
			case ADD_REMOTE_VALUE: return ADD_REMOTE;
			case RESOLVE_CONFLICT_VALUE: return RESOLVE_CONFLICT;
			case SKIP_LOCAL_VALUE: return SKIP_LOCAL;
			case SKIP_REMOTE_VALUE: return SKIP_REMOTE;
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
	private SynchronizationAction(int value, String name, String literal) {
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
	
} //SynchronizationAction
