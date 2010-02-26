package org.remus.infomngmnt.common.core.util;

public class KeyValueObject {
	public KeyValueObject(final String id, final String value) {
		super();
		this.id = id;
		this.value = value;
	}

	private final String id;

	public String getId() {
		return this.id;
	}

	public String getValue() {
		return this.value;
	}

	private final String value;
}