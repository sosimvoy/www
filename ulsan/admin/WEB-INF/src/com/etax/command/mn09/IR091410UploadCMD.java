/*****************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR091410UploadCMD.Java
* ���α׷��ۼ��� : 
* ���α׷��ۼ��� : 2010-05-13
* ���α׷�����	 : ������ > ������������ > ���ε��
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;
import com.oreilly.servlet.MultipartRequest;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR091410;
import com.etax.entity.CommonEntity;

public class IR091410UploadCMD extends BaseCommand {
  private static Logger logger = Logger.getLogger(IR091410UploadCMD.class);
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {
    MultipartRequest mRequest = (MultipartRequest)request.getAttribute("page.upload.multipartRequest");
    String filename = (String)request.getAttribute("page.upload.fileName");

		HttpSession session = request.getSession(false);
		String current_organ = (String)session.getAttribute("session.current_organ");

    CommonEntity paramInfo = new CommonEntity();
    if(mRequest==null){
		  paramInfo.setValue("current_organ",  current_organ);
		  paramInfo.setValue("f_name",   "");
	  } else {
		  paramInfo.setValue("current_organ",  current_organ);
		  paramInfo.setValue("f_name",   filename);
	  }

    logger.info("paramInfo : " + paramInfo);

    /* ���� ��� */
	  if(IR091410.insertSignInfo(conn, paramInfo) < 1 ) {
		  throw new ProcessException("E002"); //����� �����޽��� ǥ��
	  }
    
		CommonEntity signInfo = IR091410.getSignInfo(conn, paramInfo);
		request.setAttribute("page.mn09.signInfo", signInfo);
	}
}