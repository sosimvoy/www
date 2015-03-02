/*****************************************************
* ������Ʈ��		: ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���		: IR010710DeleteCMD.java
* ���α׷��ۼ���	: (��)�̸�����
* ���α׷��ۼ���	: 2010-09-08
* ���α׷�����		: ���� > OCR��������/��� > ����
* ���α׷����      : 1. �Է��� ȸ������ ���� ���Լ���� ������ ���� Ȯ��
                      2. �ش� ȸ������ ���Լ���� ����
******************************************************/

package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn01.IR010710;
import com.etax.entity.CommonEntity;

public class IR010710DeleteCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR010710DeleteCMD.class);	// log4j ����
	
	// Connection �� �ʿ��� ���� BaseCommand �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
	
		paramInfo.setValue("input_date",         request.getParameter("input_date"));
		logger.info(paramInfo);

		CommonEntity orcData = IR010710.getOcrCount(conn, paramInfo);

		if(orcData.getLong("TAXIN_CNT") > 0){	// M010_TAXIN_T(���Լ����) ���翩�� Ȯ��
			/* ���� */
			if(IR010710.ocrDelete(conn, paramInfo) < 1 ) {			
				throw new ProcessException("E004"); //������ �����޽��� ǥ��
			}
			request.setAttribute("page.mn01.delMsg", "����ó���Ǿ����ϴ�.");
		}else{
			request.setAttribute("page.mn01.delMsg", "�ش� �۾����ڿ� ������ �����Ͱ� �����ϴ�.");
		}
	}
}