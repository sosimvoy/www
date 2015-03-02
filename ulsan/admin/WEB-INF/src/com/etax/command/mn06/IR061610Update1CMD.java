 /**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR061610Update1CMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ܾ����ڷ��Է¼���(��Ÿ����)
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn06.IR061610;
import com.etax.db.mn06.IR060000;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;


public class IR061610Update1CMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR061610Update1CMD.class); // log4j ����

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 

        CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("reg_date",            request.getParameter("reg_date2"));
        paramInfo.setValue("last_year",           Integer.parseInt(request.getParameter("reg_date2").substring(0,4)) -1 + "");

        String fis_year    = (request.getParameter("reg_date2")).substring(0,4);  //ȸ�迬��
		String reg_date    = request.getParameter("reg_date2");   //ȸ����
		String before_date = TextHandler.getBeforeDate(conn, reg_date);  //��������
        String magam_date  = IR060000.ilbanCheck(conn, paramInfo).getString("M320_DATEILBAN");

		String city_byul   = request.getParameter("city_byul");
		String ulsan_byul   = request.getParameter("ulsan_byul");

		String[] seip_content   = request.getParameterValues("seip_content");
		String[] seip_amt       = request.getParameterValues("seip_amt");
		String[] sechul_content = request.getParameterValues("sechul_content");
		String[] sechul_amt     = request.getParameterValues("sechul_amt");

		String SucMsg = new String();  //�޽���
    
		CommonEntity dailyData = IR060000.dailyCheck(conn, reg_date);
		if (!(reg_date).equals(TextHandler.getBusinessDate(conn, reg_date)) ) { 
			//������ üũ
		    SucMsg = "�ش����ڴ� �������� �ƴմϴ�.";
		} else if (Integer.parseInt(reg_date) > Integer.parseInt(TextHandler.getCurrentDate())) {  
			//������� ������ �������� üũ
		    SucMsg = "������ڰ� ���������Դϴ�.";
		} else if (dailyData.size() == 0) {
			//���Ͼ��� ���� ���� �ִ��� üũ
			SucMsg = "���ݿ��� ��� �� �����ٶ��ϴ�.";
		} else if (!"Y".equals(dailyData.getString("M210_ETCDEPOSITSTATE")) ) {
            //��Ÿ������ ����ߴ��� ����
            SucMsg = "��Ÿ���� ��� �� �����ٶ��ϴ�.";
        } else if ("Y".equals(dailyData.getString("M210_WORKENDSTATE")) )	{
			//���� ���� ���� üũ
			SucMsg = "���Ͼ����� �����Ǿ����ϴ�.";
		} else {
            // ----------- ���� �۾� ---------------//
			if (Integer.parseInt(reg_date) <= Integer.parseInt(magam_date)) {  //����� ������ڰ� ȸ�踶�����ں��� ���ų� ���� 
				CommonEntity gwaJanakData = IR060000.getLastJanakData(conn, reg_date);  //���⵵ �ܾ��� �ڷ� ���翩��(�Ϲ�,Ư��ȸ�踸)
			    if (gwaJanakData.size() == 0) {
					//���⵵ �ܾ��� �ڷ� ���� ��
				    SucMsg = "���⵵ �ܾ��� �ڷᰡ �����ϴ�. �����ڿ��� ���ǹٶ��ϴ�.";
			    } 
			}  //���� ��
            if ("".equals(SucMsg)) {
				// --------- ���⵵ �۾� ----------------//
				CommonEntity janakData = IR060000.getJanakData(conn, reg_date);  //�ܾ��� �ڷ� ���翩��
				if (janakData.size () == 0)	{
					SucMsg = "���⵵ �ܾ��� �ڷᰡ �����ϴ�. �����ڿ��� ���ǹٶ��ϴ�.";
				} else {
					if (IR061610.updateEtcJanak(conn, reg_date, city_byul, ulsan_byul, seip_content, seip_amt, sechul_content, sechul_amt) < 1) {  //�ܾ��� ���⵵ ����
			            throw new ProcessException("E003");
			        }
				}
				request.setAttribute("page.mn06.SucMsg",   "�ܾ��� �ڷᰡ �����Ǿ����ϴ�.");
            } else if (!"".equals(SucMsg)) {
				//�����޽��� ����
				request.setAttribute("page.mn06.SucMsg",   SucMsg);
            }
		}
    }
}
