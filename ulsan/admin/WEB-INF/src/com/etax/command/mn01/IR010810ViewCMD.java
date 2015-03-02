/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR010810ViewCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용   : 세입 > 세입금정정필통지서
****************************************************************/

package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.PNGEncodeParam;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.entity.CommonEntity;


public class IR010810ViewCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR010810ViewCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
    
        paramInfo.setValue("file", request.getParameter("file"));
        String fileDir = "/home1/tmax/jeus6/webhome/app_home/report";

        String saveFile = fileDir + request.getParameter("file");
  	
        logger.info("paramInfo : " + paramInfo);

        try 
        { 
                //파일명으로 파일을 로딩합니다... 
            RenderedOp src = JAI.create("fileload", saveFile); 
            OutputStream os = new FileOutputStream(fileDir+"/temp.png"); 
             
            //PNG형식으로 변환시 이용되는 파라미터로.. 
            //BMPEncodeParam과 다른 형식의 파라미터도 있으니 API를 참고하세요.. 
            PNGEncodeParam param = new PNGEncodeParam.Palette(); 

            //실제 변환하는 부분입니다.. 다른형식으로 변환하신다면 PNG를 바꿔주세요.. 
            ImageEncoder enc = ImageCodec.createImageEncoder("PNG", os, param); 
            enc.encode(src); 
            os.close(); 
        } catch(Exception e) { 
            e.printStackTrace(); 
        } 
	}
}
