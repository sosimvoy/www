/********************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR070410SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-08-02
* ���α׷�����   : �ϰ�/���� > ���Ϻ�����ȸ
********************************************************/

package com.etax.command.mn07;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;


import com.etax.framework.command.BaseCommand;
import com.etax.db.mn07.IR070410;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR070410InsertCMD extends BaseCommand {
    private static Logger logger = Logger.getLogger(IR070410InsertCMD.class);
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {
        
        /* (��ȸ��)�Ķ���� ���� */
	    CommonEntity paramInfo = new CommonEntity();
        paramInfo.setValue("acc_year",   request.getParameter("acc_year"));
        paramInfo.setValue("acc_date",   request.getParameter("acc_date"));

        paramInfo.setValue("M470_CAR_AGO_GC01",         request.getParameter("M470_CAR_AGO_GC01"));     //������漼 �ݰ�� ī�� ���ϴ���
        paramInfo.setValue("M470_CAR_GC01",             request.getParameter("M470_CAR_GC01"));         //������漼 ī�� ���ϼ�����
        paramInfo.setValue("M470_CAR_ICHE_GC01",        request.getParameter("M470_CAR_ICHE_GC01"));    //������漼 ī�� ������ü��
        paramInfo.setValue("M470_CAR_AGODATE_GC01",     request.getParameter("M470_CAR_AGODATE_GC01")); //������漼 ī�� ��ü������

        paramInfo.setValue("M470_CAR_AGO_GC02",         request.getParameter("M470_CAR_AGO_GC02"));     //������漼 �ݰ�� �� ī�� ���ϴ���
        paramInfo.setValue("M470_CAR_GC02",             request.getParameter("M470_CAR_GC02"));         //������漼 ī��� ���ϼ�����
        paramInfo.setValue("M470_CAR_ICHE_GC02",        request.getParameter("M470_CAR_ICHE_GC02"));    //������漼 ī��� ������ü��
        paramInfo.setValue("M470_CAR_AGODATE_GC02",     request.getParameter("M470_CAR_AGODATE_GC02")); //������漼 ī��� ��ü������

        paramInfo.setValue("M470_NONG_AGO_GC01",        request.getParameter("M470_NONG_AGO_GC01"));    //������Ư�� �ݰ�� ī�� ���ϴ���
        paramInfo.setValue("M470_NONG_GC01",            request.getParameter("M470_NONG_GC01"));        //������Ư�� ī�� ���ϼ�����
        paramInfo.setValue("M470_NONG_ICHE_GC01",       request.getParameter("M470_NONG_ICHE_GC01"));   //������Ư�� ī�� ������ü��
        paramInfo.setValue("M470_NONG_AGODATE_GC01",    request.getParameter("M470_NONG_AGODATE_GC01"));//������Ư�� ī�� ��ü������

        paramInfo.setValue("M470_NONG_AGO_GC02",        request.getParameter("M470_NONG_AGO_GC02"));    //������Ư�� �ݰ�� �� ī�� ���ϴ���
        paramInfo.setValue("M470_NONG_GC02",            request.getParameter("M470_NONG_GC02"));        //������Ư�� ī��� ���ϼ�����
        paramInfo.setValue("M470_NONG_ICHE_GC02",       request.getParameter("M470_NONG_ICHE_GC02"));   //������Ư�� ī��� ������ü��
        paramInfo.setValue("M470_NONG_AGODATE_GC02",    request.getParameter("M470_NONG_AGODATE_GC02"));//������Ư�� ī��� ��ü������

        paramInfo.setValue("M470_BAL_AGO_GP01",         request.getParameter("M470_BAL_AGO_GP01"));     //����û����ü� �ݰ�� �Ϲ� ���ϴ���
        paramInfo.setValue("M470_BAL_GP01",             request.getParameter("M470_BAL_GP01"));         //����û����ü� �Ϲ� ���ϼ�����
        paramInfo.setValue("M470_BAL_ICHE_GP01",        request.getParameter("M470_BAL_ICHE_GP01"));    //����û����ü� �Ϲ� ������ü��
        paramInfo.setValue("M470_BAL_AGODATE_GP01",     request.getParameter("M470_BAL_AGODATE_GP01")); //����û����ü� �Ϲ� ��ü������

        paramInfo.setValue("M470_BAL_AGO_GC01",         request.getParameter("M470_BAL_AGO_GC01"));     //����û����ü� �ݰ�� ī�� ���ϴ���
        paramInfo.setValue("M470_BAL_GC01",             request.getParameter("M470_BAL_GC01"));         //����û����ü� ī�� ���ϼ�����
        paramInfo.setValue("M470_BAL_ICHE_GC01",        request.getParameter("M470_BAL_ICHE_GC01"));    //����û����ü� ī�� ������ü��
        paramInfo.setValue("M470_BAL_AGODATE_GC01",     request.getParameter("M470_BAL_AGODATE_GC01")); //����û����ü� ī�� ��ü������

        paramInfo.setValue("M470_BAL_AGO_GC02",         request.getParameter("M470_BAL_AGO_GC02"));     //����û����ü� �ݰ�� �� ī�� ���ϴ���
        paramInfo.setValue("M470_BAL_GC02",             request.getParameter("M470_BAL_GC02"));         //����û����ü� ī��� ���ϼ�����
        paramInfo.setValue("M470_BAL_ICHE_GC02",        request.getParameter("M470_BAL_ICHE_GC02"));    //����û����ü� ī��� ������ü��
        paramInfo.setValue("M470_BAL_AGODATE_GC02",     request.getParameter("M470_BAL_AGODATE_GC02")); //����û����ü� ī��� ��ü������

        paramInfo.setValue("M470_CD_GUM_CARD_SUM",      request.getParameter("M470_CD_GUM_CARD_SUM"));     //������漼 �ݰ�� ī�� ���ϴ���
        paramInfo.setValue("M470_CD_GUM_CARD_SUNAP",    request.getParameter("M470_CD_GUM_CARD_SUNAP"));   //������漼 ī�� ���ϼ�����
        paramInfo.setValue("M470_CD_GUM_CARD_ICHE",     request.getParameter("M470_CD_GUM_CARD_ICHE"));    //������漼 ī�� ������ü��
        paramInfo.setValue("M470_CD_GUM_CARD_DATE",     request.getParameter("M470_CD_GUM_CARD_DATE"));    //������漼 ī�� ��ü������

        paramInfo.setValue("M470_NT_GUM_CARD_SUM",      request.getParameter("M470_NT_GUM_CARD_SUM"));     //������Ư�� �ݰ�� ī�� ���ϴ���
        paramInfo.setValue("M470_NT_GUM_CARD_SUNAP",    request.getParameter("M470_NT_GUM_CARD_SUNAP"));   //������Ư�� ī�� ���ϼ�����
        paramInfo.setValue("M470_NT_GUM_CARD_ICHE",     request.getParameter("M470_NT_GUM_CARD_ICHE"));    //������Ư�� ī�� ������ü��
        paramInfo.setValue("M470_NT_GUM_CARD_DATE",     request.getParameter("M470_NT_GUM_CARD_DATE"));    //������Ư�� ī�� ��ü������

        paramInfo.setValue("M470_BS_GUM_CARD_SUM",      request.getParameter("M470_BS_GUM_CARD_SUM"));     //����û����ü� �ݰ�� ī�� ���ϴ���
        paramInfo.setValue("M470_BS_GUM_CARD_SUNAP",    request.getParameter("M470_BS_GUM_CARD_SUNAP"));   //����û����ü� ī�� ���ϼ�����
        paramInfo.setValue("M470_BS_GUM_CARD_ICHE",     request.getParameter("M470_BS_GUM_CARD_ICHE"));    //����û����ü� ī�� ������ü��
        paramInfo.setValue("M470_BS_GUM_CARD_DATE",     request.getParameter("M470_BS_GUM_CARD_DATE"));    //����û����ü� ī�� ��ü������

        paramInfo.setValue("M470_BS_GUM_NOCARD_SUM",    request.getParameter("M470_BS_GUM_NOCARD_SUM"));   //����û����ü� �ݰ�� �� ī�� ���ϴ���
        paramInfo.setValue("M470_BS_GUM_NOCARD_SUNAP",  request.getParameter("M470_BS_GUM_NOCARD_SUNAP")); //����û����ü� ī��� ���ϼ�����
        paramInfo.setValue("M470_BS_GUM_NOCARD_ICHE",   request.getParameter("M470_BS_GUM_NOCARD_ICHE"));  //����û����ü� ī��� ������ü��
        paramInfo.setValue("M470_BS_GUM_NOCARD_DATE",   request.getParameter("M470_BS_GUM_NOCARD_DATE"));  //����û����ü� ī��� ��ü������

        paramInfo.setValue("M470_BS_GUM_OARS_SUM",      request.getParameter("M470_BS_GUM_OARS_SUM"));   //����û����ü� OARS ���ϴ���
        paramInfo.setValue("M470_BS_GUM_OARS_SUNAP",    request.getParameter("M470_BS_GUM_OARS_SUNAP")); //����û����ü� OARS ���ϼ�����
        paramInfo.setValue("M470_BS_GUM_OARS_ICHE",     request.getParameter("M470_BS_GUM_OARS_ICHE"));  //����û����ü� OARS ������ü��
        paramInfo.setValue("M470_BS_GUM_OARS_DATE",     request.getParameter("M470_BS_GUM_OARS_DATE"));  //����û����ü� OARS ��ü������

        paramInfo.setValue("M470_BS_GUM_NARS_SUM",      request.getParameter("M470_BS_GUM_NARS_SUM"));   //����û����ü� NARS ���ϴ���
        paramInfo.setValue("M470_BS_GUM_NARS_SUNAP",    request.getParameter("M470_BS_GUM_NARS_SUNAP")); //����û����ü� NARS ���ϼ�����
        paramInfo.setValue("M470_BS_GUM_NARS_ICHE",     request.getParameter("M470_BS_GUM_NARS_ICHE"));  //����û����ü� NARS ������ü��
        paramInfo.setValue("M470_BS_GUM_NARS_DATE",     request.getParameter("M470_BS_GUM_NARS_DATE"));  //����û����ü� NARS ��ü������
        
        paramInfo.setValue("M470_SS_GUM_OCARD_SUM",     request.getParameter("M470_SS_GUM_OCARD_SUM_AGO")); //���ܼ��� �ݰ�� ī�� ���ϴ���
        paramInfo.setValue("M470_SS_GUM_OCARD_SUNAP",   request.getParameter("M470_SS_GUM_OCARD_SUNAP"));   //���ܼ��� ī�� ���ϼ�����
        paramInfo.setValue("M470_SS_GUM_OCARD_ICHE",    request.getParameter("M470_SS_GUM_OCARD_ICHE"));    //���ܼ��� ī�� ������ü��
        paramInfo.setValue("M470_SS_GUM_OCARD_DATE",     request.getParameter("M470_SS_GUM_OCARD_DATE"));   //���ܼ��� ī�� ��ü������
        
        paramInfo.setValue("M470_SS_GUM_NCARD_SUM",     request.getParameter("M470_SS_GUM_NCARD_SUM_AGO")); //���ܼ��� �ݰ�� ī�� ���ϴ���
        paramInfo.setValue("M470_SS_GUM_NCARD_SUNAP",   request.getParameter("M470_SS_GUM_NCARD_SUNAP"));   //���ܼ��� ī�� ���ϼ�����
        paramInfo.setValue("M470_SS_GUM_NCARD_ICHE",    request.getParameter("M470_SS_GUM_NCARD_ICHE"));    //���ܼ��� ī�� ������ü��
        paramInfo.setValue("M470_SS_GUM_NCARD_DATE",    request.getParameter("M470_SS_GUM_NCARD_DATE"));    //���ܼ��� ī�� ��ü������
        
        paramInfo.setValue("M470_SS_GUM_OARS_SUM",      request.getParameter("M470_SS_GUM_OARS_SUM_AGO"));   //���ܼ��� OARS ���ϴ���
        paramInfo.setValue("M470_SS_GUM_OARS_SUNAP",    request.getParameter("M470_SS_GUM_OARS_SUNAP")); //���ܼ��� OARS ���ϼ�����
        paramInfo.setValue("M470_SS_GUM_OARS_ICHE",     request.getParameter("M470_SS_GUM_OARS_ICHE"));  //���ܼ��� OARS ������ü��
        paramInfo.setValue("M470_SS_GUM_OARS_DATE",     request.getParameter("M470_SS_GUM_OARS_DATE"));  //���ܼ��� OARS ��ü������

        paramInfo.setValue("M470_SS_GUM_NARS_SUM",      request.getParameter("M470_SS_GUM_NARS_SUM_AGO"));   //���ܼ��� NARS ���ϴ���
        paramInfo.setValue("M470_SS_GUM_NARS_SUNAP",    request.getParameter("M470_SS_GUM_NARS_SUNAP")); //���ܼ��� NARS ���ϼ�����
        paramInfo.setValue("M470_SS_GUM_NARS_ICHE",     request.getParameter("M470_SS_GUM_NARS_ICHE"));  //���ܼ��� NARS ������ü��
        paramInfo.setValue("M470_SS_GUM_NARS_DATE",     request.getParameter("M470_SS_GUM_NARS_DATE"));  //���ܼ��� NARS ��ü������
        
        String acc_date = request.getParameter("acc_date");
        String acc_year = request.getParameter("acc_year");

        String SucMsg = ""; //�޽���

        if (!acc_date.equals(TextHandler.getBusinessDate(conn, acc_date)) ) {
            SucMsg = "ȸ�����ڰ� �������Դϴ�. ȸ�����ڴ� �ݵ�� �������̾�� �մϴ�.";
        } else {
            //�ش� ȸ������ �ڷ� ��ȸ
            CommonEntity tempNote = IR070410.getDateNote(conn, paramInfo);
            if (tempNote.size() > 0) {
                SucMsg = "�ش��ϴ� ȸ�����ڿ� �ڷᰡ �����մϴ�. ���� �� �ٽ� ����Ͻñ� �ٶ��ϴ�.";
            } else {
                if (IR070410.insertNote(conn, paramInfo) < 1) {
                    SucMsg = "��� �� ������ �߻��Ͽ����ϴ�. �����ڿ��� ���ǹٶ��ϴ�.";
                } else {
                    SucMsg = TextHandler.formatDate(acc_date, "yyyyMMdd", "yyyy-MM-dd") + " �ڷᰡ ��ϵǾ����ϴ�.";
                }
            }

            //�ش� ȸ������ �ڷ� ��ȸ
            CommonEntity dateNote = IR070410.getDateNote(conn, paramInfo);
            request.setAttribute("page.mn07.dateNote", dateNote);
            
            String f_date = TextHandler.getBusinessDate(conn, acc_year+"0101");
            logger.info("���ʿ����� : " + f_date);
            if (f_date.equals(acc_date)) { //���� �������� ��� ��� �ڷ� 0���� ��������
                //�� ������ �ڷ� ��ȸ
                CommonEntity agoNote = IR070410.getAgoNote1(conn, paramInfo);
                request.setAttribute("page.mn07.agoNote", agoNote);

            } else {
                //�� ������ �ڷ� ��ȸ
                CommonEntity agoNote = IR070410.getAgoNote(conn, paramInfo);
                request.setAttribute("page.mn07.agoNote", agoNote);
            }
        }
        request.setAttribute("page.mn07.SucMsg", SucMsg);
	}
}