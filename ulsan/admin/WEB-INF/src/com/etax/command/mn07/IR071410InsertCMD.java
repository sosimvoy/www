/**********************************************************************
* ������Ʈ��		  : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���		  : IR071410InsertCMD.java
* ���α׷��ۼ���	: (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����   : �ϰ�/���� > ���Լ����ϰ�����(�ұ�ó��)
***********************************************************************/
package com.etax.command.mn07;

import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;
import java.util.*;
import jxl.*;
import jxl.write.*;
import com.oreilly.servlet.MultipartRequest;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.entity.CommonEntity;
import com.etax.config.ETaxConfig;
import com.etax.db.mn07.IR071410;
import com.etax.util.TextHandler;

public class IR071410InsertCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR071410InsertCMD.class);

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {
   
		CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("acc_year",    request.getParameter("acc_year"));
		paramInfo.setValue("acc_date",    request.getParameter("acc_date"));
		paramInfo.setValue("tax_type",   request.getParameter("tax_type"));
    if ("T1".equals(paramInfo.getString("tax_type")) ) {
      paramInfo.setValue("work_gubun",    "0");  //��������
    } else if ("T2".equals(paramInfo.getString("tax_type")) ) {
      paramInfo.setValue("work_gubun",    "1");  //��������
    }
		paramInfo.setValue("rev_cd",        request.getParameter("rev_cd"));      //�������
    paramInfo.setValue("acc_type",      request.getParameter("acc_type"));    //ȸ�豸��
    paramInfo.setValue("part_code",     request.getParameter("part_code"));   //�μ��ڵ�
    paramInfo.setValue("semok_code",    request.getParameter("semok_code"));  //�����ڵ�
    paramInfo.setValue("acc_code",      request.getParameter("acc_code"));    //ȸ���ڵ�
    paramInfo.setValue("input_type",    request.getParameter("input_type"));  //�Է±���
    paramInfo.setValue("year_type",     request.getParameter("year_type"));   //�⵵����
    paramInfo.setValue("add_type",      request.getParameter("add_type"));    //�ݾ���������
    paramInfo.setValue("amt",           request.getParameter("amt"));         //�ݾ�

    String SucMsg = "";  //�޽���

    CommonEntity dailyMax = IR071410.getDailyMax(conn, paramInfo);
    
    if (Integer.parseInt(paramInfo.getString("acc_date")) > Integer.parseInt(TextHandler.getCurrentDate()) ) {
      //ȸ�����ڴ� ���Ϻ��� �۾ƾ� �Ѵ�.
      SucMsg = "ȸ�����ڴ� ���Ϻ��� �۾ƾ��մϴ�.";
    } else if (Integer.parseInt(paramInfo.getString("acc_year")) > Integer.parseInt(paramInfo.getString("acc_date").substring(0,4)) ) {
      //ȸ�������� ������ ȸ�迬������ ũ�ų� ���ƾ� �Ѵ�.
      SucMsg = "ȸ�������� ������ ȸ�迬������ ũ�ų� ���ƾ� �մϴ�.";
    } else if (Integer.parseInt(paramInfo.getString("acc_date").substring(0,4)) - Integer.parseInt(paramInfo.getString("acc_year")) == 1
      && Integer.parseInt(paramInfo.getString("acc_date").substring(4,8)) > 310) {
      //ȸ�迬���� ���⵵�̰� ȸ�������� ������ ���⵵�̸鼭 ���ڰ� 3�� 10�� ������ ��
      SucMsg = "���⵵ ���� �ÿ��� ȸ�����ڰ� 3�� 10�� ���İ� �Ǽ��� �ȵ˴ϴ�.";
    } else if (!paramInfo.getString("acc_date").equals(TextHandler.getBusinessDate(conn, paramInfo.getString("acc_date"))) ) {
      //ȸ������ ������ üũ
      SucMsg = "ȸ�����ڰ� �������� �ƴմϴ�.";
    } else if (Integer.parseInt(dailyMax.getString("M210_DATE")) < Integer.parseInt(paramInfo.getString("acc_date")) ) {
      SucMsg = "ȸ�����ڰ� �������ں��� Ů�ϴ�.";
    } else {
      CommonEntity recode = IR071410.getRecord(conn, paramInfo);  //ȸ������ �ڷ� ���� ����
      if (recode.size() > 0) {  //�ϰ��ڷᰡ ������ ��
        IR071410.dateNoteUpdate(conn, paramInfo);
        IR071410.dateNoteUpdateProc(conn, paramInfo);
        SucMsg = "���� ������ �Ǿ����ϴ�.";
      } else if (recode.size() == 0) {
        IR071410.dateNoteInsert(conn, paramInfo);
        IR071410.dateNoteInsertProc(conn, paramInfo);
        SucMsg = "���� �Է��� �Ǿ����ϴ�.";
      }
    }
    
    //��������ڵ�
    List<CommonEntity> revCdList = IR071410.getRevCdList(conn, paramInfo);
    request.setAttribute("page.mn07.revCdList", revCdList);

    //�μ��ڵ�
    List<CommonEntity> partCdList = IR071410.getPartCdList(conn, paramInfo);
    request.setAttribute("page.mn07.partCdList", partCdList);


    //ȸ���ڵ�
    List<CommonEntity> accCdList = IR071410.getAccCdList(conn, paramInfo);
    request.setAttribute("page.mn07.accCdList", accCdList);


     //�����ڵ�
    List<CommonEntity> semokCdList = IR071410.getSemokCdList(conn, paramInfo);
    request.setAttribute("page.mn07.semokCdList", semokCdList);


    request.setAttribute("page.mn07.SucMsg",   SucMsg);
  }
}