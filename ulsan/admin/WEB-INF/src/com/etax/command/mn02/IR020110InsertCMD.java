/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : IR020110InsertCMD.java
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-01
* 프로그램내용	   : 세출 > 수기분등록
****************************************************************/

package com.etax.command.mn02;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn02.IR020110;
import com.etax.db.mn01.IR010110;
import com.etax.db.mn02.IR020000;
import com.etax.command.common.TransLogInsert; 
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;

public class IR020110InsertCMD extends BaseCommand {
 
	private static Logger logger = Logger.getLogger(IR020110InsertCMD.class);
	
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    CommonEntity paramInfo = new CommonEntity();

		  paramInfo.setValue("fis_year",               request.getParameter("fis_year"));
			paramInfo.setValue("fis_date",               request.getParameter("fis_date"));
			paramInfo.setValue("acc_type",               request.getParameter("acc_type"));
		  paramInfo.setValue("part_code",              request.getParameter("part_code"));									 
			paramInfo.setValue("acc_code",               request.getParameter("acc_code"));
		  paramInfo.setValue("semok_code",             request.getParameter("semok_code"));
			paramInfo.setValue("intype",                 request.getParameter("intype"));
			paramInfo.setValue("order_name",             request.getParameter("order_name"));
			paramInfo.setValue("order_no",               request.getParameter("order_no"));
			paramInfo.setValue("amt",                    TextHandler.unformatNumber(request.getParameter("amt")));

      logger.info("paramInfo : " + paramInfo);		
    
			CommonEntity endChk = IR020000.getDailyEndWork(conn, paramInfo); //일일마감체크
      
        if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{
          request.setAttribute("page.mn02.FailMsg", "일일마감이 완료된 회계일자는 등록할 수 없습니다.");
		    
        } else {

            CommonEntity dayChk = IR020000.getEndYear(conn, paramInfo); //마감일체크

              if ("A".equals(request.getParameter("acc_type")))	{  //일반회계
            
                if ( Integer.parseInt(request.getParameter("fis_date")) > Integer.parseInt(dayChk.getString("M320_DATEILBAN")) )	{
                    request.setAttribute("page.mn02.FailMsg", "유효하지 않은 날짜입니다. 확인 후 다시 등록하세요.");

                } else {

                    CommonEntity noData = IR020110.getOrderNo(conn, paramInfo);   //지급번호 중복 여부

                       if(noData.getLong("NO_CNT") == 0){
                       
                         //로그기록 남기는 클래스및 메소드 호출
                         TransLogInsert tli = new TransLogInsert();
                         tli.execute(request, response, conn);
                         paramInfo.setValue("log_no", tli.getLogNo());
                         
                         if(IR020110.revWriteInsert(conn, paramInfo) < 1 ) {
                           throw new ProcessException("E002"); //등록중 오류메시지 표시
                         }
       
                       } else {
                             request.setAttribute("page.mn02.FailMsg", "지급번호가 중복됩니다. 다시 등록하세요.");	
         
                       }
                }
              }
                

              if ("B".equals(request.getParameter("acc_type")))	{  //특별회계
            
                if ( Integer.parseInt(request.getParameter("fis_date")) > Integer.parseInt(dayChk.getString("M320_DATETEKBEYL")) )	{
                    request.setAttribute("page.mn02.FailMsg", "유효하지 않은 날짜입니다. 확인 후 다시 등록하세요.");

                } else {

                    CommonEntity noData = IR020110.getOrderNo(conn, paramInfo);   //지급번호 중복 여부

                       if(noData.getLong("NO_CNT") == 0){
                       
                         //로그기록 남기는 클래스및 메소드 호출
                         TransLogInsert tli = new TransLogInsert();
                         tli.execute(request, response, conn);
                         paramInfo.setValue("log_no", tli.getLogNo());
                         
                         if(IR020110.revWriteInsert(conn, paramInfo) < 1 ) {
                           throw new ProcessException("E002"); //등록중 오류메시지 표시
                         }
       
                       } else {
                             request.setAttribute("page.mn02.FailMsg", "지급번호가 중복됩니다. 다시 등록하세요.");
                       }
                }       
              }


              if ("E".equals(request.getParameter("acc_type")))	{   //기금
            
                if ( Integer.parseInt(request.getParameter("fis_date")) > Integer.parseInt(dayChk.getString("M320_DATEGIGEUM")) )	{
                    request.setAttribute("page.mn02.FailMsg", "유효하지 않은 날짜입니다. 확인 후 다시 등록하세요.");

                } else {

                    CommonEntity noData = IR020110.getOrderNo(conn, paramInfo);   //지급번호 중복 여부

                       if(noData.getLong("NO_CNT") == 0){
                       
                         //로그기록 남기는 클래스및 메소드 호출
                         TransLogInsert tli = new TransLogInsert();
                         tli.execute(request, response, conn);
                         paramInfo.setValue("log_no", tli.getLogNo());
                       
                         if(IR020110.revWriteInsert(conn, paramInfo) < 1 ) {
                           throw new ProcessException("E002"); //등록중 오류메시지 표시
                         }
         
                       } else {
                             request.setAttribute("page.mn02.FailMsg", "지급번호가 중복됩니다. 다시 등록하세요.");	
                       }
                }       
              }
			       
           
        }

		/* 리스트 조회 */
    CommonEntity fis_date  = IR010110.getFisDate(conn);
    request.setAttribute("page.mn01.fis_date", fis_date); 

		List<CommonEntity> revDeptList = IR020110.getRevDeptList(conn, paramInfo);
    request.setAttribute("page.mn02.revDeptList", revDeptList); 

		List<CommonEntity> revAccNmList = IR020110.getRevAccNmList(conn, paramInfo);
    request.setAttribute("page.mn02.revAccNmList", revAccNmList); 

		List<CommonEntity> revSemokList = IR020110.getRevSemokList(conn, paramInfo);
    request.setAttribute("page.mn02.revSemokList", revSemokList);
	}
}