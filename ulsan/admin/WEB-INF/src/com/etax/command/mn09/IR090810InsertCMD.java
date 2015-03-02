/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	   : IR090810InsertCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-08-21
* 프로그램내용	 : 시스템운영 > 세목코드 등록
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR090810;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;
import com.etax.db.common.SelectBox;

public class IR090810InsertCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR090810InsertCMD.class);	// log4j 설정
	// Connection 이 필요한 경우는 BaseCommand 의 abstract execute() 를 구현해서 사용
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    CommonEntity paramInfo = new CommonEntity();
            paramInfo.setValue("under_year",      request.getParameter("under_year")); 
			paramInfo.setValue("year",              request.getParameter("year"));
			paramInfo.setValue("accGubun",          request.getParameter("accGubun"));
			paramInfo.setValue("acc_code",          request.getParameter("acc_code"));
			paramInfo.setValue("workGubun",         request.getParameter("workGubun"));
	    
			String[] semokCode = request.getParameterValues("semokCode");
			String[] semokName = request.getParameterValues("semokName");
			String[] segumGubun = request.getParameterValues("segumGubun");
			String[] mokGubun = request.getParameterValues("mokGubun");
      
    	for (int i=0; i < mokGubun.length; i++)	{
  
				if (!"".equals(semokCode[i])) {
					paramInfo.setValue("semokCode",        semokCode[i]);
			    paramInfo.setValue("semokName",        semokName[i]);
			    paramInfo.setValue("segumGubun",       segumGubun[i]);
			    paramInfo.setValue("mokGubun",         mokGubun[i]);
					logger.info("paramInfo : " + paramInfo);

					/* 회계코드 등록 */
				  if(IR090810.insertSemokCode(conn, paramInfo) < 1 ) {
				  	throw new ProcessException("E003"); //수정중 오류메시지 표시
			  	}
				}
		
				request.setAttribute("page.mn09.SucMsg", "등록되었습니다.");
	    }
				/* 사용자세목코드 조회 */
				List<CommonEntity> accCdList = SelectBox.getAccCdList3(conn, paramInfo);
				request.setAttribute("page.mn09.accCdList", accCdList); 
				
				List<CommonEntity> accCdList1 = SelectBox.getAccCdList4(conn, paramInfo);
				request.setAttribute("page.mn09.accCdList1", accCdList1); 
			}
   	}

            
