 /**********************************************************************
* 프로젝트명		  : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명		  : IR071210ViewCMD.java
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
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>  삭제할 데이터 없음  <<<<<<<<<<<<<<<<<<<<<<<<");
      }
      
      if (mRequest != null) {
        try {
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
		  		    CommonEntity data = new CommonEntity();
		  		    semok = TextHandler.replace(TextHandler.replace(sheet.getCell(3,n).getContents(), " ", ""), "　", ""); // 공백제거 후 세목이름 저장
		  		    semok5 = TextHandler.replace(TextHandler.replace(sheet.getCell(5,n).getContents(), " ", ""), "　", ""); // 공백제거 후 세목이름 저장
              /*
              if ("지난연도".equals(semok) && cnt == 1 ) {
                cnt ++;
                continue;
              } else if ("지난연도".equals(semok) && cnt == 2 ) {
                continue;
              }
              if ("".equals(semok) && !"".equals(semok5)) {
                if("소계".equals(semok5) || "합계".equals(semok5)) {
                  continue;
                }else {
                  semok = "지난연도" + semok5;
                }
              }
              */
              if ("지난연도".equals(semok) && cnt == 1 ) {
                cnt ++;
                if("소계".equals(semok5)) {
                  continue;
                }
                semok = "지난연도" + semok5;
              } else if ("지난연도".equals(semok) && cnt == 2 ) {
                if("소계".equals(semok5)) {
                  continue;
                }
                semok = "지난연도" + semok5;
              }
              if ("".equals(semok) && !"".equals(semok5)) {
                if("소계".equals(semok5) || "합계".equals(semok5)) {
                  continue;
                }else {
                  semok = "지난연도" + semok5;
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

								if (IR071210.insertData(conn, data) < 1)	{  //미리보기용 자료 넣기(광역시세일계표)
		  				    throw new ProcessException("E002");
		  				  }
							}
            } 

						//광역시세세입일계표미리보기 조회
				    List<CommonEntity> preview = IR071210.getNonghyupList(conn, paramInfo);
		  	    request.setAttribute("page.mn07.nongHyupList",  preview);
          } else if ("G7".equals(mRequest.getParameter("data_type")) || "G8".equals(mRequest.getParameter("data_type")) ) {  // 과오납환부일계표일 경우
            paramInfo.setValue("data_type1",  "G7");
					  for (int n=6; n<row; n++) {
		  		    CommonEntity data = new CommonEntity();
		  		    semok = TextHandler.replace(TextHandler.replace(sheet.getCell(1,n).getContents(), " ", ""), "　", ""); // 공백제거 후 세목이름 저장

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
								//과년도
								data.setValue("JUNGGU_GWA",  TextHandler.unformatNumber((sheet.getCell(10,n).getContents()).trim()));
		  				  data.setValue("NAMGU_GWA",   TextHandler.unformatNumber((sheet.getCell(11,n).getContents()).trim()));
		  				  data.setValue("DONGGU_GWA",  TextHandler.unformatNumber((sheet.getCell(12,n).getContents()).trim()));
		  				  data.setValue("BUKGU_GWA",   TextHandler.unformatNumber((sheet.getCell(13,n).getContents()).trim()));
		  				  data.setValue("ULJUGUN_GWA", TextHandler.unformatNumber((sheet.getCell(14,n).getContents()).trim()));

								if (IR071210.insertGwaData(conn, data) < 1)	{  //미리보기용 자료 넣기(과오납환부일계표)
		  				    throw new ProcessException("E002");
		  				  }
							}     
            }
						//과오납환부일계표미리보기 조회
		  	    List<CommonEntity> nongHyupGwaList = IR071210.getNonghyupGwaList(conn, paramInfo);
		  	    request.setAttribute("page.mn07.nongHyupGwaList",  nongHyupGwaList);
					} else if ("G9".equals(mRequest.getParameter("data_type")) ) {  //차량등록사업소시세일계표
            paramInfo.setValue("data_type1",  "G8");

            for (int n=0; n<row; n++) {
		  		    CommonEntity data = new CommonEntity();
		  		    semok = TextHandler.replace(TextHandler.replace(sheet.getCell(1,n).getContents(), " ", ""), "　", ""); // 공백제거 후 세목이름 저장
              CommonEntity srtData = IR071210.getSrtData(conn, paramInfo, semok);

              if (srtData.size() > 0 ) {			
		  				  data.setValue("FIS_YEAR",    paramInfo.getString("fis_year"));
		  				  data.setValue("ACC_DATE",    paramInfo.getString("acc_date"));
		  				  data.setValue("DATA_TYPE",   paramInfo.getString("data_type"));
		  				  data.setValue("USER_ID",     userid);						
		  				  data.setValue("SEMOK_CD",    srtData.getString("M440_SEMOKCODE"));
		  				  data.setValue("SEMOK_NM",    semok);
                //금액
                data.setValue("JUNGGU",      TextHandler.unformatNumber((sheet.getCell(3,n).getContents()).trim()));
		  				  data.setValue("NAMGU",       TextHandler.unformatNumber((sheet.getCell(5,n).getContents()).trim()));
		  				  data.setValue("DONGGU",      TextHandler.unformatNumber((sheet.getCell(7,n).getContents()).trim()));
		  				  data.setValue("BUKGU",       TextHandler.unformatNumber((sheet.getCell(9,n).getContents()).trim()));
		  				  data.setValue("ULJUGUN",     TextHandler.unformatNumber((sheet.getCell(11,n).getContents()).trim()));
                //건수
                data.setValue("JUNGGU_CNT",      TextHandler.unformatNumber((sheet.getCell(2,n).getContents()).trim()));
		  				  data.setValue("NAMGU_CNT",       TextHandler.unformatNumber((sheet.getCell(4,n).getContents()).trim()));
		  				  data.setValue("DONGGU_CNT",      TextHandler.unformatNumber((sheet.getCell(6,n).getContents()).trim()));
		  				  data.setValue("BUKGU_CNT",       TextHandler.unformatNumber((sheet.getCell(8,n).getContents()).trim()));
		  				  data.setValue("ULJUGUN_CNT",     TextHandler.unformatNumber((sheet.getCell(10,n).getContents()).trim()));

								if (IR071210.insertCarData(conn, data) < 1)	{  //미리보기용 자료 넣기(광역시세일계표)
		  				    throw new ProcessException("E002");
		  				  }
							}
            }

            //차량등록사업소세입계표 조회
		  	    List<CommonEntity> nongHyupCarList = IR071210.getNonghyupCarList(conn, paramInfo);
		  	    request.setAttribute("page.mn07.nongHyupList",  nongHyupCarList);

          }

		  		if (file.exists()) {
            file.delete();
            logger.info("업로드 파일 삭제 완료");
          }
        } catch (Exception e) {
          logger.info("에러 발생 ::::::  [ " + e + " ] ::::::");
          request.setAttribute("page.mn07.SucMsg",  "데이터 작업 중 오류가 발생하였습니다.");
        }

        request.setAttribute("page.mn07.data_type",   mRequest.getParameter("data_type"));
      } 
  }
}
