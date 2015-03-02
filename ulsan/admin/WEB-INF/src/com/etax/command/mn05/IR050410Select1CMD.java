/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR050410Select1CMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > �ڱ�(��)������������ȸ-�ڱݹ�������
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


public class IR050410Select1CMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR050410Select1CMD.class); // log4j ����

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

        CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("doc_no",           request.getParameter("doc_no"));
		paramInfo.setValue("fis_year",         request.getParameter("fis_year"));
        paramInfo.setValue("acc_date",         request.getParameter("acc_date"));
        paramInfo.setValue("doc_code",         request.getParameter("doc_code"));

        if ("ED05".equals(paramInfo.getString("doc_code")) ) {
            paramInfo.setValue("report_gubun",     "1");
        } else if ("ED06".equals(paramInfo.getString("doc_code")) ) {
            paramInfo.setValue("report_gubun",     "2");
        } else if ("ED00".equals(paramInfo.getString("doc_code")) ) {
            paramInfo.setValue("report_gubun",     "1");
        }

        logger.info("paramInfo : " + paramInfo);
    
        /* �ڱ������������ ��ȸ- ��������(��1��������) */
        List<CommonEntity> bankReportList1 = IR050410.getBankReportList(conn, paramInfo);
        request.setAttribute("page.mn05.cityBaeList1", bankReportList1);

        if ("ED00".equals(paramInfo.getString("doc_code")) ) {
            paramInfo.setValue("report_gubun",     "2");
            /* �ڱ������������ ��ȸ- ��������(��1��������) */
            List<CommonEntity> bankReportList2 = IR050410.getBankReportList(conn, paramInfo);
            request.setAttribute("page.mn05.cityBaeList2", bankReportList2);
        }
        
    }
}