/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR030510SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용   : 세입세출외현금 > 주행세 조회/수정/삭제
****************************************************************/
 
package com.etax.command.mn03;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.db.common.SelectBox;
import com.etax.framework.command.BaseCommand;
import com.etax.db.mn03.IR030510;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR030510SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR030510SelectCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();

    if (!"".equals(request.getParameter("start_date")) ) {
      paramInfo.setValue("start_date",       request.getParameter("start_date"));
    } else {
	    paramInfo.setValue("start_date",       TextHandler.getCurrentDate());
    }
		paramInfo.setValue("end_date",         request.getParameter("end_date"));
		paramInfo.setValue("napseja",          request.getParameter("napseja"));
    String fis_date = TextHandler.getBeforeDate(conn, TextHandler.getCurrentDate());
    request.setAttribute("page.mn03.fis_date",   fis_date);

    logger.info("paramInfo : " + paramInfo);
	  
    if (Integer.parseInt(paramInfo.getString("start_date")) > Integer.parseInt(TextHandler.getCurrentDate())) {  
      //등록일이 오늘을 지났는지 체크
		  request.setAttribute("page.mn06.SucMsg",   "등록일자가 금일이후입니다.");
    }
     
  	if (!"".equals(request.getParameter("start_date")) ) {
		  List<CommonEntity> juheangList = IR030510.getJuheangList(conn, paramInfo);
		  request.setAttribute("page.mn03.juheangList", juheangList);
    }
	}
}