/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : IR030110InsertCMD.java
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-01
* 프로그램내용	   : 세입세출외현금 > 수기분등록
****************************************************************/

package com.etax.command.mn03;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn03.IR030110;
import com.etax.db.mn01.IR010110;
import com.etax.db.mn03.IR030000;
import com.etax.command.common.TransLogInsert; 
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR030110InsertCMD extends BaseCommand {
 
    private static Logger logger = Logger.getLogger(IR030110InsertCMD.class);
	
    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {
  
        CommonEntity paramInfo = new CommonEntity();

        paramInfo.setValue("fis_year",        request.getParameter("fis_year"));
        paramInfo.setValue("fis_date",        request.getParameter("fis_date"));
        paramInfo.setValue("part_code",       request.getParameter("part_code"));
        paramInfo.setValue("acc_code",        request.getParameter("acc_code"));
        paramInfo.setValue("dwtype",          request.getParameter("dwtype"));
        paramInfo.setValue("intype",          request.getParameter("intype"));
        paramInfo.setValue("cash_type",       request.getParameter("cash_type"));
        paramInfo.setValue("deposit_type",    request.getParameter("deposit_type"));
        paramInfo.setValue("order_name",      request.getParameter("order_name"));
        paramInfo.setValue("note",            request.getParameter("note"));
        paramInfo.setValue("order_no",        request.getParameter("order_no"));
        paramInfo.setValue("cnt",             request.getParameter("cnt"));
        paramInfo.setValue("amt",             TextHandler.unformatNumber(request.getParameter("amt")));
        
        logger.info("paramInfo : " + paramInfo);
    
        CommonEntity endChk = IR030000.getDailyEndWork(conn, paramInfo); //일일마감체크
        
        if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{
            request.setAttribute("page.mn03.FailMsg", "일일마감이 완료된 회계일자는 등록할 수 없습니다.");
            
        } else {

            CommonEntity dayChk = IR030000.getEndYear(conn, paramInfo); //마감일체크

            if ( Integer.parseInt(request.getParameter("fis_date")) > Integer.parseInt(dayChk.getString("M320_DATEHYUNGEUM")) )	{
                request.setAttribute("page.mn03.FailMsg", "유효하지 않은 날짜입니다. 확인 후 다시 등록하세요.");

            } else {

                CommonEntity noData = IR030110.getOrderNo(conn, paramInfo);   //지급번호 중복 여부

                if(noData.getLong("NO_CNT") == 0){	
                    
                    //로그기록 남기는 클래스및 메소드 호출
                    TransLogInsert tli = new TransLogInsert();
                    tli.execute(request, response, conn);
                    paramInfo.setValue("log_no", tli.getLogNo());

                    if(IR030110.cashInsert(conn, paramInfo) < 1 ) {  //수기분 등록 
                        throw new ProcessException("E002"); //등록중 오류메시지 표시
                    }
                    request.setAttribute("page.mn03.SucMsg", "등록처리되었습니다.");
                } else {
                    request.setAttribute("page.mn03.SucMsg", "지급번호가 중복됩니다. 다시 등록하세요.");
                }
            }
        }

        /* 리스트 조회 */
        CommonEntity fis_date  = IR010110.getFisDate(conn);
        request.setAttribute("page.mn01.fis_date", fis_date);
		
        List<CommonEntity> cashDeptList = IR030110.getCashDeptList(conn, paramInfo);
        request.setAttribute("page.mn03.cashDeptList", cashDeptList);

        List<CommonEntity> accNameList = IR030110.getAccNameList(conn,paramInfo);
        request.setAttribute("page.mn03.accNameList", accNameList); 
    }
}