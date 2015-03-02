/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR050110SelectCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > �ڱݹ�����ó���ܾ���ȸ
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn05.IR050110;
import com.etax.db.trans.TransDAO;
import com.etax.command.common.TransferNo;
import com.etax.trans.TransHandler;
import com.etax.trans.data.GNBRev;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR050110SelectCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR050110SelectCMD.class); // log4j ����

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{

	    String errMsg = "";  //�����޽���
	    String result = "";  //�������� String

		CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("fis_year",   request.getParameter("fis_year"));
        paramInfo.setValue("part_code",  "00000");
		paramInfo.setValue("acc_code",   "11");
        paramInfo.setValue("acc_type",   "A");

		List<CommonEntity> acctInfoList = TransDAO.getAcctInfo(conn, paramInfo);

		if (acctInfoList.size() == 0)	{
			request.setAttribute("page.mn05.ErrMsg", "��ȸ������ ���°� �����ϴ�.");
		} else {
			CommonEntity pcNo = TransferNo.transferNo(conn, "");
			HashMap<String, String> findData = new HashMap<String, String>();
		    findData.put("common01", "1850");
		    findData.put("common02", "SDS00071");
            findData.put("common04", "7000");
	        findData.put("common05", "200");
		    findData.put("common07", pcNo.getString("TRANS_MANAGE_NO"));
		    findData.put("common12", "632");
		    findData.put("common13", pcNo.getString("M260_TERMINALNO"));
	        findData.put("detail01", TextHandler.getCurrentDate());

			List<CommonEntity> dataList =  new ArrayList<CommonEntity>();  // �������� ���� ����Ʈ

			try {
			    result = TransHandler.execute(conn, findData, acctInfoList, "JAN");	
			    dataList = GNBRev.getList(result, "JAN");
			} catch (Exception e) {
			    e.printStackTrace();
			    errMsg = "��� �� ������ �߻��Ͽ����ϴ�.";
			}
      
			List<CommonEntity> resultList = new ArrayList<CommonEntity>();
			for (int i=0; i<dataList.size(); i++) {
				CommonEntity row = (CommonEntity)dataList.get(i);
				row.setValue("FIS_YEAR",    paramInfo.getString("fis_year"));
				row.setValue("ACCT_NO",     row.getString("detail03"));
				row.setValue("JAN_AMT",     row.getLong("detail04")+"");
				CommonEntity acctNick = TransDAO.getAcctNick(conn, row);
				row.setValue("ACCT_NICK",   acctNick.getString("M300_ACCNAME"));
				resultList.add(row);
			}
			request.setAttribute("page.mn05.resultList", resultList);
			request.setAttribute("page.mn05.ErrMsg", errMsg);
		}			
		
		/* �ڱݹ�����ó���ܾ���ȸ */
		CommonEntity acctData = IR050110.getAcctData(conn, paramInfo);
		request.setAttribute("page.mn05.acctData", acctData);
    }
}