/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR051310SelectCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금배정 > 잉여금이입통지서조회
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn05.IR051310;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;

public class IR051310SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR051310SelectCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		if (!"".equals(request.getParameter("this_year")) )	{
		  paramInfo.setValue("this_year",   request.getParameter("this_year"));
		} else {
			paramInfo.setValue("this_year",   TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy"));
		}
		
    if (!"".equals(request.getParameter("dept_list")) )	{
			paramInfo.setValue("dept_list",   request.getParameter("dept_list"));
    } else {
		  paramInfo.setValue("dept_list",   "00000");
		}
    
    logger.info("paramInfo : " + paramInfo);
	  
		/* 부서명 조회 */
		List<CommonEntity> deptList = IR051310.getDeptList(conn, paramInfo);
		request.setAttribute("page.mn05.deptList", deptList);

		/* 회계명 조회 */
		List<CommonEntity> accList = IR051310.getAccList(conn, paramInfo);
		request.setAttribute("page.mn05.accList", accList);
  }
}