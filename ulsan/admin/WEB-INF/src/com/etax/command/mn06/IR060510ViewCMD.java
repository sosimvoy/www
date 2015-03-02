/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR060510ViewCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ڱݿ�Ź��������ȸ - ������ �˾�
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
import com.etax.db.mn06.IR060510;
import com.etax.db.mn06.IR060000;
import com.etax.entity.CommonEntity;

public class IR060510ViewCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR060510ViewCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{

		CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("fis_year",     request.getParameter("fis_year"));
		paramInfo.setValue("reg_date",     request.getParameter("reg_date"));
    paramInfo.setValue("doc_no",       request.getParameter("doc_no"));
		paramInfo.setValue("doc_code",     "ED07");
		
    logger.info("paramInfo : " + paramInfo);
    
		//��Ź�Ѿ�
		CommonEntity totAmt = IR060510.getDepositTotAmt(conn, paramInfo);
    request.setAttribute("page.mn06.totAmt", totAmt);
    
    List<CommonEntity> bankDepViewList = IR060510.getBankDepReportView(conn, paramInfo);
    request.setAttribute("page.mn06.bankDepViewList", bankDepViewList);

    CommonEntity manager = IR060510.getManagerName(conn, paramInfo);
		request.setAttribute("page.mn06.manager", manager);

    CommonEntity sealInfo = IR060000.getSealInfo(conn);
		request.setAttribute("page.mn06.sealInfo", sealInfo);
  }
}