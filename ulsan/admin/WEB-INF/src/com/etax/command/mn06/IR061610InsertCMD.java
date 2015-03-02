 /**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR061610InsertCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금운용 > 잔액장자료입력(공금예금)
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

    private static Logger logger = Logger.getLogger(IR061610InsertCMD.class); // log4j 설정

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 

        CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("reg_date",            request.getParameter("reg_date"));
        paramInfo.setValue("last_year",           Integer.parseInt(request.getParameter("reg_date").substring(0,4)) -1 + "");

        String fis_year    = (request.getParameter("reg_date")).substring(0,4);  //회계연도
		String reg_date    = request.getParameter("reg_date");   //회계일
		String before_date = TextHandler.getBeforeDate(conn, reg_date);  //전영업일
		String after_date  = TextHandler.getBusinessDate(conn, TextHandler.addDays(reg_date, 1));  //익영업일
		String after_year  = after_date.substring(0,4);  //익영업일 회계연도
        String magam_date  = IR060000.ilbanCheck(conn, paramInfo).getString("M320_DATEILBAN");
        logger.info("일반,특별 마감일 : " + magam_date);
        logger.info("before_date : " + before_date);
        logger.info("after_date : " + after_date);

		CommonEntity daemonData = IR060000.getDaemonData(conn, reg_date); 
		if (!(request.getParameter("reg_date")).equals(TextHandler.getBusinessDate(conn, reg_date)) ) { //영업일 체크
		    request.setAttribute("page.mn06.SucMsg",   "해당일자는 영업일이 아닙니다.");
		} else if (Integer.parseInt(request.getParameter("reg_date")) > Integer.parseInt(TextHandler.getCurrentDate())) {  //등록일이 오늘을 지났는지 체크
		    request.setAttribute("page.mn06.SucMsg",   "등록일자가 금일이후입니다.");
		} else if (daemonData.size() > 0 && "N".equals(daemonData.getString("M480_INQUIRYYN")) ) { //데몬 연동 테이블 조회 "N"이거나 자료가 없으면 에러처리
		    request.setAttribute("page.mn06.SucMsg",   "기등록건 처리 중입니다. 잠시 후 시도하세요.");
		} else {
			CommonEntity dailyData = IR060000.dailyCheck(conn, reg_date);
			if ("Y".equals(dailyData.getString("M210_WORKENDSTATE")) || "Y".equals(dailyData.getString("M210_PUBLICDEPOSITSTATE"))) { //업무마감체크
				request.setAttribute("page.mn06.SucMsg",   "공금예금 잔액등록이 마감되었습니다.");
			} else {
                // ----------- 폐쇄기 작업 ---------------//
				if (Integer.parseInt(reg_date) <= Integer.parseInt(magam_date)) {  //폐쇄기면 등록일자가 회계마감일자보다 같거나 작음
                    logger.info("폐쇄기 작업 시작");
					CommonEntity lastBefore = IR060000.lastDailyCheck(conn, before_date);  //과년도(전영업일) 마감체크
					if (lastBefore.size() == 0)	{  // 마감을 하지 않은 경우
						request.setAttribute("page.mn06.SucMsg",   "과년도 전영업일의 공금예금 먼저 등록하세요.");
					} else if (!"Y".equals(lastBefore.getString("M210_ETCDEPOSITSTATE"))) {  //기타예금등록하지 않은 경우
					    request.setAttribute("page.mn06.SucMsg",   "과년도 전영업일의 기타예금 잔액을 먼저 등록하세요.");
					} else {
						CommonEntity lastCheck = IR060000.lastDailyCheck(conn, reg_date);  //과년도영업일 마감체크
						if (lastCheck.size() == 0)	{  // 일일마감자료 없음

						    CommonEntity gwaJanakData = IR060000.getLastJanakData(conn, reg_date);  //과년도 잔액장 자료 존재여부(일반,특별회계만)
							if (gwaJanakData.size() == 0) {
								request.setAttribute("page.mn06.SucMsg",    "과년도 잔액장 자료가 없습니다. 관리자에게 문의바랍니다.");
							} else {
                                if (Integer.parseInt(reg_date) >= Integer.parseInt(fis_year + "0101")
                                    && Integer.parseInt(reg_date) < Integer.parseInt(magam_date)) {
                                    if (IR061610.insertJanackData3(conn, reg_date, after_date) < 1)	{ //회계일자를 받아서 익일로 넣기(일반,특별회계만(A, B)
					                    throw new ProcessException("E002");
					                }
                                    logger.info("과년도 익일작업");
                                }								
                                
							    if (IR061610.insertLastData(conn, reg_date) < 1) {  // 과년도 일일마감 등록
						            throw new ProcessException("E002");
                                }
                                logger.info("과년도 일일마감 등록");
							}
						} else {
							request.setAttribute("page.mn06.SucMsg",   "수정버튼을 눌러 처리하세요.");
						}
					}
				}  //폐쇄기 끝
				// --------- 현년도 작업 ----------------//
                CommonEntity beforeData = IR060000.dailyCheck(conn, before_date);  //전영업일 마감체크
				if (beforeData.size() == 0)	{
					request.setAttribute("page.mn06.SucMsg",   "전영업일의 공금예금 먼저 등록하세요.");
				} else if (!"Y".equals(beforeData.getString("M210_ETCDEPOSITSTATE"))) {  //기타예금을 등록하지 않은 경우
				    request.setAttribute("page.mn06.SucMsg",   "전영업일의 기타예금 잔액을 먼저 등록하세요.");
				} else {
					if (dailyData.size() == 0 )	{  // 일일마감자료 등록 여부
					    CommonEntity janakData = IR060000.getJanakData(conn, reg_date);  //잔액장 자료 존재여부

					    if (janakData.size() == 0) {
						    request.setAttribute("page.mn06.SucMsg",   "금일 잔액장 자료가 없습니다. 관리자에게 문의바랍니다.");
					    } else {
							if (!after_year.equals(fis_year)) {    //회계연도와 익영업일 연도가 다를 경우 ( 즉, 12월 31일 작업)
								if (IR061610.insertJanackData2(conn, reg_date, after_date) < 1)	{ // 익영업일 연도를 회계연도로 세팅해서 익일로 넣기
					                throw new ProcessException("E002");
					            }

                                if (IR061610.insertIlbanData(conn, reg_date, after_date) < 1) {  //일반회계,특별회계 모든 값 0으로 해서 넣기
                                    throw new ProcessException("E002");
                                }
                                logger.info("12월 31일 작업");
							} else {
                                if (reg_date.equals(magam_date)) {
                                    //일반 특별회계는 당해 3월 11일자료에 과년도, 당해 3월10자 잔액 합산을 insert한다
                                    if (IR061610.insertJanackData4(conn, reg_date, after_date, fis_year) < 1)	{ //회계일자를 받아서 익일로 넣기(3월 10일 작업처리)
					                    throw new ProcessException("E002");
					                }
                                    logger.info("3월 10일자 익일자료 넣기");
                                } else {
					                if (IR061610.insertJanackData(conn, reg_date, after_date) < 1)	{ //회계일자를 받아서 익일로 넣기
					                    throw new ProcessException("E002");
					                }
                                    logger.info("현년도 익일 자료 넣기");
                                }
                            }
                            
                            CommonEntity pcNo = TransDAO.getTmlNo(conn);
					        String tml_no = pcNo.getString("M260_TERMINALNO");
							if (IR061610.insertAcctInfo(conn, reg_date, tml_no) < 1) {   
								//데몬 연계테이블에 계좌 넣기
								throw new ProcessException("E002");
							}
              
							if (Integer.parseInt(reg_date) <= Integer.parseInt(magam_date) ) {  
								//폐쇄기면 등록일자가 회계마감일자보다 같거나 작음
							    if (IR061610.insertAcctInfo2(conn, reg_date, tml_no) < 1) {   
									//데몬 연계테이블에 계좌 넣기(일반,특별회계만)
							        throw new ProcessException("E002");
						 	    }
							}

							if (IR061610.insertDailyData(conn, reg_date) < 1) {  //현년도 일일 마감 등록
					            throw new ProcessException("E002");
                            }

                            logger.info("현년도 일일마감등록");
                            
							request.setAttribute("page.mn06.SucMsg",   "공금예금 잔액이 등록되었습니다.");
					    }
					} else {
                        logger.info("현년도 수정 버튼을 눌러야 함");
						request.setAttribute("page.mn06.SucMsg",   "수정버튼을 눌러 처리하세요.");
					}
				}
			}
		}
        
    }
}