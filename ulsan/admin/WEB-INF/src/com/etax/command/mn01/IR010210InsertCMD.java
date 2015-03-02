/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	   : IR010210InsertCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-01
* ���α׷�����	 : ���� > ����е��
******************************************************/

package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn01.IR010210;
import com.etax.db.mn01.IR010110;
import com.etax.db.mn01.IR010000;
import com.etax.command.common.TransLogInsert;  
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;

public class IR010210InsertCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR010210InsertCMD.class);	// log4j ����
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {
	
	  CommonEntity paramInfo = new CommonEntity();

    paramInfo.setValue("fis_year",          request.getParameter("fis_year"));		 //ȸ�迬��
    paramInfo.setValue("fis_date",          request.getParameter("fis_date"));		 //ȸ������
    paramInfo.setValue("intype",            request.getParameter("intype"));			 //�Է±���
    paramInfo.setValue("gigwan",            request.getParameter("gigwan"));			 //�������
    paramInfo.setValue("part_code",         request.getParameter("part_code"));		 //�μ�
    paramInfo.setValue("acc_code",          request.getParameter("acc_code"));		 //ȸ���ڵ�(��)

    String[] semok_list = request.getParameterValues("semok_code");
    String[] amt_list = request.getParameterValues("amt");
    String[] cnt_list = request.getParameterValues("cnt");
    
    CommonEntity endChk = IR010000.getDailyEndWork(conn, paramInfo); //���ϸ���üũ

    if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{
      request.setAttribute("page.mn01.FailMsg", "���ϸ����� �Ϸ�� ȸ�����ڴ� ����� �� �����ϴ�.");
    
    } else {
     
      CommonEntity dayChk = IR010000.getEndYear(conn, paramInfo); //������üũ 
        
      if ( Integer.parseInt(request.getParameter("fis_date")) > Integer.parseInt(dayChk.getString("M320_DATEILBAN")) )	{
        request.setAttribute("page.mn01.FailMsg", "��ȿ���� ���� ��¥�Դϴ�. Ȯ�� �� �ٽ� ����ϼ���.");
    
      } else {

        //�αױ�� ����� Ŭ������ �޼ҵ� ȣ��
        TransLogInsert tli = new TransLogInsert();
        tli.execute(request, response, conn);
        paramInfo.setValue("log_no", tli.getLogNo());

		    for (int i=0; i<semok_list.length; i++)	{
	        if ( !"".equals(amt_list[i]) || !"".equals(cnt_list[i])) {
			      paramInfo.setValue("semok_code",        semok_list[i]);	      
		        paramInfo.setValue("amt",               TextHandler.unformatNumber(amt_list[i]));					 
		        paramInfo.setValue("cnt",               cnt_list[i]);	
                if ("1130100".equals(semok_list[i])) {
                    paramInfo.setValue("yeartype",     "Y2");
                } else {
                    paramInfo.setValue("yeartype",     "Y1");
                }

				    logger.info("paramInfo : " + paramInfo);		
              	
            if (IR010210.writeInsert(conn, paramInfo) < 1) {
              throw new ProcessException("E002"); //����� �����޽���
            }
          }
          
        }
        request.setAttribute("page.mn01.SucMsg", "���ó���Ǿ����ϴ�.");	
      }
    }
    
    /* ����Ʈ ��ȸ */
		CommonEntity fis_date  = IR010110.getFisDate(conn);
    request.setAttribute("page.mn01.fis_date", fis_date);  
         
		List<CommonEntity> accDeptList = IR010210.getAccDeptList(conn, paramInfo);
		request.setAttribute("page.mn01.accDeptList", accDeptList);
	 
	  List<CommonEntity> accNmList = IR010210.getAccNmList(conn, paramInfo);
    request.setAttribute("page.mn01.accNmList", accNmList); 

		List<CommonEntity> accSemokList = IR010210.getAccSemokList(conn, paramInfo);
    request.setAttribute("page.mn01.accSemokList", accSemokList); 

		List<CommonEntity> gigwanList = IR010210.getGigwanList(conn);
    request.setAttribute("page.mn01.gigwanList", gigwanList);
	}
} 