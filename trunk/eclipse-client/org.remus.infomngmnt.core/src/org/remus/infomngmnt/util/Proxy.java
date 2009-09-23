/****************************************************************************
 * Copyright (c) 2004 Composent, Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Composent, Inc. - initial API and implementation
 *****************************************************************************/
package org.remus.infomngmnt.util;

import java.io.ObjectStreamException;
import java.io.Serializable;

public class Proxy implements Serializable {

	private static final long serialVersionUID = -2481483596779542834L;

	public static class Type implements Serializable {

		private static final long serialVersionUID = 361071073081975271L;

		private static final String DIRECT_NAME = "direct"; //$NON-NLS-1$

		private static final String HTTP_NAME = "http"; //$NON-NLS-1$

		private static final String SOCKS_NAME = "socks"; //$NON-NLS-1$

		private final transient String name;

		protected Type(final String name) {
			this.name = name;
		}

		public static final Type DIRECT = new Type(DIRECT_NAME);

		public static final Type HTTP = new Type(HTTP_NAME);

		public static final Type SOCKS = new Type(SOCKS_NAME);

		public static Type fromString(final String type) {
			if (type == null) {
				return null;
			} else if (type.equals(DIRECT_NAME)) {
				return DIRECT;
			} else if (type.equals(HTTP_NAME)) {
				return HTTP;
			} else if (type.equals(SOCKS_NAME)) {
				return SOCKS;
			} else {
				return null;
			}
		}

		@Override
		public String toString() {
			return this.name;
		}

		// For serialization
		private static int nextOrdinal = 0;

		private final int ordinal = nextOrdinal++;

		private static final Type[] VALUES = { DIRECT, HTTP, SOCKS };

		/**
		 * @return Object
		 * @throws ObjectStreamException
		 *             not thrown by this implementation.
		 */
		Object readResolve() throws ObjectStreamException {
			return VALUES[this.ordinal];
		}
	}

	ProxyAddress address;

	Type type;

	String username;

	String password;

	public final static Proxy NO_PROXY = new Proxy();

	private Proxy() {
		this.type = Type.DIRECT;
		this.address = null;
	}

	public Proxy(final Type type, final ProxyAddress address, final String username,
			final String password) {
		this.type = type;
		this.address = address;
		this.username = username;
		this.password = password;
	}

	public Proxy(final Type type, final ProxyAddress address) {
		this(type, address, null, null);
	}

	public Type getType() {
		return this.type;
	}

	public ProxyAddress getAddress() {
		return this.address;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public boolean hasCredentials() {
		return (this.username != null && !this.username.equals("")); //$NON-NLS-1$
	}

	@Override
	public String toString() {
		if (getType() == Type.DIRECT) {
			return "DIRECT"; //$NON-NLS-1$
		}
		return getType() + " @ " + getAddress(); //$NON-NLS-1$
	}

	@Override
	public final boolean equals(final Object obj) {
		if (obj == null || !(obj instanceof Proxy)) {
			return false;
		}
		Proxy p = (Proxy) obj;
		if (p.getType() == getType()) {
			if (getAddress() == null) {
				return (p.getAddress() == null);
			}
			return getAddress().equals(p.getAddress());
		}
		return false;
	}

	@Override
	public final int hashCode() {
		if (getAddress() == null) {
			return getType().hashCode();
		}
		return getType().hashCode() + getAddress().hashCode();
	}

}
