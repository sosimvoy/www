/*****************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR091910InsertCMD.java
* ���α׷��ۼ���	: (��)�̸�����
* ���α׷��ۼ���	: 2012-02-13
* ���α׷�����		: �ý��ۿ > �μ����������� ���
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR091910;
import com.etax.entity.CommonEntity;

public class IR091910InsertCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR091910InsertCMD.class);	// log4j ����
	
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("accgubun",      request.getParameter("accgubun"));
		paramInfo.setValue("reportgubun",   request.getParameter("reportgubun"));
        paramInfo.setValue("list_part",     request.getParameter("list_part"));

        paramInfo.setValue("partcode",      request.getParameter("partcode"));
		paramInfo.setValue("reportcode",    request.getParameter("reportcode"));
        paramInfo.setValue("acccode",       request.getParameter("acccode"));
        paramInfo.setValue("elecyn",        request.getParameter("elecyn"));
        paramInfo.setValue("ilgwalyn",      request.getParameter("ilgwalyn"));
        
        String SucMsg = "";
        CommonEntity ilgwal = IR091910.getIlgwalInfo(conn, paramInfo);
        if (ilgwal.size() > 0 && !paramInfo.getString("ilgwalyn").equals(ilgwal.getString("M630_ILGWALYN")) ) {
            SucMsg = "�����ڷ�� �ϰ����翩�ΰ� �������� �ʽ��ϴ�. �����ڷ� ���� �� ����ϼ���.";
        } else {
            CommonEntity count = IR091910.getCount(conn, paramInfo);
            if ("0".equals(count.getString("CNT")) ) {
                try {
                    IR091910.insertDocInfo(conn, paramInfo);
                    SucMsg = "���������ڷᰡ ��ϵǾ����ϴ�.";
                } catch (Exception e) {
                    logger.info("IR091910InsertCMD exception : [" + e + "]");
                    SucMsg = "��� �� ������ �߻��Ͽ����ϴ�.";
                }
            } else {
                SucMsg = "�̹� ��ϵ� �ڷ��Դϴ�.";
            }
        }
        request.setAttribute("page.mn09.SucMsg", SucMsg);
        
		/* ���form�� �μ��ڷ� ��ȸ */
		List<CommonEntity> partList = IR091910.getPartList(conn, paramInfo);
		request.setAttribute("page.mn09.partList", partList);

		/* ���form�� �������� ��ȸ */
		List<CommonEntity> docList = IR091910.getDocList(conn, paramInfo);
		request.setAttribute("page.mn09.docList", docList);

        /* ���form�� �������� ��ȸ */
		List<CommonEntity> accList = IR091910.getAccList(conn, paramInfo);
		request.setAttribute("page.mn09.accList", accList);

        /* �μ��� �������� ��ȸ */
		List<CommonEntity> docInfoList = IR091910.getDocInfoList(conn, paramInfo);
		request.setAttribute("page.mn09.docInfoList", docInfoList);
	}
}