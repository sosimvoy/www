/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR070310SelectCMD.java
* ���α׷��ۼ���    : (��)�̸�����
* ���α׷��ۼ���    : 2010-10-01
* ���α׷�����      : �ϰ�/���� > ȸ��⵵�̿�
* ���α׷����      : ȸ�豸��(A,B,E/D)�� ���� �������ڷ� �ϰ����̺� ������� ��ȸ 
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
import com.etax.db.mn07.IR070310;
import com.etax.entity.CommonEntity;
import com.etax.trans.GNBDaemonCall;

public class IR070310SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR070310SelectCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{
    
        CommonEntity paramInfo = new CommonEntity();
        
        String acc_year     = request.getParameter("acc_year");
        String next_year    = request.getParameter("next_year");
        String acc_type     = request.getParameter("acc_type");
        String end_date     = "";   // ��������(ȸ�豸�к� ��������)

		paramInfo.setValue("acc_year",  acc_year);
		paramInfo.setValue("next_year", next_year);
		paramInfo.setValue("acc_type",  acc_type);
        
         /* 1.������(������) ��ȸ */
        CommonEntity closeDate = IR070310.getCloseDate(conn, paramInfo);
        
        if(acc_type.equals("A")){   // �Ϲ�ȸ��(A)
            end_date = closeDate.getString("M320_DATEILBAN");
        }else if(acc_type.equals("B")){   // Ư��ȸ��(B)
            end_date = closeDate.getString("M320_DATETEKBEYL");
        }else if(acc_type.equals("D")){   // ���Լ��������(D)
            end_date = closeDate.getString("M320_DATEHYUNGEUM");
        }else if(acc_type.equals("E")){   // ���(E)
            end_date = closeDate.getString("M320_DATEGIGEUM");
        }
        
        logger.info("end_date::"+end_date);

		paramInfo.setValue("end_date",  end_date);


        if(acc_type.equals("D")){
             /* 2-1.�̿������ȸ ��Ȳ ��ȸ(D) */
            List<CommonEntity> transList = IR070310.getTransCashList(conn, paramInfo);
            request.setAttribute("page.mn07.transList", transList);
        }else{
             /* 2-2.�̿������ȸ ��Ȳ ��ȸ(A,B,E) */
            List<CommonEntity> transList = IR070310.getTransList(conn, paramInfo);
            request.setAttribute("page.mn07.transList", transList);
        }
    }
}