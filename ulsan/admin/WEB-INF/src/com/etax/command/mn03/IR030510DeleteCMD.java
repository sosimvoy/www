/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR090510DeleteCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-21
* ���α׷�����   : ���Լ���� ���� > ���༼ ��ȸ/����/����
****************************************************************/

package com.etax.command.mn03;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn03.IR030510;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR030510DeleteCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR030510DeleteCMD.class); // log4j ����

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    
		CommonEntity paramInfo = new CommonEntity();
    
        paramInfo.setValue("start_date",       request.getParameter("start_date"));
		paramInfo.setValue("end_date",         request.getParameter("end_date"));
		paramInfo.setValue("napseja",          request.getParameter("napseja"));

		String[] chk_list = request.getParameterValues("userChk");
		String[] seq_list = request.getParameterValues("seq_list");
        String[] cashType1 = request.getParameterValues("cashType");
		String[] jingsuType1 = request.getParameterValues("jingsuType");
        String[] repayType1 = request.getParameterValues("repayType");
        String[] input_amt = request.getParameterValues("input_amt");
        String[] input_date = request.getParameterValues("input_date");  

        paramInfo.setValue("acc_date",   input_date[Integer.parseInt(chk_list[0])]);

        String jingsuType = "";
        String repayType = "";
        String cashType = "";

        CommonEntity maxDate = IR030510.getMaxDate(conn);  //�����ڷ� ���� ���ϱ�
        paramInfo.setValue("max_date",   maxDate.getString("M070_DATE"));

        String bigyo_date = TextHandler.addDays(paramInfo.getString("max_date"), -124);
        String SucMsg = "";

        if (Integer.parseInt(bigyo_date) >  Integer.parseInt(input_date[Integer.parseInt(chk_list[0])]) ) {
            SucMsg =  "���� �ڷ����ڿ��� 124�� ���� ��¥�� ������ �� �����ϴ�.";
        } else {
		    for (int i=0; i<chk_list.length; i++) {
		        int chk_val = Integer.parseInt(chk_list[i]);
                jingsuType = jingsuType1[chk_val];
                repayType = repayType1[chk_val];
                cashType = cashType1[chk_val];
			
		        paramInfo.setValue("seq",        seq_list[chk_val]);
                paramInfo.setValue("input_amt",  input_amt[chk_val]);

		        if (IR030510.deleteJuheang(conn, paramInfo) < 1) {
		            throw new ProcessException("E004"); //������ �����޽��� ǥ��
		        }

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

                if (IR030510.updateData(conn, paramInfo)< 1) {
                    throw new ProcessException("E003");
                }
            }  //for�� end
            /* 2012.01.11 ������ �ش� �ʵ� �ܿ� �ٸ� �ʵ�� ������ �ʿ䰡 ���ٰ�
               �󹫴��� ����
            CommonEntity minInfo = IR030510.getMinDate(conn, paramInfo); //���� ��������
            paramInfo.setValue("min_date",    minInfo.getString("M070_DATE"));

            List<CommonEntity> dayList = IR030510.getDayList(conn, paramInfo);

            for (int i = 0; i < dayList.size(); i++) {
                CommonEntity dayInfo = (CommonEntity)dayList.get(i);
                paramInfo.setValue("acc_date",    dayInfo.getString("M070_DATE"));

                CommonEntity roopNu = IR030510.getLastNu(conn, paramInfo);  //�۾��� �������� ���� ����

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

                /*
                if (IR030510.updateData(conn, paramInfo)< 1) {
                    throw new ProcessException("E003");
                }
                */
                
                /*
                if (IR030510.updateRoopData(conn, paramInfo) < 1) {  //�Է¹��� ȸ������ ���� �ڷ� ����
                    throw new ProcessException("E003");
                }
                
            } //���ڸ���Ʈ 
            */
        }

        if (!"".equals(SucMsg) ) {
            request.setAttribute("page.mn03.SucMsg", SucMsg);
        } else {
            request.setAttribute("page.mn03.SucMsg", "����ó�� �Ǿ����ϴ�.");
        }
    
		List<CommonEntity> juheangList = IR030510.getJuheangList(conn, paramInfo);
		request.setAttribute("page.mn03.juheangList", juheangList);
    }
}