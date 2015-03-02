/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR090910DeleteCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-08-21
* 프로그램내용   : 시스템운영 > 세목코드 사용 삭제
****************************************************************/

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

public class IR090810DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR090810DeleteCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    
		CommonEntity paramInfo = new CommonEntity();
            paramInfo.setValue("under_year",      request.getParameter("under_year")); 
			paramInfo.setValue("accGubun", request.getParameter("accGubun"));
			paramInfo.setValue("accGubun1", request.getParameter("accGubun1"));

		String[] chk_list        = request.getParameterValues("userChk");
		String[] year_list       = request.getParameterValues("year_list");
		String[] accGubun_list   = request.getParameterValues("accGubun_list");
		String[] accCode_list    = request.getParameterValues("accCode_list");
   	    String[] workGubun_list  = request.getParameterValues("workGubun_list");
		String[] semokCode_list  = request.getParameterValues("semokCode_list");
		String[] mokGubun_list   = request.getParameterValues("mokGubun_list");

		for (int i=0; i<chk_list.length; i++) {
		int chk_val = Integer.parseInt(chk_list[i]);
			
		paramInfo.setValue("year",          year_list[chk_val]);
		paramInfo.setValue("accGubun",      accGubun_list[chk_val]);
		paramInfo.setValue("accCode",       accCode_list[chk_val]);
		paramInfo.setValue("workGubun",     workGubun_list[chk_val]);
		paramInfo.setValue("semokCode",     semokCode_list[chk_val]);
		paramInfo.setValue("mokGubun",      mokGubun_list[chk_val]);

			if (IR090810.deleteSemokCode(conn, paramInfo) < 1) {
		    throw new ProcessException("E004"); //삭제중 오류메시지 표시
		  }
    }
		request.setAttribute("page.mn09.SucMsg", "삭제되었습니다.");

			/* 사용자세목코드 조회 */
		List<CommonEntity> accCdList = SelectBox.getAccCdList3(conn, paramInfo);
    request.setAttribute("page.mn09.accCdList", accCdList); 
		
		List<CommonEntity> accCdList1 = SelectBox.getAccCdList4(conn, paramInfo);
    request.setAttribute("page.mn09.accCdList1", accCdList1); 

			/* 세목코드 조회 */
		List semokCodeList = IR090810.getbudgetSemokList(conn, paramInfo);
		request.setAttribute("page.mn09.semokCodeList", semokCodeList);
  }
}
