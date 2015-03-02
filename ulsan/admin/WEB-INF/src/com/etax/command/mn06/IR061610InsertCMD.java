 /**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR061610InsertCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ܾ����ڷ��Է�(���ݿ���)
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn06.IR061610;
import com.etax.db.mn06.IR060000;
import com.etax.db.trans.TransDAO;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;


public class IR061610InsertCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR061610InsertCMD.class); // log4j ����

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 

        CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("reg_date",            request.getParameter("reg_date"));
        paramInfo.setValue("last_year",           Integer.parseInt(request.getParameter("reg_date").substring(0,4)) -1 + "");

        String fis_year    = (request.getParameter("reg_date")).substring(0,4);  //ȸ�迬��
		String reg_date    = request.getParameter("reg_date");   //ȸ����
		String before_date = TextHandler.getBeforeDate(conn, reg_date);  //��������
		String after_date  = TextHandler.getBusinessDate(conn, TextHandler.addDays(reg_date, 1));  //�Ϳ�����
		String after_year  = after_date.substring(0,4);  //�Ϳ����� ȸ�迬��
        String magam_date  = IR060000.ilbanCheck(conn, paramInfo).getString("M320_DATEILBAN");
        logger.info("�Ϲ�,Ư�� ������ : " + magam_date);
        logger.info("before_date : " + before_date);
        logger.info("after_date : " + after_date);

		CommonEntity daemonData = IR060000.getDaemonData(conn, reg_date); 
		if (!(request.getParameter("reg_date")).equals(TextHandler.getBusinessDate(conn, reg_date)) ) { //������ üũ
		    request.setAttribute("page.mn06.SucMsg",   "�ش����ڴ� �������� �ƴմϴ�.");
		} else if (Integer.parseInt(request.getParameter("reg_date")) > Integer.parseInt(TextHandler.getCurrentDate())) {  //������� ������ �������� üũ
		    request.setAttribute("page.mn06.SucMsg",   "������ڰ� ���������Դϴ�.");
		} else if (daemonData.size() > 0 && "N".equals(daemonData.getString("M480_INQUIRYYN")) ) { //���� ���� ���̺� ��ȸ "N"�̰ų� �ڷᰡ ������ ����ó��
		    request.setAttribute("page.mn06.SucMsg",   "���ϰ� ó�� ���Դϴ�. ��� �� �õ��ϼ���.");
		} else {
			CommonEntity dailyData = IR060000.dailyCheck(conn, reg_date);
			if ("Y".equals(dailyData.getString("M210_WORKENDSTATE")) || "Y".equals(dailyData.getString("M210_PUBLICDEPOSITSTATE"))) { //��������üũ
				request.setAttribute("page.mn06.SucMsg",   "���ݿ��� �ܾ׵���� �����Ǿ����ϴ�.");
			} else {
                // ----------- ���� �۾� ---------------//
				if (Integer.parseInt(reg_date) <= Integer.parseInt(magam_date)) {  //����� ������ڰ� ȸ�踶�����ں��� ���ų� ����
                    logger.info("���� �۾� ����");
					CommonEntity lastBefore = IR060000.lastDailyCheck(conn, before_date);  //���⵵(��������) ����üũ
					if (lastBefore.size() == 0)	{  // ������ ���� ���� ���
						request.setAttribute("page.mn06.SucMsg",   "���⵵ ���������� ���ݿ��� ���� ����ϼ���.");
					} else if (!"Y".equals(lastBefore.getString("M210_ETCDEPOSITSTATE"))) {  //��Ÿ���ݵ������ ���� ���
					    request.setAttribute("page.mn06.SucMsg",   "���⵵ ���������� ��Ÿ���� �ܾ��� ���� ����ϼ���.");
					} else {
						CommonEntity lastCheck = IR060000.lastDailyCheck(conn, reg_date);  //���⵵������ ����üũ
						if (lastCheck.size() == 0)	{  // ���ϸ����ڷ� ����

						    CommonEntity gwaJanakData = IR060000.getLastJanakData(conn, reg_date);  //���⵵ �ܾ��� �ڷ� ���翩��(�Ϲ�,Ư��ȸ�踸)
							if (gwaJanakData.size() == 0) {
								request.setAttribute("page.mn06.SucMsg",    "���⵵ �ܾ��� �ڷᰡ �����ϴ�. �����ڿ��� ���ǹٶ��ϴ�.");
							} else {
                                if (Integer.parseInt(reg_date) >= Integer.parseInt(fis_year + "0101")
                                    && Integer.parseInt(reg_date) < Integer.parseInt(magam_date)) {
                                    if (IR061610.insertJanackData3(conn, reg_date, after_date) < 1)	{ //ȸ�����ڸ� �޾Ƽ� ���Ϸ� �ֱ�(�Ϲ�,Ư��ȸ�踸(A, B)
					                    throw new ProcessException("E002");
					                }
                                    logger.info("���⵵ �����۾�");
                                }								
                                
							    if (IR061610.insertLastData(conn, reg_date) < 1) {  // ���⵵ ���ϸ��� ���
						            throw new ProcessException("E002");
                                }
                                logger.info("���⵵ ���ϸ��� ���");
							}
						} else {
							request.setAttribute("page.mn06.SucMsg",   "������ư�� ���� ó���ϼ���.");
						}
					}
				}  //���� ��
				// --------- ���⵵ �۾� ----------------//
                CommonEntity beforeData = IR060000.dailyCheck(conn, before_date);  //�������� ����üũ
				if (beforeData.size() == 0)	{
					request.setAttribute("page.mn06.SucMsg",   "���������� ���ݿ��� ���� ����ϼ���.");
				} else if (!"Y".equals(beforeData.getString("M210_ETCDEPOSITSTATE"))) {  //��Ÿ������ ������� ���� ���
				    request.setAttribute("page.mn06.SucMsg",   "���������� ��Ÿ���� �ܾ��� ���� ����ϼ���.");
				} else {
					if (dailyData.size() == 0 )	{  // ���ϸ����ڷ� ��� ����
					    CommonEntity janakData = IR060000.getJanakData(conn, reg_date);  //�ܾ��� �ڷ� ���翩��

					    if (janakData.size() == 0) {
						    request.setAttribute("page.mn06.SucMsg",   "���� �ܾ��� �ڷᰡ �����ϴ�. �����ڿ��� ���ǹٶ��ϴ�.");
					    } else {
							if (!after_year.equals(fis_year)) {    //ȸ�迬���� �Ϳ����� ������ �ٸ� ��� ( ��, 12�� 31�� �۾�)
								if (IR061610.insertJanackData2(conn, reg_date, after_date) < 1)	{ // �Ϳ����� ������ ȸ�迬���� �����ؼ� ���Ϸ� �ֱ�
					                throw new ProcessException("E002");
					            }

                                if (IR061610.insertIlbanData(conn, reg_date, after_date) < 1) {  //�Ϲ�ȸ��,Ư��ȸ�� ��� �� 0���� �ؼ� �ֱ�
                                    throw new ProcessException("E002");
                                }
                                logger.info("12�� 31�� �۾�");
							} else {
                                if (reg_date.equals(magam_date)) {
                                    //�Ϲ� Ư��ȸ��� ���� 3�� 11���ڷῡ ���⵵, ���� 3��10�� �ܾ� �ջ��� insert�Ѵ�
                                    if (IR061610.insertJanackData4(conn, reg_date, after_date, fis_year) < 1)	{ //ȸ�����ڸ� �޾Ƽ� ���Ϸ� �ֱ�(3�� 10�� �۾�ó��)
					                    throw new ProcessException("E002");
					                }
                                    logger.info("3�� 10���� �����ڷ� �ֱ�");
                                } else {
					                if (IR061610.insertJanackData(conn, reg_date, after_date) < 1)	{ //ȸ�����ڸ� �޾Ƽ� ���Ϸ� �ֱ�
					                    throw new ProcessException("E002");
					                }
                                    logger.info("���⵵ ���� �ڷ� �ֱ�");
                                }
                            }
                            
                            CommonEntity pcNo = TransDAO.getTmlNo(conn);
					        String tml_no = pcNo.getString("M260_TERMINALNO");
							if (IR061610.insertAcctInfo(conn, reg_date, tml_no) < 1) {   
								//���� �������̺� ���� �ֱ�
								throw new ProcessException("E002");
							}
              
							if (Integer.parseInt(reg_date) <= Integer.parseInt(magam_date) ) {  
								//����� ������ڰ� ȸ�踶�����ں��� ���ų� ����
							    if (IR061610.insertAcctInfo2(conn, reg_date, tml_no) < 1) {   
									//���� �������̺� ���� �ֱ�(�Ϲ�,Ư��ȸ�踸)
							        throw new ProcessException("E002");
						 	    }
							}

							if (IR061610.insertDailyData(conn, reg_date) < 1) {  //���⵵ ���� ���� ���
					            throw new ProcessException("E002");
                            }

                            logger.info("���⵵ ���ϸ������");
                            
							request.setAttribute("page.mn06.SucMsg",   "���ݿ��� �ܾ��� ��ϵǾ����ϴ�.");
					    }
					} else {
                        logger.info("���⵵ ���� ��ư�� ������ ��");
						request.setAttribute("page.mn06.SucMsg",   "������ư�� ���� ó���ϼ���.");
					}
				}
			}
		}
        
    }
}