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
 * A representation of the literals of the enumeration '<em><b>Rating</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.remus.infomngmnt.InfomngmntPackage#getRating()
 * @model
 * @generated
 */
public enum Rating implements Enumerator {
	/**
	 * The '<em><b>USELESS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #USELESS_VALUE
	 * @generated
	 * @ordered
	 */
	USELESS(0, "USELESS", "USELESS"),

	/**
	 * The '<em><b>POOR</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #POOR_VALUE
	 * @generated
	 * @ordered
	 */
	POOR(1, "POOR", "POOR"),

	/**
	 * The '<em><b>AVERAGE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #AVERAGE_VALUE
	 * @generated
	 * @ordered
	 */
	AVERAGE(2, "AVERAGE", "AVERAGE"),

	/**
	 * The '<em><b>HELPFUL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #HELPFUL_VALUE
	 * @generated
	 * @ordered
	 */
	HELPFUL(3, "HELPFUL", "HELPFUL"),

	/**
	 * The '<em><b>FANTASTIC</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FANTASTIC_VALUE
	 * @generated
	 * @ordered
	 */
	FANTASTIC(4, "FANTASTIC", "FANTASTIC");

	/**
	 * The '<em><b>USELESS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>USELESS</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #USELESS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int USELESS_VALUE = 0;

	/**
	 * The '<em><b>POOR</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>POOR</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #POOR
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int POOR_VALUE = 1;

	/**
	 * The '<em><b>AVERAGE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>AVERAGE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #AVERAGE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int AVERAGE_VALUE = 2;

	/**
	 * The '<em><b>HELPFUL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>HELPFUL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #HELPFUL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int HELPFUL_VALUE = 3;

	/**
	 * The '<em><b>FANTASTIC</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>FANTASTIC</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FANTASTIC
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int FANTASTIC_VALUE = 4;

	/**
	 * An array of all the '<em><b>Rating</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final Rating[] VALUES_ARRAY =
		new Rating[] {
			USELESS,
			POOR,
			AVERAGE,
			HELPFUL,
			FANTASTIC,
		};

	/**
	 * A public read-only list of all the '<em><b>Rating</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<Rating> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Rating</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Rating get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			Rating result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Rating</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Rating getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			Rating result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Rating</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Rating get(int value) {
		switch (value) {
			case USELESS_VALUE: return USELESS;
			case POOR_VALUE: return POOR;
			case AVERAGE_VALUE: return AVERAGE;
			case HELPFUL_VALUE: return HELPFUL;
			case FANTASTIC_VALUE: return FANTASTIC;
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
	private Rating(int value, String name, String literal) {
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
	
} //Rating
