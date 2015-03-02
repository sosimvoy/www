/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR060000Register.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금운용 > 자금예탁승인조회-일계등록
***********************************************************************/
package com.etax.command.mn06;

import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;
import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn06.IR060000;
import com.etax.command.common.TransLogInsert;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;
public class IR060000Register {
    private static Logger logger = Logger.getLogger("IR060000Register"); // log4j 설정

    public static String permission(Connection conn, CommonEntity pmsData) throws SQLException{     
    String SucMsg = "";    
    String acc_date = pmsData.getString("acc_date");  //회계일자        logger.info("acc_date 1 : " + acc_date);    
    long addAmt = 0L;  // 테이블 증감액    
    conn.setAutoCommit(false);
        while (Integer.parseInt(acc_date) <= Integer.parseInt(TextHandler.getBusinessDate(conn, TextHandler.addDays(TextHandler.getCurrentDate(), 1))) ) {
            logger.info("acc_date 2 : " + acc_date);
            CommonEntity singleCnt = IR060000.getSingleCount(conn, pmsData, acc_date);  //잔액장 개별조회
            if ("0".equals(singleCnt.getString("CNT")) ) {
                if (acc_date.equals(pmsData.getString("acc_date")) ) {
                    //루프 속 회계일자와 입력된 회계일자가 같을 때
                    if ("1".equals(pmsData.getString("work_flag")) || "3".equals(pmsData.getString("work_flag")) ) {

                        CommonEntity impCnt = IR060000.getImpCount(conn, pmsData);  // 잔액장 조회 회계년도, 회계일자 제외
                        if ("0".equals(impCnt.getString("CNT")) ) {
                            if (IR060000.insertHiddenData(conn, pmsData, acc_date) < 1 ) {
                                //전일잔액은 0으로, 당일증감액은 입력금액으로 등록
                                conn.rollback();
                                conn.setAutoCommit(true);
                                SucMsg = "등록 중 오류가 발생하였습니다.";
                            }
                        } else {
                            //잔액장 존재할 때
                            SucMsg = "등록 중 오류가 발생하였습니다.";
                            acc_date = "99999999";
                            conn.rollback();
                            conn.setAutoCommit(true);
                        }
                    } else {
                        //work_flag가 2, 4일 때
                        SucMsg = "등록 중 오류가 발생하였습니다.";
                        acc_date = "99999999";
                        conn.rollback();
                        conn.setAutoCommit(true);
                    }
                } else {
                    SucMsg = "";
                    acc_date = "99999999";
                }                logger.info("acc_date 3 : " + acc_date);
            } else {                logger.info("acc_date 4 : " + acc_date);
                //잔액장 개별조회가 0건이 아닐 때
                if (acc_date.equals(pmsData.getString("acc_date")) ) {
                    //작업회계일자와 입력 일자가 같을 때                    logger.info("deposit_type : " + pmsData.getString("deposit_type"));                    logger.info("due_day : " + pmsData.getString("due_day"));
                    CommonEntity fieldInfo = IR060000.getFieldInfo(conn, pmsData);
                    String field_name = fieldInfo.getString("FIELD_NAME");                    logger.info("field_name : " + field_name);
                    if ("1".equals(pmsData.getString("work_flag")) || "2".equals(pmsData.getString("work_flag")) ) {
                    // 작업 구분이 1, 2 일 때(신규, 입금)
                    //업데이트 추가
                        if (IR060000.updatePlusAmt(conn, pmsData, acc_date, field_name) < 1) {
                            //해당금액으로 당일 또는 전일 금액 수정
                            conn.rollback();
                            conn.setAutoCommit(true);
                        }
                    } else {
                        //작업 구분이 3, 4 일 때(해지, 입금취소)
                        //업데이트 추가
                        if (IR060000.updateMinusAmt(conn, pmsData, acc_date, field_name) < 1) {
                            //해당금액으로 당일 또는 전일 금액 수정
                            conn.rollback();
                            conn.setAutoCommit(true);
                        }
                    }
                } else {
                    //작업회계일자와 입력 일자가 다를 때
                    CommonEntity yesterField = IR060000.getYesterFieldInfo(conn, pmsData);
                    String yester_field = yesterField.getString("FIELD_NAME");
                    //업데이트 추가
                    if (IR060000.updateYesterAmt(conn, pmsData, acc_date, yester_field, addAmt) < 1) {
                        //해당금액으로 당일 또는 전일 금액 수정
                        conn.rollback();
                        conn.setAutoCommit(true);
                    }
                }
        
                CommonEntity yesterInfo = IR060000.getYesterInfo(conn, pmsData, acc_date);
                addAmt = yesterInfo.getLong("YESTER_AMT");                logger.info("acc_date 5 : " + acc_date);
                acc_date = TextHandler.getBusinessDate(conn, TextHandler.addDays(acc_date, 1));  // 작업회계일자를 다음 영업일로 수정
                logger.info("acc_date 6 : " + acc_date);
            }
        }
        return SucMsg;
    }
}