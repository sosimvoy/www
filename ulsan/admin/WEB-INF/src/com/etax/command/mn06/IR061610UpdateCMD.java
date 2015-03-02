 /**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR061610UpdateCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ܾ����ڷ��Է¼���(���ݿ���)
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn06.IR061610;
import com.etax.db.mn06.IR060000;
import com.etax.db.trans.TransDAO;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;


public class IR061610UpdateCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR061610UpdateCMD.class); // log4j ����

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{
		
		HttpSession session = request.getSession(false);
		String user_id = (String)session.getAttribute("session.user_id");

        CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("reg_date",            request.getParameter("reg_date"));
		paramInfo.setValue("user_id",             request.getParameter("user_id"));
        paramInfo.setValue("last_year",           Integer.parseInt(request.getParameter("reg_date").substring(0,4)) -1 + "");

        String fis_year    = (request.getParameter("reg_date")).substring(0,4);  //ȸ�迬��
		String reg_date    = request.getParameter("reg_date");   //ȸ����
		String before_date = TextHandler.getBeforeDate(conn, reg_date);  //��������
        String magam_date  = IR060000.ilbanCheck(conn, paramInfo).getString("M320_DATEILBAN");

		String SucMsg = "";  //�޽���
   
		CommonEntity daemonData = IR060000.getDaemonData(conn, reg_date); 
		CommonEntity dailyData = IR060000.dailyCheck(conn, reg_date);
		if (!(request.getParameter("reg_date")).equals(TextHandler.getBusinessDate(conn, reg_date)) ) { 
			//������ üũ
		    SucMsg = "�ش����ڴ� �������� �ƴմϴ�.";

		} else if (Integer.parseInt(request.getParameter("reg_date")) > Integer.parseInt(TextHandler.getCurrentDate())) {  
			//������� ������ �������� üũ
		    SucMsg = "������ڰ� ���������Դϴ�.";

		} else if (dailyData.size() == 0) {
			//���Ͼ��� ���� ���� �ִ��� üũ
			SucMsg = "���ݿ��� ��� �� �����ٶ��ϴ�.";

		} else if ("Y".equals(dailyData.getString("M210_WORKENDSTATE")) )	{
			//���� ���� ���� üũ
			SucMsg = "���Ͼ����� �����Ǿ����ϴ�.";

		} else if (daemonData.size() > 0 && "N".equals(daemonData.getString("M480_INQUIRYYN")) ) { 
			//���� ���� ���̺� ��ȸ "N"�̸� ����ó��
		    SucMsg = "���ϰ� ó�� ���Դϴ�. ��� �� �õ��ϼ���.";
			
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

					CommonEntity pcNo = TransDAO.getTmlNo(conn);
					String tml_no = pcNo.getString("M260_TERMINALNO");

					if (IR061610.insertAcctInfo(conn, reg_date, tml_no) < 1) {  
						//���� �������̺� ���� �ֱ�
						throw new ProcessException("E002");
					}
              
					if (Integer.parseInt(reg_date) <= Integer.parseInt(magam_date)) { 
                        //����� ������ڰ� ȸ�踶�����ں��� ���ų� ���� 
					    if (IR061610.insertAcctInfo2(conn, reg_date, tml_no) < 1) {   
							//���� �������̺� ���� �ֱ�(�Ϲ�,Ư��ȸ�踸)
					        throw new ProcessException("E002");
					    }
					}
				}
        
				SucMsg =  "�ܾ��� �ڷᰡ �����Ǿ����ϴ�.";
            }
		}

		request.setAttribute("page.mn06.SucMsg",   SucMsg);
    } 
}