/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : IR020410InsertCMD.java
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-08-12
* 프로그램내용	   : 세외수입 > 징수결의 등록
****************************************************************/

package com.etax.command.mn04;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;
import com.oreilly.servlet.MultipartRequest;

import com.etax.command.common.TransLogInsert;
import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn04.IR040310;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;


public class IR040310InsertCMD extends BaseCommand {
    private static Logger logger = Logger.getLogger(IR040310InsertCMD.class);
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {
   
        String procyn = "Y";

        CommonEntity paramInfo = new CommonEntity();

        paramInfo.setValue("seq",                   request.getParameter("seq"));	
		paramInfo.setValue("year",                  request.getParameter("year"));  	
		paramInfo.setValue("baluiDate",             request.getParameter("baluiDate"));			 //정정사항 과목
		paramInfo.setValue("gojiseoPublish",        request.getParameter("gojiseoPublish"));			 //기재사항 영수일자
		paramInfo.setValue("napibDate",             request.getParameter("napibDate"));			 //정정사항 영수일자
		paramInfo.setValue("jingsubuWrite",         request.getParameter("jingsubuWrite"));		 //기재사항 영수장소
		paramInfo.setValue("gwan",                  request.getParameter("gwan"));
		paramInfo.setValue("hang",                  request.getParameter("hang"));
		paramInfo.setValue("mok",                   request.getParameter("mok"));
		paramInfo.setValue("semok",                 request.getParameter("semok"));
		paramInfo.setValue("bonAmt",                TextHandler.replace(request.getParameter("bonAmt"),",",""));
        paramInfo.setValue("gasanAmt",              TextHandler.replace(request.getParameter("gasanAmt"),",",""));
		paramInfo.setValue("interestAmt",           TextHandler.replace(request.getParameter("interestAmt"),",",""));
        paramInfo.setValue("totalAmt",              TextHandler.replace(request.getParameter("totalAmt"),",",""));
		paramInfo.setValue("napbujaName",           request.getParameter("napbujaName"));
        paramInfo.setValue("juminNo",               request.getParameter("juminNo"));
		paramInfo.setValue("address",               request.getParameter("address"));
        paramInfo.setValue("businessName",          request.getParameter("businessName"));
	    paramInfo.setValue("userName",              request.getParameter("userName"));
        paramInfo.setValue("fis_year",              request.getParameter("fis_year"));
        if ("".equals(paramInfo.getString("fis_year")) ) {
            paramInfo.setValue("fis_year",    TextHandler.formatDate(TextHandler.getCurrentDate(), "yyyyMMdd", "yyyy"));
        } 

	    CommonEntity taskID = IR040310.getTaskId(conn, paramInfo); //권한체크
      
        if ("ED03".equals(taskID.getString("M200_DOCUMENTCODE")))	{
            paramInfo.setValue("doc_code",    "ED03");
			paramInfo.setValue("kyuluiType",    "J1");
            procyn = "Y";
        } else if ("ED12".equals(taskID.getString("M200_DOCUMENTCODE")))	{ 
            paramInfo.setValue("doc_code",    "ED12");
			paramInfo.setValue("kyuluiType",    "J3");
            procyn = "Y";
		} else if ("ED15".equals(taskID.getString("M200_DOCUMENTCODE")))	{ 
            paramInfo.setValue("doc_code",    "ED15");
			paramInfo.setValue("kyuluiType",    "J4");
            procyn = "Y";
        } else {
            procyn = "N";
            request.setAttribute("page.mn04.SucMsg", "징수결의서등록 권한이 없습니다.");
        }

        if ("Y".equals(procyn)) {
            if (!"".equals(paramInfo.getString("year")) && (paramInfo.getString("year")).length() == 4) {  //080회계년도가 널이 아닐때
                paramInfo.setValue("col_name",    "M080_MONTHAMT_"+Integer.parseInt((TextHandler.getCurrentDate()).substring(4,6))+"");
                if (!((TextHandler.getCurrentDate()).substring(0,4)).equals(paramInfo.getString("year"))) { //회계년도와 현재년도 다를 때
                    if ("01".equals((TextHandler.getCurrentDate()).substring(4,6)) ) {
                        paramInfo.setValue("col_name",    "M080_MONTHAMT_13");
                    } else if ("02".equals((TextHandler.getCurrentDate()).substring(4,6)) ) {
                        paramInfo.setValue("col_name",    "M080_MONTHAMT_14");
                    }
                }
            }

            if (!((TextHandler.getCurrentDate()).substring(0,4)).equals(paramInfo.getString("year")) 
                && !"".equals(paramInfo.getString("year"))) { //회계년도와 현재년도 다를 때)
                if (Integer.parseInt((TextHandler.getCurrentDate()).substring(0,4)) > 2) {
                    request.setAttribute("page.mn04.SucMsg", "회계년도가 이월되어 등록이 불가능합니다.");
                }
            } else {
	 
	            //로그기록 남기는 클래스및 메소드 호출
                TransLogInsert tli = new TransLogInsert();
  	            tli.execute(request, response, conn);
	            paramInfo.setValue("log_no", tli.getLogNo());
                logger.info("paramInfo : " + paramInfo);

                /* 수기분 등록 */
  	            if(IR040310.jingsuInsert(conn, paramInfo) < 1 ) {
	  	            throw new ProcessException("E002"); //등록중 오류메시지 표시
	            }

                if (!"".equals(paramInfo.getString("seq")) ) {  //예산서 금액 수정
                    if (IR040310.updateYesanAmt(conn, paramInfo) < 1)  {
                        throw new ProcessException("E003");
                    }
                }
                request.setAttribute("page.mn04.SucMsg", "등록되었습니다.");
            }
        }//징수결의서 등록 권한체크	

        paramInfo.setValue("napbujaName",  request.getParameter("napbujaName"));	
		paramInfo.setValue("semokcode",    request.getParameter("mok"));

		/* 징수결의 상세  */
		CommonEntity budgetView = IR040310.getBudgetView(conn, paramInfo);
		request.setAttribute("page.mn04.budgetView", budgetView);

        /* 담당자 리스트 */
        List<CommonEntity> managerList = IR040310.getManagerList(conn);
        request.setAttribute("page.mn04.managerList", managerList);

        /* 목코드 select box처리쿼리 */
        List<CommonEntity> semokcodeList = IR040310.getsemokcodeList(conn, paramInfo);
        request.setAttribute("page.mn04.semokcodeList", semokcodeList);
        /* 세목코드 selectbox처리쿼리 */
        List<CommonEntity> sesemokcode = IR040310.getsesemokcodeList(conn, paramInfo);
        request.setAttribute("page.mn04.sesemokcode", sesemokcode);
        /* 목코드 선택시 상위코드정보 조회쿼리 */
        CommonEntity sangweesemokList = IR040310.getsangweesemokList(conn, paramInfo);
        request.setAttribute("page.mn04.sangweesemokList", sangweesemokList);

        /* 담당자 리스트 */
        List<CommonEntity> napbuList = IR040310.getnapbuList(conn, paramInfo);
        request.setAttribute("page.mn04.napbuList", napbuList);
        /* 담당자 리스트 */
        CommonEntity napbuaddressList = IR040310.getnapbuaddressList(conn, paramInfo);
        request.setAttribute("page.mn04.napbuaddressList", napbuaddressList);

		  
	}
}