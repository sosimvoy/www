/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR051410DeleteCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금배정 > 잉여금이입수기분취소
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn05.IR051410;
import com.etax.db.mn05.IR050000;
import com.etax.entity.CommonEntity;

public class IR051410DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR051410DeleteCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("fis_year",   request.getParameter("fis_year"));
		paramInfo.setValue("acc_date",   request.getParameter("acc_date"));
    
    logger.info("paramInfo : " + paramInfo);

		String[] chk = request.getParameterValues("allotChk");
		String[] m240_seq = request.getParameterValues("m240_seq");
		String[] m010_seq = request.getParameterValues("m010_seq");

		CommonEntity endChk = IR050000.getDailyEndWork(conn, paramInfo); //일일마감체크
		if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{
      request.setAttribute("page.mn05.SucMsg", "일일마감이 완료된 회계일자는 잉여금이입수기분을 취소할 수 없습니다..");
		} else {

			for (int i=0; i<chk.length; i++) {
				int y = Integer.parseInt(chk[i]);
        //잉여금이입수기분 취소
				if (IR051410.deleteSrpSugi(conn, paramInfo, m240_seq[y]) <1 ) {
			    throw new ProcessException("E004");
		    }
        
				//세입수기분 취소
				if (IR051410.deleteSeipSugi(conn, paramInfo, m010_seq[y]) <1 ) {
			    throw new ProcessException("E004");
		    }
			}

		  request.setAttribute("page.mn05.SucMsg", "취소처리되었습니다.");
		}
	  
		/* 잉여금이입수기분 취소 조회 */
		List<CommonEntity> srpSugiCancelList = IR051410.getSrpSugiCancelList(conn, paramInfo);
		request.setAttribute("page.mn05.srpSugiCancelList", srpSugiCancelList);
  }
}