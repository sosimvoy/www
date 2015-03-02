/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR050510TransCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금배정 > 계좌등록-수취조회 전문
***********************************************************************/
package com.etax.command.mn05;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.entity.CommonEntity;
import com.etax.command.common.TransferNo;
import com.etax.trans.TransHandler;
import com.etax.trans.data.*;

public class IR050510TransCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR050510TransCMD.class); // log4j 설정

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{
    
		CommonEntity pcNo = TransferNo.transferNo(conn, "");
    
		HashMap<String, String> findData = new HashMap<String, String>();
		findData.put("common01", "0300");
		findData.put("common02", "SDS00071");
		findData.put("common04", "6000");
		findData.put("common05", "100");
		findData.put("common07", pcNo.getString("TRANS_MANAGE_NO"));
		findData.put("common12", "632");
		findData.put("common13", pcNo.getString("M260_TERMINALNO"));
		findData.put("detail01", request.getParameter("acct_no"));
		findData.put("detail04", request.getParameter("bank_cd"));
		findData.put("detail06", "10");//최소금액 10원

		logger.info("전문검색사항 : " +  findData);

		String errMsg = "";
		String result = "";
		CommonEntity resultData =  new CommonEntity();
		try	{
		    result = TransHandler.execute(conn, findData, "ACCT");
			resultData = GNBRev.getData(result, "ACCT");
		}	catch (Exception e) {
			e.printStackTrace();
			errMsg = "통신 중 에러가 발생하였습니다.";
		}
    
		request.setAttribute("page.mn05.result", resultData);
		request.setAttribute("page.mn05.ErrMsg", errMsg);
    }
}