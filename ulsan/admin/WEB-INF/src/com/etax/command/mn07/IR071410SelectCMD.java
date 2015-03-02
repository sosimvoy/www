/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR071410SelectCMD.java
* ���α׷��ۼ���    : (��)�̸�����
* ���α׷��ۼ���    : 2010-10-01
* ���α׷�����      : �ϰ�/���� > ���Լ����ϰ�����(�ұ�ó��) ��ȸ
***********************************************************************/
package com.etax.command.mn07;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import java.io.*;

import org.apache.log4j.Logger;

import com.etax.util.*;
import com.etax.framework.command.BaseCommand;
import com.etax.db.mn07.IR071410;
import com.etax.entity.CommonEntity;

public class IR071410SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR071410SelectCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{
    
    CommonEntity paramInfo = new CommonEntity();

    String SucMsg = "";  //�޽���

    paramInfo.setValue("acc_year",      request.getParameter("acc_year"));  //ȸ�迬��
    paramInfo.setValue("acc_date",      request.getParameter("acc_date"));  //ȸ������
		paramInfo.setValue("tax_type",      request.getParameter("tax_type"));  //���Լ��ⱸ��
		paramInfo.setValue("input_type",    request.getParameter("input_type"));  //�Է±���
		paramInfo.setValue("add_type",      request.getParameter("add_type"));  //��������
		paramInfo.setValue("amt",           request.getParameter("amt"));       //�ݾ�
		paramInfo.setValue("year_type",     request.getParameter("year_type")); //�⵵����
    if ("".equals(paramInfo.getString("tax_type")) ) { 
      paramInfo.setValue("work_gubun",    "0");  //��������
    } else if ("T1".equals(paramInfo.getString("tax_type")) ) {
      paramInfo.setValue("work_gubun",    "0");  //��������
    } else if ("T2".equals(paramInfo.getString("tax_type")) ) {
      paramInfo.setValue("work_gubun",    "1");  //��������
    }
		paramInfo.setValue("rev_cd",        request.getParameter("rev_cd"));    //�������
    paramInfo.setValue("acc_type",      request.getParameter("acc_type"));  //ȸ�豸��
    paramInfo.setValue("part_code",     request.getParameter("part_code")); //�μ��ڵ�
    paramInfo.setValue("acc_code",      request.getParameter("acc_code")); //ȸ���ڵ�
    paramInfo.setValue("gubun",         request.getParameter("gubun")); //����
    
    if ("".equals(paramInfo.getString("acc_code")) && "".equals(paramInfo.getString("acc_year"))) {
      paramInfo.setValue("acc_code",   "11");  //ȸ���ڵ�
    } else if ("".equals(paramInfo.getString("acc_code")) && "A".equals(paramInfo.getString("acc_type")) ) {
      if ("T1".equals(paramInfo.getString("tax_type")) ) {
        paramInfo.setValue("acc_code",   "11");  //ȸ���ڵ�
      } else if ("T2".equals(paramInfo.getString("tax_type")) ) {
        paramInfo.setValue("acc_code",   "16");  //ȸ���ڵ�
      } 
    } else if ("".equals(paramInfo.getString("acc_code")) && "B".equals(paramInfo.getString("acc_type")) ) {
      paramInfo.setValue("acc_code",   "04");  //ȸ���ڵ�
    } else if ("".equals(paramInfo.getString("acc_code")) && "E".equals(paramInfo.getString("acc_type")) ) {
      paramInfo.setValue("acc_code",   "01");  //ȸ���ڵ�
    }

    if ("C".equals(paramInfo.getString("gubun"))) {
      CommonEntity accCd = IR071410.getAccCd(conn, paramInfo);
      paramInfo.setValue("acc_code",   accCd.getString("ACC_CD"));
    }

    if ("".equals(paramInfo.getString("acc_year")) ) {
      paramInfo.setValue("acc_year",    TextHandler.getCurrentDate().substring(0,4));  //ȸ�迬��
    }

    if ("".equals(paramInfo.getString("acc_type")) ) {
      paramInfo.setValue("acc_type",    "A");  //ȸ�豸��
    }

    if ("".equals(paramInfo.getString("part_code")) ) {
      paramInfo.setValue("part_code",    "00000");  //�μ��ڵ�
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