/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR071110DeleteCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-08-21
* 프로그램내용   : 시스템운영 > 예산과목 삭제
****************************************************************/

package com.etax.command.mn07;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn07.IR071110;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;
import com.etax.db.common.SelectBox;
import com.etax.util.TextHandler;

public class IR071110DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR071110DeleteCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    
		CommonEntity paramInfo = new CommonEntity();

		String[] chk_list        = request.getParameterValues("userChk");
		String[] del_year        = request.getParameterValues("del_year");
		String[] del_date        = request.getParameterValues("del_date");
		String[] del_prtdate     = request.getParameterValues("del_prtdate");
		String[] del_taxgbn      = request.getParameterValues("del_taxgbn");
		String[] del_proctype    = request.getParameterValues("del_proctype");
		String[] del_partcode    = request.getParameterValues("del_partcode");
		String[] del_semokcode   = request.getParameterValues("del_semokcode");
		String[] del_seq         = request.getParameterValues("del_seq");
		String[] del_sunabamt    = request.getParameterValues("del_sunabamt");
		String[] del_sunabcnt    = request.getParameterValues("del_sunabcnt");
		String[] del_hwanbuamt   = request.getParameterValues("del_hwanbuamt");
		String[] del_hwanbucnt   = request.getParameterValues("del_hwanbucnt");

		for (int i=0; i<chk_list.length; i++) {
  		int chk_val = Integer.parseInt(chk_list[i]);
			
	  	paramInfo.setValue("del_year"     ,  del_year[chk_val]);
	  	paramInfo.setValue("del_date"     ,  del_date[chk_val]);
	  	paramInfo.setValue("del_prtdate"  ,  del_prtdate[chk_val]);
	  	paramInfo.setValue("del_taxgbn"   ,  del_taxgbn[chk_val]);
	  	paramInfo.setValue("del_proctype" ,  del_proctype[chk_val]);
	  	paramInfo.setValue("del_semokcode",  del_semokcode[chk_val]);
	  	paramInfo.setValue("del_partcode" ,  del_partcode[chk_val]);
	  	paramInfo.setValue("del_seq"      ,  del_seq[chk_val]);
	  	paramInfo.setValue("del_sunabamt" ,  del_sunabamt[chk_val]);
	  	paramInfo.setValue("del_sunabcnt" ,  del_sunabcnt[chk_val]);
	  	paramInfo.setValue("del_hwanbuamt" ,  del_hwanbuamt[chk_val]);
	  	paramInfo.setValue("del_hwanbucnt" ,  del_hwanbucnt[chk_val]);

      if("1".equals(del_proctype[chk_val])) {
        IR071110.BudgetdayDeleteCMD(conn, paramInfo);
      }else {
        IR071110.BudgetdayCancelCMD(conn, paramInfo);
        IR071110.BudgethstDeleteCMD(conn, paramInfo);
        if ("0".equals(del_seq[chk_val])) {
            IR071110.BudgetMasterDelete(conn, paramInfo); //시퀀스가 0이면 통으로 삭제
        } else {
            IR071110.BudgetdiscardCMD(conn, paramInfo);
        }
      }
   }

   request.setAttribute("page.mn07.SucMsg", "삭제되었습니다.");

    paramInfo.setValue("queyear", request.getParameter("queyear"));
    paramInfo.setValue("que_date", request.getParameter("que_date"));
    paramInfo.setValue("quetaxgbn", request.getParameter("quetaxgbn"));
    paramInfo.setValue("queprocgbn", request.getParameter("queprocgbn"));
    paramInfo.setValue("taxgbn"  , request.getParameter("taxgbn"));
    paramInfo.setValue("mokgubun", request.getParameter("mokgubun"));

		/* 수입일계표 조회 */		
		List<CommonEntity> budgetList = IR071110.budgetList(conn, paramInfo);
		request.setAttribute("page.mn07.budgetList", budgetList);

		/* 예산과목 조회 */		
		List<CommonEntity> budgetsemokList = IR071110.getbudgetsemokList(conn, paramInfo);
		request.setAttribute("page.mn07.budgetsemokList", budgetsemokList);

		/* 예산부서 조회 */		
		List<CommonEntity> budgetpartList = IR071110.getbudgetpartList(conn, paramInfo);
		request.setAttribute("page.mn07.budgetpartList", budgetpartList);

    String ago_acc_date = TextHandler.getAgoBusinessDate(conn, TextHandler.addDays(TextHandler.getCurrentDate(),-1));  //오늘날짜 기준 전영업일
    request.setAttribute("page.mn07.ago_acc_date", ago_acc_date);

  }
}
