/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR061310InsertCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금운용 > 자금인출(기금,특별) 등록
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn06.IR061310;
import com.etax.db.mn06.IR060000;
import com.etax.db.mn06.IR060610;
import com.etax.command.common.TransLogInsert;
import com.etax.command.mn06.IR060000Register;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;


public class IR061310InsertCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR061310InsertCMD.class); // log4j 설정

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 

        CommonEntity paramInfo = new CommonEntity();
        paramInfo.setValue("fis_year",           request.getParameter("fis_year"));
        paramInfo.setValue("acct_kind",          request.getParameter("acct_kind"));
        paramInfo.setValue("acct_list",          request.getParameter("acct_list"));
        paramInfo.setValue("acc_type",           request.getParameter("acct_kind"));//잔액장 용도
        paramInfo.setValue("acc_code",           request.getParameter("acct_list"));//잔액장 용도
        paramInfo.setValue("part_code",          request.getParameter("part_code"));
        paramInfo.setValue("work_flag",          "3");

		String[] chk_list       = request.getParameterValues("allotChk");
		String[] deposit_type   = request.getParameterValues("deposit_type");
		String[] due_day        = request.getParameterValues("due_day");
		String[] input_amt      = request.getParameterValues("input_amt");
        String[] cancel_date    = request.getParameterValues("cancel_date");
		String[] cancel_ija     = request.getParameterValues("cancel_ija");
        String[] acct_no        = request.getParameterValues("acct_no");
		String[] jwasu_no       = request.getParameterValues("jwasu_no");
        String[] mmda_type      = request.getParameterValues("mmda_type");
        String[] dep_amt        = request.getParameterValues("dep_amt");
        String[] mmda_cancel    = request.getParameterValues("mmda_cancel");
    
        String SucMsg = "";
        String ErrMsg = "";
		String acc_date = TextHandler.getCurrentDate();
        String fis_year = request.getParameter("fis_year");

        int len = 0;
        String holy = "";
        for (int i=0; i<chk_list.length; i++) {
		    int x = Integer.parseInt(chk_list[i]);
            if (!cancel_date[x].equals(TextHandler.getBusinessDate(conn, cancel_date[x])) ) {
                holy = "해지일자가 공휴일입니다.";
            }  
        }
		conn.setAutoCommit(false);
		//일일마감여부 체크
        CommonEntity d_cnt = IR060000.dailyCheck(conn, acc_date);
        CommonEntity closeInfo = IR060000.closingCheck1(conn, acc_date);  //폐쇄기체크
        if ("Y".equals(d_cnt.getString("M210_WORKENDSTATE")) ) {
	    	SucMsg = "일일마감이 완료되었습니다. 일계등록처리를 할 수 없습니다.";
        } else if (!"".equals(holy) ) {
            SucMsg = holy;
        } else if ("FALSE".equals(closeInfo.getString("DATE_CHEK")) 
            && !fis_year.equals(acc_date.substring(0,4))) {
            //폐쇄기간이 아닌데 회계과년도 작업을 시도할 때
            SucMsg = "폐쇄기간(1월1일 ~ 3월10일)을 제외한 기간에 회계과년도 자료 등록을 할 수 없습니다.";
        } else {
			TransLogInsert tli = new TransLogInsert();
		    tli.execute(request, response, conn);
	        paramInfo.setValue("log_no"  , tli.getLogNo());

	        for (int i=0; i<chk_list.length; i++) {
		      	int y = Integer.parseInt(chk_list[i]);
                paramInfo.setValue("deposit_type",   deposit_type[y]);
                paramInfo.setValue("input_amt",      input_amt[y]);
                paramInfo.setValue("due_day",        due_day[y]);
              	paramInfo.setValue("cancel_date",    cancel_date[y]);
                paramInfo.setValue("cancel_ija",     cancel_ija[y]);
                paramInfo.setValue("acct_no",        acct_no[y]);
                paramInfo.setValue("jwasu_no",       jwasu_no[y]);
              	paramInfo.setValue("acc_date",       cancel_date[y]);
                paramInfo.setValue("mmda_type",      mmda_type[y]);
                paramInfo.setValue("dep_amt",        dep_amt[y]);
                paramInfo.setValue("mmda_cancel",    mmda_cancel[y]);
                logger.info("paramInfo : " + paramInfo);

              	ErrMsg = IR060000Register.permission(conn, paramInfo);
                
                len += ErrMsg.length();
                
              	if (len == 0) {
                
                    CommonEntity m130Seq = IR061310.getInchulSeq(conn);
                    paramInfo.setValue("m130_seq",    m130Seq.getString("SEQNO"));

                    if (IR061310.insertInchul(conn, paramInfo)<1) {
                        conn.rollback();
		  	            conn.setAutoCommit(true);
                        SucMsg = "인출테이블 등록 중에 오류가 발생하였습니다.";
                    }

                  	if ("G3".equals(deposit_type[y]) || "G4".equals(deposit_type[y])) {
                    
				    	if (IR061310.updateMMDA(conn, paramInfo) < 1 )	{
		  		            conn.rollback();
		  	                conn.setAutoCommit(true);
		  			        throw new ProcessException("E003"); //수정중 오류메시지
		  		        }
					  
                        if (IR061310.insertMMDADetail(conn, paramInfo) < 1 )	{
		  		            conn.rollback();
		  	                conn.setAutoCommit(true);
		  		            throw new ProcessException("E002"); //등록중 오류메시지
		  		        }
		  		
                    } else {  //정기예금, 환매체
		  		        if (IR061310.updateHwan(conn, paramInfo) < 1 )	{
		  			        conn.rollback();
		  	                conn.setAutoCommit(true);
		  			        throw new ProcessException("E002"); //등록중 오류메시지
		  		        }
		  	        }
            
                    if ("".equals(SucMsg)) {
                        SucMsg = "일계등록 처리가 완료되었습니다.";
                    } 

                } else {
                    SucMsg = "잔액장 처리 중 오류가 발생하였습니다.";
                    conn.rollback();
	                conn.setAutoCommit(true);
                }
        
            }
        }

        conn.commit();
        conn.setAutoCommit(true);
        request.setAttribute("page.mn06.SucMsg", SucMsg);

        List<CommonEntity> acctList = new ArrayList<CommonEntity>();
        List<CommonEntity> partList = new ArrayList<CommonEntity>();
        /* 회계종류 조회 */
		if ("".equals(request.getParameter("fis_year")) || request.getParameter("fis_year") == null) {
            partList = IR060610.getPartList(conn);
			acctList = IR060610.getAcctList(conn);
      
		} else {
            partList = IR060610.getPartList(conn, paramInfo);
			acctList = IR060610.getAcctList(conn, paramInfo);
      
		}

        request.setAttribute("page.mn06.acctList", acctList);
        request.setAttribute("page.mn06.partList", partList);

		/* 자금인출 승인조회 */
        List<CommonEntity> inchulSpList = IR061310.getInchulSpList(conn, paramInfo);
		request.setAttribute("page.mn06.inchulSpList", inchulSpList);
    }
}