/******************************************************************************
 * 화 일 이 름 : DbManager.java
 * 최초 작성일 : 2009-12-11
 * 최근 변경일 : 2009-12-11
 * 설       명 : DbManager
 * MODIFY #1 2009-12-11 :
 *        #2 2009-12-11 :
 ******************************************************************************/

package com.etax.util;

import java.sql.*;

import org.apache.log4j.*;

import com.etax.config.ETaxConfig;

public class DbManager {
    Logger logger = Logger.getLogger(DbManager.class);
    private Connection conn = null;

    public Connection getConnection(String gubun) {
      try {

        //울산 DB 접속
        if ("TEST".equals(gubun)) {
          conn = DriverManager.getConnection(ETaxConfig.getString("db.etax.url"),
                                             ETaxConfig.getString("db.etax.user"),
                                             ETaxConfig.getString("db.etax.pwd"));
        } else if("REAL".equals(gubun)) {
          conn = DriverManager.getConnection(ETaxConfig.getString("db.etax.r_url"),
                                             ETaxConfig.getString("db.etax.r_user"),
                                             ETaxConfig.getString("db.etax.r_pwd"));
				}

      } catch(Exception ex) {
        logger.info("DbManager.class Exception[" + ex + "]");
      }

      return conn;
    }
}
