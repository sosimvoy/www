/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR040310SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용   : 세외수입 > 등록내역 상세
****************************************************************/
 
package com.etax.command.mn04;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn04.IR040310;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;


public class IR040310SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR040310SelectCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {

    /* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
      
			paramInfo.setValue("napbujaName",  request.getParameter("napbujaName"));	
		  paramInfo.setValue("semokcode",    request.getParameter("mok"));
		  paramInfo.setValue("fis_year",     request.getParameter("fis_year"));
        if ("".equals(paramInfo.getString("fis_year")) ) {
            paramInfo.setValue("fis_year",    TextHandler.formatDate(TextHandler.getCurrentDate(), "yyyyMMdd", "yyyy"));
        }

    /* 담당자 리스트 */
    List<CommonEntity> managerList = IR040310.getManagerList(conn);
    request.setAttribute("page.mn04.managerList", managerList);
    
    /* 목코드 select box처리쿼리 */
    List<CommonEntity> semokcodeList = IR040310.getsemokcodeList(conn, paramInfo);
    request.setAttribute("page.mn04.semokcodeList", semokcodeList);
    /* 세목코드 selectbox처리쿼리 */
    List<CommonEntity> sesemokcode = IR040310.getsesemokcodeList(conn, paramInfo);
    request.setAttribute("page.mn04.sesemokcode", sesemokcode);
    /* 목코드 선택시 상위코드정보 조회쿼리 */
    CommonEntity sangweesemokList = IR040310.getsangweesemokList(conn, paramInfo);
    request.setAttribute("page.mn04.sangweesemokList", sangweesemokList);

    /* 담당자 리스트 */
    List<CommonEntity> napbuList = IR040310.getnapbuList(conn, paramInfo);
    request.setAttribute("page.mn04.napbuList", napbuList);
    /* 담당자 리스트 */
    CommonEntity napbuaddressList = IR040310.getnapbuaddressList(conn, paramInfo);
    request.setAttribute("page.mn04.napbuaddressList", napbuaddressList);
	}
}
