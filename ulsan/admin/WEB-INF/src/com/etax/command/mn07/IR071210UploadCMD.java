/**********************************************************************
* 프로젝트명		  : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명		  : IR071210UploadCMD.java
* 프로그램작성자	: (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용   : 세입 > 농협자료등록
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
import com.etax.command.common.TransLogInsert;  //로그기록 남기기 위한 import
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
        String prt_date  = TextHandler.getndaybeforeBusinessDate(conn, acc_date,7);  //오늘날짜 기준 전영업일
        String bacc_date  = TextHandler.getndaybeforeBusinessDate(conn, acc_date,1);  //오늘날짜 기준 전영업일

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

        String ago_acc_date = TextHandler.getAgoBusinessDate(conn, TextHandler.addDays(TextHandler.getCurrentDate(),-1));  //오늘날짜 기준 전영업일

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
            paramInfo.setValue("proctype",    "4");                       //광역시세 세입일계표
        } else if ("G7".equals(mRequest.getParameter("data_type")) ) {
            paramInfo.setValue("proctype",    "5");                       //과오납환부일계표(세입)
        } else if ("G8".equals(mRequest.getParameter("data_type")) ) {
            paramInfo.setValue("proctype",    "6");                       //과오납환부일계표(과오납)
        } else if ("G9".equals(mRequest.getParameter("data_type")) ) {
            paramInfo.setValue("proctype",    "3");                       //차량등록사업소 세입일계표
        }
		CommonEntity dataInfo = IR071210.getDataInfo(conn, paramInfo);  //중복 자료 체크
		if (!"0".equals(dataInfo.getString("CNT")) )	{
			request.setAttribute("page.mn07.SucMsg",  "이미 등록된 자료입니다.");
		} else {
      
            if (mRequest != null) {
                try {
            
				    request.setAttribute("work_log", "A01");
				    request.setAttribute("trans_gubun", "161");
                    //로그기록 남기는 클래스및 메소드 호출
                    TransLogInsert tli = new TransLogInsert();
		            tli.execute(request, response, conn);
		            paramInfo.setValue("log_no", tli.getLogNo());

					logger.info("paramInfo : " + paramInfo);

                    uploadDir = ETaxConfig.getString("upload_dir") + "/excel/";
                    File file = new File(uploadDir + fileName);
            
				  
		    	    String semok = ""; //세목코드
		    	    String semok5 = ""; //세목코드
            
		    	    Workbook workbook = Workbook.getWorkbook(file);
                    Sheet sheet = workbook.getSheet(0);

                    int col = sheet.getColumns(); //열
                    int row = sheet.getRows();    //행
					if ("G6".equals(mRequest.getParameter("data_type")) ) {  // 광역시세세입일계표일 경우
                        paramInfo.setValue("data_type1",  "G6");
                        int cnt = 1;
                        for (int n=8; n<row; n++) {
                            String byeargbn = "0";
		    	            CommonEntity data = new CommonEntity();
		    		        semok = TextHandler.replace(TextHandler.replace(sheet.getCell(3,n).getContents(), " ", ""), "　", ""); // 공백제거 후 세목이름 저장
  		  		            semok5 = TextHandler.replace(TextHandler.replace(sheet.getCell(5,n).getContents(), " ", ""), "　", ""); // 공백제거 후 세목이름 저장

                            if ("지난연도".equals(semok) && cnt == 1 ) {
                                cnt ++;
                                if("소계".equals(semok5)) {
                                    continue;
                                }
                                semok = "지난연도" + semok5;
                                byeargbn = "1";
                            } else if ("지난연도".equals(semok) && cnt == 2 ) {
                                if("소계".equals(semok5)) {
                                    continue;
                                }
                                semok = "지난연도" + semok5;
                                byeargbn = "1";
                            }
                            if ("".equals(semok) && !"".equals(semok5)) {
                                if("소계".equals(semok5) || "합계".equals(semok5)) {
                                    continue;
                                } else {
                                    semok = "지난연도" + semok5;
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

							} //농협코드매핑정보가 있는지 체크하는 if 
                
                        } //for loop
					} else if ("G7".equals(mRequest.getParameter("data_type")) || "G8".equals(mRequest.getParameter("data_type")) )	{  //과오납환부일계표
				        paramInfo.setValue("data_type1",  "G7");
						for (int n=6; n<row; n++) {
		    			    CommonEntity data = new CommonEntity();
		    			    semok = TextHandler.replace(TextHandler.replace(sheet.getCell(1,n).getContents(), " ", ""), "　", ""); // 공백제거 후 세목이름 저장
		    			    CommonEntity srtData = IR071210.getSrtData(conn, paramInfo, semok);
		    			    if (srtData.size() > 0 ) {
                                data.setValue("fis_year",    paramInfo.getString("fis_year"));
                                data.setValue("acc_date",    paramInfo.getString("acc_date"));
                                data.setValue("gtaxgbn",     srtData.getString("M550_GTAXGBN"));						
                                data.setValue("taxgbn",      srtData.getString("M550_TAXGBN"));						
                                data.setValue("semokCode",   srtData.getString("M440_SEMOKCODE"));	

                                //중구자료처리
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

                                //남구자료처리
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

                                //동구자료처리
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

                                //북구자료처리
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

                                //울주군자료처리
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
                            }//농협코드매핑정보가 있는지 체크하는 if 

		    			    semok = "지난연도"+TextHandler.replace(TextHandler.replace(sheet.getCell(1,n).getContents(), " ", ""), "　", ""); // 공백제거 후 세목이름 저장
		    			    srtData = IR071210.getSrtData(conn, paramInfo, semok);
		    			    if (srtData.size() > 0 ) {
                                data.setValue("fis_year",    paramInfo.getString("fis_year"));
                                data.setValue("acc_date",    paramInfo.getString("acc_date"));
                                data.setValue("prt_date",    TextHandler.replace(prt_date, " ", ""));
                                data.setValue("gtaxgbn",     srtData.getString("M550_GTAXGBN"));						
                                data.setValue("taxgbn",      srtData.getString("M550_TAXGBN"));						
                                data.setValue("semokCode",   srtData.getString("M440_SEMOKCODE"));	

                                //지난연도 중구자료처리
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

                                //지난연도 남구자료처리
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

                                //지난연도 동구자료처리
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

                                //지난연도 북구자료처리
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

                                //지난연도 울주군자료처리
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
                            } //지난연도 농협코드매핑정보가 있는지 체크하는 if 
                        } //for loop
                        IR071210.incomemst2hst(conn, paramInfo);
					} else if ("G9".equals(mRequest.getParameter("data_type"))) {
                        paramInfo.setValue("data_type1",  "G8");
                        for (int n=0; n<row; n++) {
		    			    CommonEntity data = new CommonEntity();
		    			    semok = TextHandler.replace(TextHandler.replace(sheet.getCell(1,n).getContents(), " ", ""), "　", ""); // 공백제거 후 세목이름 저장

		    			    CommonEntity srtData = IR071210.getSrtData(conn, paramInfo, semok);
		    			    if (srtData.size() > 0 ) {

                                data.setValue("fis_year",    paramInfo.getString("fis_year"));
                                data.setValue("acc_date",    paramInfo.getString("acc_date"));
                                data.setValue("prt_date",    paramInfo.getString("acc_date"));
                                data.setValue("gtaxgbn",     srtData.getString("M550_GTAXGBN"));						
                                data.setValue("taxgbn",      srtData.getString("M550_TAXGBN"));						
                                data.setValue("semokCode",   srtData.getString("M440_SEMOKCODE"));	

                                //중구자료처리
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

                                //남구자료처리
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

                                //동구자료처리
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

                                //북구자료처리
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

                                //울주군자료처리
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
							} //차량매핑정보가 있는지 체크하는 if 
                
                        } //for loop
                        IR071210.incomemst2hst(conn, paramInfo);
                    }
		    		if (file.exists()) {
                        file.delete();
                        logger.info("업로드 파일 삭제 완료");
                    }
					request.setAttribute("page.mn07.SucMsg",   "농협자료 등록이 완료되었습니다.");
                } catch (Exception e) {
                    logger.info("에러 발생 ::::::  [ " + e + " ] ::::::");
                    request.setAttribute("page.mn07.SucMsg",  "데이터 작업 중 오류가 발생하였습니다.");
                }
            } 
		}
    }
}
