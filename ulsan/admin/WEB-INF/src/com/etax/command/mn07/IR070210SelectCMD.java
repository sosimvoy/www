/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR070210SelectCMD.java
* ���α׷��ۼ���    : (��)�̸�����
* ���α׷��ۼ���    :  2010-08-27
* ���α׷�����      : �ϰ�/���� > ���Ͼ�������
***********************************************************************/
package com.etax.command.mn07;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import java.io.*;

import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn07.IR070210;
import com.etax.entity.CommonEntity;
import com.etax.util.StringUtil;
import com.etax.trans.GNBDaemonCall;

public class IR070210SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR070210SelectCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{
/*    
    try{
	   if (GNBDaemonCall.setDaemonShellExecuteCommand("SendJanak","20100907","IPGM","0000") == false){
		   System.out.println("���д�.........");
	   }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception ex) {
      ex.printStackTrace();	
	}
*/
     
      CommonEntity paramInfo = new CommonEntity();
      paramInfo.setValue("acc_year",    request.getParameter("acc_year"));

	 /* ���Ͼ������� ��Ȳ ��ȸ */
    List<CommonEntity> magamList = IR070210.getMagamList(conn, paramInfo);
    request.setAttribute("page.mn07.magamList", magamList);

    CommonEntity dateData = IR070210.getDateData(conn, paramInfo);
        request.setAttribute("page.mn07.dateData", dateData);

  }
}
