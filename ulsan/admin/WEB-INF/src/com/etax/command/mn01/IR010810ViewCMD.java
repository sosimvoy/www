/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR010810ViewCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����   : ���� > ���Ա�������������
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
	private static Logger logger = Logger.getLogger(IR010810ViewCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
    
        paramInfo.setValue("file", request.getParameter("file"));
        String fileDir = "/home1/tmax/jeus6/webhome/app_home/report";

        String saveFile = fileDir + request.getParameter("file");
  	
        logger.info("paramInfo : " + paramInfo);

        try 
        { 
                //���ϸ����� ������ �ε��մϴ�... 
            RenderedOp src = JAI.create("fileload", saveFile); 
            OutputStream os = new FileOutputStream(fileDir+"/temp.png"); 
             
            //PNG�������� ��ȯ�� �̿�Ǵ� �Ķ���ͷ�.. 
            //BMPEncodeParam�� �ٸ� ������ �Ķ���͵� ������ API�� �����ϼ���.. 
            PNGEncodeParam param = new PNGEncodeParam.Palette(); 

            //���� ��ȯ�ϴ� �κ��Դϴ�.. �ٸ��������� ��ȯ�ϽŴٸ� PNG�� �ٲ��ּ���.. 
            ImageEncoder enc = ImageCodec.createImageEncoder("PNG", os, param); 
            enc.encode(src); 
            os.close(); 
        } catch(Exception e) { 
            e.printStackTrace(); 
        } 
	}
}
