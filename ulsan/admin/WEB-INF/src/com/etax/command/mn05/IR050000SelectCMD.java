/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR050000SelectCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금배정 > 자금배정진행상태
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn05.IR050000;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;


public class IR050000SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR050000SelectCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    
			/* 자금배정진행상태 */
    CommonEntity allotingInfo = IR050000.getAllotingStat(conn);
    request.setAttribute("page.mn05.allotingInfo", allotingInfo);
  }
}