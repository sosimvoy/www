/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	   : IR030710BatIntCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용	 : 세출 > 등록내역 수정
******************************************************/

package com.etax.command.mn03;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn03.IR030710;
import com.etax.db.mn03.IR030000;
import com.etax.entity.CommonEntity;
import com.etax.command.common.TransLogInsert; 

public class IR030710BatIntCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR030710BatIntCMD.class);	// log4j 설정
	// Connection 이 필요한 경우는 BaseCommand 의 abstract execute() 를 구현해서 사용
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

        CommonEntity paramInfo = new CommonEntity();
    
        paramInfo.setValue("fis_year",         request.getParameter("fis_year"));
        paramInfo.setValue("fis_date",         request.getParameter("fis_date"));

        CommonEntity endChk = IR030000.getDailyEndWork(conn, paramInfo); //일일마감체크
		
        if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{
            request.setAttribute("page.mn03.SucMsg", "일일마감이 완료된 회계일자는 등록할 수 없습니다.");
        } else {

            //로그기록 남기는 클래스및 메소드 호출
            TransLogInsert tli = new TransLogInsert();
            tli.execute(request, response, conn);
            paramInfo.setValue("log_no", tli.getLogNo());

            int insertcnt = IR030710.batchcashInsert(conn, paramInfo);

            request.setAttribute("page.mn03.SucMsg", insertcnt + "건 등록처리되었습니다.");
        }
	}
}