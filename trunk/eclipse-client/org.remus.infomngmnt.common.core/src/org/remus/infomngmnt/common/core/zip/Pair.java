/*******************************************************************************
 * Copyright (c) 2009 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.common.core.zip;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class Pair<Key, Value> {

	public Pair() {
	}

	public Pair(final Key key, final Value value) {
		this.setKey(key);
		this.setValue(value);
	}

	private Key key = null;

	public Key getKey() {
		return this.key;
	}

	public void setKey(final Key key) {
		this.key = key;
	}

	private Value value = null;

	public Value getValue() {
		return this.value;
	}

	public void setValue(final Value value) {
		this.value = value;
	}

	@Override
	public int hashCode() {

		int hashCode = 0;

		if (this.getKey() != null)
			hashCode += this.getKey().hashCode();
		else
			hashCode += (new Object()).hashCode();

		hashCode *= 31;

		if (this.getValue() != null)
			hashCode += this.getValue().hashCode();
		else
			hashCode += (new Object()).hashCode();

		return hashCode;

	}

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("Pair(");
		stringBuffer.append((this.getKey() != null) ? this.getKey().toString() : "null");
		stringBuffer.append(", ");
		stringBuffer.append((this.getValue() != null) ? this.getValue().toString() : "null");
		stringBuffer.append(")");
		return stringBuffer.toString();
	}

	@Override
	public boolean equals(final Object object) {
		if (object == null || !(object instanceof Pair))
			return false;
		else if (object == this)
			return true;
		Pair pair = (Pair) object;
		return this.equals(pair.getKey(), this.getKey())
				&& this.equals(pair.getValue(), this.getValue());
	}

	private boolean equals(final Object object1, final Object object2) {
		if (object1 == object2)
			return true;
		else if (object1 == null || object2 == null)
			return false;
		return object1.equals(object2);
	}

}
