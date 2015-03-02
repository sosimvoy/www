/*********************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR070410SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-08-02
* 프로그램내용   : 일계/보고서 > 일일보고조회
**********************************************************/
 
package com.etax.command.mn07;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn07.IR070410;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR070410SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR070410SelectCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
	    /* (조회할)파라미터 세팅 */
	    CommonEntity paramInfo = new CommonEntity();
        paramInfo.setValue("acc_year",   request.getParameter("acc_year"));
        paramInfo.setValue("acc_date",   request.getParameter("acc_date").replace("-", ""));
        
        String acc_date = request.getParameter("acc_date").replace("-", "");
        String acc_year = request.getParameter("acc_year");

        String SucMsg = ""; //메시지

        if (!acc_date.equals(TextHandler.getBusinessDate(conn, acc_date)) ) {
            SucMsg = "회계일자가 공휴일입니다. 회계일자는 반드시 영업일이어야 합니다.";
        } else {
            //해당 회계일자 자료 조회
            CommonEntity dateNote = IR070410.getDateNote(conn, paramInfo);
            request.setAttribute("page.mn07.dateNote", dateNote);

            String f_date = TextHandler.getBusinessDate(conn, acc_year+"0101");
            logger.info("연초영업일 : " + f_date);
            if (f_date.equals(acc_date)) { //연초 영업일일 경우 모든 자료 0으로 가져오기
                //전 영업일 자료 조회
                CommonEntity agoNote = IR070410.getAgoNote1(conn, paramInfo);
                request.setAttribute("page.mn07.agoNote", agoNote);

            } else {
                //전 영업일 자료 조회
                CommonEntity agoNote = IR070410.getAgoNote(conn, paramInfo);
                request.setAttribute("page.mn07.agoNote", agoNote);
            }
        }

        request.setAttribute("page.mn07.SucMsg", SucMsg);
	}     
}