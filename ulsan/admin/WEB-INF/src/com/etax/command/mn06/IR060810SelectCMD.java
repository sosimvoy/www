/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR060810SelectCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금운용 > 자금인출요구등록 - 조회
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn06.IR060810;
import com.etax.entity.CommonEntity;

public class IR060810SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR060810SelectCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    
    
    int cpage = 1;
		if( !"".equals(request.getParameter("cpage")) ) {
			cpage = Integer.parseInt(request.getParameter("cpage"));	
		}

		int fromRow = (cpage-1) * 10;
		int toRow = cpage * 10;

    CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("fis_year",   request.getParameter("fis_year"));
    paramInfo.setValue("fromRow",            fromRow + "");
    paramInfo.setValue("toRow",              toRow + "");

    logger.info("paramInfo : " + paramInfo);

		List<CommonEntity> bankOutList = IR060810.getBankOutList(conn, paramInfo);
		request.setAttribute("page.mn06.bankOutList", bankOutList);

    CommonEntity rowData = IR060810.getTotalCount(conn, paramInfo);
		request.setAttribute("page.mn06.totalCount", new Integer(rowData.getInt("CNT")));
  }
}