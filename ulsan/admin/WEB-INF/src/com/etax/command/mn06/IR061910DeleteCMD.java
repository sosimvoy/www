/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR061910DeleteCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ڱݿ��ȸ�迬���̿����
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
import com.etax.db.mn06.IR061910;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;


public class IR061910DeleteCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR061910DeleteCMD.class); // log4j ����

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

        CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("this_year_list",     request.getParameter("this_year_list"));
        paramInfo.setValue("last_year_list",     request.getParameter("last_year_list"));
		paramInfo.setValue("acc_type",           request.getParameter("acc_type"));	
    
		List<CommonEntity> carryInfo = IR061910.getCarryYN(conn, paramInfo);

		int jeongi = 0;
		int mmda = 0;
        int yetak = 0;

		if (carryInfo.size() == 1 && "Y".equals(carryInfo.get(0).getString("M250_ACCTRANSFERYN")) ) {

            CommonEntity dateInfo = IR061910.getAccDate(conn, paramInfo);
            paramInfo.setValue("acc_date",    TextHandler.getndayafterBusinessDate(conn, dateInfo.getString("ACC_DATE"), 1));

            logger.info("paramInfo : " + paramInfo);
      
            CommonEntity count = IR061910.countYetak(conn, paramInfo);
            if ("0".equals(count.getString("CNT")) ) {
        
			    //���⿹��, ȯ��ü
                jeongi = IR061910.deleteJeongi(conn, paramInfo);

			    //MMDA
			    mmda = IR061910.deleteMMDA(conn, paramInfo);

                //��Ź���̺�(M120)
                yetak = IR061910.deleteYetak(conn, paramInfo);

                request.setAttribute("page.mn06.SucMsg", "���⿹��,ȯ��ü: " + jeongi + "��, MMDA: " + mmda + "���� �̿���ҵǾ����ϴ�.");
                logger.info("��Ź : " + yetak + "��, ���� : " + jeongi + "��, MMDA : " + mmda + "�� ����");
            } else {
                request.setAttribute("page.mn06.SucMsg", "ȸ�迬�� �̿� ó�� �� �̿��� ���·� ��Ź �Ǵ� ������ �߻��Ͽ� �̿� ��Ҹ� �� �� �����ϴ�..");
            }   
			
		} else {
            logger.info("ȸ�迬�� �̿� ���� : " + carryInfo);
			request.setAttribute("page.mn06.SucMsg", "�ش��ϴ� �̿�ȸ�迬���� �̿��۾��� �Ϸ���� �ʾҽ��ϴ�.");
		}		
    }
}
