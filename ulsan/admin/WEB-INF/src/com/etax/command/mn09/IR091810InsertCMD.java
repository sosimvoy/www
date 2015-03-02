/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	   : IR091810InsertCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-21
* ���α׷�����	 : �ý��ۿ > �μ��ڵ� ���
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR091810;
import com.etax.entity.CommonEntity;
import com.etax.util.StringUtil;

public class IR091810InsertCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR091810InsertCMD.class);	// log4j ����
	// Connection �� �ʿ��� ���� BaseCommand �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    CommonEntity paramInfo = new CommonEntity();

	  paramInfo.setValue("fis_year",      request.getParameter("year"));
		//paramInfo.setValue("account_no",    request.getParameter("acct_no"));
		String CuserPw = "";
		try {
		    CuserPw = request.getParameter("acct_no");
		    if(!"".equals(CuserPw)) {
                CuserPw = StringUtil.encrypt("ETS.TRANS_TEF_EFAM026", "OUT_ACCT_ACCT_NO", CuserPw);
		    }
		}catch (Exception e) {
		    e.printStackTrace();
		}

        paramInfo.setValue("account_no",    CuserPw);
	    paramInfo.setValue("accgubun",      request.getParameter("stdAcccode"));
		paramInfo.setValue("part_code",     request.getParameter("sysPartcode"));
	    paramInfo.setValue("acc_code",      request.getParameter("sysAccount"));
		paramInfo.setValue("sysPartcode",  request.getParameter("sysPartcode")); 
        logger.info("paramInfo : "+paramInfo);
        
        CommonEntity idInfo   = IR091810.getcheckYn(conn, paramInfo);     //���µ�Ͽ��� üũ
        if(idInfo.getLong("insertCnt") > 0 ) {
            request.setAttribute("page.mn09.SucMsg", "��ϵ� ���¹�ȣ�Դϴ�.");
        } else {
		  if(IR091810.inserttefAccount(conn, paramInfo) < 1 ) {
		    throw new ProcessException("E003"); //������ �����޽��� ǥ��
		  }
          request.setAttribute("page.mn09.SucMsg", "��ϵǾ����ϴ�.");
        }
    
    //����˾��� ��������
		paramInfo.setValue("queyear",      request.getParameter("year")); 
		paramInfo.setValue("accgbn",       request.getParameter("stdAcccode")); 

		/* ���form�� �μ��ڷ� ��ȸ */
		List usePartList = IR091810.getusePartList(conn, paramInfo);
		request.setAttribute("page.mn09.usePartList", usePartList);

		/* ���form�� ȸ���ڷ� ��ȸ */
		List useAcccodeList = IR091810.getuseAcccodeList(conn, paramInfo);
		request.setAttribute("page.mn09.useAcccodeList", useAcccodeList);

    //ǥ�ؼ����ڵ� �ڷ� ��ȸ��
		paramInfo.setValue("queyear",           request.getParameter("queyear")); 
		paramInfo.setValue("accgbn",            request.getParameter("questdAcccode")); 
		logger.info("paramInfo : " + paramInfo);
		List tefAccountList = IR091810.gettefAccountList(conn, paramInfo);
		request.setAttribute("page.mn09.tefAccountList", tefAccountList);
		
	}
}
