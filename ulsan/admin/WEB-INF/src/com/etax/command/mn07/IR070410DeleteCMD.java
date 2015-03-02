/********************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR070410DeleteCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-21
* ���α׷�����   : �ϰ�/���� > ���Ϻ������
********************************************************/

package com.etax.command.mn07;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn07.IR070410;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;


public class IR070410DeleteCMD extends BaseCommand {
    private static Logger logger = Logger.getLogger(IR070410DeleteCMD.class); // log4j ����
    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 
	  
	   /* (��ȸ��)�Ķ���� ���� */
        CommonEntity paramInfo = new CommonEntity();
	  
	    paramInfo.setValue("acc_year",   request.getParameter("acc_year"));
        paramInfo.setValue("acc_date",   request.getParameter("acc_date"));

        String acc_date = request.getParameter("acc_date");
        String acc_year = request.getParameter("acc_year");

        String SucMsg = ""; //�޽���

        if (!acc_date.equals(TextHandler.getBusinessDate(conn, acc_date)) ) {
            SucMsg = "ȸ�����ڰ� �������Դϴ�. ȸ�����ڴ� �ݵ�� �������̾�� �մϴ�.";
        } else {
            if (IR070410.deleteNote(conn, paramInfo) < 1) {
                SucMsg = "�ش��ϴ� ȸ�����ڿ� �ڷᰡ �������� �ʽ��ϴ�.";
            } else {
                SucMsg = TextHandler.formatDate(acc_date, "yyyyMMdd", "yyyy-MM-dd") + " �ڷᰡ �����Ǿ����ϴ�.";
            }

            //�ش� ȸ������ �ڷ� ��ȸ
            CommonEntity dateNote = IR070410.getDateNote(conn, paramInfo);
            request.setAttribute("page.mn07.dateNote", dateNote);
            
            String f_date = TextHandler.getBusinessDate(conn, acc_year+"0101");
            logger.info("���ʿ����� : " + f_date);
            if (f_date.equals(acc_date)) { //���� �������� ��� ��� �ڷ� 0���� ��������
                //�� ������ �ڷ� ��ȸ
                CommonEntity agoNote = IR070410.getAgoNote1(conn, paramInfo);
                request.setAttribute("page.mn07.agoNote", agoNote);

            } else {
                //�� ������ �ڷ� ��ȸ
                CommonEntity agoNote = IR070410.getAgoNote(conn, paramInfo);
                request.setAttribute("page.mn07.agoNote", agoNote);
            }

        }
        request.setAttribute("page.mn07.SucMsg", SucMsg);
    }
}
