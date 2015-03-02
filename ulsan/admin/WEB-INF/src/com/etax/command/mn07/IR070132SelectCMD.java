/**********************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR070132SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-09-10
* ���α׷�����   : �ϰ躸�� > ���� ��ȸ > ���Ժб�ǥ(�Ѱ������ü�)
**********************************************************************/
 
package com.etax.command.mn07;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import java.util.Date;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.util.TextHandler;
import com.etax.util.StringUtil;
import com.etax.db.mn07.IR070132;
import com.etax.db.common.ReportDAO;   
import com.etax.entity.CommonEntity;

public class IR070132SelectCMD extends BaseCommand {
	
  private static Logger logger = Logger.getLogger(IR070132SelectCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
		
		String acc_year = request.getParameter("acc_year");		 //ȸ�迬��
		String acc_date = TextHandler.replace(request.getParameter("acc_date"),"-","");		 //ȸ������ 

    int acc_date_int = Integer.parseInt(acc_date);  


    String from_year = acc_year;     // ����⵵
    String to_year = "";
	  int last_int = Integer.parseInt(from_year) + 1;                  // ����⵵ + 1 (int -> String)
	  to_year = String.valueOf(last_int);
    
    String quarter = "";
    String first_bungi =  "";
    String last_bungi =  "";

                
    if ((Integer.parseInt(from_year + "0101") <= acc_date_int) &&  (acc_date_int <= Integer.parseInt(from_year + "0331"))) {
        quarter = "1";
        first_bungi =  from_year + "0101";
        last_bungi =  from_year + "0331";
		}	else if ( (Integer.parseInt(from_year + "0401") <= acc_date_int)  && (acc_date_int <= Integer.parseInt(from_year + "0631"))) { 
			 	quarter = "2"; 
        first_bungi =  from_year + "0401";
        last_bungi =  from_year + "0631";
		} else if ((Integer.parseInt(from_year + "0701") <= acc_date_int) && (acc_date_int <= Integer.parseInt(from_year +"0931"))) { 		
				quarter = "3";        
        first_bungi =  from_year + "0701";
        last_bungi =  from_year + "0931";
		}	else if ((Integer.parseInt(from_year + "1001") <= acc_date_int) && (acc_date_int <= Integer.parseInt(to_year +"0310"))) { 	
			  quarter = "4";          
        first_bungi =  from_year + "1001";
        last_bungi =  to_year + "0310";
	  }

  
		String first_business_date = TextHandler.getBusinessDate(conn, first_bungi);  //�б��ʿ�����
		String last_business_date = TextHandler.getAgoBusinessDate(conn, last_bungi);   //�б⸻������

		
		if(acc_year.equals("") || acc_year == null){
			Date date = new Date();
			acc_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
		}
		if(acc_date.equals("") || acc_date == null){
			Date date = new Date();
			acc_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyyMMdd");
		}

		if( !"last_business_date".equals(request.getParameter("acc_date")) ) {
		  request.setAttribute("page.mn07.SucMsg", "�б⸻ ���� �������� �ƴմϴ�. �ٽ� �����Ͻʽÿ�.");
		}
	
		paramInfo.setValue("acc_year",	acc_year);								// ȸ��⵵
		paramInfo.setValue("acc_date",	StringUtil.replace(acc_date,"-",""));	// ȸ������
		paramInfo.setValue("first_business_date",	first_business_date);		//�б��ʿ�����
		paramInfo.setValue("last_business_date",	last_business_date);		//�б⸻������

    /* ��������	*/
  		CommonEntity endState = ReportDAO.getEndState(conn, paramInfo);
		request.setAttribute("page.mn07.endState", endState);

		/* ���� */
  		List<CommonEntity> reportList = IR070132.getReportList(conn, paramInfo);
		request.setAttribute("page.mn07.reportList", reportList);

     /* �б� */
	   request.setAttribute("page.mn07.quarter", quarter);
	}
}
