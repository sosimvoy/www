/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR060610SelectCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금운용 > 자금예탁(기금,특별회계)
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn06.IR060610;
import com.etax.entity.CommonEntity;


public class IR060610SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR060610SelectCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("acct_kind",       request.getParameter("acct_kind"));
		paramInfo.setValue("fis_year",        request.getParameter("fis_year"));
    paramInfo.setValue("part_code",       request.getParameter("part_code"));
    
    logger.info("paramInfo : " + paramInfo);
    
		List<CommonEntity> acctList = new ArrayList<CommonEntity>();
    List<CommonEntity> partList = new ArrayList<CommonEntity>();
	  /* 회계종류 조회 */
		if ("".equals(request.getParameter("fis_year")) || request.getParameter("fis_year") == null) {
      partList = IR060610.getPartList(conn);
			acctList = IR060610.getAcctList(conn);
      
		} else {
      partList = IR060610.getPartList(conn, paramInfo);
			acctList = IR060610.getAcctList(conn, paramInfo);
      
		}
    
		request.setAttribute("page.mn06.acctList", acctList);
    request.setAttribute("page.mn06.partList", partList);
  }
}