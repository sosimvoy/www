/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	   : IR010510UpdateCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용	 : 세입 > 등록내역 수정
******************************************************/

package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn01.IR010510;
import com.etax.db.mn01.IR010000;
import com.etax.entity.CommonEntity;

public class IR010510UpdateCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR010510UpdateCMD.class);	// log4j 설정
	// Connection 이 필요한 경우는 BaseCommand 의 abstract execute() 를 구현해서 사용
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    CommonEntity paramInfo = new CommonEntity();  
    paramInfo.setValue("fis_year",         request.getParameter("fis_year"));
    paramInfo.setValue("fis_date",         request.getParameter("fis_date"));
    paramInfo.setValue("m010_seq",         request.getParameter("m010_seq"));

    CommonEntity endChk = IR010000.getDailyEndWork(conn, paramInfo); //일일마감체크
    
      if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{  
        System.out.println("마감여부:::"+endChk.getString("M210_WORKENDSTATE"));
        paramInfo.setValue("cnt",              request.getParameter("cnt")); 
      
        if(IR010510.expWriteUpdate1(conn, paramInfo) < 1 ) {
          throw new ProcessException("E003"); //수정중 오류메시지 표시
        } 
        request.setAttribute("page.mn01.SucMsg", "수정처리되었습니다.");   
       
      } else {
        paramInfo.setValue("intype",           request.getParameter("intype"));
        paramInfo.setValue("year_type",        request.getParameter("year_type"));
        paramInfo.setValue("gigwan",           request.getParameter("gigwan"));
        paramInfo.setValue("acc_type",         request.getParameter("acc_type"));	
        paramInfo.setValue("part_code",        request.getParameter("part_code"));
        paramInfo.setValue("acc_code",         request.getParameter("acc_code"));
        paramInfo.setValue("semok_code",       request.getParameter("semok_code"));
        paramInfo.setValue("amt",              request.getParameter("amt"));
        paramInfo.setValue("cnt",              request.getParameter("cnt")); 
      
        if(IR010510.expWriteUpdate(conn, paramInfo) < 1 ) {
          throw new ProcessException("E003"); //수정중 오류메시지 표시
        }
        request.setAttribute("page.mn01.SucMsg", "수정처리되었습니다.");   
      
      }

      logger.info("paramInfo : " + paramInfo);		
  
		/* 수기분 상세  */
		CommonEntity expWriteView = IR010510.getExpWriteView(conn, paramInfo);
		request.setAttribute("page.mn01.expWriteView", expWriteView);

    List<CommonEntity> deptList = IR010510.getDeptList(conn, paramInfo);
		request.setAttribute("page.mn01.deptList", deptList);	

		List<CommonEntity> accCdList = IR010510.getAccCdList(conn, paramInfo);
    request.setAttribute("page.mn01.accCdList", accCdList); 	

		List<CommonEntity> semokList = IR010510.getSemokList(conn, paramInfo);
    request.setAttribute("page.mn01.semokList", semokList);

    List<CommonEntity> gigwanList = IR010510.getGigwanList(conn);
    request.setAttribute("page.mn01.gigwanList", gigwanList);

    request.setAttribute("page.mn01.endChk", endChk);
	}
}
