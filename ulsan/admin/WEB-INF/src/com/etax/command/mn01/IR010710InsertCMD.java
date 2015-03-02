/*****************************************************
* ������Ʈ��		: ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���		: IR010710InsertCMD.java
* ���α׷��ۼ���	: (��)�̸�����
* ���α׷��ۼ���	: 2010-09-08
* ���α׷�����		: ���� > OCR��������/��� > ó��(���)
* ���α׷����      : 1. �Է��� ȸ������ ���� ���Լ����&���ܼ���OCR�ڷ� ������ ���� Ȯ��(ȸ���� ���� ���Ͻ� ���� �Ұ�)
                      2. �ش� ȸ������ ���Լ���� ���
******************************************************/

package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn01.IR010710;
import com.etax.command.common.TransLogInsert;  //�αױ�� ����� ���� import
import com.etax.entity.CommonEntity;

public class IR010710InsertCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR010710InsertCMD.class);	// log4j ����
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {
	
	  CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("work_log",      request.getParameter("work_log"));			//��������
		paramInfo.setValue("trans_gubun",   request.getParameter("trans_gubun"));		//�ŷ�����
		paramInfo.setValue("input_date",	request.getParameter("input_date"));		//�۾�����
		
		
        /*
		CommonEntity orcData = IR010710.getOcrCount(conn, paramInfo);

		if(orcData.getLong("TAXIN_CNT") == 0){	// M010_TAXIN_T(���Լ����) ���翩�� Ȯ��
			if(orcData.getLong("ETC_CNT") > 0){	// ETC_T(���ܼ���OCR�ڷ�) ���翩�� Ȯ��
				
				//�αױ�� ����� Ŭ������ �޼ҵ� ȣ��
				TransLogInsert tli = new TransLogInsert();
				tli.execute(request, response, conn);
				paramInfo.setValue("log_no",		tli.getLogNo());	// �α׹�ȣ
		
				if (IR010710.ocrInsert(conn, paramInfo) < 1) {
					throw new ProcessException("E002"); //����� �����޽���
				}
				request.setAttribute("page.mn01.insMsg", "ó�� �Ǿ����ϴ�.");
			}else{
				request.setAttribute("page.mn01.insMsg", "�ش� �۾������� OCR�ڷᰡ �����ϴ�.");
			}
		}else{
			request.setAttribute("page.mn01.insMsg", "�ش� �۾����ڴ� �̹� ó�� �Ǿ����ϴ�.");
		}
        */ 
        //2011-12-21 �߰�OCRó���� ���� ���� ���� by ������
	    //�αױ�� ����� Ŭ������ �޼ҵ� ȣ��
		CommonEntity magamData = IR010710.getMagamCount(conn, paramInfo);
		if(magamData.getLong("MAGAM_CNT") > 0){	// �������� üũ
          request.setAttribute("page.mn01.insMsg", "�۾����ڴ� ������ �����Դϴ�.");
        } else {
		  TransLogInsert tli = new TransLogInsert();
		  tli.execute(request, response, conn);
		  paramInfo.setValue("log_no",		tli.getLogNo());	// �α׹�ȣ
		  int intcnt = IR010710.ocrInsert(conn, paramInfo);
		  request.setAttribute("page.mn01.insMsg", intcnt + "�� ó�� �Ǿ����ϴ�.");
        }
	}
} 