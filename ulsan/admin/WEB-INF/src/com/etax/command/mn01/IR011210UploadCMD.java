/**********************************************************************
* ������Ʈ��		  : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���		  : IR011210UploadCMD.java
* ���α׷��ۼ���	: (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����   : ���� > GIRO�����
***********************************************************************/
package com.etax.command.mn01;

import javax.servlet.http.*;

import java.io.*;
import java.sql.*;

import com.oreilly.servlet.MultipartRequest;

import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.entity.CommonEntity;
import com.etax.config.ETaxConfig;
import com.etax.db.mn01.IR011210;
import com.etax.trans.data.GiroBody;

public class IR011210UploadCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR011210UploadCMD.class);

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {
        MultipartRequest mRequest = (MultipartRequest)request.getAttribute("page.upload.multipartRequest");
        String fileName = (String)request.getAttribute("page.upload.fileName");

        String uploadDir = "";

		HttpSession session = request.getSession(false);
        String userid    = (String)session.getAttribute("session.user_id");

		CommonEntity paramInfo = new CommonEntity();
		if (mRequest != null) {
		    paramInfo.setValue("job_dt",      mRequest.getParameter("job_dt"));
		    paramInfo.setValue("user_id",     userid);
		    
		    CommonEntity dataInfo = IR011210.getDataInfo(conn, paramInfo);  //�ߺ� �ڷ� üũ
	        if (!"0".equals(dataInfo.getString("CNT")) ) {
	            request.setAttribute("page.mn01.SucMsg",  "�̹� ��ϵ� �ڷ��Դϴ�.");
	        } else {
	            try {
                    logger.info("paramInfo : " + paramInfo);
                    uploadDir = ETaxConfig.getString("upload_dir") + "/giro/";
                    File file = new File(uploadDir + fileName);
                    BufferedReader in = new BufferedReader(new FileReader(uploadDir + fileName));
                    
                    String str;

                    while ((str = in.readLine()) != null) { //������ ������ ������ ���
                        GiroBody txtBody = new GiroBody();
                        txtBody.parseMsg(str);
                        if (!"".equals(txtBody.getMsgString("data09")) && "31".equals(txtBody.getMsgString("data08").substring(0, 2))) {
                            //���ڳ��ι�ȣ�� �����ϰ� ��� �����͸� ����
                            //System.out.println(str);
                            IR011210.insertData(conn, paramInfo, txtBody);
                        }
                    }
                    
                    in.close();
                    
                    if (file.exists()) {
                        file.delete();
                        logger.info("���ε� ���� ���� �Ϸ�");
                    }
                    request.setAttribute("page.mn01.SucMsg",   "GIRO��� ����� �Ϸ�Ǿ����ϴ�.");
                } catch (Exception e) {
                    logger.info("���� �߻� ::::::  [ " + e + " ] ::::::");
                    request.setAttribute("page.mn01.SucMsg",  "����� �۾� �� ������ �߻��Ͽ����ϴ�.");
                }
	        } // else -end
		    
		    CommonEntity giroData = IR011210.getGiroData(conn, paramInfo);
		    request.setAttribute("page.mn01.giroData", giroData);
		} // if - end
		
		
    }
}
