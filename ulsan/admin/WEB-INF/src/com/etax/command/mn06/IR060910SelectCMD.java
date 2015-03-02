/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR060910SelectCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금운용 > 자금인출요구조회/취소
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn06.IR060910;
import com.etax.entity.CommonEntity;

public class IR060910SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR060910SelectCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{

    CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("acc_date",   request.getParameter("acc_date"));

    logger.info("paramInfo : " + paramInfo);

		List<CommonEntity> inchulReqList = IR060910.getInchulReqList(conn, paramInfo);
		request.setAttribute("page.mn06.inchulReqList", inchulReqList);
  }
}