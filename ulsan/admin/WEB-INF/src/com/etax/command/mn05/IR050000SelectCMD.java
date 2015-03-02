/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR050000SelectCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > �ڱݹ����������
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn05.IR050000;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;


public class IR050000SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR050000SelectCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    
			/* �ڱݹ���������� */
    CommonEntity allotingInfo = IR050000.getAllotingStat(conn);
    request.setAttribute("page.mn05.allotingInfo", allotingInfo);
  }
}