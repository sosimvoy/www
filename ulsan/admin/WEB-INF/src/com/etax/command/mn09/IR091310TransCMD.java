/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR091310TransCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 시스템운영 > 통신망개시종료
***********************************************************************/
package com.etax.command.mn09;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR091310;
import com.etax.entity.CommonEntity;
import com.etax.trans.TransHandler;
import com.etax.command.common.TransferNo;
import com.etax.trans.data.*;


public class IR091310TransCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR091310TransCMD.class); // log4j 설정

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    
    
        String errMsg = "";

        String result = "";
        CommonEntity resultData =  new CommonEntity();

        if("init".equals(request.getParameter("gubun")) ) {
            CommonEntity lastInfo = IR091310.getLastInfo(conn);
            request.setAttribute("page.mn09.lastINFO", lastInfo);
        } else {
            CommonEntity transOpen = IR091310.getTransOpen(conn);

            if ("0000".equals(transOpen.getString("M570_ERR_CODE")) ) {
                errMsg = "오늘은 이미 업무개시가 적용되었습니다.";
                request.setAttribute("page.mn09.lastINFO", transOpen);
            } else {
                CommonEntity pcNo = TransferNo.transferNo(conn, "");  // 공통부 세팅
        
                HashMap<String, String> findData = new HashMap<String, String>();
        
                findData.put("common01", "0300");
                findData.put("common04", "1000");
                findData.put("common05", "100");
        
                findData.put("common07", pcNo.getString("TRANS_MANAGE_NO"));
                findData.put("common13", pcNo.getString("M260_TERMINALNO"));
                findData.put("detail01", "1");

                logger.info("전문검색사항 : " +  findData);

                try	{
                    result = TransHandler.execute(conn, findData, "COMM");
                    resultData = GNBRev.getData(result, "COMM");
                } catch (Exception e) {
                    e.printStackTrace();
                    errMsg = "통신 중 에러가 발생하였습니다.";
                    resultData.setValue("common10", "9999");
                    resultData.setValue("common11", "통신장애가 발생하였습니다.다시시도하세요.");
                }

                if ("0000".equals(resultData.getString("common10")) ) {
                    resultData.setValue("common11", "업무개시가 정상 수신되었습니다.");
                }

                IR091310.updateRevData(conn, resultData);  //전문수신 정보 등록

                CommonEntity transResult = IR091310.getTransOpen(conn);
                request.setAttribute("page.mn09.lastINFO", transResult);
            }
        }

        request.setAttribute("page.mn09.ErrMsg", errMsg);
    }
}