/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	   : IR010510DeleteCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용	 : 세입 > 수기분 등록내역 삭제
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
import com.etax.db.mn01.IR010110;			
import com.etax.entity.CommonEntity;

public class IR010510DeleteCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR010510DeleteCMD.class);	// log4j 설정
	
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
	
		paramInfo.setValue("fis_year",         request.getParameter("fis_year"));
		paramInfo.setValue("m010_seq",         request.getParameter("m010_seq"));
    
		paramInfo.setValue("fis_date",         request.getParameter("fis_date"));     
	  paramInfo.setValue("intype",           request.getParameter("intype"));
		paramInfo.setValue("acc_type",         request.getParameter("acc_type"));	
	  paramInfo.setValue("part_code",        request.getParameter("part_code"));
		paramInfo.setValue("acc_code",         request.getParameter("acc_code"));
	  paramInfo.setValue("semok_code",       request.getParameter("semok_code"));
	  paramInfo.setValue("amt",              request.getParameter("amt"));
	
		String[] chk_list = request.getParameterValues("chk_yn");
		String[] year_list = request.getParameterValues("year_list");
		String[] seq_list = request.getParameterValues("seq_list");

		for (int i=0; i<chk_list.length; i++) {
		  int chk_val = Integer.parseInt(chk_list[i]);
      
			paramInfo.setValue("fis_year",       year_list[chk_val]);
			paramInfo.setValue("m010_seq",       seq_list[chk_val]);
 
		  logger.info("paramInfo : " + paramInfo);		

      CommonEntity endChk = IR010000.getDailyEndWork(conn, paramInfo); //일일마감체크
	
        if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{     
            request.setAttribute("page.mn01.delMsg", "일일마감이 완료된 회계일자는 삭제할 수 없습니다.");   
        
        } else {
          
            /* 수기분 삭제 */
            if(IR010510.expWriteDelete(conn, paramInfo) < 1 ) {			
              throw new ProcessException("E004"); //삭제중 오류메시지 표시
            }
            request.setAttribute("page.mn01.delMsg", "삭제처리되었습니다.");
        }
		}
    paramInfo.setValue("fis_year",         request.getParameter("fis_year"));
		                                                                             
    /* 수기분 리스트 */
		List<CommonEntity> expWriteList = IR010510.getExpWriteList(conn, paramInfo);
		request.setAttribute("page.mn01.expWriteList", expWriteList);

		List<CommonEntity> deptList = IR010510.getDeptList(conn, paramInfo);
		request.setAttribute("page.mn01.deptList", deptList);	

		List<CommonEntity> accCdList = IR010510.getAccCdList(conn, paramInfo);
    request.setAttribute("page.mn01.accCdList", accCdList); 	

		List<CommonEntity> semokList = IR010510.getSemokList(conn, paramInfo);
    request.setAttribute("page.mn01.semokList", semokList);

    CommonEntity fis_date  = IR010110.getFisDate(conn);
    request.setAttribute("page.mn01.fis_date", fis_date); 

	}
}