/**********************************************************************
* ������Ʈ��		  : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���		  : IR010610UploadCMD.java
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
import jxl.*;
import com.oreilly.servlet.MultipartRequest;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.entity.CommonEntity;
import com.etax.command.common.TransLogInsert;  //�αױ�� ����� ���� import
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

        String ago_acc_date = TextHandler.getAgoBusinessDate(conn, TextHandler.addDays(TextHandler.getCurrentDate(),-1));  //���ó�¥ ���� ��������
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
		
		CommonEntity dailyChk = IR010610.getDailyInfo(conn, paramInfo);  //���ϸ���üũ

		if ("Y".equals(dailyChk.getString("M210_WORKENDSTATE")))	{
			request.setAttribute("page.mn01.SucMsg",   "���ϸ����� �������ϴ�. �����ڷ� ����� �� �� �����ϴ�.");
                } else {
			CommonEntity dataInfo = IR010610.getDataInfo(conn, paramInfo);  //�ߺ� �ڷ� üũ
			if (!"0".equals(dataInfo.getString("CNT")) )	{
				request.setAttribute("page.mn01.SucMsg",  "�̹� ��ϵ� �ڷ��Դϴ�.");
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

                                    //�Ǽ�
                                    data.setValue("JUNGGU_CNT",      "1");
		  				            data.setValue("NAMGU_CNT",       "1");
		  				            data.setValue("DONGGU_CNT",      "1");
		  				            data.setValue("BUKGU_CNT",       "1");
		  				            data.setValue("ULJUGUN_CNT",     "1");

								    if (!"".equals(data.getString("JUNGGU")) && !"0".equals(data.getString("JUNGGU")) )	{  //�ݾ��� 0�� �ƴ� �Ǹ� ���
									    if (IR010610.insertJunggu(conn, data, paramInfo) < 1)	{  //�߱��ڷ� �ֱ�
		    				                throw new ProcessException("E002");
		    				            }
								    }

                                    if (!"".equals(data.getString("NAMGU")) && !"0".equals(data.getString("NAMGU")) )	{  //�ݾ��� 0�� �ƴ� �Ǹ� ���
								        if (IR010610.insertNamgu(conn, data, paramInfo) < 1)	{  //�����ڷ� �ֱ�
		    				                throw new ProcessException("E002");
		    				            }
								    }

                                    if (!"".equals(data.getString("DONGGU")) && !"0".equals(data.getString("DONGGU")) )	{  //�ݾ��� 0�� �ƴ� �Ǹ� ���
								        if (IR010610.insertDonggu(conn, data, paramInfo) < 1)	{  //�����ڷ� �ֱ�
		    				                throw new ProcessException("E002");
		    				            }
								    }

                                    if (!"".equals(data.getString("BUKGU")) && !"0".equals(data.getString("BUKGU")) )	{  //�ݾ��� 0�� �ƴ� �Ǹ� ���
								        if (IR010610.insertBukgu(conn, data, paramInfo) < 1)	{  //�ϱ��ڷ� �ֱ�
		    				                throw new ProcessException("E002");
		    				            }
								    }

                                    if (!"".equals(data.getString("ULJUGUN")) && !"0".equals(data.getString("ULJUGUN")) )	{  //�ݾ��� 0�� �ƴ� �Ǹ� ���
								        if (IR010610.insertUlju(conn, data, paramInfo) < 1)	{  //���ֱ��ڷ� �ֱ�
		    				                throw new ProcessException("E002");
		    				            }
								    }
							    }
                
                            }
						} else if ("G2".equals(mRequest.getParameter("data_type")) || "G3".equals(mRequest.getParameter("data_type")) )	{  //������ȯ���ϰ�ǥ
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
									if (!"1130100".equals(srtData.getString("M440_SEMOKCODE")) && !"2290100".equals(srtData.getString("M440_SEMOKCODE"))) {
										//�����ڵ尡 ���漼,���ܼ��� �Ұ谡 �ƴѰ��(���⵵���� �ִ´�.)
										data.setValue("JUNGGU",      TextHandler.unformatNumber((sheet.getCell(4,n).getContents()).trim()));
		  				                data.setValue("NAMGU",       TextHandler.unformatNumber((sheet.getCell(5,n).getContents()).trim()));
		  				                data.setValue("DONGGU",      TextHandler.unformatNumber((sheet.getCell(6,n).getContents()).trim()));
		  				                data.setValue("BUKGU",       TextHandler.unformatNumber((sheet.getCell(7,n).getContents()).trim()));
		  				                data.setValue("ULJUGUN",     TextHandler.unformatNumber((sheet.getCell(8,n).getContents()).trim()));

									} else if ("1130100".equals(srtData.getString("M440_SEMOKCODE")) || "2290100".equals(srtData.getString("M440_SEMOKCODE"))) {
										//�����ڵ尡 ���漼,���Լ��� �Ұ��� ���(���⵵ �Ұ踦 �ִ´�.)
										data.setValue("JUNGGU",      TextHandler.unformatNumber((sheet.getCell(10,n).getContents()).trim()));
		  				                data.setValue("NAMGU",       TextHandler.unformatNumber((sheet.getCell(11,n).getContents()).trim()));
		  				                data.setValue("DONGGU",      TextHandler.unformatNumber((sheet.getCell(12,n).getContents()).trim()));
		  				                data.setValue("BUKGU",       TextHandler.unformatNumber((sheet.getCell(13,n).getContents()).trim()));
		  				                data.setValue("ULJUGUN",     TextHandler.unformatNumber((sheet.getCell(14,n).getContents()).trim()));
									}

                                    //�Ǽ�
                                    data.setValue("JUNGGU_CNT",      "1");
		  				            data.setValue("NAMGU_CNT",       "1");
		  				            data.setValue("DONGGU_CNT",      "1");
		  				            data.setValue("BUKGU_CNT",       "1");
		  				            data.setValue("ULJUGUN_CNT",     "1");
                  
									if (!"".equals(data.getString("JUNGGU")) && !"0".equals(data.getString("JUNGGU")) )	{  //�ݾ��� 0�� �ƴ� �Ǹ� ���
									    if (IR010610.insertJunggu(conn, data, paramInfo) < 1)	{  //�߱��ڷ� �ֱ�
		    					            throw new ProcessException("E002");
		    				            }
									}

                                    if (!"".equals(data.getString("NAMGU")) && !"0".equals(data.getString("NAMGU")) )	{  //�ݾ��� 0�� �ƴ� �Ǹ� ���
								        if (IR010610.insertNamgu(conn, data, paramInfo) < 1)	{  //�����ڷ� �ֱ�
		    					            throw new ProcessException("E002");
		    				            }
									}

                                    if (!"".equals(data.getString("DONGGU")) && !"0".equals(data.getString("DONGGU")) )	{  //�ݾ��� 0�� �ƴ� �Ǹ� ���
								        if (IR010610.insertDonggu(conn, data, paramInfo) < 1)	{  //�����ڷ� �ֱ�
		    					            throw new ProcessException("E002");
		    				            }
									}

                                    if (!"".equals(data.getString("BUKGU")) && !"0".equals(data.getString("BUKGU")) )	{  //�ݾ��� 0�� �ƴ� �Ǹ� ���
								        if (IR010610.insertBukgu(conn, data, paramInfo) < 1)	{  //�ϱ��ڷ� �ֱ�
		    					            throw new ProcessException("E002");
		    				            }
								    }

                                    if (!"".equals(data.getString("ULJUGUN")) && !"0".equals(data.getString("ULJUGUN")) )	{  //�ݾ��� 0�� �ƴ� �Ǹ� ���
								        if (IR010610.insertUlju(conn, data, paramInfo) < 1)	{  //���ֱ��ڷ� �ֱ�
		    					            throw new ProcessException("E002");
		    				            }
									}
                                } 
                            }
						} else if ("G4".equals(mRequest.getParameter("data_type"))) {
                            paramInfo.setValue("data_type1",  "G3");
                            for (int n=2; n<row; n++) {
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

								    if (!"".equals(data.getString("JUNGGU")) && !"0".equals(data.getString("JUNGGU")) )	{  //�ݾ��� 0�� �ƴ� �Ǹ� ���
									    if (IR010610.insertJunggu(conn, data, paramInfo) < 1)	{  //�߱��ڷ� �ֱ�
		    				                throw new ProcessException("E002");
		    				            }
								    }

                                    if (!"".equals(data.getString("NAMGU")) && !"0".equals(data.getString("NAMGU")) )	{  //�ݾ��� 0�� �ƴ� �Ǹ� ���
								        if (IR010610.insertNamgu(conn, data, paramInfo) < 1)	{  //�����ڷ� �ֱ�
		    				                throw new ProcessException("E002");
		    				            }
								    }

                                    if (!"".equals(data.getString("DONGGU")) && !"0".equals(data.getString("DONGGU")) )	{  //�ݾ��� 0�� �ƴ� �Ǹ� ���
								        if (IR010610.insertDonggu(conn, data, paramInfo) < 1)	{  //�����ڷ� �ֱ�
		    				                throw new ProcessException("E002");
		    				            }
								    }

                                    if (!"".equals(data.getString("BUKGU")) && !"0".equals(data.getString("BUKGU")) )	{  //�ݾ��� 0�� �ƴ� �Ǹ� ���
								        if (IR010610.insertBukgu(conn, data, paramInfo) < 1)	{  //�ϱ��ڷ� �ֱ�
		    				                throw new ProcessException("E002");
		    				            }
									}

                                    if (!"".equals(data.getString("ULJUGUN")) && !"0".equals(data.getString("ULJUGUN")) )	{  //�ݾ��� 0�� �ƴ� �Ǹ� ���
								        if (IR010610.insertUlju(conn, data, paramInfo) < 1)	{  //���ֱ��ڷ� �ֱ�
		    				                throw new ProcessException("E002");
		    				            }
								    }
								}
                
                            }
                        }
		    		    if (file.exists()) {
                            file.delete();
                            logger.info("���ε� ���� ���� �Ϸ�");
                        }
						request.setAttribute("page.mn01.SucMsg",   "�����ڷ� ����� �Ϸ�Ǿ����ϴ�.");
                    } catch (Exception e) {
                        logger.info("���� �߻� ::::::  [ " + e + " ] ::::::");
                        request.setAttribute("page.mn01.SucMsg",  "������ �۾� �� ������ �߻��Ͽ����ϴ�.");
                    }

                } 
			}
        }
    }
}
