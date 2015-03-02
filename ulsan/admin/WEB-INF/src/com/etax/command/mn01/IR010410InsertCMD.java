/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	   : IR010410InsertCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-05-13
* ���α׷�����	 : ���� > ����е��(���,Ư��ȸ��)
******************************************************/

package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn01.IR010410;
import com.etax.db.mn01.IR010110;
import com.etax.db.mn01.IR010000;
import com.etax.command.common.TransLogInsert; 
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;

public class IR010410InsertCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR010410InsertCMD.class);	// log4j ����
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {
		
    CommonEntity paramInfo = new CommonEntity();
		
    paramInfo.setValue("fis_year",          request.getParameter("fis_year")); //ȸ�迬��
    paramInfo.setValue("fis_date",          request.getParameter("fis_date")); //ȸ������
    paramInfo.setValue("intype",            request.getParameter("intype"));    //�Է±���
		paramInfo.setValue("gigwan",            request.getParameter("gigwan"));  //�������
																																																			 
		String[] acc_list = request.getParameterValues("acc_type");
		String[] part_list = request.getParameterValues("part_code");
		String[] acccd_list = request.getParameterValues("acc_code");
		String[] semok_list = request.getParameterValues("semok_code");
		String[] type_list = request.getParameterValues("year_type");
		String[] amt_list = request.getParameterValues("amt");

		for (int i=0; i<acc_list.length; i++)	{
	    if (!"".equals(part_list[i]) || !"".equals(acccd_list[i]) || !"".equals(semok_list[i]) || !"".equals(type_list[i]) || !"".equals(amt_list[i])) {
				paramInfo.setValue("acc_type",         acc_list[i]);	  
        paramInfo.setValue("part_code",        part_list[i]);	       
				paramInfo.setValue("acc_code",         acccd_list[i]);	
		    paramInfo.setValue("semok_code",       semok_list[i]);	
				paramInfo.setValue("year_type",        type_list[i]);	
				paramInfo.setValue("amt",              TextHandler.unformatNumber(amt_list[i]));					 
				
				logger.info("paramInfo : " + paramInfo);		
    
		    CommonEntity endChk = IR010000.getDailyEndWork(conn, paramInfo); //���ϸ���üũ
	
        if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{
            request.setAttribute("page.mn01.FailMsg", "���ϸ����� �Ϸ�� ȸ�����ڴ� ����� �� �����ϴ�.");   
        
        } else {
            
            CommonEntity dayChk = IR010000.getEndYear(conn, paramInfo); //������üũ

              if ("B".equals(request.getParameter("acc_type")))	{   
                
                if (Integer.parseInt(request.getParameter("fis_date")) > Integer.parseInt(dayChk.getString("M320_DATETEKBEYL"))) {
                    request.setAttribute("page.mn01.FailMsg", "��ȿ���� ���� ��¥�Դϴ�. Ȯ�� �� �ٽ� ����ϼ���.");   
                
                } else {
                    //�αױ�� ����� Ŭ������ �޼ҵ� ȣ��
                    TransLogInsert tli = new TransLogInsert();
                    tli.execute(request, response, conn);
                    paramInfo.setValue("log_no", tli.getLogNo());
                    
                    if (IR010410.writeSpecial(conn, paramInfo) < 1) {
                      throw new ProcessException("E002"); //����� �����޽���
                    }
                      request.setAttribute("page.mn01.SucMsg", "���ó���Ǿ����ϴ�.");
                }
              }

              if ("E".equals(request.getParameter("acc_type")))	{ 
                
                if (Integer.parseInt(request.getParameter("fis_date")) > Integer.parseInt(dayChk.getString("M320_DATEGIGEUM"))) {
                    request.setAttribute("page.mn01.FailMsg", "��ȿ���� ���� ��¥�Դϴ�. Ȯ�� �� �ٽ� ����ϼ���.");               
                
                } else {
                    //�αױ�� ����� Ŭ������ �޼ҵ� ȣ��
                    TransLogInsert tli = new TransLogInsert();
                    tli.execute(request, response, conn);
                    paramInfo.setValue("log_no", tli.getLogNo());
                    
                    if (IR010410.writeSpecial(conn, paramInfo) < 1) {
                      throw new ProcessException("E002"); //����� �����޽���
                    }
                      request.setAttribute("page.mn01.SucMsg", "���ó���Ǿ����ϴ�.");
                }
              }
        }
      }
		}

    /* ����Ʈ ��ȸ */
		CommonEntity fis_date  = IR010110.getFisDate(conn);
    request.setAttribute("page.mn01.fis_date", fis_date); 
		
		List<CommonEntity> specDeptList = IR010410.getSpecDeptList(conn, paramInfo);
		request.setAttribute("page.mn01.specDeptList", specDeptList);
		
		List<CommonEntity> specNameList = IR010410.getSpecNameList(conn, paramInfo);
		request.setAttribute("page.mn01.specNameList", specNameList);

		List<CommonEntity> specSemokList = IR010410.getSpecSemokList(conn, paramInfo);
		request.setAttribute("page.mn01.specSemokList", specSemokList);

		List<CommonEntity> gigwanList = IR010410.getGigwanList(conn);
    request.setAttribute("page.mn01.gigwanList", gigwanList);
	}
} 