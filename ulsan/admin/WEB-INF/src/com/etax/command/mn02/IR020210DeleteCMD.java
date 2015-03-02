/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	   : IR020210DeleteCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용	 : 세출 > 등록내역 삭제
******************************************************/

package com.etax.command.mn02;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn02.IR020210;
import com.etax.db.mn02.IR020000;
import com.etax.db.mn01.IR010110;
import com.etax.entity.CommonEntity;

public class IR020210DeleteCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR020210DeleteCMD.class);	// log4j 설정
	
	// Connection 이 필요한 경우는 BaseCommand 의 abstract execute() 를 구현해서 사용
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
		
    paramInfo.setValue("fis_year",         request.getParameter("fis_year"));
		paramInfo.setValue("m030_seq",         request.getParameter("m030_seq"));
    
		paramInfo.setValue("fis_date",         request.getParameter("fis_date"));
		paramInfo.setValue("acc_type",         request.getParameter("acc_type"));
		paramInfo.setValue("part_code",        request.getParameter("part_code"));
		paramInfo.setValue("acc_code",         request.getParameter("acc_code"));
		paramInfo.setValue("intype",           request.getParameter("intype"));

    logger.info("paramInfo : " + paramInfo);		

		String[] chk_list = request.getParameterValues("chk_yn");
		String[] year_list = request.getParameterValues("year_list");
		String[] seq_list = request.getParameterValues("seq_list");

		for (int i=0; i<chk_list.length; i++) {
		  int chk_val = Integer.parseInt(chk_list[i]);
      
			paramInfo.setValue("fis_year",       year_list[chk_val]);
			paramInfo.setValue("m030_seq",       seq_list[chk_val]);
 
		  logger.info(paramInfo);

      CommonEntity endChk = IR020000.getDailyEndWork(conn, paramInfo); //일일마감체크
	
        if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{     
          request.setAttribute("page.mn02.delMsg", "일일마감이 완료된 회계일자는 삭제할 수 없습니다.");   
        
        } else {

            /* 수기분 삭제 */
            if(IR020210.revWriteDelete(conn, paramInfo) < 1 ) {			
              throw new ProcessException("E004"); //삭제중 오류메시지 표시
            }
            request.setAttribute("page.mn02.delMsg", "삭제처리되었습니다.");
        }
		}
	  paramInfo.setValue("fis_year",         request.getParameter("fis_year"));    // paramInfo.setValue("fis_year",       year_list[chk_val]);	에서 받아오므로
		                                                                             // 마지막에 조회파라미터에서 이걸 뿌려준다.
    /* 리스트 조회 */
    CommonEntity fis_date  = IR010110.getFisDate(conn);
    request.setAttribute("page.mn01.fis_date", fis_date); 

		List<CommonEntity> revWriteList = IR020210.getRevWriteList(conn, paramInfo);
		request.setAttribute("page.mn02.revWriteList", revWriteList);

		List<CommonEntity> revDeptList = IR020210.getRevDeptList(conn, paramInfo);
    request.setAttribute("page.mn02.revDeptList", revDeptList); 

		List<CommonEntity> revAccNmList = IR020210.getRevAccNmList(conn, paramInfo);
    request.setAttribute("page.mn02.revAccNmList", revAccNmList); 

		List<CommonEntity> revSemokList = IR020210.getRevSemokList(conn, paramInfo);
    request.setAttribute("page.mn02.revSemokList", revSemokList); 
	}
}