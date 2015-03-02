/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR062110InsertCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > ������������
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

    private static Logger logger = Logger.getLogger(IR062110InsertCMD.class); // log4j ����

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
            request.setAttribute("page.mn06.SucMsg",   "ȸ�����ڰ� �����Դϴ�.");
        } else {
		    if (IR062110.segyeInsert(conn, paramInfo) > 0) {
                request.setAttribute("page.mn06.SucMsg",   "��ϵǾ����ϴ�.");
            }
        }

        List<CommonEntity> accList = IR062110.getAccList(conn, paramInfo);
        request.setAttribute("page.mn06.accList",   accList);
		
    }
}

