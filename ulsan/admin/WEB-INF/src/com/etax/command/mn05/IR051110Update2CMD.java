/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR051110Update2CMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > �׿�������ó��
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn05.IR051110;
import com.etax.db.mn05.IR050000;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;

public class IR051110Update2CMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR051110Update2CMD.class); // log4j ����

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

        HttpSession session = request.getSession(false);
        String user_id = (String)session.getAttribute("session.user_id");

        CommonEntity paramInfo = new CommonEntity();
        paramInfo.setValue("reg_date",    request.getParameter("reg_date"));
        paramInfo.setValue("acc_date",    request.getParameter("reg_date")); //������ȸ��
        paramInfo.setValue("fis_year",   (request.getParameter("reg_date")).substring(0,4)); //������ȸ��
        paramInfo.setValue("user_id",     user_id);

		String[] chk = request.getParameterValues("allotChk");
		String[] seq = request.getParameterValues("seq_list");
        String[] m010seq = request.getParameterValues("m010seq_list");
        String[] m240seq = request.getParameterValues("m240seq_list");

		for (int i=0; i<chk.length; i++) {
			int y = Integer.parseInt(chk[i]);

			IR051110.srpDelete(conn, paramInfo, seq[y]);  //������ ���·� ����
 
            IR051110.srp240Delete(conn, paramInfo, m240seq[y]);  //�׿������Ը� ����

            IR051110.srp010Delete(conn, paramInfo, m010seq[y]);  //���Լ���� ����

		}
	  
		/* �׿������� ��ȸ */
		List<CommonEntity> srpPmsList = IR051110.getSrpCancelList(conn, paramInfo);
		request.setAttribute("page.mn05.srpPmsList", srpPmsList);

        CommonEntity magamInfo = IR050000.getDailyEndWork(conn, paramInfo);
        request.setAttribute("page.mn05.magamState",  (String)magamInfo.getString("M210_WORKENDSTATE"));

		request.setAttribute("page.mn05.SucMsg", "�������ó���Ǿ����ϴ�.");
    }
}