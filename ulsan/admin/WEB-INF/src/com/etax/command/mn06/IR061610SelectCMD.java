/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR061610SelectCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ܾ����ڷ��Է�-��Ÿ���� ��ȸ
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn06.IR061610;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR061610SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR061610SelectCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("reg_date",      request.getParameter("reg_date2"));
		String reg_date = request.getParameter("reg_date2");

    logger.info("paramInfo : " + paramInfo);

		if (!reg_date.equals(TextHandler.getBusinessDate(conn, reg_date)) ) { //������ üũ
		  request.setAttribute("page.mn06.SucMsg",   "�ش����ڴ� �������� �ƴմϴ�.");
		} else if (Integer.parseInt(request.getParameter("reg_date2")) > Integer.parseInt(TextHandler.getCurrentDate())) {  //������� ������ �������� üũ
		  request.setAttribute("page.mn06.SucMsg",   "������ڰ� ���������Դϴ�.");
		} else {

		  CommonEntity etcJanInfo = IR061610.getEtcJanInfo(conn, paramInfo);
		  request.setAttribute("page.mn06.etcJanInfo", etcJanInfo);
		}
  }
}