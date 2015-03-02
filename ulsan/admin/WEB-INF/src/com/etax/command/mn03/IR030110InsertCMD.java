/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR030110InsertCMD.java
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-01
* ���α׷�����	   : ���Լ�������� > ����е��
****************************************************************/

package com.etax.command.mn03;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn03.IR030110;
import com.etax.db.mn01.IR010110;
import com.etax.db.mn03.IR030000;
import com.etax.command.common.TransLogInsert; 
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR030110InsertCMD extends BaseCommand {
 
    private static Logger logger = Logger.getLogger(IR030110InsertCMD.class);
	
    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {
  
        CommonEntity paramInfo = new CommonEntity();

        paramInfo.setValue("fis_year",        request.getParameter("fis_year"));
        paramInfo.setValue("fis_date",        request.getParameter("fis_date"));
        paramInfo.setValue("part_code",       request.getParameter("part_code"));
        paramInfo.setValue("acc_code",        request.getParameter("acc_code"));
        paramInfo.setValue("dwtype",          request.getParameter("dwtype"));
        paramInfo.setValue("intype",          request.getParameter("intype"));
        paramInfo.setValue("cash_type",       request.getParameter("cash_type"));
        paramInfo.setValue("deposit_type",    request.getParameter("deposit_type"));
        paramInfo.setValue("order_name",      request.getParameter("order_name"));
        paramInfo.setValue("note",            request.getParameter("note"));
        paramInfo.setValue("order_no",        request.getParameter("order_no"));
        paramInfo.setValue("cnt",             request.getParameter("cnt"));
        paramInfo.setValue("amt",             TextHandler.unformatNumber(request.getParameter("amt")));
        
        logger.info("paramInfo : " + paramInfo);
    
        CommonEntity endChk = IR030000.getDailyEndWork(conn, paramInfo); //���ϸ���üũ
        
        if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{
            request.setAttribute("page.mn03.FailMsg", "���ϸ����� �Ϸ�� ȸ�����ڴ� ����� �� �����ϴ�.");
            
        } else {

            CommonEntity dayChk = IR030000.getEndYear(conn, paramInfo); //������üũ

            if ( Integer.parseInt(request.getParameter("fis_date")) > Integer.parseInt(dayChk.getString("M320_DATEHYUNGEUM")) )	{
                request.setAttribute("page.mn03.FailMsg", "��ȿ���� ���� ��¥�Դϴ�. Ȯ�� �� �ٽ� ����ϼ���.");

            } else {

                CommonEntity noData = IR030110.getOrderNo(conn, paramInfo);   //���޹�ȣ �ߺ� ����

                if(noData.getLong("NO_CNT") == 0){	
                    
                    //�αױ�� ����� Ŭ������ �޼ҵ� ȣ��
                    TransLogInsert tli = new TransLogInsert();
                    tli.execute(request, response, conn);
                    paramInfo.setValue("log_no", tli.getLogNo());

                    if(IR030110.cashInsert(conn, paramInfo) < 1 ) {  //����� ��� 
                        throw new ProcessException("E002"); //����� �����޽��� ǥ��
                    }
                    request.setAttribute("page.mn03.SucMsg", "���ó���Ǿ����ϴ�.");
                } else {
                    request.setAttribute("page.mn03.SucMsg", "���޹�ȣ�� �ߺ��˴ϴ�. �ٽ� ����ϼ���.");
                }
            }
        }

        /* ����Ʈ ��ȸ */
        CommonEntity fis_date  = IR010110.getFisDate(conn);
        request.setAttribute("page.mn01.fis_date", fis_date);
		
        List<CommonEntity> cashDeptList = IR030110.getCashDeptList(conn, paramInfo);
        request.setAttribute("page.mn03.cashDeptList", cashDeptList);

        List<CommonEntity> accNameList = IR030110.getAccNameList(conn,paramInfo);
        request.setAttribute("page.mn03.accNameList", accNameList); 
    }
}