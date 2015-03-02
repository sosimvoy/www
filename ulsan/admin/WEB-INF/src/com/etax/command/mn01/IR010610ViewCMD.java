/**********************************************************************
* ������Ʈ��		  : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���		  : IR010610ViewCMD.java
* ���α׷��ۼ���	: (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����   : ���� > �����ڷ���
***********************************************************************/
package com.etax.command.mn01;

import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;
import java.util.*;
import jxl.*;
import com.oreilly.servlet.MultipartRequest;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.entity.CommonEntity;
import com.etax.config.ETaxConfig;
import com.etax.db.mn01.IR010610;
import com.etax.util.TextHandler;

public class IR010610ViewCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR010610ViewCMD.class);

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {
        MultipartRequest mRequest = (MultipartRequest)request.getAttribute("page.upload.multipartRequest");
        String fileName = (String)request.getAttribute("page.upload.fileName");

        String uploadDir = "";

        HttpSession session = request.getSession(false);
        String userid    = (String)session.getAttribute("session.user_id");

        CommonEntity paramInfo = new CommonEntity();
        paramInfo.setValue("fis_year",    mRequest.getParameter("fis_year"));
        paramInfo.setValue("acc_date",    mRequest.getParameter("acc_date"));
        paramInfo.setValue("data_type",   mRequest.getParameter("data_type"));
        paramInfo.setValue("user_id",     userid);

        CommonEntity dailyChk = IR010610.getDailyInfo(conn, paramInfo);  //���ϸ���üũ

        if ("Y".equals(dailyChk.getString("M210_WORKENDSTATE"))) {
            request.setAttribute("page.mn01.SucMsg",   "���ϸ����� �������ϴ�. �����ڷ� ����� �� �� �����ϴ�.");
        } else {
            if (IR010610.deleteData(conn, paramInfo) < 1) {
                logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>  ������ ������ ����  <<<<<<<<<<<<<<<<<<<<<<<<");
            }
      
            if (mRequest != null) {
                try {
                    uploadDir = ETaxConfig.getString("upload_dir") + "/excel/";
                    File file = new File(uploadDir + fileName);
          
                    String semok = ""; //�����ڵ�
                    Workbook workbook = Workbook.getWorkbook(file);
                    Sheet sheet = workbook.getSheet(0);
                    int row = sheet.getRows();    //��
                    if ("G1".equals(mRequest.getParameter("data_type")) ) {  // �����ü������ϰ�ǥ�� ���
                        paramInfo.setValue("data_type1",  "G1");
                        int cnt = 1;
                        for (int n=8; n<row; n++) {
                            CommonEntity data = new CommonEntity();
                            semok = TextHandler.replace(TextHandler.replace(sheet.getCell(3,n).getContents(), " ", ""), "��", ""); // �������� �� �����̸� ����

                            if ("��������".equals(semok) && cnt == 1 ) {
                                semok = "���漼���������Ұ�";
                                cnt ++;
                            } else if ("��������".equals(semok) && cnt == 2 ) {
                                semok = "���ܼ������������Ұ�";
                                cnt ++;
                            }

                            CommonEntity srtData = IR010610.getSrtData(conn, paramInfo, semok);
                            
                            if (srtData.size() > 0 ) {
                                data.setValue("FIS_YEAR",    paramInfo.getString("fis_year"));
                                data.setValue("ACC_DATE",    paramInfo.getString("acc_date"));
                                data.setValue("DATA_TYPE",   paramInfo.getString("data_type"));
                                data.setValue("USER_ID",     userid);
                                data.setValue("SEMOK_CD",    srtData.getString("M440_SEMOKCODE"));
                                data.setValue("SEMOK_NM",    semok);
                                data.setValue("JUNGGU",      TextHandler.unformatNumber((sheet.getCell(8,n).getContents()).trim()));
                                data.setValue("NAMGU",       TextHandler.unformatNumber((sheet.getCell(10,n).getContents()).trim()));
                                data.setValue("DONGGU",      TextHandler.unformatNumber((sheet.getCell(14,n).getContents()).trim()));
                                data.setValue("BUKGU",       TextHandler.unformatNumber((sheet.getCell(16,n).getContents()).trim()));
                                data.setValue("ULJUGUN",     TextHandler.unformatNumber((sheet.getCell(18,n).getContents()).trim()));
                                
                                data.setValue("CELL08",      sheet.getCell(8, n).getContents().trim());
                                data.setValue("CELL09",      sheet.getCell(9, n).getContents().trim());
                                data.setValue("CELL10",      sheet.getCell(10, n).getContents().trim());
                                
                                logger.info("DATA : " + data);

                                if (IR010610.insertData(conn, data) < 1) {  //�̸������ �ڷ� �ֱ�(�����ü��ϰ�ǥ)
                                    throw new ProcessException("E002");
                                }
                            } //end if (srtData.size() > 0 )
                            
                        }//end for (int n=8; n<row; n++)

                        //�����ü������ϰ�ǥ�̸����� ��ȸ
                        List<CommonEntity> preview = IR010610.getNonghyupList(conn, paramInfo);
                        request.setAttribute("page.mn01.nongHyupList",  preview);
                    } else if ("G2".equals(mRequest.getParameter("data_type")) || "G3".equals(mRequest.getParameter("data_type")) ) {  // ������ȯ���ϰ�ǥ�� ���
                        paramInfo.setValue("data_type1",  "G2");
					        for (int n=6; n<row; n++) {
		  		                CommonEntity data = new CommonEntity();
		  		                semok = TextHandler.replace(TextHandler.replace(sheet.getCell(1,n).getContents(), " ", ""), "��", ""); // �������� �� �����̸� ����
		  		                CommonEntity srtData = IR010610.getSrtData(conn, paramInfo, semok);
					  
		  			            if (srtData.size() > 0 ) {
		  				            data.setValue("FIS_YEAR",    paramInfo.getString("fis_year"));
		  				            data.setValue("ACC_DATE",    paramInfo.getString("acc_date"));
		  				            data.setValue("DATA_TYPE",   paramInfo.getString("data_type"));
		  				            data.setValue("USER_ID",     userid);
		  				            data.setValue("SEMOK_CD",    srtData.getString("M440_SEMOKCODE"));
		  				            data.setValue("SEMOK_NM",    semok);

                                    data.setValue("JUNGGU",      TextHandler.replace((sheet.getCell(4,n).getContents()).trim(), ",", ""));
		  				            data.setValue("NAMGU",       TextHandler.replace((sheet.getCell(5,n).getContents()).trim(), ",", ""));
		  				            data.setValue("DONGGU",      TextHandler.replace((sheet.getCell(6,n).getContents()).trim(), ",", ""));
		  				            data.setValue("BUKGU",       TextHandler.replace((sheet.getCell(7,n).getContents()).trim(), ",", ""));
		  				            data.setValue("ULJUGUN",     TextHandler.replace((sheet.getCell(8,n).getContents()).trim(), ",", ""));
								    //���⵵
								    data.setValue("JUNGGU_GWA",  TextHandler.unformatNumber((sheet.getCell(10,n).getContents()).trim()));
		  				            data.setValue("NAMGU_GWA",   TextHandler.unformatNumber((sheet.getCell(11,n).getContents()).trim()));
		  				            data.setValue("DONGGU_GWA",  TextHandler.unformatNumber((sheet.getCell(12,n).getContents()).trim()));
		  				            data.setValue("BUKGU_GWA",   TextHandler.unformatNumber((sheet.getCell(13,n).getContents()).trim()));
		  				            data.setValue("ULJUGUN_GWA", TextHandler.unformatNumber((sheet.getCell(14,n).getContents()).trim()));

								    if (IR010610.insertGwaData(conn, data) < 1) {  //�̸������ �ڷ� �ֱ�(������ȯ���ϰ�ǥ)
		  				                throw new ProcessException("E002");
		  				            }
							    }     
                            }
						    //������ȯ���ϰ�ǥ�̸����� ��ȸ
		  	                List<CommonEntity> nongHyupGwaList = IR010610.getNonghyupGwaList(conn, paramInfo);
		  	                request.setAttribute("page.mn01.nongHyupGwaList",  nongHyupGwaList);
					    } else if ("G4".equals(mRequest.getParameter("data_type")) ) {  //������ϻ���ҽü��ϰ�ǥ
                            paramInfo.setValue("data_type1",  "G3");
                            int rownum = 0;

                            for (int n=2; n<row; n++) {
		  		                CommonEntity data = new CommonEntity();
		  		                semok = TextHandler.replace(TextHandler.replace(sheet.getCell(1,n).getContents(), " ", ""), "��", ""); // �������� �� �����̸� ����
                                CommonEntity srtData = IR010610.getSrtData(conn, paramInfo, semok);

                                if (srtData.size() > 0 ) {	
                                                            rownum ++;		
		  				            data.setValue("FIS_YEAR",    paramInfo.getString("fis_year"));
		  				            data.setValue("ACC_DATE",    paramInfo.getString("acc_date"));
		  				            data.setValue("DATA_TYPE",   paramInfo.getString("data_type"));
		  				            data.setValue("USER_ID",     userid);						
		  				            data.setValue("SEMOK_CD",    srtData.getString("M440_SEMOKCODE"));
		  				            data.setValue("SEMOK_NM",    semok);
                                                            data.setValue("ROW_NUM",     rownum+"");
                                    //�ݾ�
                                    data.setValue("JUNGGU",      TextHandler.unformatNumber((sheet.getCell(3,n).getContents()).trim()));
		  				            data.setValue("NAMGU",       TextHandler.unformatNumber((sheet.getCell(5,n).getContents()).trim()));
		  				            data.setValue("DONGGU",      TextHandler.unformatNumber((sheet.getCell(7,n).getContents()).trim()));
		  				            data.setValue("BUKGU",       TextHandler.unformatNumber((sheet.getCell(9,n).getContents()).trim()));
		  				            data.setValue("ULJUGUN",     TextHandler.unformatNumber((sheet.getCell(11,n).getContents()).trim()));
                                    //�Ǽ�
                                    data.setValue("JUNGGU_CNT",      TextHandler.unformatNumber((sheet.getCell(2,n).getContents()).trim()));
		  				            data.setValue("NAMGU_CNT",       TextHandler.unformatNumber((sheet.getCell(4,n).getContents()).trim()));
		  				            data.setValue("DONGGU_CNT",      TextHandler.unformatNumber((sheet.getCell(6,n).getContents()).trim()));
		  				            data.setValue("BUKGU_CNT",       TextHandler.unformatNumber((sheet.getCell(8,n).getContents()).trim()));
		  				            data.setValue("ULJUGUN_CNT",     TextHandler.unformatNumber((sheet.getCell(10,n).getContents()).trim()));

								    if (IR010610.insertCarData(conn, data) < 1)	{  //�̸������ �ڷ� �ֱ�(�����ü��ϰ�ǥ)
		  				                throw new ProcessException("E002");
		  				            }
							    }
                            }

                            //������ϻ���Ҽ��԰�ǥ ��ȸ
		  	                List<CommonEntity> nongHyupCarList = IR010610.getNonghyupCarList(conn, paramInfo);
		  	                request.setAttribute("page.mn01.nongHyupList",  nongHyupCarList);

                        }

		  		        if (file.exists()) {
                            file.delete();
                            logger.info("���ε� ���� ���� �Ϸ�");
                        }
                    } catch (Exception e) {
                        logger.info("���� �߻� ::::::  [ " + e + " ] ::::::");
                        request.setAttribute("page.mn01.SucMsg",  "������ �۾� �� ������ �߻��Ͽ����ϴ�.");
                }

                request.setAttribute("page.mn01.data_type",   mRequest.getParameter("data_type"));
            } 
        }
    }
}
