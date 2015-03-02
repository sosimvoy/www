/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR071510SelectCMD.java
* 프로그램작성자    : (주)미르이즈
* 프로그램작성일    : 2010-10-01
* 프로그램내용      : 일계/보고서 > 세입세출일계정정(소급처리) 조회
***********************************************************************/
package com.etax.command.mn07;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;

import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn07.IR071510;
import com.etax.entity.CommonEntity;

public class IR071510SelectCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR071510SelectCMD.class); // log4j 설정

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{
    
        CommonEntity paramInfo = new CommonEntity();
        paramInfo.setValue("acc_year",      request.getParameter("acc_year"));  //회계연도
        paramInfo.setValue("acc_date_s",      request.getParameter("acc_date_s"));  //회계일자
        paramInfo.setValue("acc_date_e",      request.getParameter("acc_date_e"));  //세입세출구분
        
        logger.debug("paramInfo : " + paramInfo);
        //본청세출조회
        List<CommonEntity> sechulList = IR071510.getSechulList(conn, paramInfo);
        request.setAttribute("page.mn07.sechulList", sechulList);
    }
}