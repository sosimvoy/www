/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR060810InsertCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금운용 > 자금인출 요구등록
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn06.IR060810;
import com.etax.db.mn06.IR060000;
import com.etax.command.common.TransLogInsert;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;


public class IR060810InsertCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR060810InsertCMD.class); // log4j 설정

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

        int cpage = 1;
		if( !"".equals(request.getParameter("cpage")) ) {
			cpage = Integer.parseInt(request.getParameter("cpage"));	
		}

		int fromRow = (cpage-1) * 10;
		int toRow = cpage * 10;

        CommonEntity paramInfo = new CommonEntity();
        paramInfo.setValue("fis_year",        request.getParameter("fis_year"));
        paramInfo.setValue("acc_date",        TextHandler.getCurrentDate());
        paramInfo.setValue("fromRow",            fromRow + "");
        paramInfo.setValue("toRow",              toRow + "");

		String[] chk_list   = request.getParameterValues("allotChk");
		String[] acct_no    = request.getParameterValues("acct_no");
		String[] jwasu_no   = request.getParameterValues("jwasu_no");
		String[] dep_gubun  = request.getParameterValues("dep_gubun");
		String[] dep_amt      = request.getParameterValues("dep_amt");
        String[] mmda_type  = request.getParameterValues("mmda_type");
        String[] mmda_cancel  = request.getParameterValues("mmda_cancel");
    
        String SucMsg = "";
		String acc_date = TextHandler.getCurrentDate();
        String fis_year = request.getParameter("fis_year");

        conn.setAutoCommit(false);
        //일일마감여부 체크
        CommonEntity d_cnt = IR060000.dailyCheck(conn, acc_date);
        CommonEntity closeInfo = IR060000.closingCheck1(conn, acc_date);  //폐쇄기체크
        if ("Y".equals(d_cnt.getString("M210_WORKENDSTATE")) ) {
			SucMsg = "일일마감이 완료되었습니다. 등록처리를 할 수 없습니다.";
        } else if (!acc_date.equals(TextHandler.getBusinessDate(conn, acc_date)) ) {
            SucMsg = "해지일자가 영업일이 아닙니다.";
        } else if ("FALSE".equals(closeInfo.getString("DATE_CHEK")) 
            && !fis_year.equals(acc_date.substring(0,4))) {
            //폐쇄기간이 아닌데 회계과년도 작업을 시도할 때
            SucMsg = "폐쇄기간(1월1일 ~ 3월10일)을 제외한 기간에 회계과년도 자료 등록을 할 수 없습니다.";
        } else {

            //로그기록 남기는 클래스및 메소드 호출
            TransLogInsert tli = new TransLogInsert();
		    tli.execute(request, response, conn);
		    paramInfo.setValue("log_no", tli.getLogNo());

	        for (int i=0; i<chk_list.length; i++) {
			    int y = Integer.parseInt(chk_list[i]);

                paramInfo.setValue("deposit_type",   dep_gubun[y]);
                paramInfo.setValue("input_amt",      dep_amt[y]);
                paramInfo.setValue("acct_no",        acct_no[y]);
                paramInfo.setValue("jwasu_no",       jwasu_no[y]);
                paramInfo.setValue("mmda_type",      mmda_type[y]);
                paramInfo.setValue("mmda_cancel",    mmda_cancel[y]);

                logger.info("paramInfo : " + paramInfo);
        
                if ("G3".equals(dep_gubun[y])) {
                    if (IR060810.insertMMDA(conn, paramInfo) < 1 )	{  //MMDA인출등록
			            conn.rollback();
	                    conn.setAutoCommit(true);
  		                throw new ProcessException("E002"); //등록중 오류메시지
		            }                   
          
                    if (IR060810.updateMMDAReqAmt(conn, paramInfo) < 1 )	{  //MMDA요구잔액
			            conn.rollback();
	                    conn.setAutoCommit(true);
  		                throw new ProcessException("E002"); //등록중 오류메시지
		            }
                } else {
                    if (IR060810.insertJeongHwan(conn, paramInfo) < 1 )	{  //정기환매체인출등록
			            conn.rollback();
	                    conn.setAutoCommit(true);
  		                throw new ProcessException("E002"); //등록중 오류메시지
		            }
          
                    if (IR060810.updateReqYN(conn, paramInfo) < 1 )	{  //정기환매체요구상태수정
			            conn.rollback();
	                    conn.setAutoCommit(true);
  		                throw new ProcessException("E002"); //등록중 오류메시지
		            } 
                }
            }
            SucMsg = "등록처리되었습니다.";
	    }
    
		conn.commit();
	    conn.setAutoCommit(true);

		List<CommonEntity> bankOutList = IR060810.getBankOutList(conn, paramInfo);
		request.setAttribute("page.mn06.bankOutList", bankOutList);

        CommonEntity rowData = IR060810.getTotalCount(conn, paramInfo);
		request.setAttribute("page.mn06.totalCount", new Integer(rowData.getInt("CNT")));
		
		request.setAttribute("page.mn06.SucMsg", SucMsg);
    }
}