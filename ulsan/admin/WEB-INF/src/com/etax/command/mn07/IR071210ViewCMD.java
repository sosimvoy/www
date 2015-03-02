 /**********************************************************************
* ������Ʈ��		  : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���		  : IR071210ViewCMD.java
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
import com.etax.config.ETaxConfig;
import com.etax.db.mn07.IR071210;
import com.etax.util.TextHandler;

public class IR071210ViewCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR071210ViewCMD.class);

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


		  if (IR071210.deleteData(conn, paramInfo) < 1) {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>  ������ ������ ����  <<<<<<<<<<<<<<<<<<<<<<<<");
      }
      
      if (mRequest != null) {
        try {
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
		  		    CommonEntity data = new CommonEntity();
		  		    semok = TextHandler.replace(TextHandler.replace(sheet.getCell(3,n).getContents(), " ", ""), "��", ""); // �������� �� �����̸� ����
		  		    semok5 = TextHandler.replace(TextHandler.replace(sheet.getCell(5,n).getContents(), " ", ""), "��", ""); // �������� �� �����̸� ����
              /*
              if ("��������".equals(semok) && cnt == 1 ) {
                cnt ++;
                continue;
              } else if ("��������".equals(semok) && cnt == 2 ) {
                continue;
              }
              if ("".equals(semok) && !"".equals(semok5)) {
                if("�Ұ�".equals(semok5) || "�հ�".equals(semok5)) {
                  continue;
                }else {
                  semok = "��������" + semok5;
                }
              }
              */
              if ("��������".equals(semok) && cnt == 1 ) {
                cnt ++;
                if("�Ұ�".equals(semok5)) {
                  continue;
                }
                semok = "��������" + semok5;
              } else if ("��������".equals(semok) && cnt == 2 ) {
                if("�Ұ�".equals(semok5)) {
                  continue;
                }
                semok = "��������" + semok5;
              }
              if ("".equals(semok) && !"".equals(semok5)) {
                if("�Ұ�".equals(semok5) || "�հ�".equals(semok5)) {
                  continue;
                }else {
                  semok = "��������" + semok5;
                }
              }

		  		    CommonEntity srtData = IR071210.getSrtData(conn, paramInfo, semok);
					  
		  			  if (srtData.size() > 0 ) {			
		  				  data.setValue("FIS_YEAR",    paramInfo.getString("fis_year"));
		  				  data.setValue("ACC_DATE",    paramInfo.getString("acc_date"));
		  				  data.setValue("DATA_TYPE",   paramInfo.getString("data_type"));
		  				  data.setValue("USER_ID",     userid);						
		  				  data.setValue("SEMOK_CD",    srtData.getString("M440_SEMOKCODE"));
		  				  data.setValue("SEMOK_NM",    semok);
                data.setValue("JUNGGU",      TextHandler.unformatNumber((sheet.getCell(9,n).getContents()).trim()));
		  				  data.setValue("NAMGU",       TextHandler.unformatNumber((sheet.getCell(11,n).getContents()).trim()));
		  				  data.setValue("DONGGU",      TextHandler.unformatNumber((sheet.getCell(15,n).getContents()).trim()));
		  				  data.setValue("BUKGU",       TextHandler.unformatNumber((sheet.getCell(17,n).getContents()).trim()));
		  				  data.setValue("ULJUGUN",     TextHandler.unformatNumber((sheet.getCell(19,n).getContents()).trim()));

								if (IR071210.insertData(conn, data) < 1)	{  //�̸������ �ڷ� �ֱ�(�����ü��ϰ�ǥ)
		  				    throw new ProcessException("E002");
		  				  }
							}
            } 

						//�����ü������ϰ�ǥ�̸����� ��ȸ
				    List<CommonEntity> preview = IR071210.getNonghyupList(conn, paramInfo);
		  	    request.setAttribute("page.mn07.nongHyupList",  preview);
          } else if ("G7".equals(mRequest.getParameter("data_type")) || "G8".equals(mRequest.getParameter("data_type")) ) {  // ������ȯ���ϰ�ǥ�� ���
            paramInfo.setValue("data_type1",  "G7");
					  for (int n=6; n<row; n++) {
		  		    CommonEntity data = new CommonEntity();
		  		    semok = TextHandler.replace(TextHandler.replace(sheet.getCell(1,n).getContents(), " ", ""), "��", ""); // �������� �� �����̸� ����

              CommonEntity srtData = IR071210.getSrtData(conn, paramInfo, semok);
					  
		  			  if (srtData.size() > 0 ) {			
		  				  data.setValue("FIS_YEAR",    paramInfo.getString("fis_year"));
		  				  data.setValue("ACC_DATE",    paramInfo.getString("acc_date"));
		  				  data.setValue("DATA_TYPE",   paramInfo.getString("data_type"));
		  				  data.setValue("USER_ID",     userid);						
		  				  data.setValue("SEMOK_CD",    srtData.getString("M440_SEMOKCODE"));
		  				  data.setValue("SEMOK_NM",    semok);
                data.setValue("JUNGGU",      TextHandler.unformatNumber((sheet.getCell(4,n).getContents()).trim()));
		  				  data.setValue("NAMGU",       TextHandler.unformatNumber((sheet.getCell(5,n).getContents()).trim()));
		  				  data.setValue("DONGGU",      TextHandler.unformatNumber((sheet.getCell(6,n).getContents()).trim()));
		  				  data.setValue("BUKGU",       TextHandler.unformatNumber((sheet.getCell(7,n).getContents()).trim()));
		  				  data.setValue("ULJUGUN",     TextHandler.unformatNumber((sheet.getCell(8,n).getContents()).trim()));
								//���⵵
								data.setValue("JUNGGU_GWA",  TextHandler.unformatNumber((sheet.getCell(10,n).getContents()).trim()));
		  				  data.setValue("NAMGU_GWA",   TextHandler.unformatNumber((sheet.getCell(11,n).getContents()).trim()));
		  				  data.setValue("DONGGU_GWA",  TextHandler.unformatNumber((sheet.getCell(12,n).getContents()).trim()));
		  				  data.setValue("BUKGU_GWA",   TextHandler.unformatNumber((sheet.getCell(13,n).getContents()).trim()));
		  				  data.setValue("ULJUGUN_GWA", TextHandler.unformatNumber((sheet.getCell(14,n).getContents()).trim()));

								if (IR071210.insertGwaData(conn, data) < 1)	{  //�̸������ �ڷ� �ֱ�(������ȯ���ϰ�ǥ)
		  				    throw new ProcessException("E002");
		  				  }
							}     
            }
						//������ȯ���ϰ�ǥ�̸����� ��ȸ
		  	    List<CommonEntity> nongHyupGwaList = IR071210.getNonghyupGwaList(conn, paramInfo);
		  	    request.setAttribute("page.mn07.nongHyupGwaList",  nongHyupGwaList);
					} else if ("G9".equals(mRequest.getParameter("data_type")) ) {  //������ϻ���ҽü��ϰ�ǥ
            paramInfo.setValue("data_type1",  "G8");

            for (int n=0; n<row; n++) {
		  		    CommonEntity data = new CommonEntity();
		  		    semok = TextHandler.replace(TextHandler.replace(sheet.getCell(1,n).getContents(), " ", ""), "��", ""); // �������� �� �����̸� ����
              CommonEntity srtData = IR071210.getSrtData(conn, paramInfo, semok);

              if (srtData.size() > 0 ) {			
		  				  data.setValue("FIS_YEAR",    paramInfo.getString("fis_year"));
		  				  data.setValue("ACC_DATE",    paramInfo.getString("acc_date"));
		  				  data.setValue("DATA_TYPE",   paramInfo.getString("data_type"));
		  				  data.setValue("USER_ID",     userid);						
		  				  data.setValue("SEMOK_CD",    srtData.getString("M440_SEMOKCODE"));
		  				  data.setValue("SEMOK_NM",    semok);
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

								if (IR071210.insertCarData(conn, data) < 1)	{  //�̸������ �ڷ� �ֱ�(�����ü��ϰ�ǥ)
		  				    throw new ProcessException("E002");
		  				  }
							}
            }

            //������ϻ���Ҽ��԰�ǥ ��ȸ
		  	    List<CommonEntity> nongHyupCarList = IR071210.getNonghyupCarList(conn, paramInfo);
		  	    request.setAttribute("page.mn07.nongHyupList",  nongHyupCarList);

          }

		  		if (file.exists()) {
            file.delete();
            logger.info("���ε� ���� ���� �Ϸ�");
          }
        } catch (Exception e) {
          logger.info("���� �߻� ::::::  [ " + e + " ] ::::::");
          request.setAttribute("page.mn07.SucMsg",  "������ �۾� �� ������ �߻��Ͽ����ϴ�.");
        }

        request.setAttribute("page.mn07.data_type",   mRequest.getParameter("data_type"));
      } 
  }
}
