/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR051510DeleteCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금배정 > 자금배정승인내역조회/책임자승인처리(책임자승인 확인창에서 취소 눌렀을 때)
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
import com.etax.db.mn05.IR051510;
import com.etax.db.mn05.IR050000;
import com.etax.trans.TransHandler;
import com.etax.db.trans.TransDAO;
import com.etax.trans.data.GNBRev;
import com.etax.entity.CommonEntity;

public class IR051510DeleteCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR051510DeleteCMD.class); // log4j 설정

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 

        CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("imp_rec_cd",       "9999");
        paramInfo.setValue("m100_sendno",      request.getParameter("m100_sendno"));
		paramInfo.setValue("m100_allotgubun",  request.getParameter("m100_allotgubun"));
		paramInfo.setValue("m100_allotamt",    request.getParameter("m100_allotamt"));

        paramInfo.setValue("allot_date",         request.getParameter("allot_date"));
		paramInfo.setValue("err_code",           "OVRD");
		paramInfo.setValue("err_cont",           "책임자승인이필요합니다.");
		paramInfo.setValue("allot_type",         "1");  //자금 배정시
		

        if (IR050000.errAllot(conn, paramInfo) < 1 ) {
			//확인창에서 취소 누른 후 원상복귀
			throw new ProcessException("E004");
        }

		/* 자금배정승인내역 조회 */
        List<CommonEntity> allotList = IR051510.getAllotList(conn, paramInfo);
        request.setAttribute("page.mn05.allotList", allotList);
    }
}