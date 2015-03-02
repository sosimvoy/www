/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	   : IR020210UpdateCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용	 : 세출 > 등록내역 수정
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
import com.etax.db.mn02.IR020110;
import com.etax.db.mn01.IR010110;
import com.etax.entity.CommonEntity;

public class IR020210UpdateCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR020210UpdateCMD.class);	// log4j 설정
	// Connection 이 필요한 경우는 BaseCommand 의 abstract execute() 를 구현해서 사용
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    CommonEntity paramInfo = new CommonEntity();
    
    paramInfo.setValue("fis_year",         request.getParameter("fis_year"));
    paramInfo.setValue("fis_date",         request.getParameter("fis_date"));
    paramInfo.setValue("m030_seq",         request.getParameter("m030_seq"));
    paramInfo.setValue("acc_type",         request.getParameter("acc_type"));
    paramInfo.setValue("part_code",        request.getParameter("part_code"));
    paramInfo.setValue("acc_code",         request.getParameter("acc_code"));
    paramInfo.setValue("order_no",         request.getParameter("order_no"));
    paramInfo.setValue("org_no",           request.getParameter("org_no"));

    CommonEntity endChk = IR020000.getDailyEndWork(conn, paramInfo); //일일마감체크

    if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{   
        
      CommonEntity noData = IR020110.getOrderNo(conn, paramInfo);   //지급번호 중복 여부
      
      if(noData.getLong("NO_CNT") != 0 && !paramInfo.getString("order_no").equals(paramInfo.getString("org_no"))) { 
        request.setAttribute("page.mn02.FailMsg", "지급번호가 중복됩니다. 다시 등록하세요.");	
      
      } else {
 
        paramInfo.setValue("order_name",           request.getParameter("order_name"));
        paramInfo.setValue("order_no",             request.getParameter("order_no"));

        if(IR020210.revWriteUpdate1(conn, paramInfo) < 1 ) {
          throw new ProcessException("E003"); //수정중 오류메시지 표시
        }
        request.setAttribute("page.mn02.SucMsg", "수정처리되었습니다.");
      }
                                  
    } else {
          
      CommonEntity noData = IR020110.getOrderNo(conn, paramInfo);   //지급번호 중복 여부
          
      if(noData.getLong("NO_CNT") != 0 && !paramInfo.getString("order_no").equals(paramInfo.getString("org_no"))) { 
        request.setAttribute("page.mn02.FailMsg", "지급번호가 중복됩니다. 다시 등록하세요.");	
      
      } else {

        paramInfo.setValue("acc_type",             request.getParameter("acc_type"));
        paramInfo.setValue("part_code",            request.getParameter("part_code"));
        paramInfo.setValue("acc_code",             request.getParameter("acc_code"));	
        paramInfo.setValue("semok_code",           request.getParameter("semok_code"));
        paramInfo.setValue("intype",               request.getParameter("intype"));	
        paramInfo.setValue("order_name",           request.getParameter("order_name"));
        paramInfo.setValue("order_no",             request.getParameter("order_no"));
        paramInfo.setValue("amt",                  request.getParameter("amt"));

        logger.info("paramInfo : " + paramInfo);		

        if(IR020210.revWriteUpdate(conn, paramInfo) < 1 ) {
          throw new ProcessException("E003"); //수정중 오류메시지 표시
        }
        request.setAttribute("page.mn02.SucMsg", "수정처리되었습니다.");
      }
    }
			
		/* 리스트 조회 */
    CommonEntity fis_date  = IR010110.getFisDate(conn);
    request.setAttribute("page.mn01.fis_date", fis_date); 

		CommonEntity revWriteView = IR020210.getRevWriteView(conn, paramInfo);
		request.setAttribute("page.mn02.revWriteView", revWriteView); 

		List<CommonEntity> revDeptList = IR020210.getRevDeptList(conn, paramInfo);
    request.setAttribute("page.mn02.revDeptList", revDeptList); 

		List<CommonEntity> revAccNmList = IR020210.getRevAccNmList(conn, paramInfo);
    request.setAttribute("page.mn02.revAccNmList", revAccNmList); 

		List<CommonEntity> revSemokList = IR020210.getRevSemokList(conn, paramInfo);
    request.setAttribute("page.mn02.revSemokList", revSemokList);
    
    request.setAttribute("page.mn02.endChk", endChk);
	}
}
