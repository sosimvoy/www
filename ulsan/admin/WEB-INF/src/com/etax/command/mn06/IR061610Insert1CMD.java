/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR061610Insert1CMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ܾ����ڷ��Է�(��Ÿ����)
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn06.IR061610;
import com.etax.db.mn06.IR060000;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;


public class IR061610Insert1CMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR061610Insert1CMD.class); // log4j ����

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 

        CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("reg_date",            request.getParameter("reg_date2"));
        paramInfo.setValue("last_year",           Integer.parseInt(request.getParameter("reg_date2").substring(0,4)) -1 + "");

        String fis_year    = (request.getParameter("reg_date2")).substring(0,4);  //ȸ�迬��
		String reg_date    = request.getParameter("reg_date2");   //ȸ����
		String before_date = TextHandler.getBeforeDate(conn, reg_date);  //��������
		String after_date  = TextHandler.getBusinessDate(conn, TextHandler.addDays(reg_date, 1));  //�Ϳ�����
		String after_year  = after_date.substring(0,4);  //�Ϳ����� ȸ�迬��
		String city_byul   = request.getParameter("city_byul");
		String ulsan_byul   = request.getParameter("ulsan_byul");
        String magam_date  = IR060000.ilbanCheck(conn, paramInfo).getString("M320_DATEILBAN");

		String[] seip_content   = request.getParameterValues("seip_content");
		String[] seip_amt       = request.getParameterValues("seip_amt");
		String[] sechul_content = request.getParameterValues("sechul_content");
		String[] sechul_amt     = request.getParameterValues("sechul_amt");
		
    
		CommonEntity dailyData = IR060000.dailyCheck(conn, reg_date);  //���ϸ���üũ

		if (!reg_date.equals(TextHandler.getBusinessDate(conn, reg_date)) ) { //������ üũ
		    request.setAttribute("page.mn06.SucMsg",   "�ش����ڴ� �������� �ƴմϴ�.");

		} else if (Integer.parseInt(reg_date) > Integer.parseInt(TextHandler.getCurrentDate())) {  //������� ������ �������� üũ
		    request.setAttribute("page.mn06.SucMsg",   "������ڰ� ���������Դϴ�.");

		} else if (dailyData.size() == 0){
			request.setAttribute("page.mn06.SucMsg",   "���ݿ����ܾ� ��� �� ��Ÿ���� �ܾ��� ����ϼ���.");

		} else if ("Y".equals(dailyData.getString("M210_WORKENDSTATE")) )	{
			request.setAttribute("page.mn06.SucMsg",   "���Ͼ����� �����Ǿ� ��Ÿ���� �ܾ� ����� �� �� �����ϴ�.");

		} else if ("Y".equals(dailyData.getString("M210_ETCDEPOSITSTATE")) ) {
			request.setAttribute("page.mn06.SucMsg",   "��Ÿ���� �ܾ� ����� �����Ǿ����ϴ�. ��ȸ �� ��Ÿ���� �ܾ��� �����ٶ��ϴ�.");

		} else {
            // ----------- ���� �۾� ---------------//
			if (Integer.parseInt(reg_date) <= Integer.parseInt(magam_date)) {  //����� ������ڰ� ȸ�踶�����ں��� ���ų� ����

			    if (IR061610.upateEtcMagam2(conn, reg_date) < 1) {   //��Ÿ���� ���� "Y"�� ����(���⵵)
			        throw new ProcessException("E003");
			    }

			}  //���� ��


			// --------- ���⵵ �۾� ----------------//
  
			if (IR061610.updateEtcJanak(conn, reg_date, city_byul, ulsan_byul, seip_content, seip_amt, sechul_content, sechul_amt) < 1) {  //�ܾ��� ���⵵ ����
			    throw new ProcessException("E003");
			}

			if (IR061610.upateEtcMagam(conn, reg_date) < 1) {  //��Ÿ���� ���� "Y"�� ����
			    throw new ProcessException("E003");
			}

			request.setAttribute("page.mn06.SucMsg",   "��Ÿ���� �ܾ��� ��ϵǾ����ϴ�.");
			
		}
    }
}