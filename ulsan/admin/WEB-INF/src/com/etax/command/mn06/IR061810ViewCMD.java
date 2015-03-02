 /**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR061810ViewCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금운용 > 평잔표조회
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn06.IR061810;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;

public class IR061810ViewCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR061810ViewCMD.class); // log4j 설정

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 

    CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("acc_year",            request.getParameter("acc_year"));
		paramInfo.setValue("acc_mon",             request.getParameter("acc_month"));
		paramInfo.setValue("acc_month",           paramInfo.getString("acc_year")+TextHandler.zeroFill(paramInfo.getString("acc_mon"),2));
		paramInfo.setValue("start_date",          request.getParameter("start_date"));
		paramInfo.setValue("end_date",          request.getParameter("end_date"));

		logger.info("paramInfo : " + paramInfo);

		if (!"".equals(paramInfo.getString("acc_mon")) ) {
		    int last_year =  Integer.parseInt(paramInfo.getString("acc_year"))-1;
		    paramInfo.setValue("last_year",           last_year+"");
		    List<CommonEntity> lastAvgList = new ArrayList<CommonEntity>();  //전년도
		    List<CommonEntity> thisAvgList = new ArrayList<CommonEntity>();  //현년도
		    for (int i=1; i<=12; i++) {
		        paramInfo.setValue("acc_month",        paramInfo.getString("last_year")+TextHandler.zeroFill(i+"",2));
		        CommonEntity rowData = IR061810.getAvgData(conn, paramInfo);
		        rowData.setValue("MONTH",             i+"");
		        rowData.setValue("GONGGUM_AMT",       rowData.getString("GONGGUM_AMT"));
		        rowData.setValue("JEONGGI_AMT",       rowData.getString("JEONGGI_AMT"));
		        rowData.setValue("HWAN_AMT",          rowData.getString("HWAN_AMT"));
		        rowData.setValue("MMDA_AMT",          rowData.getString("MMDA_AMT"));
		        rowData.setValue("SAVE_AMT",          rowData.getString("SAVE_AMT"));
		        rowData.setValue("TOT_AMT",           rowData.getString("TOT_AMT"));

		        lastAvgList.add(rowData);
		    }

		    for (int i=1; i<=Integer.parseInt(paramInfo.getString("acc_mon")); i++) {
		        paramInfo.setValue("acc_month",        paramInfo.getString("acc_year")+TextHandler.zeroFill(i+"",2));
		        CommonEntity rowData = IR061810.getAvgData(conn, paramInfo);
		        rowData.setValue("MONTH",             i+"");
		        rowData.setValue("GONGGUM_AMT",       rowData.getString("GONGGUM_AMT"));
		        rowData.setValue("JEONGGI_AMT",       rowData.getString("JEONGGI_AMT"));
		        rowData.setValue("HWAN_AMT",          rowData.getString("HWAN_AMT"));
		        rowData.setValue("MMDA_AMT",          rowData.getString("MMDA_AMT"));
		        rowData.setValue("SAVE_AMT",          rowData.getString("SAVE_AMT"));
		        rowData.setValue("TOT_AMT",           rowData.getString("TOT_AMT"));

		        thisAvgList.add(rowData);
		    }
    
		    request.setAttribute("page.mn06.lastAvgList",   lastAvgList);  //과년도
		    request.setAttribute("page.mn06.thisAvgList",   thisAvgList);  //현년도
		    request.setAttribute("page.mn06.last_year",   paramInfo.getString("last_year"));
		} else if ("".equals(paramInfo.getString("acc_mon")) ) {
		    List<CommonEntity> giganList = new ArrayList<CommonEntity>();
		    paramInfo.setValue("acc_type",           "A"); //일반회계
		    List<CommonEntity> giganList1 = IR061810.getGiganList(conn, paramInfo);
		    paramInfo.setValue("acc_type",           "B"); //특별회계
            List<CommonEntity> giganList2 = IR061810.getGiganList(conn, paramInfo);
            paramInfo.setValue("acc_type",           "E"); //기금
            List<CommonEntity> giganList3 = IR061810.getGiganList(conn, paramInfo);
            giganList1.remove(0); //일반회계 소계 제거
            giganList.addAll(giganList1);
            giganList.addAll(giganList2);
            giganList.addAll(giganList3);
		    request.setAttribute("page.mn06.giganList", giganList);

		    request.setAttribute("page.mn06.date", paramInfo);
		}
    
    }
}