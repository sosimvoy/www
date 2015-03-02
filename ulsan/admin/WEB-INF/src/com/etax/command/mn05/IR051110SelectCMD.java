/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR051110SelectCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금배정 > 잉여금이입승인조회
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn05.IR051110;
import com.etax.db.mn05.IR050000;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;

public class IR051110SelectCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR051110SelectCMD.class); // log4j 설정

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

        CommonEntity paramInfo = new CommonEntity();
        paramInfo.setValue("reg_date",    request.getParameter("reg_date"));
        paramInfo.setValue("acc_date",    request.getParameter("reg_date")); //마감조회용
        paramInfo.setValue("fis_year",   (request.getParameter("reg_date")).substring(0,4)); //마감조회용
    
        logger.info("paramInfo : " + paramInfo);
	  
		/* 잉여금이입 조회 */
		List<CommonEntity> srpPmsList = IR051110.getSrpCancelList(conn, paramInfo);
		request.setAttribute("page.mn05.srpPmsList", srpPmsList);

        CommonEntity magamInfo = IR050000.getDailyEndWork(conn, paramInfo);
        request.setAttribute("page.mn05.magamState",  (String)magamInfo.getString("M210_WORKENDSTATE"));
    }
}