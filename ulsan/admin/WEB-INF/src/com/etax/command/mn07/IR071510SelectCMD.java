/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR071510SelectCMD.java
* ���α׷��ۼ���    : (��)�̸�����
* ���α׷��ۼ���    : 2010-10-01
* ���α׷�����      : �ϰ�/���� > ���Լ����ϰ�����(�ұ�ó��) ��ȸ
***********************************************************************/
package com.etax.command.mn07;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;

import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn07.IR071510;
import com.etax.entity.CommonEntity;

public class IR071510SelectCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR071510SelectCMD.class); // log4j ����

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{
    
        CommonEntity paramInfo = new CommonEntity();
        paramInfo.setValue("acc_year",      request.getParameter("acc_year"));  //ȸ�迬��
        paramInfo.setValue("acc_date_s",      request.getParameter("acc_date_s"));  //ȸ������
        paramInfo.setValue("acc_date_e",      request.getParameter("acc_date_e"));  //���Լ��ⱸ��
        
        logger.debug("paramInfo : " + paramInfo);
        //��û������ȸ
        List<CommonEntity> sechulList = IR071510.getSechulList(conn, paramInfo);
        request.setAttribute("page.mn07.sechulList", sechulList);
    }
}