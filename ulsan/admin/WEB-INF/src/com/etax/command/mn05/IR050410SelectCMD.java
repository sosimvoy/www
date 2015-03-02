/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR050410SelectCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > �ڱݹ�����������ȸ
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn05.IR050410;
import com.etax.entity.CommonEntity;


public class IR050410SelectCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR050410SelectCMD.class); // log4j ����

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

        /* ��ȸ�� ������ ���� ���ϱ� */
		HttpSession session = request.getSession(false);
		String user_id = (String)session.getAttribute("session.user_id");

        CommonEntity paramInfo = new CommonEntity();
        paramInfo.setValue("allot_date",         request.getParameter("allot_date"));
		paramInfo.setValue("fis_year",           request.getParameter("fis_year"));
        paramInfo.setValue("report_gubun",       request.getParameter("report_gubun"));
    
        logger.info("paramInfo : " + paramInfo);
        
          /* �ڱݹ������γ��� ��ȸ */
        List<CommonEntity> bankAllotNoticeList = IR050410.getBankAllotNoticeList(conn, paramInfo);
        request.setAttribute("page.mn05.bankAllotNoticeList", bankAllotNoticeList);
            
     }
}