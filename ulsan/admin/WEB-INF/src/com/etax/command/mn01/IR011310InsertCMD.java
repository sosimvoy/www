/*****************************************************
* ������Ʈ��		: ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���		: IR011310InsertCMD.java
* ���α׷��ۼ���	: (��)�̸�����
* ���α׷��ۼ���	: 2014-11-19
* ���α׷�����		: ���� > �����ϰ���
******************************************************/

package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
import java.util.List;

import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn01.IR011310;
import com.etax.entity.CommonEntity;

public class IR011310InsertCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR011310InsertCMD.class);	// log4j ����
	
	// Connection �� �ʿ��� ���� BaseCommand �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("ac_year",           request.getParameter("ac_year"));
		paramInfo.setValue("ac_date",           request.getParameter("ac_date").replace("-", ""));
		
		CommonEntity data = IR011310.getCarDailyData(conn, paramInfo);
		
		if (!"0".equals(data.getString("CNT"))) {
		    request.setAttribute("page.mn01.SucMsg",  "�ش�����("+request.getParameter("ac_date")+")�� �̹� ��ϵǾ����ϴ�.");
		} else {
            String[] ac_name = request.getParameterValues("ac_name");
            String[] order_no = request.getParameterValues("order_no");
            String[] before_nugye = request.getParameterValues("before_nugye");
            String[] today_suip = request.getParameterValues("today_suip");
            String[] hwanbu_amt = request.getParameterValues("hwanbu_amt");
            String[] hwanbu_nugye = request.getParameterValues("hwanbu_nugye");
            String[] correct_amt = request.getParameterValues("correct_amt");
            String[] today_nugye = request.getParameterValues("today_nugye");
            
            for (int i=0; i<ac_name.length; i++) {
                paramInfo.setValue("before_nugye",     before_nugye[i].replace(",", ""));//���ϴ���
                paramInfo.setValue("today_suip",       today_suip[i].replace(",", ""));//���ϼ���
                paramInfo.setValue("hwanbu_amt",       hwanbu_amt[i].replace(",", ""));//ȯ��
                paramInfo.setValue("hwanbu_nugye",     hwanbu_nugye[i].replace(",", ""));//ȯ�δ���
                paramInfo.setValue("correct_amt",      correct_amt[i].replace(",", ""));//����
                paramInfo.setValue("today_nugye",      today_nugye[i].replace(",", ""));//���ϴ�����
                paramInfo.setValue("ac_name",          ac_name[i]);//ȸ���
                paramInfo.setValue("order_no",         order_no[i]);//����
                logger.info("paramInfo : " + paramInfo);
                //������ϻ���� �ϰ� ���
                IR011310.insertCarDailyData(conn, paramInfo);
            }
            request.setAttribute("page.mn01.SucMsg",  "���ó���Ǿ����ϴ�.");
		}

        List<CommonEntity> carDailyList = IR011310.getCarDailyList(conn, paramInfo);
        request.setAttribute("page.mn01.carDailyList", carDailyList);
	}
}
