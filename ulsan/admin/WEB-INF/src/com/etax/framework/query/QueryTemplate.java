package com.etax.framework.query;

import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.entity.CommonEntity;

public class QueryTemplate {

	private static Logger logger = Logger.getLogger(QueryTemplate.class);	// log4j 설정

	private String query;

	public QueryTemplate() {

	}

	public QueryTemplate(String sql) {
		this.query = sql;
	}

	public void setQuery(String sql) {
		this.query = sql;
	}

	
	/**
	 * 리스트 조회
	 */
	public List<CommonEntity> getList(Connection conn, QueryParameters parameters) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CommonEntity> resultList = new ArrayList<CommonEntity>();
		try { 
			pstmt = conn.prepareStatement(this.query);
			if( parameters != null )
				parameters.setParameters(pstmt);
			  logger.info("[query]");
			  logger.info("=======================================================================================");
			  logger.info("\n"+this.query);
			  logger.info("=======================================================================================");
			  logger.info("[QueryParameters] : "+parameters);
			  rs =pstmt.executeQuery();

			ResultSetMetaData metaData = rs.getMetaData();

			while(rs.next()) {
				CommonEntity rowData = new CommonEntity();
				for( int i=0 ; i < metaData.getColumnCount() ; i++ ) {
					String columnName = metaData.getColumnName(i+1);
					rowData.setValue(columnName, rs.getString(columnName));
				}
				resultList.add(rowData);
			}

			return resultList;

		} finally {
			if(rs != null)		rs.close();
			if(pstmt != null)	pstmt.close();
		} 
	}

	public List<CommonEntity> getList(Connection conn) throws SQLException {
		return this.getList(conn, null);
	}


	/**
	 * 한건 상세 조회
	 */
    public CommonEntity getData(Connection conn, QueryParameters parameters) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CommonEntity rowData = new CommonEntity();
		try { 
			pstmt = conn.prepareStatement(this.query);
			if( parameters != null )
				parameters.setParameters(pstmt);
			  logger.info("[query]");
			  logger.info("=======================================================================================");
			  logger.info("\n"+this.query);
			  logger.info("=======================================================================================");
			  logger.info("[QueryParameters] : "+parameters);
			  rs =pstmt.executeQuery();

			  ResultSetMetaData metaData = rs.getMetaData();

			if(rs.next()) {
				rowData = new CommonEntity();
				for( int i=0 ; i < metaData.getColumnCount() ; i++ ) {
					String columnName = metaData.getColumnName(i+1);
					rowData.setValue(columnName, rs.getString(columnName));
				}
			}

			return rowData;

		} finally {
			if(rs != null)		rs.close();
			if(pstmt != null)	pstmt.close();
		} 
	}

	public CommonEntity getData(Connection conn) throws SQLException {
		return this.getData(conn, null);
	}



	/**
	 * 등록
	 */
	public int insert(Connection conn, QueryParameters parameters) throws SQLException {
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(this.query);
			if( parameters != null )
				parameters.setParameters(pstmt);
			  logger.info("[query]");
			  logger.info("=======================================================================================");
			  logger.info("\n"+this.query);
			  logger.info("=======================================================================================");
			  logger.info("[QueryParameters] : "+parameters);
			  result = pstmt.executeUpdate();
			  return result;
		} finally {
			if(pstmt != null)	pstmt.close();
		}
	}

	public int insert(Connection conn) throws SQLException {
		return this.insert(conn, null);
	}

	/**
	 * 수정
	 */
	public int update(Connection conn, QueryParameters parameters) throws SQLException {
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(this.query);
			if( parameters != null )
				parameters.setParameters(pstmt);
			  logger.info("[query]");
			  logger.info("=======================================================================================");
			  logger.info("\n"+this.query);
			  logger.info("=======================================================================================");
			  logger.info("[QueryParameters] : "+parameters);
			  result = pstmt.executeUpdate();
			return result;
		} finally {
			if(pstmt != null)	pstmt.close();
		}
	}

	public int update(Connection conn) throws SQLException {
		return this.update(conn, null);
	}

	/**
	 * 삭제
	 */
	public int delete(Connection conn, QueryParameters parameters) throws SQLException {
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(this.query);
			if( parameters != null )
				parameters.setParameters(pstmt);
			  logger.info("[query]");
			  logger.info("=======================================================================================");
			  logger.info("\n"+this.query);
			  logger.info("=======================================================================================");
			  logger.info("[QueryParameters] : "+parameters);
			  result = pstmt.executeUpdate();
			  return result;
		} finally {
			if(pstmt != null)	pstmt.close();
		}
	}
	
	public int delete(Connection conn) throws SQLException {
		return this.delete(conn, null);
	}


	/**
	 * 배치작업
	 */
	public int[] executeBatch(Connection conn, QueryParameters[] parameters) throws SQLException {
		PreparedStatement pstmt = null;
		int[] result;
		try {
			pstmt = conn.prepareStatement(this.query);
			for( int i=0 ; parameters != null && i<parameters.length ; i++ ) {
				parameters[i].setParameters(pstmt);
				pstmt.addBatch();
			}
			logger.info("[query]");
			logger.info("=======================================================================================");
			logger.info("\n"+this.query);
			logger.info("=======================================================================================");
			result = pstmt.executeBatch();
			return result;
		} finally {
			if(pstmt != null)	pstmt.close();
		}
	}
}
