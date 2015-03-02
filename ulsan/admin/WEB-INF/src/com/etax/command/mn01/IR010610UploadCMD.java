/**********************************************************************
* 프로젝트명		  : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명		  : IR010610UploadCMD.java
* 프로그램작성자	: (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용   : 세입 > 농협자료등록
***********************************************************************/
package com.etax.command.mn01;

import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;
import jxl.*;
import com.oreilly.servlet.MultipartRequest;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.entity.CommonEntity;
import com.etax.command.common.TransLogInsert;  //로그기록 남기기 위한 import
import com.etax.config.ETaxConfig;
import com.etax.db.mn01.IR010610;
import com.etax.util.TextHandler;

public class IR010610UploadCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR010610UploadCMD.class);

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
		paramInfo.setValue("work_log",    mRequest.getParameter("work_log"));
        if ("G3".equals(paramInfo.getString("data_type")) ) {
            paramInfo.setValue("trans_gubun", "163");
        } else {
		    paramInfo.setValue("trans_gubun", mRequest.getParameter("trans_gubun"));
        }
		paramInfo.setValue("user_id",     userid);

        String ago_acc_date = TextHandler.getAgoBusinessDate(conn, TextHandler.addDays(TextHandler.getCurrentDate(),-1));  //오늘날짜 기준 전영업일
        request.setAttribute("page.mn01.ago_acc_date", ago_acc_date);

		if ("G1".equals(mRequest.getParameter("data_type")) || "G3".equals(mRequest.getParameter("data_type")) 
            || "G4".equals(mRequest.getParameter("data_type")) ) {
			paramInfo.setValue("in_type",     "I1");
		} else if ("G2".equals(mRequest.getParameter("data_type"))) {
			paramInfo.setValue("in_type",     "I2");
		}

        if ("G4".equals(paramInfo.getString("data_type")) ) {
            paramInfo.setValue("sunap_gigwan",    "310001");
        } else {
            paramInfo.setValue("sunap_gigwan",    "110000");
        }
		
		CommonEntity dailyChk = IR010610.getDailyInfo(conn, paramInfo);  //일일마감체크

		if ("Y".equals(dailyChk.getString("M210_WORKENDSTATE")))	{
			request.setAttribute("page.mn01.SucMsg",   "일일마감이 끝났습니다. 농협자료 등록을 할 수 없습니다.");
                } else {
			CommonEntity dataInfo = IR010610.getDataInfo(conn, paramInfo);  //중복 자료 체크
			if (!"0".equals(dataInfo.getString("CNT")) )	{
				request.setAttribute("page.mn01.SucMsg",  "이미 등록된 자료입니다.");
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
		    		    Workbook workbook = Workbook.getWorkbook(file);
                        Sheet sheet = workbook.getSheet(0);

                        int row = sheet.getRows();    //행
						if ("G1".equals(mRequest.getParameter("data_type")) ) {  // 광역시세세입일계표일 경우
                            paramInfo.setValue("data_type1",  "G1");
                            int cnt = 1;
                            for (int n=8; n<row; n++) {
		    			        CommonEntity data = new CommonEntity();
		    			        semok = TextHandler.replace(TextHandler.replace(sheet.getCell(3,n).getContents(), " ", ""), "　", ""); // 공백제거 후 세목이름 저장

                                if ("지난연도".equals(semok) && cnt == 1 ) {
                                    semok = "지방세지난연도소계";
                                    cnt ++;
                                } else if ("지난연도".equals(semok) && cnt == 2 ) {
                                    semok = "세외수입지난연도소계";
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

                                    //건수
                                    data.setValue("JUNGGU_CNT",      "1");
		  				            data.setValue("NAMGU_CNT",       "1");
		  				            data.setValue("DONGGU_CNT",      "1");
		  				            data.setValue("BUKGU_CNT",       "1");
		  				            data.setValue("ULJUGUN_CNT",     "1");

								    if (!"".equals(data.getString("JUNGGU")) && !"0".equals(data.getString("JUNGGU")) )	{  //금액이 0이 아닌 건만 등록
									    if (IR010610.insertJunggu(conn, data, paramInfo) < 1)	{  //중구자료 넣기
		    				                throw new ProcessException("E002");
		    				            }
								    }

                                    if (!"".equals(data.getString("NAMGU")) && !"0".equals(data.getString("NAMGU")) )	{  //금액이 0이 아닌 건만 등록
								        if (IR010610.insertNamgu(conn, data, paramInfo) < 1)	{  //남구자료 넣기
		    				                throw new ProcessException("E002");
		    				            }
								    }

                                    if (!"".equals(data.getString("DONGGU")) && !"0".equals(data.getString("DONGGU")) )	{  //금액이 0이 아닌 건만 등록
								        if (IR010610.insertDonggu(conn, data, paramInfo) < 1)	{  //동구자료 넣기
		    				                throw new ProcessException("E002");
		    				            }
								    }

                                    if (!"".equals(data.getString("BUKGU")) && !"0".equals(data.getString("BUKGU")) )	{  //금액이 0이 아닌 건만 등록
								        if (IR010610.insertBukgu(conn, data, paramInfo) < 1)	{  //북구자료 넣기
		    				                throw new ProcessException("E002");
		    				            }
								    }

                                    if (!"".equals(data.getString("ULJUGUN")) && !"0".equals(data.getString("ULJUGUN")) )	{  //금액이 0이 아닌 건만 등록
								        if (IR010610.insertUlju(conn, data, paramInfo) < 1)	{  //울주군자료 넣기
		    				                throw new ProcessException("E002");
		    				            }
								    }
							    }
                
                            }
						} else if ("G2".equals(mRequest.getParameter("data_type")) || "G3".equals(mRequest.getParameter("data_type")) )	{  //과오납환부일계표
						    paramInfo.setValue("data_type1",  "G2");
						    for (int n=6; n<row; n++) {
	    			            CommonEntity data = new CommonEntity();
		    			        semok = TextHandler.replace(TextHandler.replace(sheet.getCell(1,n).getContents(), " ", ""), "　", ""); // 공백제거 후 세목이름 저장
		    			        CommonEntity srtData = IR010610.getSrtData(conn, paramInfo, semok);
		    			        if (srtData.size() > 0 ) {
		    				        data.setValue("FIS_YEAR",    paramInfo.getString("fis_year"));
		    				        data.setValue("ACC_DATE",    paramInfo.getString("acc_date"));
		    				        data.setValue("DATA_TYPE",   paramInfo.getString("data_type"));
		    				        data.setValue("USER_ID",     userid);						
		    				        data.setValue("SEMOK_CD",    srtData.getString("M440_SEMOKCODE"));
		    				        data.setValue("SEMOK_NM",    semok);
									if (!"1130100".equals(srtData.getString("M440_SEMOKCODE")) && !"2290100".equals(srtData.getString("M440_SEMOKCODE"))) {
										//세목코드가 지방세,세외수입 소계가 아닌경우(현년도분을 넣는다.)
										data.setValue("JUNGGU",      TextHandler.unformatNumber((sheet.getCell(4,n).getContents()).trim()));
		  				                data.setValue("NAMGU",       TextHandler.unformatNumber((sheet.getCell(5,n).getContents()).trim()));
		  				                data.setValue("DONGGU",      TextHandler.unformatNumber((sheet.getCell(6,n).getContents()).trim()));
		  				                data.setValue("BUKGU",       TextHandler.unformatNumber((sheet.getCell(7,n).getContents()).trim()));
		  				                data.setValue("ULJUGUN",     TextHandler.unformatNumber((sheet.getCell(8,n).getContents()).trim()));

									} else if ("1130100".equals(srtData.getString("M440_SEMOKCODE")) || "2290100".equals(srtData.getString("M440_SEMOKCODE"))) {
										//세목코드가 지방세,세입수입 소계인 경우(과년도 소계를 넣는다.)
										data.setValue("JUNGGU",      TextHandler.unformatNumber((sheet.getCell(10,n).getContents()).trim()));
		  				                data.setValue("NAMGU",       TextHandler.unformatNumber((sheet.getCell(11,n).getContents()).trim()));
		  				                data.setValue("DONGGU",      TextHandler.unformatNumber((sheet.getCell(12,n).getContents()).trim()));
		  				                data.setValue("BUKGU",       TextHandler.unformatNumber((sheet.getCell(13,n).getContents()).trim()));
		  				                data.setValue("ULJUGUN",     TextHandler.unformatNumber((sheet.getCell(14,n).getContents()).trim()));
									}

                                    //건수
                                    data.setValue("JUNGGU_CNT",      "1");
		  				            data.setValue("NAMGU_CNT",       "1");
		  				            data.setValue("DONGGU_CNT",      "1");
		  				            data.setValue("BUKGU_CNT",       "1");
		  				            data.setValue("ULJUGUN_CNT",     "1");
                  
									if (!"".equals(data.getString("JUNGGU")) && !"0".equals(data.getString("JUNGGU")) )	{  //금액이 0이 아닌 건만 등록
									    if (IR010610.insertJunggu(conn, data, paramInfo) < 1)	{  //중구자료 넣기
		    					            throw new ProcessException("E002");
		    				            }
									}

                                    if (!"".equals(data.getString("NAMGU")) && !"0".equals(data.getString("NAMGU")) )	{  //금액이 0이 아닌 건만 등록
								        if (IR010610.insertNamgu(conn, data, paramInfo) < 1)	{  //남구자료 넣기
		    					            throw new ProcessException("E002");
		    				            }
									}

                                    if (!"".equals(data.getString("DONGGU")) && !"0".equals(data.getString("DONGGU")) )	{  //금액이 0이 아닌 건만 등록
								        if (IR010610.insertDonggu(conn, data, paramInfo) < 1)	{  //동구자료 넣기
		    					            throw new ProcessException("E002");
		    				            }
									}

                                    if (!"".equals(data.getString("BUKGU")) && !"0".equals(data.getString("BUKGU")) )	{  //금액이 0이 아닌 건만 등록
								        if (IR010610.insertBukgu(conn, data, paramInfo) < 1)	{  //북구자료 넣기
		    					            throw new ProcessException("E002");
		    				            }
								    }

                                    if (!"".equals(data.getString("ULJUGUN")) && !"0".equals(data.getString("ULJUGUN")) )	{  //금액이 0이 아닌 건만 등록
								        if (IR010610.insertUlju(conn, data, paramInfo) < 1)	{  //울주군자료 넣기
		    					            throw new ProcessException("E002");
		    				            }
									}
                                } 
                            }
						} else if ("G4".equals(mRequest.getParameter("data_type"))) {
                            paramInfo.setValue("data_type1",  "G3");
                            for (int n=2; n<row; n++) {
		    			        CommonEntity data = new CommonEntity();
		    			        semok = TextHandler.replace(TextHandler.replace(sheet.getCell(1,n).getContents(), " ", ""), "　", ""); // 공백제거 후 세목이름 저장

		    			        CommonEntity srtData = IR010610.getSrtData(conn, paramInfo, semok);
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

								    if (!"".equals(data.getString("JUNGGU")) && !"0".equals(data.getString("JUNGGU")) )	{  //금액이 0이 아닌 건만 등록
									    if (IR010610.insertJunggu(conn, data, paramInfo) < 1)	{  //중구자료 넣기
		    				                throw new ProcessException("E002");
		    				            }
								    }

                                    if (!"".equals(data.getString("NAMGU")) && !"0".equals(data.getString("NAMGU")) )	{  //금액이 0이 아닌 건만 등록
								        if (IR010610.insertNamgu(conn, data, paramInfo) < 1)	{  //남구자료 넣기
		    				                throw new ProcessException("E002");
		    				            }
								    }

                                    if (!"".equals(data.getString("DONGGU")) && !"0".equals(data.getString("DONGGU")) )	{  //금액이 0이 아닌 건만 등록
								        if (IR010610.insertDonggu(conn, data, paramInfo) < 1)	{  //동구자료 넣기
		    				                throw new ProcessException("E002");
		    				            }
								    }

                                    if (!"".equals(data.getString("BUKGU")) && !"0".equals(data.getString("BUKGU")) )	{  //금액이 0이 아닌 건만 등록
								        if (IR010610.insertBukgu(conn, data, paramInfo) < 1)	{  //북구자료 넣기
		    				                throw new ProcessException("E002");
		    				            }
									}

                                    if (!"".equals(data.getString("ULJUGUN")) && !"0".equals(data.getString("ULJUGUN")) )	{  //금액이 0이 아닌 건만 등록
								        if (IR010610.insertUlju(conn, data, paramInfo) < 1)	{  //울주군자료 넣기
		    				                throw new ProcessException("E002");
		    				            }
								    }
								}
                
                            }
                        }
		    		    if (file.exists()) {
                            file.delete();
                            logger.info("업로드 파일 삭제 완료");
                        }
						request.setAttribute("page.mn01.SucMsg",   "농협자료 등록이 완료되었습니다.");
                    } catch (Exception e) {
                        logger.info("에러 발생 ::::::  [ " + e + " ] ::::::");
                        request.setAttribute("page.mn01.SucMsg",  "데이터 작업 중 오류가 발생하였습니다.");
                    }

                } 
			}
        }
    }
}
