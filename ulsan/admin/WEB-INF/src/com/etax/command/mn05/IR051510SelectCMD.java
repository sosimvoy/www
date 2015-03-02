/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR051510SelectCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > �ڱݹ�����ó���ܾ���ȸ
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn05.IR051510;
import com.etax.db.trans.TransDAO;
import com.etax.command.common.TransferNo;
import com.etax.trans.TransHandler;
import com.etax.trans.data.GNBRev;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR051510SelectCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR051510SelectCMD.class); // log4j ����

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{

        HttpSession session = request.getSession(false);
	    String user_id = (String)session.getAttribute("session.user_id");
	    String errMsg = "";  //�����޽���
	    String result = "";  //�������� String

		CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("imp_rec_cd",   "9999");
		paramInfo.setValue("allot_date",   request.getParameter("allot_date"));

		/* ������ ����� �����ȸ */
        List<CommonEntity>  allotList = IR051510.getAllotList(conn, paramInfo);
        request.setAttribute("page.mn05.allotList", allotList);
    }
}