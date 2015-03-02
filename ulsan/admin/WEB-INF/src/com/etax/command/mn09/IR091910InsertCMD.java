/*****************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR091910InsertCMD.java
* 프로그램작성자	: (주)미르이즈
* 프로그램작성일	: 2012-02-13
* 프로그램내용		: 시스템운영 > 부서별문서정보 등록
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR091910;
import com.etax.entity.CommonEntity;

public class IR091910InsertCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR091910InsertCMD.class);	// log4j 설정
	
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("accgubun",      request.getParameter("accgubun"));
		paramInfo.setValue("reportgubun",   request.getParameter("reportgubun"));
        paramInfo.setValue("list_part",     request.getParameter("list_part"));

        paramInfo.setValue("partcode",      request.getParameter("partcode"));
		paramInfo.setValue("reportcode",    request.getParameter("reportcode"));
        paramInfo.setValue("acccode",       request.getParameter("acccode"));
        paramInfo.setValue("elecyn",        request.getParameter("elecyn"));
        paramInfo.setValue("ilgwalyn",      request.getParameter("ilgwalyn"));
        
        String SucMsg = "";
        CommonEntity ilgwal = IR091910.getIlgwalInfo(conn, paramInfo);
        if (ilgwal.size() > 0 && !paramInfo.getString("ilgwalyn").equals(ilgwal.getString("M630_ILGWALYN")) ) {
            SucMsg = "기존자료와 일괄결재여부가 동일하지 않습니다. 기존자료 삭제 후 등록하세요.";
        } else {
            CommonEntity count = IR091910.getCount(conn, paramInfo);
            if ("0".equals(count.getString("CNT")) ) {
                try {
                    IR091910.insertDocInfo(conn, paramInfo);
                    SucMsg = "문서정보자료가 등록되었습니다.";
                } catch (Exception e) {
                    logger.info("IR091910InsertCMD exception : [" + e + "]");
                    SucMsg = "등록 중 오류가 발생하였습니다.";
                }
            } else {
                SucMsg = "이미 등록된 자료입니다.";
            }
        }
        request.setAttribute("page.mn09.SucMsg", SucMsg);
        
		/* 등록form의 부서자료 조회 */
		List<CommonEntity> partList = IR091910.getPartList(conn, paramInfo);
		request.setAttribute("page.mn09.partList", partList);

		/* 등록form의 문서종류 조회 */
		List<CommonEntity> docList = IR091910.getDocList(conn, paramInfo);
		request.setAttribute("page.mn09.docList", docList);

        /* 등록form의 문서종류 조회 */
		List<CommonEntity> accList = IR091910.getAccList(conn, paramInfo);
		request.setAttribute("page.mn09.accList", accList);

        /* 부서별 문서정보 조회 */
		List<CommonEntity> docInfoList = IR091910.getDocInfoList(conn, paramInfo);
		request.setAttribute("page.mn09.docInfoList", docInfoList);
	}
}