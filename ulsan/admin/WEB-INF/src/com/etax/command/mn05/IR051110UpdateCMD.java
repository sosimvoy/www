/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR051110UpdateCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > �׿������Կ䱸���
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn05.IR051110;
import com.etax.db.mn05.IR050000;
import com.etax.command.common.TransLogInsert;  //�αױ�� ����� ���� import
import com.etax.entity.CommonEntity;

public class IR051110UpdateCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR051110UpdateCMD.class); // log4j ����

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

        HttpSession session = request.getSession(false);
        String user_id = (String)session.getAttribute("session.user_id");

        CommonEntity paramInfo = new CommonEntity();
        paramInfo.setValue("reg_date",    request.getParameter("reg_date"));
        paramInfo.setValue("acc_date",    request.getParameter("reg_date")); //������ȸ��
        paramInfo.setValue("fis_year",   (request.getParameter("reg_date")).substring(0,4)); //������ȸ��
        paramInfo.setValue("user_id",     user_id);
        paramInfo.setValue("work_log",    request.getParameter("work_log"));
        paramInfo.setValue("trans_gubun", request.getParameter("trans_gubun"));

		//�αױ�� ����� Ŭ������ �޼ҵ� ȣ��
        TransLogInsert tli = new TransLogInsert();
		tli.execute(request, response, conn);
		paramInfo.setValue("log_no", tli.getLogNo());
    
        logger.info("paramInfo : " + paramInfo);

		String[] chk = request.getParameterValues("allotChk");
		String[] seq = request.getParameterValues("seq_list");

		for (int i=0; i<chk.length; i++) {
			int y = Integer.parseInt(chk[i]);

			IR051110.updateSrpPms(conn, paramInfo, seq[y]); //�ϰ��ϻ��·� ����

            IR051110.insertSrpList1(conn, paramInfo, seq[y]);

			IR051110.insertSrpList2(conn, paramInfo, seq[y]);
		}
	  
		/* �׿������� ��ȸ */
		List<CommonEntity> srpPmsList = IR051110.getSrpCancelList(conn, paramInfo);
		request.setAttribute("page.mn05.srpPmsList", srpPmsList);

        CommonEntity magamInfo = IR050000.getDailyEndWork(conn, paramInfo);
        request.setAttribute("page.mn05.magamState",  (String)magamInfo.getString("M210_WORKENDSTATE"));

		request.setAttribute("page.mn05.SucMsg", "�ϰ���ó���Ǿ����ϴ�.");
    }
}