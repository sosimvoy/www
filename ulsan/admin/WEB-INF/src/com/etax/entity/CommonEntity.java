package com.etax.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@SuppressWarnings("serial")
public class CommonEntity extends HashMap<Object, Object> {

	public CommonEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CommonEntity(String[] args) {
		for( int i=0 ; i<args.length ; i++) {
			this.setValue(args[i], args[i]);
		}
	}

	public CommonEntity(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
		// TODO Auto-generated constructor stub
	}

	public CommonEntity(int initialCapacity) {
		super(initialCapacity);
		// TODO Auto-generated constructor stub
	}

	public CommonEntity(Map<Object, Object> m) {
		super(m);
		// TODO Auto-generated constructor stub
	}

	public String getString(String key) {
		try {
			if( super.get(key.toUpperCase()) == null ) {
				return "";
			} else {
				return (String)super.get(key.toUpperCase());
			}
		} catch( Exception ex ) {
			ex.printStackTrace();
			return "";
		}
	}

	public String getString(String key, String defaultVal) {
		if( "".equals(this.getString(key)) ) {
			return defaultVal;
		} else {
			return this.getString(key);
		}
	}

	public int getInt(String key) {
		int returnValue = 0;
		try {
			if( super.get(key.toUpperCase()) == null ) return returnValue;
			returnValue = ((Integer)super.get(key.toUpperCase())).intValue();
		} catch(Exception e) {
			returnValue = Integer.parseInt((String)super.get(key.toUpperCase()));
		}
		return returnValue;
	}

	public long getLong(String key) {
		long returnValue = 0;
		try {
			if( super.get(key.toUpperCase()) == null ) return returnValue;
			returnValue = ((Long)super.get(key.toUpperCase())).longValue();
		} catch(Exception e) {
			returnValue = Long.parseLong((String)super.get(key.toUpperCase()));
		}
		return returnValue;
	}


	public double getDouble(String key) {
		double returnValue = 0d;
		try {
			if( super.get(key.toUpperCase()) == null ) return returnValue;
			returnValue = ((Double)super.get(key.toUpperCase())).doubleValue();
		} catch(Exception e) {
			if("".equals((String)super.get(key.toUpperCase()))) {
				return 0d;
			}
			returnValue = Double.parseDouble((String)super.get(key.toUpperCase()));
		}
		return returnValue;
	}

	public Object get(String key) {
		return super.get(key.toUpperCase());
	}

	public void setValue(String key, Object value) {
		super.put(key.toUpperCase(),value);
	}

	public void setValue(Object key, Object value) {
		super.put(key, value);
	}

	public String toString() {
		return super.toString();
	}

  @SuppressWarnings("unchecked")
public Set keySet() {
    return super.keySet();
  }
}