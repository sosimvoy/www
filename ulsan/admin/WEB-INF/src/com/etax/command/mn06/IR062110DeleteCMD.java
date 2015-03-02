/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR062110DeleteCMD.java
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

public class IR062110DeleteCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR062110DeleteCMD.class); // log4j 설정

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

        CommonEntity paramInfo = new CommonEntity();
        paramInfo.setValue("acc_year",     request.getParameter("acc_year"));
        paramInfo.setValue("acc_type",     request.getParameter("acc_type"));
		paramInfo.setValue("s_date",              request.getParameter("s_date"));
        paramInfo.setValue("s_year",              request.getParameter("s_year"));	
        
        String[] seq_no = request.getParameterValues("chk_val");

        for (int i=0; i<seq_no.length; i++) {
            paramInfo.setValue("seq", seq_no[i]);
            IR062110.segyeDelete(conn, paramInfo);
        }

        request.setAttribute("page.mn06.SucMsg",   "삭제되었습니다.");

        List<CommonEntity> accList = IR062110.getAccList(conn, paramInfo);
        request.setAttribute("page.mn06.accList",   accList);

        List<CommonEntity> segyeList = IR062110.getSegyeList(conn, paramInfo);
        request.setAttribute("page.mn06.segyeList",   segyeList);
		
    }
}

