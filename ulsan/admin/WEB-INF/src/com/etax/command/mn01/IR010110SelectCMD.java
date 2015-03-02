/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	   : IR010110InsertCMD.java
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
import com.etax.util.TextHandler;

public class IR010110SelectCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR010110SelectCMD.class);	// log4j ����
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {

    /* ����Ʈ ��ȸ */
    CommonEntity fis_date  = IR010110.getFisDate(conn);
    request.setAttribute("page.mn01.fis_date", fis_date); 
	}
}