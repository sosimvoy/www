/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	   : IR091120InsertCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-21
* ���α׷�����	 : �ý��ۿ > �����ڵ���
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;
import com.oreilly.servlet.MultipartRequest;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR091210;
import com.etax.entity.CommonEntity;

public class IR091210InsertCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR091210InsertCMD.class);	// log4j ����
	// Connection �� �ʿ��� ���� BaseCommand �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

        CommonEntity paramInfo = new CommonEntity();

	    paramInfo.setValue("year",         request.getParameter("year"));
	 
   	    CommonEntity codeCnt = IR091210.getYearCode(conn, paramInfo);
        logger.info("paramInfo : " + paramInfo);
     
		if ("0".equals(codeCnt.getString("CNT_1")) )	{
			if(IR091210.insertEndYearCode(conn, paramInfo) < 1 ) {
		        throw new ProcessException("E002"); //������ �����޽��� ǥ��
		    }
		}
		if ("0".equals(codeCnt.getString("CNT_2")) )	{
			if(IR091210.insertEndYearCode2(conn, paramInfo) < 1 ) {
		        throw new ProcessException("E002"); //������ �����޽��� ǥ��
			}
		}
		if ("0".equals(codeCnt.getString("CNT_3")) )	{
			if(IR091210.insertEndYearCode3(conn, paramInfo) < 1 ) {
		        throw new ProcessException("E002"); //������ �����޽��� ǥ��
			}
		}
		if ("0".equals(codeCnt.getString("CNT_4")) )	{
			if(IR091210.insertEndYearCode4(conn, paramInfo) < 1 ) {
		        throw new ProcessException("E002"); //������ �����޽��� ǥ��
			}
		}
		if ("0".equals(codeCnt.getString("CNT_5")) )	{
			if(IR091210.insertEndYearCode5(conn, paramInfo) < 1 ) {
		        throw new ProcessException("E002"); //������ �����޽��� ǥ��
			}
		}
		if ("0".equals(codeCnt.getString("CNT_6")) )	{
			if(IR091210.insertEndYearCode6(conn, paramInfo) < 1 ) {
		        throw new ProcessException("E002"); //������ �����޽��� ǥ��
			}
		}
		if ("0".equals(codeCnt.getString("CNT_7")) )	{
			if(IR091210.insertEndYearCode7(conn, paramInfo) < 1) {
                throw new ProcessException("E002");
            }
		}
        //ǥ���ڵ� �׸� �߰��� ���� ������
        //�ѿ��� 2012.01.04
		if ("0".equals(codeCnt.getString("CNT_11")) )	{  //����
			if (IR091210.insertEndYearCode11(conn, paramInfo) < 1) {
                throw new ProcessException("E002");
            }
		}
		if ("0".equals(codeCnt.getString("CNT_12")) )	{  //�μ�
			if(IR091210.insertEndYearCode12(conn, paramInfo) < 1) {
                throw new ProcessException("E002");
            }
		}
        //�����ڵ� �׸� �߰��� ���� ������
        //������ 2010.11.10
		if ("0".equals(codeCnt.getString("CNT_8")) )	{
			if (IR091210.insertEndYearCode8(conn, paramInfo) < 1) {
                throw new ProcessException("E002");
            }
		}
		if ("0".equals(codeCnt.getString("CNT_9")) )	{
			if(IR091210.insertEndYearCode9(conn, paramInfo) < 1) {
                throw new ProcessException("E002");
            }
		}
        
        //�����ڵ����̺� ȸ�迬�� �߰��� ���� ������
        //�ѿ��� 2010.12.30
        if ("0".equals(codeCnt.getString("CNT_10")) )	{
			if(IR091210.insertEndYearCode10(conn, paramInfo) < 1 ) {
                throw new ProcessException("E002");
            }
		}

	    List<CommonEntity> endYearCode = IR091210.getEndYearCode(conn, paramInfo);
		request.setAttribute("page.mn09.endYearCode", endYearCode);

		request.setAttribute("page.mn09.SucMsg", "��ϵǾ����ϴ�.");
	}
}
