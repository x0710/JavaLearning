package com.jh.connect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Field {
	private String type;
	private String field;
	private boolean beNull;
	private String pk;
	private String defaultString;
	private String extra;
	
	
	Field(String type, String field, boolean beNull, String pk, String defaultString, String extra) {
		super();
		this.type = type;
		this.field = field;
		this.beNull = beNull;
		this.pk = pk;
		this.defaultString = defaultString;
		this.extra = extra;
	}
	public String getType() {
		return type;
	}

	public String getField() {
		return field;
	}

	public boolean isBeNull() {
		return beNull;
	}

	public String getPk() {
		return pk;
	}

	public String getDefaultString() {
		return defaultString;
	}

	public String getExtra() {
		return extra;
	}
	

	
}
