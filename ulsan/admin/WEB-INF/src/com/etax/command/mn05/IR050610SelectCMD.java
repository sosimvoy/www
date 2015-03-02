/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR050610SelectCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금배정 > 계좌별거래내역조회
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

    private static Logger logger = Logger.getLogger(IR050610SelectCMD.class); // log4j 설정

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 

		String errMsg = "";  //에러메시지
		String result = "";  //수신전문 String

		CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("acct_no",          request.getParameter("acct_no"));
		paramInfo.setValue("start_date",       request.getParameter("start_date"));
		paramInfo.setValue("end_date",         request.getParameter("end_date"));

		logger.info("paramInfo : " + paramInfo);

		if ("Y".equals(request.getParameter("flag")) ) {  //조회버튼을 통해서 command 명령을 내린건지 판단
			CommonEntity pcNo = TransferNo.transferNo(conn, "");  // 단말번호 조회

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

			List<CommonEntity> dataList =  new ArrayList<CommonEntity>();  // 수신전문 담을 리스트
			CommonEntity rstData = new CommonEntity();
			try {
			    result = TransHandler.execute(conn, findData, "DEAL");	
			    dataList = GNBRev.getList(result, "DEAL");
			    rstData = GNBRev.getData(result, "DEAL");
			} catch (Exception e) {
			    e.printStackTrace();
			    errMsg = "통신 중 에러가 발생하였습니다.";
			}

			request.setAttribute("page.mn05.bankTransList", dataList);
			request.setAttribute("page.mn05.ErrMsg",  errMsg);
			request.setAttribute("page.mn05.rstData",  rstData);
		}
    }
}