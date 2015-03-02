/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR051010DeleteCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금배정 > 잉여금이입요구취소
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
import com.etax.db.mn05.IR051010;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;

public class IR051010DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR051010DeleteCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("reg_date",    request.getParameter("reg_date"));
    
    logger.info("paramInfo : " + paramInfo);

		String[] chk = request.getParameterValues("allotChk");
		String[] seq = request.getParameterValues("seq_list");

		for (int i=0; i<chk.length; i++) {
			int y = Integer.parseInt(chk[i]);

			if (IR051010.deleteSurplus(conn, paramInfo, seq[y]) < 1 ) {
				throw new ProcessException("E004");
			}
		}
	  
		/* 잉여금이입 조회 */
		List<CommonEntity> srpCancelList = IR051010.getSrpCancelList(conn, paramInfo);
		request.setAttribute("page.mn05.srpCancelList", srpCancelList);

		request.setAttribute("page.mn05.SucMsg", "취소처리되었습니다.");
  }
}