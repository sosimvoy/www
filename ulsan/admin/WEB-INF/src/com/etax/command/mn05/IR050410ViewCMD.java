/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR050410ViewCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금배정 > 자금(재)배정통지서조회 - 통지서 팝업
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn05.IR050410;
import com.etax.db.mn05.IR050000;
import com.etax.entity.CommonEntity;

public class IR050410ViewCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR050410ViewCMD.class); // log4j 설정

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

		CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("fis_year",           request.getParameter("fis_year"));
		paramInfo.setValue("doc_no",             request.getParameter("doc_no"));
        paramInfo.setValue("acc_date",           request.getParameter("allot_date"));

        CommonEntity manager = IR050410.getManagerName(conn, paramInfo);
		request.setAttribute("page.mn05.manager", manager);
        paramInfo.setValue("doc_code",         manager.getString("M180_DOCUMENTCODE"));
		
        if ("ED05".equals(paramInfo.getString("doc_code")) ) {
            paramInfo.setValue("report_gubun",       "1");
        } else if ("ED06".equals(paramInfo.getString("doc_code")) ) {
            paramInfo.setValue("report_gubun",       "2");
        } else if ("ED00".equals(paramInfo.getString("doc_code")) ) {
            paramInfo.setValue("report_gubun",       "1");
        }
        logger.info("paramInfo : " + paramInfo);

        /* 자금재배정통지서 조회- 배정내역(제1관서지역) */
        List<CommonEntity> bankReportList1 = IR050410.getBankReportList(conn, paramInfo);
        request.setAttribute("page.mn05.cityBaeList1", bankReportList1);

        CommonEntity bonData1 = (CommonEntity)bankReportList1.get(0);
      
        CommonEntity hangul1 = IR050410.getHangulAmt(conn, bonData1);
        bonData1.setValue("HANGUL_AMT",   hangul1.getString("HANGUL_AMT"));
        request.setAttribute("page.mn05.bonData1",   bonData1);
        
        if ("ED00".equals(paramInfo.getString("doc_code")) ) {
            paramInfo.setValue("report_gubun",       "2");
            /* 자금재배정통지서 조회- 배정내역(제1관서지역) */
            List<CommonEntity> bankReportList2 = IR050410.getBankReportList(conn, paramInfo);
            request.setAttribute("page.mn05.cityBaeList2", bankReportList2);

            CommonEntity bonData2 = (CommonEntity)bankReportList2.get(0);
            CommonEntity bonData3 = new CommonEntity();
            bonData3.setValue("ALLOTAMT",  (bonData1.getLong("ALLOTAMT")+bonData2.getLong("ALLOTAMT"))+"");
            
            CommonEntity hangul2 = IR050410.getHangulAmt(conn, bonData3);
            bonData2.setValue("REHANGUL_AMT",   hangul2.getString("HANGUL_AMT"));
            request.setAttribute("page.mn05.bonData2",   bonData2);
        }
        CommonEntity sealInfo = IR050000.getSealInfo(conn);
		request.setAttribute("page.mn05.sealInfo", sealInfo);
     }
}