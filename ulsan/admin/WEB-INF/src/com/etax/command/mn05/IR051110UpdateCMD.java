/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR051110UpdateCMD.java
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
import com.etax.db.mn05.IR051110;
import com.etax.db.mn05.IR050000;
import com.etax.command.common.TransLogInsert;  //로그기록 남기기 위한 import
import com.etax.entity.CommonEntity;

public class IR051110UpdateCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR051110UpdateCMD.class); // log4j 설정

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

        HttpSession session = request.getSession(false);
        String user_id = (String)session.getAttribute("session.user_id");

        CommonEntity paramInfo = new CommonEntity();
        paramInfo.setValue("reg_date",    request.getParameter("reg_date"));
        paramInfo.setValue("acc_date",    request.getParameter("reg_date")); //마감조회용
        paramInfo.setValue("fis_year",   (request.getParameter("reg_date")).substring(0,4)); //마감조회용
        paramInfo.setValue("user_id",     user_id);
        paramInfo.setValue("work_log",    request.getParameter("work_log"));
        paramInfo.setValue("trans_gubun", request.getParameter("trans_gubun"));

		//로그기록 남기는 클래스및 메소드 호출
        TransLogInsert tli = new TransLogInsert();
		tli.execute(request, response, conn);
		paramInfo.setValue("log_no", tli.getLogNo());
    
        logger.info("paramInfo : " + paramInfo);

		String[] chk = request.getParameterValues("allotChk");
		String[] seq = request.getParameterValues("seq_list");

		for (int i=0; i<chk.length; i++) {
			int y = Integer.parseInt(chk[i]);

			IR051110.updateSrpPms(conn, paramInfo, seq[y]); //일계등록상태로 변경

            IR051110.insertSrpList1(conn, paramInfo, seq[y]);

			IR051110.insertSrpList2(conn, paramInfo, seq[y]);
		}
	  
		/* 잉여금이입 조회 */
		List<CommonEntity> srpPmsList = IR051110.getSrpCancelList(conn, paramInfo);
		request.setAttribute("page.mn05.srpPmsList", srpPmsList);

        CommonEntity magamInfo = IR050000.getDailyEndWork(conn, paramInfo);
        request.setAttribute("page.mn05.magamState",  (String)magamInfo.getString("M210_WORKENDSTATE"));

		request.setAttribute("page.mn05.SucMsg", "일계등록처리되었습니다.");
    }
}