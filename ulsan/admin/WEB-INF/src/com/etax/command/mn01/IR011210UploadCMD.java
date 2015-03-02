/**********************************************************************
* 프로젝트명		  : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명		  : IR011210UploadCMD.java
* 프로그램작성자	: (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용   : 세입 > GIRO대사등록
***********************************************************************/
package com.etax.command.mn01;

import javax.servlet.http.*;

import java.io.*;
import java.sql.*;

import com.oreilly.servlet.MultipartRequest;

import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.entity.CommonEntity;
import com.etax.config.ETaxConfig;
import com.etax.db.mn01.IR011210;
import com.etax.trans.data.GiroBody;

public class IR011210UploadCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR011210UploadCMD.class);

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {
        MultipartRequest mRequest = (MultipartRequest)request.getAttribute("page.upload.multipartRequest");
        String fileName = (String)request.getAttribute("page.upload.fileName");

        String uploadDir = "";

		HttpSession session = request.getSession(false);
        String userid    = (String)session.getAttribute("session.user_id");

		CommonEntity paramInfo = new CommonEntity();
		if (mRequest != null) {
		    paramInfo.setValue("job_dt",      mRequest.getParameter("job_dt"));
		    paramInfo.setValue("user_id",     userid);
		    
		    CommonEntity dataInfo = IR011210.getDataInfo(conn, paramInfo);  //중복 자료 체크
	        if (!"0".equals(dataInfo.getString("CNT")) ) {
	            request.setAttribute("page.mn01.SucMsg",  "이미 등록된 자료입니다.");
	        } else {
	            try {
                    logger.info("paramInfo : " + paramInfo);
                    uploadDir = ETaxConfig.getString("upload_dir") + "/giro/";
                    File file = new File(uploadDir + fileName);
                    BufferedReader in = new BufferedReader(new FileReader(uploadDir + fileName));
                    
                    String str;

                    while ((str = in.readLine()) != null) { //파일의 내용이 있으면 출력
                        GiroBody txtBody = new GiroBody();
                        txtBody.parseMsg(str);
                        if (!"".equals(txtBody.getMsgString("data09")) && "31".equals(txtBody.getMsgString("data08").substring(0, 2))) {
                            //전자납부번호가 존재하고 울산 데이터만 수집
                            //System.out.println(str);
                            IR011210.insertData(conn, paramInfo, txtBody);
                        }
                    }
                    
                    in.close();
                    
                    if (file.exists()) {
                        file.delete();
                        logger.info("업로드 파일 삭제 완료");
                    }
                    request.setAttribute("page.mn01.SucMsg",   "GIRO대사 등록이 완료되었습니다.");
                } catch (Exception e) {
                    logger.info("에러 발생 ::::::  [ " + e + " ] ::::::");
                    request.setAttribute("page.mn01.SucMsg",  "대사등록 작업 중 오류가 발생하였습니다.");
                }
	        } // else -end
		    
		    CommonEntity giroData = IR011210.getGiroData(conn, paramInfo);
		    request.setAttribute("page.mn01.giroData", giroData);
		} // if - end
		
		
    }
}
