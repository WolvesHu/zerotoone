package com.wolves.zerotoone.orm.mapping;

import java.sql.ResultSet;

public enum MyResultSetType {
	FORWARD_ONLY(ResultSet.TYPE_FORWARD_ONLY), 
	SCROLL_INSENSITIVE(ResultSet.TYPE_SCROLL_INSENSITIVE), 
	SCROLL_SENSITIVE(ResultSet.TYPE_SCROLL_SENSITIVE);

	private int value;

	MyResultSetType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
