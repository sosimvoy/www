/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR090910DeleteCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-08-21
* 프로그램내용   : 시스템운영 > 세목코드 사용 삭제
****************************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR090910;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;
import com.etax.db.common.SelectBox;

public class IR090910DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR090910DeleteCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    
		CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("year1", request.getParameter("year1"));
    paramInfo.setValue("accGubun1", request.getParameter("accGubun1"));
    
		String[] chk_list        = request.getParameterValues("userChk");
		String[] year_list       = request.getParameterValues("year_list");
    String[] partCode_list   = request.getParameterValues("partCode_list");
		String[] accGubun_list   = request.getParameterValues("accGubun_list");
		String[] accCode_list    = request.getParameterValues("accCode_list");
    String[] semokCode_list  = request.getParameterValues("semokCode_list");
		String[] workGubun_list  = request.getParameterValues("workGubun_list");

		for (int i=0; i<chk_list.length; i++) {
		int chk_val = Integer.parseInt(chk_list[i]);
			
		paramInfo.setValue("year",          year_list[chk_val]);
		paramInfo.setValue("partCode",      partCode_list[chk_val]);
		paramInfo.setValue("accGubun",      accGubun_list[chk_val]);
		paramInfo.setValue("accCode",       accCode_list[chk_val]);
		paramInfo.setValue("semokCode",     semokCode_list[chk_val]);
		paramInfo.setValue("workGubun",     workGubun_list[chk_val]);
        
        logger.info("paramInfo : " + paramInfo);
			if (IR090910.deleteUserCode(conn, paramInfo) < 1) {
		    throw new ProcessException("E004"); //삭제중 오류메시지 표시
		  }
    }

    request.setAttribute("page.mn09.SucMsg", "삭제되었습니다.");

	  /* 사용자세목코드 조회 */
		List <CommonEntity>userCodeList = IR090910.getUserCodeList(conn, paramInfo);
		request.setAttribute("page.mn09.userCodeList", userCodeList);

		List<CommonEntity> deptList = IR090910.getAccGubunDeptList09(conn, paramInfo);
		request.setAttribute("page.mn09.deptList", deptList);	
			
		List<CommonEntity> accCdList = IR090910.getAccCdList092(conn, paramInfo);
    request.setAttribute("page.mn09.accCdList", accCdList); 
		
		List<CommonEntity> semokList = IR090910.getSemokList091(conn, paramInfo);
    request.setAttribute("page.mn09.semokList", semokList);
	  
		List<CommonEntity> accCdList1 = IR090910.getAccCdList091(conn, paramInfo);
    request.setAttribute("page.mn09.accCdList1", accCdList1); 

		List<CommonEntity> userAccDeptList1 = IR090910.getAccGubunDeptList091(conn, paramInfo);
		request.setAttribute("page.mn09.userAccDeptList1", userAccDeptList1);	
		
  }
}
