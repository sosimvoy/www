/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR030310InsertCMD.java
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-30
* ���α׷�����	   : ���Լ�������� > ��������������Ȳ ���
****************************************************************/

package com.etax.command.mn03;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.command.common.TransLogInsert;
import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn03.IR030310;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR030310InsertCMD extends BaseCommand {
 
	private static Logger logger = Logger.getLogger(IR030310InsertCMD.class);
	
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

        CommonEntity paramInfo = new CommonEntity();
  
		paramInfo.setValue("year",                   request.getParameter("date").substring(0,4));
		paramInfo.setValue("date",                   request.getParameter("date"));
		
		String date = request.getParameter("date");

        String SucMsg = "";

        int ret = 0;

		CommonEntity stampDateView = IR030310.getStampDateView(conn, paramInfo);  
	  
        conn.setAutoCommit(false);

		if ("JONJAE".equals(stampDateView.getString("TT")))	{
		  SucMsg =  "�ڷᰡ �����մϴ�.";
		} else if("SUNSEO".equals(stampDateView.getString("TT"))){
	    SucMsg =   "���ڼ������ ����ϼ���";
		} else if("FALSE".equals(stampDateView.getString("TT"))){
	    SucMsg =   "���� �ֱ��ڷẸ�� ���� �ڷ�� ����� �� �����ϴ�.";
		} else if(!(request.getParameter("date")).equals(TextHandler.getBusinessDate(conn, date)) ) { //������ üũ
		  SucMsg =   "�ش����ڴ� �������� �ƴմϴ�.";
		} else {
      
            CommonEntity MaxInfo = IR030310.getMaxDateView(conn);  //���� �ֱ� �����, ���� �ֱ� �⵵
            paramInfo.setValue("max_year",    MaxInfo.getString("MAX_YEAR"));  //���� �ֱٳ⵵
            paramInfo.setValue("max_date",    MaxInfo.getString("MAX_DATE"));  //���� �ֱ� �����

            TransLogInsert tli = new TransLogInsert();
            tli.execute(request, response, conn);
            paramInfo.setValue("log_no", tli.getLogNo());

            String[] gueonjong_list = request.getParameterValues("gueonjong");
            String[] create_list = request.getParameterValues("create");
            String[] ldisuse_list = request.getParameterValues("ldisuse");
            String[] gumgosale_list = request.getParameterValues("gumgosale");
            String[] citysale_list = request.getParameterValues("citysale");
            String[] citydivide_list = request.getParameterValues("citydivide");

            CommonEntity rowData = new CommonEntity(); //�����̿���

            for (int i=0; i<gueonjong_list.length; i++)	{

                paramInfo.setValue("gueonjong",        gueonjong_list[i]);
                paramInfo.setValue("create",           TextHandler.unformatNumber(create_list[i]));
                paramInfo.setValue("ldisuse",          TextHandler.unformatNumber(ldisuse_list[i]));
                paramInfo.setValue("gumgosale",        TextHandler.unformatNumber(gumgosale_list[i]));
                paramInfo.setValue("citysale",         TextHandler.unformatNumber(citysale_list[i]));
                paramInfo.setValue("citydivide",       TextHandler.unformatNumber(citydivide_list[i]));	
                CommonEntity janryang = IR030310.getJanryang(conn, paramInfo);  //�ܷ����ϱ�
                paramInfo.setValue("gumgorest",        janryang.getString("SIGUMGO_JAN"));
			    paramInfo.setValue("cityrest",         janryang.getString("CITY_JAN"));

                if ("FALSE".equals(janryang.getString("SIGUMGO_JAN")) ) {
                    SucMsg =  "�ñݰ��ܷ��� ���̳ʽ��Դϴ�. Ȯ�� �� �ٽ� ����Ͻñ� �ٶ��ϴ�.";
                    ret = IR030310.deleteData(conn, paramInfo);
                    if (ret < 1) {  //��� �ڷ� ����
                        SucMsg = "�ñݰ��ܷ��� ���̳ʽ��Դϴ�. Ȯ�� �� �ٽ� ����Ͻñ� �ٶ��ϴ�.";
                        conn.rollback();
                        conn.setAutoCommit(true);
                    }
                    break;
                } else if ("FALSE".equals(janryang.getString("CITY_JAN")) ) {
                    SucMsg =  "��û�����ܷ��� ���̳ʽ��Դϴ�. Ȯ�� �� �ٽ� ����Ͻñ� �ٶ��ϴ�.";
                    ret = IR030310.deleteData(conn, paramInfo);
                    if (ret < 1) {  //��� �ڷ� ����
                        SucMsg = "��û�����ܷ��� ���̳ʽ��Դϴ�. Ȯ�� �� �ٽ� ����Ͻñ� �ٶ��ϴ�.";
                        conn.rollback();
                        conn.setAutoCommit(true);
                    }
                    break;
                } else {

				    rowData = IR030310.getLastRestView(conn, paramInfo);  //�����̿� �� ���� ����

				    if (Integer.parseInt(paramInfo.getString("max_year")) < Integer.parseInt(paramInfo.getString("year")) ) {
				    
			            paramInfo.setValue("lastyear_amt",      rowData.getString("LASTREST"));
					    paramInfo.setValue("total_create",      "0");
                        paramInfo.setValue("total_disuse",      "0");
                        paramInfo.setValue("total_gumgosale",   "0");
                        paramInfo.setValue("total_citysale",    "0");
			
				    } else {

				        paramInfo.setValue("lastyear_amt",      rowData.getString("M050_LASTYEAR"));
                        paramInfo.setValue("total_create",      rowData.getString("M050_TOTALCREATE"));
			  	        paramInfo.setValue("total_disuse",      rowData.getString("M050_TOTALDISUSE"));
			  	        paramInfo.setValue("total_gumgosale",   rowData.getString("M050_TOTALGUMGOSALE"));
			  	        paramInfo.setValue("total_citysale",    rowData.getString("M050_TOTALCITYSALE"));
				    }
          logger.info("paramInfo : " + paramInfo);
           /* �������� ���*/ 
	        if(IR030310.insertStamp(conn, paramInfo) < 1 ) {
            conn.rollback();
            conn.setAutoCommit(true);
		        SucMsg =  "�������� ��� �� ������ �߻��Ͽ����ϴ�.";
            if (IR030310.deleteData(conn, paramInfo) < 1) {  //��� �ڷ� ����
              SucMsg = "�ڷ� ���� �� ������ �߻��Ͽ����ϴ�.";
              conn.rollback();
              conn.setAutoCommit(true);
            }
            break;
	        }
        }
			}
		}	

    if (!"".equals(SucMsg)) {
      request.setAttribute("page.mn03.SucMsg", SucMsg);
      conn.rollback();
      conn.setAutoCommit(true);
    } else {
		  request.setAttribute("page.mn03.SucMsg", "��ϵǾ����ϴ�.");
    }

    conn.commit();
    conn.setAutoCommit(true);
 
	   /*���� ������  */
		List<CommonEntity> stampList = IR030310.getStampList(conn, paramInfo);
		request.setAttribute("page.mn03.stampList", stampList);		

	}
}