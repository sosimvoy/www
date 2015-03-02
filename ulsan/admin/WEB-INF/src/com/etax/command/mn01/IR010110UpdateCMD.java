/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	   : IR010110UpdateCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-01
* ���α׷�����	 : ���� > ȸ�����ڵ��
******************************************************/

package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn01.IR010110;
import com.etax.entity.CommonEntity;

public class IR010110UpdateCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR010110UpdateCMD.class);	// log4j ����
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {
		CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("fis_date", request.getParameter("fis_date"));
   
    logger.info("paramInfo : " + paramInfo);		

		if(IR010110.updateFisDate(conn, paramInfo) < 1 ) {
			throw new ProcessException("E002"); //����� �����޽��� ǥ��
		}
	}
} 