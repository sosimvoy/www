/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR062110InsertCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금운용 > 세계현금전용
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
import com.etax.db.mn06.IR062110;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR062110InsertCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR062110InsertCMD.class); // log4j 설정

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

        CommonEntity paramInfo = new CommonEntity();
        String acc_date = request.getParameter("acc_date");
		paramInfo.setValue("acc_year",     request.getParameter("acc_year"));
        paramInfo.setValue("acc_date",     request.getParameter("acc_date"));
		paramInfo.setValue("jeon_type",    request.getParameter("jeon_type"));	
        paramInfo.setValue("acc_type",     request.getParameter("acc_type"));	
        paramInfo.setValue("acc_code",     request.getParameter("acc_code"));	
        paramInfo.setValue("amt",          request.getParameter("amt"));	
    
		if (!acc_date.equals(TextHandler.getBusinessDate(conn, acc_date)) ) {
            request.setAttribute("page.mn06.SucMsg",   "회계일자가 휴일입니다.");
        } else {
		    if (IR062110.segyeInsert(conn, paramInfo) > 0) {
                request.setAttribute("page.mn06.SucMsg",   "등록되었습니다.");
            }
        }

        List<CommonEntity> accList = IR062110.getAccList(conn, paramInfo);
        request.setAttribute("page.mn06.accList",   accList);
		
    }
}

