/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR091010DeleteCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-21
* ���α׷�����   : �ý��ۿ > ȸ�����ڵ� ���
****************************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR091210;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR091210DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR091210DeleteCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    
		CommonEntity paramInfo = new CommonEntity();
    
		paramInfo.setValue("year",     request.getParameter("year"));
	  
		CommonEntity codeCnt = IR091210.getYearCode(conn, paramInfo);
     logger.info("paramInfo : " + paramInfo);
		 
		 if (!"0".equals(codeCnt.getString("CNT_1")) )	{
			if (IR091210.deleteEndYearCode(conn, paramInfo) < 1) {
		    throw new ProcessException("E004"); //������ �����޽��� ǥ��
		  }
		 }
		 if (!"0".equals(codeCnt.getString("CNT_2")) )	{
    	if (IR091210.deleteEndYearCode2(conn, paramInfo) < 1) {
		    throw new ProcessException("E004"); //������ �����޽��� ǥ��
		  }
		 }
		 if (!"0".equals(codeCnt.getString("CNT_3")) )	{
			if (IR091210.deleteEndYearCode3(conn, paramInfo) < 1) {
		    throw new ProcessException("E004"); //������ �����޽��� ǥ��
		  }
		 }
		 if (!"0".equals(codeCnt.getString("CNT_4")) )	{
			if (IR091210.deleteEndYearCode4(conn, paramInfo) < 1) {
		    throw new ProcessException("E004"); //������ �����޽��� ǥ��
		  }
		 }
		 if (!"0".equals(codeCnt.getString("CNT_5")) )	{
			if (IR091210.deleteEndYearCode5(conn, paramInfo) < 1) {
		    throw new ProcessException("E004"); //������ �����޽��� ǥ��
		  }
		 }
		 if (!"0".equals(codeCnt.getString("CNT_6")) )	{
			if (IR091210.deleteEndYearCode6(conn, paramInfo) < 1) {
		    throw new ProcessException("E004"); //������ �����޽��� ǥ��
		  }
		 }
		 if (!"0".equals(codeCnt.getString("CNT_7")) )	{
			 IR091210.deleteEndYearCode7(conn, paramInfo);
		 }
         //ǥ���ڵ� �׸� �߰��� ���� ������
        //�ѿ��� 2012.01.04
        if (!"0".equals(codeCnt.getString("CNT_11")) )	{ //����
		    if (IR091210.deleteEndYearCode11(conn, paramInfo) < 1) {
                throw new ProcessException("E004"); //������ �����޽��� ǥ��
            }
		}
		if (!"0".equals(codeCnt.getString("CNT_12")) )	{  //�μ�
			if (IR091210.deleteEndYearCode12(conn, paramInfo) < 1) {
                throw new ProcessException("E004"); //������ �����޽��� ǥ��
            }
		}
    //�����ڵ� �׸� �߰��� ���� ������
    //������ 2010.11.10
    if (!"0".equals(codeCnt.getString("CNT_8")) )	{
			   if (IR091210.deleteEndYearCode8(conn, paramInfo) < 1) {
           throw new ProcessException("E004"); //������ �����޽��� ǥ��
         }
		}
		if (!"0".equals(codeCnt.getString("CNT_9")) )	{
			   if (IR091210.deleteEndYearCode9(conn, paramInfo) < 1) {
           throw new ProcessException("E004"); //������ �����޽��� ǥ��
         }
		}
        //�����ڵ����̺� ȸ�迬�� �߰��� ���� ������
        //�ѿ��� 2010.12.30
        if (!"0".equals(codeCnt.getString("CNT_10")) )	{
		    if (IR091210.deleteEndYearCode10(conn, paramInfo) < 1) {
                throw new ProcessException("E004"); //������ �����޽��� ǥ��
            }
		}

		List endYearCode = IR091210.getEndYearCode(conn, paramInfo);
		request.setAttribute("page.mn09.endYearCode", endYearCode);

		request.setAttribute("page.mn09.SucMsg", "�����Ǿ����ϴ�.");
  }
}
