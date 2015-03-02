/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR050610SelectCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > ���º��ŷ�������ȸ
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.command.common.TransferNo;
import com.etax.trans.TransHandler;
import com.etax.trans.data.GNBRev;
import com.etax.entity.CommonEntity;

public class IR050610SelectCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR050610SelectCMD.class); // log4j ����

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 

		String errMsg = "";  //�����޽���
		String result = "";  //�������� String

		CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("acct_no",          request.getParameter("acct_no"));
		paramInfo.setValue("start_date",       request.getParameter("start_date"));
		paramInfo.setValue("end_date",         request.getParameter("end_date"));

		logger.info("paramInfo : " + paramInfo);

		if ("Y".equals(request.getParameter("flag")) ) {  //��ȸ��ư�� ���ؼ� command ����� �������� �Ǵ�
			CommonEntity pcNo = TransferNo.transferNo(conn, "");  // �ܸ���ȣ ��ȸ

			HashMap<String, String> findData = new HashMap<String, String>();
			findData.put("common01", "1300");
			findData.put("common02", "SDS00071");
			findData.put("common04", "7000");
			findData.put("common05", "500");
			findData.put("common07", pcNo.getString("TRANS_MANAGE_NO"));
			findData.put("common12", "632");
			findData.put("common13", pcNo.getString("M260_TERMINALNO"));
			findData.put("detail01", request.getParameter("acct_no"));
			if (!"".equals(request.getParameter("deal_date")) )	{
				findData.put("detail02", request.getParameter("deal_date"));
			} else {
			  findData.put("detail02", request.getParameter("start_date"));
			}
			findData.put("detail03", request.getParameter("end_date"));
			if ("00000".equals(request.getParameter("trans_no")) )	{
				findData.put("detail04", "     ");
			} else {
			  findData.put("detail04", request.getParameter("trans_no"));
			}

			List<CommonEntity> dataList =  new ArrayList<CommonEntity>();  // �������� ���� ����Ʈ
			CommonEntity rstData = new CommonEntity();
			try {
			    result = TransHandler.execute(conn, findData, "DEAL");	
			    dataList = GNBRev.getList(result, "DEAL");
			    rstData = GNBRev.getData(result, "DEAL");
			} catch (Exception e) {
			    e.printStackTrace();
			    errMsg = "��� �� ������ �߻��Ͽ����ϴ�.";
			}

			request.setAttribute("page.mn05.bankTransList", dataList);
			request.setAttribute("page.mn05.ErrMsg",  errMsg);
			request.setAttribute("page.mn05.rstData",  rstData);
		}
    }
}