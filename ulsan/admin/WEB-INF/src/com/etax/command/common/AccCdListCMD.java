/*****************************************************
* ������Ʈ��		  : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���		  : AccCdListCMD.java
* ���α׷��ۼ���	: 
* ���α׷��ۼ���	: 2010-07-14
* ���α׷�����		: ���� > ȸ���ڵ� ��ȸ
******************************************************/

package com.etax.command.common;

import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;
import com.etax.framework.command.BaseCommand;
import com.etax.db.common.SelectBox;
import com.etax.entity.CommonEntity;

public class AccCdListCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(AccCdListCMD.class);	// log4j ����
	
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("part_code", request.getParameter("part_code"));

		/* ȸ���ڵ� ��ȸ */
		List<CommonEntity> accCdList = SelectBox.getAccNameList(conn,paramInfo);
		request.setAttribute("page.common.acctCdList", accCdList);
	}
}