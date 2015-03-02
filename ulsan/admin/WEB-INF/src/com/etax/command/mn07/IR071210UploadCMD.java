/**********************************************************************
* ������Ʈ��		  : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���		  : IR071210UploadCMD.java
* ���α׷��ۼ���	: (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����   : ���� > �����ڷ���
***********************************************************************/
package com.etax.command.mn07;

import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;
import java.util.*;
import jxl.*;
import jxl.write.*;
import com.oreilly.servlet.MultipartRequest;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.entity.CommonEntity;
import com.etax.command.common.TransLogInsert;  //�αױ�� ����� ���� import
import com.etax.config.ETaxConfig;
import com.etax.db.mn07.IR071210;
import com.etax.util.TextHandler;

public class IR071210UploadCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR071210UploadCMD.class);

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {
        MultipartRequest mRequest = (MultipartRequest)request.getAttribute("page.upload.multipartRequest");
        String fileName = (String)request.getAttribute("page.upload.fileName");

        String uploadDir = "";

		HttpSession session = request.getSession(false);
        String userid    = (String)session.getAttribute("session.user_id");
        String acc_date  = mRequest.getParameter("acc_date");
        String prt_date  = TextHandler.getndaybeforeBusinessDate(conn, acc_date,7);  //���ó�¥ ���� ��������
        String bacc_date  = TextHandler.getndaybeforeBusinessDate(conn, acc_date,1);  //���ó�¥ ���� ��������

		CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("fis_year",    mRequest.getParameter("fis_year"));
		paramInfo.setValue("acc_date",    mRequest.getParameter("acc_date"));
		paramInfo.setValue("data_type",   mRequest.getParameter("data_type"));
		paramInfo.setValue("work_log",    mRequest.getParameter("work_log"));
        if ("G3".equals(paramInfo.getString("data_type")) ) {
            paramInfo.setValue("trans_gubun", "169");
        } else {
		    paramInfo.setValue("trans_gubun", mRequest.getParameter("trans_gubun"));
        }
		paramInfo.setValue("user_id",     userid);

        String ago_acc_date = TextHandler.getAgoBusinessDate(conn, TextHandler.addDays(TextHandler.getCurrentDate(),-1));  //���ó�¥ ���� ��������

        request.setAttribute("page.mn07.ago_acc_date", ago_acc_date);

		if ("G6".equals(mRequest.getParameter("data_type")) || "G8".equals(mRequest.getParameter("data_type")) 
            || "G9".equals(mRequest.getParameter("data_type")) ) {
			paramInfo.setValue("in_type",     "I1");
		} else if ("G7".equals(mRequest.getParameter("data_type"))) {
			paramInfo.setValue("in_type",     "I2");
		}

        if ("G9".equals(paramInfo.getString("data_type")) ) {
            paramInfo.setValue("sunap_gigwan",    "310001");
        } else {
            paramInfo.setValue("sunap_gigwan",    "110000");
        }
		
		if ("G6".equals(mRequest.getParameter("data_type")) ) {
            paramInfo.setValue("proctype",    "4");                       //�����ü� �����ϰ�ǥ
        } else if ("G7".equals(mRequest.getParameter("data_type")) ) {
            paramInfo.setValue("proctype",    "5");                       //������ȯ���ϰ�ǥ(����)
        } else if ("G8".equals(mRequest.getParameter("data_type")) ) {
            paramInfo.setValue("proctype",    "6");                       //������ȯ���ϰ�ǥ(������)
        } else if ("G9".equals(mRequest.getParameter("data_type")) ) {
            paramInfo.setValue("proctype",    "3");                       //������ϻ���� �����ϰ�ǥ
        }
		CommonEntity dataInfo = IR071210.getDataInfo(conn, paramInfo);  //�ߺ� �ڷ� üũ
		if (!"0".equals(dataInfo.getString("CNT")) )	{
			request.setAttribute("page.mn07.SucMsg",  "�̹� ��ϵ� �ڷ��Դϴ�.");
		} else {
      
            if (mRequest != null) {
                try {
            
				    request.setAttribute("work_log", "A01");
				    request.setAttribute("trans_gubun", "161");
                    //�αױ�� ����� Ŭ������ �޼ҵ� ȣ��
                    TransLogInsert tli = new TransLogInsert();
		            tli.execute(request, response, conn);
		            paramInfo.setValue("log_no", tli.getLogNo());

					logger.info("paramInfo : " + paramInfo);

                    uploadDir = ETaxConfig.getString("upload_dir") + "/excel/";
                    File file = new File(uploadDir + fileName);
            
				  
		    	    String semok = ""; //�����ڵ�
		    	    String semok5 = ""; //�����ڵ�
            
		    	    Workbook workbook = Workbook.getWorkbook(file);
                    Sheet sheet = workbook.getSheet(0);

                    int col = sheet.getColumns(); //��
                    int row = sheet.getRows();    //��
					if ("G6".equals(mRequest.getParameter("data_type")) ) {  // �����ü������ϰ�ǥ�� ���
                        paramInfo.setValue("data_type1",  "G6");
                        int cnt = 1;
                        for (int n=8; n<row; n++) {
                            String byeargbn = "0";
		    	            CommonEntity data = new CommonEntity();
		    		        semok = TextHandler.replace(TextHandler.replace(sheet.getCell(3,n).getContents(), " ", ""), "��", ""); // �������� �� �����̸� ����
  		  		            semok5 = TextHandler.replace(TextHandler.replace(sheet.getCell(5,n).getContents(), " ", ""), "��", ""); // �������� �� �����̸� ����

                            if ("��������".equals(semok) && cnt == 1 ) {
                                cnt ++;
                                if("�Ұ�".equals(semok5)) {
                                    continue;
                                }
                                semok = "��������" + semok5;
                                byeargbn = "1";
                            } else if ("��������".equals(semok) && cnt == 2 ) {
                                if("�Ұ�".equals(semok5)) {
                                    continue;
                                }
                                semok = "��������" + semok5;
                                byeargbn = "1";
                            }
                            if ("".equals(semok) && !"".equals(semok5)) {
                                if("�Ұ�".equals(semok5) || "�հ�".equals(semok5)) {
                                    continue;
                                } else {
                                    semok = "��������" + semok5;
                                    byeargbn = "1";
                                }
                            }
		    			    CommonEntity srtData = IR071210.getSrtData(conn, paramInfo, semok);
		    			    if (srtData.size() > 0 ) {
                  
                                data.setValue("fis_year",    paramInfo.getString("fis_year"));
                                data.setValue("acc_date",    paramInfo.getString("acc_date"));
                                data.setValue("prt_date",    TextHandler.replace(prt_date, " ", ""));
                                data.setValue("gtaxgbn",     srtData.getString("M550_GTAXGBN"));						
                                data.setValue("taxgbn",      srtData.getString("M550_TAXGBN"));						
                                data.setValue("semokCode",   srtData.getString("M440_SEMOKCODE"));						
                                data.setValue("proctype",    "4");
                                data.setValue("sunapamt",    TextHandler.unformatNumber((sheet.getCell(9,n).getContents()).trim()));				
                                data.setValue("sunapcnt",    "1");
                                data.setValue("hwanbuamt",   "0");
                                data.setValue("hwanbucnt",   "0");
                                data.setValue("partCode",    srtData.getString("M440_PARTCODE11"));
                                if(!"".equals(data.getString("partCode"))) {
                                    if (!"".equals(data.getString("sunapamt")) && !"0".equals(data.getString("sunapamt")) )	{
                                        IR071210.inserthist(conn, data, paramInfo);
                                        IR071210.insertmast(conn, data, paramInfo);
                                    }
                                }

		    				    data.setValue("sunapamt",    TextHandler.unformatNumber((sheet.getCell(11,n).getContents()).trim()));				
                                data.setValue("partCode",    srtData.getString("M440_PARTCODE12"));
                                if(!"".equals(data.getString("partCode"))) {
                                    if (!"".equals(data.getString("sunapamt")) && !"0".equals(data.getString("sunapamt")) )	{
                                        IR071210.inserthist(conn, data, paramInfo);
                                        IR071210.insertmast(conn, data, paramInfo);
                                    }
                                }

		    				    data.setValue("sunapamt",    TextHandler.unformatNumber((sheet.getCell(15,n).getContents()).trim()));				
                                data.setValue("partCode",    srtData.getString("M440_PARTCODE13"));
                                if(!"".equals(data.getString("partCode"))) {
                                    if (!"".equals(data.getString("sunapamt")) && !"0".equals(data.getString("sunapamt")) )	{
                                        IR071210.inserthist(conn, data, paramInfo);
                                        IR071210.insertmast(conn, data, paramInfo);
                                    }
                                }

		    				    data.setValue("sunapamt",    TextHandler.unformatNumber((sheet.getCell(17,n).getContents()).trim()));				
                                data.setValue("partCode",    srtData.getString("M440_PARTCODE14"));
                                if(!"".equals(data.getString("partCode"))) {
                                    if (!"".equals(data.getString("sunapamt")) && !"0".equals(data.getString("sunapamt")) )	{
                                        IR071210.inserthist(conn, data, paramInfo);
                                        IR071210.insertmast(conn, data, paramInfo);
                                    }
                                }
		    				    data.setValue("sunapamt",    TextHandler.unformatNumber((sheet.getCell(19,n).getContents()).trim()));				
                                data.setValue("partCode",    srtData.getString("M440_PARTCODE15"));
                                if(!"".equals(data.getString("partCode"))) {
                                    if (!"".equals(data.getString("sunapamt")) && !"0".equals(data.getString("sunapamt")) )	{
                                        IR071210.inserthist(conn, data, paramInfo);
                                        IR071210.insertmast(conn, data, paramInfo);
                                    }
                                }

							} //�����ڵ���������� �ִ��� üũ�ϴ� if 
                
                        } //for loop
					} else if ("G7".equals(mRequest.getParameter("data_type")) || "G8".equals(mRequest.getParameter("data_type")) )	{  //������ȯ���ϰ�ǥ
				        paramInfo.setValue("data_type1",  "G7");
						for (int n=6; n<row; n++) {
		    			    CommonEntity data = new CommonEntity();
		    			    semok = TextHandler.replace(TextHandler.replace(sheet.getCell(1,n).getContents(), " ", ""), "��", ""); // �������� �� �����̸� ����
		    			    CommonEntity srtData = IR071210.getSrtData(conn, paramInfo, semok);
		    			    if (srtData.size() > 0 ) {
                                data.setValue("fis_year",    paramInfo.getString("fis_year"));
                                data.setValue("acc_date",    paramInfo.getString("acc_date"));
                                data.setValue("gtaxgbn",     srtData.getString("M550_GTAXGBN"));						
                                data.setValue("taxgbn",      srtData.getString("M550_TAXGBN"));						
                                data.setValue("semokCode",   srtData.getString("M440_SEMOKCODE"));	

                                //�߱��ڷ�ó��
	      				        data.setValue("partCode",    srtData.getString("M440_PARTCODE11"));
                                if ("G7".equals(mRequest.getParameter("data_type"))) {
                                    data.setValue("prt_date",    TextHandler.replace(prt_date, " ", ""));
                                    data.setValue("proctype",    "5");
                                    data.setValue("chkamt",      TextHandler.unformatNumber((sheet.getCell(4,n).getContents()).trim()));				
                                    data.setValue("sunapamt",    TextHandler.unformatNumber((sheet.getCell(4,n).getContents()).trim()));				
                                    data.setValue("sunapcnt",    "1");
                                    data.setValue("hwanbuamt",   "0");
                                    data.setValue("hwanbucnt",   "0");
                                } else {
                                    data.setValue("prt_date",    TextHandler.replace(bacc_date, " ", ""));
                                    data.setValue("proctype",    "6");
                                    data.setValue("chkamt",      TextHandler.unformatNumber((sheet.getCell(4,n).getContents()).trim()));				
                                    data.setValue("sunapamt",    "0");
                                    data.setValue("sunapcnt",    "0");
  		    				        data.setValue("hwanbuamt",   TextHandler.unformatNumber((sheet.getCell(4,n).getContents()).trim()));				
                                    data.setValue("hwanbucnt",   "1");
                                }
                                if(!"".equals(data.getString("partCode"))) {
                                    if (!"".equals(data.getString("chkamt")) && !"0".equals(data.getString("chkamt")) )	{
                                        IR071210.inserthist(conn, data, paramInfo);
                                    }
                                }

                                //�����ڷ�ó��
	      				        data.setValue("partCode",    srtData.getString("M440_PARTCODE12"));
                                if ("G7".equals(mRequest.getParameter("data_type"))) {
                                    data.setValue("prt_date",    TextHandler.replace(prt_date, " ", ""));
                                    data.setValue("proctype",    "5");
                                    data.setValue("chkamt",      TextHandler.unformatNumber((sheet.getCell(5,n).getContents()).trim()));				
                                    data.setValue("sunapamt",    TextHandler.unformatNumber((sheet.getCell(5,n).getContents()).trim()));				
                                    data.setValue("sunapcnt",    "1");
                                    data.setValue("hwanbuamt",   "0");
                                    data.setValue("hwanbucnt",   "0");
                                } else {
                                    data.setValue("prt_date",    TextHandler.replace(bacc_date, " ", ""));
                                    data.setValue("proctype",    "6");
                                    data.setValue("chkamt",      TextHandler.unformatNumber((sheet.getCell(5,n).getContents()).trim()));				
                                    data.setValue("sunapamt",    "0");
                                    data.setValue("sunapcnt",    "0");
  		    				        data.setValue("hwanbuamt",   TextHandler.unformatNumber((sheet.getCell(5,n).getContents()).trim()));				
                                    data.setValue("hwanbucnt",   "1");
                                }
                                if(!"".equals(data.getString("partCode"))) {
                                    if (!"".equals(data.getString("chkamt")) && !"0".equals(data.getString("chkamt")) )	{
                                        IR071210.inserthist(conn, data, paramInfo);
                                    }
                                }

                                //�����ڷ�ó��
	      				        data.setValue("partCode",    srtData.getString("M440_PARTCODE13"));
                                if ("G7".equals(mRequest.getParameter("data_type"))) {
                                    data.setValue("prt_date",    TextHandler.replace(prt_date, " ", ""));
                                    data.setValue("proctype",    "5");
                                    data.setValue("chkamt",      TextHandler.unformatNumber((sheet.getCell(6,n).getContents()).trim()));				
                                    data.setValue("sunapamt",    TextHandler.unformatNumber((sheet.getCell(6,n).getContents()).trim()));				
                                    data.setValue("sunapcnt",    "1");
                                    data.setValue("hwanbuamt",   "0");
                                    data.setValue("hwanbucnt",   "0");
                                } else {
                                    data.setValue("prt_date",    TextHandler.replace(bacc_date, " ", ""));
                                    data.setValue("proctype",    "6");
                                    data.setValue("chkamt",      TextHandler.unformatNumber((sheet.getCell(6,n).getContents()).trim()));				
                                    data.setValue("sunapamt",    "0");
                                    data.setValue("sunapcnt",    "0");
  		    				        data.setValue("hwanbuamt",   TextHandler.unformatNumber((sheet.getCell(6,n).getContents()).trim()));				
                                    data.setValue("hwanbucnt",   "1");
                                }
                                if(!"".equals(data.getString("partCode"))) {
                                    if (!"".equals(data.getString("chkamt")) && !"0".equals(data.getString("chkamt")) )	{
                                        IR071210.inserthist(conn, data, paramInfo);
                                    }
                                }

                                //�ϱ��ڷ�ó��
	      				        data.setValue("partCode",    srtData.getString("M440_PARTCODE14"));
                                if ("G7".equals(mRequest.getParameter("data_type"))) {
  		    				        data.setValue("prt_date",    TextHandler.replace(prt_date, " ", ""));
                                    data.setValue("proctype",    "5");
                                    data.setValue("chkamt",      TextHandler.unformatNumber((sheet.getCell(7,n).getContents()).trim()));				
                                    data.setValue("sunapamt",    TextHandler.unformatNumber((sheet.getCell(7,n).getContents()).trim()));				
                                    data.setValue("sunapcnt",    "1");
                                    data.setValue("hwanbuamt",   "0");
                                    data.setValue("hwanbucnt",   "0");
                                } else {
                                    data.setValue("prt_date",    TextHandler.replace(bacc_date, " ", ""));
                                    data.setValue("proctype",    "6");
                                    data.setValue("chkamt",      TextHandler.unformatNumber((sheet.getCell(7,n).getContents()).trim()));				
                                    data.setValue("sunapamt",    "0");
                                    data.setValue("sunapcnt",    "0");
  		    				        data.setValue("hwanbuamt",   TextHandler.unformatNumber((sheet.getCell(7,n).getContents()).trim()));				
                                    data.setValue("hwanbucnt",   "1");
                                }
                                if(!"".equals(data.getString("partCode"))) {
                                    if (!"".equals(data.getString("chkamt")) && !"0".equals(data.getString("chkamt")) )	{
                                        IR071210.inserthist(conn, data, paramInfo);
                                    }
                                }

                                //���ֱ��ڷ�ó��
	      				        data.setValue("partCode",    srtData.getString("M440_PARTCODE15"));
                                if ("G7".equals(mRequest.getParameter("data_type"))) {
                                    data.setValue("prt_date",    TextHandler.replace(prt_date, " ", ""));
                                    data.setValue("proctype",    "5");
                                    data.setValue("chkamt",      TextHandler.unformatNumber((sheet.getCell(8,n).getContents()).trim()));				
                                    data.setValue("sunapamt",    TextHandler.unformatNumber((sheet.getCell(8,n).getContents()).trim()));				
                                    data.setValue("sunapcnt",    "1");
                                    data.setValue("hwanbuamt",   "0");
                                    data.setValue("hwanbucnt",   "0");
                                } else {
                                    data.setValue("prt_date",    TextHandler.replace(bacc_date, " ", ""));
                                    data.setValue("proctype",    "6");
                                    data.setValue("chkamt",      TextHandler.unformatNumber((sheet.getCell(8,n).getContents()).trim()));				
                                    data.setValue("sunapamt",    "0");
                                    data.setValue("sunapcnt",    "0");
  		    				        data.setValue("hwanbuamt",   TextHandler.unformatNumber((sheet.getCell(8,n).getContents()).trim()));				
                                    data.setValue("hwanbucnt",   "1");
                                }
                                if(!"".equals(data.getString("partCode"))) {
                                    if (!"".equals(data.getString("chkamt")) && !"0".equals(data.getString("chkamt")) )	{
                                        IR071210.inserthist(conn, data, paramInfo);
                                    }
                                }
                            }//�����ڵ���������� �ִ��� üũ�ϴ� if 

		    			    semok = "��������"+TextHandler.replace(TextHandler.replace(sheet.getCell(1,n).getContents(), " ", ""), "��", ""); // �������� �� �����̸� ����
		    			    srtData = IR071210.getSrtData(conn, paramInfo, semok);
		    			    if (srtData.size() > 0 ) {
                                data.setValue("fis_year",    paramInfo.getString("fis_year"));
                                data.setValue("acc_date",    paramInfo.getString("acc_date"));
                                data.setValue("prt_date",    TextHandler.replace(prt_date, " ", ""));
                                data.setValue("gtaxgbn",     srtData.getString("M550_GTAXGBN"));						
                                data.setValue("taxgbn",      srtData.getString("M550_TAXGBN"));						
                                data.setValue("semokCode",   srtData.getString("M440_SEMOKCODE"));	

                                //�������� �߱��ڷ�ó��
	      				        data.setValue("partCode",    srtData.getString("M440_PARTCODE21"));
                                if ("G7".equals(mRequest.getParameter("data_type"))) {
                                    data.setValue("prt_date",    TextHandler.replace(prt_date, " ", ""));
                                    data.setValue("proctype",    "5");
                                    data.setValue("chkamt",      TextHandler.unformatNumber((sheet.getCell(10,n).getContents()).trim()));				
                                    data.setValue("sunapamt",    TextHandler.unformatNumber((sheet.getCell(10,n).getContents()).trim()));				
                                    data.setValue("sunapcnt",    "1");
                                    data.setValue("hwanbuamt",   "0");
                                    data.setValue("hwanbucnt",   "0");
                                } else {
                                    data.setValue("prt_date",    TextHandler.replace(bacc_date, " ", ""));
                                    data.setValue("proctype",    "6");
                                    data.setValue("chkamt",      TextHandler.unformatNumber((sheet.getCell(10,n).getContents()).trim()));				
                                    data.setValue("sunapamt",    "0");
                                    data.setValue("sunapcnt",    "0");
  		    				        data.setValue("hwanbuamt",   TextHandler.unformatNumber((sheet.getCell(10,n).getContents()).trim()));				
                                    data.setValue("hwanbucnt",   "1");
                                }
                                if(!"".equals(data.getString("partCode"))) {
                                    if (!"".equals(data.getString("chkamt")) && !"0".equals(data.getString("chkamt")) )	{
                                        IR071210.inserthist(conn, data, paramInfo);
                                    }
                                }

                                //�������� �����ڷ�ó��
	      				        data.setValue("partCode",    srtData.getString("M440_PARTCODE22"));
                                if ("G7".equals(mRequest.getParameter("data_type"))) {
                                    data.setValue("prt_date",    TextHandler.replace(prt_date, " ", ""));
                                    data.setValue("proctype",    "5");
                                    data.setValue("chkamt",      TextHandler.unformatNumber((sheet.getCell(11,n).getContents()).trim()));				
                                    data.setValue("sunapamt",    TextHandler.unformatNumber((sheet.getCell(11,n).getContents()).trim()));				
                                    data.setValue("sunapcnt",    "1");
                                    data.setValue("hwanbuamt",   "0");
                                    data.setValue("hwanbucnt",   "0");
                                } else {
                                    data.setValue("prt_date",    TextHandler.replace(bacc_date, " ", ""));
                                    data.setValue("proctype",    "6");
                                    data.setValue("chkamt",      TextHandler.unformatNumber((sheet.getCell(11,n).getContents()).trim()));				
                                    data.setValue("sunapamt",    "0");
                                    data.setValue("sunapcnt",    "0");
  		    				        data.setValue("hwanbuamt",   TextHandler.unformatNumber((sheet.getCell(11,n).getContents()).trim()));				
                                    data.setValue("hwanbucnt",   "1");
                                }
                                if(!"".equals(data.getString("partCode"))) {
                                    if (!"".equals(data.getString("chkamt")) && !"0".equals(data.getString("chkamt")) )	{
                                        IR071210.inserthist(conn, data, paramInfo);
                                    }
                                }

                                //�������� �����ڷ�ó��
	      				        data.setValue("partCode",    srtData.getString("M440_PARTCODE23"));
                                if ("G7".equals(mRequest.getParameter("data_type"))) {
                                    data.setValue("prt_date",    TextHandler.replace(prt_date, " ", ""));
                                    data.setValue("proctype",    "5");
                                    data.setValue("chkamt",      TextHandler.unformatNumber((sheet.getCell(12,n).getContents()).trim()));				
                                    data.setValue("sunapamt",    TextHandler.unformatNumber((sheet.getCell(12,n).getContents()).trim()));				
                                    data.setValue("sunapcnt",    "1");
                                    data.setValue("hwanbuamt",   "0");
                                    data.setValue("hwanbucnt",   "0");
                                } else {
                                    data.setValue("prt_date",    TextHandler.replace(bacc_date, " ", ""));
                                    data.setValue("proctype",    "6");
                                    data.setValue("chkamt",      TextHandler.unformatNumber((sheet.getCell(12,n).getContents()).trim()));				
                                    data.setValue("sunapamt",    "0");
                                    data.setValue("sunapcnt",    "0");
  		    				        data.setValue("hwanbuamt",   TextHandler.unformatNumber((sheet.getCell(12,n).getContents()).trim()));				
                                    data.setValue("hwanbucnt",   "1");
                                }
                                if(!"".equals(data.getString("partCode"))) {
                                    if (!"".equals(data.getString("chkamt")) && !"0".equals(data.getString("chkamt")) )	{
                                        IR071210.inserthist(conn, data, paramInfo);
                                    }
                                }

                                //�������� �ϱ��ڷ�ó��
	      				        data.setValue("partCode",    srtData.getString("M440_PARTCODE24"));
                                if ("G7".equals(mRequest.getParameter("data_type"))) {
                                    data.setValue("prt_date",    TextHandler.replace(prt_date, " ", ""));
                                    data.setValue("proctype",    "5");
                                    data.setValue("chkamt",      TextHandler.unformatNumber((sheet.getCell(13,n).getContents()).trim()));				
                                    data.setValue("sunapamt",    TextHandler.unformatNumber((sheet.getCell(13,n).getContents()).trim()));				
                                    data.setValue("sunapcnt",    "1");
                                    data.setValue("hwanbuamt",   "0");
                                    data.setValue("hwanbucnt",   "0");
                                } else {
                                    data.setValue("prt_date",    TextHandler.replace(bacc_date, " ", ""));
                                    data.setValue("proctype",    "6");
                                    data.setValue("chkamt",      TextHandler.unformatNumber((sheet.getCell(13,n).getContents()).trim()));				
                                    data.setValue("sunapamt",    "0");
                                    data.setValue("sunapcnt",    "0");
  		    				        data.setValue("hwanbuamt",   TextHandler.unformatNumber((sheet.getCell(13,n).getContents()).trim()));				
                                    data.setValue("hwanbucnt",   "1");
                                }
                                if(!"".equals(data.getString("partCode"))) {
                                    if (!"".equals(data.getString("chkamt")) && !"0".equals(data.getString("chkamt")) )	{
                                        IR071210.inserthist(conn, data, paramInfo);
                                    }
                                }

                                //�������� ���ֱ��ڷ�ó��
	      				        data.setValue("partCode",    srtData.getString("M440_PARTCODE25"));
                                if ("G7".equals(mRequest.getParameter("data_type"))) {
                                    data.setValue("prt_date",    TextHandler.replace(prt_date, " ", ""));
                                    data.setValue("proctype",    "5");
                                    data.setValue("chkamt",      TextHandler.unformatNumber((sheet.getCell(14,n).getContents()).trim()));				
                                    data.setValue("sunapamt",    TextHandler.unformatNumber((sheet.getCell(14,n).getContents()).trim()));				
                                    data.setValue("sunapcnt",    "1");
                                    data.setValue("hwanbuamt",   "0");
                                    data.setValue("hwanbucnt",   "0");
                                } else {
                                    data.setValue("prt_date",    TextHandler.replace(bacc_date, " ", ""));
                                    data.setValue("proctype",    "6");
                                    data.setValue("chkamt",      TextHandler.unformatNumber((sheet.getCell(14,n).getContents()).trim()));				
                                    data.setValue("sunapamt",    "0");
                                    data.setValue("sunapcnt",    "0");
  		    				        data.setValue("hwanbuamt",   TextHandler.unformatNumber((sheet.getCell(14,n).getContents()).trim()));				
                                    data.setValue("hwanbucnt",   "1");
                                }
                                if(!"".equals(data.getString("partCode"))) {
                                    if (!"".equals(data.getString("chkamt")) && !"0".equals(data.getString("chkamt")) )	{
                                        IR071210.inserthist(conn, data, paramInfo);
                                    }
                                }
                            } //�������� �����ڵ���������� �ִ��� üũ�ϴ� if 
                        } //for loop
                        IR071210.incomemst2hst(conn, paramInfo);
					} else if ("G9".equals(mRequest.getParameter("data_type"))) {
                        paramInfo.setValue("data_type1",  "G8");
                        for (int n=0; n<row; n++) {
		    			    CommonEntity data = new CommonEntity();
		    			    semok = TextHandler.replace(TextHandler.replace(sheet.getCell(1,n).getContents(), " ", ""), "��", ""); // �������� �� �����̸� ����

		    			    CommonEntity srtData = IR071210.getSrtData(conn, paramInfo, semok);
		    			    if (srtData.size() > 0 ) {

                                data.setValue("fis_year",    paramInfo.getString("fis_year"));
                                data.setValue("acc_date",    paramInfo.getString("acc_date"));
                                data.setValue("prt_date",    paramInfo.getString("acc_date"));
                                data.setValue("gtaxgbn",     srtData.getString("M550_GTAXGBN"));						
                                data.setValue("taxgbn",      srtData.getString("M550_TAXGBN"));						
                                data.setValue("semokCode",   srtData.getString("M440_SEMOKCODE"));	

                                //�߱��ڷ�ó��
	      				        data.setValue("partCode",    srtData.getString("M440_PARTCODE11"));

                                data.setValue("proctype",    "3");
                                data.setValue("sunapamt",    TextHandler.unformatNumber((sheet.getCell(3,n).getContents()).trim()));				
                                data.setValue("sunapcnt",    "1");
                                data.setValue("hwanbuamt",   "0");
                                data.setValue("hwanbucnt",   "0");

                                if(!"".equals(data.getString("partCode"))) {
                                    if (!"".equals(data.getString("sunapamt")) && !"0".equals(data.getString("sunapamt")) )	{
                                        IR071210.inserthist(conn, data, paramInfo);
                                    }
                                }

                                //�����ڷ�ó��
	      				        data.setValue("partCode",    srtData.getString("M440_PARTCODE12"));
  		    				    data.setValue("proctype",    "3");
  		    				    data.setValue("sunapamt",    TextHandler.unformatNumber((sheet.getCell(5,n).getContents()).trim()));				
                                data.setValue("sunapcnt",    "1");
                                data.setValue("hwanbuamt",   "0");
                                data.setValue("hwanbucnt",   "0");

                                if(!"".equals(data.getString("partCode"))) {
                                    if (!"".equals(data.getString("sunapamt")) && !"0".equals(data.getString("sunapamt")) )	{
                                        IR071210.inserthist(conn, data, paramInfo);
                                    }
                                }

                                //�����ڷ�ó��
	      				        data.setValue("partCode",    srtData.getString("M440_PARTCODE13"));
                                data.setValue("proctype",    "3");
                                data.setValue("sunapamt",    TextHandler.unformatNumber((sheet.getCell(7,n).getContents()).trim()));				
                                data.setValue("sunapcnt",    "1");
                                data.setValue("hwanbuamt",   "0");
                                data.setValue("hwanbucnt",   "0");

                                if(!"".equals(data.getString("partCode"))) {
                                    if (!"".equals(data.getString("sunapamt")) && !"0".equals(data.getString("sunapamt")) )	{
                                        IR071210.inserthist(conn, data, paramInfo);
                                    }
                                }

                                //�ϱ��ڷ�ó��
	      				        data.setValue("partCode",    srtData.getString("M440_PARTCODE14"));
  		    				    data.setValue("proctype",    "3");
  		    				    data.setValue("sunapamt",    TextHandler.unformatNumber((sheet.getCell(9,n).getContents()).trim()));				
                                data.setValue("sunapcnt",    "1");
                                data.setValue("hwanbuamt",   "0");
                                data.setValue("hwanbucnt",   "0");

                                if(!"".equals(data.getString("partCode"))) {
                                    if (!"".equals(data.getString("sunapamt")) && !"0".equals(data.getString("sunapamt")) )	{
                                        IR071210.inserthist(conn, data, paramInfo);
                                    }
                                }

                                //���ֱ��ڷ�ó��
	      				        data.setValue("partCode",    srtData.getString("M440_PARTCODE15"));
  		    				    data.setValue("proctype",    "3");
  		    				    data.setValue("sunapamt",    TextHandler.unformatNumber((sheet.getCell(11,n).getContents()).trim()));				
                                data.setValue("sunapcnt",    "1");
                                data.setValue("hwanbuamt",   "0");
                                data.setValue("hwanbucnt",   "0");

                                if(!"".equals(data.getString("partCode"))) {
                                    if (!"".equals(data.getString("sunapamt")) && !"0".equals(data.getString("sunapamt")) )	{
                                        IR071210.inserthist(conn, data, paramInfo);
                                    }
                                }
							} //�������������� �ִ��� üũ�ϴ� if 
                
                        } //for loop
                        IR071210.incomemst2hst(conn, paramInfo);
                    }
		    		if (file.exists()) {
                        file.delete();
                        logger.info("���ε� ���� ���� �Ϸ�");
                    }
					request.setAttribute("page.mn07.SucMsg",   "�����ڷ� ����� �Ϸ�Ǿ����ϴ�.");
                } catch (Exception e) {
                    logger.info("���� �߻� ::::::  [ " + e + " ] ::::::");
                    request.setAttribute("page.mn07.SucMsg",  "������ �۾� �� ������ �߻��Ͽ����ϴ�.");
                }
            } 
		}
    }
}
