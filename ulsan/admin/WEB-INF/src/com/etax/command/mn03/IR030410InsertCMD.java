/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR030410InsertCMD.java
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-30
* ���α׷�����	   : ���Լ�������� > ���༼���
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
import com.etax.db.mn03.IR030410;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR030410InsertCMD extends BaseCommand {
 
	private static Logger logger = Logger.getLogger(IR030410InsertCMD.class);
	
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

        String SucMsg = ""; //�޽���
        CommonEntity paramInfo = new CommonEntity();

	    paramInfo.setValue("year",                   request.getParameter("acc_date").substring(0,4));
	    paramInfo.setValue("acc_date",               request.getParameter("acc_date"));
	    paramInfo.setValue("jingsuType",             request.getParameter("jingsuType"));		
	    paramInfo.setValue("repayType",              request.getParameter("repayType"));
		paramInfo.setValue("cashType",               request.getParameter("cashType"));
		paramInfo.setValue("amt",                    request.getParameter("amt"));
		paramInfo.setValue("napseja",                request.getParameter("napseja"));
		paramInfo.setValue("logno",                  request.getParameter("logno"));

        CommonEntity maxInfo = IR030410.getFisDate(conn);
        paramInfo.setValue("max_date",    maxInfo.getString("M070_DATE"));

        String bigyo_date = TextHandler.addDays(paramInfo.getString("max_date"), -124);
        String first_date = TextHandler.getBusinessDate(conn, TextHandler.getCurrentDate().substring(0,4)+"0101");

        if (Integer.parseInt(paramInfo.getString("acc_date")) < Integer.parseInt("20101001")) {
            SucMsg = "2010�� 10�� 1�� �ڷ� ������ ����� �� �����ϴ�.";
        } else if (Integer.parseInt(bigyo_date) >  Integer.parseInt(paramInfo.getString("acc_date")) ) {
            SucMsg =  "���� �ڷ����ڿ��� 124�� ���� ��¥�� ����� �� �����ϴ�.";
        } else if (!paramInfo.getString("acc_date").equals(TextHandler.getBusinessDate(conn, paramInfo.getString("acc_date"))) ) {
            SucMsg = "������� �������� �ƴմϴ�.";
        } else if (Integer.parseInt(paramInfo.getString("max_date")) <  Integer.parseInt(paramInfo.getString("acc_date"))) {
            SucMsg = "���༼ ���ϸ����� ���༼ ����� �õ��Ͻñ� �ٶ��ϴ�.";
        } else {
		    String jingsuType= request.getParameter("jingsuType");
		    String repayType= request.getParameter("repayType");
		    String cashType= request.getParameter("cashType");

            if (jingsuType.equals("J1") && repayType.equals("R1") && cashType.equals("A1")){
                paramInfo.setValue("col_name", "M070_DAYSUIPSUMAMT"); 
                paramInfo.setValue("cnt_name", "M070_DAYSUIPSUMCNT");
            } else if(jingsuType.equals("J1") && repayType.equals("R1") && cashType.equals("A2")){
                paramInfo.setValue("col_name", "M070_DAYGWAONAPSUMAMT"); 
                paramInfo.setValue("cnt_name", "M070_DAYGWAONAPSUMCNT");     	
            } else if(jingsuType.equals("J1") && repayType.equals("R2") && cashType.equals("A1")){
                paramInfo.setValue("col_name","M070_DAYSUIPSUMINTEREST"); 
                paramInfo.setValue("cnt_name", "");
			} else if(jingsuType.equals("J1") && repayType.equals("R2") && cashType.equals("A2")){
                paramInfo.setValue("col_name","M070_DAYGWAONAPSUMINTEREST");
                paramInfo.setValue("cnt_name", "");
			} else if(jingsuType.equals("J2") && repayType.equals("R1") && cashType.equals("A1")){
                paramInfo.setValue("col_name","M070_SDAYSUIPSUMAMT");
                paramInfo.setValue("cnt_name", "M070_SDAYSUIPSUMCNT");  
            } else if(jingsuType.equals("J2") && repayType.equals("R1") && cashType.equals("A2")){
                paramInfo.setValue("col_name","M070_SDAYGWAONAPSUMAMT"); 
                paramInfo.setValue("cnt_name","M070_SDAYGWAONAPSUMCNT");                                 
            } else if(jingsuType.equals("J2") && repayType.equals("R2") && cashType.equals("A1")){
                paramInfo.setValue("col_name","M070_SDAYSUIPSUMINTEREST"); 
                paramInfo.setValue("cnt_name", "");
			} else if(jingsuType.equals("J2") && repayType.equals("R2") && cashType.equals("A2")){
                paramInfo.setValue("col_name","M070_SDAYGWAONAPSUMINTEREST");
                paramInfo.setValue("cnt_name", "");
			} else if(jingsuType.equals("J1") && repayType.equals("R1") && cashType.equals("A3")){
                paramInfo.setValue("col_name","M070_DAYJIGUPSUMAMT");
                paramInfo.setValue("cnt_name","M070_DAYJIGUPSUMCNT");                        
			} else if(jingsuType.equals("J1") && repayType.equals("R1") && cashType.equals("A4")){
                paramInfo.setValue("col_name","M070_DAYBANNAPSUMAMT");
                paramInfo.setValue("cnt_name","M070_DAYBANNAPSUMCNT");                      
			} else if(jingsuType.equals("J1") && repayType.equals("R2") && cashType.equals("A3")){
                paramInfo.setValue("col_name","M070_DAYJIGUPSUMINTEREST");
                paramInfo.setValue("cnt_name", "");
			} else if(jingsuType.equals("J1") && repayType.equals("R2") && cashType.equals("A4")){
                paramInfo.setValue("col_name","M070_DAYBANNAPSUMINTEREST");
                paramInfo.setValue("cnt_name", "");
			} else if(jingsuType.equals("J2") && repayType.equals("R1") && cashType.equals("A3")){
                paramInfo.setValue("col_name","M070_SDAYJIGUPSUMAMT");
                paramInfo.setValue("cnt_name","M070_SDAYJIGUPSUMCNT");                                  
			} else if(jingsuType.equals("J2") && repayType.equals("R1") && cashType.equals("A4")){
                paramInfo.setValue("col_name","M070_SDAYBANNAPSUMAMT");
                paramInfo.setValue("cnt_name","M070_SDAYBANNAPSUMCNT");                                   
			} else if(jingsuType.equals("J2") && repayType.equals("R2") && cashType.equals("A3")){
                paramInfo.setValue("col_name","M070_SDAYJIGUPSUMINTEREST");
                paramInfo.setValue("cnt_name", "");
			} else if(jingsuType.equals("J2") && repayType.equals("R2") && cashType.equals("A4")){
                paramInfo.setValue("col_name","M070_SDAYBANNAPSUMINTEREST");
                paramInfo.setValue("cnt_name", "");
            }
				
	        //�αױ�� ����� Ŭ������ �޼ҵ� ȣ��
            TransLogInsert tli = new TransLogInsert();
	        tli.execute(request, response, conn);
	        paramInfo.setValue("log_no", tli.getLogNo());
            logger.info("paramInfo : " + paramInfo);
    
		    /* ���༼ ��� */
	        if(IR030410.insertjuheang(conn, paramInfo) < 1 ) {
		        throw new ProcessException("E002"); //����� �����޽��� ǥ��
	        }

            if (Integer.parseInt(paramInfo.getString("acc_date")) == Integer.parseInt(paramInfo.getString("max_date")) ) { 
                //�������ڿ� ���� ����
                if(IR030410.updatejuheangDay(conn, paramInfo) < 1 ) {
                    throw new ProcessException("E003");
                }
            } else if (Integer.parseInt(paramInfo.getString("acc_date")) < Integer.parseInt(paramInfo.getString("max_date")) ) {
                //�������ں��� ���� ���� ���
                CommonEntity lastNu = IR030410.getLastNu(conn, paramInfo);  //ȸ������ �������� ���� ����

                paramInfo.setValue("M070_SPREDAYAMT",                  lastNu.getString("M070_SPREDAYAMT")); //Ư�������ܾ�
                paramInfo.setValue("M070_SPREDAYINTERESTAMT",          lastNu.getString("M070_SPREDAYINTERESTAMT")); //Ư����������
                paramInfo.setValue("M070_PREDAYAMT",                   lastNu.getString("M070_PREDAYAMT")); //�ֵ������ܾ�
                paramInfo.setValue("M070_PREDAYINTERESTAMT",           lastNu.getString("M070_PREDAYINTERESTAMT")); //�ֵ���������
                paramInfo.setValue("M070_SPREDAYTOTALCNT",             lastNu.getString("M070_SPREDAYTOTALCNT")); //Ư������¡���Ǽ�����
                paramInfo.setValue("M070_SPREDAYTOTALAMT",             lastNu.getString("M070_SPREDAYTOTALAMT")); //Ư������¡���ݾ״���
                paramInfo.setValue("M070_SPREDAYTOTALINTEREST",        lastNu.getString("M070_SPREDAYTOTALINTEREST")); //Ư������¡�����ڴ���
                paramInfo.setValue("M070_PREDAYTOTALCNT",              lastNu.getString("M070_PREDAYTOTALCNT")); //�ֵ�����¡���Ǽ�����
                paramInfo.setValue("M070_PREDAYTOTALAMT",              lastNu.getString("M070_PREDAYTOTALAMT")); //�ֵ�����¡���ݾ״���
                paramInfo.setValue("M070_PREDAYTOTALINTEREST",         lastNu.getString("M070_PREDAYTOTALINTEREST")); //�ֵ�����¡�����ڴ���
                paramInfo.setValue("M070_SPREDAYDIVIDETOTALCNT",       lastNu.getString("M070_SPREDAYDIVIDETOTALCNT")); //Ư�����Ϲ�аǼ�����
                paramInfo.setValue("M070_SPREDAYDIVIDETOTALAMT",       lastNu.getString("M070_SPREDAYDIVIDETOTALAMT")); //Ư�����Ϲ�бݾ״���
                paramInfo.setValue("M070_SPREDAYDIVIDETOTALINTERES",   lastNu.getString("M070_SPREDAYDIVIDETOTALINTERES")); //Ư�����Ϲ�����ڴ���
                paramInfo.setValue("M070_PREDAYDIVIDETOTALCNT",        lastNu.getString("M070_PREDAYDIVIDETOTALCNT")); //�ֵ����Ϲ�аǼ�����
                paramInfo.setValue("M070_PREDAYDIVIDETOTALAMT",        lastNu.getString("M070_PREDAYDIVIDETOTALAMT")); //�ֵ����Ϲ�бݾ״���
                paramInfo.setValue("M070_PREDAYDIVIDETOTALINTEREST",   lastNu.getString("M070_PREDAYDIVIDETOTALINTEREST")); //�ֵ����Ϲ�����ڴ���

                if (first_date.equals(paramInfo.getString("acc_date"))) {  //���ʿ������̸�
                    //ȸ�����ڿ� �����⵵ �ٸ� ��
                    paramInfo.setValue("M070_SPREDAYTOTALCNT",             "0"); //Ư������¡���Ǽ�����
                    paramInfo.setValue("M070_SPREDAYTOTALAMT",             lastNu.getString("M070_SPREDAYAMT")); //Ư������¡���ݾ״���
                    paramInfo.setValue("M070_SPREDAYTOTALINTEREST",        lastNu.getString("M070_SPREDAYINTERESTAMT")); //Ư������¡�����ڴ���
                    paramInfo.setValue("M070_PREDAYTOTALCNT",              "0"); //�ֵ�����¡���Ǽ�����
                    paramInfo.setValue("M070_PREDAYTOTALAMT",              lastNu.getString("M070_PREDAYAMT")); //�ֵ�����¡���ݾ״���
                    paramInfo.setValue("M070_PREDAYTOTALINTEREST",         lastNu.getString("M070_PREDAYINTERESTAMT")); //�ֵ�����¡�����ڴ���
                    paramInfo.setValue("M070_SPREDAYDIVIDETOTALCNT",       "0"); //Ư�����Ϲ�аǼ�����
                    paramInfo.setValue("M070_SPREDAYDIVIDETOTALAMT",       "0"); //Ư�����Ϲ�бݾ״���
                    paramInfo.setValue("M070_SPREDAYDIVIDETOTALINTERES",   "0"); //Ư�����Ϲ�����ڴ���
                    paramInfo.setValue("M070_PREDAYDIVIDETOTALCNT",        "0"); //�ֵ����Ϲ�аǼ�����
                    paramInfo.setValue("M070_PREDAYDIVIDETOTALAMT",        "0"); //�ֵ����Ϲ�бݾ״���
                    paramInfo.setValue("M070_PREDAYDIVIDETOTALINTEREST",   "0"); //�ֵ����Ϲ�����ڴ���
                }

                CommonEntity juhaengCnt = IR030410.juhaengCnt(conn, paramInfo);

                /* �Է¹��� ȸ������ ó�� */
                if ("0".equals(juhaengCnt.getString("CNT"))) {  // �ش��� �ϰ��ڷ� ���� ��
                    if(IR030410.insertjuheangDay(conn, paramInfo) < 1 ) {
                        throw new ProcessException("E002"); //����� �����޽��� ǥ��
                    }
                } else {
                    //�������ڿ� ���� ����
                    if(IR030410.updatejuheangDay(conn, paramInfo) < 1 ) {
                        throw new ProcessException("E003");
                    }
                }

                CommonEntity minInfo = IR030410.getMinDate(conn, paramInfo);
                paramInfo.setValue("min_date",    minInfo.getString("M070_DATE"));
        
                /* ȸ�� ���� ������ ���ڸ���Ʈ */
                List<CommonEntity> dayList = IR030410.getDayList(conn, paramInfo);
           
                for (int i = 0; i < dayList.size(); i++) {
                    CommonEntity dayInfo = (CommonEntity)dayList.get(i);
                    paramInfo.setValue("acc_date",    dayInfo.getString("M070_DATE"));

                    CommonEntity roopNu = IR030410.getLastNu(conn, paramInfo);  //�۾��� �������� ���� ����

                    paramInfo.setValue("M070_SPREDAYAMT",                  roopNu.getString("M070_SPREDAYAMT")); //Ư�������ܾ�
                    paramInfo.setValue("M070_SPREDAYINTERESTAMT",          roopNu.getString("M070_SPREDAYINTERESTAMT")); //Ư����������
                    paramInfo.setValue("M070_PREDAYAMT",                   roopNu.getString("M070_PREDAYAMT")); //�ֵ������ܾ�
                    paramInfo.setValue("M070_PREDAYINTERESTAMT",           roopNu.getString("M070_PREDAYINTERESTAMT")); //�ֵ���������
                    paramInfo.setValue("M070_SPREDAYTOTALCNT",             roopNu.getString("M070_SPREDAYTOTALCNT")); //Ư������¡���Ǽ�����
                    paramInfo.setValue("M070_SPREDAYTOTALAMT",             roopNu.getString("M070_SPREDAYTOTALAMT")); //Ư������¡���ݾ״���
                    paramInfo.setValue("M070_SPREDAYTOTALINTEREST",        roopNu.getString("M070_SPREDAYTOTALINTEREST")); //Ư������¡�����ڴ���
                    paramInfo.setValue("M070_PREDAYTOTALCNT",              roopNu.getString("M070_PREDAYTOTALCNT")); //�ֵ�����¡���Ǽ�����
                    paramInfo.setValue("M070_PREDAYTOTALAMT",              roopNu.getString("M070_PREDAYTOTALAMT")); //�ֵ�����¡���ݾ״���
                    paramInfo.setValue("M070_PREDAYTOTALINTEREST",         roopNu.getString("M070_PREDAYTOTALINTEREST")); //�ֵ�����¡�����ڴ���
                    paramInfo.setValue("M070_SPREDAYDIVIDETOTALCNT",       roopNu.getString("M070_SPREDAYDIVIDETOTALCNT")); //Ư�����Ϲ�аǼ�����
                    paramInfo.setValue("M070_SPREDAYDIVIDETOTALAMT",       roopNu.getString("M070_SPREDAYDIVIDETOTALAMT")); //Ư�����Ϲ�бݾ״���
                    paramInfo.setValue("M070_SPREDAYDIVIDETOTALINTERES",   roopNu.getString("M070_SPREDAYDIVIDETOTALINTERES")); //Ư�����Ϲ�����ڴ���
                    paramInfo.setValue("M070_PREDAYDIVIDETOTALCNT",        roopNu.getString("M070_PREDAYDIVIDETOTALCNT")); //�ֵ����Ϲ�аǼ�����
                    paramInfo.setValue("M070_PREDAYDIVIDETOTALAMT",        roopNu.getString("M070_PREDAYDIVIDETOTALAMT")); //�ֵ����Ϲ�бݾ״���
                    paramInfo.setValue("M070_PREDAYDIVIDETOTALINTEREST",   roopNu.getString("M070_PREDAYDIVIDETOTALINTEREST")); //�ֵ����Ϲ�����ڴ���

                    if (paramInfo.getString("acc_date").equals(paramInfo.getString("min_date"))) {
                        paramInfo.setValue("M070_SPREDAYTOTALCNT",             "0"); //Ư������¡���Ǽ�����
                        paramInfo.setValue("M070_SPREDAYTOTALAMT",             roopNu.getString("M070_SPREDAYAMT")); //Ư������¡���ݾ״���
                        paramInfo.setValue("M070_SPREDAYTOTALINTEREST",        roopNu.getString("M070_SPREDAYINTERESTAMT")); //Ư������¡�����ڴ���
                        paramInfo.setValue("M070_PREDAYTOTALCNT",              "0"); //�ֵ�����¡���Ǽ�����
                        paramInfo.setValue("M070_PREDAYTOTALAMT",              roopNu.getString("M070_PREDAYAMT")); //�ֵ�����¡���ݾ״���
                        paramInfo.setValue("M070_PREDAYTOTALINTEREST",         roopNu.getString("M070_PREDAYINTERESTAMT")); //�ֵ�����¡�����ڴ���
                        paramInfo.setValue("M070_SPREDAYDIVIDETOTALCNT",       "0"); //Ư�����Ϲ�аǼ�����
                        paramInfo.setValue("M070_SPREDAYDIVIDETOTALAMT",       "0"); //Ư�����Ϲ�бݾ״���
                        paramInfo.setValue("M070_SPREDAYDIVIDETOTALINTERES",   "0"); //Ư�����Ϲ�����ڴ���
                        paramInfo.setValue("M070_PREDAYDIVIDETOTALCNT",        "0"); //�ֵ����Ϲ�аǼ�����
                        paramInfo.setValue("M070_PREDAYDIVIDETOTALAMT",        "0"); //�ֵ����Ϲ�бݾ״���
                        paramInfo.setValue("M070_PREDAYDIVIDETOTALINTEREST",   "0"); //�ֵ����Ϲ�����ڴ���
                    }

                    if (IR030410.updateRoopData(conn, paramInfo) < 1) {  //�Է¹��� ȸ������ ���� �ڷ� ����
                        throw new ProcessException("E003");
                    }

                }

            }
    
        }

        if (!"".equals(SucMsg) ) {
            request.setAttribute("page.mn03.SucMsg", SucMsg);
        } else {
            request.setAttribute("page.mn03.SucMsg", "���ó�� �Ǿ����ϴ�.");
        }
	}
}