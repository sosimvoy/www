/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR030610InsertCMD.java
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-30
* ���α׷�����	   : ���Լ�������� > ���༼ ���ϸ���
****************************************************************/

package com.etax.command.mn03;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn03.IR030610;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR030610InsertCMD extends BaseCommand {
 
	private static Logger logger = Logger.getLogger(IR030610InsertCMD.class);
	
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

        String SucMsg = ""; //�޽���
        CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("acc_date",               request.getParameter("acc_date"));

        CommonEntity maxInfo = IR030610.getFisDate(conn);
        paramInfo.setValue("max_date",    maxInfo.getString("M070_DATE"));

        CommonEntity businessInfo = IR030610.getNextBusinessDate(conn, paramInfo);  //�Ϳ�����
        paramInfo.setValue("next_date",    businessInfo.getString("NEXT_DAY"));
        paramInfo.setValue("year",         paramInfo.getString("next_date").substring(0,4));

        String bigyo_date = TextHandler.addDays(paramInfo.getString("max_date"), -124);
        String first_date = TextHandler.getBusinessDate(conn, paramInfo.getString("year")+"0101");  //���ʿ�����

        if (Integer.parseInt(paramInfo.getString("acc_date")) < Integer.parseInt("20100930")) {
            SucMsg = "2010�� 10�� 1�� ���� �ڷ�� �����۾��� �� �� �����ϴ�.";
        } else if (Integer.parseInt(bigyo_date) >  Integer.parseInt(paramInfo.getString("acc_date")) ) {
            SucMsg =  "���� �ڷ����ڿ��� 124�� ���� ��¥�� �����۾��� �� �� �����ϴ�.";
        } else if (!paramInfo.getString("acc_date").equals(TextHandler.getBusinessDate(conn, paramInfo.getString("acc_date"))) ) {
            SucMsg = "�������� �������� �ƴմϴ�.";
        } else if (paramInfo.getString("max_date").equals(paramInfo.getString("next_date")) ) {
            SucMsg = "�̹� ������ �������ϴ�.";
        } else if (!paramInfo.getString("acc_date").equals(paramInfo.getString("max_date")) ) {
            SucMsg = "������ �۾����� " + TextHandler.formatDate(paramInfo.getString("max_date"), "yyyyMMdd", "yyyy�� MM�� dd��") + "�Դϴ�.";
        } else {
            logger.info("paramInfo : " + paramInfo);

            if (Integer.parseInt(paramInfo.getString("acc_date")) == Integer.parseInt(paramInfo.getString("max_date")) ) {
                //�������ڿ� ���� ����
                CommonEntity lastAmt = IR030610.getLastAmt(conn, paramInfo);

                paramInfo.setValue("M070_SPREDAYAMT",                  lastAmt.getString("M070_SPREDAYAMT")); //Ư�������ܾ�
                paramInfo.setValue("M070_SPREDAYINTERESTAMT",          lastAmt.getString("M070_SPREDAYINTERESTAMT")); //Ư����������
                paramInfo.setValue("M070_PREDAYAMT",                   lastAmt.getString("M070_PREDAYAMT")); //�ֵ������ܾ�
                paramInfo.setValue("M070_PREDAYINTERESTAMT",           lastAmt.getString("M070_PREDAYINTERESTAMT")); //�ֵ���������
                paramInfo.setValue("M070_SPREDAYTOTALCNT",             lastAmt.getString("M070_SPREDAYTOTALCNT")); //Ư������¡���Ǽ�����
                paramInfo.setValue("M070_SPREDAYTOTALAMT",             lastAmt.getString("M070_SPREDAYTOTALAMT")); //Ư������¡���ݾ״���
                paramInfo.setValue("M070_SPREDAYTOTALINTEREST",        lastAmt.getString("M070_SPREDAYTOTALINTEREST")); //Ư������¡�����ڴ���
                paramInfo.setValue("M070_PREDAYTOTALCNT",              lastAmt.getString("M070_PREDAYTOTALCNT")); //�ֵ�����¡���Ǽ�����
                paramInfo.setValue("M070_PREDAYTOTALAMT",              lastAmt.getString("M070_PREDAYTOTALAMT")); //�ֵ�����¡���ݾ״���
                paramInfo.setValue("M070_PREDAYTOTALINTEREST",         lastAmt.getString("M070_PREDAYTOTALINTEREST")); //�ֵ�����¡�����ڴ���
                paramInfo.setValue("M070_SPREDAYDIVIDETOTALCNT",       lastAmt.getString("M070_SPREDAYDIVIDETOTALCNT")); //Ư�����Ϲ�аǼ�����
                paramInfo.setValue("M070_SPREDAYDIVIDETOTALAMT",       lastAmt.getString("M070_SPREDAYDIVIDETOTALAMT")); //Ư�����Ϲ�бݾ״���
                paramInfo.setValue("M070_SPREDAYDIVIDETOTALINTERES",   lastAmt.getString("M070_SPREDAYDIVIDETOTALINTERES")); //Ư�����Ϲ�����ڴ���
                paramInfo.setValue("M070_PREDAYDIVIDETOTALCNT",        lastAmt.getString("M070_PREDAYDIVIDETOTALCNT")); //�ֵ����Ϲ�аǼ�����
                paramInfo.setValue("M070_PREDAYDIVIDETOTALAMT",        lastAmt.getString("M070_PREDAYDIVIDETOTALAMT")); //�ֵ����Ϲ�бݾ״���
                paramInfo.setValue("M070_PREDAYDIVIDETOTALINTEREST",   lastAmt.getString("M070_PREDAYDIVIDETOTALINTEREST")); //�ֵ����Ϲ�����ڴ���

                if (first_date.equals(paramInfo.getString("next_date"))) {  //�Ϳ������� ���ʿ������̸�
                    paramInfo.setValue("M070_SPREDAYTOTALCNT",             "0"); //Ư������¡���Ǽ�����
                    paramInfo.setValue("M070_SPREDAYTOTALAMT",             lastAmt.getString("M070_SPREDAYAMT")); //Ư������¡���ݾ״���
                    paramInfo.setValue("M070_SPREDAYTOTALINTEREST",        lastAmt.getString("M070_SPREDAYINTERESTAMT")); //Ư������¡�����ڴ���
                    paramInfo.setValue("M070_PREDAYTOTALCNT",              "0"); //�ֵ�����¡���Ǽ�����
                    paramInfo.setValue("M070_PREDAYTOTALAMT",              lastAmt.getString("M070_PREDAYAMT")); //�ֵ�����¡���ݾ״���
                    paramInfo.setValue("M070_PREDAYTOTALINTEREST",         lastAmt.getString("M070_PREDAYINTERESTAMT")); //�ֵ�����¡�����ڴ���
                    paramInfo.setValue("M070_SPREDAYDIVIDETOTALCNT",       "0"); //Ư�����Ϲ�аǼ�����
                    paramInfo.setValue("M070_SPREDAYDIVIDETOTALAMT",       "0"); //Ư�����Ϲ�бݾ״���
                    paramInfo.setValue("M070_SPREDAYDIVIDETOTALINTERES",   "0"); //Ư�����Ϲ�����ڴ���
                    paramInfo.setValue("M070_PREDAYDIVIDETOTALCNT",        "0"); //�ֵ����Ϲ�аǼ�����
                    paramInfo.setValue("M070_PREDAYDIVIDETOTALAMT",        "0"); //�ֵ����Ϲ�бݾ״���
                    paramInfo.setValue("M070_PREDAYDIVIDETOTALINTEREST",   "0"); //�ֵ����Ϲ�����ڴ���

                }

                if(IR030610.insertjuheangDay(conn, paramInfo) < 1 ) {
                    throw new ProcessException("E002"); //����� �����޽��� ǥ��
                }
            }
        }

        if (!"".equals(SucMsg) ) {
            request.setAttribute("page.mn03.SucMsg", SucMsg);
        } else {
            request.setAttribute("page.mn03.SucMsg", "����ó�� �Ǿ����ϴ�.");
        }
	}
}
