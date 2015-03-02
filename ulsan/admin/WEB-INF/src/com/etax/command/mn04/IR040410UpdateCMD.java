/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	   : IR040410UpdateCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-08-12
* 프로그램내용	 : 세외수입 > 징수결의 수정
******************************************************/

package com.etax.command.mn04;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;
import com.oreilly.servlet.MultipartRequest;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn04.IR040410;
import com.etax.entity.CommonEntity;

public class IR040410UpdateCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR040410UpdateCMD.class);	// log4j 설정
	// Connection 이 필요한 경우는 BaseCommand 의 abstract execute() 를 구현해서 사용
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("baluiDate",             request.getParameter("baluiDate"));			 //정정사항 과목
		paramInfo.setValue("gojiseoPublish",        request.getParameter("gojiseoPublish"));			 //기재사항 영수일자
		paramInfo.setValue("napibDate",             request.getParameter("napibDate"));			 //정정사항 영수일자
		paramInfo.setValue("jingsubuWrite",         request.getParameter("jingsubuWrite"));		 //기재사항 영수장소
		paramInfo.setValue("gwan",                  request.getParameter("gwan"));
		paramInfo.setValue("hang",                  request.getParameter("hang"));
		paramInfo.setValue("mok",                   request.getParameter("mok"));
		paramInfo.setValue("semok",                 request.getParameter("semok"));
		paramInfo.setValue("bonAmt",                request.getParameter("bonAmt"));
    paramInfo.setValue("gasanAmt",              request.getParameter("gasanAmt"));
		paramInfo.setValue("interestAmt",           request.getParameter("interestAmt"));
		paramInfo.setValue("totalAmt",              request.getParameter("totalAmt"));
    paramInfo.setValue("orgAmt",                request.getParameter("orgAmt"));
		paramInfo.setValue("napbujaName",           request.getParameter("napbujaName"));
    paramInfo.setValue("juminNo",               request.getParameter("juminNo"));
		paramInfo.setValue("address",               request.getParameter("address"));
    paramInfo.setValue("businessName",          request.getParameter("businessName"));
	  paramInfo.setValue("userName",              request.getParameter("userName"));
		paramInfo.setValue("seq",                   request.getParameter("seq"));
    paramInfo.setValue("year",                  request.getParameter("year"));
		paramInfo.setValue("m80_seq",               request.getParameter("m80_seq"));
    paramInfo.setValue("acc_date",              request.getParameter("acc_date"));

    if (!"".equals(paramInfo.getString("year")) && (paramInfo.getString("year")).length() == 4) {  //회계년도가 널이 아닐때
      paramInfo.setValue("col_name",    "M080_MONTHAMT_"+Integer.parseInt((paramInfo.getString("acc_date")).substring(4,6))+"");
      if (!((paramInfo.getString("acc_date")).substring(0,4)).equals(paramInfo.getString("year"))) { //회계년도와 현재년도 다를 때
        if ("01".equals((paramInfo.getString("acc_date")).substring(4,6)) ) {
          paramInfo.setValue("col_name",    "M080_MONTHAMT_13");
        } else if ("02".equals((paramInfo.getString("acc_date")).substring(4,6)) ) {
          paramInfo.setValue("col_name",    "M080_MONTHAMT_14");
        }
      }
    }

    long hidden_amt = 0L;
    hidden_amt = paramInfo.getLong("totalAmt") - paramInfo.getLong("orgAmt");
    paramInfo.setValue("hidden_amt",        hidden_amt+"");
		
    logger.info("paramInfo : " +paramInfo);
      
		/* 수기분 수정 */
		if(IR040410.jingsuUpdate(conn, paramInfo) < 1 ) {
		  throw new ProcessException("E003"); //수정중 오류메시지 표시
		}

    if (!"0".equals(paramInfo.getString("hidden_amt")) && !"".equals(paramInfo.getString("m80_seq")) ) {
      if (IR040410.updateRevAmt(conn, paramInfo)<1) {
        throw new ProcessException("E003");
      }
    }

		/* 수기분 상세  */
		CommonEntity jingsuView = IR040410.getJingsuView(conn, paramInfo);
		request.setAttribute("page.mn04.jingsuView", jingsuView);

		request.setAttribute("page.mn04.SucMsg", "수정되었습니다.");
	}
}