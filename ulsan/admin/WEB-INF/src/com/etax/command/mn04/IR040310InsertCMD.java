/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR020410InsertCMD.java
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-08-12
* ���α׷�����	   : ���ܼ��� > ¡������ ���
****************************************************************/

package com.etax.command.mn04;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;
import com.oreilly.servlet.MultipartRequest;

import com.etax.command.common.TransLogInsert;
import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn04.IR040310;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;


public class IR040310InsertCMD extends BaseCommand {
    private static Logger logger = Logger.getLogger(IR040310InsertCMD.class);
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {
   
        String procyn = "Y";

        CommonEntity paramInfo = new CommonEntity();

        paramInfo.setValue("seq",                   request.getParameter("seq"));	
		paramInfo.setValue("year",                  request.getParameter("year"));  	
		paramInfo.setValue("baluiDate",             request.getParameter("baluiDate"));			 //�������� ����
		paramInfo.setValue("gojiseoPublish",        request.getParameter("gojiseoPublish"));			 //������� ��������
		paramInfo.setValue("napibDate",             request.getParameter("napibDate"));			 //�������� ��������
		paramInfo.setValue("jingsubuWrite",         request.getParameter("jingsubuWrite"));		 //������� �������
		paramInfo.setValue("gwan",                  request.getParameter("gwan"));
		paramInfo.setValue("hang",                  request.getParameter("hang"));
		paramInfo.setValue("mok",                   request.getParameter("mok"));
		paramInfo.setValue("semok",                 request.getParameter("semok"));
		paramInfo.setValue("bonAmt",                TextHandler.replace(request.getParameter("bonAmt"),",",""));
        paramInfo.setValue("gasanAmt",              TextHandler.replace(request.getParameter("gasanAmt"),",",""));
		paramInfo.setValue("interestAmt",           TextHandler.replace(request.getParameter("interestAmt"),",",""));
        paramInfo.setValue("totalAmt",              TextHandler.replace(request.getParameter("totalAmt"),",",""));
		paramInfo.setValue("napbujaName",           request.getParameter("napbujaName"));
        paramInfo.setValue("juminNo",               request.getParameter("juminNo"));
		paramInfo.setValue("address",               request.getParameter("address"));
        paramInfo.setValue("businessName",          request.getParameter("businessName"));
	    paramInfo.setValue("userName",              request.getParameter("userName"));
        paramInfo.setValue("fis_year",              request.getParameter("fis_year"));
        if ("".equals(paramInfo.getString("fis_year")) ) {
            paramInfo.setValue("fis_year",    TextHandler.formatDate(TextHandler.getCurrentDate(), "yyyyMMdd", "yyyy"));
        } 

	    CommonEntity taskID = IR040310.getTaskId(conn, paramInfo); //����üũ
      
        if ("ED03".equals(taskID.getString("M200_DOCUMENTCODE")))	{
            paramInfo.setValue("doc_code",    "ED03");
			paramInfo.setValue("kyuluiType",    "J1");
            procyn = "Y";
        } else if ("ED12".equals(taskID.getString("M200_DOCUMENTCODE")))	{ 
            paramInfo.setValue("doc_code",    "ED12");
			paramInfo.setValue("kyuluiType",    "J3");
            procyn = "Y";
		} else if ("ED15".equals(taskID.getString("M200_DOCUMENTCODE")))	{ 
            paramInfo.setValue("doc_code",    "ED15");
			paramInfo.setValue("kyuluiType",    "J4");
            procyn = "Y";
        } else {
            procyn = "N";
            request.setAttribute("page.mn04.SucMsg", "¡�����Ǽ���� ������ �����ϴ�.");
        }

        if ("Y".equals(procyn)) {
            if (!"".equals(paramInfo.getString("year")) && (paramInfo.getString("year")).length() == 4) {  //080ȸ��⵵�� ���� �ƴҶ�
                paramInfo.setValue("col_name",    "M080_MONTHAMT_"+Integer.parseInt((TextHandler.getCurrentDate()).substring(4,6))+"");
                if (!((TextHandler.getCurrentDate()).substring(0,4)).equals(paramInfo.getString("year"))) { //ȸ��⵵�� ����⵵ �ٸ� ��
                    if ("01".equals((TextHandler.getCurrentDate()).substring(4,6)) ) {
                        paramInfo.setValue("col_name",    "M080_MONTHAMT_13");
                    } else if ("02".equals((TextHandler.getCurrentDate()).substring(4,6)) ) {
                        paramInfo.setValue("col_name",    "M080_MONTHAMT_14");
                    }
                }
            }

            if (!((TextHandler.getCurrentDate()).substring(0,4)).equals(paramInfo.getString("year")) 
                && !"".equals(paramInfo.getString("year"))) { //ȸ��⵵�� ����⵵ �ٸ� ��)
                if (Integer.parseInt((TextHandler.getCurrentDate()).substring(0,4)) > 2) {
                    request.setAttribute("page.mn04.SucMsg", "ȸ��⵵�� �̿��Ǿ� ����� �Ұ����մϴ�.");
                }
            } else {
	 
	            //�αױ�� ����� Ŭ������ �޼ҵ� ȣ��
                TransLogInsert tli = new TransLogInsert();
  	            tli.execute(request, response, conn);
	            paramInfo.setValue("log_no", tli.getLogNo());
                logger.info("paramInfo : " + paramInfo);

                /* ����� ��� */
  	            if(IR040310.jingsuInsert(conn, paramInfo) < 1 ) {
	  	            throw new ProcessException("E002"); //����� �����޽��� ǥ��
	            }

                if (!"".equals(paramInfo.getString("seq")) ) {  //���꼭 �ݾ� ����
                    if (IR040310.updateYesanAmt(conn, paramInfo) < 1)  {
                        throw new ProcessException("E003");
                    }
                }
                request.setAttribute("page.mn04.SucMsg", "��ϵǾ����ϴ�.");
            }
        }//¡�����Ǽ� ��� ����üũ	

        paramInfo.setValue("napbujaName",  request.getParameter("napbujaName"));	
		paramInfo.setValue("semokcode",    request.getParameter("mok"));

		/* ¡������ ��  */
		CommonEntity budgetView = IR040310.getBudgetView(conn, paramInfo);
		request.setAttribute("page.mn04.budgetView", budgetView);

        /* ����� ����Ʈ */
        List<CommonEntity> managerList = IR040310.getManagerList(conn);
        request.setAttribute("page.mn04.managerList", managerList);

        /* ���ڵ� select boxó������ */
        List<CommonEntity> semokcodeList = IR040310.getsemokcodeList(conn, paramInfo);
        request.setAttribute("page.mn04.semokcodeList", semokcodeList);
        /* �����ڵ� selectboxó������ */
        List<CommonEntity> sesemokcode = IR040310.getsesemokcodeList(conn, paramInfo);
        request.setAttribute("page.mn04.sesemokcode", sesemokcode);
        /* ���ڵ� ���ý� �����ڵ����� ��ȸ���� */
        CommonEntity sangweesemokList = IR040310.getsangweesemokList(conn, paramInfo);
        request.setAttribute("page.mn04.sangweesemokList", sangweesemokList);

        /* ����� ����Ʈ */
        List<CommonEntity> napbuList = IR040310.getnapbuList(conn, paramInfo);
        request.setAttribute("page.mn04.napbuList", napbuList);
        /* ����� ����Ʈ */
        CommonEntity napbuaddressList = IR040310.getnapbuaddressList(conn, paramInfo);
        request.setAttribute("page.mn04.napbuaddressList", napbuaddressList);

		  
	}
}