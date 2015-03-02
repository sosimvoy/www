/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR051510DeleteCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > �ڱݹ������γ�����ȸ/å���ڽ���ó��(å���ڽ��� Ȯ��â���� ��� ������ ��)
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn05.IR051510;
import com.etax.db.mn05.IR050000;
import com.etax.trans.TransHandler;
import com.etax.db.trans.TransDAO;
import com.etax.trans.data.GNBRev;
import com.etax.entity.CommonEntity;

public class IR051510DeleteCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR051510DeleteCMD.class); // log4j ����

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 

        CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("imp_rec_cd",       "9999");
        paramInfo.setValue("m100_sendno",      request.getParameter("m100_sendno"));
		paramInfo.setValue("m100_allotgubun",  request.getParameter("m100_allotgubun"));
		paramInfo.setValue("m100_allotamt",    request.getParameter("m100_allotamt"));

        paramInfo.setValue("allot_date",         request.getParameter("allot_date"));
		paramInfo.setValue("err_code",           "OVRD");
		paramInfo.setValue("err_cont",           "å���ڽ������ʿ��մϴ�.");
		paramInfo.setValue("allot_type",         "1");  //�ڱ� ������
		

        if (IR050000.errAllot(conn, paramInfo) < 1 ) {
			//Ȯ��â���� ��� ���� �� ���󺹱�
			throw new ProcessException("E004");
        }

		/* �ڱݹ������γ��� ��ȸ */
        List<CommonEntity> allotList = IR051510.getAllotList(conn, paramInfo);
        request.setAttribute("page.mn05.allotList", allotList);
    }
}