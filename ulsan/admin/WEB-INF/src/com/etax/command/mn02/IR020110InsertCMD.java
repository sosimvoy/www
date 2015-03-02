/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR020110InsertCMD.java
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-01
* ���α׷�����	   : ���� > ����е��
****************************************************************/

package com.etax.command.mn02;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn02.IR020110;
import com.etax.db.mn01.IR010110;
import com.etax.db.mn02.IR020000;
import com.etax.command.common.TransLogInsert; 
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;

public class IR020110InsertCMD extends BaseCommand {
 
	private static Logger logger = Logger.getLogger(IR020110InsertCMD.class);
	
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    CommonEntity paramInfo = new CommonEntity();

		  paramInfo.setValue("fis_year",               request.getParameter("fis_year"));
			paramInfo.setValue("fis_date",               request.getParameter("fis_date"));
			paramInfo.setValue("acc_type",               request.getParameter("acc_type"));
		  paramInfo.setValue("part_code",              request.getParameter("part_code"));									 
			paramInfo.setValue("acc_code",               request.getParameter("acc_code"));
		  paramInfo.setValue("semok_code",             request.getParameter("semok_code"));
			paramInfo.setValue("intype",                 request.getParameter("intype"));
			paramInfo.setValue("order_name",             request.getParameter("order_name"));
			paramInfo.setValue("order_no",               request.getParameter("order_no"));
			paramInfo.setValue("amt",                    TextHandler.unformatNumber(request.getParameter("amt")));

      logger.info("paramInfo : " + paramInfo);		
    
			CommonEntity endChk = IR020000.getDailyEndWork(conn, paramInfo); //���ϸ���üũ
      
        if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{
          request.setAttribute("page.mn02.FailMsg", "���ϸ����� �Ϸ�� ȸ�����ڴ� ����� �� �����ϴ�.");
		    
        } else {

            CommonEntity dayChk = IR020000.getEndYear(conn, paramInfo); //������üũ

              if ("A".equals(request.getParameter("acc_type")))	{  //�Ϲ�ȸ��
            
                if ( Integer.parseInt(request.getParameter("fis_date")) > Integer.parseInt(dayChk.getString("M320_DATEILBAN")) )	{
                    request.setAttribute("page.mn02.FailMsg", "��ȿ���� ���� ��¥�Դϴ�. Ȯ�� �� �ٽ� ����ϼ���.");

                } else {

                    CommonEntity noData = IR020110.getOrderNo(conn, paramInfo);   //���޹�ȣ �ߺ� ����

                       if(noData.getLong("NO_CNT") == 0){
                       
                         //�αױ�� ����� Ŭ������ �޼ҵ� ȣ��
                         TransLogInsert tli = new TransLogInsert();
                         tli.execute(request, response, conn);
                         paramInfo.setValue("log_no", tli.getLogNo());
                         
                         if(IR020110.revWriteInsert(conn, paramInfo) < 1 ) {
                           throw new ProcessException("E002"); //����� �����޽��� ǥ��
                         }
       
                       } else {
                             request.setAttribute("page.mn02.FailMsg", "���޹�ȣ�� �ߺ��˴ϴ�. �ٽ� ����ϼ���.");	
         
                       }
                }
              }
                

              if ("B".equals(request.getParameter("acc_type")))	{  //Ư��ȸ��
            
                if ( Integer.parseInt(request.getParameter("fis_date")) > Integer.parseInt(dayChk.getString("M320_DATETEKBEYL")) )	{
                    request.setAttribute("page.mn02.FailMsg", "��ȿ���� ���� ��¥�Դϴ�. Ȯ�� �� �ٽ� ����ϼ���.");

                } else {

                    CommonEntity noData = IR020110.getOrderNo(conn, paramInfo);   //���޹�ȣ �ߺ� ����

                       if(noData.getLong("NO_CNT") == 0){
                       
                         //�αױ�� ����� Ŭ������ �޼ҵ� ȣ��
                         TransLogInsert tli = new TransLogInsert();
                         tli.execute(request, response, conn);
                         paramInfo.setValue("log_no", tli.getLogNo());
                         
                         if(IR020110.revWriteInsert(conn, paramInfo) < 1 ) {
                           throw new ProcessException("E002"); //����� �����޽��� ǥ��
                         }
       
                       } else {
                             request.setAttribute("page.mn02.FailMsg", "���޹�ȣ�� �ߺ��˴ϴ�. �ٽ� ����ϼ���.");
                       }
                }       
              }


              if ("E".equals(request.getParameter("acc_type")))	{   //���
            
                if ( Integer.parseInt(request.getParameter("fis_date")) > Integer.parseInt(dayChk.getString("M320_DATEGIGEUM")) )	{
                    request.setAttribute("page.mn02.FailMsg", "��ȿ���� ���� ��¥�Դϴ�. Ȯ�� �� �ٽ� ����ϼ���.");

                } else {

                    CommonEntity noData = IR020110.getOrderNo(conn, paramInfo);   //���޹�ȣ �ߺ� ����

                       if(noData.getLong("NO_CNT") == 0){
                       
                         //�αױ�� ����� Ŭ������ �޼ҵ� ȣ��
                         TransLogInsert tli = new TransLogInsert();
                         tli.execute(request, response, conn);
                         paramInfo.setValue("log_no", tli.getLogNo());
                       
                         if(IR020110.revWriteInsert(conn, paramInfo) < 1 ) {
                           throw new ProcessException("E002"); //����� �����޽��� ǥ��
                         }
         
                       } else {
                             request.setAttribute("page.mn02.FailMsg", "���޹�ȣ�� �ߺ��˴ϴ�. �ٽ� ����ϼ���.");	
                       }
                }       
              }
			       
           
        }

		/* ����Ʈ ��ȸ */
    CommonEntity fis_date  = IR010110.getFisDate(conn);
    request.setAttribute("page.mn01.fis_date", fis_date); 

		List<CommonEntity> revDeptList = IR020110.getRevDeptList(conn, paramInfo);
    request.setAttribute("page.mn02.revDeptList", revDeptList); 

		List<CommonEntity> revAccNmList = IR020110.getRevAccNmList(conn, paramInfo);
    request.setAttribute("page.mn02.revAccNmList", revAccNmList); 

		List<CommonEntity> revSemokList = IR020110.getRevSemokList(conn, paramInfo);
    request.setAttribute("page.mn02.revSemokList", revSemokList);
	}
}