/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR070210SelectCMD.java
* 프로그램작성자    : (주)미르이즈
* 프로그램작성일    :  2010-08-27
* 프로그램내용      : 일계/보고서 > 일일업무마감
***********************************************************************/
package com.etax.command.mn07;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import java.io.*;

import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn07.IR070210;
import com.etax.entity.CommonEntity;
import com.etax.util.StringUtil;
import com.etax.trans.GNBDaemonCall;

public class IR070210SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR070210SelectCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{
/*    
    try{
	   if (GNBDaemonCall.setDaemonShellExecuteCommand("SendJanak","20100907","IPGM","0000") == false){
		   System.out.println("실패다.........");
	   }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception ex) {
      ex.printStackTrace();	
	}
*/
     
      CommonEntity paramInfo = new CommonEntity();
      paramInfo.setValue("acc_year",    request.getParameter("acc_year"));

	 /* 일일업무마감 현황 조회 */
    List<CommonEntity> magamList = IR070210.getMagamList(conn, paramInfo);
    request.setAttribute("page.mn07.magamList", magamList);

    CommonEntity dateData = IR070210.getDateData(conn, paramInfo);
        request.setAttribute("page.mn07.dateData", dateData);

  }
}
