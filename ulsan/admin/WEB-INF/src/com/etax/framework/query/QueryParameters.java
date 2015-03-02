package com.etax.framework.query;

import java.util.*;
import java.sql.*;
import org.apache.log4j.Logger;


public class QueryParameters {

	private static Logger logger = Logger.getLogger(QueryParameters.class);	// log4j ¼³Á¤
	
	private List<Object> parameters;

	public QueryParameters() {
		this.parameters = new ArrayList<Object>();
	}

	public void setString(int index, String value) {
		this.parameters.add(index-1, value); 
	}

	public void setInt(int index, int value) {
		this.parameters.add(index-1, new Integer(value));
	}


	protected void setParameters(PreparedStatement pstmt) throws SQLException {
		for( int i=0 ; i < this.parameters.size() ; i++ ) {
			Object object = this.parameters.get(i);
			if( object instanceof String ) {
				pstmt.setString(i+1, (String)object);
			} else if( object instanceof Integer ) {
				pstmt.setInt(i+1, ((Integer)object).intValue());
			}
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		logger.info("this.parameters.size()="+this.parameters.size());
		for( int i=0 ; i < this.parameters.size() ; i++ ) {
			Object paramValue = this.parameters.get(i);
			if( i != this.parameters.size()-1 )
				sb.append(i+1 + "=" + paramValue + ", ");
			else 
				sb.append(i+1 + "=" + paramValue);
		}
		sb.append("]");
		return sb.toString();
	}
}