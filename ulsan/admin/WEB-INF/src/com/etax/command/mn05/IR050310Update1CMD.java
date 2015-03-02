/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR050310Update1CMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금배정 > 자금배정승인내역조회/배정처리 - 재배정취소
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn05.IR050310;
import com.etax.entity.CommonEntity;

public class IR050310Update1CMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR050310Update1CMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 

    CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("allot_date",         request.getParameter("allot_date"));
    paramInfo.setValue("fis_year",           request.getParameter("fis_year"));
    
    logger.info("paramInfo : " + paramInfo);

		String[] allotChk  = request.getParameterValues("allotChk");
		String[] year_list = request.getParameterValues("year_list");
		String[] seq_list  = request.getParameterValues("seq_list");
		String[] allot_yn  = request.getParameterValues("allot_yn");

		for (int i=0; i<allotChk.length; i++)	{
			int y = Integer.parseInt(allotChk[i]);
			if ("Y".equals(allot_yn[y])) {
				if (IR050310.reAllotDelete(conn, seq_list[y], paramInfo) < 1)	{
			    throw new ProcessException("E003"); //수정중 오류메시지
		    }
			}
		}

		/* 자금재배정승인내역 조회 */
    List<CommonEntity> reAllotList = IR050310.getReAllotList(conn, paramInfo);
    request.setAttribute("page.mn05.reAllotList", reAllotList);
		
		request.setAttribute("page.mn05.SucMsg", "재배정취소처리되었습니다.");
  }
}