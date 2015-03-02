/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR051510SelectCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금배정 > 자금배정미처리잔액조회
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn05.IR051510;
import com.etax.db.trans.TransDAO;
import com.etax.command.common.TransferNo;
import com.etax.trans.TransHandler;
import com.etax.trans.data.GNBRev;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR051510SelectCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR051510SelectCMD.class); // log4j 설정

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{

        HttpSession session = request.getSession(false);
	    String user_id = (String)session.getAttribute("session.user_id");
	    String errMsg = "";  //에러메시지
	    String result = "";  //수신전문 String

		CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("imp_rec_cd",   "9999");
		paramInfo.setValue("allot_date",   request.getParameter("allot_date"));

		/* 통신장애 재배정 목록조회 */
        List<CommonEntity>  allotList = IR051510.getAllotList(conn, paramInfo);
        request.setAttribute("page.mn05.allotList", allotList);
    }
}